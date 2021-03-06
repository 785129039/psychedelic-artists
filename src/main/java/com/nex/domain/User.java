package com.nex.domain;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import com.nex.domain.common.Entity;

@RooJavaBean
@RooJpaActiveRecord(finders="findUsersByEmail", persistenceUnit = "puPsyartists", table = "user", versionField = "")
public class User implements Entity {
	
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
	@Size(min = 1)
	@Email
	private String email;

	@NotNull
	@Size(min = 1)
	private String name;

	@OneToMany
	@JoinTable(name = "rights", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private List<Role> roles;
	
	@JsonIgnore
	public ShaPasswordEncoder getEncoder() {
		return encoder;
	}
	@JsonIgnore
	public String getSalt() {
		return salt;
	}
	@JsonIgnore
	public String getEmail() {
		return email;
	}
	@JsonIgnore
	public List<Role> getRoles() {
		return roles;
	}

	
	
}
