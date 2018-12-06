package com.metafour.multitenancy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.metafour.multitenancy.impl.TenantScopeImpl;
import com.metafour.multitenancy.scan.Address;
import com.metafour.multitenancy.scan.AddressCglibProxy;
import com.metafour.multitenancy.scan.AddressDynamicProxy;
import com.metafour.multitenancy.scan.IAddress;

/**
 * 
 * @author Imtiaz Rahi
 * @since 2017-09-25
 * @see org.springframework.web.context.request.RequestScopeTests
 * @see org.springframework.web.context.request.RequestScopedProxyTests
 */
@ExtendWith({ SpringExtension.class })
@ContextConfiguration(classes = { TenantScopeTestConfiguration.class })
public class TenantScopeBeanFactoryTest {
	@Autowired private DefaultListableBeanFactory beanFactory;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		this.beanFactory.registerScope(TenantContextHolder.SCOPE_TENANT, TenantScopeImpl.newInstance());
	}

	@AfterEach
	public void tearDown() throws Exception {
		TenantContextHolder.clearCurrentTenant();
	}

	@Test
	public void getFromScope() {
		// System.out.println(beanFactory.getClass().getName());
		final String tenant = "tenant_100";
		/* Setup tenant context */
		TenantContextHolder.setCurrentTenant(tenant);

		final String beanId = "tenantScopedObject";
		TestBean bean = (TestBean) beanFactory.getBean(beanId);
		assertEquals(tenant, bean.getName());
		assertSame(bean, beanFactory.getBean(beanId));
	}

	@Test
	public void getProxyFromScope() {
		final String beanId = "tenantScopeCglibProxy";
		assertTrue(AopUtils.isCglibProxy(beanFactory.getBean(beanId)));

		// for (String it : beanFactory.getBeanDefinitionNames()) System.out.println(it);

		final String tenant = "tenant_100";
		/* Setup tenant context */
		TenantContextHolder.setCurrentTenant(tenant);
		TestBean bean = (TestBean) beanFactory.getBean(beanId);

		// assertNull(beanFactory.getBean("scopedTarget." + beanId));
		assertEquals("scoped-" + tenant, bean.getName());
		assertNotNull(beanFactory.getBean("scopedTarget." + beanId));

		TestBean target = (TestBean) beanFactory.getBean("scopedTarget." + beanId);
		assertEquals(TestBean.class, target.getClass());
		assertEquals("scoped-" + tenant, target.getName());
		assertSame(bean, beanFactory.getBean(beanId));
		assertEquals(bean.toString(), target.toString());
	}

	@Test
	public void getFromScopeThroughDynamicProxy() {
		final String beanId = "tenantScopeDynamicProxy";
		assertTrue(AopUtils.isJdkDynamicProxy(beanFactory.getBean(beanId)));

		final String tenant = "tenant_100";
		/* Setup tenant context */
		TenantContextHolder.setCurrentTenant(tenant);
		ITestBean bean = (ITestBean) beanFactory.getBean(beanId);

		// assertNull(beanFactory.getBean("scopedTarget." + beanId));
		assertEquals("scopedproxy-" + tenant, bean.getName());
		assertNotNull(beanFactory.getBean("scopedTarget." + beanId));

		TestBean target = (TestBean) beanFactory.getBean("scopedTarget." + beanId);
		assertEquals(TestBean.class, target.getClass());
		assertEquals("scopedproxy-" + tenant, target.getName());
		assertSame(bean, beanFactory.getBean(beanId));
		assertEquals(bean.toString(), target.toString());
	}

	@Test
	public void testScopeAnnotationOnClass() {
		final String tenant = "tenant_100";
		/* Setup tenant context */
		TenantContextHolder.setCurrentTenant(tenant);

		final String beanId = "address";
		Address bean = (Address) beanFactory.getBean(beanId);
		assertEquals(tenant, bean.getName());
		assertSame(bean, beanFactory.getBean(beanId));
	}

	@Test
	public void testScopeAnnotationOnClassCglibProxy() {
		final String beanId = "addressCglibProxy";
		assertTrue(AopUtils.isCglibProxy(beanFactory.getBean(beanId)));

		// for (String it : beanFactory.getBeanDefinitionNames()) System.out.println(it);

		final String tenant = "tenant_100";
		/* Setup tenant context */
		TenantContextHolder.setCurrentTenant(tenant);
		Address bean = (Address) beanFactory.getBean(beanId);

		// assertNull(beanFactory.getBean("scopedTarget." + beanId));
		assertEquals("scoped-" + tenant, bean.getName());
		assertNotNull(beanFactory.getBean("scopedTarget." + beanId));

		Address target = (Address) beanFactory.getBean("scopedTarget." + beanId);
		assertEquals(AddressCglibProxy.class, target.getClass());
		assertEquals("scoped-" + tenant, target.getName());
		assertSame(bean, beanFactory.getBean(beanId));
		assertEquals(bean.toString(), target.toString());
	}

	@Test
	public void testScopeAnnotationOnClassThroughDynamicProxy() {
		final String beanId = "addressDynamicProxy";
		assertTrue(AopUtils.isJdkDynamicProxy(beanFactory.getBean(beanId)));

		final String tenant = "tenant_100";
		/* Setup tenant context */
		TenantContextHolder.setCurrentTenant(tenant);
		IAddress bean = (IAddress) beanFactory.getBean(beanId);

		// assertNull(beanFactory.getBean("scopedTarget." + beanId));
		assertEquals("scopedproxy-" + tenant, bean.getName());
		assertNotNull(beanFactory.getBean("scopedTarget." + beanId));

		Address target = (Address) beanFactory.getBean("scopedTarget." + beanId);
		assertEquals(AddressDynamicProxy.class, target.getClass());
		assertEquals("scopedproxy-" + tenant, target.getName());
		assertSame(bean, beanFactory.getBean(beanId));
		assertEquals(bean.toString(), target.toString());
	}

	@Test
	public void testGetInnerBeanFromScope() throws Exception {
		//for (String it : beanFactory.getBeanDefinitionNames()) System.out.println(it);
		TestBean bean = (TestBean) this.beanFactory.getBean("outerBean");
		assertFalse(AopUtils.isAopProxy(bean));
		assertTrue(AopUtils.isCglibProxy(bean.getSpouse()));

		final String name = "scopedInnerBean";
		final String tenant = "tenant_100";
		/* Setup tenant context */
		TenantContextHolder.setCurrentTenant(tenant);
		assertEquals("scopedproxy-" + tenant, bean.getSpouse().getName());
		assertNotNull(beanFactory.getBean("scopedTarget." + name));
		assertEquals(TestBean.class, beanFactory.getBean("scopedTarget." + name).getClass());
		assertEquals("scopedproxy-" + tenant, ((TestBean) beanFactory.getBean("scopedTarget." + name)).getName());
	}

}
