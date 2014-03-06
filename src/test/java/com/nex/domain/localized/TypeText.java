package com.nex.domain.localized;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

import com.nex.domain.localizedfield.LocalizedFieldNormal;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "puTest", table = "type_text", versionField="")
public class TypeText implements LocalizedFieldNormal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "type_id", insertable = false, updatable = false)
	private Type localizable;	
}
