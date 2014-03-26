package com.nex.web.spring.controller.web.profile;

import java.lang.reflect.Method;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.nex.annotation.Logger;
import com.nex.domain.ResetUserPassword;
import com.nex.domain.User;
import com.nex.utils.Requestutils;
import com.nex.web.spring.controller.common.RejectErrorController;

@Controller
@RequestMapping("/profile/changepassword/")
@Logger
public class UserChangePasswordController extends RejectErrorController {

	@ModelAttribute("user")
	public ResetUserPassword loadUser() {
		User loggedUser = Requestutils.getLoggedUser();
		return ResetUserPassword.findResetUserPassword(loggedUser.getId());
	}
	
	@RequestMapping
	public String showForm() {
		return "web/profile/edit";
	}
	@RequestMapping(method=RequestMethod.POST)
	public String saveForm(@ModelAttribute("user") @Valid ResetUserPassword entity,
			Errors errors, Model uiModel, HttpServletRequest request) {
		if(!errors.hasErrors()){
			try {
				entity.flush();
				request.setAttribute("_success", true);
				return "redirect:?_success=true";
			} catch (Exception e) {
				log.error("", e);
				getEntityManager(entity).detach(entity);
				rejectAndTranslateError(RequestContextUtils.getLocale(request), errors, "form.actions.save.error.message.persist", new String[] {e.getMessage()});
			}
		} else {
			getEntityManager(entity).detach(entity);
			rejectFormErrors(RequestContextUtils.getLocale(request), errors);
		}
		return showForm();
	}
	protected EntityManager getEntityManager(ResetUserPassword entity) {
		Method method = ReflectionUtils.findMethod(entity.getClass(),
				"entityManager");
		EntityManager em = (EntityManager) ReflectionUtils.invokeMethod(method,
				null);
		return em;
	}
}
