package com.smapley.powerwork.db.entity;


import com.smapley.powerwork.mode.BaseMode;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "Message")
public class MessageEntity implements BaseMode {

	@Column(name = "mes_id",isId = true,autoGen = false)
	private int mes_id;
	@Column(name = "use_id")
	private int use_id;
	@Column(name = "src_use_id")
	private int src_use_id;
	@Column(name = "type")
	private int type;
	@Column(name = "details")
	private String details;
	@Column(name = "pro_id")
	private int pro_id;
	@Column(name = "tas_id")
	private int tas_id;
	@Column(name = "cre_date")
	private long cre_date;
	@Column(name = "refresh")
	private long refresh;
	@Column(name = "state")
	private int state;


	public int getMes_id() {
		return mes_id;
	}

	public void setMes_id(int mes_id) {
		this.mes_id = mes_id;
	}

	public int getUse_id() {
		return use_id;
	}

	public void setUse_id(int use_id) {
		this.use_id = use_id;
	}

	public int getSrc_use_id() {
		return src_use_id;
	}

	public void setSrc_use_id(int src_use_id) {
		this.src_use_id = src_use_id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getPro_id() {
		return pro_id;
	}

	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}

	public int getTas_id() {
		return tas_id;
	}

	public void setTas_id(int tas_id) {
		this.tas_id = tas_id;
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

}
