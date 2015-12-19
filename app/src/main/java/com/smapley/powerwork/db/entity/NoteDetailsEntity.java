package com.smapley.powerwork.db.entity;

import com.smapley.powerwork.mode.BaseMode;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "NoteDetails")
public class NoteDetailsEntity implements BaseMode {

	@Column(name = "det_id",isId = true,autoGen = false)
	private int det_id;
	@Column(name = "not_id")
	private int not_id;
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

	public NoteDetailsEntity(int type) {
		this.type = type;
	}

	public NoteDetailsEntity(){

	}

	public int getDet_id() {
		return det_id;
	}

	public void setDet_id(int det_id) {
		this.det_id = det_id;
	}


	public int getNot_id() {
		return not_id;
	}

	public void setNot_id(int not_id) {
		this.not_id = not_id;
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
