package fr.ixsou.elo.commands;

import fr.ixsou.elo.Elo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EloPlayer implements CommandExecutor {

    private final Elo plugin;

    public EloPlayer(Elo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            sender.sendMessage(plugin.getMessage("elo-see-self").replace("%amount%", String.valueOf(plugin.getElo(player))));
            return true;
        }

        else if (args.length == 1) {
            Player target = plugin.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(plugin.getMessage("player-not-found"));
                return true;
            }
            sender.sendMessage(plugin.getMessage("elo-see").replace("%player%", target.getName()).replace("%amount%", String.valueOf(plugin.getElo(target))));
            return true;
        }

        else {
            sender.sendMessage(plugin.getMessage("no-command"));
            return true;
        }

    }
}
