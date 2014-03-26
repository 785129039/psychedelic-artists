package com.nex.security.permissions.checker;

import com.nex.domain.User;
import com.nex.security.permissions.ParsedAuthority;
import com.nex.utils.RequestUtils;

public class UserPermissionChecher extends AbstractPermissionChecker {

	@Override
	public Boolean check(ParsedAuthority authority) {
		User user = getParameterObject(User.class);
		String definition = authority.getDefinition();
		if(definition.equals("loggedUser")) {
			User luser = RequestUtils.getLoggedUser();
			if(luser == null) {
				return Boolean.FALSE;
			} else if(luser.getId().equals(user.getId()))  {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

}
