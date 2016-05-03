package com.smapley.powerwork.db.mode;

import com.smapley.powerwork.db.entity.DiscussEntity;
import com.smapley.powerwork.db.entity.DynamicEntity;
import com.smapley.powerwork.db.entity.PraiseEntity;

import java.util.List;

public class DynamicMode {

	private DynamicEntity dynamicEntity;
	private List<DiscussEntity> listDiscussEntities;
	private List<PraiseEntity> listPraiseEntities;


	public DynamicEntity getDynamicEntity() {
		return dynamicEntity;
	}

	public void setDynamicEntity(DynamicEntity dynamicEntity) {
		this.dynamicEntity = dynamicEntity;
	}

	public List<DiscussEntity> getListDiscussEntities() {
		return listDiscussEntities;
	}

	public void setListDiscussEntities(List<DiscussEntity> listDiscussEntities) {
		this.listDiscussEntities = listDiscussEntities;
	}

	public List<PraiseEntity> getListPraiseEntities() {
		return listPraiseEntities;
	}

	public void setListPraiseEntities(List<PraiseEntity> listPraiseEntities) {
		this.listPraiseEntities = listPraiseEntities;
	}

	

}
