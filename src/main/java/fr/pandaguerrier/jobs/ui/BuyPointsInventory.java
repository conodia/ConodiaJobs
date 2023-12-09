package fr.pandaguerrier.jobs.ui;

import fr.pandaguerrier.jobs.contracts.ConodiaGui;
import fr.pandaguerrier.jobs.managers.FLevelManager;
import fr.pandaguerrier.jobs.utils.Constants;
import fr.pandaguerrier.jobs.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class BuyPointsInventory implements ConodiaGui {
  Player player;
  FLevelManager levelManager;
  int points;

  public BuyPointsInventory(Player player, FLevelManager levelManager, int points) {
    this.player = player;
    this.levelManager = levelManager;
    this.points = points;
    handle();
  }

  @Override
  public void handle() {
    Inventory inventory = Bukkit.createInventory(player, 45, this.getInventoryName());
    Utils.setBorders(inventory);

    double restXp = this.levelManager.getXp() - (points * Constants.XP_PER_POINT);

    if (points >= 10) {
      inventory.setItem(19, Utils.createGuiItem(Material.WOOL, "§cEnlever 10", 14, ""));
    }

    if (points >= 1) {
      inventory.setItem(20, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "§cEnlever 1", 14, ""));
    }

    inventory.setItem(13, Utils.createGuiItem(Material.PAPER, "§9Xp restante: §b" + restXp, 0, ""));

    if (points >= 1) {
      inventory.setItem(30, Utils.createGuiItem(Material.REDSTONE_BLOCK, "§cAnnuler l'achat", 0, ""));
      inventory.setItem(31, Utils.createGuiItem(Material.GOLD_BLOCK, "§6Points a acheter: §e" + points, 0, "§8-------------------", "§9Prix: §b" + points * Constants.XP_PER_POINT + " xp §6(" + Constants.XP_PER_POINT + "xp / points).", "§8-------------------"));
      inventory.setItem(32, Utils.createGuiItem(Material.STAINED_CLAY, "§aConfirmer l'achat", 13, ""));
    } else{
      inventory.setItem(31, Utils.createGuiItem(Material.GOLD_BLOCK, "§6Points a acheter: §e" + points, 0, ""));
    }
    if (restXp > Constants.XP_PER_POINT) {
      inventory.setItem(24, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "§aAjouter 1", 13, ""));
    }

    if (restXp > Constants.XP_PER_POINT * 10) {
      inventory.setItem(25, Utils.createGuiItem(Material.WOOL, "§aAjouter 10", 5, ""));
    }

    this.player.openInventory(inventory);
  }

  @Override
  public String getInventoryName() {
    return "§9➜ §bAchat de points";
  }

  @Override
  public void onInteract(InventoryClickEvent event) {
    if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
      return;
    }

    int pointPayload = event.getInventory().getItem(31).getItemMeta().getDisplayName().replace("§6Points a acheter: §e", "").isEmpty() ? 0 : Integer.parseInt(event.getInventory().getItem(31).getItemMeta().getDisplayName().replace("§6Points a acheter: §e", ""));
    this.points = this.points + pointPayload;

    switch (event.getCurrentItem().getType()) {
      case STAINED_GLASS_PANE:
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§cEnlever 1")) {
          this.points -= 1;
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§aAjouter 1")) {
          this.points += 1;
        }
        break;
      case WOOL:
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§cEnlever 10")) {
          this.points -= 10;
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§aAjouter 10")) {
          this.points += 10;
        }
        break;
      case STAINED_CLAY:
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§aConfirmer l'achat")) {
          this.levelManager.addPoints(this.points, this.points * Constants.XP_PER_POINT);
          this.levelManager.update();
          this.player.closeInventory();
          this.player.sendMessage("§aVous avez acheté §e" + this.points + " §apoints.");
        }
        return;
      case REDSTONE_BLOCK:
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§cAnnuler l'achat")) {
          this.player.closeInventory();
        }
        return;
    }

    new BuyPointsInventory(this.player, this.levelManager, this.points);
  }
}
