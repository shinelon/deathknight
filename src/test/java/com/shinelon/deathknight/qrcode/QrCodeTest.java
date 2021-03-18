package com.shinelon.deathknight.qrcode;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import org.junit.jupiter.api.Test;

/***
 * @author Shinelon
 */
public class QrCodeTest {
    @Test
    public void qrCode() {
        String content = "url";
        QrConfig qrConfig = QrConfig.create();
        String ret = QrCodeUtil.generateAsBase64(content, qrConfig, ImgUtil.IMAGE_TYPE_JPEG);
        System.out.println(ret);
    }
}
