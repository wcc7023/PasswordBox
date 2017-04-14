package com.wang.eggroll.passwordbox.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by eggroll on 28/03/2017.
 */

public class AESHelper {

    // 算法/模式/填充
    private static final String CipherMode = "AES/ECB/PKCS5Padding";

    // 创建密钥
    private static SecretKeySpec createKey(String password) throws UnsupportedEncodingException {
        if (password == null) {
            password = "";
        }
        byte[] bytePassword = password.getBytes("utf-8");
        byte[] bytePasswordFinal;

        //根据输入密码位数的不同，自动在AES-128，AES-192，AES-256中切换
        if(bytePassword.length <= 16){
            bytePasswordFinal = new byte[16];
            for (int i = 0; i < bytePassword.length; i++) {
                bytePasswordFinal[i] = bytePassword[i];
            }
        } else if (bytePassword.length <= 24 && bytePassword.length >16) {
            bytePasswordFinal = new byte[24];
            for (int i = 0; i < bytePassword.length; i++) {
                bytePasswordFinal[i] = bytePassword[i];
            }
        } else if(bytePassword.length <= 32 && bytePassword.length >24){
            bytePasswordFinal = new byte[32];
            for (int i = 0; i < bytePassword.length; i++) {
                bytePasswordFinal[i] = bytePassword[i];
            }
        }
        //输入密码大于32位，截断
        else bytePasswordFinal = Arrays.copyOf(bytePassword, 32);

        return new SecretKeySpec(bytePasswordFinal, "AES");
    }

    // 加密字节数据
    public static byte[] encrypt(byte[] content, String password) {
        try {
            SecretKeySpec key = createKey(password);
            System.out.println(key);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    ///** 加密(结果进行Base64变换) **/
    //content是用户输入的密码，password是MD5之后的锁屏密码
    public static String encrypt(String content, String password) {
        byte[] data = null;
        try {
            //将明文转换成byte数组
            data = content.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将明文byte数组加密为密文byte数组
        byte[] data2 = encrypt(data, password);
        //将密文byte数组进行Base64变换，使之可见，并转换成String
        String result = new String(Base64.encode(data2, Base64.DEFAULT));
        return result;
    }

    //解密字节数组
    public static byte[] decrypt(byte[] content, String password) {
        try {
            SecretKeySpec key = createKey(password);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 解密Base64编码
    public static String decrypt(String content, String password) {
        byte[] data = null;
        try {
            //把密文字符串进行反Base64变换，变换为原始密文数组
            data = Base64.decode(content, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将原始密文数组进行解密
        data = decrypt(data, password);
        if (data == null)
            return null;
        String result = null;
        try {
            //将解密后的数组转换为String
            result = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
