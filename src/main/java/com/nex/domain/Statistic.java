package com.nex.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

import com.nex.annotation.Logger;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "puPsyartists", table = "record_statistic", versionField = "")
@Logger
public class Statistic {
	
	public static final Integer MAX_RATING = 5;
	
	@Id
    @Column(name="record_id", unique=true, nullable=false)
    @GeneratedValue(generator="gen")
    @GenericGenerator(name="gen", strategy="foreign", parameters=@Parameter(name="property", value="record"))
	private Long id;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private Record record;
	
	private Long downloads = new Long(0);
	private Long ratingCount = new Long(0);
	private Long ratingSum = new Long(0);
	private Double ratingPercent = new Double(0);
	
	public void setRating(Integer grade) {
		this.ratingCount++;
		this.ratingSum += grade;
		this.ratingPercent = ((((double)this.ratingSum /(double) this.ratingCount) / MAX_RATING ) * 100);
	}
	public Integer getRating() {
		if(this.ratingCount > 0 && this.ratingSum > 0) {
			return (int) Math.ceil(this.ratingSum/this.ratingCount);
		}
		return 0;
	}	
	
	public void addDownload() {
		this.downloads++;
	}
}
