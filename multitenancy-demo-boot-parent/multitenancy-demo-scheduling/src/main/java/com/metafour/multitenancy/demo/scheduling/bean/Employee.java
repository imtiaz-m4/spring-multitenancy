package com.metafour.multitenancy.demo.scheduling.bean;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Employee JPA entity, enhanced using lombok.
 * 
 * @author Imtiaz Rahi
 * @since 2017-08-25
 * @see EmployeeRepository
 */
@Entity
@Data @NoArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@NonNull
	String firstName, lastName;
	String email, phone;
	String department, office;

	@Version
	int version;
}
