package model;

import java.io.Serializable;
import java.util.Date;

//yユーザーの情報をもつBeans
public class UserRegistrationBeans implements Serializable  {
	private String name; //ユーザー名
	private int pass; //PW
	private int money;
	private Date date;
	private int turn;


	public UserRegistrationBeans () {}

	public UserRegistrationBeans(String name ,int pass) {
		this.name=name;
		this.pass=pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

}
