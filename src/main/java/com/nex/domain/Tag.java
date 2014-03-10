package com.nex.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

import com.nex.annotation.Modifiable;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "puPsyartists", table = "tag", versionField = "")
@Modifiable
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Size(min=1, max=50)
	private String name;
}
