package com.metafour.multitenancy;

import java.lang.annotation.*;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.annotation.AliasFor;

/**
 * {@code @TenantScope} is a specialization of Spring {@link Scope @Scope} for a
 * component whose lifecycle is bound to the current tenant context.
 *
 * <p>Specifically, {@code @TenantScope} is a <em>composed annotation</em> that
 * acts as a shortcut for {@code @Scope("tenant")} with the default
 * {@link #proxyMode} set to {@link ScopedProxyMode#TARGET_CLASS TARGET_CLASS}.
 *
 * <p>{@code @TenantScope} may be used as a meta-annotation to create custom composed annotations.
 * 
 * @author Imtiaz Rahi
 * @since 2017-09-15
 * @see org.springframework.web.context.annotation.RequestScope
 * @see org.springframework.web.context.annotation.SessionScope
 * @see org.springframework.web.context.annotation.ApplicationScope
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Scope(TenantContextHolder.SCOPE_TENANT)
public @interface TenantScope {

	/**
	 * Alias for {@link Scope#proxyMode}.
	 * <p> Defaults to {@link ScopedProxyMode#TARGET_CLASS}.
	 */
	@AliasFor(annotation = Scope.class)
	ScopedProxyMode proxyMode() default ScopedProxyMode.TARGET_CLASS;

}
