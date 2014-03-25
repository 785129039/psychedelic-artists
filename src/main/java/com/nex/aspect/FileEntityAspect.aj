package com.nex.aspect;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PreRemove;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nex.domain.Genre;
import com.nex.domain.Tag;
import com.nex.domain.Type;
import com.nex.domain.User;
import com.nex.domain.common.FileEntity;
import com.nex.io.FileFactory;




public aspect FileEntityAspect {
	
	
//	@Transient
//	private MultipartFile FileEntity.file;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long FileEntity.id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User FileEntity.user;
	
	@Transient
	private Set<String> FileEntity.tagNames = new HashSet<String>();

	@Enumerated(EnumType.STRING)
	private Type FileEntity.type;
	
	@Transient
	@NotEmpty
	private Set<Long> FileEntity.genreIds = new HashSet<Long>();
	
	private String FileEntity.path;
	
	@Transient
	private String FileEntity.prevPath;
	
	public void FileEntity.prepareForSave() {
		for(String s: this.tagNames) {
			this.getTags().add(Tag.findTagsByName(s.trim()).getSingleResult());
		}
		for(Long id: this.genreIds) {
			this.getGenres().add(Genre.findGenre(id));
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
	
	@PostLoad
	public void preLoad() {

	}
	
	public void FileEntity.storeFile(MultipartHttpServletRequest request) {
//		if(!this.file.isEmpty()) {
		String oldpath = this.path;	
			try {
				Map<String, MultipartFile> files = request.getFileMap();
				for(Map.Entry<String, MultipartFile> file: files.entrySet()) {
					MultipartFile mf = file.getValue();
					this.path = mf.getOriginalFilename();
					FileFactory factory = new FileFactory(getFullPath(), this.path);
					factory.saveFileAsStream(mf.getInputStream());
				}
				if(oldpath!=null && !oldpath.equals(this.path))
				//delete odl file when new file is succesfuly saved
				new FileFactory(getFullPath(), oldpath).deleteFile();
			} catch (IOException e) {
				//when new file is not sucecsfuly saved, then delete saved parts and renname path to oldpath
				new FileFactory(getFullPath(), this.path).deleteFile();
				this.path = oldpath;
				LoggerFactory.getLogger(getClass()).error("", e);
			}
//		}
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
	
	public Set<Long> FileEntity.getGenreIds() {
		List<Genre> genres = this.getGenres();
		for(Genre g: genres) {
			this.genreIds.add(g.getId());
		}
		return this.genreIds;
	}
	
	public void FileEntity.setGenreIds(Set<Long> genreIds) {
		 this.genreIds = genreIds;
		 this.getGenres().clear();
	}
	
	@PreRemove
	public void FileEntity.deleteFile() {
		new FileFactory(getFullPath(), this.path).deleteFile();
	}

	private String FileEntity.getFullPath() {
		return this.getServerPath() +getType().name().toLowerCase() + "\\" + this.user.getEmail() + "\\"+this.id+"\\";
	}
	
	public User FileEntity.getUser() {
		return this.user;
	}
	public void FileEntity.setUser(User user) {
		this.user = user;
	}
	
	public Type FileEntity.getType() {
		return this.type;
	}
	
	public void FileEntity.setType(Type type) {
		this.type = type;
	}
}
