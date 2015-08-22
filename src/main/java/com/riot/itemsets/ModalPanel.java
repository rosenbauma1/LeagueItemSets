package com.riot.itemsets;

import java.awt.event.ItemEvent;
import java.io.File;
import java.util.ArrayList;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.JsonObject;
import com.riot.itemsets.dao.ProGamesDao;
import com.riot.itemsets.dao.ProGamesDaoJdbc;
import com.riot.itemsets.dao.ProPlayersDao;
import com.riot.itemsets.dao.ProPlayersDaoJdbc;
import com.riot.itemsets.objects.Games;
import com.riot.itemsets.objects.Players;

import constant.Region;
import main.java.riotapi.RiotApiException;

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
				StaticImage champImage, enemyChampImage, item0, item1, item2, item3, item4, item5, item6, goldSpentIcon;
				Label vsWon, champName, enemyChampName, goldSpent, vsChamp;
				Form<?> form = new Form<>("exportForm");
				final SpecialButton exportButton = new SpecialButton("exportButton", item);
				//exportButton.add(new AttributeAppender("onclick", "alert('" + game.getGameId() +"ExportButton');"));
				item.add(champImage = new StaticImage("champImage", new Model<String>(game.getChampImage())));
				item.add(enemyChampImage = new StaticImage("enemyChampImage", new Model<String>(game.getEnemyChampImage())));
				item.add(vsChamp = new Label("vsChamp", Model.of("vs")));
				item.add((vsWon = new Label("vsWon", Model.of("VS"))).add(new AttributeAppender("style", (game.isWinner() ? "color:green;" : "color:red;"))));
				item.add(champName = new Label("champName", game.getChampName()));
				item.add(enemyChampName = new Label("enemyChampName", game.getEnemyChampName()));
				item.add(goldSpent = new Label("goldSpent", game.getGoldSpent()));
				item.add(item0 = new StaticImage("item0", new Model<String>(game.getItem0())));
				item.add(item1 = new StaticImage("item1", new Model<String>(game.getItem1())));
				item.add(item2 = new StaticImage("item2", new Model<String>(game.getItem2())));
				item.add(item3 = new StaticImage("item3", new Model<String>(game.getItem3())));
				item.add(item4 = new StaticImage("item4", new Model<String>(game.getItem4())));
				item.add(item5 = new StaticImage("item5", new Model<String>(game.getItem5())));
				item.add(item6 = new StaticImage("item6", new Model<String>(game.getItem6())));
				item.add(goldSpentIcon = new StaticImage("goldSpentIcon", new Model<String>("images/gold.png")));
				form.add(exportButton);
				item.add(form);
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
						System.out.println("Calling Caleb's itemset generation with match id: " + game.getGameId()
						+ " summoner id: " + game.getSummonerId() 
						+ " enemy name: " + game.getEnemyChampName());
				String titleOfFile = model.getObject().toString() + "_" + game.getChampName().replace(" ", "").replace("'", "") 
									+ "_vs_" 
									+ game.getEnemyChampName().replace(" ", "").replace("'", "") + ".json";
				
				
				JsonObject jObject = null;
					
				try {
					jObject = CreateJson.readWriteJson(region, titleOfFile, game.getGameId(), game.getSummonerId());
				} catch (riotapi.RiotApiException e) {
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
										+        "window.URL.revokeObjectURL(url);\n"
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
	
	private class SpecialButton extends AjaxButton {
	    final ListItem<Games> item;

	    public SpecialButton(final String id, final ListItem<Games> item) {
	        super(id);

	        this.item = item;
	    }

	    @Override
	    protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
	        // here you cand do everything you want with the item and the model object of the item.(row)
//	        Games game = item.getModelObject();
//	        System.out.println("Calling Caleb's itemset generation with match id: " + game.getGameId()
//			+ " summoner id: " + game.getSummonerId() 
//			+ " enemy name: " + game.getEnemyChampName());
	    }

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

}
