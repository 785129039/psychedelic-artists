package com.nex.security.permission;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nex.security.permissions.ConfigurablePermissionsHandler;
import com.nex.security.permissions.RoleProvider;

public class SpringSecurityPermission extends ConfigurablePermissionsHandler<GrantedAuthority> {

	@Override
	public RoleProvider<GrantedAuthority> getRolesProvider() {
		return new RoleProvider<GrantedAuthority>() {
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				Authentication a = SecurityContextHolder.getContext().getAuthentication();
				if(a != null)
					return a.getAuthorities();
				else 
					return new ArrayList<GrantedAuthority>();
			}

			@Override
			public String toString(GrantedAuthority t) {
				return t.getAuthority();
			}
		};
	}

}
