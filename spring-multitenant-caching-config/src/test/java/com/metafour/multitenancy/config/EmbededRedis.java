package com.metafour.multitenancy.config;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Profile;

import lombok.extern.slf4j.Slf4j;
import redis.embedded.RedisServer;

@TestComponent @Profile({ "dev", "test" }) @Slf4j
public class EmbededRedis {

	@Value("${spring.redis.port:9981}")
	private int port;

	private RedisServer server;

	@PostConstruct
	public void startRedis() throws IOException {
		log.info("Starting embeded redis server at port {}", port);
		System.out.println("Starting embeded redis server at port " + port);
		server = new RedisServer(port);
		server.start();
	}

	@PreDestroy
	public void stopRedis() {
		server.stop();
	}
}
