package com.csuft.util;

import org.springframework.util.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class MD5Util {

    // MD5加密(只能加密，不能解密)
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

}
