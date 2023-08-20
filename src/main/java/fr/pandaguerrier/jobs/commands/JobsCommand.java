package fr.pandaguerrier.jobs.commands;

import fr.pandaguerrier.jobs.ui.MainInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JobsCommand implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) return false;
    final Player player = (Player) sender;

    new MainInventory(player);
    return true;
  }
}
