package com.nex.utils;

import javax.servlet.http.HttpServletRequest;

public class Requestutils {
	
	public static String createContextRedirect(String url, boolean exposedAttributes) {
		return "redirect:/web" + url;
	}
	public static String createRedirect(String url, boolean exposedAttributes) {
		return "redirect:" + url;
	}
	public static String getFullURL(HttpServletRequest request) {
	    StringBuffer requestURL = request.getRequestURL();
	    String queryString = request.getQueryString();

	    if (queryString == null) {
	        return requestURL.toString();
	    } else {
	        return requestURL.append('?').append(queryString).toString();
	    }
	}
	
	public static String getBaseUrl( HttpServletRequest request ) {
	    if ( ( request.getServerPort() == 80 ) ||
	         ( request.getServerPort() == 443 ) )
	      return request.getScheme() + "://" +
	             request.getServerName() +
	             request.getContextPath();
	    else
	      return request.getScheme() + "://" +
	             request.getServerName() + ":" + request.getServerPort() +
	             request.getContextPath();
	  }
}
