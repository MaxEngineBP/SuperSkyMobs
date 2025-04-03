/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Creeper
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Zombie
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.CreatureSpawnEvent
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.LeatherArmorMeta
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package rubukkit.BeYkeR.FullBright;

import java.util.Random;

import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rubukkit.BeYkeR.FullBright.FullBright;

import static org.bukkit.Bukkit.getServer;

public class Mobs
implements Listener {
    Color[] clr = new Color[]{Color.WHITE, Color.SILVER, Color.GRAY, Color.BLACK, Color.RED, Color.MAROON, Color.YELLOW, Color.OLIVE, Color.LIME, Color.GREEN, Color.AQUA, Color.TEAL, Color.BLUE, Color.NAVY, Color.FUCHSIA, Color.PURPLE};
    private String eqhelm = "DIAMOND_HELMET,iron_HELMET,golden_HELMET";
    String eqchestplate = "DIAMOND_CHESTPLATE,iron_CHESTPLATE,golden_CHESTPLATE";
    String leggings = "DIAMOND_LEGGINGS,iron_LEGGINGS,golden_LEGGINGS";
    String boots = "iron_boots,golden_boots,diamond_boots";
    String weapons = "iron_axe,gold_axe,iron_sword,gold_sword,diamond_axe,diamond_sword";

    String helmette = "protection,fire_protection,blast_protection,projectile_protection,thorns,unbreaking,respiration,aqua_affinity";

    String chestplate = "protection,fire_protection,blast_protection,projectile_protection,thorns,unbreaking";
    String leggins = "protection,fire_protection,blast_protection,projectile_protection,thorns,unbreaking";
    String bootenc = "protection,fire_protection,blast_protection,projectile_protection,thorns,unbreaking,feather_falling";

    String weaponenc = "sharpness,smite,bane_of_arthropods,knockback,fire_aspect,looting";
    String encbow = "power,punch,flame,infinity,unbreaking";
    public FullBright plugin;
    Random random = new Random();

    public int getRandomInt(int maxvalue) {
        return this.random.nextInt(maxvalue);
    }

    public int tryChance(int chance) {
        return this.random.nextInt(chance);
    }

    public Mobs(FullBright instance) {
        this.plugin = instance;
    }

    public boolean rollDiceChance(int chance) {
        return this.random.nextInt(100) < chance;
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onCreatureWearArmour(CreatureSpawnEvent e) {
        float dropchance = (float)this.plugin.getConfig().getInt("mobequipdropchance") / 100.0f;
        float dropweaponchance = (float)this.plugin.getConfig().getInt("mobweapondropchance") / 100.0f;
        int eqenchance = this.plugin.getConfig().getInt("mobequipenchance");
//        if (this.plugin.getConfig().getBoolean("debug")) {
//            this.plugin.getServer().getConsoleSender().sendMessage("[FullBright] Drop chance: " + dropchance);
//        }
        if (e.getLocation().getWorld().equals(Bukkit.getWorlds().get(0))) {
            if (e.getEntityType() == EntityType.PIG_ZOMBIE) {
//                getServer().getConsoleSender().sendMessage(e.getEntityType().toString() + ": " + e.getSpawnReason().toString());
                e.setCancelled(true);
                return;
            }
            if (e.getEntityType() == EntityType.IRON_GOLEM) {
//                getServer().getConsoleSender().sendMessage(e.getEntityType().toString() + ": " + e.getSpawnReason().toString());
                if (e.getSpawnReason()!= CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM) {
                    e.setCancelled(true);
                }
                return;
            }

        }

        if (this.plugin.getConfig().getBoolean("mobequip")) {
            Zombie z;
            if (!e.getLocation().getWorld().equals(Bukkit.getWorlds().get(0))) {
                return;
            }
            if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL) {
                return;
            }
            LivingEntity lent = e.getEntity();
            if (e.getEntityType() != EntityType.SKELETON && e.getEntityType() != EntityType.ZOMBIE && e.getEntityType() != EntityType.CREEPER) {
                return;
            }
            if (e.getEntityType() == EntityType.CREEPER) {
                if (this.rollDiceChance(this.plugin.getConfig().getInt("CreeperPoweredChance"))) {
                    ((Creeper)e.getEntity()).setPowered(true);
                }
                return;
            }
            if (e.getEntityType() == EntityType.ZOMBIE && (z = (Zombie)e.getEntity()).isBaby()) {
                return;
            }
            if (!this.rollDiceChance(this.plugin.getConfig().getInt("mobequipchance"))) {
                return;
            }
            ItemStack item = this.getRndItem(this.eqhelm);
            if (this.rollDiceChance(eqenchance)) {
                item = this.setRandomEnchantment(item, this.helmette, 4);
            }
            lent.getEquipment().setHelmet(item);
            lent.getEquipment().setHelmetDropChance(dropchance);
            item = this.getRndItem(this.eqchestplate);
            if (this.rollDiceChance(eqenchance)) {
                item = this.setRandomEnchantment(item, this.chestplate, 4);
            }
                        lent.getEquipment().setChestplate(item);
            lent.getEquipment().setChestplateDropChance(dropchance);

            item = this.getRndItem(this.leggings);
            if (this.rollDiceChance(eqenchance)) {
                item = this.setRandomEnchantment(item, this.leggins, 4);
            }
                        lent.getEquipment().setLeggings(item);
            lent.getEquipment().setLeggingsDropChance(dropchance);

            item = this.getRndItem(this.boots);
            if (this.rollDiceChance(eqenchance)) {
                item = this.setRandomEnchantment(item, this.bootenc, 4);
            }
                        lent.getEquipment().setBoots(item);
            lent.getEquipment().setBootsDropChance(dropchance);

            item = this.getRndItem(this.weapons);
            if (this.rollDiceChance(eqenchance) && lent.getType() == EntityType.ZOMBIE) {
                item = this.setRandomEnchantment(item, this.weaponenc, this.plugin.getConfig().getInt("weaponenclvl"));
            }
            lent.getEquipment().setItemInMainHand(item);
            lent.getEquipment().setItemInMainHandDropChance(dropweaponchance);

            if (lent.getType() == EntityType.SKELETON && this.rollDiceChance(100)) {
                ItemStack bow = new ItemStack(Material.BOW);
                if (this.rollDiceChance(eqenchance)) {
                    bow = this.setRandomEnchantment(bow, this.encbow, 4);
                }
                lent.getEquipment().setItemInMainHand(bow);
                lent.getEquipment().setItemInMainHandDropChance(dropweaponchance);
            }

//            lent.set
            if (lent.getType() == EntityType.ZOMBIE && this.plugin.getConfig().getBoolean("mobzombiespeed") && this.rollDiceChance(this.plugin.getConfig().getInt("mobzombiespeedChance"))) {
                lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1728000, 2));
            }
        }
    }

    public ItemStack getRndItem(String str) {
        if (str.isEmpty()) {
            return new ItemStack(Material.AIR);
        }
        String[] ln = str.split(",");
        if (ln.length == 0) {
            return new ItemStack(Material.AIR);
        }
        ItemStack item = this.parseItemStack(ln[this.random.nextInt(ln.length)]);
        if (item == null) {
            return new ItemStack(Material.AIR);
        }
        item.setAmount(1);
        item = this.colorizeRndLeather(item);
        return item;
    }

    public ItemStack colorizeRndLeather(ItemStack item) {
        if (item.getType() != Material.LEATHER_BOOTS && item.getType() != Material.LEATHER_CHESTPLATE && item.getType() != Material.LEATHER_HELMET && item.getType() != Material.LEATHER_LEGGINGS) {
            return item.clone();
        }
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
        meta.setColor(this.clr[this.random.nextInt(this.clr.length)]);
        item.setItemMeta((ItemMeta)meta);
        return item.clone();
    }

    public ItemStack setRandomEnchantment(ItemStack item, String enchlist, int maxlevel) {
        ItemStack itm = item.clone();
        if (enchlist.isEmpty() || item.getType() == Material.AIR) {
            return itm.clone();
        }
        String[] ln = enchlist.split(",");
        String enc = ln[this.getRandomInt(ln.length)];

//        Enchantment e = Enchantment.getByName((String)enc.toUpperCase());
        NamespacedKey key = NamespacedKey.minecraft(enc);
        Enchantment e = Enchantment.getByKey(key);
        if (this.plugin.getConfig().getBoolean("debug")) {
            getServer().getConsoleSender().sendMessage("*** FB KEY: " + key.toString());
            getServer().getConsoleSender().sendMessage("*** FB ENC: " + e.toString());
        }
//        Enchantment e = Enchantment.DURABILITY;
        if (e == null) {
//            getServer().getConsoleSender().sendMessage("*** FB NULL");
            return itm.clone();
        }
        int lvl = getRandomInt(maxlevel) + 1;
        if (this.plugin.getConfig().getBoolean("debug")) {
//            getServer().getConsoleSender().sendMessage("*** FB " + e.toString());
            getServer().getConsoleSender().sendMessage("*** FB ITEM: " + itm.toString());
//            getServer().getConsoleSender().sendMessage("*** FB ITEM TYPE: " + itm.getType().toString());
            getServer().getConsoleSender().sendMessage("*** FB LVL: " + lvl);
        }
        itm.addUnsafeEnchantment(e, lvl);

//        itm.addUnsafeEnchantment(Enchantment.DURABILITY, 2); //this.getRandomInt(maxlevel) + 1);
        return itm.clone();
    }

    public ItemStack parseItemStack(String itemstr) {
        if (itemstr.isEmpty()) {
            return null;
        }
        String istr = itemstr;
        String enchant = "";
        String name = "";
        if (itemstr.contains("$")) {
            name = itemstr.substring(0, itemstr.indexOf("$"));
            istr = itemstr.substring(name.length() + 1);
        }
        if (istr.contains("@")) {
            enchant = istr.substring(istr.indexOf("@") + 1);
            istr = istr.substring(0, istr.indexOf("@"));
        }
        int id = -1;
        Material mat= null;
        int amount = 1;
        short data = 0;
        String[] si = istr.split("\\*");
        if (si.length > 0) {
            String[] ti;
            if (si.length == 2) {
                amount = Math.max(this.getMinMaxRandom(si[1]), 1);
            }
            if ((ti = si[0].split(":")).length > 0) {
                if (ti[0].matches("[0-9]*")) {
                    id = Integer.parseInt(ti[0]);
                } else {
                    mat = Material.getMaterial((String)ti[0].toUpperCase());
                    if (mat == null) {
                        return null;
                    }
//                    id = mat.getId();
                }
                if (ti.length == 2 && ti[1].matches("[0-9]*")) {
                    data = Short.parseShort(ti[1]);
                }
                ItemStack item = new ItemStack(mat, amount, data);
//                getServer().getConsoleSender().sendMessage("*** FB " + ti[0].toUpperCase());
                if (!enchant.isEmpty()) {
                    item = this.setEnchantments(item, enchant);
                }
                if (!name.isEmpty()) {
                    ItemMeta im = item.getItemMeta();
                    im.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)name.replace("_", " ")));
                    item.setItemMeta(im);
                }
                return item;
            }
        }
        return null;
    }

    public int getMinMaxRandom(String minmaxstr) {
        int min = 0;
        int max = 0;
        String strmin = minmaxstr;
        String strmax = minmaxstr;
        if (minmaxstr.contains("-")) {
            strmin = minmaxstr.substring(0, minmaxstr.indexOf("-"));
            strmax = minmaxstr.substring(minmaxstr.indexOf("-") + 1);
        }
        if (strmin.matches("[1-9]+[0-9]*")) {
            min = Integer.parseInt(strmin);
        }
        max = min;
        if (strmax.matches("[1-9]+[0-9]*")) {
            max = Integer.parseInt(strmax);
        }
        return max > min ? min + this.tryChance(1 + max - min) : min;
    }

    public ItemStack setEnchantments(ItemStack item, String enchants) {
        String[] ln;
        ItemStack i = item.clone();
        if (enchants.isEmpty()) {
            return i;
        }
        for (String ec : ln = enchants.split(",")) {
            Enchantment e;
            if (ec.isEmpty()) continue;
            Color clr = this.colorByName(ec);
            if (clr != null) {
//                if (!this.isIdInList(item.getTypeId(), "298,299,300,301")) continue;
                LeatherArmorMeta meta = (LeatherArmorMeta)i.getItemMeta();
                meta.setColor(clr);
                i.setItemMeta((ItemMeta)meta);
                continue;
            }
            String ench = ec;
            int level = 1;
            if (ec.contains(":")) {
                ench = ec.substring(0, ec.indexOf(":"));
                level = Math.max(1, this.getMinMaxRandom(ec.substring(ench.length() + 1)));
            }
            NamespacedKey key = new NamespacedKey(this.plugin, ench.toUpperCase());
            if ((e = Enchantment.getByKey(key)) == null) continue;
            i.addUnsafeEnchantment(e, level);
        }
        return i;
    }

    public Color colorByName(String colorname) {
        Color[] clr = new Color[]{Color.WHITE, Color.SILVER, Color.GRAY, Color.BLACK, Color.RED, Color.MAROON, Color.YELLOW, Color.OLIVE, Color.LIME, Color.GREEN, Color.AQUA, Color.TEAL, Color.BLUE, Color.NAVY, Color.FUCHSIA, Color.PURPLE};
        String[] clrs = new String[]{"WHITE", "SILVER", "GRAY", "BLACK", "RED", "MAROON", "YELLOW", "OLIVE", "LIME", "GREEN", "AQUA", "TEAL", "BLUE", "NAVY", "FUCHSIA", "PURPLE"};
        for (int i = 0; i < clrs.length; ++i) {
            if (!colorname.equalsIgnoreCase(clrs[i])) continue;
            return clr[i];
        }
        return null;
    }

    public boolean isIdInList(int id, String str) {
        String[] ln;
        if (!str.isEmpty() && (ln = str.split(",")).length > 0) {
            for (int i = 0; i < ln.length; ++i) {
                if (ln[i].isEmpty() || !ln[i].matches("[0-9]*") || Integer.parseInt(ln[i]) != id) continue;
                return true;
            }
        }
        return false;
    }
}

