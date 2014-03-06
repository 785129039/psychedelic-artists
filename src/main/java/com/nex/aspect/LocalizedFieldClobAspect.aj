package com.nex.aspect;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.NaturalId;

import com.nex.annotation.ShortString;
import com.nex.domain.localizedfield.LocalizedFieldClob;

public aspect LocalizedFieldClobAspect {
	
	
	@Column(name = "lang")
	@NaturalId(mutable = true)
	@XmlTransient
	private String LocalizedFieldClob.languageCode;
	
	@Lob
	@ShortString(max=100)
	@XmlTransient
	@Column(name="value")
	private String LocalizedFieldClob.value;
	
	
	public String LocalizedFieldClob.getLanguageCode() {
		return this.languageCode;
	}
	
	
	public String LocalizedFieldClob.getValue() {
		return this.value;
	}
	public void LocalizedFieldClob.setLanguageCode(
			String languageCode) {
		this.languageCode = languageCode;
	}
	public void LocalizedFieldClob.setValue(String value) {
		this.value = value;
	}
}
