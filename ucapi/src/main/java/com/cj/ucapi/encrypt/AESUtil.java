package com.cj.ucapi.encrypt;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>Create Time: 2018年03月21日</p>
 * <p>@author tangxd</p>
 **/
public class AESUtil {
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_STR = "AES/ECB/PKCS5Padding";
    /**
     * AES加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data,String key) throws Exception {
        SecretKeySpec secret = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR); // 创建密码器
        cipher.init(Cipher.ENCRYPT_MODE, secret);// 初始化
        return Hex.encodeHexString(cipher.doFinal(data.getBytes()));
    }

    /**
     * AES解密
     * @param data
     * @return
     * @throws Exception
     */
    public static String decrypt(String data,String key) throws Exception{
        SecretKeySpec secret = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
        cipher.init(Cipher.DECRYPT_MODE, secret);
        return new String(cipher.doFinal(Hex.decodeHex(data)));
    }
}
