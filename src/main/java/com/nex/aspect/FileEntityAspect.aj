package com.nex.aspect;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.nex.domain.Tag;
import com.nex.domain.User;
import com.nex.domain.common.FileEntity;
import com.nex.io.FileFactory;



public aspect FileEntityAspect {
	
	
	@Transient
	private MultipartFile FileEntity.file;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long FileEntity.id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User FileEntity.user;
	
	@Transient
	private Set<String> FileEntity.tagNames = new HashSet<String>();

	private String FileEntity.path;
	
	@Transient
	private String FileEntity.prevPath;
	
	public void FileEntity.prepareForSave() {
		if(!this.file.isEmpty()){
			path = file.getOriginalFilename();
		}
		for(String s: this.tagNames) {
			this.getTags().add(Tag.findTagsByName(s.trim()).getSingleResult());
		}
	}
	
	private void FileEntity.storeTags() {
		for(String tagname: this.tagNames) {
			Tag t = null;
			try {
				t = Tag.findTagsByName(tagname.trim()).getSingleResult();
			} catch (Exception e) {
				LoggerFactory.getLogger(getClass()).error(e.getMessage());
			}
			if(t == null) {
				t = new Tag();
				t.setName(tagname);
				t.persist();
				t.flush();
			}
		}
	}
	
	public void FileEntity.storeFile() {
		if(!this.file.isEmpty()) {
			try {
				new FileFactory(getFullPath(), this.prevPath).deleteFile();
				new FileFactory(getFullPath(), this.path).saveFile(file.getBytes());
			} catch (IOException e) {
				LoggerFactory.getLogger(getClass()).error("", e);
			}
		}
	}
	public Set<String> FileEntity.getTagNames() {
		List<Tag> tags = getTags();
		for(Tag tag: tags) {
			this.tagNames.add(tag.getName());
		}
		return tagNames;
	}
	public void FileEntity.setTagNames(Set<String> tagNames) {
		this.tagNames = tagNames;
		this.getTags().clear();
		storeTags();
	}
	public void FileEntity.setFile(MultipartFile file) {
		this.file = file;
		if(!this.file.isEmpty()) {
			this.prevPath = this.path;
			this.path = this.file.getOriginalFilename();
		}
	}
	@PreRemove
	public void FileEntity.deleteFile() {
		new FileFactory(getFullPath(), this.path).deleteFile();
	}

	private String FileEntity.getFullPath() {
		return this.getServerPath() + this.user.getEmail() + "\\"+this.id+"\\";
	}
	
	public User FileEntity.getUser() {
		return this.user;
	}
	public void FileEntity.setUser(User user) {
		this.user = user;
	}

}
