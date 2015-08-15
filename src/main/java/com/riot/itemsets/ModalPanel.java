package com.riot.itemsets;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.riot.itemsets.dao.ProGamesDaoJdbc;
import com.riot.itemsets.dao.ProPlayersDaoJdbc;
import com.riot.itemsets.objects.Games;
import com.riot.itemsets.objects.Players;



public class ModalPanel extends Panel{

	private static final long serialVersionUID = 1L;

	public ModalPanel(String id, IModel<?> model) {
		super(id, model);
		
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		
		ProPlayersDaoJdbc proPlayersDao = (ProPlayersDaoJdbc) context.getBean("proPlayersDaoJdbc");
		ProGamesDaoJdbc proGamesDao = (ProGamesDaoJdbc) context.getBean("proGamesDaoJdbc");
		
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
			}
		}
		ArrayList<Games> games = (ArrayList<Games>) proGamesDao.listGames(player.getSummonerId());

		if(games.isEmpty()){
			games.add(createEmptyGame());
		}
		
		ListView<Games> gamesList = new ListView<Games>("gamesList", games) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Games> item) {
				Games game = item.getModel().getObject();
				item.add(new StaticImage("champImage", new Model<String>(game.getChampImage())));
				item.add(new StaticImage("enemyChampImage", new Model<String>(game.getEnemyChampImage())));
				item.add(new Label("champName", game.getChampName()));
				item.add(new Label("enemyChampName", game.getEnemyChampName()));
				item.add(new Label("goldSpent", game.getGoldSpent()));
				item.add(new StaticImage("item0", new Model<String>(game.getItem0())));
				item.add(new StaticImage("item1", new Model<String>(game.getItem1())));
				item.add(new StaticImage("item2", new Model<String>(game.getItem2())));
				item.add(new StaticImage("item3", new Model<String>(game.getItem3())));
				item.add(new StaticImage("item4", new Model<String>(game.getItem4())));
				item.add(new StaticImage("item5", new Model<String>(game.getItem5())));
				item.add(new StaticImage("item6", new Model<String>(game.getItem6())));
			}
		};
		container.add(gamesList);
		add(container);
	}

	private Games createEmptyGame() {
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
