package com.smapley.powerwork.entity;

import com.smapley.powerwork.mode.BaseMode;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "project")
public class ProjectEntity implements BaseMode{

	private final int type = 1;

	@Column(name = "pro_id" ,isId = true,autoGen = false)
	private int pro_id;
	@Column(name = "use_id")
	private int use_id;
	@Column(name = "name")
	private String name;
	@Column(name = "pic_url")
	private String pic_url;
	@Column(name = "cre_date")
	private long cre_date;
	@Column(name = "total")
	private int total;


	
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public int getType() {
		return type;
	}
}
