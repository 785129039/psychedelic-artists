package com.nex.domain;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

import com.nex.annotation.Modifiable;
import com.nex.domain.common.Entity;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "puPsyartists", table = "sample", versionField = "")
@Modifiable
public class Sample implements Entity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@NotNull
	@Size(min=1, max=50)
	private String name;
	
	private String description;
	
	@NotNull
	@Size(min=1, max=50)
	private String path;
	
	@OneToMany
	@JoinTable(name = "sample_tag", joinColumns = { @JoinColumn(name = "sample_id") }, inverseJoinColumns = { @JoinColumn(name = "tag_id") })
	private List<Tag> tags;
	
	@OneToMany
	@JoinTable(name = "sample_genre", joinColumns = { @JoinColumn(name = "sample_id") }, inverseJoinColumns = { @JoinColumn(name = "genre_id") })
	@NotEmpty
	private List<Genre> genres;
}
