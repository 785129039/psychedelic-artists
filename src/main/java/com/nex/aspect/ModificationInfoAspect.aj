package com.nex.aspect;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import com.nex.annotation.Modifiable;


public aspect ModificationInfoAspect {
	
	public interface ModificationInfo{}
	
	@Transient
	@XmlTransient
	private String ModificationInfo.createdBy;
	@Transient
	@XmlTransient
	private String ModificationInfo.modifiedBy;
	@Transient
	@XmlTransient
	private Date ModificationInfo.modifiedOn;
	@Transient
	@XmlTransient
	private Date ModificationInfo.createdOn;
	
	declare parents: @Modifiable * implements ModificationInfo;
	
	
	@PrePersist
	public void ModificationInfo.modifyWhenCreating() {
		this.setCreatedOn(new Date());
		String cl = this.getCurrentUser();
		this.setCreatedBy(cl);
		this.setModifiedOn(new Date());
		this.setModifiedBy(cl);
	}

	@PreUpdate
	public void ModificationInfo.modifyWhenUpdating() {
		this.setModifiedOn(new Date());
		this.setModifiedBy(this.getCurrentUser());
	}
	
	public String ModificationInfo.getCurrentUser() {
		return "ehm";
	}
	
	@ManyToOne()
	@JoinColumn(name = "CREATED_BY", insertable = true, updatable = true)
	@Access(AccessType.PROPERTY)
	public String ModificationInfo.getCreatedBy() {
		return createdBy;
	}
	
	@Column(name="MODIFIED_ON")
	@Access(AccessType.PROPERTY)
	public Date ModificationInfo.getModifiedOn() {
		return modifiedOn;
	}
	
	@Column(name="CREATED_ON")
	@Access(AccessType.PROPERTY)
	public Date ModificationInfo.getCreatedOn() {
		return createdOn;
	}
	@ManyToOne()
	@JoinColumn(name = "MODIFIED_BY", insertable = true, updatable = true)
	@Access(AccessType.PROPERTY)
	public String ModificationInfo.getModifiedBy() {
		return this.modifiedBy;
	}
	
	public void ModificationInfo.setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public void ModificationInfo.setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	
	public void ModificationInfo.setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	public void ModificationInfo.setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public String ModificationInfo.getCreatedByFullName() {
		String l = getCreatedBy();
		return l != null ? l:"";
	}

	public String ModificationInfo.getModifiedByFullName() {
		String l = getModifiedBy();
		return l != null ? l:"";
	}
}
