package com.towne.framework.springmvc.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="contacts")
public class Contacts {
	
	private String tname = "towne_test";
	
	private List<Contact> contacts;

	@XmlElement(name="contact")
	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
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
