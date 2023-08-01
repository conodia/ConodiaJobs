package fr.pandaguerrier.newjob.entities;

import java.util.HashMap;

public class ConfigEntities {
    private final HashMap<String, JobBlocksEntities> blockLevels;
    public ConfigEntities(HashMap<String, JobBlocksEntities> blockLevels) {
        this.blockLevels = blockLevels;
    }

    public HashMap<String, JobBlocksEntities> getBlockLevels() {
        return blockLevels;
    }
}
