package com.metafour.multitenancy.cache.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * EmployeePOJO JPA entity, enhanced using lombok.
 * 
 * @author Imtiaz Rahi
 * @since 2017-08-25
 * @see EmployeeRepository
 */
@Data @NoArgsConstructor @Accessors(chain = true) @RequiredArgsConstructor
public class EmployeePOJO {

	Long id;

	@NonNull
	String firstName, lastName;
	String email, phone;
	String department, office;

	int version;
}
