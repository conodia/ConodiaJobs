package fr.pandaguerrier.jobs.ui;

import fr.pandaguerrier.jobs.enums.Jobs;
import fr.pandaguerrier.jobs.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MainInventory {
  Player player;

  public MainInventory(Player player) {
    this.player = player;
    handle();
  }

  public void handle() {
    Inventory inventory = Bukkit.createInventory(player, 45, "§9➜ §bJobs");
    Utils.setBorders(inventory);

    int start = 20;
    for (Jobs job : Jobs.values()) {
      inventory.setItem(start, Utils.createGuiItem(job.getMaterial(), job.getFormattedName(), 0, "§8§m---------------------", "§7Cliquez pour accèder", "§8§m---------------------"));
      start++;
    }

    this.player.openInventory(inventory);
  }
}
