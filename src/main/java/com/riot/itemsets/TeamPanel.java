package com.riot.itemsets;

import java.util.ArrayList;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.riot.itemsets.dao.ProPlayersDao;
import com.riot.itemsets.dao.ProPlayersDaoJdbc;
import com.riot.itemsets.objects.Players;

public class TeamPanel extends Panel{

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	ProPlayersDao proPlayersDao;

	public TeamPanel(String id, IModel model) {
		super(id, model);
		add(new Label("teamName", model ));
		
		//initializing daos for player
//		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
//		
//		ProPlayersDaoJdbc proPlayersDao = (ProPlayersDaoJdbc) context.getBean("proPlayersDaoJdbc");
		
		ArrayList<Players> allPlayers = (ArrayList<Players>) proPlayersDao.listPlayers();
		ArrayList<String> regions = new ArrayList<String>();
		ArrayList<String> teams = new ArrayList<String>();
		for(Players player : allPlayers){
			if(!regions.contains(player.getRegion())){
				regions.add(player.getRegion());
			}
			if(!teams.contains(player.getTeamName()) && player.getTeamName().equals(model.getObject().toString())){
				teams.add(player.getTeamName());
			}
		}
		for(String team : teams){
			add(new PlayerPanel("playerPanel", Model.of(team)));		
		}
	}

}
