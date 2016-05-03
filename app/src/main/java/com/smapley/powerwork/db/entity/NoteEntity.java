package com.smapley.powerwork.db.entity;

import com.smapley.powerwork.mode.BaseMode;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "Note")
public class NoteEntity implements BaseMode{

	@Column(name = "not_id", isId = true, autoGen = false)
	private int not_id;
	@Column(name = "use_id")
	private int use_id;
	@Column(name = "alarm")
	private long alarm;
	@Column(name = "name")
	private String name;
	@Column(name = "cre_date")
	private long cre_date;
	@Column(name = "refresh")
	private long refresh;
	@Column(name = "state")
	private int state;

	private final int type=3;


	public int getNot_id() {
		return not_id;
	}

	public void setNot_id(int not_id) {
		this.not_id = not_id;
	}

	public int getUse_id() {
		return use_id;
	}

	public void setUse_id(int use_id) {
		this.use_id = use_id;
	}

	public long getAlarm() {
		return alarm;
	}

	public void setAlarm(long alarm) {
		this.alarm = alarm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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