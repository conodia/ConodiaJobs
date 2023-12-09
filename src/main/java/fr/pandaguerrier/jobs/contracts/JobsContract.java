package fr.pandaguerrier.jobs.contracts;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import fr.pandaguerrier.jobs.ConodiaJobs;
import fr.pandaguerrier.jobs.enums.Jobs;
import fr.pandaguerrier.jobs.managers.FLevelManager;
import fr.pandaguerrier.jobs.utils.Constants;
import fr.pandaguerrier.jobs.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import java.util.List;

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
    if (xp <= 0) return;

    FPlayer fPlayer = FPlayers.getInstance().getByPlayer(this.player);

    if (fPlayer.getFaction() != null) {
      FLevelManager fLevelManager = ConodiaJobs.getInstance().getfLevelCache().getCache().get(fPlayer.getFaction().getTag());
      if (fLevelManager != null) {
        fLevelManager.addXP(xp * 0.7);
      }
    }


    if (level >= Constants.MAX_LEVEL) return;

    if (this.xp + xp >= (level + 1) * Constants.MAX_XP) {
      this.addLevel();
    } else {
      Utils.sendActionBar(player, createProgressBar());
      this.xp += xp;
    }
  }

  private String createProgressBar() {
    double percent = this.xp / ((level + 1) * Constants.MAX_XP);
    int progress = (int) (percent * 100);
    StringBuilder bar = new StringBuilder("§a");
    // process: 50% = §a:::::§7:::::
    for (int i = 0; i < progress / 10; i++) {
      bar.append(":");
    }
    for (int i = 0; i < 10 - (progress / 10); i++) {
      bar.append("§7:");
    }
    bar.append("§a");
    return bar + " §b" + (int) (percent * 100) + "%" + " - §a" + this.job.getFormattedName() + " §b";
  }

  public void addLevel() {
    level++;
    this.xp = 0;

    player.sendTitle("§2Level Up !", "§2" + (level - 1) + "§a >> §2" + level + "§a : " + this.job.getFormattedName());
    Utils.sendActionBar(player, "§bVous êtes passé level §9" + level + "§b dans le métier " + this.job.getFormattedName() + "§b  !");
    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
    ConodiaJobs.getInstance().getPlayerCache().getCache().get(player.getUniqueId().toString()).update();

    // recompense system
    if (ConodiaJobs.getInstance().getRewardFile().getStringList("jobs." + this.job.getName()).size() < level) return;
    List<String> commands = List.of(ConodiaJobs.getInstance().getRewardFile().getStringList("jobs." + this.job.getName()).get(level - 1).split(";"));

    commands.forEach(command -> {
      System.out.println(command);
      String key = command.split("\\.")[0];
      String amount = command.split("\\.")[1];

      Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cc give p " + key + " " + amount + " " + player.getName());
    });
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
