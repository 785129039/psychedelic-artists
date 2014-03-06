package com.nex.domain.common;

import java.io.Serializable;

public interface Entity extends Serializable {

	Object getId();
	Entity merge();
	void persist();
	void flush();
	void remove();
	
}
