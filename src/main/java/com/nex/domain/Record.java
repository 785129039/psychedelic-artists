package com.nex.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

import com.nex.annotation.Logger;
import com.nex.annotation.Modifiable;
import com.nex.domain.common.FileEntity;
import com.nex.domain.common.FilterableEntity;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "puPsyartists", table = "record", versionField = "")
@Modifiable
@Logger
public class Record implements FileEntity, FilterableEntity {
	
	private static final long serialVersionUID = 1L;

	@Transient
	@Value("${record.path}")
	private String serverPath;
	
	@ManyToMany
	@JoinTable(name = "record_tag", joinColumns = { @JoinColumn(name = "record_id") }, inverseJoinColumns = { @JoinColumn(name = "tag_id") })
	private List<Tag> tags = new ArrayList<Tag>();
	
	@ManyToMany
	@JoinTable(name = "record_genre", joinColumns = { @JoinColumn(name = "record_id") }, inverseJoinColumns = { @JoinColumn(name = "genre_id") })
	private List<Genre> genres = new ArrayList<Genre>();
	
	@OneToMany(mappedBy="record", cascade=CascadeType.REMOVE)
	private List<RecordComment> comments = new ArrayList<RecordComment>();
	
	@NotNull
	@NotBlank
	private String name;
	
	private String description;
	
}
