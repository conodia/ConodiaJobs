package fr.pandaguerrier.jobs.commands;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import fr.pandaguerrier.jobs.ConodiaJobs;
import fr.pandaguerrier.jobs.managers.FLevelManager;
import fr.pandaguerrier.jobs.managers.LeaderboardManager;
import fr.pandaguerrier.jobs.ui.LevelInventory;
import fr.pandaguerrier.jobs.ui.MainInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelsCommand implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) return false;
    final Player player = (Player) sender;

    if (args.length >= 1 && args[0].equalsIgnoreCase("classement")) {
      new LeaderboardManager(player).open(1);
      return true;
    }

    FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);

    if (fPlayer.getFaction().isWilderness()) {
      player.sendMessage("§cVous devez être dans une faction pour utiliser cette commande.");
      return false;
    }

    FLevelManager fLevelManager = ConodiaJobs.getInstance().getfLevelCache().getCache().get(fPlayer.getFaction().getTag());

    new LevelInventory(player, fLevelManager);
    return true;
  }
}
