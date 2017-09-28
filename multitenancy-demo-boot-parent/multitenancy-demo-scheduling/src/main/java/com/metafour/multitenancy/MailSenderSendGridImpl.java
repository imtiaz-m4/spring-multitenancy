package com.metafour.multitenancy;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.sendgrid.*;

/**
 * Spring {@link MailSender} implementation using SendGrid (v4.1.0) API.
 * 
 * @author Imtiaz Rahi
 * @since 2017-09-14
 */
@Service
public class MailSenderSendGridImpl implements MailSender {
	@Autowired SendGrid mailService;

	@Override
	public void send(SimpleMailMessage msg) throws MailException {
		Content content = new Content("text/plain", msg.getText());
		Mail sgmail = new Mail(new Email(msg.getFrom()), msg.getSubject(), new Email(msg.getTo()[0]), content);
		
		try {
			Request rq = new Request();
			rq.setMethod(Method.POST);
			rq.setEndpoint("mail/send");
			rq.setBody(sgmail.build());
			Response rs = mailService.api(rq);
			SendGridStatus status = SendGridStatus.parse(rs.getStatusCode());
			System.out.println("Status: " + status.code());
			System.out.println("Desc: " + status.description());
			System.out.println(rs.getBody());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void send(SimpleMailMessage... simpleMessages) throws MailException {
		for (SimpleMailMessage it : simpleMessages)
			send(it);
	}

}
