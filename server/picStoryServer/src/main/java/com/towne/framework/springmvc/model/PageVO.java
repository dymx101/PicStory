package com.towne.framework.springmvc.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "page")
public class PageVO implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6928999677121182481L;
	private String MediaUrl;
	private int MediaType;
	private String Content;

	public String getMediaUrl() {
		return MediaUrl;
	}

	@XmlElement
	public void setMediaUrl(String mediaUrl) {
		MediaUrl = mediaUrl;
	}

	public int getMediaType() {
		return MediaType;
	}

	@XmlElement
	public void setMediaType(int mediaType) {
		MediaType = mediaType;
	}

	public String getContent() {
		return Content;
	}

	@XmlElement
	public void setContent(String content) {
		Content = content;
	}
}
