package com.riot.itemsets;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.JsonObject;
import com.riot.itemsets.objects.BlockForItems;

import constant.Region;
import dto.Match.Event;
import dto.Match.Frame;
import dto.Match.MatchDetail;
import dto.Match.ParticipantIdentity;
import riotapi.RiotApi;
import riotapi.RiotApiException;

public class CreateJson {
	
	public static JsonObject readWriteJson(Region region, String title, Long matchId,
			Long sumId) throws RiotApiException {

		RiotApi api = new RiotApi("bf272907-42f9-4085-9470-1049579d9d2f");

		MatchDetail matchOne = api.getMatch(Region.NA, matchId, true);

		// collects frames from game
		ArrayList<Frame> frameList = new ArrayList<Frame>();
		frameList.addAll((Collection<? extends Frame>) matchOne.getTimeline()
				.getFrames());

		// stores summoner's id local to the match
		int sumPartId = 0;
		for (ParticipantIdentity participant : matchOne
				.getParticipantIdentities()) {
			if (participant.getPlayer().getSummonerId() == sumId) {
				sumPartId = participant.getParticipantId();
			}
		}
		// item purchase, counter++ add item id, item sold/destroyed/undo,
		// counter++ subtract item id
		// grabs starting items from frame 1 & 2

		ArrayList<BlockForItems> allEvents = new ArrayList<BlockForItems>();
		
		for (int i = 1; i < frameList.size(); i++) {
			ArrayList<Integer> items = new ArrayList<Integer>();
			BlockForItems block = new BlockForItems(items);
			for (int j = 0; j < frameList.get(i).getEvents().size(); j++) {
				Event event = frameList.get(i).getEvents().get(j);
				if (event.getParticipantId() == sumPartId) {
					if (event.getEventType().equals("ITEM_PURCHASED")) {
						items.add((event.getItemId() == 2010 ? 2003 : event.getItemId()));
						block.setItems(items);
					} else if (event.getEventType().equals("ITEM_UNDO")) {
						items.remove(items.lastIndexOf((Integer) (event.getItemId() == 2010 ? 2003 : event.getItemId())));
						block.setItems(items);
					}
				}
			}
			if(!(block.getItems().isEmpty())){
				block.setTimestamp("~" + i + ":00");
				allEvents.add(block);
			}
		}

		 return GsonFileBuilder.gsonToJsonBuilder(allEvents, title);
		
	}
}
