package com.shinelon.deathknight.ijpay.service.biz.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import com.shinelon.deathknight.ijpay.bean.BizBillAnalyseResult;
import com.shinelon.deathknight.ijpay.bean.BizBillDetailBean;
import com.shinelon.deathknight.ijpay.constants.AlipayConstants;
import com.shinelon.deathknight.ijpay.handler.bill.AlipayBizBillAnalyseHandler;
import com.shinelon.deathknight.ijpay.service.biz.BizBillAnalyseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.zip.ZipInputStream;

/**
 * 业务账单分析
 *
 * @author Shinelon
 */
@Slf4j
@Service
public class AlipayBizBillAnalyseServiceImpl implements BizBillAnalyseService {

    @Autowired
    @Qualifier("billBizPool")
    private ExecutorService billBizPool;
    @Autowired
    private AlipayBizBillAnalyseHandler alipayBizBillAnalyseHandler;

    public BizBillAnalyseResult analyse(InputStream in, Charset charset) {
        BizBillAnalyseResult ret = new BizBillAnalyseResult();
        ret.setIsSuccess(true);
        List<String> fileLines = getFileLines(in, charset);
        //to check list
        List<CompletableFuture<BizBillDetailBean>> featureList = new ArrayList<>(fileLines.size());
        for (String line : fileLines) {
            addFeature(featureList, line);
        }
        List<BizBillDetailBean> billList = getResult(featureList);
        ret.setBillList(billList);
        return ret;
    }

    private List<BizBillDetailBean> getResult(List<CompletableFuture<BizBillDetailBean>> featureList) {
        List<BizBillDetailBean> billList = new ArrayList<>(featureList.size());
        for (CompletableFuture<BizBillDetailBean> tmpFuture : featureList) {
            try {
                BizBillDetailBean bizBillDetailBean = tmpFuture.get();
                billList.add(bizBillDetailBean);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            } catch (ExecutionException e) {
                log.error(e.getMessage(), e);
            }

        }
        return billList;
    }

    private void addFeature(List<CompletableFuture<BizBillDetailBean>> featureList, String line) {
        if (StringUtils.startsWith(line, AlipayConstants.BILL_FILE_COMMENT_SYMBOL)) {
            return;
        }
        if (StringUtils.startsWith(line, AlipayConstants.CONTENT_HEAD_LINE_1)) {
            return;
        }
        CompletableFuture<BizBillDetailBean> analyseFuture = CompletableFuture.supplyAsync(() -> {
            return alipayBizBillAnalyseHandler.analyse(line);
        }, billBizPool);
        analyseFuture.exceptionally(e -> {
            log.error(e.getMessage(), e);
            return null;
        });
        featureList.add(analyseFuture);
    }

    private List<String> getFileLines(InputStream in, Charset charset) {
        List<String> fileLines = new ArrayList<>(512);
        ZipInputStream zipInputStream = new ZipInputStream(in, charset);
        ZipUtil.read(zipInputStream, zipEntry -> {
            String name = zipEntry.getName();
            if (!StringUtils.contains(name, AlipayConstants.BIZ_BILL_FILE_NAME)) {
                return;
            }
            IoUtil.readLines(zipInputStream, charset, fileLines);
        });
        IoUtil.close(zipInputStream);
        IoUtil.close(in);
        return fileLines;
    }
}
