package com.nex.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class TransactionalFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String method = ((HttpServletRequest) request).getMethod();
		if ("GET".equals(method)) {
			chainReadonly(request, response, chain);
		} else if ("POST".equals(method) || "PUT".equals(method)
				|| "DELETE".equals(method)) {
			chainWrite(request, response, chain);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public void chainReadonly(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	@Transactional()
	public void chainWrite(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
