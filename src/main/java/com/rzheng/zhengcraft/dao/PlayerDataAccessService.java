package com.rzheng.zhengcraft.dao;

import com.rzheng.zhengcraft.datasource.PostgresDatasource;
import com.rzheng.zhengcraft.entities.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlayerDataAccessService implements PlayerDao {

    private Connection connection;

    public PlayerDataAccessService() {
        try {
            this.connection = PostgresDatasource.hikariDataSource().getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public int addPlayer(UUID id, String name) {
        final String sql = "INSERT INTO player (id, name) VALUES (?, ?)";

        if (getPlayerById(id).isEmpty()) {
            try(PreparedStatement statement = connection.prepareStatement(sql);) {

                statement.setObject(1, id);
                statement.setString(2, name);
                statement.executeQuery();

                return 1;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        final String sql = "SELECT * FROM player";

        try(PreparedStatement statement = connection.prepareStatement(sql);
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
    public Optional<Player> getPlayerById(UUID id) {
        List<Player> players = new ArrayList<>();

        final String sql = "SELECT * FROM player WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID playerId = UUID.fromString(resultSet.getString("id"));
                String name = resultSet.getString("name");
                players.add(new Player(playerId, name));
            }
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (players.size() > 0)
            return Optional.ofNullable(players.get(0));
        return Optional.empty();
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
