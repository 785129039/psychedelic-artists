package com.nex.web.spring.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.nex.security.permissions.ConfigurablePermissionsHandler;
import com.nex.web.freemarker.FreemarkerTemplateHelper;

public class FreemarkerView extends FreeMarkerView {

	@Override
	protected void doRender(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RequestContext requestContext = (RequestContext) model
				.get(SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE);
		FreemarkerTemplateHelper templateHelper = new FreemarkerTemplateHelper(
				requestContext);
		request.setAttribute("_th", templateHelper);
		request.setAttribute("_sc", request.getServletPath());
		request.setAttribute("_permissionHandler", ConfigurablePermissionsHandler.getHandler());
		request.setAttribute("_contextTemplates", "/templates");
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		if(user != null && !user.getPrincipal().equals("anonymousUser")) {
			request.setAttribute("_user", ((User) user.getPrincipal()).getUsername());
		}
		super.doRender(model, request, response);
	}

}
