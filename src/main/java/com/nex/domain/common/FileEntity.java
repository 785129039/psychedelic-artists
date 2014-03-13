package com.nex.domain.common;

import java.util.List;

import com.nex.domain.Genre;
import com.nex.domain.Tag;

public interface FileEntity extends Entity {
	
	List<Tag> getTags();
	List<Genre> getGenres();
	String getServerPath();
	
}
