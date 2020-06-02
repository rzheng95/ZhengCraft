package com.rzheng.zhengcraft.dao;

import com.rzheng.zhengcraft.entities.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerDao {

    int addPlayer(UUID id, String name);

    default int addPlayer(String name) {
        return addPlayer(UUID.randomUUID(), name);
    }

    List<Player> getPlayers();

    Optional<Player> getPlayerById(UUID id);

    Player getPlayerByName(String name);

    int updatePlayer(Player player);

    int deletePlayerById(UUID id);
}
