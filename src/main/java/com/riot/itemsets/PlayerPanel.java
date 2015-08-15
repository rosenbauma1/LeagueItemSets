package com.riot.itemsets;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.riot.itemsets.dao.ProGamesDaoJdbc;
import com.riot.itemsets.dao.ProPlayersDaoJdbc;
import com.riot.itemsets.objects.Games;
import com.riot.itemsets.objects.Players;

import constant.Region;
import dto.Match.MatchDetail;
import dto.Match.Participant;
import dto.Match.ParticipantStats;
import dto.MatchList.MatchList;
import dto.MatchList.MatchReference;
import dto.Static.Champion;
import riotapi.RiotApi;
import riotapi.RiotApiException;

public class PlayerPanel extends Panel{

	private static final long serialVersionUID = 1L;
	
	private Players player;
	private String region;
	
	private ProGamesDaoJdbc proGamesDao;

	public PlayerPanel(String id, final IModel<?> model) {
		super(id, model);
		
		Label proName = (new Label("proName", model));
		proName.add(new AttributeModifier("data-target", "#modal" + model.getClass().toString()));
		add(proName);
		
		//initializing daos for player
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		
		ProPlayersDaoJdbc proPlayersDao = (ProPlayersDaoJdbc) context.getBean("proPlayersDaoJdbc");
		proGamesDao = (ProGamesDaoJdbc) context.getBean("proGamesDaoJdbc");
		
		ArrayList<Players> allPlayers = (ArrayList<Players>) proPlayersDao.listPlayers();
		
		for(Players p : allPlayers){
			if(p.getProName().equals(model.getObject().toString())){
				
				region = p.getRegion();
				player = p;
				
				Image proImage = new Image("proImage", p.getThumbnailPath());
				proImage.add(new AttributeModifier("src", p.getThumbnailPath()));
				proImage.add(new AttributeModifier("data-target", "#modal" + model.getObject().toString()));
				proImage.add(new AjaxEventBehavior("click") {

					private static final long serialVersionUID = 1L;

					@Override
					protected void onEvent(AjaxRequestTarget target) {
						try {
							 updateGamesPlayed(player.getSummonerId());
						
						} catch (RiotApiException e) {
							e.printStackTrace();
						}
					}
				});
				
				
				add(proImage);
			}
		}
		
		proName.add(new AjaxEventBehavior("click") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onEvent(AjaxRequestTarget target) {
				try {
					 updateGamesPlayed(player.getSummonerId());
				
				} catch (RiotApiException e) {
					e.printStackTrace();
				}
			}
		});
		
		add(new ModalPanel("modalPanel", model));
		

	}
	
	public void updateGamesPlayed(Long summonerId) throws RiotApiException{
		RiotApi api = new RiotApi("470300d9-77fb-481f-b085-332443586fb8");
		
		//set the region of api to player's region
		switch(region){
			case "NA":
				api.setRegion(Region.NA);
				break;
			case "EU":
				api.setRegion(Region.EUNE);
				break;
			case "KR":
				api.setRegion(Region.KR);
				break;
		}
		MatchList matches = api.getMatchList(summonerId);
		for(int i = 0; i < 1; i++){
			
			//player's match info
			MatchReference ref = matches.getMatches().get(i);
			Champion champ = api.getDataChampion((int) ref.getChampion());
			MatchDetail match = api.getMatch(ref.getMatchId());
			List<Participant> participants = match.getParticipants();
			Participant player = null;
			Participant enemyPlayer = null;
			
			//get players stats
			for(Participant p : participants){
				if(p.getChampionId() == ref.getChampion()){
					player = p;
				}	
			}
			//get enemy players stats
			for(Participant p : participants){
				System.out.println(p.getTimeline().getLane() + " == " + ref.getLane() + " && " + p.getChampionId() + " != " + ref.getChampion());
				if(p.getTimeline().getLane().contains(ref.getLane()) && p.getChampionId() != ref.getChampion()){
					enemyPlayer = p;
				}
			}
			Champion enemyChamp = api.getDataChampion(enemyPlayer.getChampionId());
			ParticipantStats playerStats = player.getStats();
			
			//game object to write to the DB
			Games game = new Games();
			
			//set player's champion image, id, and name
			game.setChampId((int)ref.getChampion());
			game.setChampName(champ.getName());
			game.setChampImage("http://ddragon.leagueoflegends.com/cdn/5.2.1/img/champion/" + champ.getName() + ".png");
			
			//set enemy player's champion image, id, and name
			game.setEnemyChampId(enemyChamp.getId());
			game.setEnemyChampName(enemyChamp.getName());
			game.setEnemyChampImage("http://ddragon.leagueoflegends.com/cdn/5.2.1/img/champion/" + enemyChamp.getName() + ".png");
			
			game.setGameId(ref.getMatchId());
			game.setGoldSpent(playerStats.getGoldSpent());
			
			//set player's item images
			if(playerStats.getItem0() != 0l){
				game.setItem0("http://ddragon.leagueoflegends.com/cdn/5.2.1/img/item/" + playerStats.getItem0() +".png");
			} else {
				game.setItem0("images/noitem.png");
			}
			if(playerStats.getItem1() != 0l){
				game.setItem1("http://ddragon.leagueoflegends.com/cdn/5.2.1/img/item/" + playerStats.getItem1() +".png");
			} else {
				game.setItem1("images/noitem.png");
			}
			if(playerStats.getItem2() != 0l){
				game.setItem2("http://ddragon.leagueoflegends.com/cdn/5.2.1/img/item/" + playerStats.getItem2() +".png");
			} else {
				game.setItem2("images/noitem.png");
			}
			if(playerStats.getItem3() != 0l){
				game.setItem3("http://ddragon.leagueoflegends.com/cdn/5.2.1/img/item/" + playerStats.getItem3() +".png");
			} else {
				game.setItem3("images/noitem.png");
			}
			if(playerStats.getItem4() != 0l){
				game.setItem4("http://ddragon.leagueoflegends.com/cdn/5.2.1/img/item/" + playerStats.getItem4() +".png");
			} else {
				game.setItem4("images/noitem.png");
			}
			if(playerStats.getItem5() != 0l){
				game.setItem5("http://ddragon.leagueoflegends.com/cdn/5.2.1/img/item/" + playerStats.getItem5() +".png");
			} else {
				game.setItem5("images/noitem.png");
			}
			if(playerStats.getItem6() != 0l){
				game.setItem6("http://ddragon.leagueoflegends.com/cdn/5.2.1/img/item/" + playerStats.getItem6() +".png");
			} else {
				game.setItem6("images/noitem.png");
			}
			
			//set additional player info.
			game.setSummonerId(summonerId);
			game.setLane(ref.getLane());
			game.setWinner(playerStats.isWinner());
			
			// write game object to db table protables
			proGamesDao.create(game);
		}
	}

}
