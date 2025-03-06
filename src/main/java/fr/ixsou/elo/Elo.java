package fr.ixsou.elo;

import fr.ixsou.elo.commands.EloAdmin;
import fr.ixsou.elo.commands.EloPlayer;
import fr.ixsou.elo.events.Events;
import fr.ixsou.elo.utils.TabCompleteAdmin;
import fr.ixsou.elo.utils.TabCompletePlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Elo extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();
        getCommand("eloadmin").setExecutor(new EloAdmin(this));
        getCommand("eloadmin").setTabCompleter(new TabCompleteAdmin(this));
        getCommand("elo").setTabCompleter(new TabCompletePlayer(this));
        getCommand("elo").setExecutor(new EloPlayer(this));

        registerEvents(new Events(this));

        getLogger().info("§aPlugin Elo activé !");

    }

    private void registerEvents(Events events) {
        getServer().getPluginManager().registerEvents(events, this);
    }

    @Override
    public void onDisable() {

        saveDefaultConfig();

        getLogger().info("§cPlugin Elo désactivé !");
    }

    public int getElo(Player player) {
        return getConfig().getInt("players." + player.getUniqueId() + ".elo");
    }

    public String getMessage(String path) {
        if (path.equals("no-found-message")) {
            return ChatColor.translateAlternateColorCodes('&', "&cMessage introuvable !");
        }

        Object messageObject = getConfig().get("messages." + path);

        if (messageObject instanceof String) {
            return ChatColor.translateAlternateColorCodes('&', (String) messageObject);
        }

        return getMessage("no-found-message");
    }

}
