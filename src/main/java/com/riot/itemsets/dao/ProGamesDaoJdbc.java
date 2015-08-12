package com.riot.itemsets.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.riot.itemsets.objects.Games;

public class ProGamesDaoJdbc implements ProGamesDao{

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	private class GamesMapper implements RowMapper<Games>{

		@Override
		public Games mapRow(ResultSet rs, int rowNum) throws SQLException {
			Games game = new Games();
			game.setGameId(rs.getLong("gameid"));
			game.setWinner((rs.getInt("winner") == 1));
			game.setLane(rs.getString("lane"));
			game.setItem0(rs.getLong("item0id"));
			game.setItem1(rs.getLong("item1id"));
			game.setItem2(rs.getLong("item2id"));
			game.setItem3(rs.getLong("item3id"));
			game.setItem4(rs.getLong("item4id"));
			game.setItem5(rs.getLong("item5id"));
			game.setItem6(rs.getLong("item6id"));
			game.setGoldSpent(rs.getLong("goldspent"));
			game.setChampId(rs.getInt("champid"));
			game.setChampName(rs.getString("champname"));
			game.setEnemyChampId(rs.getInt("enemychampid"));
			game.setEnemyChampName(rs.getString("enemychampname"));
			game.setSummonerId(rs.getLong("summonerid"));
			return game;
		}
		
	}
	
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void create(Games game) {
		String sql = "INSERT into progames (gameid, winner, lane, item0id, item1id, item2id, item3id, item4id, item5id, item6id, goldspent, champid, champname, enemychampid, enemychampname, summonerid)"
				+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplateObject.update(sql, game.getGameId(), 
									game.isWinner(), 
									game.getLane(), 
									game.getItem0(), game.getItem1(), game.getItem2(), game.getItem3(), game.getItem4(), game.getItem5(), game.getItem6(), 
									game.getGoldSpent(), 
									game.getChampId(), game.getChampName(),
									game.getEnemyChampId(), game.getEnemyChampName(), 
									game.getSummonerId());
	}

	@Override
	public List<Games> listGames(Long id) {
		String sql = "Select * from progames where summonerid = ?";
		List<Games> games = jdbcTemplateObject.query(sql, new Object[]{id}, new GamesMapper());
		return games;
	}

}
