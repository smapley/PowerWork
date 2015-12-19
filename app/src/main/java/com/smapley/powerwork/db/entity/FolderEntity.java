package com.smapley.powerwork.db.entity;

import com.smapley.powerwork.mode.BaseMode;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "Folder")
public class FolderEntity implements BaseMode{

	@Column(name = "fol_id",isId = true,autoGen = false)
	private int fol_id;
	@Column(name = "name")
	private String name;
	@Column(name = "pro_id")
	private int pro_id;
	@Column(name = "fol_id2")
	private int fol_id2;
	@Column(name = "use_id")
	private int use_id;
	@Column(name = "refresh")
	private long refresh;
	@Column(name = "state")
	private int state;

	private final int type=0;

	private  boolean isBack=false;

	public boolean isBack() {
		return isBack;
	}

	public void setIsBack(boolean isBack) {
		this.isBack = isBack;
	}

	public int getFol_id() {
		return fol_id;
	}

	public void setFol_id(int fol_id) {
		this.fol_id = fol_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPro_id() {
		return pro_id;
	}

	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}

	public int getFol_id2() {
		return fol_id2;
	}

	public void setFol_id2(int fol_id2) {
		this.fol_id2 = fol_id2;
	}

	public int getUse_id() {
		return use_id;
	}

	public void setUse_id(int use_id) {
		this.use_id = use_id;
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
