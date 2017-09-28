package com.metafour.multitenancy;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.core.io.ClassPathResource;

import com.metafour.multitenancy.impl.TenantScopeImpl;

/**
 * 
 * @author Imtiaz Rahi
 * @since 2017-09-25
 * @see org.springframework.web.context.request.RequestScopeTests
 * @see org.springframework.web.context.request.RequestScopedProxyTests
 */
public class TenantScopeXmlBeanDefTest {
	private final DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

	@Before
	public void setUp() throws Exception {
		this.beanFactory.registerScope(TenantContextHolder.SCOPE_TENANT, TenantScopeImpl.newInstance());
		this.beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver());
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this.beanFactory);
		reader.loadBeanDefinitions(new ClassPathResource("tenantScopeTests.xml", getClass()));
		this.beanFactory.preInstantiateSingletons();
	}

	@After
	public void tearDown() throws Exception {
		TenantContextHolder.clearCurrentTenant();
	}

	@Test
	public void getFromScope() {
		// for (String it: this.beanFactory.getBeanDefinitionNames()) System.out.println(it);
		final String tenant = "tenant_100";
		/* Setup tenant context */
		TenantContextHolder.setCurrentTenant(tenant);

		final String beanId = "tenantScopedObject";
		TestBean bean = (TestBean) this.beanFactory.getBean(beanId);
		assertEquals(tenant, bean.getName());
		assertSame(bean, this.beanFactory.getBean(beanId));
	}
}
