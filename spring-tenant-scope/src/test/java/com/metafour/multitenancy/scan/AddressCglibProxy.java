package com.metafour.multitenancy.scan;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.metafour.multitenancy.TenantContextHolder;
import com.metafour.multitenancy.TenantScope;

/**
 * Sample of class-based (CGLIB) proxy injection into tenant scope.
 * 
 * @author Imtiaz Rahi
 * @since 2017-09-25
 */
@Component
@TenantScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AddressCglibProxy extends Address {

	@Override
	@PostConstruct
	public void setTenantName() {
		this.name = "scoped-" + TenantContextHolder.getCurrentTenant().toString();
	}

}
