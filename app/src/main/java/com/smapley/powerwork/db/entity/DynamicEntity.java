package com.smapley.powerwork.db.entity;

import com.smapley.powerwork.mode.BaseMode;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by smapley on 15/11/27.
 */
@Table(name = "Dynamic")
public class DynamicEntity implements BaseMode{

	@Column(name = "dyn_id",isId = true,autoGen = false)
	private int dyn_id;
	@Column(name = "use_id")
	private int use_id;
	@Column(name = "tas_id")
	private int tas_id;
	@Column(name = "type")
	private int type;
	@Column(name = "pro_id")
	private int pro_id;
	@Column(name = "pic_url")
	private String pic_url;
	@Column(name = "fil_id")
	private int fil_id;
	@Column(name = "refresh")
	private long refresh;
	@Column(name = "state")
	private int state;
	@Column(name = "cre_date")
	private long cre_date;




	public int getDyn_id() {
		return dyn_id;
	}

	public void setDyn_id(int dyn_id) {
		this.dyn_id = dyn_id;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPro_id() {
		return pro_id;
	}

	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public int getFil_id() {
		return fil_id;
	}

	public void setFil_id(int fil_id) {
		this.fil_id = fil_id;
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

	public long getCre_date() {
		return cre_date;
	}

	public void setCre_date(long cre_date) {
		this.cre_date = cre_date;
	}

}
