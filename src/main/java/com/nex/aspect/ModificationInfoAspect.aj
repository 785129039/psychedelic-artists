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

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nex.annotation.Modifiable;
import com.nex.domain.User;
import com.nex.utils.RequestUtils;


public aspect ModificationInfoAspect {
	
	public interface ModificationInfo{}
	
	@Transient
	private User ModificationInfo.createdBy;
	@Transient
	private User ModificationInfo.modifiedBy;
	@Transient
	private Date ModificationInfo.modifiedOn;
	@Transient
	private Date ModificationInfo.createdOn;
	@Transient
	private Boolean ModificationInfo.changeModification;
	
	declare parents: @Modifiable * implements ModificationInfo;
	
	
	@PrePersist
	public void ModificationInfo.modifyWhenCreating() {
		this.setCreatedOn(new Date());
		User cl = this.getCurrentUser();
		this.setCreatedBy(cl);
		this.setModifiedOn(new Date());
		this.setModifiedBy(cl);
		this.preSave();
	}

	@PreUpdate
	public void ModificationInfo.modifyWhenUpdating() {
		if(this.changeModification) {
			this.setModifiedOn(new Date());
			this.setModifiedBy(this.getCurrentUser());
			this.preUpdate();
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@JsonIgnore
	public User ModificationInfo.getCurrentUser() {
		return RequestUtils.getLoggedUser();
	}
	
	@ManyToOne()
	@JoinColumn(name = "CREATED_BY", insertable = true, updatable = true)
	@Access(AccessType.PROPERTY)
	public User ModificationInfo.getCreatedBy() {
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
	public User ModificationInfo.getModifiedBy() {
		return this.modifiedBy;
	}
	
	public void ModificationInfo.setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	
	public void ModificationInfo.setChangeModification(Boolean changeModification) {
		this.changeModification = changeModification;
	}
	
	public void ModificationInfo.preSave(){}
	public void ModificationInfo.preUpdate(){}
	
	public void ModificationInfo.setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	
	public void ModificationInfo.setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	public void ModificationInfo.setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
