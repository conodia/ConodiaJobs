package fr.pandaguerrier.newjob.entities;

import fr.pandaguerrier.newjob.jobs.*;

import java.util.HashMap;
import java.util.Map;

public class PlayersEntitites {
    private final Map<String, PlayerCache> playerStats = new HashMap<>();

    public PlayersEntitites(PlayerCache miner, PlayerCache constructor, PlayerCache lumberjack, PlayerCache farmer, PlayerCache hunter) {
        playerStats.put(new Miner().getJobName(), miner);
        playerStats.put(new Constructor().getJobName(), constructor);
        playerStats.put(new Lumberjack().getJobName(), lumberjack);
        playerStats.put(new Farmer().getJobName(), farmer);
        playerStats.put(new Hunter().getJobName(), hunter);
    }

    public PlayersEntitites() {
        playerStats.put(new Miner().getJobName(), new PlayerCache(0, 0));
        playerStats.put(new Constructor().getJobName(), new PlayerCache(0, 0));
        playerStats.put(new Lumberjack().getJobName(), new PlayerCache(0, 0));
        playerStats.put(new Farmer().getJobName(), new PlayerCache(0, 0));
        playerStats.put(new Hunter().getJobName(), new PlayerCache(0, 0));

    }

    public Map<String, PlayerCache> getPlayerStats() {
        return playerStats;
    }
}
