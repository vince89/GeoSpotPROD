package com.geospot.entities;

import org.springframework.data.annotation.Id;

public class Tag {
	
	@Id
	private long tagId;
	
	private long tagName;

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public long getTagName() {
		return tagName;
	}

	public void setTagName(long tagName) {
		this.tagName = tagName;
	}

}
