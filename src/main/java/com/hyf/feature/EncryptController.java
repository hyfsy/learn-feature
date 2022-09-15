package com.hyf.feature;

import com.hyf.feature.util.StringUtil;
import com.hyf.feature.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * URL有效时间（timestamp）、URL请求唯一（nonce）、参数签名防止篡改（sign）
 * <p>
 * 开放平台：AccessKey + SecretKey
 * APP：AppKey + token（appKey相当于SecretKey，token相当于AccessKey，行为类似）
 * <p>
 * API Token(接口令牌): 用于访问不需要用户登录的接口，如登录、注册、一些基本数据的获取等。获取接口令牌需要拿appId、timestamp和sign来换，sign=加密(timestamp+key)
 * USER Token(用户令牌): 用于访问需要用户登录之后的接口，如：获取我的基本信息、保存、修改、删除等操作。获取用户令牌需要拿用户名和密码来换
 * <p>
 * 所有的安全措施都用上的话有时候难免太过复杂，在实际项目中需要根据自身情况作出裁剪
 *
 * @author baB_hyf
 * @date 2020/09/12
 */
@RestController
public class EncryptController {


    /**
     * 请求超时时间，分钟（防止重放攻击）
     */
    private static final int               REQUEST_MAX_AGE = 15;
    private static final Map<String, Long> nonceMap        = new ConcurrentHashMap<>();
    private static final Logger            log             = LoggerFactory.getLogger(EncryptController.class);

    @RequestMapping("access")
    public boolean validAccess(HttpServletRequest request) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, -REQUEST_MAX_AGE);
        long expiredTimestamp = instance.getTimeInMillis();

        if (StringUtil.isBlank(request.getParameter("timestamp"))
                || StringUtil.isBlank(request.getParameter("AccessKey"))
                || StringUtil.isBlank(request.getParameter("nonce"))
                || StringUtil.isBlank(request.getParameter("sign"))) {
            log.warn("请求参数缺失！");
            return false;
        }

        long timestamp = Long.parseLong(request.getParameter("timestamp"));
        // 过期处理
        if (timestamp < expiredTimestamp) {
            log.warn("请求已过期！");
            return false;
        }

        String accessKey = request.getParameter("AccessKey");
        if (StringUtil.isBlank(accessKey)) {
            log.warn("请求未携带AccessKey！");
            return false;
        }

        String uid = getUid(accessKey);
        if (StringUtil.isBlank(uid)) {
            log.warn("请求携带了非法的AccessKey！");
            return false;
        }

        // 防止篡改请求
        boolean access = TokenUtil.validAccess(request);
        if (!access) {
            log.warn("请求被篡改！");
            return false;
        }

        boolean nonce = validReplayAttack(request.getParameter("nonce"), timestamp, expiredTimestamp);
        if (!nonce) {
            log.warn("预防重放攻击！");
            return false;
        }

        log.info("请求成功！");
        return true;
    }

    @RequestMapping("accessApp")
    public void validAccessApp(HttpServletRequest request) {

    }

    @RequestMapping("token")
    public void validToken(String p) {

    }


    /**
     * 通过accessKey获取用户信息
     */
    public String getUid(String AccessKey) {
        return AccessKey;
    }

    /**
     * 验证是否为重放攻击
     *
     * @param nonce            随机数
     * @param timestamp        时间戳
     * @param expiredTimestamp 过期的时间戳
     */
    public boolean validReplayAttack(String nonce, Long timestamp, long expiredTimestamp) {
        Long oldTimestamp = nonceMap.put(nonce, timestamp);
        boolean success = oldTimestamp == null;

        // 清除超时的，减小存储压力
        nonceMap.forEach((k, v) -> {
            if (v < expiredTimestamp) { // 15m前的删除
                nonceMap.remove(k);
            }
        });

        return success;
    }

    public static class Wrapper {
        public static class TokenInfo {
            /** token类型: api:0 user:1 */
            private Integer  tokenType;
            private AppInfo  appInfo;
            private UserInfo userInfo;
        }

        public static class AppInfo {
            private String appKey;
            private String appSecret;
        }

        public static class UserInfo {
            private String      username;
            private String      mobile;
            private String      password;
            private String      salt;
            private AccessToken accessToken;
        }

        public static class AccessToken {
            private String token;
            private Date   expireTime;
        }
    }
}
