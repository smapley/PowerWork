package com.smapley.powerwork.db.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "TasUse", onCreated = "CREATE UNIQUE INDEX index_task ON TasUse(use_id,tas_id,rank)")
public class TasUseEntity {

	@Column(name = "id",isId = true)
	private int id;
	@Column(name = "use_id")
	private int use_id;
	@Column(name = "tas_id")
	private int tas_id;
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

	public int getUse_id() {
		return use_id;
	}

	public void setUse_id(int use_id) {
		this.use_id = use_id;
	}

	public int getTas_id() {
		return tas_id;
	}

	public void setTas_id(int tas_id) {
		this.tas_id = tas_id;
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
