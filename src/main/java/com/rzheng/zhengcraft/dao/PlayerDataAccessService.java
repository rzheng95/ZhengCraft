package com.rzheng.zhengcraft.dao;

import com.rzheng.zhengcraft.datasource.PostgresDatasource;
import com.rzheng.zhengcraft.entities.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerDataAccessService implements PlayerDao {

    @Override
    public int addPlayer(UUID id, String name) {
        final String sql = "INSERT INTO player (id, name) VALUES (?, ?)";


        return 0;
    }

    @Override
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        final String sql = "SELECT * FROM player";
        try(Connection connection = PostgresDatasource.hikariDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String name = resultSet.getString("name");
                players.add(new Player(id, name));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return players;
    }

    @Override
    public Player getPlayerById(UUID id) {
        return null;
    }

    @Override
    public Player getPlayerByName(String name) {
        return null;
    }

    @Override
    public int updatePlayer(Player player) {
        return 0;
    }

    @Override
    public int deletePlayerById(UUID id) {
        return 0;
    }
}
