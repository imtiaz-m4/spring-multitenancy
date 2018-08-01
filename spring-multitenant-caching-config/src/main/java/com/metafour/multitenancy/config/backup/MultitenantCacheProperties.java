package com.metafour.multitenancy.config.backup;

import java.util.Map;

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
 * @since 2018-07-27
 */
//@Component
//@PropertySources({
//	@PropertySource(name = "mt-prp", value = "classpath:multitenant.properties", ignoreResourceNotFound = true),
//	@PropertySource(name = "mt-yml", value = "classpath:multitenant.yml", factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)
//})
//@ConfigurationProperties
@lombok.Data
public class MultitenantCacheProperties {

	Map<String, TenantCacheProperties> tenants;

}
