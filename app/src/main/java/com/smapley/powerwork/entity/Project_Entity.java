package com.smapley.powerwork.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.sql.Timestamp;

@Table(name = "project")
public class Project_Entity {

	@Column(name = "pro_id" ,isId = true,autoGen = false)
	private int pro_id;
	@Column(name = "use_id")
	private int use_id;
	@Column(name = "name")
	private String name;
	@Column(name = "pic_url")
	private String pic_url;
	@Column(name = "cre_date")
	private Timestamp cre_date;
	
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
	public Timestamp getCre_date() {
		return cre_date;
	}
	public void setCre_date(Timestamp cre_date) {
		this.cre_date = cre_date;
	}
	
	
}
