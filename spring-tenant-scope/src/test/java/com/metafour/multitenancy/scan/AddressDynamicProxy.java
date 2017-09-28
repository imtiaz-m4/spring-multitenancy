package com.metafour.multitenancy.scan;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.metafour.multitenancy.TenantContextHolder;
import com.metafour.multitenancy.TenantScope;

/**
 * Sample of dynamic proxy injection into tenant scope.
 * 
 * @author Imtiaz Rahi
 * @since 2017-09-25
 */
@Component
@TenantScope(proxyMode = ScopedProxyMode.INTERFACES)
public class AddressDynamicProxy extends Address {

	@Override
	@PostConstruct
	public void setTenantName() {
		this.name = "scopedproxy-" + TenantContextHolder.getCurrentTenant().toString();
	}

}
