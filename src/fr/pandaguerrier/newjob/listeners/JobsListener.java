package fr.pandaguerrier.newjob.listeners;

import com.massivecraft.factions.listeners.FactionsBlockListener;
import fr.pandaguerrier.newjob.jobs.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;

public class JobsListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void miner(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (FactionsBlockListener.playerCanBuildDestroyBlock(player, block.getLocation(), "destroy", true)) {
           new Miner().addXpByBlock(player, block);
        }
    }

    @EventHandler()
    public void furnaceMiner(FurnaceExtractEvent event) {
        Player player = event.getPlayer();
        new Miner().addXp(player, 5);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void constructor(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (FactionsBlockListener.playerCanBuildDestroyBlock(player, block.getLocation(), "destroy", true)) {
            new Constructor().addXpByBlock(player, block);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void farmer(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (FactionsBlockListener.playerCanBuildDestroyBlock(player, block.getLocation(), "destroy", true)) {
            if (block.getType().equals(Material.NETHER_WARTS) && !isGrow(block)) return;
            new Farmer().addXpByBlock(player, block);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void craftFarmer(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();
        new Farmer().addXpByCraft(player, event.getRecipe().getResult());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void hunter(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        Entity killer = event.getEntity().getKiller();
        if (!(killer instanceof Player)) return;

        Player player = ((Player) killer).getPlayer();
        new Hunter().addXpByMob(player, entity);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void lumberJack(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (FactionsBlockListener.playerCanBuildDestroyBlock(player, block.getLocation(), "destroy", true)) {
            new Lumberjack().addXpByBlock(player, block);
        }
    }

    private boolean isGrow(Block block) {
        return block.getType().equals(Material.NETHER_WARTS) && block.getData() == 3;
    }
}
