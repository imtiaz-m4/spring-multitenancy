package com.metafour.multitenancy.config;

import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.metafour.multitenancy.TenantContextHolder;
import com.metafour.multitenancy.TenantLookupFailureException;

import lombok.extern.slf4j.Slf4j;

/**
 * A {@link CacheManager} implementation that supports multi-tenancy by wrapping around another {@link CacheManager}
 * implementation, the delegate, and decorating caches returned by the delegate with {@link MultitenantCache}.
 * 
 * @author Imtiaz Rahi (imtiaz.rahi@metafour.com)
 * @author Joe Laudadio (Joe.Laudadio@AltegraHealth.com)
 * @since 2018-07-30
 */
@Slf4j
public class MultitenantCacheManager implements CacheManager {
	/** Actual {@link CacheManager} implementation to which calls will be delegated */
	private final CacheManager delegate;
	/** Defines whether or not calls to {@link #getCache(String)} should fail if there is no multi-tenant context defined */
	private final boolean contextRequired;

	/**
	 * Creates a new {@link MultitenantCacheManager} that wraps the given delegate. The contextRequired parameter
	 * defines whether or not calls to {@link #getCache(String)} should fail if there is no multi-tenant context defined.
	 * 
	 * @param delegate {@link CacheManager} implementation to which calls will be delegated
	 * @param contextRequired Defines whether or not calls to {@link #getCache(String)} should fail
	 */
	public MultitenantCacheManager(final CacheManager delegate, final boolean contextRequired) {
		if (delegate == null) throw new NullPointerException("Cache manager delegate may not be null");
		this.delegate = delegate;
		this.contextRequired = contextRequired;
		if (log.isTraceEnabled()) log.trace("(cache-manager) configuring for tenant: {}", getTenantContext());
	}

	/**
	 * Convenience constructor equivalent to {@code MultitenantCacheManager(CacheManager, false)}.
	 * 
	 * @param delegate {@link CacheManager} implementation to which calls will be delegated
	 */
	public MultitenantCacheManager(final CacheManager delegate) {
		this(delegate, false);
	}

	@Override
	public Cache getCache(String name) {
		Cache cache = this.delegate.getCache(name);
		if (log.isTraceEnabled()) log.trace("(cache-manager) get cache {} for tenant {}", name, getTenantContext());
		if (isContextRequired() && getTenantContext() == null) throw new TenantLookupFailureException("Tenant context required but not available");
		if (cache != null) cache = new MultitenantCache(cache, isContextRequired());
		return cache;
	}

	@Override
	public Collection<String> getCacheNames() {
		return this.delegate.getCacheNames();
	}

	public boolean isContextRequired() {
		return contextRequired;
	}

	public String getTenantContext() {
		String ctx = TenantContextHolder.getCurrentTenantId();
		if (log.isTraceEnabled()) log.trace("(cache-manager) found tenant: {}", ctx);
		return ctx != null && ctx.trim().isEmpty() ? null : ctx;
	}

	public String getDelegateName() {
		return this.delegate.getClass().getSimpleName();
	}

	@Override
	public String toString() {
		return String.format("Cache manager '%s' is used by tenant '%s'", getDelegateName(), getTenantContext());
	}
}
