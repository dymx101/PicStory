package com.towne.framework.hibernate.bo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@SuppressWarnings("serial")
@Entity
@Table(name = "MOMENT")
public class Moment implements Serializable {
	private long idMOMENT;
	private int pMonIndex;
	private String pMonDesc;
	private Set<Page> pages;
	private Story story;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getIdMOMENT() {
		return idMOMENT;
	}

	public void setIdMOMENT(long idMOMENT) {
		this.idMOMENT = idMOMENT;
	}

	public int getpMonIndex() {
		return pMonIndex;
	}

	
	public void setpMonIndex(int pMonIndex) {
		this.pMonIndex = pMonIndex;
	}

	public String getpMonDesc() {
		return pMonDesc;
	}

	public void setpMonDesc(String pMonDesc) {
		this.pMonDesc = pMonDesc;
	}

	@OneToMany(mappedBy = "moment",fetch=FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL })
	public Set<Page> getPages() {
		return pages;
	}

	public void setPages(Set<Page> pages) {
		this.pages = pages;
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
