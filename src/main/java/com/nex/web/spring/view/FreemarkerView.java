package com.nex.web.spring.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.nex.security.permissions.ConfigurablePermissionsHandler;
import com.nex.web.freemarker.FreemarkerTemplateHelper;

public class FreemarkerView extends FreeMarkerView {
	public static final String IS_ERORR = "_isError";
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
		if(request.getAttribute(IS_ERORR) != null) {
			model.put("_contextTemplates", "");
		}
		SecurityContext ctx = (SecurityContext) request.getSession().getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		if(!model.containsKey("user") && ctx!=null) {
			model.put("_user", ctx.getAuthentication().getName());
		}
		super.doRender(model, request, response);
	}

}
