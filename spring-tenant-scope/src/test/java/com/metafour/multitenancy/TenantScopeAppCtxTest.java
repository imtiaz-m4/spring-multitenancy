package com.metafour.multitenancy;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.metafour.multitenancy.config.TenantScopeConfig;
import com.metafour.multitenancy.scan.Address;

/**
 * 
 * @author Imtiaz Rahi
 * @since 2017-09-25
 * @see org.springframework.web.context.request.RequestScopeTests
 * @see org.springframework.web.context.request.RequestScopedProxyTests
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TenantScopeConfig.class, TenantScopeTestConfiguration.class })
public class TenantScopeAppCtxTest {
	@Autowired ApplicationContext applicationContext;

	@After
	public void tearDown() throws Exception {
		TenantContextHolder.clearCurrentTenant();
	}

	@Test
	public void getFromScope() {
		//for (String it : applicationContext.getBeanDefinitionNames()) System.out.println(it);
		final String tenant = "tenant_100";
		/* Setup tenant context */
		TenantContextHolder.setCurrentTenant(tenant);
		
		final String beanId = "tenantScopedObject";
		TestBean bean = (TestBean) applicationContext.getBean(beanId);
		assertEquals(tenant, bean.getName());
		assertSame(bean, applicationContext.getBean(beanId));
	}

	@Test
	public void switchTenantGetFromScope() {
		final String tenant = "tenant_100", tenant2 = "tenant_200";
		final String beanId = "tenantScopedObject";

		/* Setup tenant context */
		TenantContextHolder.setCurrentTenant(tenant);
		TestBean bean = (TestBean) applicationContext.getBean(beanId);
		assertEquals(tenant, bean.getName());

		/* Setup new tenant context */
		TenantContextHolder.setCurrentTenant(tenant2);
		TestBean bean2 = (TestBean) applicationContext.getBean(beanId);
		assertEquals(tenant2, bean2.getName());
}

	@Test
	public void getAnnotatedBean() {
		final String tenant = "tenant_100";
		/* Setup tenant context */
		TenantContextHolder.setCurrentTenant(tenant);
		
		final String beanId = "address";
		Address bean = (Address) applicationContext.getBean(beanId);
		assertEquals(tenant, bean.getName());
		assertSame(bean, applicationContext.getBean(beanId));
	}
}
