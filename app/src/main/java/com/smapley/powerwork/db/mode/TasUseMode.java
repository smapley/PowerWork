package com.smapley.powerwork.db.mode;


import com.smapley.powerwork.db.entity.TasUseEntity;
import com.smapley.powerwork.db.entity.UserEntity;

public class TasUseMode {

	private TasUseEntity tasUseEntity;

	private UserEntity userEntity;

	

	public TasUseEntity getTasUseEntity() {
		return tasUseEntity;
	}

	public void setTasUseEntity(TasUseEntity tasUseEntity) {
		this.tasUseEntity = tasUseEntity;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
	
}
