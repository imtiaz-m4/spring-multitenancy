package com.metafour.multitenancy;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.metafour.multitenancy.config.DataSourceConfig;
import com.metafour.multitenancy.config.MultitenancyProperties;

/**
 * TODO test case does not work. need to fix.
 * 
 * @author Imtiaz Rahi
 * @since 2017-09-26
 */
//@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { DataSourceConfig.class })
public class TestMultitenantPropertiesSetup {
	@Autowired private MultitenancyProperties props;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test() {
		System.out.println(props);

//		for (Entry<String, TenantProperties> it : props.getTenants().entrySet()) {
//			System.out.println("Tenant : " + it.getKey());
//			System.out.println("   " + it.getValue());
//			TenantDataSource dbprop = it.getValue().getDatasource();
//			System.out.println("   " + dbprop.getName());
//			System.out.println("   " + dbprop.getUsername());
//		}
	}

}
