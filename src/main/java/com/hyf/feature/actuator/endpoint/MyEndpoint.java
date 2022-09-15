package com.hyf.feature.actuator.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

/**
 * @author baB_hyf
 * @date 2020/10/20
 */
@Component
@Endpoint(id = "my")
public class MyEndpoint {

	@ReadOperation // get
	public String getName() {
		return "getName";
	}

	@WriteOperation // post
	public void setName(@Nullable Date date) {
		System.out.println(date);
	}

	@DeleteOperation // delete
	public void testDelete(@Selector/* Nullable无效 */ String path, @Selector(match = Selector.Match.ALL_REMAINING) String[] path2) {
		System.out.println(path);
		System.out.println(Arrays.toString(path2));
		System.out.println("delete");
	}

	// 不能存在两个相同的Operation注解
	public void testDelete2() {
		System.out.println("delete");
	}
}
