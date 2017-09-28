package com.metafour.multitenancy.scan;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.metafour.multitenancy.TenantContextHolder;
import com.metafour.multitenancy.TenantScope;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Imtiaz Rahi
 * @since 2017-09-25
 */
@Component
@TenantScope
@Data @NoArgsConstructor
public class Address implements IAddress {

	String name;
	String line1, line2, city, country;
	String latitude, longitude;

	public Address(String name) {
		super();
		this.name = name;
	}

	@PostConstruct
	public void setTenantName() {
		this.name = TenantContextHolder.getCurrentTenant().toString();
	}
}
