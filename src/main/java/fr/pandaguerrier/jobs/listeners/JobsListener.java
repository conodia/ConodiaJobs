package fr.pandaguerrier.jobs.listeners;

import com.massivecraft.factions.listeners.FactionsBlockListener;
import fr.pandaguerrier.jobs.ConodiaJobs;
import fr.pandaguerrier.jobs.enums.Jobs;
import fr.pandaguerrier.jobs.managers.JobsManager;
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

import java.util.Map;

public class JobsListener implements Listener {
  Map<String, JobsManager> cache = ConodiaJobs.getInstance().getPlayerCache().getCache();

  @EventHandler(priority = EventPriority.LOWEST)
  public void miner(BlockBreakEvent event) {
    Player player = event.getPlayer();
    Block block = event.getBlock();

    if (FactionsBlockListener.playerCanBuildDestroyBlock(player, block.getLocation(), "destroy", true)) {
      JobsManager jobsManager = cache.get(player.getUniqueId().toString());
      jobsManager.getMiner().addXp(JobsManager.getBlockXp(block.getType(), "blocks", Jobs.MINER));
    }
  }

  @EventHandler()
  public void furnaceMiner(FurnaceExtractEvent event) {
    Player player = event.getPlayer();
    cache.get(player.getUniqueId().toString()).getMiner().addXp(5);
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void constructor(BlockPlaceEvent event) {
    Player player = event.getPlayer();
    Block block = event.getBlock();

    if (FactionsBlockListener.playerCanBuildDestroyBlock(player, block.getLocation(), "destroy", true)) {
      cache.get(player.getUniqueId().toString()).getBuilder().addXp(JobsManager.getBlockXp(block.getType(), "blocks", Jobs.CONSTRUCTOR));
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void farmer(BlockBreakEvent event) {
    Player player = event.getPlayer();
    Block block = event.getBlock();

    if (FactionsBlockListener.playerCanBuildDestroyBlock(player, block.getLocation(), "destroy", true)) {
      if (block.getType().equals(Material.NETHER_WARTS) && !isGrow(block)) return;
      cache.get(player.getUniqueId().toString()).getFarmer().addXp(JobsManager.getBlockXp(block.getType(), "blocks", Jobs.FARMER));
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void craftFarmer(CraftItemEvent event) {
    Player player = (Player) event.getWhoClicked();
    cache.get(player.getUniqueId().toString()).getFarmer().addXp(JobsManager.getBlockXp(event.getRecipe().getResult().getType(), "craft", Jobs.FARMER));
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void hunter(EntityDeathEvent event) {
    Entity entity = event.getEntity();
    Player killer = event.getEntity().getKiller();

    Player player = killer.getPlayer();
    cache.get(player.getUniqueId().toString()).getFarmer().addXp(JobsManager.getEntityXp(entity, "mobs", Jobs.HUNTER));
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void lumberJack(BlockBreakEvent event) {
    Player player = event.getPlayer();
    Block block = event.getBlock();

    if (FactionsBlockListener.playerCanBuildDestroyBlock(player, block.getLocation(), "destroy", true)) {
      cache.get(player.getUniqueId().toString()).getWoodcutter().addXp(JobsManager.getBlockXp(block.getType(), "blocks", Jobs.WOODCUTTER));
    }
  }

  private boolean isGrow(Block block) {
    return block.getType().equals(Material.NETHER_WARTS) && block.getData() == 3;
  }
}
