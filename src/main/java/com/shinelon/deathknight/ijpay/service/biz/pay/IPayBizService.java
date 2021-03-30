package com.shinelon.deathknight.ijpay.service.biz.pay;

import com.shinelon.deathknight.ijpay.dto.biz.PayReqDTO;
import com.shinelon.deathknight.ijpay.dto.biz.PayResDTO;
import com.shinelon.deathknight.ijpay.service.biz.ITradeBizService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/***
 * @author Shinelon
 */
@Service
public interface IPayBizService extends ITradeBizService {

    /**
     * 创建token
     *
     * @param userId
     * @return
     */
    PayResDTO payToken(String userId);

    /**
     * alipayPc
     *
     * @param payReqDTO
     * @param response
     */
    void alipayPc(PayReqDTO payReqDTO, HttpServletResponse response);

    /**
     * alipayQrCode
     *
     * @param payReqDTO
     * @return
     */
    PayResDTO alipayQrCode(PayReqDTO payReqDTO);

    /**
     * wechatNative
     *
     * @param payReqDTO
     * @return
     */
    PayResDTO wechatNative(PayReqDTO payReqDTO);

    /**
     * query
     *
     * @param payChannel
     * @param orderNo
     * @return
     */
    PayResDTO query(String payChannel, String orderNo);

    /**
     * close
     *
     * @param payChannel
     * @param orderNo
     */
    void close(String payChannel, String orderNo);


}
