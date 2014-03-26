package com.nex.web.spring.controller.web.profile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.nex.web.spring.controller.common.RejectErrorController;

@Controller
@RequestMapping("/resetpassword/")
public class ForgetPasswordController extends RejectErrorController {

	@Resource(name = "mailSender")
	private JavaMailSender mailSender;
	
	public class ResetForm {
		@NotNull
		@NotBlank
		public String email;
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
	}
	
	@ModelAttribute("form")
	public ResetForm loadForm() {
		return new ResetForm();
	}
	
	@RequestMapping
	public String showDialog() {
		return "web/profile/reset/show";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String sendResetRequest(@ModelAttribute("form") @Valid ResetForm form, Errors errors, HttpServletRequest request) {
		if(!errors.hasErrors()){
			SimpleMailMessage m = new SimpleMailMessage();
			m.setTo(form.getEmail());
			m.setFrom("noreply@psychedelic-artists.cz");
			m.setSubject("test");
			m.setText("text body");
			mailSender.send(m);
			return "redirect:/web/login";
		} else {
			rejectFormErrors(RequestContextUtils.getLocale(request), errors);
		}
		return showDialog();
	}
}
