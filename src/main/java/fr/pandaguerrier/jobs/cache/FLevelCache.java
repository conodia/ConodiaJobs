package fr.pandaguerrier.jobs.cache;

import fr.pandaguerrier.conodiagameapi.ConodiaGameAPI;
import fr.pandaguerrier.jobs.ConodiaJobs;
import fr.pandaguerrier.jobs.managers.FLevelManager;
import fr.pandaguerrier.jobs.managers.JobsManager;
import fr.pandaguerrier.jobs.utils.Utils;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.UUID;

public class FLevelCache extends CacheManager<FLevelManager> {
  @Override
  public void sync() {
    JSONArray payload = (JSONArray) ConodiaGameAPI.getInstance().getApiManager().get("/levels", new JSONObject()).get("factions");

    for (Object object : payload) {
      JSONObject faction = (JSONObject) object;
      FLevelManager fLevelManager = FLevelManager.from(faction);

      if (fLevelManager == null) {
        continue;
      }

      this.getCache().put(fLevelManager.getFaction().getTag(), fLevelManager);
    }
  }
}