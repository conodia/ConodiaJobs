package fr.pandaguerrier.jobs.listeners;

import com.massivecraft.factions.event.FactionCreateEvent;
import com.massivecraft.factions.event.FactionDisbandEvent;
import fr.pandaguerrier.conodiagameapi.ConodiaGameAPI;
import fr.pandaguerrier.jobs.ConodiaJobs;
import fr.pandaguerrier.jobs.managers.FLevelManager;
import fr.pandaguerrier.jobs.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.json.simple.JSONObject;

public class FactionListener implements Listener {

  @EventHandler(priority = EventPriority.LOWEST)
  public void create(FactionCreateEvent event) {
    FLevelManager fLevelManager = Utils.createFaction(event.getFPlayer().getFaction());
    ConodiaJobs.getInstance().getfLevelCache().getCache().put(event.getFPlayer().getFaction().getTag(), fLevelManager);
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void delete(FactionDisbandEvent event) {
    FLevelManager fLevelManager = Utils.createFaction(event.getFPlayer().getFaction());

    ConodiaGameAPI.getInstance().getApiManager().destroy("/levels/" + fLevelManager.getId(), new JSONObject());
    ConodiaJobs.getInstance().getfLevelCache().getCache().remove(event.getFaction().getTag());
  }
}
