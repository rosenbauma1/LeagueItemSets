package com.riot.itemsets;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.riot.itemsets.dao.ProGamesDaoJdbc;
import com.riot.itemsets.dao.ProPlayersDaoJdbc;
import com.riot.itemsets.objects.Players;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	//Daos for players and game data
	private ProPlayersDaoJdbc proPlayersDao;
	private ProGamesDaoJdbc proGamesDao;
	
	public HomePage(final PageParameters parameters) {
		super(parameters);
		
		//initializing daos for player and game data
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		
		proPlayersDao = (ProPlayersDaoJdbc) context.getBean("proPlayersDaoJdbc");
		proGamesDao = (ProGamesDaoJdbc) context.getBean("proGamesDaoJdbc");
		
		Players bjerg = proPlayersDao.listPlayers().get(0);
		add(new Label("playerName", bjerg.getProName()));
		add(new Label("sumId", bjerg.getSummonerId()));
		add(new Label("role", bjerg.getRole()));
		add(new Label("teamName", bjerg.getTeamName()));
		add(new Label("thumbnailPath", bjerg.getThumbnailPath()));
		add(new Label("headerPath", bjerg.getHeaderPath()));
		add(new Label("region", bjerg.getRegion()));

    }
}
