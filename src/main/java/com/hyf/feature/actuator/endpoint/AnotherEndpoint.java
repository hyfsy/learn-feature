package com.hyf.feature.actuator.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.security.Principal;

/**
 * @author baB_hyf
 * @date 2020/10/23
 */
@Component
@WebEndpoint(id = "another")
public class AnotherEndpoint {

	@ReadOperation
	public Resource getResource() {
		return new ClassPathResource("banner.txt");
	}

	@WriteOperation
	public void setPrincipal(@Nullable Principal principal/* 未经身份认证不会注入该对象 */) {
		System.out.println(principal);
		System.out.println();
	}
}
