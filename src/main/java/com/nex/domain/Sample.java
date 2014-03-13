package com.nex.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

import com.nex.annotation.Logger;
import com.nex.annotation.Modifiable;
import com.nex.domain.common.FileEntity;
import com.nex.domain.common.FilterableEntity;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "puPsyartists", table = "sample", versionField = "")
@Modifiable
@Logger
public class Sample implements FileEntity, FilterableEntity {
	
	@Transient
	@Value("${samples.path}")
	private String serverPath;
	
	@ManyToMany()
	@JoinTable(name = "sample_tag", joinColumns = { @JoinColumn(name = "sample_id") }, inverseJoinColumns = { @JoinColumn(name = "tag_id") })
	private List<Tag> tags = new ArrayList<Tag>();
	
	@ManyToMany
	@JoinTable(name = "sample_genre", joinColumns = { @JoinColumn(name = "sample_id") }, inverseJoinColumns = { @JoinColumn(name = "genre_id") })
	@NotEmpty
	private List<Genre> genres;
	
	@OneToMany(mappedBy="sample", cascade=CascadeType.REMOVE)
	private List<SampleComment> comments = new ArrayList<SampleComment>();
	
	@NotNull
	@NotBlank
	private String name;
	
	private String description;
}
