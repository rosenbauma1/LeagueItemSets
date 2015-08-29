package com.riot.itemsets;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GsonFileBuilder {
	
	public static JsonObject gsonToJsonBuilder(String title, ArrayList<Integer> startingItems, ArrayList<Integer> coreItems, ArrayList<Integer> endGame) {
	
		JsonObject builder = new JsonObject();
		builder.addProperty("title", title);
		builder.addProperty("type", "custom");
		builder.addProperty("map", "any");
		builder.addProperty("mode", "any");
		builder.addProperty("priority", "false");
		builder.addProperty("sortrank", 0);
		
		JsonArray blocks = new JsonArray();
		JsonObject starterItems = itemsBlockBuilder("Starting Items", startingItems);
		JsonObject corItems = itemsBlockBuilder("Core Items", coreItems);
		JsonObject endGameItems = itemsBlockBuilder("Final Items", endGame);
		
		if(starterItems != null){
			blocks.add(starterItems);
		}
		if(corItems != null){
			blocks.add(corItems);
		}
		if(endGameItems != null){
			blocks.add(endGameItems);
		}
		builder.add("blocks", blocks);
		return builder;
	}
	
	private static JsonObject itemsBlockBuilder(String type, ArrayList<Integer> itemLists){
		
		JsonObject itemsObjectBuilder = new JsonObject();
		itemsObjectBuilder.addProperty("type", type);
		itemsObjectBuilder.addProperty("recMath", "false");
		itemsObjectBuilder.addProperty("minSummonerLevel", -1);
		itemsObjectBuilder.addProperty("maxSummonerLevel", -1);
		itemsObjectBuilder.addProperty("showIfSummonerSpell", "");
		itemsObjectBuilder.addProperty("hideIfSummonerSpell", "");
		
		System.out.println("type: " + type + " " + itemLists.size());
		JsonArray itemsArrayBuilder = new JsonArray();
		for(int i = 0; i < itemLists.size(); i++){
			JsonObject itemListObject = new JsonObject();
			itemListObject.addProperty("id", itemLists.get(i).toString());
			itemListObject.addProperty("count", 1);
			itemsArrayBuilder.add(itemListObject);
			itemsObjectBuilder.add("items", itemsArrayBuilder);
		}
		
		return itemsObjectBuilder;
		
	}

}