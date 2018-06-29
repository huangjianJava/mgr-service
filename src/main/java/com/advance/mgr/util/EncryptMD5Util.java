package com.advance.mgr.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.MessageDigest;

public class EncryptMD5Util {
    private static Log logger = LogFactory.getLog(EncryptMD5Util.class);

    private final static String[] hexDigits =
            {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String getMD5(String value) {
        String result = null;

        if (value == null) {
            return null;
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] results = md.digest(value.getBytes());
            String resultString = byteArrayToHexString(results);

            // result = resultString.toUpperCase();
            result = resultString.toLowerCase();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }


        return result;

    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();

        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static void main(String[] args) {
        String password = "12345";
        System.out.println("MD5 of " + password + ":\n" + EncryptMD5Util.getMD5(password));

    }
}
