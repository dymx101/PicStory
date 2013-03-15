package com.towne.framework.springmvc.model;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "moments")
public class Moments implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1233898340811709177L;

	private String tname = "towne_TAG";

	private Set<MomentV> moments;

	public Set<MomentV> getMoments() {
		return moments;
	}

	@XmlElement
	public void setMoments(Set<MomentV> moments) {
		this.moments = moments;
	}

	/**
	 * @return the tname
	 */
	public String getTname() {
		return tname;
	}

	/**
	 * @param tname
	 *            the tname to set
	 */
	@XmlElement
	public void setTname(String tname) {
		this.tname = tname;
	}
}
