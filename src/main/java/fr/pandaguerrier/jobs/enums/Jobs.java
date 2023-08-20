package fr.pandaguerrier.jobs.enums;

import org.bukkit.Material;

public enum Jobs {
  HUNTER("hunter", "§cChasseur", Material.DIAMOND_SWORD),
  MINER("miner", "§8Mineur", Material.DIAMOND_PICKAXE),
  CONSTRUCTOR("builder", "§6Constructeur", Material.BRICK),
  FARMER("farmer", "§2Fermier", Material.WHEAT),
  WOODCUTTER("woodcutter", "§9Bûcheron", Material.DIAMOND_AXE);

  private final String name;
  private final String formattedName;
  private final Material material;

  Jobs(String name, String formattedName, Material material) {
    this.name = name;
    this.formattedName = formattedName;
    this.material = material;
  }

  public String getName() {
    return name;
  }

  public String getFormattedName() {
    return formattedName;
  }

  public Material getMaterial() {
    return material;
  }
}