package com.geospot.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class LvlMap {
	
	@Id
	private int LvlMapId;
	
	private String lvl1Category;
	
	private String lvl2Category;

	public int getLvlMapId() {
		return LvlMapId;
	}

	public void setLvlMapId(int lvlMapId) {
		LvlMapId = lvlMapId;
	}

	public String getLvl1Category() {
		return lvl1Category;
	}

	public void setLvl1Category(String lvl1Category) {
		this.lvl1Category = lvl1Category;
	}

	public String getLvl2Category() {
		return lvl2Category;
	}

	public void setLvl2Category(String lvl2Category) {
		this.lvl2Category = lvl2Category;
	}
	
}
