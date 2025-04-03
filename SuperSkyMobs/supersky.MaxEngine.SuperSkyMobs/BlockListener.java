package rubukkit.BeYkeR.FullBright;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockListener implements Listener {
  public FullBright plugin;
  
  public BlockListener(FullBright instance) {
    this.plugin = instance;
  }
  
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent e) {
    if (e.isCancelled() || e.getPlayer() == null)
      return; 
    Player p = e.getPlayer();
    if (p.getWorld().equals(Bukkit.getWorlds().get(1)) && e.getTo().getBlockY() > 127) {
      Location spawn = new Location(Bukkit.getWorlds().get(0), -71.0D, 64.0D, -204.0D);
      p.teleport(spawn);
    } 
    if (this.plugin.getConfig().getBoolean("parkour-teleport")) {
      if (!p.getWorld().equals(Bukkit.getWorlds().get(2)))
        return; 
      if (e.getTo().getBlockY() < 10) {
        Location spawn = new Location(Bukkit.getWorlds().get(0), -222.0D, 64.0D, -160.0D);
        p.teleport(spawn);
      } 
    } 
  }
  
  @EventHandler
  public void onPayerChat(AsyncPlayerChatEvent e) {
    if (e.isCancelled() || e.getPlayer() == null)
      return; 
    String message = e.getMessage();
    for (Player p : Bukkit.getOnlinePlayers()) {
      if (!message.toLowerCase().contains(p.getName().toLowerCase()))
        continue; 
      p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 3.0F, 0.5F);
    } 
  }
  
  @EventHandler
  public void onEnchantItem(EnchantItemEvent e) {
    if (e.isCancelled())
      return; 
    if (!this.plugin.getConfig().getBoolean("enchantTag"))
      return; 
    Player p = e.getEnchanter();
    ItemStack i = e.getItem();
    if (i == null)
      return; 
    ItemMeta im = i.getItemMeta();
    List<String> lore = new ArrayList<>();
    if (im.hasLore())
      lore = im.getLore(); 
    lore.add(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "" + ChatColor.GREEN + p.getDisplayName());
    im.setLore(lore);
    i.setItemMeta(im);
  }
  
  @EventHandler
  public void onEntityDeathEvent(EntityDeathEvent event) {
    if (event.getEntity() instanceof org.bukkit.entity.Monster && event.getEntity().getKiller() instanceof Player) {
      Player p = event.getEntity().getKiller();
      if (p.hasPermission("fb.doublexp")) {
        event.setDroppedExp(event.getDroppedExp() * 2);
      } else if (p.hasPermission("fb.extraexp")) {
        event.setDroppedExp(event.getDroppedExp() * 3);
      } else if (p.hasPermission("fb.superexp")) {
        event.setDroppedExp(event.getDroppedExp() * 10);
      } 
    } 
  }
  
  @EventHandler
  public void onSpawn(CreatureSpawnEvent event) {
    if (event.isCancelled())
      return; 
    LivingEntity e = event.getEntity();
    if (e.getWorld().equals(Bukkit.getWorlds().get(0))) {
      if (!event.getEntityType().equals(EntityType.GUARDIAN))
        return; 
      if ((new Random()).nextInt(100) > 10)
        event.setCancelled(true); 
      return;
    } 
  }
}
