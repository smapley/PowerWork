package com.smapley.powerwork.db.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "ProUser", onCreated = "CREATE UNIQUE INDEX index_project ON ProUser(use_id,pro_id)")
public class ProUseEntity {

	@Column(name = "id", isId = true,autoGen = true)
	private int id;
	@Column(name = "use_id")
	private int use_id;
	@Column(name = "pro_id")
	private int pro_id;
	@Column(name = "rank")
	private int rank;
	@Column(name = "refresh")
	private long refresh;
	@Column(name = "state")
	private int state;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPro_id() {
		return pro_id;
	}

	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}


	public int getUse_id() {
		return use_id;
	}

	public void setUse_id(int use_id) {
		this.use_id = use_id;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
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
