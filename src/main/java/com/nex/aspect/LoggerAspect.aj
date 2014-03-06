package com.nex.aspect;

import javax.persistence.Transient;

import org.slf4j.LoggerFactory;

import com.nex.annotation.Logger;



public aspect LoggerAspect {
public interface LoggerInjection {}
	
	@Transient
	public transient org.slf4j.Logger LoggerInjection.log = LoggerFactory.getLogger(getClass());
	
	declare parents: @Logger * implements LoggerInjection;
}
