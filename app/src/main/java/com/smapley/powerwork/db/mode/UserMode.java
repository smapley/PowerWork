package com.smapley.powerwork.db.mode;

import com.smapley.powerwork.db.entity.UserEntity;

import java.util.List;


public class UserMode {
	private UserEntity userEntity;
    
    private List<NoteMode> listNoteModes;
    private List<ProjectMode> listProjectModes;
    

  
	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public List<NoteMode> getListNoteModes() {
		return listNoteModes;
	}

	public void setListNoteModes(List<NoteMode> listNoteModes) {
		this.listNoteModes = listNoteModes;
	}

	public List<ProjectMode> getListProjectModes() {
		return listProjectModes;
	}

	public void setListProjectModes(List<ProjectMode> listProjectModes) {
		this.listProjectModes = listProjectModes;
	}
	
	
}
