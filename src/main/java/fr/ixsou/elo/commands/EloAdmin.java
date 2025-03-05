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
            player.sendMessage("§cVeuillez entrer un argument ! §8(/eloadmin help)");
            return true;
        }

        if (args[0].equalsIgnoreCase("give")){
            if (args.length < 3){
                player.sendMessage("§cVeuillez entrer un joueur et un montant !");
                return true;
            }
            Player target = player.getServer().getPlayer(args[1]);
            if (target == null){
                player.sendMessage("§cLe joueur n'est pas connecté !");
                return true;
            }
            int amount = Integer.parseInt(args[2]);
            target.sendMessage("§aVous avez reçu " + amount + " points d'élo !");
            player.sendMessage("§aVous avez donné " + amount + " points d'élo à " + target.getName());
            plugin.getConfig().set("players." + target.getUniqueId() + ".elo", plugin.getConfig().getInt("players." + target.getUniqueId() + ".elo") + amount);
            plugin.saveConfig();
        }


        return true;
    }
}
