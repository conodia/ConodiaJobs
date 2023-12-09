package fr.pandaguerrier.jobs.ui;

import fr.pandaguerrier.jobs.ConodiaJobs;
import fr.pandaguerrier.jobs.contracts.ConodiaGui;
import fr.pandaguerrier.jobs.contracts.JobsContract;
import fr.pandaguerrier.jobs.enums.Jobs;
import fr.pandaguerrier.jobs.utils.Constants;
import fr.pandaguerrier.jobs.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class JobInventory implements ConodiaGui {
  Player player;
  Jobs job;

  public JobInventory(Player player, Jobs job) {
    this.player = player;
    this.job = job;
    handle();
  }

  @Override
  public void handle() {
    Inventory inventory = Bukkit.createInventory(player, 45, this.job.getFormattedName());
    Utils.setBorders(inventory);
    JobsContract jobsContract = ConodiaJobs.getInstance().getPlayerCache().getCache().get(player.getUniqueId().toString()).getJobContract(this.job);
    inventory.setItem(4, Utils.createGuiItem(job.getMaterial(), job.getFormattedName(), 0, ""));

    inventory.setItem(22, Utils.createGuiItem(job.getMaterial(), "§9§lInformations", 0, "§8-------------------", "§9Niveau: §b" + jobsContract.getLevel() + "§8/§9" + Constants.MAX_LEVEL, "§9Expérience: §b" + jobsContract.getXp() + "§8/§9" + (jobsContract.getLevel() + 1) * Constants.MAX_XP, "§8-------------------"));
    this.player.openInventory(inventory);
  }

  @Override
  public String getInventoryName() {
    return this.job.getFormattedName();
  }

  @Override
  public void onInteract(InventoryClickEvent event) {}
}
