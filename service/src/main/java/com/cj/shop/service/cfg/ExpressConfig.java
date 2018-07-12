package com.cj.shop.service.cfg;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/7/12
 * @since 1.0
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "cj.mall.express")
public class ExpressConfig {
    private String userId;

    private String apiKey;

    public Map<String, Object> map;

    public ExpressConfig() {
        if (map == null) {
            synchronized (this) {
                map = new HashMap<>();
                map.put("顺丰速运", "SF");
                map.put("百世快递", "HTKY");
                map.put("中通快递", "ZTO");
                map.put("申通快递", "STO");
                map.put("圆通速递", "YTO");
                map.put("韵达速递", "YD");
                map.put("邮政快递包裹", "YZPY");
                map.put("EMS", "EMS");
                map.put("天天快递", "HHTT");
                map.put("京东物流", "JD");
                map.put("优速快递", "UC");
                map.put("德邦快递", "DBL");
                map.put("快捷快递", "FAST");
                map.put("宅急送", "ZJS");
                map.put("TNT快递", "TNT");
                map.put("UPS", "UPS");
                map.put("百世快运", "BTWL");
                map.put("捷安达", "JAD");
                map.put("苏宁物流", "SNWL");
                map.put("安鲜达", "AXD");
                map.put("阿里跨境电商物流", "ALKJWL");
            }
        }
    }


    /**
     * 调用第三方api 获取签名
     * 加密算法：
     * 先md5加密 jsonParam+API-Key 然后Base64编码，最后 进行URL(utf-8)编码
     *
     * @param jsonPram
     * @return
     */
    public String getDataSign(String jsonPram) throws UnsupportedEncodingException {
        return URLEncoder.encode(Base64.getEncoder().encode(getMD5Byte(jsonPram + apiKey)).toString(), "UTF-8");
    }


    /**
     * MD5加密字符串 返回十六进制编码
     *
     * @param message 要加密的信息
     * @return 十六进制编码的MD5字符串
     */
    public static String getMD5Hex(String message) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); // 创建一个md5算法对象
            byte[] messageByte = message.getBytes("UTF-8");
            byte[] md5Byte = md.digest(messageByte); // 获得MD5字节数组,16*8=128位
            md5 = HexUtils.toHexString(md5Byte); // 转换为16进制字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }

    /**
     * MD5加密字符串 返回字节数组
     *
     * @param message 要加密的信息
     * @return 十六进制编码的MD5字符串
     */
    public static byte[] getMD5Byte(String message) {
        byte[] md5Byte = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); // 创建一个md5算法对象
            byte[] messageByte = message.getBytes("UTF-8");
            md5Byte = md.digest(messageByte); // 获得MD5字节数组,16*8=128位
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Byte;
    }
}
