package com.towne.framework.hibernate.bo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@SuppressWarnings("serial")
@Entity
@Table(name = "STORY")
public class Story implements Serializable {
	private long idSTORY;
	private String StoryCode;
	private String StoryName;
	private String StoryDesc;
	private double Latitude;
	private double Longitude;
	private int TransferrCount; // 转发数量
	private int CommentCount; // 评论数量
	private int Type; // 消息类型（0，原创；1，评论；2，转发）
	private Timestamp TimeL;
	private Set<Moment> moments;
	private Set<Storyfollower> storyfollowers;
	private User user;
	private Feed feed;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getIdSTORY() {
		return idSTORY;
	}

	public void setIdSTORY(long idSTORY) {
		this.idSTORY = idSTORY;
	}

	public String getStoryCode() {
		return StoryCode;
	}

	public void setStoryCode(String storyCode) {
		StoryCode = storyCode;
	}

	public String getStoryName() {
		return StoryName;
	}

	public void setStoryName(String storyName) {
		StoryName = storyName;
	}

	public String getStoryDesc() {
		return StoryDesc;
	}

	public void setStoryDesc(String storyDesc) {
		StoryDesc = storyDesc;
	}

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public double getLongitude() {
		return Longitude;
	}

	public void setLongitude(double longitude) {
		Longitude = longitude;
	}

	public int getTransferrCount() {
		return TransferrCount;
	}

	public void setTransferrCount(int transferrCount) {
		TransferrCount = transferrCount;
	}

	public int getCommentCount() {
		return CommentCount;
	}

	public void setCommentCount(int commentCount) {
		CommentCount = commentCount;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public Timestamp getTimeL() {
		return TimeL;
	}

	public void setTimeL(Timestamp timeL) {
		TimeL = timeL;
	}

	@OneToMany(mappedBy = "story", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL })
	public Set<Moment> getMoments() {
		return moments;
	}

	public void setMoments(Set<Moment> moments) {
		this.moments = moments;
	}

	@OneToMany(mappedBy = "story", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL })
	public Set<Storyfollower> getStoryfollowers() {
		return storyfollowers;
	}

	public void setStoryfollowers(Set<Storyfollower> storyfollowers) {
		this.storyfollowers = storyfollowers;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL })
	@JoinColumn(name = "USER_idUSER")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FEED_idFEED", insertable = true, unique = true)
	public Feed getFeed() {
		return feed;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
	}
}
