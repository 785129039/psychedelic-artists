package com.nex.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

import com.nex.annotation.Logger;
import com.nex.annotation.Modifiable;
import com.nex.domain.common.FileEntity;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "puPsyartists", table = "preset", versionField = "")
@Modifiable
@Logger
public class Preset implements FileEntity {
	
	@Transient
	@Value("${presets.path}")
	private String serverPath;
	
	@ManyToMany()
	@JoinTable(name = "preset_tag", joinColumns = { @JoinColumn(name = "preset_id") }, inverseJoinColumns = { @JoinColumn(name = "tag_id") })
	private List<Tag> tags = new ArrayList<Tag>();
	
	@ManyToMany
	@JoinTable(name = "preset_genre", joinColumns = { @JoinColumn(name = "preset_id") }, inverseJoinColumns = { @JoinColumn(name = "genre_id") })
	@NotEmpty
	private List<Genre> genres;
	
	@OneToMany(mappedBy="preset")
	private List<PresetComment> comments;
	
	@NotNull
	@Size(min=1, max=50)
	private String name;
	
	private String description;
}
