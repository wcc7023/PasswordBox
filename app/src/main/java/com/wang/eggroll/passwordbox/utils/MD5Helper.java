package com.wang.eggroll.passwordbox.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by eggroll on 12/04/2017.
 */

public class MD5Helper {

    //TODO:这里涉及到View层的东西，不应该出现，应该在Presenter完成View-->Byte的转换
    public static String patternToMD5String(List<PatternView.Cell> pattern){
        byte[] patternToByte = PatternUtils.patternToBytes(pattern);
        String md5 = null;
        try {
            md5 = getMD5(patternToByte);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }

    public static String getMD5(byte[] val) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(val);
        byte[] m = md5.digest();//加密
        return getString(m);
    }
    private static String getString(byte[] b){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < b.length; i ++){
            sb.append(b[i]);
        }
        return sb.toString();
    }
}
