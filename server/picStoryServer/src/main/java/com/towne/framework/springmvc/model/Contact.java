package com.towne.framework.springmvc.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.towne.framework.common.model.Trader;

@Entity
@Table(name="contact",catalog="framework")
@NamedQuery(name="findById",query="select a from Contact a where a.id= ?1")
@XmlRootElement(name="contact")
public class Contact implements Serializable{
	
	private static final long serialVersionUID = -1311420045457626219L;

	private int id;
	
	private String firstname;
	private String lastname;
	
	private String email;
	private String telephone;
	
	private String qq;
	
	private int age;

	private Trader trader;
	
	public Trader getTrader() {
		return trader;
	}

	@XmlElement
	public void setTrader(Trader trader) {
		this.trader = trader;
	}
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}
    
	@XmlElement
	public void setId(int id) {
		this.id = id;
	}

	@NotEmpty(message="firstname is not nil!")
	public String getFirstname() {
		return firstname;
	}

	@XmlElement
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	@NotEmpty(message="lastname is not nil!")
	public String getLastname() {
		return lastname;
	}

	@XmlElement
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Email(message="is not a legitimate e-mail!")
	@NotEmpty(message="e-mail can not be empty !")
	public String getEmail() {
		return email;
	}

	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}
	@NotEmpty(message="phone number can not be empty!")
	@Pattern(regexp="((\\d{3,4})|\\d{3,4}-)?\\d{7,8}",message="phone number only for digital, and can not be more than 11! (010-8805784)")
	public String getTelephone() {
		return telephone;
	}

	@XmlElement
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Range(min=1,max=150,message="only in the age of between 1-150")
	public int getAge() {
		return age;
	}

	@XmlElement
	public void setAge(int age) {
		this.age = age;
	}

	@NotEmpty(message="QQ can not be empty!")
	@Pattern(regexp="^[1-9]*[1-9][0-9]*$",message="QQ only for digital")
	public String getQq() {
		return qq;
	}

	@XmlElement
	public void setQq(String qq) {
		this.qq = qq;
	}
	
	

}
