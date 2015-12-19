package com.smapley.powerwork.db.mode;


import com.smapley.powerwork.db.entity.ProUseEntity;
import com.smapley.powerwork.db.entity.UserEntity;

public class ProUseMode {

	private ProUseEntity proUseEntity;
	
	private UserEntity userEntity;


	
	public ProUseEntity getProUseEntity() {
		return proUseEntity;
	}


	public void setProUseEntity(ProUseEntity proUseEntity) {
		this.proUseEntity = proUseEntity;
	}


	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

}
