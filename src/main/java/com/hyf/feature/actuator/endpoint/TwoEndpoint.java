package com.hyf.feature.actuator.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

/**
 * 通过MBeanServer获取不同的{@link org.springframework.boot.actuate.endpoint.jmx.EndpointMBean}对象，
 * 执行invoke方法，最后和 @WebEndpoint统一执行逻辑参考：
 * {@link org.springframework.boot.actuate.endpoint.invoke.reflect.ReflectiveOperationInvoker}
 *
 * @author baB_hyf
 * @date 2020/10/29
 */
@Component
@Endpoint(id = "two")
public class TwoEndpoint {

	@ReadOperation
	public String t1() {
		return "test1";
	}
}
