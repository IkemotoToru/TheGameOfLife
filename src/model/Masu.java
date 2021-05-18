package model;

import java.io.Serializable;

public class Masu implements Serializable {

	private String masu_id;
	private String masu_content;
	private int masu_money;
	private String masu_bunrui;
	private String masu_image;

	public Masu () {}

	public Masu (String masu_id , String masu_content , int masu_money , String masu_bunrui , String masu_image) {
		this.masu_id = masu_id;
		this.masu_content = masu_content;
		this.masu_money = masu_money;
		this.masu_bunrui = masu_bunrui;
		this.masu_image = masu_image;
	}

	public String getMasu_image() {
		return masu_image;
	}

	public void setMasu_image(String masu_image) {
		this.masu_image = masu_image;
	}

	public String getMasu_id() {
		return masu_id;
	}

	public void setMasu_id(String masu_id) {
		this.masu_id = masu_id;
	}

	public String getMasu_content() {
		return masu_content;
	}

	public void setMasu_content(String masu_content) {
		this.masu_content = masu_content;
	}

	public int getMasu_money() {
		return masu_money;
	}

	public void setMasu_money(int masu_money) {
		this.masu_money = masu_money;
	}

	public String getMasu_bunrui() {
		return masu_bunrui;
	}

	public void setMasu_bunrui(String masu_bunrui) {
		this.masu_bunrui = masu_bunrui;
	}

}
