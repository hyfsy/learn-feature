package com.hyf.feature.schedule;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 第一种任务调度
 *
 * @author baB_hyf
 * @date 2020/12/06
 */
public class TimerTaskTest {

	public static void main(String[] args) {
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				System.out.println("timer tasker schedule...");
			}
		};

		Timer timer = new Timer();
		System.out.println("start");
		timer.schedule(timerTask, 1000, 3000); // 延迟一秒后，每三秒执行一次，延迟一秒后会立即执行一次
		// timer.cancel(); // 停止
	}
}
