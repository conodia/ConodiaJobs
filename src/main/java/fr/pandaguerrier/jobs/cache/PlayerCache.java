package fr.pandaguerrier.jobs.cache;

import fr.pandaguerrier.conodiagameapi.ConodiaGameAPI;
import fr.pandaguerrier.jobs.ConodiaJobs;
import fr.pandaguerrier.jobs.managers.JobsManager;
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
      System.out.println("PlayerCache: " + job.get("player_id"));
      Player player = ConodiaJobs.getInstance().getServer().getPlayer(UUID.fromString(job.get("player_id").toString()));
      System.out.println("PlayerCache: " + player.getName());
      JobsManager jobsManager = JobsManager.from(job, ConodiaJobs.getInstance().getServer().getPlayer(UUID.fromString(job.get("player_id").toString())));

      this.getCache().put(job.get("player_id").toString(), jobsManager);
      System.out.println("PlayerCache: " + job.get("player_name"));
    }
  }
}