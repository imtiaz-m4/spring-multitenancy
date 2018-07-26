package com.metafour.multitenancy.demo.webapp.bean;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

/**
 * Employee repository for CRUD operations.
 * 
 * @author Imtiaz Rahi
 * @since 2017-08-25
 * @see Employee
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	@Cacheable("cachedFirstName")
	List<Employee> findByFirstName(String firstname);
}
