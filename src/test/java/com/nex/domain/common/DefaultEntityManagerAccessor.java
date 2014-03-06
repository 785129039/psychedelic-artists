package com.nex.domain.common;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Configurable;

import cz.tsystems.common.data.entitymanager.EntityManagerAccessor;

@Configurable
public class DefaultEntityManagerAccessor implements EntityManagerAccessor { 
	
	@PersistenceContext(unitName = "puTest")
	private EntityManager entityManager;


	public DefaultEntityManagerAccessor() {
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}
