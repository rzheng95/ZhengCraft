package com.rzheng.zhengcraft.listeners;

import com.rzheng.zhengcraft.dao.PlayerDataAccessService;
import com.rzheng.zhengcraft.entities.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class ZhengCraftMainListener implements Listener {

    private final PlayerDataAccessService playerDataAccessService;


    public ZhengCraftMainListener() {
        this.playerDataAccessService = new PlayerDataAccessService();
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e)
    {

        e.getPlayer().sendMessage("You broke " + e.getBlock().getBlockData().getMaterial());

        Optional<List<Player>> players = playerDataAccessService.getPlayers.get();

        players.get().stream()
                .map(player ->
                    player.getId().toString()
                        .concat("\t")
                        .concat(player.getName()))
                .collect(Collectors.toSet())
                .forEach(System.out::println);

        playerDataAccessService.addPlayer.test(new Player(e.getPlayer().getUniqueId(), e.getPlayer().getName()));
//        playerDataAccessService.addPlayer2(new Player(e.getPlayer().getUniqueId(), e.getPlayer().getName()));


    }

//    @EventHandler
//    public void onBlockPlaceEvent(BlockPlaceEvent e) {
//        e.getPlayer().sendMessage("You placed a " + e.getBlock().getBlockData().getMaterial());
//
//    }



    // listen and track how many blocks what kind of block placed
    // if the number of blocks > blockThrottle OR it has been 5 minutes
    // Use a Map to track of what player placed what kind of block and how many

    // player1 -> GRASS_BLOCK -> 21
    // player1 -> SAND -> 11
    // player1 -> DIRT -> 5

    // player2 -> GRASS_BLOCK -> 31
    // player2 -> DIRT -> 2

    // player3 -> GRASS_BLOCK -> 7
    // player3 -> SAND -> 15

    // player TABLE
    // UUID | username

    // block_placed TABLE
    // block_type | num_of_blocks | player.UUID

//     Map<Player, Map<BlockType, Integer>>
//    Map<Player, Map<Block, Integer>> cache = new HashMap<>();

}
