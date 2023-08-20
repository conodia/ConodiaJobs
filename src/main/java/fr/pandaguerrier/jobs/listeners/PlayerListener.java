package fr.pandaguerrier.jobs.listeners;

import fr.pandaguerrier.jobs.ConodiaJobs;
import fr.pandaguerrier.jobs.managers.JobsManager;
import fr.pandaguerrier.jobs.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

  @EventHandler(priority = EventPriority.LOWEST)
  public void join(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    JobsManager jobsManager = ConodiaJobs.getInstance().getPlayerCache().getCache().get(player.getUniqueId().toString());

    if (jobsManager == null) {
      jobsManager = Utils.createPlayer(player);
      ConodiaJobs.getInstance().getPlayerCache().getCache().put(player.getUniqueId().toString(), jobsManager);
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void leave(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    JobsManager jobsManager = ConodiaJobs.getInstance().getPlayerCache().getCache().get(player.getUniqueId().toString());

    if (jobsManager == null) {
      jobsManager = Utils.createPlayer(player);
      ConodiaJobs.getInstance().getPlayerCache().getCache().put(player.getUniqueId().toString(), jobsManager);
    } else {
      jobsManager.update();
    }
  }
}
