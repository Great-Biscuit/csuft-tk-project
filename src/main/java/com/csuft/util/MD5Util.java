package com.csuft.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

public class MD5Util {

    // MD5加密
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

}
