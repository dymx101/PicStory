package com.towne.framework.hibernate.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@SuppressWarnings("serial")
@Entity
@Table(name = "FEED")
public class Feed implements Serializable {

	private long idFEEDS;
	private String UKey;
	private Timestamp TimeLine;
	private User user;
	private Story story;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getIdFEEDS() {
		return idFEEDS;
	}

	public void setIdFEEDS(long idFEEDS) {
		this.idFEEDS = idFEEDS;
	}

	public String getUKey() {
		return UKey;
	}

	public void setUKey(String uKey) {
		UKey = uKey;
	}

	public Timestamp getTimeLine() {
		return TimeLine;
	}

	public void setTimeLine(Timestamp timeLine) {
		TimeLine = timeLine;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USER_idUSER")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToOne(mappedBy="feed",fetch=FetchType.LAZY)
	@Cascade(value={CascadeType.ALL})
	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

}
