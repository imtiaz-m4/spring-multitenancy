package com.metafour.multitenancy.cache;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.metafour.multitenancy.TenantContextHolder;
import com.metafour.multitenancy.cache.bean.EmployeePOJO;
import com.metafour.multitenancy.cache.bean.SimpleService;
import com.metafour.multitenancy.config.MultitenantCachingRedissonConfig;

/**
 * @author Imtiaz Rahi
 * @since 2018-07-27
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { MultitenantCachingRedissonConfig.class, MultitenantCachingConfigExtraSetup.class })
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
public class TestMultitenantCaching {
	@Autowired private ApplicationContext appctx;
	@Autowired private SimpleService service;

	@Test
	public void testGetEmployeeWithCaching() {
		final String ID = "johndoe";

		switchTenant("tenantXXX");
		/* Get once (not cached) */
		assertNotNull(service.getEmployee(ID));
		/* Get second time (cached) */
		assertThat(service.getEmployee(ID).getDepartment(), is("Operation"));

		service.addEmployee(new EmployeePOJO("Default", "Tenant").setDepartment("XXX1"));
		service.addEmployee(new EmployeePOJO("Default2", "Tenant2").setDepartment("XXX2"));
		assertNotNull(service.getEmployee("defaulttenant"));
		assertThat(service.getEmployee("defaulttenant").getDepartment(), is("XXX1"));

		switchTenant("tenantYYY");
		service.addEmployee(new EmployeePOJO("DefaultYYY", "TenantYYY"));
		/* Get once (not cached) */
		assertNotNull(service.getEmployee(ID));
		assertNotNull(service.getEmployee("defaultyyytenantyyy"));

		switchTenant("tenantXXX");
		/* Get once (not cached) */
		assertThat(service.getEmployee("default2tenant2").getDepartment(), is("XXX2"));
		assertNull(service.getEmployee("defaultyyytenantyyy"));

		switchTenant("tenantYYY");
		System.out.println("In scope: " + service.getTenant());
		/* Get second time (cached) */
		assertNotNull(service.getEmployee("defaultyyytenantyyy"));
		assertThat(service.getEmployee(ID).getDepartment(), is("Operation"));

	}

	private void switchTenant(String tenantId) {
		TenantContextHolder.setCurrentTenant(tenantId);
		System.out.println("============================");
		System.out.println("In scope: " + service.getTenant());
	}

	@Test
	public void testContextBeans() {
		//for (String it: appctx.getBeanDefinitionNames()) System.out.println(it);
		assertTrue(appctx.containsBean("tenantScope"));
		assertTrue(appctx.containsBean("embededRedis"));
		assertTrue(appctx.containsBean("cacheManager"));
		//assertThat(appctx.getBean("cacheManager").getClass().getSimpleName(), is("MultitenantCacheManager"));
		//System.out.println(appctx.getBean("cacheManager").getClass().getName());
	}
}
