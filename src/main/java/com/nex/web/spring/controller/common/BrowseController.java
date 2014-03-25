package com.nex.web.spring.controller.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.nex.annotation.Logger;
import com.nex.domain.Genre;
import com.nex.domain.Tag;
import com.nex.domain.common.CommentEntity;
import com.nex.domain.common.FilterableEntity;
import com.nex.utils.ReflectionUtils;
import com.nex.utils.StringUtils;

import cz.tsystems.common.data.filter.Filter;
import cz.tsystems.common.data.filter.FilterUtil;
import cz.tsystems.common.data.filter.FilteredList;
import cz.tsystems.common.data.filter.PageSetting;
import cz.tsystems.common.data.filter.RequestBasedFilter;
import cz.tsystems.common.data.filter.Sort;
import cz.tsystems.common.data.filter.SortDirection;

@Logger
public abstract class BrowseController<T extends FilterableEntity, C extends CommentEntity<T>> extends RejectErrorController {
	
	@ModelAttribute("genres")
	private List<Genre> loadGenres() {
		return Genre.findAllGenres();
	}
	
	@Resource(name = "jpqlfilter")
	private FilterUtil jpqlFilter;
	private Boolean successRemember = Boolean.TRUE;
	protected String controllerUrl() {
		return "web/browse/";
	}
	
	protected abstract Class<T> getEntityClass();
	protected abstract Class<C> getCommentClass();
	protected String targetField() {
		return "record";
	}
	@ModelAttribute("comment")
	protected C createNewComment(HttpServletRequest request) {
		try {
			return (C) getCommentClass().newInstance();
		} catch (Exception e) {
			log.error("Create of new entity failed!", e);
			throw new RuntimeException("Create of new entity failed!", e);
		}
	}
	
	@RequestMapping(value="{pid}", method = RequestMethod.POST)
	public String createComment(@PathVariable String pid, @ModelAttribute("comment") @Valid C entity, Errors errors, HttpServletRequest request,
			HttpServletResponse response, Model uiModel) {
		try {
			if (errors.hasErrors()) {
				// BeanUtils.setProperty(entity, "id", null);
				rejectFormErrors(RequestContextUtils.getLocale(request), errors);
				return show(pid, request, response, uiModel);
			}
			T ownerEntity = findEntityById(pid);
			entity.setEntity(ownerEntity);
			entity.persist();
			entity.flush();
			request.setAttribute("success", true);
		} catch (Exception e) {
			log.info("", e);
			rejectAndTranslateError(RequestContextUtils.getLocale(request), errors,
					"form.actions.save.error.message.persist",
					new Object[] { e.getMessage() });
			return show(pid, request, response, uiModel);
		}
		return controllerRedirectUrl(request, pid);
	}
	protected String controllerRedirectUrl(HttpServletRequest request, String uri) {
		String simple = request.getParameter("_simple");
		String params = simple!=null?"?_simple=true":"";
		if(successRemember) {
			if(simple == null) {
				params = "?_success=true";
			} else {
				params += "&_success=true";
			}
		}
		return "redirect:" + uri + params;
	}
	@RequestMapping(value="{pid}", method = RequestMethod.GET)
	public String show(@PathVariable String pid, HttpServletRequest request,
			HttpServletResponse response, Model uiModel) {
		uiModel.addAttribute("entity", findEntityById(pid));
		processFilter(request, response, uiModel, (Class<FilterableEntity>) getCommentClass(), pid);
		uiModel.addAttribute("_class", getEntityClass().getSimpleName());
		return controllerUrl() + "detail";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(HttpServletRequest request,
			HttpServletResponse response, Model uiModel) {
		processFilter(request, response, uiModel, (Class<FilterableEntity>) getEntityClass(), null);
		uiModel.addAttribute("_class", getEntityClass().getSimpleName());
		return controllerUrl() + getListTemplate();
	}
	
	public String getListTemplate() {
		return "list";
	}
	
	public void processFilter(HttpServletRequest request,
			HttpServletResponse response, Model uiModel, Class<FilterableEntity> cls, String id) {
		RequestBasedFilter filter = createFilter(request);
		filter.addIgnoreKey("lang");
		// filter.configureFromDatabase(request);
		filter.configureFilterFromHttp(request, response);
		if(id != null) {
			filter.getConditions().clear();
			filter.addDefaultCondition(targetField() + ".id|Equal(Long)", id);
		} else {
			configureFilter(filter, request);
		}
		FilteredList<FilterableEntity> list = getListFilter(this.jpqlFilter, filter, cls);
		uiModel.addAttribute("entities", list);
		uiModel.addAttribute("entities_data", list.getData());
		uiModel.addAttribute("entities_filter", list.getFilter());
	}
	
	protected RequestBasedFilter createFilter(HttpServletRequest request) {
		request.setAttribute("filterId", "_null_");
		Map<String, String> defaultConditions = new HashMap<String, String>();
		Sort sort = new Sort("modifiedOn", SortDirection.DESC);
		RequestBasedFilter filter = new RequestBasedFilter(defaultConditions,
				sort, new PageSetting(10, true));
		return filter;
	}
	
	@SuppressWarnings("unchecked")
	protected FilteredList<FilterableEntity> getListFilter(FilterUtil jpqlf, Filter filter, Class<FilterableEntity> cls) {
		try {
			return jpqlf.findByFilter(cls, filter);
		} catch (Exception e) {
			log.error("", e);
		}
		return new FilteredList<FilterableEntity>(filter, new ArrayList<FilterableEntity>(), 0);
	}
	
	public void configureFilter(Filter filter, HttpServletRequest request) {
		filter.addConditionReplacement("name", "name|LRLike(String)");
		filter.addAssociation("tags");
		filter.addConditionReplacement("tag", "tags.id|In(Long[])");
		filter.addAssociation("genres");
		filter.addConditionReplacement("genre", "genres.id|In(Long[])");
		filter.setAddDistinctToQuery(true);
		String tags = filter.getConditions().get("tag");
		if(tags == null || StringUtils.isEmpty(tags)) return;
		String[] tagIds = StringUtils.getArrayFromString(tags.replaceAll("\\[|\\]", ""), ",");
		List<Tag> t = new ArrayList<Tag>();
		for(String id: tagIds) {
			t.add(Tag.findTag(Long.valueOf(id)));
		}
		request.setAttribute("tags", t);
	}
	public T findEntityById(String id) {
		Class<T> entityClass = getEntityClass();
		String findByIdMethodName = "find" + entityClass.getSimpleName();
		Method findByIdMethod = ReflectionUtils.findMethod(entityClass,
				findByIdMethodName, Long.class);
		@SuppressWarnings("unchecked")
		T entity = (T) ReflectionUtils.invokeMethod(findByIdMethod, null,
				new Long(id));
		if (entity == null) {
			throw new EntityNotFoundException("Entity "
					+ entityClass.getSimpleName() + " with id=" + id
					+ " not found.");
		}
		return entity;
	}	
	
}
