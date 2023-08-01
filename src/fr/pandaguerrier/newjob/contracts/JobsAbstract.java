package fr.pandaguerrier.newjob.contracts;

import fr.pandaguerrier.newjob.ConodiaJobs;
import fr.pandaguerrier.newjob.utils.Utils;
import fr.pandaguerrier.newjob.entities.PlayerCache;
import fr.pandaguerrier.newjob.entities.PlayersEntitites;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.github.paperspigot.Title;

import java.util.HashMap;
import java.util.UUID;

public abstract class JobsAbstract {
    private final HashMap<UUID, PlayersEntitites> playersJobs = ConodiaJobs.getInstance().getPlayersJobs();
    final int MAX_LEVEL = 20;
    final int MAX_XP = 2509;

    abstract public String getJobName();

    abstract public String getFormattedJobName();

    abstract public Material getMaterial();

    public double getBlockXp(Block block) {
        FileConfiguration config = ConodiaJobs.getInstance().getConfig();
        return config.getDouble("jobs." + getJobName() + ".blocks." + block.getType().toString(), 0);
    }

    public void addXpByBlock(Player player, Block block) {
        this.addXp(player, this.getBlockXp(block));
    };

    public void addXp(Player player, double xp) {
        PlayersEntitites playersEntitites = playersJobs.get(player.getUniqueId());
        if (playersEntitites == null) playersEntitites = Utils.createPlayer(player);

        PlayerCache playerJob = playersEntitites.getPlayerStats().get(this.getJobName());
        if (playerJob == null) playerJob = Utils.createPlayer(player).getPlayerStats().get(this.getJobName());

        if (playerJob.getLevel() >= this.MAX_LEVEL) return;

        if (playerJob.getXp() + xp >= (playerJob.getLevel() + 1) * this.MAX_XP) {
            this.addLevel(player, playerJob);
        } else {
            playerJob.addXp(xp);
        }
    }

    public void addLevel(Player player, PlayerCache playersStats) {
        int new_level = playersStats.getLevel() + 1;

        player.sendTitle(new Title("§2Level Up !", "§aVous avez êtes passé niveau §2" + new_level + "§a dans le métier §2" + this.getFormattedJobName() + "§a  !", 15, 30, 10));
        Utils.sendActionBar(player, "§bVous êtes passé level §9" + new_level + "§b dans le métier §9" + this.getFormattedJobName() + "§b  !");
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);

        playersStats.setXp(0);
        playersStats.addLevel();
    };

    public void removeLevel(Player player) {
        PlayerCache playerJob = playersJobs.get(player.getUniqueId()).getPlayerStats().get(this.getJobName());
        playerJob.setLevel(playerJob.getLevel() - 1);
    }

    public void removeXp(Player player, double xp) {
        PlayerCache playerJob = playersJobs.get(player.getUniqueId()).getPlayerStats().get(this.getJobName());
        playerJob.setXp(playerJob.getXp() - xp);
    }

    public double getXp(Player player) {
        PlayerCache playerJob = playersJobs.get(player.getUniqueId()).getPlayerStats().get(this.getJobName());
        return playerJob.getXp();
    }

    public int getLevel(Player player) {
        PlayerCache playerJob = playersJobs.get(player.getUniqueId()).getPlayerStats().get(this.getJobName());
        return playerJob.getLevel();
    }

    public int getMAX_LEVEL() {
        return MAX_LEVEL;
    }

    public int getMAX_XP() {
        return MAX_XP;
    }
}