package com.riot.itemsets.dao;

import java.util.List;

import javax.sql.DataSource;

import com.riot.itemsets.objects.Players;

public interface ProPlayersDao {
	 /** 
	    * This is the method to be used to initialize
	    * database resources ie. connection.
	    */
	   public void setDataSource(DataSource ds);
	   /** 
	    * This is the method to be used to create
	    * a record in the pro players table.
	    */
	   public Players getPlayer(Long id);
	   /** 
	    * This is the method to be used to list down
	    * all the players.
	    */
	   public List<Players> listPlayers();
}
