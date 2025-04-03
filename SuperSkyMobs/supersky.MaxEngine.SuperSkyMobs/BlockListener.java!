package rubukkit.BeYkeR.FullBright;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
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
import rubukkit.BeYkeR.FullBright.FullBright;

public class BlockListener
implements Listener {
    public FullBright plugin;

    public BlockListener(FullBright instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Location spawn;
        if (e.isCancelled() || e.getPlayer() == null) {
            return;
        }
        Player p = e.getPlayer();
        if (p.getWorld().equals(Bukkit.getWorlds().get(1)) && e.getTo().getBlockY() > 127) {
            spawn = new Location((World)Bukkit.getWorlds().get(0), -71.0, 64.0, -204.0);
            p.teleport(spawn);
        }
        if (this.plugin.getConfig().getBoolean("parkour-teleport")) {
            if (!p.getWorld().equals(Bukkit.getWorlds().get(2))) {
                return;
            }
            if (e.getTo().getBlockY() < 10) {
                spawn = new Location((World)Bukkit.getWorlds().get(0), -222.0, 64.0, -160.0);
                p.teleport(spawn);
            }
        }
    }

    @EventHandler
    public void onPayerChat(AsyncPlayerChatEvent e) {
        if (e.isCancelled() || e.getPlayer() == null) {
            return;
        }
        String message = e.getMessage();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!message.toLowerCase().contains(p.getName().toLowerCase())) continue;
            p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 3.0f, 0.5f);
        }
    }

    @EventHandler
    public void onEnchantItem(EnchantItemEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (!this.plugin.getConfig().getBoolean("enchantTag")) {
            return;
        }
        Player p = e.getEnchanter();
        ItemStack i = e.getItem();
        if (i == null) {
            return;
        }
        ItemMeta im = i.getItemMeta();
        List<String> lore = new ArrayList<String>();
        if (im.hasLore()) {
            lore = im.getLore();
        }
        lore.add((Object)ChatColor.BOLD + "" + (Object)ChatColor.LIGHT_PURPLE + "\u0417\u0430\u0447\u0430\u0440\u043e\u0432\u0430\u043b: " + (Object)ChatColor.GREEN + p.getDisplayName());
        im.setLore(lore);
        i.setItemMeta(im);
    }

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        if (event.getEntity() instanceof Monster && event.getEntity().getKiller() instanceof Player) {
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
        if (event.isCancelled()) {
            return;
        }
        LivingEntity e = event.getEntity();
        if (e.getWorld().equals(Bukkit.getWorlds().get(0))) {
            if (!event.getEntityType().equals((Object)EntityType.GUARDIAN)) {
                return;
            }
            if (new Random().nextInt(100) > 10) {
                event.setCancelled(true);
            }
            return;
        }
    }
}
