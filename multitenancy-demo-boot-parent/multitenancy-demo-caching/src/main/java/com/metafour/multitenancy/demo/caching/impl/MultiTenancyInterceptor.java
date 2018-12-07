package com.metafour.multitenancy.demo.caching.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.metafour.multitenancy.TenantContextHolder;
import com.metafour.multitenancy.demo.caching.config.WebMvcConfig;

/**
 * HTTP request interceptor to extract tenant id from HTTP URL path. <br>
 * Added to Spring {@link InterceptorRegistry interceptor registry} using {@link WebMvcConfigurer}.
 * 
 * @author Imtiaz Rahi
 * @since 2017-08-25
 * @see WebMvcConfig
 */
public class MultiTenancyInterceptor extends HandlerInterceptorAdapter {
	/** Key to identify {@code Tenant id} from HTTP URL */
	public static final String TENANT_ID_KEY = "tenantid";

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		Map<String, Object> vars = (Map<String, Object>) req.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		if (vars.containsKey(TENANT_ID_KEY)) {
			TenantContextHolder.setCurrentTenant(vars.get(TENANT_ID_KEY));
		}
		return true;
	}
}
