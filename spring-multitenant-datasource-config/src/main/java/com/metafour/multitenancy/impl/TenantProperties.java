package com.metafour.multitenancy.impl;

import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;

//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import lombok.Data;

/**
 * Configuration bean to hold each tenant configuration.
 * 
 * @author Imtiaz Rahi
 * @since 2017-08-28
 */
@Data
@ConfigurationProperties
public class TenantProperties {
	/** Tenant name (optional) */
	private String name;
	/** Mark tenant configuration as active or not */
	private Boolean active;
	private String senderEmail;
	private TenantDataSource datasource;

	public boolean isActive() {
		return active == null ? true : active;
	}

	@Data
	public static class TenantDataSource {
		/** Name of the datasource */
		private String name;

		/** JDBC URL of the database; e.g. <code>jdbc:postgresql://localhost:5432/tenant_2</code> */
		private String url;

		/** Login user of the database */
		private String username;

		/** Login password of the database */
		private String password;

		/** Fully qualified name of the JDBC driver, Auto-detected based on the URL by default */
		private String driverClassName;

		public String getName() {
			if (name == null || name.isEmpty()) name = UUID.randomUUID().toString();
			return name;
		}
	}

}
