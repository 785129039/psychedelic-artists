package com.nex.mail;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailSender {
	
	private String auth;
	private String host;
	private int port;
	private String username;
	private String password;
	private String ssl;
	private String protocol;
	
	public  JavaMailSenderImpl getSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();

		Properties mailProps = new Properties();
		mailProps.put("mail.smtps.auth", this.auth);
		mailProps.put("mail.smtp.starttls.enable", this.ssl);
		sender.setJavaMailProperties(mailProps);

		sender.setProtocol(this.protocol);
		sender.setPort(this.port);
		sender.setHost(this.host);
		sender.setUsername(this.username);
		sender.setPassword(this.password);
		return sender;
		
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSsl(String ssl) {
		this.ssl = ssl;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	
}
