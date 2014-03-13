package com.nex.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

import com.nex.annotation.ModifiableAnonymouse;
import com.nex.domain.common.CommentEntity;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "puPsyartists", table = "preset_comment", versionField = "")
@ModifiableAnonymouse
public class PresetComment implements CommentEntity<Preset> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String comment;
	@ManyToOne
	@JoinColumn(name="preset_id")
	private Preset preset;
	
	public String provideUserName() {
		return this.username;
	}
	
	@Override
	public void setEntity(Preset t) {
		this.preset = t;
	}
}
