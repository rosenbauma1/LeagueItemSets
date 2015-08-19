package com.riot.itemsets;

import java.util.ArrayList;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.riot.itemsets.dao.ProPlayersDao;
import com.riot.itemsets.dao.ProPlayersDaoJdbc;
import com.riot.itemsets.objects.Players;

public class HomePage extends WebPage{
	
	private static final long serialVersionUID = 1L;
	
	//Daos for players
	@SpringBean
	ProPlayersDao proPlayersDao;
	//private ProPlayersDaoJdbc proPlayersDao;
	
	public HomePage() {
		super();
		//initializing daos for player and game data
//		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
//		
//		proPlayersDao = (ProPlayersDaoJdbc) context.getBean("proPlayersDaoJdbc");
		
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
