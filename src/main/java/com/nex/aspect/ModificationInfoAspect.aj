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

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nex.annotation.Modifiable;
import com.nex.domain.User;


public aspect ModificationInfoAspect {
	
	public interface ModificationInfo{}
	
	@Transient
	@XmlTransient
	private User ModificationInfo.createdBy;
	@Transient
	@XmlTransient
	private User ModificationInfo.modifiedBy;
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
		User cl = this.getCurrentUser();
		this.setCreatedBy(cl);
		this.setModifiedOn(new Date());
		this.setModifiedBy(cl);
	}

	@PreUpdate
	public void ModificationInfo.modifyWhenUpdating() {
		this.setModifiedOn(new Date());
		this.setModifiedBy(this.getCurrentUser());
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public User ModificationInfo.getCurrentUser() {
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		return User.findUsersByEmail(user.getName()).getSingleResult();
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
