package fr.pandaguerrier.jobs.contracts;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface ConodiaGui {
  Player player = null;
  public void handle();
  public String getInventoryName();
  public void onInteract(InventoryClickEvent event);
}
