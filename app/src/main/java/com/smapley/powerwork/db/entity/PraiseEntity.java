package com.smapley.powerwork.db.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "Praise")
public class PraiseEntity {

	@Column(name = "pra_id",isId = true,autoGen = false)
	private int pra_id;
	@Column(name = "use_id")
	private int use_id;
	@Column(name = "dyn_id")
	private int dyn_id;
	@Column(name = "refresh")
	private long refresh;
	@Column(name = "state")
	private int state;
	

	
	public int getPra_id() {
		return pra_id;
	}
	public void setPra_id(int pra_id) {
		this.pra_id = pra_id;
	}
	public int getUse_id() {
		return use_id;
	}
	public void setUse_id(int use_id) {
		this.use_id = use_id;
	}
	public int getDyn_id() {
		return dyn_id;
	}
	public void setDyn_id(int dyn_id) {
		this.dyn_id = dyn_id;
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
