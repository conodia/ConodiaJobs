package fr.pandaguerrier.jobs.ui;

import fr.pandaguerrier.jobs.contracts.ConodiaGui;
import fr.pandaguerrier.jobs.managers.FLevelManager;
import fr.pandaguerrier.jobs.managers.LeaderboardManager;
import fr.pandaguerrier.jobs.utils.Constants;
import fr.pandaguerrier.jobs.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class LevelInventory implements ConodiaGui {
  Player player;
  FLevelManager levelManager;

  public LevelInventory(Player player, FLevelManager levelManager) {
    this.player = player;
    this.levelManager = levelManager;
    handle();
  }

  @Override
  public void handle() {
    Inventory inventory = Bukkit.createInventory(player, 45, this.getInventoryName());
    Utils.setBorders(inventory);
    inventory.setItem(21, Utils.createGuiItem(Material.PAPER, "§9§lInformations", 0, "§8-------------------", "§9Points: §b" + this.levelManager.getPoints(), "§9Expérience: §b" + this.levelManager.getXp(), "", "§eInformation: échangez de l'xp contre des points pour monter dans le classement.", "§aL'xp s'obtient en montant ses métiers !", "§8-------------------"));
    inventory.setItem(22, Utils.createGuiItem(Material.GOLD_BLOCK, "§9§lClassement", 0, ""));
    inventory.setItem(23, Utils.createGuiItem(Material.GOLD_INGOT, "§9§lAcheter des points", 0, ""));

    this.player.openInventory(inventory);
  }

  @Override
  public String getInventoryName() {
    return "§9➜ §bNiveaux";
  }

  @Override
  public void onInteract(InventoryClickEvent event) {
    if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
      return;
    }

    switch (event.getCurrentItem().getType()) {
      case PAPER:
        event.setCancelled(true);
        break;
      case GOLD_BLOCK:
        new LeaderboardManager(this.player).open(1);
        break;
      case GOLD_INGOT:
        new BuyPointsInventory(this.player, this.levelManager, 0);
        break;
      default:
        event.setCancelled(true);
        break;
    }
  }
}
