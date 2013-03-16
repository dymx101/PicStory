package com.towne.framework.springmvc.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "moment")
public class MomentVO  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7506470262811170546L;
	private int pMonIndex;
	private String pMonDesc;
	private List<PageVO> pages;

	public int getpMonIndex() {
		return pMonIndex;
	}

	@XmlElement
	public void setpMonIndex(int pMonIndex) {
		this.pMonIndex = pMonIndex;
	}

	public String getpMonDesc() {
		return pMonDesc;
	}

	@XmlElement
	public void setpMonDesc(String pMonDesc) {
		this.pMonDesc = pMonDesc;
	}

	public List<PageVO> getPages() {
		return pages;
	}

	@XmlElement
	public void setPages(List<PageVO> pages) {
		this.pages = pages;
	}
}
