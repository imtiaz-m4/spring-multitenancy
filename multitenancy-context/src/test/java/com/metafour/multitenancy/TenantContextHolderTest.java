package com.metafour.multitenancy;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

public class TenantContextHolderTest extends TenantContextHolder {
	private static final String TENANT_ID = "tenant_100";
	private static final String DEFAULT_ID = "default";
	
	private TenantContextHolder holder = new TenantContextHolder();

	@After
	public void tearDown() throws Exception {
		TenantContextHolder.clearCurrentTenant();
	}

	@Test
	public void testSetCurrentTenant() {
		TenantContextHolder.setCurrentTenant(TENANT_ID);
		assertNotEquals(holder.getTenant(), DEFAULT_ID);
		assertSame(holder.getScopeContext(), TenantContextHolder.getCurrentTenant());
	}

	@Test
	public void testGetCurrentTenant() {
		assertNull(TenantContextHolder.getCurrentTenant());
		TenantContextHolder.setCurrentTenant(DEFAULT_ID);
		assertEquals(DEFAULT_ID, TenantContextHolder.getCurrentTenant());
	}

	@Test
	public void testGetTenant() {
		TenantContextHolder.setCurrentTenant(TENANT_ID);
		assertEquals(holder.getTenant(), TENANT_ID);
	}

	@Test
	public void testGetScopeContext() {
		assertNull(holder.getScopeContext());
		TenantContextHolder.setCurrentTenant(TENANT_ID);
		assertTrue(holder.getScopeContext() instanceof Object);
		assertSame(String.class, holder.getScopeContext().getClass());
	}

	@Test
	public void testClearCurrentTenant() {
		TenantContextHolder.clearCurrentTenant();
		assertNull(TenantContextHolder.getCurrentTenant());
	}

}
