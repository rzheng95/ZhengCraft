package com.rzheng.zhengcraft.listeners;

import com.rzheng.zhengcraft.dao.BlockDataAccessService;
import com.rzheng.zhengcraft.dao.PlayerDataAccessService;
import com.rzheng.zhengcraft.entities.Block;
import com.rzheng.zhengcraft.entities.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.*;
import java.util.stream.Collectors;


public class ZhengCraftMainListener implements Listener {

    private final PlayerDataAccessService playerDataAccessService;
    private final BlockDataAccessService blockDataAccessService;
    Map<UUID, Map<String, Integer>> cache = new HashMap<>();

    public ZhengCraftMainListener() {
        this.playerDataAccessService = new PlayerDataAccessService();
        this.blockDataAccessService = new BlockDataAccessService();
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

        Block block = new Block(e.getPlayer().getUniqueId(), e.getBlock().getBlockData().getMaterial().toString(), 1);
        blockDataAccessService.addBlock.test(block);
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e) {
        e.getPlayer().sendMessage("You placed a " + e.getBlock().getBlockData().getMaterial());



    }



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

//    CREATE TABLE blocks_placed {
//        material VARCHAR(255),
//        quantity bigint,
//        player_id foreign key to player id,
//        primary key(player_id, material)
//    }

}
