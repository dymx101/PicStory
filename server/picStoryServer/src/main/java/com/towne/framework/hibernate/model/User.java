package com.towne.framework.hibernate.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@SuppressWarnings("serial")
@Entity
@Table(name = "USER")
public class User implements Serializable {
	private long idUSER;
	private String UserName;
	private String UdId;
	private String UserPass;
	private int FeedsCount;
	private int FansCount;
	private int FollowsCount;
	private Set<Feed> feeds;
	private Set<Story> storys;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getIdUSER() {
		return idUSER;
	}

	public void setIdUSER(long idUSER) {
		this.idUSER = idUSER;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserPass() {
		return UserPass;
	}

	public void setUserPass(String userPass) {
		UserPass = userPass;
	}

	public int getFeedsCount() {
		return FeedsCount;
	}

	public void setFeedsCount(int feedsCount) {
		FeedsCount = feedsCount;
	}

	public int getFollowsCount() {
		return FollowsCount;
	}

	public void setFollowsCount(int followsCount) {
		FollowsCount = followsCount;
	}

	public int getFansCount() {
		return FansCount;
	}

	public String getUdId() {
		return UdId;
	}

	public void setUdId(String udId) {
		UdId = udId;
	}

	public void setFansCount(int fansCount) {
		FansCount = fansCount;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL })
	public Set<Feed> getFeeds() {
		return feeds;
	}

	public void setFeeds(Set<Feed> feeds) {
		this.feeds = feeds;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL })
	public Set<Story> getStorys() {
		return storys;
	}

	public void setStorys(Set<Story> storys) {
		this.storys = storys;
	}

}
