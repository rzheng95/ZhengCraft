package com.rzheng.zhengcraft.dao;

import com.rzheng.zhengcraft.entities.Player;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class test {
    public static void main(String[] args) {
        PlayerDataAccessService playerDataAccessService = new PlayerDataAccessService();
        Optional<List<Player>> players = playerDataAccessService.getPlayers.get();
        players.get().stream().map(player -> player.getId().toString().concat("\t").concat(player.getName())).collect(Collectors.toSet()).forEach(System.out::println);
    }
}
