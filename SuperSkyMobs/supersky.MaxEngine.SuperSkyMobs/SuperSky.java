package rubukkit.BeYkeR.FullBright;

import java.io.File;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class FullBright extends JavaPlugin implements Listener {
  public static String pluginName = "[FullBright] ";
  
  public void onEnable() {
    PluginDescriptionFile pdFile = getDescription();
    getServer().getPluginManager().registerEvents(new BlockListener(this), (Plugin)this);
    getServer().getPluginManager().registerEvents(new Mobs(this), (Plugin)this);
    try {
      FileConfiguration fc = getConfig();
      if (!(new File(getDataFolder(), "config.yml")).exists()) {
        fc.options().header("FullBright v" + pdFile.getVersion() + " Configuration\nby BeYkeR");
        fc.addDefault("mode", "BREAK");
        fc.addDefault("light", Integer.valueOf(4));
        fc.addDefault("zombie-amount", Integer.valueOf(4));
        List worlds = getServer().getWorlds();
        fc.addDefault("tabcolors", Boolean.valueOf(false));
        fc.options().copyDefaults(true);
        saveConfig();
        fc.options().copyDefaults(false);
        fc.addDefault("mobequip", Boolean.valueOf(true));
        fc.addDefault("mobequipchance", Integer.valueOf(80));
        fc.addDefault("mobequipenchance", Integer.valueOf(50));
        fc.addDefault("mobequipdropchance", Integer.valueOf(10));
        fc.addDefault("debug", Boolean.valueOf(false));
        fc.addDefault("mobzombiespeed", Boolean.valueOf(true));
        fc.addDefault("mobzombiespeedChance", Integer.valueOf(70));
        fc.addDefault("is_world_nether_SpawnLimit", Boolean.valueOf(true));
        fc.addDefault("world_nether_SpawnLimit", Integer.valueOf(100));
        fc.addDefault("is_world_the_end_SpawnLimit", Boolean.valueOf(true));
        fc.addDefault("world_the_end_SpawnLimit", Integer.valueOf(100));
        fc.addDefault("weaponenclvl", Integer.valueOf(10));
        fc.addDefault("CreeperPoweredChance", Integer.valueOf(25));
        fc.addDefault("mobweapondropchance", Integer.valueOf(5));
        fc.addDefault("enchantTag", Boolean.valueOf(false));
      } 
      if (fc.getBoolean("tabcolor"))
        getServer().getConsoleSender().sendMessage(pluginName + "Tab colors is active!"); 
      if (getConfig().getBoolean("is_world_nether_SpawnLimit"))
        Bukkit.getWorld("world_nether").setMonsterSpawnLimit(getConfig().getInt("world_nether_SpawnLimit")); 
      if (getConfig().getBoolean("is_world_the_end_SpawnLimit"))
        Bukkit.getWorld("world_the_end").setMonsterSpawnLimit(getConfig().getInt("world_the_end_SpawnLimit")); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    if (args.length == 0) {
      if (cmd.getName().equalsIgnoreCase("fb")) {
        sender.sendMessage(ChatColor.BLUE + "===" + ChatColor.GRAY + "FullBright v1.0" + ChatColor.RED + "===");
        sender.sendMessage(ChatColor.GRAY + "[FullBright] " + ChatColor.GOLD + "/fb mode [BREAK/SPAWN]");
        sender.sendMessage(ChatColor.GRAY + "[FullBright] " + ChatColor.GOLD + "/fb light [);
        sender.sendMessage(ChatColor.GRAY + "[FullBright] " + ChatColor.GOLD + "/fb zombie [);
        sender.sendMessage(ChatColor.GRAY + "[FullBright] " + ChatColor.GOLD + "/fb limit [);
        return true;
      } 
    } else {
      if (args[0].equalsIgnoreCase("debug") && sender.hasPermission("fb.debug")) {
        getConfig().set("debug", Boolean.valueOf(!getConfig().getBoolean("debug")));
        if (getConfig().getBoolean("debug")) {
          sender.sendMessage(ChatColor.GRAY + "[FullBright] Debug ON");
        } else {
          sender.sendMessage(ChatColor.GRAY + "[FullBright] Debug OFF");
        } 
        return true;
      } 
      if (args[0].equalsIgnoreCase("rboat") && sender instanceof org.bukkit.command.ConsoleCommandSender) {
        sender.sendMessage(ChatColor.GRAY + "[FullBright] Removing boats...");
        for (Player pl : Bukkit.getOnlinePlayers()) {
          if (pl != null && 
            pl.getVehicle() instanceof Boat) {
            Boat boat = (Boat)pl.getVehicle();
            boat.eject();
            boat.remove();
            sender.sendMessage(ChatColor.GRAY + "[FullBright] " + ChatColor.RED + " Remove Boat. Player: " + pl.getDisplayName());
          } 
        } 
        return true;
      } 
      if (args[0].equalsIgnoreCase("mode")) {
        if (sender.hasPermission("fb.mode")) {
          if (args.length == 1) {
            sender.sendMessage(ChatColor.GRAY + "[FullBright] " + ChatColor.RED + ");
            sender.sendMessage(": mode [BREAK/SPAWN]");
            return true;
          } 
          if (args.length == 2) {
            getConfig().set("mode", args[1]);
            getConfig().options().copyDefaults(true);
            saveConfig();
            getConfig().options().copyDefaults(false);
            sender.sendMessage(ChatColor.GRAY + "[FullBright] " + ChatColor.GREEN + ");
            return true;
          } 
        } 
      } else if (args[0].equalsIgnoreCase("limit")) {
        if (sender.hasPermission("fb.limit")) {
          if (args.length == 1) {
            sender.sendMessage(ChatColor.GRAY + "[FullBright] " + ChatColor.RED + ");
            sender.sendMessage(": light [);
            return true;
          } 
          if (args.length == 2) {
            int limit = Integer.parseInt(args[1]);
            Bukkit.getWorld("world").setMonsterSpawnLimit(limit);
            sender.sendMessage(ChatColor.GRAY + "[FullBright] " + ChatColor.GREEN + ");
            return true;
          } 
        } 
      } else if (args[0].equalsIgnoreCase("light")) {
        if (sender.hasPermission("fb.light")) {
          if (args.length == 1) {
            sender.sendMessage(ChatColor.GRAY + "[FullBright] " + ChatColor.RED + ");
            sender.sendMessage(": light [);
            return true;
          } 
          if (args.length == 2) {
            int number = Integer.parseInt(args[1]);
            getConfig().set("light", Integer.valueOf(number));
            getConfig().options().copyDefaults(true);
            saveConfig();
            getConfig().options().copyDefaults(false);
            sender.sendMessage(ChatColor.GRAY + "[FullBright] " + ChatColor.GREEN + ");
            return true;
          } 
        } 
      } else if (args[0].equalsIgnoreCase("zombie") && sender.hasPermission("fb.zombie")) {
        if (args.length == 1) {
          sender.sendMessage(ChatColor.GRAY + "[FullBright] " + ChatColor.RED + ");
          sender.sendMessage(": zombie [);
          return true;
        } 
        if (args.length == 2) {
          if (getConfig().getString("mode").equals("SPAWN")) {
            int number = Integer.parseInt(args[1]);
            getConfig().set("zombie-amount", Integer.valueOf(number));
            getConfig().options().copyDefaults(true);
            saveConfig();
            getConfig().options().copyDefaults(false);
            sender.sendMessage(ChatColor.GRAY + "[FullBright] " + ChatColor.GREEN + ");
            return true;
          } 
          sender.sendMessage(ChatColor.GRAY + "[FullBright]" + ChatColor.RED + "mode " + ChatColor.WHITE + getConfig().getString("mode") + ChatColor.RED + ". " + ChatColor.WHITE + "SPAWN" + ChatColor.RED + " );
          return true;
        } 
      } 
    } 
    return false;
  }
}
