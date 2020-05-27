package com.rzheng.zhengcraft.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ZhengCraftMainListener implements Listener {

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e)
    {
        e.getPlayer().sendMessage("You broke " + e.getBlock().getBlockData().getMaterial());
//        e.getPlayer().getUniqueId()
    }
}
