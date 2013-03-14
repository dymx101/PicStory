package com.towne.framework.hibernate.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "PAGE")
public class Page implements Serializable {
	private long idPAGE;
	private String MediaUrl;
	private int MediaType;
	private String Content;
	private Moment moment;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getIdPAGE() {
		return idPAGE;
	}

	public void setIdPAGE(long idPAGE) {
		this.idPAGE = idPAGE;
	}

	public String getMediaUrl() {
		return MediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		MediaUrl = mediaUrl;
	}

	public int getMediaType() {
		return MediaType;
	}

	public void setMediaType(int mediaType) {
		MediaType = mediaType;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Moment_idMoment")
	public Moment getMoment() {
		return moment;
	}

	public void setMoment(Moment moment) {
		this.moment = moment;
	}
}
