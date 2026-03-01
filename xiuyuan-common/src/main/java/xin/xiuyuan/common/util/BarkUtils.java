package xin.xiuyuan.common.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * Bark消息通知辅助类
 *
 * @author xinbaojian
 * @create 2026-03-01 21:06
 **/
@Slf4j
public class BarkUtils {

    /**
     * 连接超时时间(毫秒)
     */
    private static final int CONNECT_TIMEOUT = 10000;

    public static void main(String[] args) {
        String baseUrl = "https://bark.xiuyuan.xin/ivQqRhBtfDH7Upywu7nau9";
//        String body = "测试 消息";
//        String title = "这是标题";
//        String result = send(baseUrl, title, body);
//        System.out.println(result);

        BarkPostBody body = new BarkPostBody()
                .setTitle("这是标题")
                .setSubtitle("这是副标题")
                .setBody("这是消息内容")
                .setLevel(BarkLevel.CRITICAL.getLevel())
                .setId("1b4db7eb-4057-5ddf-91e0-36dec72071f5")
                .setSound(BarkSound.ALARM.getSound())
//                .setDelete("1")
                ;
        String result = sendPost(baseUrl, body);
        log.info("sendPost result: {}", result);
    }

    /**
     * 发送消息
     *
     * @param baseUrl baseUrl
     * @param body    消息内容
     * @return String
     */
    public static String send(String baseUrl, String body) {
        if (StrUtil.isBlank(baseUrl) || StrUtil.isBlank(body)) return null;
        String url = StrUtil.format("{}/{}", baseUrl, body);
        return HttpUtil.get(url, CONNECT_TIMEOUT);
    }

    /**
     * 发送消息
     *
     * @param baseUrl baseUrl
     * @param title   标题
     * @param body    消息内容
     */
    public static String send(String baseUrl, String title, String body) {
        if (StrUtil.isBlank(baseUrl) || StrUtil.isBlank(body)) return null;
        String url = StrUtil.format("{}/{}/{}", baseUrl, title, body);
        return HttpUtil.get(url, CONNECT_TIMEOUT);
    }

    /**
     * 发送消息
     *
     * @param baseUrl  baseUrl
     * @param title    标题
     * @param subtitle 副标题
     * @param body     消息内容
     */
    public static String send(String baseUrl, String title, String subtitle, String body) {
        if (StrUtil.isBlank(baseUrl) || StrUtil.isBlank(body)) return null;
        String url = StrUtil.format("{}/{}/{}/{}", baseUrl, title, subtitle, body);
        return HttpUtil.get(url, CONNECT_TIMEOUT);
    }

    /**
     * 发送消息
     *
     * @param baseUrl    baseUrl
     * @param paramsBody 消息内容
     */
    public static String sendPost(String baseUrl, BarkPostBody paramsBody) {
        if (StrUtil.isBlank(baseUrl) || paramsBody == null) return null;
        String json = JSONUtil.toJsonStr(paramsBody);
        log.info("sendPost json: {}", json);
        return HttpUtil.post(baseUrl, json);
    }

    @Getter
    @AllArgsConstructor
    public enum BarkLevel {

        /**
         * 默认值，系统会立即亮屏显示通知
         */
        ACTIVE("active"),
        /**
         * 重要警告, 在静音模式下也会响铃
         */
        CRITICAL("critical"),
        /**
         * 时效性通知，可在专注状态下显示通知。
         */
        TIME_SENSITIVE("timeSensitive"),

        /**
         * 仅将通知添加到通知列表，不会亮屏提醒。
         */
        PASSIVE("passive");
        private final String level;
    }

    @Getter
    @AllArgsConstructor
    public enum BarkSound {
        ALARM("alarm"),
        ANTICIPATE("anticipate"),
        BELL("bell"),
        BIRDSONG("birdsong"),
        BLOOM("bloom"),
        CALYPSO("calypso"),
        CHIME("chime"),
        CHOO("choo"),
        DESCENT("descent"),
        ELECTRONIC("electronic"),
        FANFARE("fanfare"),
        GLASS("glass"),
        GOTOSLEEP("gotosleep"),
        HEALTHNOTIFICATION("healthnotification"),
        HORN("horn"),
        LADDER("ladder"),
        MAILSENT("mailsent"),
        MINUET("minuet"),
        MULTIWAYINVITATION("multiwayinvitation"),
        NEWMAIL("newmail"),
        NEWSFLASH("newsflash"),
        NOIR("noir"),
        PAYMENTSUCCESS("paymentsuccess"),
        SHAKE("shake"),
        SHERWOODFOREST("sherwoodforest"),
        SILENCE("silence"),
        SPELL("spell"),
        SUSPENSE("suspense"),
        TELEGRAPH("telegraph"),
        TIPPOES("tiptoes"),
        TYPEDRIVERS("typewriters"),
        UPDATE("update"),
        ;
        private final String sound;
    }

    @Data
    @Accessors(chain = true)
    public static class BarkPostBody {

        /**
         * 标题
         */
        private String title;

        /**
         * 副标题
         */
        private String subtitle;

        /**
         * 消息内容
         */
        private String body;

        /**
         * 推送内容，支持基础markdown格式，传递此参数将忽略body 字段，注意处理特殊字符。
         */
        private String markdown;

        /**
         * 推送中断级别
         */
        private String level = BarkLevel.ACTIVE.getLevel();

        /**
         * 重要警告的通知音量，取值范围：0-10，默认值为5
         */
        private Integer volume;

        /**
         * 推送角标，可以是任意数字
         */
        private Integer badge;

        /**
         * 传"1"时，通知铃声重复播放
         */
        private String call;

        /**
         * 传"1"时， iOS14.5以下自动复制推送内容，iOS14.5以上需手动长按推送或下拉推送
         */
        private String autoCopy;

        /**
         * 复制推送时，指定复制的内容，不传此参数将复制整个推送内容。
         */
        private String copy;

        /**
         * 可以为推送设置不同的铃声
         */
        private String sound;

        /**
         * 为推送设置自定义图标，设置的图标将替换默认Bark图标。
         * 图标会自动缓存在本机，相同的图标 URL 仅下载一次。
         */
        private String icon;

        /**
         * 推送图片
         */
        private String image;

        /**
         * 对消息进行分组，推送将按group分组显示在通知中心中。
         * 也可在历史消息列表中选择查看不同的群组。
         */
        private String group;

        /**
         * 传 1 保存推送，传其他的不保存推送，不传按APP内设置来决定是否保存。
         */
        private String isArchive;

        /**
         * 点击推送时，跳转的URL ，支持URL Scheme 和 Universal Link
         */
        private String url;

        /**
         * 传 "none" 时，点击推送不会弹窗
         */
        private String action;

        /**
         * 使用相同的ID值时，将更新对应推送的通知内容
         * 需 Bark v1.5.2, bark-server v2.2.5 以上，Json传参需使用字符串类型
         */
        private String id;

        /**
         * 传 "1" 时，将从系统通知中心和APP内历史记录中删除通知，需搭配 id 参数使用
         * 需在设置里开启”后台App刷新“，否则无效。
         */
        private String delete;

    }
}
