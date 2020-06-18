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

package com.rzheng.zhengcraft.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class ConfigManager
{
    private static boolean initialized;
    private static ConfigManager instance;

    private Plugin plugin;
    private FileConfiguration config;

    private ConfigManager(Plugin plugin)
    {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig()
    {
        verifyFile();
        this.plugin.reloadConfig();
        this.config = this.plugin.getConfig();
    }

    private void verifyFile()
    {
        File file = new File(this.plugin.getDataFolder(), "config.yml");
        if(!file.exists())
        {
            this.plugin.saveDefaultConfig();
        }
    }

    public static void initialize(Plugin plugin)
    {
        if(!initialized)
        {
            instance = new ConfigManager(plugin);
            initialized = true;
        }
    }

    public static boolean reload()
    {
        if(!initialized)
            return false;
        try
        {
            getInstance().loadConfig();
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static ConfigManager getInstance()
    {
        return ConfigManager.instance;
    }

    public float getPower()
    {
        return Float.parseFloat(this.config.getString("general_settings.power", "2.0"));
    }

    public boolean getOnFire()
    {
        return config.getBoolean("general_settings.on_fire", true);
    }

    public void setPower(float p)
    {
        config.set("general_settings.power", p);
    }

    public void setOnFire(boolean b)
    {
        config.set("general_settings.on_fire", b);
    }
}

