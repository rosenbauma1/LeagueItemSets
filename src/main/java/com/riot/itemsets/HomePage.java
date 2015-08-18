package com.riot.itemsets;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.riot.itemsets.dao.ProPlayersDaoJdbc;
import com.riot.itemsets.objects.Players;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import java.util.ArrayList;

import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage{
	
	private static final long serialVersionUID = 1L;
	
	//Daos for players
	@SpringBean
	ProPlayersDaoJdbc proPlayersDao;
	//private ProPlayersDaoJdbc proPlayersDao;
	
	public HomePage() {
		super();
		//initializing daos for player and game data
		//ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		
		//proPlayersDao = (ProPlayersDaoJdbc) context.getBean("proPlayersDaoJdbc");
		
		ArrayList<Players> allPlayers = (ArrayList<Players>) proPlayersDao.listPlayers();
		ArrayList<String> regions = new ArrayList<String>();
		for(Players player : allPlayers){
			if(!regions.contains(player.getRegion())){
				regions.add(player.getRegion());
			}
		}
		int counter = 0;
		for(String region : regions){
			add(new RegionPanel("regionPanel"+counter, Model.of(region)));
			counter++;
		}

    }
}