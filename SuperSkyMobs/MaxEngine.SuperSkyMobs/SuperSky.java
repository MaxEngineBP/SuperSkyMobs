/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.FileConfigurationOptions
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginDescriptionFile
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.java.JavaPlugin
 */
package rubukkit.BeYkeR.FullBright;

import java.io.File;
import java.util.List;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import rubukkit.BeYkeR.FullBright.BlockListener;
import rubukkit.BeYkeR.FullBright.Mobs;

public class FullBright
extends JavaPlugin
implements Listener {
    public static String pluginName = "[FullBright] ";

    public void onEnable() {
        PluginDescriptionFile pdFile = this.getDescription();
        this.getServer().getPluginManager().registerEvents((Listener)new BlockListener(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new Mobs(this), (Plugin)this);
        try {
            FileConfiguration fc = this.getConfig();
            if (!new File(this.getDataFolder(), "config.yml").exists()) {
                fc.options().header("FullBright v" + pdFile.getVersion() + " Configuration\nby BeYkeR");
                fc.addDefault("mode", (Object)"BREAK");
                fc.addDefault("light", (Object)4);
                fc.addDefault("zombie-amount", (Object)4);
                List worlds = this.getServer().getWorlds();
//                for (World world : worlds) {
//                    fc.addDefault("Worlds." + world.getName(), (Object)true);
//                }
                fc.addDefault("tabcolors", (Object)false);
                fc.options().copyDefaults(true);
                this.saveConfig();
                fc.options().copyDefaults(false);
                fc.addDefault("mobequip", (Object)true);
                fc.addDefault("mobequipchance", (Object)80);
                fc.addDefault("mobequipenchance", (Object)50);
                fc.addDefault("mobequipdropchance", (Object)10);
                fc.addDefault("debug", (Object)false);
                fc.addDefault("mobzombiespeed", (Object)true);
                fc.addDefault("mobzombiespeedChance", (Object)70);
                fc.addDefault("is_world_nether_SpawnLimit", (Object)true);
                fc.addDefault("world_nether_SpawnLimit", (Object)100);
                fc.addDefault("is_world_the_end_SpawnLimit", (Object)true);
                fc.addDefault("world_the_end_SpawnLimit", (Object)100);
                fc.addDefault("weaponenclvl", (Object)10);
                fc.addDefault("CreeperPoweredChance", (Object)25);
                fc.addDefault("mobweapondropchance", (Object)5);
                fc.addDefault("enchantTag", false);
            }
            if (fc.getBoolean("tabcolor")) {
                this.getServer().getConsoleSender().sendMessage(pluginName + "Tab colors is active!");
            }
            if (this.getConfig().getBoolean("is_world_nether_SpawnLimit")) {
                Bukkit.getWorld((String)"world_nether").setMonsterSpawnLimit(this.getConfig().getInt("world_nether_SpawnLimit"));
            }
            if (this.getConfig().getBoolean("is_world_the_end_SpawnLimit")) {
                Bukkit.getWorld((String)"world_the_end").setMonsterSpawnLimit(this.getConfig().getInt("world_the_end_SpawnLimit"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 0) {
            if (cmd.getName().equalsIgnoreCase("fb")) {
                sender.sendMessage((Object)ChatColor.BLUE + "===" + (Object)ChatColor.GRAY + "FullBright v1.0" + (Object)ChatColor.RED + "===");
                sender.sendMessage((Object)ChatColor.GRAY + "[FullBright] " + (Object)ChatColor.GOLD + "/fb mode [BREAK/SPAWN]");
                sender.sendMessage((Object)ChatColor.GRAY + "[FullBright] " + (Object)ChatColor.GOLD + "/fb light [\u0447\u0438\u0441\u043b\u043e]");
                sender.sendMessage((Object)ChatColor.GRAY + "[FullBright] " + (Object)ChatColor.GOLD + "/fb zombie [\u0447\u0438\u0441\u043b\u043e]");
                sender.sendMessage((Object)ChatColor.GRAY + "[FullBright] " + (Object)ChatColor.GOLD + "/fb limit [\u0447\u0438\u0441\u043b\u043e]");
                return true;
            }
        } else if (args[0].equalsIgnoreCase("debug") && sender.hasPermission("fb.debug")) {
            getConfig().set("debug", !getConfig().getBoolean("debug"));
            if (getConfig().getBoolean("debug")) {
                sender.sendMessage(ChatColor.GRAY + "[FullBright] Debug ON");
            } else {
                sender.sendMessage(ChatColor.GRAY + "[FullBright] Debug OFF");
            }
            return true;

        } else if ((args[0].equalsIgnoreCase("rboat")) && (sender instanceof ConsoleCommandSender)) {
            sender.sendMessage(ChatColor.GRAY + "[FullBright] " + "Removing boats...");
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (pl != null) {
                    if (pl.getVehicle() instanceof Boat) {
                        Vehicle boat = (Boat) pl.getVehicle();
                        boat.eject();
                        boat.remove();
                        sender.sendMessage(ChatColor.GRAY + "[FullBright] " + ChatColor.RED + " Remove Boat. Player: " + pl.getDisplayName());
                    }
                }
            }
            return true;
        } else if (args[0].equalsIgnoreCase("mode")) {
            if (sender.hasPermission("fb.mode")) {
                if (args.length == 1) {
                    sender.sendMessage((Object)ChatColor.GRAY + "[FullBright] " + (Object)ChatColor.RED + "\u0421\u043b\u0438\u0448\u043a\u043e\u043c \u043c\u0430\u043b\u043e \u0430\u0440\u0433\u0443\u043c\u0435\u043d\u0442\u043e\u0432");
                    sender.sendMessage("\u00a7c\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0439\u0442\u0435 : \u00a7f/fb mode [BREAK/SPAWN]");
                    return true;
                }
                if (args.length == 2) {
                    this.getConfig().set("mode", (Object)args[1]);
                    this.getConfig().options().copyDefaults(true);
                    this.saveConfig();
                    this.getConfig().options().copyDefaults(false);
                    sender.sendMessage((Object)ChatColor.GRAY + "[FullBright] " + (Object)ChatColor.GREEN + "\u041a\u043e\u043d\u0444\u0438\u0433 \u0438\u0437\u043c\u0435\u043d\u0435\u043d");
                    return true;
                }
            }
        } else if (args[0].equalsIgnoreCase("limit")) {
            if (sender.hasPermission("fb.limit")) {
                if (args.length == 1) {
                    sender.sendMessage((Object)ChatColor.GRAY + "[FullBright] " + (Object)ChatColor.RED + "\u0421\u043b\u0438\u0448\u043a\u043e\u043c \u043c\u0430\u043b\u043e \u0430\u0440\u0433\u0443\u043c\u0435\u043d\u0442\u043e\u0432");
                    sender.sendMessage("\u00a7c\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0439\u0442\u0435 : \u00a7f/fb light [\u0447\u0438\u0441\u043b\u043e]");
                    return true;
                }
                if (args.length == 2) {
                    int limit = Integer.parseInt(args[1]);
                    Bukkit.getWorld((String)"world").setMonsterSpawnLimit(limit);
                    sender.sendMessage((Object)ChatColor.GRAY + "[FullBright] " + (Object)ChatColor.GREEN + "\u041b\u0438\u043c\u0438\u0442 \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d");
                    return true;
                }
            }
        } else if (args[0].equalsIgnoreCase("light")) {
            if (sender.hasPermission("fb.light")) {
                if (args.length == 1) {
                    sender.sendMessage((Object)ChatColor.GRAY + "[FullBright] " + (Object)ChatColor.RED + "\u0421\u043b\u0438\u0448\u043a\u043e\u043c \u043c\u0430\u043b\u043e \u0430\u0440\u0433\u0443\u043c\u0435\u043d\u0442\u043e\u0432");
                    sender.sendMessage("\u00a7c\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0439\u0442\u0435 : \u00a7f/fb light [\u0447\u0438\u0441\u043b\u043e]");
                    return true;
                }
                if (args.length == 2) {
                    int number = Integer.parseInt(args[1]);
                    this.getConfig().set("light", (Object)number);
                    this.getConfig().options().copyDefaults(true);
                    this.saveConfig();
                    this.getConfig().options().copyDefaults(false);
                    sender.sendMessage((Object)ChatColor.GRAY + "[FullBright] " + (Object)ChatColor.GREEN + "\u041a\u043e\u043d\u0444\u0438\u0433 \u0438\u0437\u043c\u0435\u043d\u0435\u043d");
                    return true;
                }
            }
        } else if (args[0].equalsIgnoreCase("zombie") && sender.hasPermission("fb.zombie")) {
            if (args.length == 1) {
                sender.sendMessage((Object)ChatColor.GRAY + "[FullBright] " + (Object)ChatColor.RED + "\u0421\u043b\u0438\u0448\u043a\u043e\u043c \u043c\u0430\u043b\u043e \u0430\u0440\u0433\u0443\u043c\u0435\u043d\u0442\u043e\u0432");
                sender.sendMessage("\u00a7c\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0439\u0442\u0435 : \u00a7f/fb zombie [\u0447\u0438\u0441\u043b\u043e]");
                return true;
            }
            if (args.length == 2) {
                if (this.getConfig().getString("mode").equals("SPAWN")) {
                    int number = Integer.parseInt(args[1]);
                    this.getConfig().set("zombie-amount", (Object)number);
                    this.getConfig().options().copyDefaults(true);
                    this.saveConfig();
                    this.getConfig().options().copyDefaults(false);
                    sender.sendMessage((Object)ChatColor.GRAY + "[FullBright] " + (Object)ChatColor.GREEN + "\u041a\u043e\u043d\u0444\u0438\u0433 \u0438\u0437\u043c\u0435\u043d\u0435\u043d");
                    return true;
                }
                sender.sendMessage((Object)ChatColor.GRAY + "[FullBright]" + (Object)ChatColor.RED + "\u0417\u043d\u0430\u0447\u0435\u043d\u0438\u0435 mode \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d \u043d\u0430 " + (Object)ChatColor.WHITE + this.getConfig().getString("mode") + (Object)ChatColor.RED + ". \u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430 \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u0438\u0442\u0435 \u043d\u0430 " + (Object)ChatColor.WHITE + "SPAWN" + (Object)ChatColor.RED + " \u0434\u043b\u044f \u0434\u0430\u043b\u044c\u043d\u0435\u0439\u0448\u0435\u0433\u043e \u0438\u0437\u043c\u0435\u043d\u0435\u043d\u0438\u044f \u044d\u0442\u043e\u0433\u043e \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u044f.");
                return true;
            }
        }
        return false;
    }
}

z