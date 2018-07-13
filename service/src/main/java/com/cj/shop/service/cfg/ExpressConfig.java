package com.cj.shop.service.cfg;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
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
    public String getDataSign(String jsonPram) throws Exception {
        return  encrypt(jsonPram, apiKey, "UTF-8");
    }



    /**
     * base64编码
     * @param str 内容
     * @param charset 编码方式
     * @throws UnsupportedEncodingException
     */
    private String base64(String str, String charset) throws UnsupportedEncodingException{
        String encoded = base64Encode(str.getBytes(charset));
        return encoded;
    }
    /**
     * MD5加密
     * @param str 内容
     * @param charset 编码方式
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    @SuppressWarnings("unused")
    private String urlEncoder(String str, String charset) throws UnsupportedEncodingException {
        String result = URLEncoder.encode(str, charset);
        return result;
    }

    /**
     * 电商Sign签名生成
     * @param content 内容
     * @param keyValue Appkey
     * @param charset 编码方式
     * @throws UnsupportedEncodingException ,Exception
     * @return DataSign签名
     */
    @SuppressWarnings("unused")
    private String encrypt (String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception
    {
        if (keyValue != null)
        {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    private static char[] base64EncodeChars = new char[] {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/' };

    public static String base64Encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len)
            {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len)
            {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }
}
