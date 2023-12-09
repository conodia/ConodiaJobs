package fr.pandaguerrier.jobs.commands;

import fr.pandaguerrier.conodiagameapi.utils.Utils;
import fr.pandaguerrier.jobs.ConodiaJobs;
import fr.pandaguerrier.jobs.ui.MainInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class ReloadCommand implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) return false;
    final Player player = (Player) sender;

    if(!player.hasPermission("jobs.reload")) {
      player.sendMessage("§cVous n'avez pas la permission !");
      return false;
    }
    player.sendMessage("§aReload en cours...");
    ConodiaJobs.getInstance().reloadConfig();
    ConodiaJobs.getInstance().setRewardFile(Utils.createCustomConfig("rewards.yml", ConodiaJobs.getInstance()));
    player.sendMessage("§aReload terminé !");
    return true;
  }
}
