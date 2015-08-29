package com.riot.itemsets;

import java.util.ArrayList;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.google.gson.JsonObject;
import com.riot.itemsets.dao.ProGamesDao;
import com.riot.itemsets.dao.ProPlayersDao;
import com.riot.itemsets.objects.Games;
import com.riot.itemsets.objects.Players;

import constant.Region;
import dto.Static.Item;
import riotapi.RiotApi;
import riotapi.RiotApiException;

public class ModalPanel extends Panel{

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	ProPlayersDao proPlayersDao;
	
	@SpringBean
	ProGamesDao proGamesDao;
	
	private Region region;

	public ModalPanel(String id, final IModel<?> model) {
		super(id, model);
		
		WebMarkupContainer container = new WebMarkupContainer("myModal");
		container.add(new AttributeModifier("id", "modal"+model.getObject().toString()));
		container.add(new Label("proName", model));
		container.add(new Label("proName2", model));
		
		ArrayList<Players> allPlayers = (ArrayList<Players>) proPlayersDao.listPlayers();
		Players player = null;
		
		for(Players p : allPlayers){
			if(p.getProName().equals(model.getObject().toString())){
				player = p;
				container.add(new Image("proImage", p.getThumbnailPath()).add(new AttributeModifier("src", p.getThumbnailPath())));
				switch(player.getRegion()){
				case "NA":
					region = Region.NA;
					break;
				case "EU":
					region = Region.EUW;
					break;
				case "KR":
					region = Region.KR;
					break;
				}
			}
		}
		ArrayList<Games> games = (ArrayList<Games>) proGamesDao.listGames(player.getSummonerId());

		if(games.isEmpty()){
			games.add(createEmptyGame());
		}
		
		ListView<Games> gamesList = new ListView<Games>("gamesList", games) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<Games> item) {
				final Games game = item.getModel().getObject();
				StaticImage champImage, enemyChampImage;
				final StaticImage item0, item1, item2, item3, item4, item5, item6, goldSpentIcon;
				Label vsWon, champName, enemyChampName, goldSpent, vsChamp;
				Button exportButton = new Button("exportButton");
				item.add(exportButton);
				//exportButton.add(newOnClickBehavior(game));
				item.add(champImage = new StaticImage("champImage", new Model<String>(game.getChampImage())));
				item.add(enemyChampImage = new StaticImage("enemyChampImage", new Model<String>(game.getEnemyChampImage())));
				item.add(vsChamp = new Label("vsChamp", Model.of("vs")));
				item.add((vsWon = new Label("vsWon", Model.of("VS"))).add(new AttributeAppender("style", (game.isWinner() ? "color:green;" : "color:red;"))));
				item.add(champName = new Label("champName", game.getChampName()));
				item.add(enemyChampName = new Label("enemyChampName", game.getEnemyChampName()));
				item.add(goldSpent = new Label("goldSpent", game.getGoldSpent()));
				item.add(item0 = new StaticImage("item0", new Model<String>(game.getItem0())));
				item0.setOutputMarkupId(true);
				item.add(item1 = new StaticImage("item1", new Model<String>(game.getItem1())));
				item1.setOutputMarkupId(true);
				item.add(item2 = new StaticImage("item2", new Model<String>(game.getItem2())));
				item2.setOutputMarkupId(true);
				item.add(item3 = new StaticImage("item3", new Model<String>(game.getItem3())));
				item3.setOutputMarkupId(true);
				item.add(item4 = new StaticImage("item4", new Model<String>(game.getItem4())));
				item4.setOutputMarkupId(true);
				item.add(item5 = new StaticImage("item5", new Model<String>(game.getItem5())));
				item5.setOutputMarkupId(true);
				item.add(item6 = new StaticImage("item6", new Model<String>(game.getItem6())));
				item6.setOutputMarkupId(true);
				item.add(goldSpentIcon = new StaticImage("goldSpentIcon", new Model<String>("images/gold.png")));
				if(game.getGameId() == 0l){
					AttributeAppender hiddenMod = new AttributeAppender("style", "display:none;");
					champImage.add(hiddenMod);
					champName.setDefaultModel(Model.of("Riot API Rate Limit Exceeded"));
					enemyChampImage.add(hiddenMod);
					enemyChampName.add(hiddenMod);
					vsChamp.add(hiddenMod);
					vsWon.add(hiddenMod);
					item0.add(hiddenMod);
					item1.add(hiddenMod);
					item2.add(hiddenMod);
					item3.add(hiddenMod);
					item4.add(hiddenMod);
					item5.add(hiddenMod);
					item6.add(hiddenMod);
					goldSpent.add(hiddenMod);
					goldSpentIcon.add(hiddenMod);
					exportButton.add(hiddenMod);
				}

				item.add(new AjaxEventBehavior("click"){

					private static final long serialVersionUID = 1L;

					@Override
					protected void onEvent(AjaxRequestTarget target) {

				String titleOfFile = model.getObject().toString() + "_" + game.getChampName().replace(" ", "").replace("'", "") 
									+ "_vs_" 
									+ game.getEnemyChampName().replace(" ", "").replace("'", "") + ".json";
				
				
				JsonObject jObject = null;
					
				try {
					jObject = CreateJson.readWriteJson(region, titleOfFile, game.getGameId(), game.getSummonerId());
				} catch (RiotApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				target.appendJavaScript("var saveData = (function () {\n"
										+   "var a = document.createElement('a');\n"
										+ 	"document.body.appendChild(a);\n"
										+   "return function (data, fileName) { \n"
										+        "var json = JSON.stringify(data),\n"
										+            "blob = new Blob([json], {type: 'octet/stream'}),\n"
										+            "url = window.URL.createObjectURL(blob);\n"
										+        "a.href = url;\n"
										+        "a.download = fileName;\n"
										+        "a.click();\n"
									    +		 "setTimeout(function(){\n"
									    +    	 "document.body.removeChild(a);\n"
									    +    	 "window.URL.revokeObjectURL(url);\n"  
									    +        "}, 100);\n" 
										+    "};\n"
										+"}());\n"
										+"var data = " + jObject + ",\n" //caleb's jsonObj.toString() here
										+	"fileName = '" + titleOfFile + "';\n"
										+"saveData(data,fileName);\n"
										+"document.getElementById('downloadInfoModal').style.display = \"block\";");
						
					}
					
				});
			}
		};
		container.add(gamesList);
		add(container);
	}

	public static Games createEmptyGame() {
		Games game = new Games();
		game.setGameId(0l);
		game.setSummonerId(0l);
		game.setWinner(false);
		game.setLane("MID");
		game.setChampId(0);
		game.setChampImage("images/azir.png");
		game.setChampName("Azir");
		game.setEnemyChampId(1);
		game.setEnemyChampImage("images/azir.png");
		game.setEnemyChampName("Azir");
		game.setGoldSpent(32500l);
		game.setItem0("images/noitem.png");
		game.setItem1("images/noitem.png");
		game.setItem2("images/noitem.png");
		game.setItem3("images/noitem.png");
		game.setItem4("images/noitem.png");
		game.setItem5("images/noitem.png");
		game.setItem6("images/noitem.png");
		return game;
	}
	
	public static String buildItemTooltip(String itemImageUrl){
		
		RiotApi api = new RiotApi("470300d9-77fb-481f-b085-332443586fb8");
		api.setRegion(Region.NA);
		StringBuilder itemTooltip = new StringBuilder();
		if(!itemImageUrl.contains("noitem")){
			int itemId = Integer.valueOf(itemImageUrl.substring(itemImageUrl.length() - 8, itemImageUrl.length() - 4));
			try {
				Item item = api.getDataItem(itemId);
				itemTooltip.append(item.getDescription());
			} catch (RiotApiException e) {
				e.printStackTrace();
			}
		} else {
			itemTooltip.append("No Item");
		}
		
		return itemTooltip.toString();
	}

}

