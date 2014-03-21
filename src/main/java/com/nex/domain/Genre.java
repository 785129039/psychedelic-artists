package com.nex.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
//	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinTable(name = "sample_genre", joinColumns = { 
//			@JoinColumn(name = "genre_id", nullable = false, updatable = false) }, 
//			inverseJoinColumns = { @JoinColumn(name = "sample_id", 
//					nullable = false, updatable = false) })
//	private List<Sample> samples = new ArrayList<Sample>();
}
