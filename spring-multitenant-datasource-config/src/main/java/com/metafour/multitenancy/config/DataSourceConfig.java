package com.metafour.multitenancy.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.metafour.multitenancy.impl.TenantProperties;
import com.metafour.multitenancy.impl.TenantProperties.TenantDataSource;

/**
 * Inject multiple JDBC data sources as Spring beans into IOC. <br>
 * Database configurations are taken from {@code multitenant.properties} file and structured using
 * {@link MultitenancyProperties}.
 * 
 * <pre>
 * <code>
 * spring.multitenancy.datasource1.url = jdbc:postgresql://localhost:5432/tenant_1
 * spring.multitenancy.datasource1.username = username
 * spring.multitenancy.datasource1.password = password
 * spring.multitenancy.datasource1.driver-class-name = org.postgresql.Driver
 * </code>
 * </pre>
 * 
 * @author Imtiaz Rahi
 * @since 2017-08-25
 * @see MultitenancyProperties
 * @see TenantProperties
 */
@Configuration
@ComponentScan("com.metafour.multitenancy.config")
public class DataSourceConfig {
	private static final String DEFAULT_TENANT_KEY = "default";
	@Autowired private MultitenancyProperties props;

	@Bean
	public DataSource dataSource() {
		MultitenantRoutedDataSource mrds = new MultitenantRoutedDataSource();

		Map<Object, Object> targetDS = new HashMap<>();
		for (Entry<String, TenantProperties> ent : props.tenants.entrySet()) {
			TenantProperties ob = ent.getValue();
			if (!ob.isActive() || ob.getDatasource() == null) continue;
			targetDS.put(ent.getKey(), buildDS(ob.getDatasource()));
		}

		mrds.setTargetDataSources(targetDS);
		mrds.setDefaultTargetDataSource(buildDS(props.getTenants().get(DEFAULT_TENANT_KEY).getDatasource()));
		mrds.afterPropertiesSet();
		return mrds;
	}

	private DataSource buildDS(TenantDataSource dsprop) {
		DataSourceBuilder factory = DataSourceBuilder.create()
				.driverClassName(dsprop.getDriverClassName())
				.username(dsprop.getUsername())
				.password(dsprop.getPassword())
				.url(dsprop.getUrl());
		return factory.build();
	}
}
