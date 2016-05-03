package com.smapley.powerwork.db.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "userBase")
public class UserBaseEntity {
	@Column(name = "useId",isId = true,autoGen = false)
	private int useId;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "skey")
	private String skey;
	@Column(name = "refresh")
	private long refresh;
	@Column(name = "state")
	private int state;


	public int getUseId() {
		return useId;
	}

	public void setUseId(int useId) {
		this.useId = useId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSkey() {
		return skey;
	}

	public void setSkey(String skey) {
		this.skey = skey;
	}

	public long getRefresh() {
		return refresh;
	}

	public void setRefresh(long refresh) {
		this.refresh = refresh;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}


}
