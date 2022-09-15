package com.hyf.feature.hateoas.controller;

import com.hyf.feature.hateoas.assembler.EmployeeModelAssembler;
import com.hyf.feature.hateoas.model.Employee;
import com.hyf.feature.hateoas.model.Employee.Status;
import com.hyf.feature.hateoas.service.EmployeeService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author baB_hyf
 * @date 2020/10/24
 */
@RestController
@RequestMapping("/hateoas")
public class HelloController {

	public static final String TEMPLATE = "Hello %s, You id is %s, age is %s";

	private final EmployeeService        employeeService;
	private final EmployeeModelAssembler assembler;

	public HelloController(EmployeeService employeeService, EmployeeModelAssembler assembler) {
		this.employeeService = employeeService;
		this.assembler = assembler;
	}

	@RequestMapping("/hello")
	public HttpEntity<Employee> hello(
		@RequestParam(value = "name", defaultValue = "zhangsan") String name) {
		Employee employee = new Employee(1, name, 20);
		employee.setDescribe(String.format(TEMPLATE, employee.getName(), employee.getId(), employee.getAge()));

		/*
		 * Spring HATEOAS尊重各种X-FORWARDED-标头。
		 * 如果将Spring HATEOAS服务放在代理后面，并使用X-FORWARDED-HOST标头正确配置它，则将正确格式化结果链接。
		 */


		/*
		 * curl美化输出：
		 * curl -v localhost:8080/hello | json_pp
		 */


		/*
		 * methodOn：让假的控制器上的方法调用
		 * 返回的LinkBuilder将检查控制器方法的映射注释，以准确建立该方法映射到的URI
		 * 调用withSelfRel()创建Link并添加到Employee表示模型的实例
		 */
		employee.add(
			linkTo(
				methodOn(HelloController.class).hello(name)
			).withSelfRel()
		);

		return new ResponseEntity<>(employee, HttpStatus.OK);
	}


	@GetMapping("/employees/{id}")
	public EntityModel<Employee> get(@PathVariable("id") Integer id) {
		Employee employee = employeeService.get(id);

		// return EntityModel.of(
		// 	employee,
		// 	linkTo(methodOn(HelloController.class).employee(id)).withSelfRel(),
		// 	linkTo(methodOn(HelloController.class).employees()).withRel("employees"));

		return assembler.toModel(employee);
	}

	@GetMapping("/employees")
	public CollectionModel<EntityModel<Employee>> list() {
		// List<EntityModel<Employee>> employeeModelList = employeeService.list().stream().map(employee -> EntityModel.of(employee,
		// 	linkTo(methodOn(HelloController.class).employee(employee.getId())).withSelfRel(),
		// 	linkTo(methodOn(HelloController.class).employees()).withRel("employees"))).collect(Collectors.toList());

		List<EntityModel<Employee>> employeeModelList = employeeService.list().stream()
			.map(assembler::toModel).collect(Collectors.toList());

		return CollectionModel.of(employeeModelList, linkTo(methodOn(HelloController.class).list()).withSelfRel());
	}

	@PostMapping("/employees")
	public ResponseEntity<EntityModel<Employee>> add(@RequestBody Employee employee) {
		employeeService.add(employee);

		// 结果对象使用assembler包裹
		EntityModel<Employee> entityModel = assembler.toModel(employee);

		// 创建HTTP 201已创建状态消息。这种类型的响应通常包括一个Location响应标头，使用从模型的自相关链接派生的URI
		URI uri = employee.getRequiredLink(IanaLinkRelations.SELF).toUri();
		return ResponseEntity.created(uri).body(entityModel);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody Employee newEmployee) {
		Employee employee = employeeService.get(id);
		employee.setName(newEmployee.getName());
		employee.setAge(newEmployee.getAge());
		employee.setDescribe(newEmployee.getDescribe());
		employeeService.update(employee);

		// 这是有争议的，因为我们不一定要“创建”新资源。但是它预装了Location响应标头
		return ResponseEntity
			.created(employee.getRequiredLink(IanaLinkRelations.SELF).toUri())
			.body(assembler.toModel(employee));
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
		employeeService.delete(id);
		return ResponseEntity.noContent().build();
	}


	/*
	 * 将链接构建到REST API中
	 * 到目前为止，已经建立了具有裸露骨骼链接的可演化API。
	 * 为了增加API并更好地为客户服务，就需要接受Hypermedia作为应用程序状态引擎的概念
	 *
	 * 输入HATEOAS或Hypermedia作为应用程序状态引擎。
	 * 与其让客户端解析有效负载，不如为客户端提供链接以发出有效动作信号。
	 * 将基于状态的操作与数据的有效负载分离。
	 *
	 * 换句话说，当CANCEL和COMPLETE是有效动作时，将它们动态添加到链接列表中。
	 * 链接存在时，客户端仅需要向用户显示相应的按钮。
	 */

	@PutMapping("/employees/{id}/promotion")
	public ResponseEntity<?> promotion(@PathVariable("id") Integer id) {
		Employee employee = employeeService.get(id);
		if (employee.getStatus() == Status.ING) {
			employee.setStatus(Status.PROMOTION);
			employeeService.update(employee);
			return ResponseEntity.ok(assembler.toModel(employee));
		}

		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
			.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
			.body(Problem.create()
			.withTitle("Method not allowed")
			.withDetail("You cannot promotion, because current state is " + employee.getStatus()));
	}

	@DeleteMapping("/employees/{id}/leave")
	public ResponseEntity<?> leave(@PathVariable("id") Integer id) {
		Employee employee = employeeService.get(id);
		if (employee.getStatus() == Status.ING) {
			employee.setStatus(Status.LEAVE);
			employeeService.update(employee);
			return ResponseEntity.ok(assembler.toModel(employee));
		}


		try {
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(Problem.create()
					.withTitle("Method not allowed")
					.withDetail("You cannot promotion, because current state is " + employee.getStatus())
					.withProperties(employee)
					.withStatus(HttpStatus.METHOD_NOT_ALLOWED)
					.withType(new URL("http://www.baidu.com").toURI())
					.withInstance(new URL("http://www.fengsong.com").toURI())
				);
		} catch (URISyntaxException | MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
