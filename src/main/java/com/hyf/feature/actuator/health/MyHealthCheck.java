package com.hyf.feature.actuator.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * @author baB_hyf
 * @date 2020/10/22
 */
@Component
public class MyHealthCheck extends AbstractHealthIndicator {

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {

		builder
			.withDetail("test error", "the error is test")
			.withDetail("test exception", new NullPointerException("test"))

			.withDetail("error", "the error is coverage") // key error
			.withException(new NullPointerException("test2")) // key error coverage
			.outOfService() // coverage with DOWN
			.down(new ArrayIndexOutOfBoundsException("test i is out of bound"));
	}
}
