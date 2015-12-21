package com.smapley.powerwork.db.modes;

import com.smapley.powerwork.db.entity.ProjectEntity;

import java.util.List;

public class ProjectMode {

	private ProjectEntity projectEntity;

	private List<ProUseMode> listProUseModes;





	public ProjectEntity getProjectEntity() {
		return projectEntity;
	}



	public void setProjectEntity(ProjectEntity projectEntity) {
		this.projectEntity = projectEntity;
	}



	public List<ProUseMode> getListProUseModes() {
		return listProUseModes;
	}

	public void setListProUseModes(List<ProUseMode> listProUseModes) {
		this.listProUseModes = listProUseModes;
	}

}
