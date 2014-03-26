package com.nex.domain;

import javax.annotation.Resource;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import com.nex.utils.StringUtils;

@RooJavaBean
@RooJpaActiveRecord(finders="findRegistrationUsersByEmail", persistenceUnit = "puPsyartists", table = "user", versionField = "")
public class ResetUserPassword {
	
	@Resource(name = "passwordEncoder")
	@Transient
	private ShaPasswordEncoder encoder;

	@Value("${sha.salt}")
	@Transient
	private String salt;
	
	@Id
	private Long id;
	
	@NotNull
	@NotBlank
	private String password;

	@Transient
	@NotNull
	@NotBlank
	private String oldPassword;
	
	@NotNull
	@NotBlank
	@Transient
	private String matchPassword;
	
	@PreUpdate
	public void hashPassword() {
		this.password = this.encoder.encodePassword(this.password, this.salt);
		this.matchPassword = this.encoder.encodePassword(this.matchPassword, this.salt);
	}
	
	@AssertTrue(message = "{validation.password.oldpassword.notsame}")
	public Boolean isBadOldPassword() {
		if(StringUtils.isEmpty(this.oldPassword)) return Boolean.TRUE;
		RegistrationUser user = RegistrationUser.findRegistrationUser(id);
		String hashed = this.encoder.encodePassword(this.oldPassword, this.salt);
		return hashed.equals(user.getPassword());
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
