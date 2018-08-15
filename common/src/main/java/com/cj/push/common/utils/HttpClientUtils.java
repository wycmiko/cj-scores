package com.cj.push.common.utils;

import com.cj.push.common.consts.HttpConstants;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * HttpClient 请求工具类
 *
 * @author yuchuanWeng( )
 * @create 2018/3/21
 * @date 1.0
 */
public class HttpClientUtils {


    // 设置请求和传输超时时间 默认设置一分钟
    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom().setSocketTimeout(60000)
            .setConnectTimeout(60000).build();

    public static final Integer FORWARD_STATUS = 302;
    public static final String LOCATION_CONTENT_TYPE = "Location";

    /**
     * HttpClient异步发送POST请求 Json参数
     *
     * @param url       请求路径
     * @param jsonParam 请求参数
     * @return 异步返回数据
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static String httpAsyncPostJsonData(String url, String jsonParam)
            throws IOException, InterruptedException, ExecutionException {
        HttpResponse response = null;
        String result = null;
        // 构建异步请求客户端对象
        try (CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.createDefault()) {
            httpAsyncClient.start();
            HttpPost post = new HttpPost(url);
            // 构建StringEntity对象 存放参数和设置请求头
            StringEntity se = new StringEntity(jsonParam);
            // 添加请求头 application/json;charset=UTF-8
            se.setContentType(HttpConstants.CONTENT_TYPE_JSON_UTF8);
            post.setConfig(REQUEST_CONFIG);
            post.setEntity(se);
            // 发送异步请求 返回future
            Future<HttpResponse> future = httpAsyncClient.execute(post, null);
            // future.get() 有返回则返回 否则阻塞等待其返回
            response = future.get();
            result = EntityUtils.toString(response.getEntity(), HttpConstants.UTF_8);
        } finally {
        }
        return result;
    }


    public static String httpAsyncPutJsonData(String url, String jsonParam)
            throws IOException, InterruptedException, ExecutionException {
        HttpResponse response = null;
        String result = null;
        // 构建异步请求客户端对象
        try (CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.createDefault()) {
            httpAsyncClient.start();
            HttpPut post = new HttpPut(url);
            // 构建StringEntity对象 存放参数和设置请求头
            StringEntity se = new StringEntity(jsonParam);
            // 添加请求头 application/json;charset=UTF-8
            se.setContentType(HttpConstants.CONTENT_TYPE_JSON_UTF8);
            post.setConfig(REQUEST_CONFIG);
            post.setEntity(se);
            // 发送异步请求 返回future
            Future<HttpResponse> future = httpAsyncClient.execute(post, null);
            // future.get() 有返回则返回 否则阻塞等待其返回
            response = future.get();
            result = EntityUtils.toString(response.getEntity(), HttpConstants.UTF_8);
        } finally {
        }
        return result;
    }

    /**
     * HttpClient异步发送POST请求 Form-Data参数
     *
     * @param url      请求路径
     * @param paramMap 请求参数Map集合
     * @return 异步返回数据
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static String httpAsyncPostFormData(String url, Map<String, Object> paramMap)
            throws IOException, InterruptedException, ExecutionException {
        List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
        HttpResponse response = null;
        String result = null;
        // 构建异步请求客户端对象
        try (CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.createDefault();) {
            httpAsyncClient.start();
            HttpPost post = new HttpPost(url);
            // 添加form请求头
            post.addHeader(HTTP.CONTENT_TYPE, HttpConstants.CONTENT_TYPE_FORM_DATA);
            post.setConfig(REQUEST_CONFIG);
            if (paramMap != null && !paramMap.isEmpty()) {
                for (String key : paramMap.keySet()) {
                    nameValuePairs.add(new BasicNameValuePair(key, paramMap.get(key).toString()));
                }
                // 封装请求参数
                post.setEntity(new UrlEncodedFormEntity(nameValuePairs, HttpConstants.UTF_8));
            }
            // 发送异步请求 返回future
            Future<HttpResponse> future = httpAsyncClient.execute(post, null);
            // future.get() 有返回则返回 否则阻塞等待其返回
            response = future.get();
            result = EntityUtils.toString(response.getEntity(), HttpConstants.UTF_8);
        } finally {
        }
        return result;
    }

    /**
     * Http发送Post请求,返回Response对象
     *
     * @param url         请求地址
     * @param param       请求参数
     * @param contentType 请求头信息
     * @param config      请求配置
     * @return
     * @throws IOException
     */
    public static CloseableHttpResponse httpPostResponse(String url, String param, String contentType,
                                                         RequestConfig config) throws IOException {
        CloseableHttpResponse response = null;
        try (CloseableHttpClient defaultClient = HttpClients.createDefault();) {
            HttpPost post = new HttpPost(url);
            // 构建StringEntity对象 存放参数和设置请求头
            StringEntity se = new StringEntity(param);
            // 添加请求头
            se.setContentType(contentType);
            // 添加配置
            post.setConfig(config);
            // 封装实体
            post.setEntity(se);
            // 发送请求 获取响应信息
            response = defaultClient.execute(post);
        } finally {
        }
        return response;
    }

    public static String httpGetData(String url) throws IOException {
        CloseableHttpResponse response = null;
        String result = null;
        HttpGet get = new HttpGet(url);
        try (CloseableHttpClient defaultClient = HttpClients.createDefault()) {
            response = defaultClient.execute(get);
            result = EntityUtils.toString(response.getEntity());
        } finally {
        }
        return result;
    }


    public static String httpDeleteData(String url) throws IOException {
        CloseableHttpResponse response = null;
        String result = null;
        HttpDelete delete = new HttpDelete(url);
        try (CloseableHttpClient defaultClient = HttpClients.createDefault()) {
            response = defaultClient.execute(delete);
            result = EntityUtils.toString(response.getEntity());
        } finally {
        }
        return result;
    }

    public static HttpResponse httpGetResponse(String url) throws IOException {
        CloseableHttpResponse response = null;
        HttpGet get = new HttpGet(url);
        try (CloseableHttpClient defaultClient = HttpClients.createDefault()) {
            response = defaultClient.execute(get);
        } finally {
        }
        return response;
    }

    public static byte[] httpGetResponseImg(String url) throws IOException {
        byte[] bytes = null;
        CloseableHttpResponse response = null;
        HttpGet get = new HttpGet(url);
        try (CloseableHttpClient defaultClient = HttpClients.createDefault()) {
            response = defaultClient.execute(get);
            bytes = EntityUtils.toByteArray(response.getEntity());
        } finally {
        }
        return bytes;
    }


}
