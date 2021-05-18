package model;

import java.io.Serializable;

public class NowPlayer implements Serializable  {

	private String name;//プレイヤー名
	private String job;//職業
	private int money;//所持金
	private int salary;//給料
	private int advanceNumber;//今いるマス
	private int random;//このターン進むマス
	private int turnCount;//かかったターン数

	public NowPlayer () {}

	public NowPlayer(String name) {

		this.name = name;
		this.job = "無職";
		this.money = 50000;
		this.salary = 0;
		this.advanceNumber = 0;
		this.turnCount = 0;

	}

	public int getTurnCount() {
		return turnCount;
	}

	public void setTurnCount(int turnCount) {
		this.turnCount = turnCount;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getAdvanceNumber() {
		return advanceNumber;
	}

	public void setAdvanceNumber(int advanceNumber) {
		this.advanceNumber = advanceNumber;
	}

	public int getRandom() {
		return random;
	}

	public void setRandom(int random) {
		this.random = random;
	}

}
