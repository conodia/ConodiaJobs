package fr.pandaguerrier.newjob.listeners;

import com.massivecraft.factions.listeners.FactionsBlockListener;
import fr.pandaguerrier.newjob.ConodiaJobs;
import fr.pandaguerrier.newjob.contracts.JobsAbstract;
import fr.pandaguerrier.newjob.jobs.*;
import fr.pandaguerrier.newjob.ui.JobInventory;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void handle(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        ItemStack clicked = event.getCurrentItem();


        if(inventory == null || !inventory.getName().equals("§9➜ §bJobs") || !clicked.hasItemMeta() || !clicked.getItemMeta().hasDisplayName()) return;
        event.setCancelled(true);

        if (clicked.getItemMeta().getDisplayName().equals("§cRetour")) {
            player.closeInventory();
            player.chat("/menu");

            return;
        }

        for (JobsAbstract job : ConodiaJobs.getInstance().getJobs().getJobs()) {
            if (clicked.getItemMeta().getDisplayName().equals(job.getFormattedJobName())) {
                new JobInventory(player, job);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void jobs(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();

        if(inventory == null) return;
        boolean isJobGUI = false;

        for (JobsAbstract job : ConodiaJobs.getInstance().getJobs().getJobs()) {
            if (inventory.getName().equals(job.getFormattedJobName())) isJobGUI = true;
        }

        if(!isJobGUI) return;

        event.setCancelled(true);
        ItemStack clicked = event.getCurrentItem();
        if(!clicked.hasItemMeta() || !clicked.getItemMeta().hasDisplayName()) return;

        if (clicked.getItemMeta().getDisplayName().equals("§cRetour")) {
            player.closeInventory();
            player.chat("/jobs");
        }
    }
}
