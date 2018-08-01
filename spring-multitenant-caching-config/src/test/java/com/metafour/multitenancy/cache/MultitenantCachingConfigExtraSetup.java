package com.metafour.multitenancy.cache;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.metafour.multitenancy.TenantScope;
import com.metafour.multitenancy.cache.bean.SimpleService;

@TestConfiguration
public class MultitenantCachingConfigExtraSetup {

	@Bean @TenantScope
	public SimpleService simpleService() {
		return new SimpleService();
	}

}