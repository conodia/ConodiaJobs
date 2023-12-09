package fr.pandaguerrier.jobs.ui;

import fr.pandaguerrier.jobs.contracts.ConodiaGui;
import fr.pandaguerrier.jobs.enums.Jobs;
import fr.pandaguerrier.jobs.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MainInventory implements ConodiaGui {

  Player player;

  public MainInventory(Player player) {
    this.player = player;
    handle();
  }

  @Override
  public void handle() {
    Inventory inventory = Bukkit.createInventory(player, 45, this.getInventoryName());
    Utils.setBorders(inventory);

    int start = 20;
    for (Jobs job : Jobs.values()) {
      inventory.setItem(start, Utils.createGuiItem(job.getMaterial(), job.getFormattedName(), 0, "§8§m---------------------", "§7Cliquez pour accèder", "§8§m---------------------"));
      start++;
    }

    this.player.openInventory(inventory);
  }

  @Override
  public String getInventoryName() {
    return "§9➜ §bJobs";
  }

  @Override
  public void onInteract(InventoryClickEvent event) {
    Player player = (Player) event.getWhoClicked();
    ItemStack clicked = event.getCurrentItem();

    for (Jobs job : Jobs.values()) {
      if (clicked.getItemMeta().getDisplayName().equals(job.getFormattedName())) {
        new JobInventory(player, job);
      }
    }
  }
}
