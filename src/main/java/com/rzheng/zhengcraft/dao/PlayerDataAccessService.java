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
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class PlayerDataAccessService {

    private Connection connection;

    public PlayerDataAccessService() {
        try {
            this.connection = PostgresDatasource.hikariDataSource().getConnection();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }


    public Predicate<Player> addPlayer = player -> {
        final String sql = "INSERT INTO player (id, name) VALUES (?, ?)";

        if (this.getPlayerById.apply(player.getId()).isEmpty()) {
            try (PreparedStatement statement = connection.prepareStatement(sql);) {

                statement.setObject(1, player.getId());
                statement.setString(2, player.getName());
                statement.executeQuery();
                return true;
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        return false;
    };

    public Supplier<Optional<List<Player>>> getPlayers = () -> {
        List<Player> players = new ArrayList<>();
        final String sql = "SELECT * FROM player";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery();) {

            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String name = resultSet.getString("name");
                players.add(new Player(id, name));
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return Optional.of(players);
    };


    public Function<UUID, Optional<Player>> getPlayerById = id -> {
        Player player = null;
        final String sql = "SELECT * FROM player WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Player> players = new ArrayList<>();
            while (resultSet.next()) {
                UUID playerId = UUID.fromString(resultSet.getString("id"));
                String name = resultSet.getString("name");
                players.add(new Player(playerId, name));
            }
            if (players.size() > 0) {
                player = players.get(0);
            }
            resultSet.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return Optional.ofNullable(player);
    };


    public Predicate<Player> updatePlayer = player -> {
        final String sql = "UPDATE player SET name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, player.getName());
            statement.setObject(2, player.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    };


    public Predicate<UUID> deletePlayerById = id -> {
        final String sql = "DELETE FROM player WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setObject(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    };

}
