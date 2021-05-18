package model;

import java.io.Serializable;
import java.util.Date;

/*
 * ランキング用JavaBeans
 */

public class RankingBeans implements Serializable {

	private Date date;
	private String name;
	private int money;

	public RankingBeans() {}

	public RankingBeans(Date player_date, String player_name, int player_money) {
		this.date = player_date;
		this.name = player_name;
		this.money = player_money;
	}

	public Date getPlayer_date() {
		return date;
	}
	public void setPlayer_date(Date player_date) {
		this.date = player_date;
	}
	public String getPlayer_name() {
		return name;
	}
	public void setPlayer_name(String player_name) {
		this.name = player_name;
	}
	public int getPlayer_money() {
		return money;
	}
	public void setPlayer_money(int player_money) {
		this.money = player_money;
	}

}
