package fr.ixsou.elo.events;

import fr.ixsou.elo.Elo;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.EventListener;

public class Events implements Listener {

    private final Elo plugin;

    public Events(Elo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event) {
        if (plugin.getConfig().getBoolean("events.first-join-elo.enabled", true)) {
            if (!plugin.getConfig().contains("players." + event.getPlayer().getUniqueId())) {
                plugin.getConfig().set("players." + event.getPlayer().getUniqueId() + ".elo", plugin.getConfig().getInt("events.first-join-elo.amount", 1000));
                plugin.saveConfig();
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (plugin.getConfig().getBoolean("events.death-elo.enabled", true)) {
            if (plugin.getConfig().contains("players." + event.getEntity().getUniqueId())) {
                int eloLoss = plugin.getConfig().getInt("events.death-elo.amount", 10);
                plugin.getConfig().set("players." + event.getEntity().getUniqueId() + ".elo",
                        plugin.getConfig().getInt("players." + event.getEntity().getUniqueId() + ".elo") - eloLoss);
                plugin.saveConfig();

                event.getEntity().sendMessage(plugin.getMessage("elo-loose").replace("%amount%", String.valueOf(eloLoss)));

            }
        }
    }

    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        if (plugin.getConfig().getBoolean("events.kill-elo.enabled", true)) {
            if (event.getEntity().getKiller() != null) { // Vérifie si la victime a été tuée par un joueur
                Player killer = event.getEntity().getKiller();
                if (plugin.getConfig().contains("players." + killer.getUniqueId())) {
                    plugin.getConfig().set("players." + killer.getUniqueId() + ".elo",
                            plugin.getConfig().getInt("players." + killer.getUniqueId() + ".elo")
                                    + plugin.getConfig().getInt("events.kill-elo.amount", 10));
                    plugin.saveConfig();
                    killer.sendMessage(plugin.getMessage("elo-earn").replace("%amount%", String.valueOf(plugin.getConfig().getInt("events.kill-elo.amount", 10))));
                }
            }

        }
    }


}
