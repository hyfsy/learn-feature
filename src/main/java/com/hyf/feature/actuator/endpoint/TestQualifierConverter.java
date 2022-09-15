package com.hyf.feature.actuator.endpoint;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 通过依赖描述符查找参数指定的注解
 *
 * @author baB_hyf
 * @date 2020/10/29
 * {@link org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration}
 * {@link org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver#isAutowireCandidate}
 */
// @Component
@Qualifier
public class TestQualifierConverter implements Converter<String, Integer> {
	@Override
	public Integer convert(String source) {
		return 10;
	}
}
