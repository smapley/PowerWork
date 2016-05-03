package com.smapley.powerwork.db.entity;

import com.smapley.powerwork.mode.BaseMode;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "Project")
public class ProjectEntity implements BaseMode{

	@Column(name = "pro_id" ,isId = true,autoGen = false)
	private int pro_id;
	@Column(name = "name")
	private String name;
	@Column(name = "pic_url")
	private String pic_url;
	@Column(name = "cre_date")
	private long cre_date;
	@Column(name = "refresh")
	private long refresh;
	@Column(name = "state")
	private int state;

	private final int type = 1;


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

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
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
