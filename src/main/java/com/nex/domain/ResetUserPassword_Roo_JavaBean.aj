// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.nex.domain;

import com.nex.domain.ResetUserPassword;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

privileged aspect ResetUserPassword_Roo_JavaBean {
    
    public boolean ResetUserPassword.isTokenReset() {
        return this.tokenReset;
    }
    
    public void ResetUserPassword.setTokenReset(boolean tokenReset) {
        this.tokenReset = tokenReset;
    }
    
    public ShaPasswordEncoder ResetUserPassword.getEncoder() {
        return this.encoder;
    }
    
    public void ResetUserPassword.setEncoder(ShaPasswordEncoder encoder) {
        this.encoder = encoder;
    }
    
    public String ResetUserPassword.getSalt() {
        return this.salt;
    }
    
    public void ResetUserPassword.setSalt(String salt) {
        this.salt = salt;
    }
    
    public Long ResetUserPassword.getId() {
        return this.id;
    }
    
    public void ResetUserPassword.setId(Long id) {
        this.id = id;
    }
    
    public String ResetUserPassword.getPassword() {
        return this.password;
    }
    
    public void ResetUserPassword.setPassword(String password) {
        this.password = password;
    }
    
    public String ResetUserPassword.getOldPassword() {
        return this.oldPassword;
    }
    
    public void ResetUserPassword.setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    
    public String ResetUserPassword.getMatchPassword() {
        return this.matchPassword;
    }
    
    public void ResetUserPassword.setMatchPassword(String matchPassword) {
        this.matchPassword = matchPassword;
    }
    
}