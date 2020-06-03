package com.rzheng.zhengcraft.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class test {
    public static void main(String[] args) {
//        PlayerDataAccessService playerDataAccessService = new PlayerDataAccessService();
//        Optional<List<Player>> players = playerDataAccessService.getPlayers.get();
//        players.get().stream().map(player -> player.getId().toString().concat("\t").concat(player.getName())).collect(Collectors.toSet()).forEach(System.out::println);

        Map<UUID, Map<String, Integer>> cache = new HashMap<>();



        if (cache.get(UUID.fromString("8c2c0568-f3c0-4768-88d0-d034b8efbc69")) == null) {
            // create
            Map<String, Integer> block_quantity = new HashMap<>();
            block_quantity.put("Stone", 21);
            cache.put(UUID.fromString("8c2c0568-f3c0-4768-88d0-d034b8efbc69"), block_quantity);
        }

        for (UUID id : cache.keySet()) {
            System.out.println(id.toString() + cache.get(id));
        }

        if (cache.get(UUID.fromString("8c2c0568-f3c0-4768-88d0-d034b8efbc69")) != null) {
            String material = "Stone";
            int quantity = 1;

            if (cache.get(UUID.fromString("8c2c0568-f3c0-4768-88d0-d034b8efbc69")).get(material) != null) {
                cache.get(UUID.fromString("8c2c0568-f3c0-4768-88d0-d034b8efbc69")).put(material, cache.get(UUID.fromString("8c2c0568-f3c0-4768-88d0-d034b8efbc69")).get(material) + quantity);
            }
            cache.get(UUID.fromString("8c2c0568-f3c0-4768-88d0-d034b8efbc69")).put("Dirt", 11);
        }

        for (UUID id : cache.keySet()) {
            System.out.println(id.toString() + cache.get(id));
        }
    }
}
