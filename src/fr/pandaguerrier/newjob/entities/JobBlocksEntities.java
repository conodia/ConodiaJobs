package fr.pandaguerrier.newjob.entities;

import org.bukkit.Material;

import java.util.HashMap;

public class JobBlocksEntities {
    HashMap<Material, Double> blocks;
    public JobBlocksEntities(HashMap<Material, Double> blocks) {
        this.blocks = blocks;
    }
    public HashMap<Material, Double> getBlocks() {
        return blocks;
    }
}
