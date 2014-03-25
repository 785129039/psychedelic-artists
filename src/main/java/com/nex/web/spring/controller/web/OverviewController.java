package com.nex.web.spring.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nex.annotation.Logger;

@Controller
@RequestMapping("/browse/overview/")
@Logger
public class OverviewController {
//	@Resource(name = "jpqlfilter")
//	private FilterUtil jpqlFilter;
//	
//	@RequestMapping
//	public String list(HttpServletRequest request,
//			HttpServletResponse response, Model uiModel) {
//		RequestBasedFilter filter = createFilter(request);
//		filter.addIgnoreKey("lang");
//		// filter.configureFromDatabase(request);
//		filter.configureFilterFromHttp(request, response);
//		FilteredList<Overview> list = getListFilter(this.jpqlFilter, filter, Overview.class);
//		uiModel.addAttribute("entities", list);
//		uiModel.addAttribute("entities_data", list.getData());
//		uiModel.addAttribute("entities_filter", list.getFilter());
//		return "web/browse/overview";
//	}
//	protected RequestBasedFilter createFilter(HttpServletRequest request) {
//		request.setAttribute("filterId", "_null_");
//		Map<String, String> defaultConditions = new HashMap<String, String>();
//		Sort sort = new Sort("id", SortDirection.DESC);
//		RequestBasedFilter filter = new RequestBasedFilter(defaultConditions,
//				sort, new PageSetting(10, true));
//		return filter;
//	}
//	
//	@SuppressWarnings("unchecked")
//	protected FilteredList<Overview> getListFilter(FilterUtil jpqlf, Filter filter, Class<Overview> cls) {
//		try {
//			return jpqlf.findByFilter(cls, filter);
//		} catch (Exception e) {
////			log.error("", e);
//		}
//		return new FilteredList<Overview>(filter, new ArrayList<Overview>(), 0);
//	}
//	
//	public void configureFilter(cz.tsystems.common.data.filter.Filter filter,
//			HttpServletRequest request) {
//
//	}
}
