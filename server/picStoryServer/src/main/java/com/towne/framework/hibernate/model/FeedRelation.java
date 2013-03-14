package com.towne.framework.hibernate.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@SuppressWarnings("serial")
@Entity
@Table(name = "FEEDRELATION")
public class FeedRelation implements Serializable {
	private long idFEEDRELATION;
	private long ReferUserId; // 引用消息用户编号
	private long ReferFeedId; // 引用消息编号
	private long ReferedUserId; // 消息发布者编号
	private long ReferedFeedId; // 消息编号
	private int Type; // 操作类型(1,评论；2，转发)
	private Timestamp TimeL; // 时间戳,发布时间
	private Commend commend;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getIdFEEDRELATION() {
		return idFEEDRELATION;
	}

	public void setIdFEEDRELATION(long idFEEDRELATION) {
		this.idFEEDRELATION = idFEEDRELATION;
	}

	public long getReferUserId() {
		return ReferUserId;
	}

	public void setReferUserId(long referUserId) {
		ReferUserId = referUserId;
	}

	public long getReferFeedId() {
		return ReferFeedId;
	}

	public void setReferFeedId(long referFeedId) {
		ReferFeedId = referFeedId;
	}

	public long getReferedUserId() {
		return ReferedUserId;
	}

	public void setReferedUserId(long referedUserId) {
		ReferedUserId = referedUserId;
	}

	public long getReferedFeedId() {
		return ReferedFeedId;
	}

	public void setReferedFeedId(long referedFeedId) {
		ReferedFeedId = referedFeedId;
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

	@OneToOne(mappedBy="feedrelation",fetch=FetchType.LAZY)
	@Cascade(value={CascadeType.ALL})
	public Commend getCommend() {
		return commend;
	}

	public void setCommend(Commend commend) {
		this.commend = commend;
	}

}
