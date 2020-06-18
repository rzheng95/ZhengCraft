/*
 * Copyright 2020-2020 Richard R. Zheng (https://github.com/rzheng95)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to
 * do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 *
 */

package com.rzheng.zhengcraft.listeners;

import com.rzheng.zhengcraft.dao.BlockDataAccessService;
import com.rzheng.zhengcraft.dao.PlayerDataAccessService;
import com.rzheng.zhengcraft.entities.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ZhengCraftMainListener implements Listener {

    private final PlayerDataAccessService playerDataAccessService;
    private final BlockDataAccessService blockDataAccessService;

    Map<UUID, Map<String, Integer>> blockBreakCache;
    Map<UUID, Map<String, Integer>> blockPlaceCache;

    public ZhengCraftMainListener() {
        this.playerDataAccessService = new PlayerDataAccessService();
        this.blockDataAccessService = new BlockDataAccessService();
        this.blockBreakCache = new HashMap<>();
        this.blockPlaceCache = new HashMap<>();
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        updateDatabase(e.getPlayer());

        UUID uid = e.getPlayer().getUniqueId();
        String blockType = e.getBlock().getBlockData().getMaterial().toString();
        Map<String, Integer> block_count = this.blockBreakCache.get(uid);

        Optional.ofNullable(block_count).ifPresentOrElse(res -> {
            block_count.put(blockType,
                    (block_count.get(blockType) != null ? block_count.get(blockType) : 0)
                            + 1);
        }, () -> {
            this.blockBreakCache.put(uid, new HashMap<>() {{
                put(blockType, 1);
            }});
        });

        System.out.println(blockBreakCache);
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e) {
        updateDatabase(e.getPlayer());

        UUID uid = e.getPlayer().getUniqueId();
        String blockType = e.getBlock().getBlockData().getMaterial().toString();
        Map<String, Integer> block_count = this.blockPlaceCache.get(uid);

        if (block_count != null) {
            block_count.put(blockType,
                    (block_count.get(blockType) != null ? block_count.get(blockType) : 0)
                            + 1);
        } else {
            this.blockPlaceCache.put(uid, new HashMap<>() {{
                put(blockType, 1);
            }});
        }

        System.out.println(blockPlaceCache);
//        e.getPlayer().sendMessage("You placed a " + e.getBlock().getBlockData().getMaterial());
    }

    private void addPlayer(Player player) {
        if (playerDataAccessService.getPlayerById.apply(player.getUniqueId()).isEmpty()) {
            playerDataAccessService.addPlayer.test(new com.rzheng.zhengcraft.entities.Player(player.getUniqueId(), player.getDisplayName()));
        }
    }

    private void updateDatabase(Player player) {

        addPlayer(player);

        if (getCacheSize(blockBreakCache) >= 10) {
            blockBreakCache.forEach((uuid, stringIntegerMap) -> {
                stringIntegerMap.forEach((blockType, blockDestroyed) -> {
                    blockDataAccessService.addBlock.test(new Block(uuid, blockType, 0, blockDestroyed));
                });
            });
            blockBreakCache.clear();
        }

        if (getCacheSize(blockPlaceCache) >= 10) {
            blockPlaceCache.forEach((uuid, stringIntegerMap) -> {
                stringIntegerMap.forEach((blockType, blockPlaced) -> {
                    blockDataAccessService.addBlock.test(new Block(uuid, blockType, blockPlaced, 0));
                });
            });
            blockPlaceCache.clear();
        }

    }

    // returns the total number of block broke/placed
    private int getCacheSize(Map<UUID, Map<String, Integer>> cache) {
        AtomicInteger size = new AtomicInteger(0);
        cache.forEach((uuid, stringIntegerMap) -> {
            stringIntegerMap.forEach((blockType, blockDestroyed) -> {
                size.set(size.get() + blockDestroyed);
            });
        });
        return size.get();
    }


}
