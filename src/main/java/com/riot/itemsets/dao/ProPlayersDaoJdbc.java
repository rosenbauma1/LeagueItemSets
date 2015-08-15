package com.riot.itemsets.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.riot.itemsets.objects.Players;

public class ProPlayersDaoJdbc implements ProPlayersDao{
	
	private JdbcTemplate jdbcTemplateObject;

	private class PlayersMapper implements RowMapper<Players> {

		@Override
		public Players mapRow(ResultSet rs, int rowNum) throws SQLException {
			Players players = new Players();
			players.setProName(rs.getString("proname"));
			players.setRegion(rs.getString("region"));
			players.setRole(rs.getString("role"));
			players.setSummonerId(rs.getLong("summonerid"));
			players.setTeamName(rs.getString("teamname"));
			players.setThumbnailPath(rs.getString("thumbnailpath"));
			players.setHeaderPath(rs.getString("headerpath"));
			return players;
		}	
	}
	
	@Override
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public Players getPlayer(Long id) {
		String sql = "select * from proplayers where summonerid = ?";
		return jdbcTemplateObject.queryForObject(sql, new Object[]{id}, new PlayersMapper());
	}

	@Override
	public List<Players> listPlayers() {
		String sql = "Select * from proplayers";
		List<Players> players = jdbcTemplateObject.query(sql, new PlayersMapper());
		return players;
	}

}
