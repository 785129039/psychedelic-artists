// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.nex.domain;

import com.nex.domain.Sample;
import com.nex.domain.SampleComment;

privileged aspect SampleComment_Roo_JavaBean {
    
    public Long SampleComment.getId() {
        return this.id;
    }
    
    public void SampleComment.setId(Long id) {
        this.id = id;
    }
    
    public String SampleComment.getUsername() {
        return this.username;
    }
    
    public void SampleComment.setUsername(String username) {
        this.username = username;
    }
    
    public String SampleComment.getComment() {
        return this.comment;
    }
    
    public void SampleComment.setComment(String comment) {
        this.comment = comment;
    }
    
    public Sample SampleComment.getSample() {
        return this.sample;
    }
    
    public void SampleComment.setSample(Sample sample) {
        this.sample = sample;
    }
    
}
