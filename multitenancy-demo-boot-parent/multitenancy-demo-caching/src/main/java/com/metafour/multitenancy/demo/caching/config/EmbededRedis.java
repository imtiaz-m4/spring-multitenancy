package com.metafour.multitenancy.demo.caching.config;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import redis.embedded.RedisServer;

@Component
@Profile({ "dev", "test" })
public class EmbededRedis {

	@Value("${spring.redis.port:6379}")
	private int port;

	private RedisServer server;

	@PostConstruct
	public void startRedis() throws IOException {
		server = new RedisServer(port);
		server.start();
	}

	@PreDestroy
	public void stopRedis() {
		server.stop();
	}
}
