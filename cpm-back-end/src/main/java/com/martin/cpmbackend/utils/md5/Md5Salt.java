package com.martin.cpmbackend.utils.md5;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author sunyinuo
 */
public class Md5Salt {

    public static String md5Salt(String text,Integer salt){
        return DigestUtils.md5Hex(text+salt);
    }

}
