package com.nex.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

import com.nex.annotation.Modifiable;
import com.nex.domain.common.Entity;

@RooJavaBean
@RooJpaActiveRecord(finders="findGenresByPublished", persistenceUnit = "puPsyartists", table = "genre", versionField = "")
@Modifiable
public class Genre implements Entity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@Size(min=1, max=50)
	private String name;
	public Boolean published = Boolean.TRUE;
	@Override
	public String toString() {
		return this.name;
	}
}
