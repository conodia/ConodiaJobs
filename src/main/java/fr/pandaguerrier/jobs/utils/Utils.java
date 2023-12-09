package fr.pandaguerrier.jobs.utils;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import fr.pandaguerrier.jobs.ConodiaJobs;
import fr.pandaguerrier.jobs.contracts.JobsContract;
import fr.pandaguerrier.jobs.enums.Jobs;
import fr.pandaguerrier.jobs.managers.FLevelManager;
import fr.pandaguerrier.jobs.managers.JobsManager;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

public class Utils {
  public static JobsManager createPlayer(Player player) {
    JobsManager playerJobs = new JobsManager(player, JobsContract.init(player, Jobs.MINER), JobsContract.init(player, Jobs.HUNTER), JobsContract.init(player, Jobs.CONSTRUCTOR), JobsContract.init(player, Jobs.FARMER), JobsContract.init(player, Jobs.WOODCUTTER), 0);
    playerJobs.create();

    return playerJobs;
  }

  public static FLevelManager createFaction(Faction faction) {
    FLevelManager fLevelManager = new FLevelManager(0, faction, 0, 0.0);
    fLevelManager.create();

    return fLevelManager;
  }

  public static FLevelManager getFaction(Player player) {
    FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);

    if (fPlayer == null || fPlayer.getFaction() == null || fPlayer.getFaction().getTag() == null || fPlayer.getFaction().isWilderness()) {
      return null;
    }

    return ConodiaJobs.getInstance().getfLevelCache().getCache().get(fPlayer.getFaction().getTag());
  }

  public static boolean isPlayerOnline(UUID uuid) {
    try {
      Player player = Bukkit.getPlayer(uuid);
      return player.isOnline();
    } catch(Exception ignored) {}
    return false;
  }

  public static void sendActionBar(Player player, String text) {
    PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(text), (byte) 2);
    ((CraftPlayer) Bukkit.getPlayer(player.getName())).getHandle().playerConnection.sendPacket(packet);
  }

  public static ItemStack createGuiItem(Material material, String name, int data, String... lore) {
    ItemStack item = new ItemStack(material, 1, (short) data);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(name);
    meta.setLore(Arrays.asList(lore));
    item.setItemMeta(meta);
    return item;
  }

  public static void setBorders(Inventory inv) {
    inv.setItem(0, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
    inv.setItem(1, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
    inv.setItem(7, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
    inv.setItem(8, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
    inv.setItem(9, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
    inv.setItem(17, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
    inv.setItem(27, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
    inv.setItem(36, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
    inv.setItem(37, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
    inv.setItem(35, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
    inv.setItem(43, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
    inv.setItem(44, Utils.createGuiItem(Material.INK_SACK, "§cRetour", 1, ""));
  }
}
