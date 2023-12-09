package fr.pandaguerrier.jobs.listeners;

import fr.pandaguerrier.jobs.enums.Jobs;
import fr.pandaguerrier.jobs.managers.LeaderboardManager;
import fr.pandaguerrier.jobs.ui.BuyPointsInventory;
import fr.pandaguerrier.jobs.ui.LevelInventory;
import fr.pandaguerrier.jobs.ui.MainInventory;
import fr.pandaguerrier.jobs.utils.Constants;
import fr.pandaguerrier.jobs.utils.Utils;
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

    if (inventory == null || !inventory.getName().startsWith("§9➜ ")) return;
    event.setCancelled(true);

    if (!clicked.hasItemMeta() || !clicked.getItemMeta().hasDisplayName()) return;

    if (clicked.getItemMeta().getDisplayName().equals("§cRetour")) {
      player.closeInventory();
      player.chat("/menu");

      return;
    }

    if (inventory.getName().startsWith(Constants.LEADERBOARD_GUI_NAME)) {
      LeaderboardManager GuiManager = new LeaderboardManager(player);

      switch (event.getCurrentItem().getType()) {
        case NETHER_STAR:
          player.closeInventory();
          GuiManager.open(1);
          break;
        case ARROW:
          player.closeInventory();
          GuiManager.open(Integer.parseInt(event.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§bPage: ", "")));
          break;
      }
    }

    switch (inventory.getName()) {
      case "§9➜ §bJobs":
        new MainInventory(player).onInteract(event);
        break;
      case "§9➜ §bNiveaux":
        new LevelInventory(player, Utils.getFaction(player)).onInteract(event);
        break;
      case "§9➜ §bAchat de points":
        new BuyPointsInventory(player, Utils.getFaction(player), 0).onInteract(event);
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
