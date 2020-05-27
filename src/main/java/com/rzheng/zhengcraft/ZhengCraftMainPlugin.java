package com.rzheng.zhengcraft;

import com.rzheng.zhengcraft.command.ZhengCraftMainCommand;
import com.rzheng.zhengcraft.listener.ZhengCraftMainListener;
import org.bukkit.plugin.java.JavaPlugin;

public class ZhengCraftMainPlugin extends JavaPlugin {
    @Override
    public void onEnable()
    {
        this.getServer().getPluginManager().registerEvents(new ZhengCraftMainListener(), this);

        this.getCommand("zhengcraft").setExecutor(new ZhengCraftMainCommand());

    }
}
