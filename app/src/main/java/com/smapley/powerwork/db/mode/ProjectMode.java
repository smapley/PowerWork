package com.smapley.powerwork.db.mode;

import com.smapley.powerwork.db.entity.ProjectEntity;

import java.util.List;

public class ProjectMode {

	private ProjectEntity projectEntity;
	
	private List<ProUseMode> listProUseModes;
	private List<TaskMode> listTaskModes;
	private List<DynamicMode> listDynamicModes;
	private List<FolderMode> listFolderModes;
	


	

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

	public List<TaskMode> getListTaskModes() {
		return listTaskModes;
	}

	public void setListTaskModes(List<TaskMode> listTaskModes) {
		this.listTaskModes = listTaskModes;
	}

	public List<DynamicMode> getListDynamicModes() {
		return listDynamicModes;
	}

	public void setListDynamicModes(List<DynamicMode> listDynamicModes) {
		this.listDynamicModes = listDynamicModes;
	}

	public List<FolderMode> getListFolderModes() {
		return listFolderModes;
	}

	public void setListFolderModes(List<FolderMode> listFolderModes) {
		this.listFolderModes = listFolderModes;
	}
}
