package fr.pandaguerrier.jobs.cache;

import fr.pandaguerrier.conodiagameapi.ConodiaGameAPI;
import fr.pandaguerrier.jobs.ConodiaJobs;
import fr.pandaguerrier.jobs.managers.JobsManager;
import fr.pandaguerrier.jobs.utils.Utils;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.UUID;

public class PlayerCache extends CacheManager<JobsManager> {
  @Override
  public void sync() {
    JSONArray payload = (JSONArray) ConodiaGameAPI.getInstance().getApiManager().get("/jobs", new JSONObject()).get("jobs");

    for (Object object : payload) {
      JSONObject job = (JSONObject) object;

      if(!Utils.isPlayerOnline(job.get("player_name").toString())) continue;

      Player player = ConodiaJobs.getInstance().getServer().getPlayer(UUID.fromString(job.get("player_id").toString()));
      JobsManager jobsManager = JobsManager.from(job, ConodiaJobs.getInstance().getServer().getPlayer(UUID.fromString(job.get("player_id").toString())));

      this.getCache().put(player.getUniqueId().toString(), jobsManager);
      System.out.println("PlayerCache: " + job.get("player_name"));
    }
  }
}