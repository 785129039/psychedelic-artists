package com.nex.domain.localized;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

import com.nex.domain.localizedfield.LocalizedEntity;
import com.nex.domain.localizedfield.LocalizedField;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "puTest", table = "record_type", versionField="")
public class Type implements LocalizedEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToMany(targetEntity = TypeText.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="localizable")
	private List<LocalizedField> localizedTexts = new ArrayList<LocalizedField>();
}
