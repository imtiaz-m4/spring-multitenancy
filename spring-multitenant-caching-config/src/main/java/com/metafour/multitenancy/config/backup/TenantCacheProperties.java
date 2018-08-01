package com.metafour.multitenancy.config.backup;

import java.util.UUID;

import lombok.Data;

/**
 * Configuration bean to hold each tenant configuration.
 * 
 * @author Imtiaz Rahi
 * @since 2018-07-27
 */
//@ConfigurationProperties
@Data
public class TenantCacheProperties {
	/** Tenant name (optional) */
	private String name;
	/** Mark tenant configuration as active or not */
	private Boolean active;
	private TenantCacheConfig cacheConfig;

	public boolean isActive() {
		return active == null ? true : active;
	}

	@Data
	public static class TenantCacheConfig {
		/** Name */
		private String name;

		/** Mark tenant caching disabled or not */
		private Boolean disabled;

		/** Cache management provider; e.g. redis, hazelcast, ehcache, jcache provider etc. */
		private CacheType provider;

		/** location of the configuration file; e.g. "classpath:cache-config.yml", "classpath:cache-config.xml" */
		private String config;

		/** JDBC URL of the database; e.g. <code>jdbc:postgresql://localhost:5432/tenant_2</code> */
		private String url;

		/** Login user of the database */
		private String username;

		/** Login password of the database */
		private String password;

		public String getName() {
			if (name == null || name.isEmpty()) name = UUID.randomUUID().toString();
			return name;
		}
	}

}
