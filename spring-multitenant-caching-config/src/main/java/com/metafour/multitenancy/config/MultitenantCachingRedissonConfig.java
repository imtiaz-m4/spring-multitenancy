package com.metafour.multitenancy.config;

import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

/**
 * Configure {@link RedissonSpringCacheManager} with {@link RedissonClient} to provide multitenant cache support.
 * 
 * @author Imtiaz Rahi
 * @since 2018-07-27
 */
@Configuration
@ComponentScan("com.metafour.multitenancy.config")
@EnableCaching @Slf4j
public class MultitenantCachingRedissonConfig {

	@Lazy @Bean(destroyMethod = "shutdown")
	public RedissonClient redisson(org.redisson.config.Config redissonConfig) {
		if (log.isTraceEnabled()) log.trace("Redisson client bean ready for injection");
		return org.redisson.Redisson.create(redissonConfig);
	}

	@Lazy @Bean("redissonConfig") @Profile("!test")
	protected org.redisson.config.Config redissonConfigLive(@Value("${spring.redis.host:redis}") String host, @Value("${spring.redis.port:6379}") String port) {
		if (log.isTraceEnabled()) log.trace("Configuring redisson client with LIVE/PROD profile; port " + port);
		org.redisson.config.Config config = new org.redisson.config.Config();
		/* This URL style works only when application runs in docker */
		config.useSingleServer().setAddress(host + ":" + port);
		return config.setCodec(redissonCodecs());
	}

	@Lazy @Bean("redissonConfig") @Profile("test")
	protected org.redisson.config.Config redissonConfigTest(@Value("${spring.redis.host:redis}") String host, @Value("${spring.redis.port:6379}") String port) {
		if (log.isTraceEnabled()) log.trace("Configuring redisson client with DEV/TEST profile; port " + port);
		org.redisson.config.Config config = new org.redisson.config.Config();
		/* This URL style works only in Spring tests */
		config.useSingleServer().setAddress("redis://" + host + ":" + port);
		return config.setCodec(redissonCodecs());
	}

	private Codec redissonCodecs() {
		ObjectMapper mapper = new ObjectMapper().findAndRegisterModules().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return new JsonJacksonCodec(mapper);
	}

	@Lazy @Bean
	public MultitenantCacheManager cacheManager(RedissonClient redissonClient) {
		if (log.isTraceEnabled()) log.trace("Multitenant cache manager configuered with RedissonSpringCacheManager delegate");
		return new MultitenantCacheManager(new RedissonSpringCacheManager(redissonClient, "classpath:redis-cache-config.yml"), true);
	}
}
