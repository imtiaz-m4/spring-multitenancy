package com.metafour.multitenancy.demo.scheduling;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 
 * @author Imtiaz Rahi
 * @since 2017-09-14
 */
@ExtendWith({ SpringExtension.class })
@ContextConfiguration(classes = { MultitenancySchedulingApplication.class })
@SpringBootTest
public class TestSendGridAPI {

	@Autowired MailSender mailService;
	
	@Test
	public void test() {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("imtiaz.rahi@gmail.com");
		msg.setTo("imtiaz.rahi+development@metafour.com");
		msg.setSubject("Testing SendGrid Email sending Java API");
		msg.setText("Email send using SendGrid email API.\n Cheers // Imtiaz Rahi");

		// mock it
		mailService.send(msg);
		fail("Not yet implemented");
		assertNull(null);
	}

}
