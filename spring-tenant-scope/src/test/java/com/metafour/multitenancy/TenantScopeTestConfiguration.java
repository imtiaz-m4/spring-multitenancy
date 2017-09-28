package com.metafour.multitenancy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
@ComponentScan("com.metafour.multitenancy.scan")
public class TenantScopeTestConfiguration {

	@Bean
	@TenantScope
	public TestBean tenantScopedObject() {
		return new TestBean(TenantContextHolder.getCurrentTenant().toString());
	}

	@Bean
	@TenantScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
	public TestBean tenantScopeCglibProxy() {
		return new TestBean("scoped-" + TenantContextHolder.getCurrentTenant().toString());
	}

	@Bean
	@TenantScope(proxyMode = ScopedProxyMode.INTERFACES)
	public TestBean tenantScopeDynamicProxy() {
		return new TestBean("scopedproxy-" + TenantContextHolder.getCurrentTenant().toString());
	}

	@Bean
	@TenantScope
	//@TenantScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
	public TestBean requestScopedObjectCircle1() {
		TestBean ob = new TestBean("scopedproxy-" + TenantContextHolder.getCurrentTenant().toString());
		ob.setSpouse(requestScopedObjectCircle2());
		return ob;
	}

	@Bean
	@TenantScope
	//@TenantScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
	public TestBean requestScopedObjectCircle2() {
		TestBean ob = new TestBean("scopedproxy-" + TenantContextHolder.getCurrentTenant().toString());
		ob.setSpouse(requestScopedObjectCircle1());
		return ob;
	}

	@Bean
	public TestBean outerBean() {
		TestBean ob = new TestBean();
		ob.setSpouse(scopedInnerBean());
		return ob;
	}

	@Bean
	@TenantScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
	public TestBean scopedInnerBean() {
		return new TestBean("scopedproxy-" + TenantContextHolder.getCurrentTenant().toString());
	}
}
