package com.metafour.multitenancy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.metafour.multitenancy.impl.MultiTenancyInterceptor;

/**
 * Configure Spring web MVC and register HTTP request interceptor to extract tenant id from HTTP URL path.
 * 
 * @author Imtiaz Rahi
 * @since 2017-08-25
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MultiTenancyInterceptor());
	}
}
