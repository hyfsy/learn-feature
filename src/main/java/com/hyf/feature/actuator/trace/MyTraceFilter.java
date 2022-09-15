package com.hyf.feature.actuator.trace;

import org.springframework.boot.actuate.trace.http.HttpExchangeTracer;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.web.trace.servlet.HttpTraceFilter;

/**
 * @author baB_hyf
 * @date 2020/10/25
 */
public class MyTraceFilter extends HttpTraceFilter {
	/**
	 * Create a new {@link HttpTraceFilter} instance.
	 *
	 * @param repository the trace repository
	 * @param tracer     used to trace exchanges
	 */
	public MyTraceFilter(HttpTraceRepository repository, HttpExchangeTracer tracer) {
		super(repository, tracer);
	}
}
