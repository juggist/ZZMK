package com.juggist.jcore.utils;

import android.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author juggist
 * @date 2018/10/30 5:00 PM
 */
public class EncryptUtil {
    public static String MD5(String string){

        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(string.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String DES(String data){
        String ENCRYPTION_MANNER = "DESede/CBC/PKCS5Padding";
        String KEY = "passwordpasswordpassword";
        String IV = "password";
        // 创建一个DESKeySpec对象
        SecretKeySpec secretKeySpec = null;
        String result = "";
        try {
            secretKeySpec = new SecretKeySpec(KEY.getBytes(),ENCRYPTION_MANNER);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(ENCRYPTION_MANNER);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(IV.getBytes()));
            byte[] encrypt = cipher.doFinal(data.getBytes());
            result = new String(Base64.encode(encrypt,Base64.DEFAULT));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return result;
    }
}
