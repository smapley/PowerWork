package com.smapley.powerwork.db.entity;

import com.smapley.powerwork.mode.BaseMode;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "Task")
public class TaskEntity implements BaseMode{
	@Column(name = "tas_id",isId = true,autoGen = false)
	private int tas_id;
	@Column(name = "pro_id")
	private int pro_id;
	@Column(name = "name")
	private String name;
	@Column(name = "sta_date")
	private long sta_date;
	@Column(name = "end_date")
	private long end_date;
	@Column(name = "progress")
	private int progress;
	@Column(name = "priority")
	private int priority;
	@Column(name = "cre_date")
	private long cre_date;
	@Column(name = "refresh")
	private long refresh;
	@Column(name = "state")
	private int state;

	private int type=0;



	public int getTas_id() {
		return tas_id;
	}

	public void setTas_id(int tas_id) {
		this.tas_id = tas_id;
	}

	public int getPro_id() {
		return pro_id;
	}

	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSta_date() {
		return sta_date;
	}

	public void setSta_date(long sta_date) {
		this.sta_date = sta_date;
	}

	public long getEnd_date() {
		return end_date;
	}

	public void setEnd_date(long end_date) {
		this.end_date = end_date;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public long getCre_date() {
		return cre_date;
	}

	public void setCre_date(long cre_date) {
		this.cre_date = cre_date;
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


	@Override
	public int getType() {
		return type;
	}
}
