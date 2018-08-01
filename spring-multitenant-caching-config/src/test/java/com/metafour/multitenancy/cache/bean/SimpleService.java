package com.metafour.multitenancy.cache.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.cache.annotation.Cacheable;

import com.metafour.multitenancy.TenantContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleService {
	private Map<String, EmployeePOJO> employees;

	public SimpleService() {
		super();
	}

	public SimpleService(Map<String, EmployeePOJO> employees) {
		super();
		this.employees = employees;
	}

	/** Sample data set for cache */
	private Map<String, EmployeePOJO> employeeList() {
		log.info("Executing employeeList method for tenant :" + getTenant());
		Map<String, EmployeePOJO> list = new HashMap<>();
		list.put("johndoe", new EmployeePOJO().setFirstName("John").setLastName("Doe").setDepartment("Operation"));
		list.put("maryann", new EmployeePOJO().setFirstName("Mary").setLastName("Ann").setDepartment("Operation"));
		list.put("robert", new EmployeePOJO().setFirstName("Robert").setLastName("Hall").setDepartment("Sales"));
		list.put("olivia", new EmployeePOJO().setFirstName("Olivia").setLastName("Cook").setDepartment("Sales"));
		list.put("emmarose", new EmployeePOJO().setFirstName("Emma").setLastName("Rose").setDepartment("Sales"));
		return list;
	}

	@Cacheable(cacheNames = "cachedEmployeeList")
	public EmployeePOJO getEmployee(String id) {
		log.info("Executing getEmployee method for employee " + id);
		Objects.requireNonNull(id, "Employee id can not be null");
		return getEmployees().get(id);
	}

	public Map<String, EmployeePOJO> getEmployees() {
		if (employees == null) setEmployees(employeeList());
		return employees;
	}

	public void setEmployees(Map<String, EmployeePOJO> employees) {
		this.employees = employees;
	}

	public Map<String, EmployeePOJO> addEmployee(EmployeePOJO ob) {
		getEmployees().put(ob.getFirstName().toLowerCase() + ob.getLastName().toLowerCase(), ob);
		return this.employees;
	}

	public String getTenant() {
		return TenantContextHolder.getCurrentTenantId();
	}
}