package com.hyf.feature.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 使用Redis Session之后，原Spring Boot的 server.session.timeout属性不再生效
 *
 * @author baB_hyf
 * @date 2020/12/06
 */
// @Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 30) // 30秒过期
public class SessionConfiguration {
}
