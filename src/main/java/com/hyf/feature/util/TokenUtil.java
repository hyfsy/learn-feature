package com.hyf.feature.util;


import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author baB_hyf
 * @date 2020/09/10
 */
public class TokenUtil {

	/**
	 * AccessKey + SecretKey 校验
	 */
	public static boolean validAccess(HttpServletRequest request) {

		Map<String, String[]> paramsMap = new TreeMap<>(request.getParameterMap());

		StringBuilder sortedQuery = new StringBuilder();
		paramsMap.forEach((k, v) -> {
			if (!"sign".equals(k)) {
				if (sortedQuery.length() != 0) {
					sortedQuery.append('&');
				}
				sortedQuery.append(k).append('=').append(StringUtil.join(v, ","));
			}
		});

		// TODO 获取SecretKey
		String SecretKey = "SecretKey";

		sortedQuery.append("&SecretKey=").append(SecretKey);
		String sign = MD5(sortedQuery.toString()).toUpperCase();
		String encrypt = request.getParameter("sign");

		return sign.equals(encrypt);
	}

	public static String MD5(String secretKey) {
		return DigestUtils.md5DigestAsHex(secretKey.getBytes());
	}
}
