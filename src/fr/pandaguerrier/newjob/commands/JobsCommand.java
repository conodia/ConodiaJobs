package fr.pandaguerrier.newjob.commands;

import fr.pandaguerrier.newjob.ui.MainInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JobsCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        final Player player = (Player) sender;

        System.out.println(player.getAddress().getHostString());

        new MainInventory(player);
        return true;
    }
}
