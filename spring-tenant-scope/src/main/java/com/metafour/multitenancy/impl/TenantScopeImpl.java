package com.metafour.multitenancy.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import com.metafour.multitenancy.TenantContextHolder;

/**
 * Tenant-backed {@link Scope} implementation.
 * 
 * @author Imtiaz Rahi
 * @since 2017-09-15
 */
public class TenantScopeImpl implements Scope {

	private final TenantContextHolder contextHolder = new TenantContextHolder();
	/** Map of Objects stored against each tenant identifier */
	private final Map<String, Map<String, Object>> tenantScopeContextDataHolder = new ConcurrentHashMap<>();

	private TenantScopeImpl() {
	}

	/**
	 * Returns a new instance.
	 * 
	 * @return New {@link TenantScopeImpl} instance
	 */
	public static TenantScopeImpl newInstance() {
		return new TenantScopeImpl();
	}

	/**
	 * Returns data in tenant scope context.
	 * 
	 * @return
	 */
	private Map<String, Object> getScopeContext() {
		return tenantScopeContextDataHolder.get(contextHolder.getTenant());
	}

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		@SuppressWarnings("unchecked")
		Map<String, Object> scope = (Map<String, Object>) resolveContextualObject(contextHolder.getTenant());
		return scope.computeIfAbsent(name, k -> objectFactory.getObject());
	}

//	@Override
//	public Object get(String name, ObjectFactory<?> objectFactory) {
//		String tenant = contextHolder.getTenant(); // TODO use static or instance of TenantContextHolder
//		Map<String, Object> scope = tenantScopeContextDataHolder.computeIfAbsent(tenant, k -> new ConcurrentHashMap<>());
//		return scope.computeIfAbsent(name, k -> objectFactory.getObject());
//	}

	@Override
	public Object remove(String name) {
		Map<String, Object> scope = getScopeContext();
		return scope == null ? null : scope.remove(name);
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
	}

	@Override
	public Object resolveContextualObject(String key) {
		return tenantScopeContextDataHolder.computeIfAbsent(key, k -> new ConcurrentHashMap<>());
	}

	@Override
	public String getConversationId() {
		return contextHolder.getTenant();
	}

}
