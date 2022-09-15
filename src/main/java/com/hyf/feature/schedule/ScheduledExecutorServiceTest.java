package com.hyf.feature.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author baB_hyf
 * @date 2020/12/06
 */
public class ScheduledExecutorServiceTest {

	public static void main(String[] args) {
		ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		System.out.println("start");
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			System.out.println("ScheduledExecutorService schedule...");
		}, 1, 3, TimeUnit.SECONDS); // 延迟1秒后，每三秒执行一次
		// scheduledExecutorService.shutdown();
	}
}
