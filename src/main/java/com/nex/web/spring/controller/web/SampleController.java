package com.nex.web.spring.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nex.domain.Sample;
import com.nex.domain.User;
import com.nex.security.permissions.aspect.Authorize;
import com.nex.security.permissions.checker.UserPermissionChecher;
import com.nex.utils.Requestutils;

import cz.tsystems.common.data.filter.Filter;
import cz.tsystems.common.data.filter.SortDirection;

@Controller
@RequestMapping("/sample/")
public class SampleController extends FileEntityController<Sample> {

	@Override
	public void init() {
		setEntityClass(Sample.class);
		setDefaultSortDirection(SortDirection.ASC);
		setDefaultSortProperty("id");
		setControllerURl("web/sample/");
		setRedirectSaveToList(false);
	}
	@Override
	public void configureFilter(Filter filter, HttpServletRequest request) {
		filter.addConditionReplacement("name", "name|LRLike(String)");
		filter.addDefaultCondition("user.id|Equal(Long)", Requestutils.getLoggedUser().getId().toString());
	}
	@Override
	protected void checkPermission(Sample entity) {
		this.check(entity.getUser());
	}
	@Authorize(value={"ROLE_USER:loggedUser", "ROLE_ADMIN:loggedUser"}, checker = UserPermissionChecher.class)
	private void check(User user) {}
	
	
}
