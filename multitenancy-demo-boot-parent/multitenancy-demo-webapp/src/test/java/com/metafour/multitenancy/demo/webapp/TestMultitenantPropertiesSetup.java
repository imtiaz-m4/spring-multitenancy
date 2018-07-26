package com.metafour.multitenancy.demo.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.metafour.multitenancy.config.MultitenancyProperties;
import com.metafour.multitenancy.demo.webapp.MutitenantDemoAppImtiazApplication;
import com.metafour.multitenancy.impl.TenantProperties;
import com.metafour.multitenancy.impl.TenantProperties.TenantDataSource;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { MutitenantDemoAppImtiazApplication.class })
public class TestMultitenantPropertiesSetup {

	@Autowired
	MultitenancyProperties props;

	@Test
	public void test() {
		for (Entry<String, TenantProperties> it : props.getTenants().entrySet()) {
			System.out.println("Tenant : " + it.getKey());
			System.out.println("   " + it.getValue());
			TenantDataSource dbprop = it.getValue().getDatasource();
			System.out.println("   " + dbprop.getName());
			System.out.println("   " + dbprop.getUsername());
		}
	}

	@Test
	public void testLambda() {
		List<String> names = Arrays.asList("Angela", "Aaron", "Bob", "Claire", "David");
		 
		List<String> namesWithA = names.stream()
		  .filter(name -> name.startsWith("A"))
		  .collect(Collectors.toList());
		System.out.println(namesWithA);
	}
}
