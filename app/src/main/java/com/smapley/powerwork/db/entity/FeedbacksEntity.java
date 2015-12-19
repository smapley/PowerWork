package com.smapley.powerwork.db.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "Feedbacks")
public class FeedbacksEntity {

	@Column(name = "fee_id",isId = true,autoGen = false)
	private int fee_id;
	@Column(name = "use_id")
	private int use_id;
	@Column(name = "details")
	private String details;
	@Column(name = "cre_date")
	private long cre_date;
	@Column(name = "refresh")
	private long refresh;
	@Column(name = "state")
	private int state;



	public int getFee_id() {
		return fee_id;
	}

	public void setFee_id(int fee_id) {
		this.fee_id = fee_id;
	}

	public int getUse_id() {
		return use_id;
	}

	public void setUse_id(int use_id) {
		this.use_id = use_id;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
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
