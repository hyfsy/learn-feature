package com.hyf.feature.actuator.trace;// package com.hyf.fsfts.toy.actuator.trace;
//
// import org.springframework.boot.actuate.autoconfigure.trace.http.HttpTraceProperties;
// import org.springframework.boot.actuate.trace.http.HttpExchangeTracer;
// import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
// import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
// import org.springframework.boot.actuate.web.trace.servlet.HttpTraceFilter;
// import org.springframework.boot.context.properties.EnableConfigurationProperties;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// /**
//  * 此处的http跟踪建议仅用于开发环境，生产环境可考虑zipkin
//  *
//  * @author baB_hyf
//  * @date 2020/10/25
//  */
// @Configuration
// @EnableConfigurationProperties(HttpTraceProperties.class)
// public class TraceConfiguration {
//
// 	/**
// 	 * 触发
// 	 */
// 	@Bean
// 	public HttpTraceRepository httpTraceRepository() {
// 		return new InMemoryHttpTraceRepository();
// 	}
//
// 	/**
// 	 * 可定制
// 	 */
// 	@Bean
// 	public HttpExchangeTracer httpExchangeTracer(HttpTraceProperties httpTraceProperties) {
// 		return new MyHttpExchangeTracer(httpTraceProperties.getInclude());
// 	}
//
// 	@Bean
// 	public HttpTraceFilter httpTraceFilter(HttpTraceRepository httpTraceRepository, HttpExchangeTracer httpExchangeTracer) {
// 		return new MyTraceFilter(httpTraceRepository, httpExchangeTracer);
// 	}
// }
