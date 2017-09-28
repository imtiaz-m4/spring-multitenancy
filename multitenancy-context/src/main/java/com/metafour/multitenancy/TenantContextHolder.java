/**
 * Copyright (c) 2017 Imtiaz Rahi.
 * 
 * This software may be modified and distributed under the terms of the MIT license.  See the LICENSE file for details.
 */
package com.metafour.multitenancy;

import java.util.Objects;

/**
 * Context holder for each tenant in application context. <br>
 * 
 * @author Imtiaz Rahi
 * @since 2017-08-28
 * @see org.springframework.web.context.request.RequestContextHolder
 */
public class TenantContextHolder {
	/** Scope identifier for tenant scope: "tenant". */
	public static final String SCOPE_TENANT = "tenant";

	/** Id of default tenant */
	public static final String DEFAULT_TENANT = "default";

	/** Thread bound tenant context holder */
	private static ThreadLocal<Object> currentTenant = ThreadLocal.withInitial(() -> DEFAULT_TENANT);

	/**
	 * Set tenant context with tenant id.
	 * 
	 * @param tenant
	 */
	public static void setCurrentTenant(Object tenant) {
		Objects.requireNonNull(tenant, "Tenant id can not be null");
		currentTenant.set(tenant);
	}

	/**
	 * Returns current tenant context.
	 * 
	 * @return Tenant context
	 */
	public static Object getCurrentTenant() {
		return currentTenant.get();
	}

	/**
	 * Returns current tenant identifier.
	 * 
	 * @return Tenant id
	 */
	public String getTenant() {
		return currentTenant.get().toString();
	}

	/**
	 * Returns current tenant context.
	 * 
	 * @return Tenant context
	 */
	public Object getScopeContext() {
		return currentTenant.get();
	}

	/** Removes the tenant context */
	public static void clearCurrentTenant() {
		currentTenant.set(null);
	}

}
