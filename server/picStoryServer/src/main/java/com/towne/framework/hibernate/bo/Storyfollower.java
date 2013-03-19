package com.towne.framework.hibernate.bo;

import java.io.Serializable;
import java.sql.Timestamp;

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
@Table(name = "STORYFOLLOWER")
public class Storyfollower implements Serializable {
	private long idSTORYFOLLOWER;
	private long UserId;
	private Timestamp FollowTime;
	private Story story;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getIdSTORYFOLLOWER() {
		return idSTORYFOLLOWER;
	}

	public void setIdSTORYFOLLOWER(long idSTORYFOLLOWER) {
		this.idSTORYFOLLOWER = idSTORYFOLLOWER;
	}

	public long getUserId() {
		return UserId;
	}

	public void setUserId(long userId) {
		UserId = userId;
	}

	public Timestamp getFollowTime() {
		return FollowTime;
	}
 
	public void setFollowTime(Timestamp followTime) {
		FollowTime = followTime;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="STORY_idSTORY")
	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

}
