package com.cj.push.service.util;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 极光第三方Push接口api
 * <a>https://www.jiguang.cn</a>
 *
 * @author yuchuanWeng
 * @date 2018/8/16
 * @since 1.0
 */
@Component
@Scope("singleton")
public class JiGuangPushBuilder {
    /**
     * 极光推送appKey&Value
     */
    private static String appKey;
    private static String masterSecret;

    @Value("${jiguang.appkey}")
    public void setAppKey(String appKey) {
        JiGuangPushBuilder.appKey = appKey;
    }

    @Value("${jiguang.masterSecret}")
    public void setMasterSecret(String masterSecret) {
        JiGuangPushBuilder.masterSecret = masterSecret;
    }


    public static class JPushClientBean {
        private static JPushClient jPushClient = new JPushClient(masterSecret, appKey);
    }

    /**
     * 获取客户端示例
     * static inner-class singleton
     *
     * @return
     */
    public static JPushClient getJPushClient() {
        return JPushClientBean.jPushClient;
    }

    /**
     * 推送全平台全用户指定文本
     *
     * @param alert 推送内容
     * @return
     */
    public static PushPayload buildPushAllPayload(String alert) {
        return PushPayload.alertAll(alert);
    }

    /**
     * 通过别名推送全平台用户内容
     *
     * @param alias        别名集合 如果为空 默认推送无别名的
     * @param alert        推送内容
     * @param isProduction ture=推送生产环境 false=开发环境
     * @return
     */
    public static PushPayload buildPushAllByAliasPayload(List<String> alias, String alert, String title, String subTitle, Boolean isProduction
            , Map<String, String> extras) {
        JsonObject alertJson = getAlertJson(title, subTitle, alert);
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience((alias == null || alias.isEmpty()) ? Audience.all() : Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(alert)
                                .setTitle(title)
                                .addExtras(extras)
                                .build())
                        .addPlatformNotification(
                                IosNotification.newBuilder()
                                        .setAlert(alertJson != null ? alertJson : alert)
                                        .addExtras(extras)
                                        .build()
                        ).build())
                .setOptions(Options.newBuilder().setApnsProduction(isProduction).build())
                .build();
    }


    /**
     * 通过别名推送全平台用户内容
     *
     * @param alias        别名集合
     * @param alert        推送内容
     * @param isProduction ture=推送生产环境 false=开发环境
     * @return
     */
    public static PushPayload buildPushAndroidIosByAliasPayload(List<String> alias, String alert, Boolean isProduction) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.alert(alert))
                .setOptions(Options.newBuilder().setApnsProduction(isProduction).build())
                .build();
    }

    /**
     * 推送安卓端
     *
     * @param alias        别名集合 如果为空 则推送全部
     * @param alert        IOS推送内容体
     * @param title        IOS推送标题
     * @param isProduction 是否推送生产环境
     * @param extras       业务扩展数据
     * @return
     */
    public static PushPayload buildPushAndroidPayload(List<String> alias, String alert, String title, Boolean isProduction, Map<String, String> extras) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience((alias == null || alias.isEmpty()) ? Audience.all() : Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(alert)
                                .setTitle(title)
                                .addExtras(extras)
                                .build()).build())
                .setOptions(Options.newBuilder().setApnsProduction(isProduction).build())
                .build();
    }


    /**
     * 推送IOS端
     *
     * @param alias        别名集合 如果为空 则推送全部
     * @param alert        IOS推送内容体
     * @param title        IOS推送标题
     * @param subTitle     IOS推送副标题  需IOS10+
     * @param isProduction 是否推送生产环境
     * @param extras       业务扩展数据
     * @return
     */
    public static PushPayload buildPushIosPayload(List<String> alias, String alert, String title, String subTitle, Boolean isProduction, Map<String, String> extras) {
        JsonObject alertJson = getAlertJson(title, subTitle, alert);

        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience((alias == null || alias.isEmpty()) ? Audience.all() : Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(alertJson != null ? alertJson : alert)
                                .addExtras(extras)
                                .build())
                        .build())
                .setOptions(Options.newBuilder().setApnsProduction(isProduction).build())
                .build();
    }



    private static JsonObject getAlertJson(String title, String subTitle, String alert){
        JsonObject alertJson = null;
        if (!StringUtils.isEmpty(title) || !StringUtils.isEmpty(subTitle)) {
            alertJson = new JsonObject();
            alertJson.addProperty("title", title);
            alertJson.addProperty("subtitle", subTitle);
            alertJson.addProperty("body", alert);
        }
        return alertJson;
    }

}
