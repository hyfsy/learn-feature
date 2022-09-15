package com.hyf.feature.cache;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;

/**
 * @author baB_hyf
 * @date 2020/12/06
 */
// @Configuration
@EnableCaching
public class CacheConfiguration extends CachingConfigurerSupport {

	@Override
	public KeyGenerator keyGenerator() {
		return (target, method, params) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(target.getClass().getName());
			sb.append('.').append(method.getName());
			for (Object param : params) {
				sb.append('.').append(param);
			}

			return sb.toString();
		};
	}
}
