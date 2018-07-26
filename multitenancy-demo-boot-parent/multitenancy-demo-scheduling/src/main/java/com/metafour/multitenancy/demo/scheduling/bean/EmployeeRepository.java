package com.metafour.multitenancy.demo.scheduling.bean;

import org.springframework.data.repository.CrudRepository;

/**
 * Employee repository for CRUD operations.
 * 
 * @author Imtiaz Rahi
 * @since 2017-08-25
 * @see Employee
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
