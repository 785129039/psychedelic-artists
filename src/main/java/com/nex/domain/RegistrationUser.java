package com.nex.domain;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nex.domain.common.Entity;
import com.nex.utils.StringUtils;

@RooJavaBean
@RooJpaActiveRecord(finders="findRegistrationUsersByEmail", persistenceUnit = "puPsyartists", table = "user", versionField = "")
public class RegistrationUser implements Entity {
	
	@Resource(name = "passwordEncoder")
	@Transient
	private ShaPasswordEncoder encoder;

	@Value("${sha.salt}")
	@Transient
	private String salt;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@NotBlank
	@Email
	private String email;

	@NotNull
	@NotBlank
	private String name;
	
	@NotNull
	@NotBlank
	private String password;

	@NotNull
	@NotBlank
	@Transient
	private String matchPassword;

	private Boolean enabled = Boolean.TRUE;
	
	@OneToMany
	@JoinTable(name = "rights", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private List<Role> roles;

	@PrePersist
	public void hashPassword() {
		this.password = this.encoder.encodePassword(this.password, this.salt);
		this.matchPassword = this.encoder.encodePassword(this.matchPassword, this.salt);
	}
	
	@AssertFalse(message = "{validation.user.exist}")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Boolean isUserExist() {
		if(StringUtils.isEmpty(this.email )) return Boolean.FALSE;
		List<RegistrationUser> users = RegistrationUser.findRegistrationUsersByEmail(this.email).getResultList();
		if(users != null && !users.isEmpty()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	@AssertTrue(message = "{validation.password.notmatch}")
	public Boolean isPasswordsMatch() {
		if (!StringUtils.isEmpty(this.password)
				&& !StringUtils.isEmpty(this.matchPassword)) {
			if (this.password.equals(this.matchPassword)) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

}
