package model;

import java.io.Serializable;

public class Textlog implements Serializable  {

	private String name;
	private String radio;
	private String text;
	private String mail;

	public Textlog() {}

	public Textlog(String name , String radio , String text) {
		this.name = name;
		this.radio = radio;
		this.text = text;
	}
	public Textlog(String name , String radio , String text , String mail) {
		this.name = name;
		this.radio = radio;
		this.text = text;
		this.mail = mail;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
