package com.towne.framework.springmvc.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.towne.framework.hibernate.model.Moment;

@XmlRootElement(name="moments")
public class Moments {
	
	private String tname = "towne_test";
	
	private List<Moment> moments;

	@XmlElement(name="moment")
	public List<Moment> getMoments() {
		return moments;
	}

	public void setMoments(List<Moment> moments) {
		this.moments = moments;
	}

	/**
	 * @return the tname
	 */
	public String getTname() {
		return tname;
	}

	/**
	 * @param tname the tname to set
	 */
	@XmlElement
	public void setTname(String tname) {
		this.tname = tname;
	}
}
