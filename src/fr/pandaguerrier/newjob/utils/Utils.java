package fr.pandaguerrier.newjob.utils;

import fr.pandaguerrier.newjob.ConodiaJobs;
import fr.pandaguerrier.newjob.contracts.JobsAbstract;
import fr.pandaguerrier.newjob.entities.PlayerCache;
import fr.pandaguerrier.newjob.entities.PlayersEntitites;
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
import java.util.HashMap;
import java.util.UUID;

public class Utils {
    private static final HashMap<UUID, PlayersEntitites> playersJobs = ConodiaJobs.getInstance().getPlayersJobs();
    public static PlayersEntitites createPlayer(Player player) {
        PlayersEntitites playersEntitites = new PlayersEntitites();
        ConodiaJobs.getInstance().getPlayersJobs().put(player.getUniqueId(), playersEntitites);
        return playersEntitites;
    }

    public static PlayerCache getPlayer(Player player, JobsAbstract job) {
        PlayersEntitites playerJob = playersJobs.get(player.getUniqueId());
        if(playerJob == null) createPlayer(player);

        assert playerJob != null;
        return playerJob.getPlayerStats().get(job.getJobName());
    }

    public static void sendActionBar(Player player, String text) {
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(text), (byte) 2);
        ((CraftPlayer) Bukkit.getPlayer(player.getName())).getHandle().playerConnection.sendPacket(packet);
    }

    public static ItemStack createGuiItem(Material material, String name, int data, String... lore) {
        ItemStack item = new ItemStack(material, 1, (short)data);
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
