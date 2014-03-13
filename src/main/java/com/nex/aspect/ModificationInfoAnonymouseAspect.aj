package com.nex.aspect;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nex.annotation.ModifiableAnonymouse;
import com.nex.domain.User;
import com.nex.utils.Requestutils;

public aspect ModificationInfoAnonymouseAspect {
public interface ModificationInfoAnonymouse{String provideUserName();}
	
	@Transient
	private String ModificationInfoAnonymouse.createdBy;
	@Transient
	private String ModificationInfoAnonymouse.modifiedBy;
	@Transient
	private Date ModificationInfoAnonymouse.modifiedOn;
	@Transient
	private Date ModificationInfoAnonymouse.createdOn;
	
	declare parents: @ModifiableAnonymouse * implements ModificationInfoAnonymouse;
	
	
	@PrePersist
	public void ModificationInfoAnonymouse.modifyWhenCreating() {
		this.setCreatedOn(new Date());
		this.setModifiedOn(new Date());
		this.setCreatedBy(this.provideUserName());
		this.setModifiedBy(this.provideUserName());
	}

	@PreUpdate
	public void ModificationInfoAnonymouse.modifyWhenUpdating() {
		this.setModifiedOn(new Date());
		this.setModifiedBy(this.provideUserName());
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public User ModificationInfoAnonymouse.getCurrentUser() {
		return Requestutils.getLoggedUser();
	}
	
	@Column(name="CREATED_BY")
	@Access(AccessType.PROPERTY)
	public String ModificationInfoAnonymouse.getCreatedBy() {
		return createdBy;
	}
	
	@Column(name="MODIFIED_ON")
	@Access(AccessType.PROPERTY)
	public Date ModificationInfoAnonymouse.getModifiedOn() {
		return modifiedOn;
	}
	
	@Column(name="CREATED_ON")
	@Access(AccessType.PROPERTY)
	public Date ModificationInfoAnonymouse.getCreatedOn() {
		return createdOn;
	}
	@Column(name = "MODIFIED_BY")
	@Access(AccessType.PROPERTY)
	public String ModificationInfoAnonymouse.getModifiedBy() {
		return this.modifiedBy;
	}
	
	public void ModificationInfoAnonymouse.setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public void ModificationInfoAnonymouse.setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	
	public void ModificationInfoAnonymouse.setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	public void ModificationInfoAnonymouse.setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
