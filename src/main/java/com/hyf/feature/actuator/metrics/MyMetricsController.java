package com.hyf.feature.actuator.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 除了使用 metrics 端点默认的这些统计指标外，我们还可以实现自定义统计指标
 * Metrics 提供 4 种基本的度量类型：Gauge、Counter、Timer、Summary
 *
 * @author baB_hyf
 * @date 2020/10/23
 */
// @RestControllerEndpoint(id = "my-metrics")
@RestController
@RequestMapping("metrics")
public class MyMetricsController {

	// tag size must be even, it is a set of key=value pairs

	private static final Counter             counter = Metrics.counter("test.counter", "tag1", "tag2...");
	private static final Timer               timer   = Metrics.timer("test.timer", "tag1...", "tag2");
	private static final DistributionSummary summary = Metrics.summary("test.summary", "t1", "t2");

	/**
	 * （计量器）是最简单的度量类型，只有一个简单的返回值，他用来记录一些对象或者事物的瞬时值
	 */
	@GetMapping("gauge")
	public void gauge() {
		Metrics.gauge("test.gauge", .55555, (d) -> d / .11111);
	}

	/**
	 * （计数器）简单理解就是一种只增不减的计数器。它通常用于记录服务的请求数量、完成的任务数量、错误的发生数量等等
	 */
	@GetMapping("counter")
	public void counter() {
		counter.increment();
	}

	/**
	 * （计时器）可以同时测量一个特定的代码逻辑块的调用（执行）速度和它的时间分布。简
	 * 单来说，就是在调用结束的时间点记录整个调用块执行的总时间，适用于测量短时间执行的事件的耗时分布，
	 * 例如消息队列消息的消费速率
	 */
	@GetMapping("timer")
	public void timer() {
		// 执行方法并记录执行时间
		timer.record(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * （摘要）用于跟踪事件的分布。它类似于一个计时器，但更一般的情况是，它的大小并不一定是一段时间的测量值。
	 * 在 micrometer 中，对应的类是 DistributionSummary，它的用法有点像 Timer，
	 * 但是记录的值是需要直接指定，而不是通过测量一个任务的执行时间
	 */
	@GetMapping("summary")
	public void summary() {
		summary.record(2);
		summary.record(3d);
		summary.record(5D);
	}
}
