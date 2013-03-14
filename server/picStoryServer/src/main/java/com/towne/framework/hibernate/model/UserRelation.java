package com.towne.framework.hibernate.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "USERELATION")
public class UserRelation implements Serializable {
	private long idUSERELATION;
	private long UserId;
	private long FollowerId;
	private int type;	// (关系类型0粉1关)

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getIdUSERELATION() {
		return idUSERELATION;
	}

	public void setIdUSERELATION(long idUSERELATION) {
		this.idUSERELATION = idUSERELATION;
	}

	public long getUserId() {
		return UserId;
	}

	public void setUserId(long userId) {
		UserId = userId;
	}

	public long getFollowerId() {
		return FollowerId;
	}

	public void setFollowerId(long followerId) {
		FollowerId = followerId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
