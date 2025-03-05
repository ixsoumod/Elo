package fr.ixsou.elo.commands;

import fr.ixsou.elo.Elo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EloAdmin implements CommandExecutor {

    private final Elo plugin;

    public EloAdmin(Elo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        Player player = (Player) sender;

        if (args.length < 1) {
            sender.sendMessage(plugin.getMessage("no-command"));
            return true;
        }

        if (args[0].equalsIgnoreCase("give")){
            if (args.length < 3){
                sender.sendMessage(plugin.getMessage("no-amount"));
                return true;
            }
            Player target = player.getServer().getPlayer(args[1]);
            if (target == null){
                sender.sendMessage(plugin.getMessage("no-player"));
                return true;
            }
            int amount = Integer.parseInt(args[2]);
            target.sendMessage(plugin.getMessage("elo-earn").replace("%amount%", String.valueOf(amount)).replace("%player%", player.getName()));
            sender.sendMessage(plugin.getMessage("elo-give").replace("%amount%", String.valueOf(amount)).replace("%player%", target.getName()));
            plugin.getConfig().set("players." + target.getUniqueId() + ".elo", plugin.getConfig().getInt("players." + target.getUniqueId() + ".elo") + amount);
            plugin.saveConfig();

        } else if (args[0].equalsIgnoreCase("take")){
            if (args.length < 3){
                sender.sendMessage(plugin.getMessage("no-command"));
                return true;
            }
            Player target = player.getServer().getPlayer(args[1]);
            if (target == null){
                sender.sendMessage(plugin.getMessage("no-player"));
                return true;
            }
            int amount = Integer.parseInt(args[2]);
            target.sendMessage(plugin.getMessage("elo-loose").replace("%amount%", String.valueOf(amount)).replace("%player%", player.getName()));
            sender.sendMessage(plugin.getMessage("elo-take").replace("%amount%", String.valueOf(amount)).replace("%player%", target.getName()));
            plugin.getConfig().set("players." + target.getUniqueId() + ".elo", plugin.getConfig().getInt("players." + target.getUniqueId() + ".elo") - amount);
            plugin.saveConfig();

        } else if (args[0].equalsIgnoreCase("set")){
            if (args.length < 3){
                sender.sendMessage(plugin.getMessage("no-amount"));
                return true;
            }
            Player target = player.getServer().getPlayer(args[1]);
            if (target == null){
                sender.sendMessage(plugin.getMessage("no-player"));
                return true;
            }
            int amount = Integer.parseInt(args[2]);
            target.sendMessage(plugin.getMessage("elo-get").replace("%amount%", String.valueOf(amount)).replace("%player%", player.getName()));
            sender.sendMessage(plugin.getMessage("elo-set").replace("%amount%", String.valueOf(amount)).replace("%player%", target.getName()));
            plugin.getConfig().set("players." + target.getUniqueId() + ".elo", amount);
            plugin.saveConfig();

        } else if (args[0].equalsIgnoreCase("help")){
            sender.sendMessage(plugin.getMessage("help.title"));
            sender.sendMessage(plugin.getMessage("help.commands.1"));
            sender.sendMessage(plugin.getMessage("help.commands.2"));
            sender.sendMessage(plugin.getMessage("help.commands.3"));
            sender.sendMessage(plugin.getMessage("help.commands.4"));
            sender.sendMessage(plugin.getMessage("help.commands.5"));

        }
        else if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(plugin.getMessage("reload"));

        }


        return true;
    }
}
