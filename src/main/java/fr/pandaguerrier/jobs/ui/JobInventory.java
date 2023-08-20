package fr.pandaguerrier.jobs.ui;

import fr.pandaguerrier.jobs.enums.Jobs;
import fr.pandaguerrier.jobs.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class JobInventory {
  Player player;
  Jobs job;

  public JobInventory(Player player, Jobs job) {
    this.player = player;
    this.job = job;
    handle();
  }

  public void handle() {
    Inventory inventory = Bukkit.createInventory(player, 45, this.job.getFormattedName());
    Utils.setBorders(inventory);
    inventory.setItem(4, Utils.createGuiItem(job.getMaterial(), job.getFormattedName(), 0, ""));
    this.player.openInventory(inventory);
  }
}
