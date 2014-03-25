package com.nex.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

import com.nex.annotation.ModifiableAnonymouse;
import com.nex.domain.common.CommentEntity;
import com.nex.domain.common.Entity;
import com.nex.domain.common.FilterableEntity;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "puPsyartists", table = "record_comment", versionField = "")
@ModifiableAnonymouse
public class RecordComment implements Entity, FilterableEntity, CommentEntity<Record> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@Size(min=1, max=50)
	private String username;
	@NotNull
	@Size(min=1)
	private String comment;
	
	@ManyToOne
	@JoinColumn(name="record_id")
	private Record record;
	
	@Override
	public void setEntity(Record t) {
		this.record = t;
	}
	public String provideUserName() {
		return this.username;
	}
}
