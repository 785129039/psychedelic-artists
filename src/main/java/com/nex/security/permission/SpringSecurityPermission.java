package com.nex.security.permission;

import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.nex.security.permissions.ConfigurablePermissionsHandler;
import com.nex.security.permissions.RoleProvider;
import com.nex.utils.ReflectionUtils;

public class SpringSecurityPermission extends ConfigurablePermissionsHandler<GrantedAuthority> {

	@Override
	public RoleProvider<GrantedAuthority> getRolesProvider() {
		return new RoleProvider<GrantedAuthority>() {

			@Override
			public String toString(GrantedAuthority t) {
				return t.getAuthority();
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities(Object... args) {
				//protoze po sendError, security context neni v security holderu, ale pouze v session
				//neni to uplne potreba cist to ze sesny, ale musi si clovek davat pozor v jakem threadu se tento handler pouziva
				HttpServletRequest request = (HttpServletRequest) getParameterObject(HttpServletRequest.class, args);
				SecurityContext ctx = SecurityContextHolder.getContext();
				if(request != null) {
					ctx = (SecurityContext) request.getSession().getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
				}
				if(ctx == null) return Collections.emptyList();
				return ctx.getAuthentication().getAuthorities();
			}
			public <T> T getParameterObject(Class<T> paramType, Object... args) {
				for(Object o: args) {
					if(ReflectionUtils.isChildOf(o.getClass(), paramType)) {
						return (T) o;
					}
				}
				return null;
			}
		};
	}

}
