package com.metafour.multitenancy.demo.scheduling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.metafour.multitenancy.TenantContextHolder;
import com.metafour.multitenancy.config.MultitenancyProperties;
import com.metafour.multitenancy.demo.scheduling.bean.Employee;
import com.metafour.multitenancy.demo.scheduling.bean.EmployeeRepository;

@Component
public class ScheduledTasks {
	private static final DateTimeFormatter FormatDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	private static final DateTimeFormatter FormatTime = DateTimeFormatter.ISO_LOCAL_TIME;

	@Value("${tenants.active.name}")
	private String[] activeTenants;

	@Autowired private EmployeeRepository repo;
	@Autowired private MailSender mailer;
	@Autowired private MultitenancyProperties props;

	//@Scheduled(fixedRate = 5000)
	public void reportCurrentTime() {
		System.out.println(LocalTime.now().format(FormatTime));
	}

	//@Scheduled(fixedRate = 120000)
	public void sendEmailRegularly() {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("imtiaz.rahi@gmail.com");
		msg.setTo("imtiaz.rahi+development@metafour.com");
		msg.setSubject("Testing SendGrid Email sending Java API " + LocalDateTime.now().format(FormatDateTime));
		msg.setText("Email send using SendGrid email API.\n Cheers // Imtiaz Rahi");
		mailer.send(msg);
	}

	@Scheduled(fixedRate = 10000)
	public void tenantTasks() {
		reportCurrentTime();
		Arrays.asList(activeTenants).stream().parallel().forEach((String tenant) -> doTenantTask(tenant));
	}

	public void doTenantTask(String tenant) {
		System.out.println("Tenant id: " + tenant);
		TenantContextHolder.setCurrentTenant(tenant);
		sendEmailToTenant(tenant);
	}

	public void sendEmailToTenant(String tenant) {
		Thread t = Thread.currentThread();
		System.out.println("[" + tenant + "] Thread id: " + t.getId() + "; name: " + t.getName());
		String data = "";
		/* Read employee data from tenant DB */
		for (Employee it: repo.findAll())
			data += it.toString() + "\n\n";

		System.out.println(tenant + ": sending email");
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("imtiaz.rahi@gmail.com"); // TODO change sender, read tenant props
		msg.setTo("imtiaz.rahi+development@metafour.com");
		msg.setSubject("[" + tenant + "] Testing SendGrid Email sending Java API " + LocalDateTime.now().format(FormatDateTime));
		String mailbody = "Email send using SendGrid email API by " + tenant + "."
							+ "\n\n Sender: " + props.getTenants().get(tenant).getSenderEmail()
							+ "\n\n" + data
							+ "\n Cheers // Imtiaz Rahi";
		msg.setText(mailbody);
		mailer.send(msg);
	}
}
