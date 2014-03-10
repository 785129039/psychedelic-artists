package com.nex.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

public class TransactionalFilter implements Filter {

	
	@Transactional
	public void doPost(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException{
		chain.doFilter(request, response);
	}
	@Transactional(readOnly=true)
	public void doGet(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException{
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String _method = ((HttpServletRequest)request).getMethod();
		if("POST".equals(_method)) {
			doPost(request, response, chain);
		} else {
			doGet(request, response, chain);
		}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
