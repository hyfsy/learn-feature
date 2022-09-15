package com.hyf.feature.actuator.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.EndpointConverter;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * @author baB_hyf
 * @date 2020/10/23
 */
// @Component
@EndpointConverter
public class MyDateConverter implements Converter<String, Date> {
	@Override
	public Date convert(String source) {
		System.out.println("source date: " + source);
		return new Date();
	}
}
