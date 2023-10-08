package cn.stkit.wxl.utils;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file WxlX508TrustManager
 * @desc  证书信任管理器（用于https请求）
 * @website http://www.stkit.cn/
 * @date 2020/6/14 7:29 下午
 */

public class WxlX508TrustManager implements X509TrustManager
{

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

}
