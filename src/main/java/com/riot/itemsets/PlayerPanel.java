package com.riot.itemsets;

import java.util.ArrayList;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.riot.itemsets.dao.ProPlayersDaoJdbc;
import com.riot.itemsets.objects.Players;

public class PlayerPanel extends Panel{

	private static final long serialVersionUID = 1L;

	public PlayerPanel(String id, IModel<?> model) {
		super(id, model);
		
		add(new Label("proName", model));
		
		//initializing daos for player
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		
		ProPlayersDaoJdbc proPlayersDao = (ProPlayersDaoJdbc) context.getBean("proPlayersDaoJdbc");
		
		ArrayList<Players> allPlayers = (ArrayList<Players>) proPlayersDao.listPlayers();
		
		for(Players player : allPlayers){
			if(player.getProName().equals(model.getObject().toString())){
				add(new Image("proImage", player.getThumbnailPath()).add(new AttributeModifier("src", player.getThumbnailPath())));
			}
		}

	}

}
