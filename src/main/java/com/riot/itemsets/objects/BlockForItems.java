package com.riot.itemsets.objects;

import java.util.ArrayList;

public class BlockForItems {

	private ArrayList<Integer> items = new ArrayList<Integer>();
	private String timestamp;
	
	
	public BlockForItems(ArrayList<Integer> items) {
		super();
		this.items = items;
	}
	public ArrayList<Integer> getItems() {
		return items;
	}

	public void setItems(ArrayList<Integer> items) {
		this.items = items;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
