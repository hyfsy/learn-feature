package com.hyf.feature.hateoas.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

/**
 * 继承代表模型
 *
 * 可继承自EntityModel能更方便的进行映射处理
 *
 * @author baB_hyf
 * @date 2020/10/24
 */
public class Employee extends RepresentationModel<Employee> {

	private Integer id;
	private String name;
	private Integer age;
	private String describe;
	private Status status = Status.ING;

	/**
	 * JsonCreator：指示Jackson如何创建此POJO的实例
	 * JsonProperty：标记Jackson应将此构造函数参数放入的字段
	 */
	@JsonCreator
	public Employee(@JsonProperty("id") Integer id,
					@JsonProperty("name") String name,
					@JsonProperty("age") Integer age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public enum Status {
		ING, PROMOTION, LEAVE
	}
}
