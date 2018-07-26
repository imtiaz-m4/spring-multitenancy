package com.metafour.multitenancy.demo.scheduling;

// https://sendgrid.com/docs/API_Reference/Web_API_v3/Mail/errors.html
public enum SendGridStatus {
	_200(200, "OK", "Your message is valid, but it is not queued to be delivered."),
	_202(202, "ACCEPTED", "Your message is both valid, and queued to be delivered."),
	_503(503, "SERVICE NOT AVAILABLE", "The SendGrid v3 Web API is not available.");

	/** HTTP status code */
	private int code;
	/** HTTP status reason */
	private String reason;
	/** HTTP status descriptive meaning */
	private String description;

	private SendGridStatus(int code, String reason, String desc) {
		this.code = code;
		this.reason = reason;
		this.description = desc;
	}

	public int code() { return code; }

	public String reason() { return reason; }

	public String description() { return description; }

	public static SendGridStatus parse(int code) {
		for (SendGridStatus it: SendGridStatus.values())
			if (it.code == code) return it;
		return null;
	}
}
