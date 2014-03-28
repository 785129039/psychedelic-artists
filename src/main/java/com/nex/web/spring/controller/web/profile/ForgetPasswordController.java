package com.nex.web.spring.controller.web.profile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.nex.annotation.Logger;
import com.nex.domain.ResetUserPassword;
import com.nex.domain.User;
import com.nex.encryption.Encryption;
import com.nex.encryption.EncryptionFactory;
import com.nex.mail.MailService;
import com.nex.utils.StringUtils;
import com.nex.web.spring.controller.common.RejectErrorController;

@Controller
@RequestMapping("/resetpassword/")
@Logger
public class ForgetPasswordController extends RejectErrorController {
	
	@Resource(name = "sendMailService")
	private MailService mailService;
	
	@Resource(name = "resetPasswordEncryptionFactory")
	private EncryptionFactory encryptionFactory;
	
	private static final String ENCRYPTION_SESSION_STORED = "pw_reset_encryption";
	
	public class ResetForm {
		@NotNull
		@NotBlank
		public String email;
		public String forgetUri;
		public int duration;
		
		public int getDuration() {
			return duration;
		}
		public void setDuration(int duration) {
			this.duration = duration;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getForgetUri() {
			return forgetUri;
		}
		public void setForgetUri(String forgetUri) {
			this.forgetUri = forgetUri;
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
	
	@RequestMapping("success")
	public String success() {
		return "web/profile/reset/success";
	}
	
	@ModelAttribute("user")
	private ResetUserPassword loadUser(HttpServletRequest request) {
		String code = getPathVariables().get("code");
		if(code != null) {
			Encryption enc = (Encryption) request.getSession().getAttribute(ENCRYPTION_SESSION_STORED);
			if(enc == null) {
				enc = encryptionFactory.get(code);
				encryptionFactory.remove(code);
				request.getSession().setAttribute(ENCRYPTION_SESSION_STORED, enc);
			}
			try {
				String userEmail = enc.decipher(code);
				User user = User.findUsersByEmail(resolveEmail(userEmail)).getSingleResult();
				ResetUserPassword resuser = ResetUserPassword.findResetUserPassword(user.getId());
				resuser.setTokenReset(true);
				return resuser;
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return null;
	}
	@RequestMapping(value= "{code}", method = RequestMethod.POST)
	public String saveForm(@ModelAttribute("user") @Valid ResetUserPassword entity,
			Errors errors, Model uiModel, HttpServletRequest request) {
		if(!errors.hasErrors()){
			try {
				entity.flush();
				request.getSession().removeAttribute(ENCRYPTION_SESSION_STORED);
				return "redirect:/web/login?_reset";
			} catch (Exception e) {
				log.error("", e);
				getEntityManager(entity).detach(entity);
				rejectAndTranslateError(RequestContextUtils.getLocale(request), errors, "form.actions.save.error.message.persist", new String[] {e.getMessage()});
			}
		} else {
			getEntityManager(entity).detach(entity);
			rejectFormErrors(RequestContextUtils.getLocale(request), errors);
		}
		return "web/profile/reset/reset";
	}
	
	@RequestMapping("{code}")
	public String showResetDialog(@ModelAttribute("user") ResetUserPassword user, HttpServletRequest request) {
		if(user == null) {
			return "web/profile/reset/error";
		}
		return "web/profile/reset/reset";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String sendResetRequest(@ModelAttribute("form") @Valid ResetForm form, Errors errors, HttpServletRequest request) {
		if(!errors.hasErrors()){
			try {
				
				Encryption enc = encryptionFactory.createNewEncryption();
				String code = enc.cipher(makeUniqEmailPerRequest(form.getEmail()));
				encryptionFactory.put(code, enc);
				form.setForgetUri("web/resetpassword/" + code);
				form.setDuration(encryptionFactory.getDuration());
				this.mailService.send("mail.forgetpassword", RequestContextUtils.getLocale(request), form, new Object[]{request}, form.getEmail());
				return "redirect:success";
			} catch (Exception e) {
				log.error("", e);
				rejectFormErrors(RequestContextUtils.getLocale(request), errors, "Global.error");
			}
		} else {
			rejectFormErrors(RequestContextUtils.getLocale(request), errors);
		}
		return showDialog();
	}
	
	public String makeUniqEmailPerRequest(String email) {
		return email + "--"+email.hashCode();
	}
	public String resolveEmail(String email) {
		return StringUtils.getArrayFromString(email, "--")[0];
	}
}
