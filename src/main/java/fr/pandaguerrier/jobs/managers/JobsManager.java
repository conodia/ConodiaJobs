package fr.pandaguerrier.jobs.managers;

import fr.pandaguerrier.conodiagameapi.ConodiaGameAPI;
import fr.pandaguerrier.jobs.ConodiaJobs;
import fr.pandaguerrier.jobs.cache.contracts.JobsContract;
import fr.pandaguerrier.jobs.enums.Jobs;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

public class JobsManager {
  private  int id;
  private final JobsContract miner;
  private final JobsContract hunter;
  private final JobsContract builder;
  private final JobsContract farmer;
  private final JobsContract woodcutter;

  private final Player player;

  public JobsManager(Player player, JobsContract miner, JobsContract hunter, JobsContract builder, JobsContract farmer, JobsContract woodcutter, int id) {
    this.player = player;
    this.hunter = hunter;
    this.miner = miner;
    this.builder = builder;
    this.farmer = farmer;
    this.woodcutter = woodcutter;
    this.id = id;
  }

  public void create() {
    ConodiaJobs.getInstance().getPlayerCache().getCache().put(player.getUniqueId().toString(), this);
    JSONObject payload = (JSONObject) ConodiaGameAPI.getInstance().getApiManager().post("/jobs", toJson()).get("job");

    this.id = (int) payload.get("id");
  }

  public void update() {
    ConodiaGameAPI.getInstance().getApiManager().put("/jobs", toJson());
  }

  public static double getBlockXp(Material material, String type, Jobs job) {
    FileConfiguration config = ConodiaJobs.getInstance().getConfig();
    return config.getDouble("jobs." + job.getName() + "." + type + "." + material.toString(), 0);
  }

  public static double getEntityXp(Entity material, String type, Jobs job) {
    FileConfiguration config = ConodiaJobs.getInstance().getConfig();
    return config.getDouble("jobs." + job.getName() + "." + type + "." + material.toString(), 0);
  }

  private JSONObject toJson() {
    JSONObject payload = new JSONObject();
    JSONObject jobs = new JSONObject();

    payload.put("playerName", player.getName());
    payload.put("playerId", player.getUniqueId().toString());

    jobs.put("miner", miner.toJson());
    jobs.put("hunter", hunter.toJson());
    jobs.put("builder", builder.toJson());
    jobs.put("farmer", farmer.toJson());
    jobs.put("woodcutter", woodcutter.toJson());

    payload.put("jobs", jobs);

    return payload;
  }

  public JobsContract getMiner() {
    return miner;
  }

  public JobsContract getHunter() {
    return hunter;
  }

  public JobsContract getBuilder() {
    return builder;
  }

  public JobsContract getFarmer() {
    return farmer;
  }

  public JobsContract getWoodcutter() {
    return woodcutter;
  }

  public Player getPlayer() {
    return player;
  }

  public static JobsManager from(JSONObject payload, Player player) {
    JSONObject jobs = (JSONObject) payload.get("jobs");
    return new JobsManager(
        player,
        JobsContract.from((JSONObject) jobs.get("miner"), player, Jobs.MINER),
        JobsContract.from((JSONObject) jobs.get("hunter"), player, Jobs.HUNTER),
        JobsContract.from((JSONObject) jobs.get("builder"), player, Jobs.CONSTRUCTOR),
        JobsContract.from((JSONObject) jobs.get("farmer"), player, Jobs.FARMER),
        JobsContract.from((JSONObject) jobs.get("woodcutter"), player, Jobs.WOODCUTTER),
         Integer.parseInt(payload.get("id").toString())
    );
  }
}