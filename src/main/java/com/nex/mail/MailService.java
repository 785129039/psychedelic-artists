package com.nex.mail;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailService {

	private MailSender mailSender;
	private SimpleMailMessage defaultEmailMessage;
	private EmailTextFormatter textFormatter;

	public void setTextFormatter(EmailTextFormatter textFormatter) {
		this.textFormatter = textFormatter;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setDefaultEmailMessage(SimpleMailMessage defaultEmailMessage) {
		this.defaultEmailMessage = defaultEmailMessage;
	}
	
	public void send(String code, String subject, Locale locale, String... recipients) {
		this.send(code, subject, locale, null, recipients);
	}
	
	public void send(String code, String subject, Locale locale, Object data, String... recipients) {
		this.send(code, subject, locale, data, new Object[0], recipients);
	}
	
	public void send(String code, Locale locale, String... recipients) {
		this.send(code, null, locale, null, new Object[0], recipients);
	}
	
	public void send(String code, Locale locale, Object data, String... recipients) {
		this.send(code, null, locale, data, new Object[0], recipients);
	}
	
	public void send(String code, Locale locale, Object data, Object[] evalsources, String... recipients) {
		this.send(code, null, locale, data, evalsources, recipients);
	}
	
	public void send(String code, String subject, Locale locale, Object data, Object[] evalsources, String... recipients) {
		String text = textFormatter.getText(code, data, locale, evalsources);
		String _subject = subject;
		if(_subject == null) {
			_subject = this.textFormatter.getSubject(code, data, locale, evalsources);
		}
		JavaMailSender sender = mailSender.getSender();
		MimeMessage message = sender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(this.defaultEmailMessage.getFrom());
			helper.setTo(recipients);
			message.setContent(text, "text/html; charset=utf-8" );
			message.setSubject(_subject, "utf-8");
			sender.send(message);
		} catch (MessagingException e) {
			LoggerFactory.getLogger(MailSender.class).error("", e);
		}
		
	}
	
}
