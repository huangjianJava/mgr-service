package com.advance.mgr.rmsAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: TODO
 * @date Date : 2018/8/2 16:59
 * @since JDK 1.8
 */
public class Constant {

    /**
     * 描述 : 头信息(rms应用名称)
     */
    public static final String HEADER_RMS_APPLICATION_NAME_CODE = "rmsApplicationName";

    /**
     * 描述 : 头信息(rms认证秘钥)
     */
    public static final String HEADER_RMS_SIGN_CODE = "rmsSign";


    /**
     * 描述 : 日期格式
     */
    public static final String DATA_FORMAT = "yyyy-MM-dd'T'HH";

    /**
     * 描述 : http头
     */
    public static final String HTTP = "http://";

    /**
     * 描述 : https头
     */
    public static final String HTTPS = "https://";


    /**
     * 描述 : 私有化构造函数
     */
    private Constant() {

    }

    /**
     * 描述 : 计算sign
     *
     * @param rmsApplicationName 系统标志
     * @param secret             系统私钥
     * @return sign
     */
    public static String sign(String rmsApplicationName, String secret) {
        final String split = "_";
        StringBuilder sb = new StringBuilder();
        sb.append(rmsApplicationName).append(split).append(secret).append(split)
                .append(new SimpleDateFormat(DATA_FORMAT).format(new Date()));
        return DigestUtils.md5Hex(sb.toString());
    }
}
