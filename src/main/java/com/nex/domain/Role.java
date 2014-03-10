package com.nex.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@RooJpaActiveRecord(finders={"findRolesByCode"},persistenceUnit = "puPsyartists", table = "role", versionField="")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String code;
	
	
	
}
