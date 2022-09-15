package com.hyf.feature.hateoas.assembler;

import com.hyf.feature.hateoas.controller.HelloController;
import com.hyf.feature.hateoas.model.Employee;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * 封装实体到代表模型的映射处理
 *
 *
 * Spring HATEOAS所有模型的抽象基类是RepresentationModel。
 * 但为简单起见，建议使用 EntityModel<T>作为机制轻松地将所有POJO包装为模型。
 *
 * @author baB_hyf
 * @date 2020/10/24
 */
@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

	/**
	 * 此处改变了返回值
	 */
	@Override
	public EntityModel<Employee> toModel(Employee entity) {

		// return EntityModel.of(
		// 	entity,
		// 	linkTo(methodOn(HelloController.class).get(entity.getId())).withSelfRel(),
		// 	linkTo(methodOn(HelloController.class).list()).withRel("employees"));

		EntityModel<Employee> entityModel = EntityModel.of(
			entity,
			linkTo(methodOn(HelloController.class).get(entity.getId())).withSelfRel(),
			linkTo(methodOn(HelloController.class).list()).withRel("employees"));

		if (Employee.Status.ING == entity.getStatus()) {
			entityModel
				.add(linkTo(methodOn(HelloController.class).promotion(entity.getId())).withRel("promotion"))
				.add(linkTo(methodOn(HelloController.class).leave(entity.getId())).withRel("leave"));
		}

		return entityModel;
	}
}
