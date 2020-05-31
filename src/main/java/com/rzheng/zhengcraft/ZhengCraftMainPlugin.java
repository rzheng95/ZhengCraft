package com.rzheng.zhengcraft;

import com.rzheng.zhengcraft.commands.ZhengCraftMainCommand;
import com.rzheng.zhengcraft.listeners.ZhengCraftMainListener;
import org.bukkit.plugin.java.JavaPlugin;

public class ZhengCraftMainPlugin extends JavaPlugin {

    @Override
    public void onEnable()
    {
        this.getServer().getPluginManager().registerEvents(new ZhengCraftMainListener(), this);

        this.getCommand("zhengcraft").setExecutor(new ZhengCraftMainCommand());


    }


}
