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
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.riot.itemsets.dao.ProGamesDao;
import com.riot.itemsets.dao.ProPlayersDao;
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
	
	//private ProGamesDaoJdbc proGamesDao;
	
	@SpringBean
	ProPlayersDao proPlayersDao;
	
	@SpringBean
	ProGamesDao proGamesDao;

	public PlayerPanel(String id, final IModel<?> model) {
		super(id, model);
		
		Label proName = (new Label("proName", model));
		proName.add(new AttributeModifier("data-target", "#modal" + model.getObject().toString()));
		add(proName);
		
		//initializing daos for player
//		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
//		
//		ProPlayersDaoJdbc proPlayersDao = (ProPlayersDaoJdbc) context.getBean("proPlayersDaoJdbc");
//		proGamesDao = (ProGamesDaoJdbc) context.getBean("proGamesDaoJdbc");
		
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
							System.out.println(player.getProName());
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
		api = changeRegion(api);
		MatchList matches = api.getMatchList(summonerId);  //api call count: 1
		for(int i = 0; i < 5; i++){
			
			//player's match info
			
			if(matches != null && matches.getMatches() != null && !matches.getMatches().isEmpty()){
				MatchReference ref = matches.getMatches().get(i);
				api.setRegion(Region.NA); //switch region to NA to get champ obj. in English
				Champion champ = api.getDataChampion((int) ref.getChampion()); //api call count: 2
				//switch the region back
				api = changeRegion(api);
				MatchDetail match = api.getMatch(ref.getMatchId()); //api call count: 3
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
					if(p.getTimeline().getLane().contains(ref.getLane()) && p.getChampionId() != player.getChampionId()){
						enemyPlayer = p;
					}
				}
				
				api.setRegion(Region.NA); //switch region to NA to get champ obj. in English
				Champion enemyChamp = api.getDataChampion(enemyPlayer.getChampionId()); //api call count: 4
				//switch the region back
				api = changeRegion(api);
				ParticipantStats playerStats = player.getStats();
				
				//game object to write to the DB
				Games game = new Games();
				
				//set player's champion image, id, and name
				game.setChampId((int)ref.getChampion());
				game.setChampName(champ.getName());
				game.setChampImage("http://ddragon.leagueoflegends.com/cdn/5.15.1/img/champion/" + champ.getName().replace("'", "").replace(" ", "") + ".png");
				
				//set enemy player's champion image, id, and name
				game.setEnemyChampId(enemyChamp.getId());
				game.setEnemyChampName(enemyChamp.getName());
				game.setEnemyChampImage("http://ddragon.leagueoflegends.com/cdn/5.15.1/img/champion/" + enemyChamp.getName().replace("'", "").replace(" ", "") + ".png");
				
				game.setGameId(ref.getMatchId());
				game.setGoldSpent(playerStats.getGoldSpent());
				
				//set player's item images
				if(playerStats.getItem0() != 0l){
					game.setItem0("http://ddragon.leagueoflegends.com/cdn/5.15.1/img/item/" + playerStats.getItem0() +".png");
				} else {
					game.setItem0("images/noitem.png");
				}
				if(playerStats.getItem1() != 0l){
					game.setItem1("http://ddragon.leagueoflegends.com/cdn/5.15.1/img/item/" + playerStats.getItem1() +".png");
				} else {
					game.setItem1("images/noitem.png");
				}
				if(playerStats.getItem2() != 0l){
					game.setItem2("http://ddragon.leagueoflegends.com/cdn/5.15.1/img/item/" + playerStats.getItem2() +".png");
				} else {
					game.setItem2("images/noitem.png");
				}
				if(playerStats.getItem3() != 0l){
					game.setItem3("http://ddragon.leagueoflegends.com/cdn/5.15.1/img/item/" + playerStats.getItem3() +".png");
				} else {
					game.setItem3("images/noitem.png");
				}
				if(playerStats.getItem4() != 0l){
					game.setItem4("http://ddragon.leagueoflegends.com/cdn/5.15.1/img/item/" + playerStats.getItem4() +".png");
				} else {
					game.setItem4("images/noitem.png");
				}
				if(playerStats.getItem5() != 0l){
					game.setItem5("http://ddragon.leagueoflegends.com/cdn/5.15.1/img/item/" + playerStats.getItem5() +".png");
				} else {
					game.setItem5("images/noitem.png");
				}
				if(playerStats.getItem6() != 0l){
					game.setItem6("http://ddragon.leagueoflegends.com/cdn/5.15.1/img/item/" + playerStats.getItem6() +".png");
				} else {
					game.setItem6("images/noitem.png");
				}
				
				//set additional player info.
				game.setSummonerId(summonerId);
				game.setLane(ref.getLane());
				game.setWinner(playerStats.isWinner());
				
				// write game object to db table protables
				proGamesDao.create(game);
			} else {
				proGamesDao.create(ModalPanel.createEmptyGame());
				continue;
			}
		}
	}
	
	public RiotApi changeRegion(RiotApi api){
		switch(region){
		case "NA":
			api.setRegion(Region.NA);
			break;
		case "EU":
			api.setRegion(Region.EUW);
			break;
		case "KR":
			api.setRegion(Region.KR);
			break;
		}
		return api;
	}
}
