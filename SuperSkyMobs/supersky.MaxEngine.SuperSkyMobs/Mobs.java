package rubukkit.BeYkeR.FullBright;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Mobs implements Listener {
  Color[] clr = new Color[] { 
      Color.WHITE, Color.SILVER, Color.GRAY, Color.BLACK, Color.RED, Color.MAROON, Color.YELLOW, Color.OLIVE, Color.LIME, Color.GREEN, 
      Color.AQUA, Color.TEAL, Color.BLUE, Color.NAVY, Color.FUCHSIA, Color.PURPLE };
  
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
    return (this.random.nextInt(100) < chance);
  }
  
  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onCreatureWearArmour(CreatureSpawnEvent e) {
    float dropchance = this.plugin.getConfig().getInt("mobequipdropchance") / 100.0F;
    float dropweaponchance = this.plugin.getConfig().getInt("mobweapondropchance") / 100.0F;
    int eqenchance = this.plugin.getConfig().getInt("mobequipenchance");
    if (e.getLocation().getWorld().equals(Bukkit.getWorlds().get(0))) {
      if (e.getEntityType() == EntityType.PIG_ZOMBIE) {
        e.setCancelled(true);
        return;
      } 
      if (e.getEntityType() == EntityType.IRON_GOLEM) {
        if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM)
          e.setCancelled(true); 
        return;
      } 
    } 
    if (this.plugin.getConfig().getBoolean("mobequip")) {
      if (!e.getLocation().getWorld().equals(Bukkit.getWorlds().get(0)))
        return; 
      if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL)
        return; 
      LivingEntity lent = e.getEntity();
      if (e.getEntityType() != EntityType.SKELETON && e.getEntityType() != EntityType.ZOMBIE && e.getEntityType() != EntityType.CREEPER)
        return; 
      if (e.getEntityType() == EntityType.CREEPER) {
        if (rollDiceChance(this.plugin.getConfig().getInt("CreeperPoweredChance")))
          ((Creeper)e.getEntity()).setPowered(true); 
        return;
      } 
      Zombie z;
      if (e.getEntityType() == EntityType.ZOMBIE && (z = (Zombie)e.getEntity()).isBaby())
        return; 
      if (!rollDiceChance(this.plugin.getConfig().getInt("mobequipchance")))
        return; 
      ItemStack item = getRndItem(this.eqhelm);
      if (rollDiceChance(eqenchance))
        item = setRandomEnchantment(item, this.helmette, 4); 
      lent.getEquipment().setHelmet(item);
      lent.getEquipment().setHelmetDropChance(dropchance);
      item = getRndItem(this.eqchestplate);
      if (rollDiceChance(eqenchance))
        item = setRandomEnchantment(item, this.chestplate, 4); 
      lent.getEquipment().setChestplate(item);
      lent.getEquipment().setChestplateDropChance(dropchance);
      item = getRndItem(this.leggings);
      if (rollDiceChance(eqenchance))
        item = setRandomEnchantment(item, this.leggins, 4); 
      lent.getEquipment().setLeggings(item);
      lent.getEquipment().setLeggingsDropChance(dropchance);
      item = getRndItem(this.boots);
      if (rollDiceChance(eqenchance))
        item = setRandomEnchantment(item, this.bootenc, 4); 
      lent.getEquipment().setBoots(item);
      lent.getEquipment().setBootsDropChance(dropchance);
      item = getRndItem(this.weapons);
      if (rollDiceChance(eqenchance) && lent.getType() == EntityType.ZOMBIE)
        item = setRandomEnchantment(item, this.weaponenc, this.plugin.getConfig().getInt("weaponenclvl")); 
      lent.getEquipment().setItemInMainHand(item);
      lent.getEquipment().setItemInMainHandDropChance(dropweaponchance);
      if (lent.getType() == EntityType.SKELETON && rollDiceChance(100)) {
        ItemStack bow = new ItemStack(Material.BOW);
        if (rollDiceChance(eqenchance))
          bow = setRandomEnchantment(bow, this.encbow, 4); 
        lent.getEquipment().setItemInMainHand(bow);
        lent.getEquipment().setItemInMainHandDropChance(dropweaponchance);
      } 
      if (lent.getType() == EntityType.ZOMBIE && this.plugin.getConfig().getBoolean("mobzombiespeed") && rollDiceChance(this.plugin.getConfig().getInt("mobzombiespeedChance")))
        lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1728000, 2)); 
    } 
  }
  
  public ItemStack getRndItem(String str) {
    if (str.isEmpty())
      return new ItemStack(Material.AIR); 
    String[] ln = str.split(",");
    if (ln.length == 0)
      return new ItemStack(Material.AIR); 
    ItemStack item = parseItemStack(ln[this.random.nextInt(ln.length)]);
    if (item == null)
      return new ItemStack(Material.AIR); 
    item.setAmount(1);
    item = colorizeRndLeather(item);
    return item;
  }
  
  public ItemStack colorizeRndLeather(ItemStack item) {
    if (item.getType() != Material.LEATHER_BOOTS && item.getType() != Material.LEATHER_CHESTPLATE && item.getType() != Material.LEATHER_HELMET && item.getType() != Material.LEATHER_LEGGINGS)
      return item.clone(); 
    LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
    meta.setColor(this.clr[this.random.nextInt(this.clr.length)]);
    item.setItemMeta((ItemMeta)meta);
    return item.clone();
  }
  
  public ItemStack setRandomEnchantment(ItemStack item, String enchlist, int maxlevel) {
    ItemStack itm = item.clone();
    if (enchlist.isEmpty() || item.getType() == Material.AIR)
      return itm.clone(); 
    String[] ln = enchlist.split(",");
    String enc = ln[getRandomInt(ln.length)];
    NamespacedKey key = NamespacedKey.minecraft(enc);
    Enchantment e = Enchantment.getByKey(key);
    if (this.plugin.getConfig().getBoolean("debug")) {
      Bukkit.getServer().getConsoleSender().sendMessage("*** FB KEY: " + key.toString());
      Bukkit.getServer().getConsoleSender().sendMessage("*** FB ENC: " + e.toString());
    } 
    if (e == null)
      return itm.clone(); 
    int lvl = getRandomInt(maxlevel) + 1;
    if (this.plugin.getConfig().getBoolean("debug")) {
      Bukkit.getServer().getConsoleSender().sendMessage("*** FB ITEM: " + itm.toString());
      Bukkit.getServer().getConsoleSender().sendMessage("*** FB LVL: " + lvl);
    } 
    itm.addUnsafeEnchantment(e, lvl);
    return itm.clone();
  }
  
  public ItemStack parseItemStack(String itemstr) {
    if (itemstr.isEmpty())
      return null; 
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
    Material mat = null;
    int amount = 1;
    short data = 0;
    String[] si = istr.split("\\*");
    if (si.length > 0) {
      if (si.length == 2)
        amount = Math.max(getMinMaxRandom(si[1]), 1); 
      String[] ti;
      if ((ti = si[0].split(":")).length > 0) {
        if (ti[0].matches("[0-9]*")) {
          id = Integer.parseInt(ti[0]);
        } else {
          mat = Material.getMaterial(ti[0].toUpperCase());
          if (mat == null)
            return null; 
        } 
        if (ti.length == 2 && ti[1].matches("[0-9]*"))
          data = Short.parseShort(ti[1]); 
        ItemStack item = new ItemStack(mat, amount, data);
        if (!enchant.isEmpty())
          item = setEnchantments(item, enchant); 
        if (!name.isEmpty()) {
          ItemMeta im = item.getItemMeta();
          im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name.replace("_", " ")));
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
    if (strmin.matches("[1-9]+[0-9]*"))
      min = Integer.parseInt(strmin); 
    max = min;
    if (strmax.matches("[1-9]+[0-9]*"))
      max = Integer.parseInt(strmax); 
    return (max > min) ? (min + tryChance(1 + max - min)) : min;
  }
  
  public ItemStack setEnchantments(ItemStack item, String enchants) {
    ItemStack i = item.clone();
    if (enchants.isEmpty())
      return i; 
    String[] ln;
    for (String ec : ln = enchants.split(",")) {
      if (!ec.isEmpty()) {
        Color clr = colorByName(ec);
        if (clr != null) {
          LeatherArmorMeta meta = (LeatherArmorMeta)i.getItemMeta();
          meta.setColor(clr);
          i.setItemMeta((ItemMeta)meta);
        } else {
          String ench = ec;
          int level = 1;
          if (ec.contains(":")) {
            ench = ec.substring(0, ec.indexOf(":"));
            level = Math.max(1, getMinMaxRandom(ec.substring(ench.length() + 1)));
          } 
          NamespacedKey key = new NamespacedKey((Plugin)this.plugin, ench.toUpperCase());
          Enchantment e;
          if ((e = Enchantment.getByKey(key)) != null)
            i.addUnsafeEnchantment(e, level); 
        } 
      } 
    } 
    return i;
  }
  
  public Color colorByName(String colorname) {
    Color[] clr = { 
        Color.WHITE, Color.SILVER, Color.GRAY, Color.BLACK, Color.RED, Color.MAROON, Color.YELLOW, Color.OLIVE, Color.LIME, Color.GREEN, 
        Color.AQUA, Color.TEAL, Color.BLUE, Color.NAVY, Color.FUCHSIA, Color.PURPLE };
    String[] clrs = { 
        "WHITE", "SILVER", "GRAY", "BLACK", "RED", "MAROON", "YELLOW", "OLIVE", "LIME", "GREEN", 
        "AQUA", "TEAL", "BLUE", "NAVY", "FUCHSIA", "PURPLE" };
    for (int i = 0; i < clrs.length; ) {
      if (!colorname.equalsIgnoreCase(clrs[i])) {
        i++;
        continue;
      } 
      return clr[i];
    } 
    return null;
  }
  
  public boolean isIdInList(int id, String str) {
    String[] ln;
    if (!str.isEmpty() && (ln = str.split(",")).length > 0)
      for (int i = 0; i < ln.length; ) {
        if (ln[i].isEmpty() || !ln[i].matches("[0-9]*") || Integer.parseInt(ln[i]) != id) {
          i++;
          continue;
        } 
        return true;
      }  
    return false;
  }
}
