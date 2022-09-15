package com.hyf.feature.hateoas.service;

import com.hyf.feature.hateoas.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author baB_hyf
 * @date 2020/10/24
 */
@Service
public class EmployeeService {

	private static final Map<Integer, Employee> employeeMap = new HashMap<>();

	static {
		employeeMap.put(1, new Employee(1, "zhangsan", 18));
		employeeMap.put(2, new Employee(2, "lisi", 19));
		employeeMap.put(3, new Employee(3, "wangwu", 20));
	}

	public Employee get(Integer id) {
		return employeeMap.get(id);
	}

	public Employee add(Employee employee) {
		return employeeMap.put(employee.getId(), employee);
	}

	public Employee update(Employee employee) {
		return employeeMap.put(employee.getId(), employee);
	}

	public Employee delete(Integer id) {
		return employeeMap.remove(id);
	}

	public List<Employee> list() {
		List<Employee> employeeList = new ArrayList<>();
		employeeMap.forEach((k, v) -> employeeList.add(v));
		return employeeList;
	}

}
