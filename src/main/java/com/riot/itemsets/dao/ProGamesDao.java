package com.riot.itemsets.dao;

import java.util.List;

import javax.sql.DataSource;

import com.riot.itemsets.objects.Games;

public interface ProGamesDao {
	
	 /** 
	    * This is the method to be used to initialize
	    * database resources ie. connection.
	    */
	   public void setDataSource(DataSource ds);
	   /** 
	    * This is the method to be used to create
	    * a record in the pro games table.
	    */
	   public void create(Games game);
	   /** 
	    * This is the method to be used to list down
	    * a record of all games given summoner id
	    */
	   public List<Games> listGames(Long id);
	   /** 
	    * This is the method to be used to check 
	    * if the match already exists
	    */
	   public boolean exists(Long matchId);

}
