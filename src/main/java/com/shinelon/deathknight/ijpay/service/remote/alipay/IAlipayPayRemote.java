package com.shinelon.deathknight.ijpay.service.remote.alipay;

import com.shinelon.deathknight.ijpay.dto.remote.alipay.AlipayPayReq;
import com.shinelon.deathknight.ijpay.dto.remote.alipay.AlipayPayRes;

import javax.servlet.http.HttpServletResponse;


/**
 * @author Shinelon
 * @date 2021-03-25 10:09
 */
public interface IAlipayPayRemote extends IAlipayTradeService {
     String NOTIFY_URL = "/aliPay/notify_url";
     String RETURN_URL = "/aliPay/return_url";

     /**
      * pcPay
      *
      * @param alipayPayReq
      * @param response
      */
     void pcPay(AlipayPayReq alipayPayReq, HttpServletResponse response);

     /**
      * close
      *
      * @param alipayPayReq
      * @return
      */
     AlipayPayRes close(AlipayPayReq alipayPayReq);

     /**
      * query
      *
      * @param alipayPayReq
      * @return
      */
     AlipayPayRes query(AlipayPayReq alipayPayReq);

}
