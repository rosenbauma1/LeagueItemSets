package com.riot.itemsets.objects;

public class Games {
	private Long gameId;
	private boolean winner;
	private String lane;
	private Long item0;
	private Long item1;
	private Long item2;
	private Long item3;
	private Long item4;
	private Long item5;
	private Long item6;
	private Long goldSpent;
	private int champId;
	private String champName;
	private int enemyChampId;
	private String enemyChampName;
	private Long summonerId;
	
	public Games(Long gameId, boolean winner, String lane, Long item0, Long item1, Long item2, Long item3, Long item4, Long item5,
			Long item6, Long goldSpent, int champId, String champName, int enemyChampId, String enemyChampName,
			Long summonerId) {
		super();
		this.gameId = gameId;
		this.winner = winner;
		this.lane = lane;
		this.item0 = item0;
		this.item1 = item1;
		this.item2 = item2;
		this.item3 = item3;
		this.item4 = item4;
		this.item5 = item5;
		this.item6 = item6;
		this.goldSpent = goldSpent;
		this.champId = champId;
		this.champName = champName;
		this.enemyChampId = enemyChampId;
		this.enemyChampName = enemyChampName;
		this.summonerId = summonerId;
	}
	
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
	public Long getItem0() {
		return item0;
	}
	public void setItem0(Long item0) {
		this.item0 = item0;
	}
	public Long getItem1() {
		return item1;
	}
	public void setItem1(Long item1) {
		this.item1 = item1;
	}
	public Long getItem2() {
		return item2;
	}
	public void setItem2(Long item2) {
		this.item2 = item2;
	}
	public Long getItem3() {
		return item3;
	}
	public void setItem3(Long item3) {
		this.item3 = item3;
	}
	public Long getItem4() {
		return item4;
	}
	public void setItem4(Long item4) {
		this.item4 = item4;
	}
	public Long getItem5() {
		return item5;
	}
	public void setItem5(Long item5) {
		this.item5 = item5;
	}
	public Long getItem6() {
		return item6;
	}
	public void setItem6(Long item6) {
		this.item6 = item6;
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
	
}
