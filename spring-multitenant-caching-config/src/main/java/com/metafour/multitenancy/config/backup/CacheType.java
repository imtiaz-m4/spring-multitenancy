package com.metafour.multitenancy.config.backup;

/**
 * Supported cache types (defined in order of precedence).
 * 
 * @author Imtiaz Rahi
 * @since 2017-10-05
 * @see org.springframework.boot.autoconfigure.cache.CacheType
 */
public enum CacheType {

	/** Generic caching using 'Cache' beans from the context */
	GENERIC,

	/** JCache (JSR-107) backed caching */
	JCACHE,

	/** EhCache backed caching */
	EHCACHE,

	/** Hazelcast backed caching */
	HAZELCAST,

	/** Infinispan backed caching */
	INFINISPAN,

	/** Couchbase backed caching */
	COUCHBASE,

	/** Redis backed caching */
	REDIS,

	/** Caffeine backed caching */
	CAFFEINE,

	/** Guava backed caching */
	GUAVA,

	/** Simple in-memory caching */
	SIMPLE,

	/** No caching */
	NONE;
}
