package com.metafour.multitenancy.config;

import java.util.Objects;
import java.util.concurrent.Callable;

import org.springframework.cache.Cache;

import com.metafour.multitenancy.TenantContextHolder;
import com.metafour.multitenancy.TenantLookupFailureException;

import lombok.extern.slf4j.Slf4j;

/**
 * A {@link Cache} implementation that provides support for multi-tenancy by translating the lookup keys into a
 * tenant-context-specific key using {@link TenantContextHolder}. The nuts and bolts of the caching are the responsibility of
 * the delegate {@link Cache} implementation that this class wraps. This class only deals with translating the keys for
 * the tenant context.
 * 
 * Whether or not null values are allowed is determined by the underlying {@link Cache} implementation. The
 * {@link MultitenantCache} does not have a problem with null values itself.
 * 
 * Care should be taken if using {@link #getNativeCache()} because the keys contained in that cache instance will not
 * match the keys that were given (they will have been translated into something tenant-specific).
 *
 * @author Imtiaz Rahi (imtiaz.rahi@metafour.com)
 * @author Joe Laudadio (Joe.Laudadio@AltegraHealth.com)
 * @since 2018-07-30
 */
@Slf4j
public final class MultitenantCache implements Cache {
	/** Actual {@link Cache} implementation to which calls will be delegated */
	private final Cache delegate;
	/** Defines whether or not cache methods should fail if there is no multi-tenant context defined */
	private final boolean contextRequired;

	/**
	 * Creates a new {@link MultitenantCache} that wraps the given delegate. The contextRequired parameter defines
	 * whether or not cache methods should fail if there is no multi-tenant context defined.
	 * 
	 * @param delegate {@link Cache} implementation to which calls will be delegated
	 * @param contextRequired Defines whether or not cache methods should fail
	 */
	public MultitenantCache(final Cache delegate, final boolean contextRequired) {
		if (delegate == null) throw new NullPointerException("Cache delegate may not be null");
		this.delegate = delegate;
		this.contextRequired = contextRequired;
	}

	/**
	 * Convenience constructor for {@code MultitenantCache(Cache, false)}.
	 * 
	 * @param delegate {@link Cache} implementation to which calls will be delegated
	 */
	public MultitenantCache(final Cache delegate) {
		this(delegate, false);
	}

	@Override
	public String getName() {
		return this.delegate.getName();
	}

	@Override
	public Object getNativeCache() {
		return this.delegate.getNativeCache();
	}

	@Override
	public ValueWrapper get(Object key) {
		if (log.isTraceEnabled()) log.trace("(cache-delegator) get(Key) {}", key);
		return this.delegate.get(translateKey(key));
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		if (log.isTraceEnabled()) log.trace("(cache-delegator) get(Key, Type) key: {}, type: {}", key, type.getSimpleName());
		return this.delegate.get(translateKey(key), type);
	}

	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		if (log.isTraceEnabled()) log.trace("(cache-delegator) get(Key, ValueLoader) {}", key);
		return this.delegate.get(translateKey(key), valueLoader);
	}

	@Override
	public void put(Object key, Object value) {
		if (log.isTraceEnabled()) log.trace("(cache-delegator) put(Key, Value) {}", key);
		this.delegate.put(translateKey(key), value);
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		return delegate.putIfAbsent(translateKey(key), value);
	}

	@Override
	public void evict(Object key) {
		if (log.isTraceEnabled()) log.trace("(cache-delegator) evict(Key) {}", key);
		this.delegate.evict(translateKey(key));
	}

	@Override
	public void clear() {
		this.delegate.clear();
	}

	public boolean isContextRequired() {
		return this.contextRequired;
	}

	private TenantKey translateKey(Object key) {
		String ctx = getTenantContext();
		if (isContextRequired() && ctx == null) throw new TenantLookupFailureException("Tenant context is required but is not available");
		if (log.isTraceEnabled()) log.trace("(cache-delegator) translated key '{}' for tenant '{}'", key, ctx);
		return new TenantKey(ctx, key);
	}

	public String getTenantContext() {
		String ctx = TenantContextHolder.getCurrentTenantId();
		if (log.isTraceEnabled()) log.trace("(cache-delegator) found tenant: {}", ctx);
		return ctx != null && ctx.trim().isEmpty() ? null : ctx;
	}

	public String getDelegateName() {
		return this.delegate.getClass().getSimpleName();
	}

	@Override
	public String toString() {
		return String.format("Cache implementation '%s' is used by tenant '%s'", getDelegateName(), getTenantContext());
	}

	static class TenantKey {
		private final String tenantContext;
		private final Object key;

		public TenantKey(final String tenantContext, Object key) {
			this.tenantContext = tenantContext;
			this.key = key;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof TenantKey)) return false;
			TenantKey that = (TenantKey) o;
			return Objects.equals(this.tenantContext, that.tenantContext) && Objects.equals(this.key, that.key);
		}

		@Override
		public int hashCode() {
			return Objects.hash(this.tenantContext, this.key);
		}
	}

}
