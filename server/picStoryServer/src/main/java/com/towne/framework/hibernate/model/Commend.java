package com.towne.framework.hibernate.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@SuppressWarnings("serial")
@Entity
@Table(name = "COMMEND")
public class Commend implements Serializable {

	private long idCOMMEND;
	private String CommendText;
	private FeedRelation feedrelation;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getIdCOMMEND() {
		return idCOMMEND;
	}

	public void setIdCOMMEND(long idCOMMEND) {
		this.idCOMMEND = idCOMMEND;
	}

	public String getCommendText() {
		return CommendText;
	}

	public void setCommendText(String commendText) {
		CommendText = commendText;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FEEDRELATION_idFEEDRELATION", insertable = true, unique = true)
	public FeedRelation getFeedrelation() {
		return feedrelation;
	}

	public void setFeedrelation(FeedRelation feedrelation) {
		this.feedrelation = feedrelation;
	}
}
