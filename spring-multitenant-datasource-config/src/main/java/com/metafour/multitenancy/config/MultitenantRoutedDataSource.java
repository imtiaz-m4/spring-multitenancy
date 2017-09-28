package com.metafour.multitenancy.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.metafour.multitenancy.TenantContextHolder;

/**
 * Database routing, switching based on tenant id received in HTTP header and stored in tenant context.
 * 
 * @author Imtiaz Rahi
 * @since 2017-08-28
 * @see TenantContextHolder
 */
public class MultitenantRoutedDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return TenantContextHolder.getCurrentTenant();
	}

}
