package com.metafour.multitenancy.scan;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.metafour.multitenancy.TenantContextHolder;
import com.metafour.multitenancy.TestBean;

@Component
@Scope(scopeName = TenantContextHolder.SCOPE_TENANT, proxyMode = ScopedProxyMode.INTERFACES)
public class TestBeanTenantScopeAnnotated extends TestBean {

	@PostConstruct
	public void setTenantName() {
		this.name = TenantContextHolder.getCurrentTenant().toString();
	}
}
