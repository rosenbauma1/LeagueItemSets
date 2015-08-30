package com.riot.itemsets.objects;

import java.io.Serializable;

public class Games implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long gameId;
	private boolean winner;
	private String lane;
	private String item0;
	private String item1;
	private String item2;
	private String item3;
	private String item4;
	private String item5;
	private String item6;
	private Long goldSpent;
	private int champId;
	private String champName;
	private int enemyChampId;
	private String champImage;
	private String enemyChampImage;
	private String enemyChampName;
	private Long summonerId;
	private boolean hidden;
	
	public Games() {

	}

	public Long getGameId() {
		return gameId;
	}
	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
	public boolean isWinner() {
		return winner;
	}
	public void setWinner(boolean winner) {
		this.winner = winner;
	}
	public Long getGoldSpent() {
		return goldSpent;
	}
	public void setGoldSpent(Long goldSpent) {
		this.goldSpent = goldSpent;
	}
	public int getChampId() {
		return champId;
	}
	public void setChampId(int champId) {
		this.champId = champId;
	}
	public String getChampName() {
		return champName;
	}
	public void setChampName(String champName) {
		this.champName = champName;
	}
	public int getEnemyChampId() {
		return enemyChampId;
	}
	public void setEnemyChampId(int enemyChampId) {
		this.enemyChampId = enemyChampId;
	}
	public String getEnemyChampName() {
		return enemyChampName;
	}
	public void setEnemyChampName(String enemyChampName) {
		this.enemyChampName = enemyChampName;
	}
	public Long getSummonerId() {
		return summonerId;
	}
	public void setSummonerId(Long summonerId) {
		this.summonerId = summonerId;
	}
	public String getLane() {
		return lane;
	}

	public void setLane(String lane) {
		this.lane = lane;
	}

	public String getEnemyChampImage() {
		return enemyChampImage;
	}

	public void setEnemyChampImage(String enemyChampImage) {
		this.enemyChampImage = enemyChampImage;
	}

	public String getChampImage() {
		return champImage;
	}

	public void setChampImage(String champImage) {
		this.champImage = champImage;
	}

	public String getItem0() {
		return item0;
	}

	public void setItem0(String item0) {
		this.item0 = item0;
	}

	public String getItem1() {
		return item1;
	}

	public void setItem1(String item1) {
		this.item1 = item1;
	}

	public String getItem2() {
		return item2;
	}

	public void setItem2(String item2) {
		this.item2 = item2;
	}

	public String getItem3() {
		return item3;
	}

	public void setItem3(String item3) {
		this.item3 = item3;
	}

	public String getItem4() {
		return item4;
	}

	public void setItem4(String item4) {
		this.item4 = item4;
	}

	public String getItem5() {
		return item5;
	}

	public void setItem5(String item5) {
		this.item5 = item5;
	}

	public String getItem6() {
		return item6;
	}

	public void setItem6(String item6) {
		this.item6 = item6;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
}
