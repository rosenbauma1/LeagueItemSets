package com.riot.itemsets.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.riot.itemsets.objects.Games;

public class ProGamesDaoJdbc implements ProGamesDao{

	
	private JdbcTemplate jdbcTemplateObject;
	
	private class GamesMapper implements RowMapper<Games>{

		@Override
		public Games mapRow(ResultSet rs, int rowNum) throws SQLException {
			Games game = new Games();
			game.setGameId(rs.getLong("gameid"));
			game.setWinner((rs.getInt("winner") == 1));
			game.setLane(rs.getString("lane"));
			game.setItem0(rs.getString("item0"));
			game.setItem1(rs.getString("item1"));
			game.setItem2(rs.getString("item2"));
			game.setItem3(rs.getString("item3"));
			game.setItem4(rs.getString("item4"));
			game.setItem5(rs.getString("item5"));
			game.setItem6(rs.getString("item6"));
			game.setGoldSpent(rs.getLong("goldspent"));
			game.setChampId(rs.getInt("champid"));
			game.setChampName(rs.getString("champname"));
			game.setChampImage(rs.getString("champimage"));
			game.setEnemyChampId(rs.getInt("enemychampid"));
			game.setEnemyChampName(rs.getString("enemychampname"));
			game.setEnemyChampImage(rs.getString("enemychampimage"));
			game.setSummonerId(rs.getLong("summonerid"));
			return game;
		}	
	}
	
	@Override
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	

	@Override
	public void create(Games game) {
		String sql = "INSERT into progames (gameid, winner, lane, item0, item1, item2, item3, item4, item5, item6, goldspent, champid, champname, champimage, enemychampid, enemychampname, enemychampimage, summonerid)"
				+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		if(!exists(game.getGameId())){
			jdbcTemplateObject.update(sql, game.getGameId(), 
									game.isWinner(), 
									game.getLane(), 
									game.getItem0(), game.getItem1(), game.getItem2(), game.getItem3(), game.getItem4(), game.getItem5(), game.getItem6(), 
									game.getGoldSpent(), 
									game.getChampId(), game.getChampName(), game.getChampImage(),
									game.getEnemyChampId(), game.getEnemyChampName(), game.getEnemyChampImage(),
									game.getSummonerId());
		}
	}

	@Override
	public List<Games> listGames(Long id) {
		String sql = "Select * from progames where summonerid = ?";
		List<Games> games = jdbcTemplateObject.query(sql, new Object[]{id}, new GamesMapper());
		return games;
	}


	@Override
	public boolean exists(Long matchId) {
		String sql = "select * from progames where gameid = ?";
		return !(jdbcTemplateObject.queryForList(sql, new Object[]{matchId}).isEmpty());

	}

}
