package com.smapley.powerwork.db.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name="Discuss")
public class DiscussEntity {

	@Column(name = "dis_id",isId = true,autoGen = false)
	private int dis_id;
	@Column(name = "use_id")
	private int use_id;
	@Column(name = "details")
	private String details;
	@Column(name = "dyn_id")
	private int dyn_id;
	@Column(name = "refresh")
	private long refresh;
	@Column(name = "state")
	private int state;
	


	public int getDis_id() {
		return dis_id;
	}
	public void setDis_id(int dis_id) {
		this.dis_id = dis_id;
	}
	public int getUse_id() {
		return use_id;
	}
	public void setUse_id(int use_id) {
		this.use_id = use_id;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
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
