package com.smapley.powerwork.db.entity;

import com.smapley.powerwork.mode.BaseMode;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "TaskDetails")
public class TaskDetailsEntity implements BaseMode {

	@Column(name = "det_id",isId = true,autoGen = false)
	private int det_id;
	@Column(name = "tas_id")
	private int tas_id;
	@Column(name = "type")
	private int type;
	@Column(name = "text")
	private String text;
	@Column(name = "path")
	private String path;
	@Column(name = "length")
	private long length;
	@Column(name = "refresh")
	private long refresh;
	@Column(name = "state")
	private int state;

	public TaskDetailsEntity(int type){
		this.type=type;
	}

	public TaskDetailsEntity(){

	}

	public int getDet_id() {
		return det_id;
	}

	public void setDet_id(int det_id) {
		this.det_id = det_id;
	}

	

	public int getTas_id() {
		return tas_id;
	}

	public void setTas_id(int tas_id) {
		this.tas_id = tas_id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
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
