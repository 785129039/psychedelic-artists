package com.nex.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

import com.nex.annotation.Modifiable;
import com.nex.domain.common.Entity;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "puPsyartists", table = "sample_comment", versionField = "")
@Modifiable
public class SampleComment implements Entity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
}
