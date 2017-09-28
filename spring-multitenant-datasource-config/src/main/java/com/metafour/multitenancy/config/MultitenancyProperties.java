package com.metafour.multitenancy.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

import com.metafour.multitenancy.impl.TenantProperties;

/**
 * Bean to automatically fill-up configuration from {@code multitenant.properties} file.
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
 * @see DataSourceConfig
 */
@Component
@PropertySources({
	@PropertySource(name = "mt-prp", value = "classpath:multitenant.properties", ignoreResourceNotFound = true),
	@PropertySource(name = "mt-yml", value = "classpath:multitenant.yml", factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)
})
@ConfigurationProperties
@lombok.Data
public class MultitenancyProperties {

	Map<String, TenantProperties> tenants;

}
