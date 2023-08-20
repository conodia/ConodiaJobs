package fr.pandaguerrier.jobs.cache.contracts;

import fr.pandaguerrier.jobs.enums.Jobs;
import fr.pandaguerrier.jobs.utils.Constants;
import fr.pandaguerrier.jobs.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

public class JobsContract {
  private int level;
  private double xp;
  private final Player player;
  private final Jobs job;

  public JobsContract(int level, double xp, Player player, Jobs job) {
    this.level = level;
    this.xp = xp;
    this.player = player;
    this.job = job;
  }

  public void addXp(double xp) {
    if (level >= Constants.MAX_LEVEL) return;
    if (this.xp + xp >= (level + 1) * Constants.MAX_XP) {
      this.addLevel();
    } else {
      this.xp += xp;
    }
  }

  public void addLevel() {
    level++;
    this.xp = 0;

    player.sendTitle("§2Level Up !", "§2" + (level - 1) + "§a >> §2" + level + "§a : ," + this.job.getFormattedName());
    Utils.sendActionBar(player, "§bVous êtes passé level §9" + level + "§b dans le métier " + this.job.getFormattedName() + "§b  !");
    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
  }

  public int getLevel() {
    return level;
  }

  public double getXp() {
    return xp;
  }

  public JSONObject toJson() {
    JSONObject payload = new JSONObject();
    payload.put("level", level);
    payload.put("xp", xp);
    return payload;
  }

  public static JobsContract from(JSONObject payload, Player player, Jobs job) {
    System.out.println("§9---------------------------------");
    System.out.println("§b" + payload);
    return new JobsContract(
        Integer.parseInt(payload.get("level").toString()),
        Double.parseDouble(payload.get("xp").toString()),
        player,
        job
    );
  }

  public static JobsContract init(Player player, Jobs job) {
    return new JobsContract(0, 0, player, job);
  }
}
