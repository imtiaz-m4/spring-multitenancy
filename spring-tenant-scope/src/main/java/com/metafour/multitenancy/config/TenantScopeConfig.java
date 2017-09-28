package com.metafour.multitenancy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.metafour.multitenancy.TenantContextHolder;
import com.metafour.multitenancy.TenantScope;
import com.metafour.multitenancy.impl.TenantScopeImpl;

/**
 * Configure {@code @TenantScope} annotation for usage.
 * 
 * @author Imtiaz Rahi
 * @since 2017-09-15
 * @see TenantScope
 * @see <a href="https://javaeeinsights.wordpress.com/2015/07/21/register-spring-custom-scope-using-java-config">Register Spring custom scope using Java Config</a>
 */
@Configuration
public class TenantScopeConfig {

	/** Spring will eventually look for a bean factory. DO NOT REMOVE this line */
	@SuppressWarnings("unused")
	@Autowired
	private ConfigurableBeanFactory configurableBeanFactory;

	/**
	 * Register {@link TenantScope} into Spring IOC.
	 * 
	 * @return {@link CustomScopeConfigurer} instance for injection
	 */
	@Bean
	public static CustomScopeConfigurer tenantScope() {
		CustomScopeConfigurer scopeConfigurer = new CustomScopeConfigurer();
		scopeConfigurer.addScope(TenantContextHolder.SCOPE_TENANT, TenantScopeImpl.newInstance());
		return scopeConfigurer;
	}
}
