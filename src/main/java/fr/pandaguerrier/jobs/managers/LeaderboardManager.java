package fr.pandaguerrier.jobs.managers;

import fr.pandaguerrier.jobs.ConodiaJobs;
import fr.pandaguerrier.jobs.builder.ItemBuilder;
import fr.pandaguerrier.jobs.utils.Constants;
import fr.pandaguerrier.jobs.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;


public class LeaderboardManager {
  private final Player player;
  private final List<FLevelManager> levels = new ArrayList<>(ConodiaJobs.getInstance().getfLevelCache().getCache().values());

  private static final int[] ALLOWED_SLOT = {
      10, 11, 12, 13, 14, 15, 16,
      19, 20, 21, 22, 23, 24, 25,
      28, 29, 30, 31, 32, 33, 34,
  };

  public LeaderboardManager(Player player) {
    this.player = player;
  }

  public final int getMaxPage() {
    return (int) Math.ceil(levels.size() / 21.0);
  }

  public void open(int page) {
    Inventory inv = Bukkit.createInventory(null, 9 * 5, Constants.LEADERBOARD_GUI_NAME + " - Page " + page +"/" + getMaxPage());
    Utils.setBorders(inv);
    setLeaderboard(page, inv);
    ItemBuilder nextBuilder = new ItemBuilder(Material.ARROW);
    ItemBuilder previousPage = new ItemBuilder(Material.ARROW);
    ItemBuilder homePage = new ItemBuilder(Material.NETHER_STAR);

    if(page == 1) {
      if (getMaxPage() == 1) {
        homePage.setName("§bRafraichir");
        inv.setItem(40, homePage.build());
      } else {
        nextBuilder.setName("§9Page: " + (page + 1));
        inv.setItem(41, nextBuilder.build());

        homePage.setName("§bPage principale");
        inv.setItem(40, homePage.build());
      }
    } else if (page == getMaxPage()) {
      previousPage.setName("§9Page: " + (page - 1));
      inv.setItem(39, previousPage.build());

      homePage.setName("§bPage principale");
      inv.setItem(40, homePage.build());
    } else {
      nextBuilder.setName("§9Page: " + (page + 1));
      inv.setItem(41, nextBuilder.build());

      previousPage.setName("§9Page: " + (page - 1));
      inv.setItem(39, previousPage.build());

      homePage.setName("§bPage principale");
      inv.setItem(40, homePage.build());
    }

    this.player.openInventory(inv);
  }

  public void setLeaderboard(int page, Inventory inv) {
    int slot = 0;

    this.levels.sort((o1, o2) -> Integer.compare(o2.getPoints(), o1.getPoints()));
    List<FLevelManager> levelsSub = new ArrayList<>(this.levels).subList((page - 1) * 21, Math.min(page * 21, this.levels.size()));

    for (FLevelManager faction : levelsSub) {
      int index = levels.indexOf(faction);
      ItemBuilder itemBuilder = new ItemBuilder(Material.PAPER);
      switch (index) {
        case 0:
          itemBuilder = new ItemBuilder(Material.GOLD_BLOCK);
          break;
        case 1:
          itemBuilder = new ItemBuilder(Material.DIAMOND_BLOCK);
          break;
        case 2:
          itemBuilder = new ItemBuilder(Material.IRON_BLOCK);
          break;
      }
      itemBuilder.setName("§9" + (index + 1) + " §8| §b" + faction.getFaction().getTag());
      itemBuilder.setLore("§9Points: §b" + faction.getPoints(), "§9Xp: §b" + Math.round(faction.getXp()), "§9Membres: §b" + faction.getFaction().getFPlayers().size());
      
      inv.setItem(ALLOWED_SLOT[slot], itemBuilder.build());
      slot++;
    }
  }
}
