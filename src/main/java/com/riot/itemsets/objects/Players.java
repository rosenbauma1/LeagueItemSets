package com.riot.itemsets.objects;

import java.io.Serializable;

public class Players implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long summonerId;
	private String proName;
	private String role;
	private String teamName;
	private String thumbnailPath;
	private String headerPath;
	private String region;
	
	public Players(Long summonerId, String proName, String role, String teamName, String thumbnailPath,
			String headerPath, String region) {
		super();
		this.summonerId = summonerId;
		this.proName = proName;
		this.role = role;
		this.teamName = teamName;
		this.thumbnailPath = thumbnailPath;
		this.headerPath = headerPath;
		this.region = region;
	}
	
	public Players() {
		
	}

	public Long getSummonerId() {
		return summonerId;
	}
	public void setSummonerId(Long summonerId) {
		this.summonerId = summonerId;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	public String getHeaderPath() {
		return headerPath;
	}
	public void setHeaderPath(String headerPath) {
		this.headerPath = headerPath;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}

}
