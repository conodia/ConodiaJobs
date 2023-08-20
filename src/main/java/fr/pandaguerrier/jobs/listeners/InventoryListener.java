package fr.pandaguerrier.jobs.listeners;

import fr.pandaguerrier.jobs.enums.Jobs;
import fr.pandaguerrier.jobs.ui.JobInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

  @EventHandler(priority = EventPriority.LOWEST)
  public void handle(InventoryClickEvent event) {
    Player player = (Player) event.getWhoClicked();
    Inventory inventory = event.getClickedInventory();
    ItemStack clicked = event.getCurrentItem();


    if (inventory == null || !inventory.getName().equals("§9➜ §bJobs") || !clicked.hasItemMeta() || !clicked.getItemMeta().hasDisplayName())
      return;
    event.setCancelled(true);

    if (clicked.getItemMeta().getDisplayName().equals("§cRetour")) {
      player.closeInventory();
      player.chat("/menu");

      return;
    }

    for (Jobs job : Jobs.values()) {
      if (clicked.getItemMeta().getDisplayName().equals(job.getFormattedName())) {
        new JobInventory(player, job);
      }
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void jobs(InventoryClickEvent event) {
    Player player = (Player) event.getWhoClicked();
    Inventory inventory = event.getClickedInventory();

    if (inventory == null) return;
    boolean isJobGUI = false;

    for (Jobs job : Jobs.values()) {
      if (inventory.getName().equals(job.getFormattedName())) isJobGUI = true;
    }

    if (!isJobGUI) return;

    event.setCancelled(true);
    ItemStack clicked = event.getCurrentItem();
    if (!clicked.hasItemMeta() || !clicked.getItemMeta().hasDisplayName()) return;

    if (clicked.getItemMeta().getDisplayName().equals("§cRetour")) {
      player.closeInventory();
      player.chat("/jobs");
    }
  }
}
