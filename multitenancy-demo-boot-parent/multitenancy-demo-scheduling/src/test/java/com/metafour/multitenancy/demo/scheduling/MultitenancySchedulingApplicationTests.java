package com.metafour.multitenancy.demo.scheduling;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({ SpringExtension.class })
@SpringBootTest
//Request returned status Code 401Body:{"errors":[{"message":"Maximum credits exceeded","field":null,"help":null}]}
public class MultitenancySchedulingApplicationTests {

	@Test
	public void contextLoads() {
	}

}
