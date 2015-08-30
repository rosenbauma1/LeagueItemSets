package com.riot.itemsets;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.riot.itemsets.objects.BlockForItems;

public class GsonFileBuilder {

	public static JsonObject gsonToJsonBuilder(ArrayList<BlockForItems> items, String title) {

		JsonObject builder = new JsonObject();
		builder.addProperty("title", title);
		builder.addProperty("type", "custom");
		builder.addProperty("map", "any");
		builder.addProperty("mode", "any");
		builder.addProperty("priority", "false");
		builder.addProperty("sortrank", 0);

		JsonArray blocks = itemsBlockBuilder(items);

		builder.add("blocks", blocks);
		return builder;
	}

	private static JsonArray itemsBlockBuilder(ArrayList<BlockForItems> frames) {

		JsonArray framesArray = new JsonArray();
		for (int i = 0; i < frames.size(); i++) {
			JsonObject oneFrame = new JsonObject();
			JsonArray frameArrayBuilder = new JsonArray();
			oneFrame.addProperty("type", "Back " + i + " (" + frames.get(i).getTimestamp() + "):");
			oneFrame.addProperty("recMath", "false");
			oneFrame.addProperty("minSummonerLevel", -1);
			oneFrame.addProperty("maxSummonerLevel", -1);
			oneFrame.addProperty("showIfSummonerSpell", "");
			oneFrame.addProperty("hideIfSummonerSpell", "");

			for (int j = 0; j < frames.get(i).getItems().size(); j++) {
				JsonObject itemObject = new JsonObject();
				System.out.println(frames.get(i).getItems().get(j));
				String itemId = frames.get(i).getItems().get(j).toString();
				itemObject.addProperty("id", itemId);
				itemObject.addProperty("count", 1);
				frameArrayBuilder.add(itemObject);
				System.out.println("item object" + itemObject.toString());
				System.out.println("frame array" + frameArrayBuilder.toString());
			}
			oneFrame.add("items", frameArrayBuilder);
			System.out.println("One Frame " + oneFrame.toString());
			framesArray.add(oneFrame);
		}
		
		return framesArray;
		
	}
}
