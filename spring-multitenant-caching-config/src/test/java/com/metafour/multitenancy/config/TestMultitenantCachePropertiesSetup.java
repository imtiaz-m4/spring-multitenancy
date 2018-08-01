package com.metafour.multitenancy.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { MultitenantCachingRedissonConfig.class })
@TestPropertySource(locations = "classpath:application-test.properties", properties = { "spring.redis.port=7826" })
@ActiveProfiles("test")
public class TestMultitenantCachePropertiesSetup {

	@Autowired
	private CacheManager manager;

	@Test
	public void contextLoads() {
		System.out.println(manager.getClass().getName());
	}

}
