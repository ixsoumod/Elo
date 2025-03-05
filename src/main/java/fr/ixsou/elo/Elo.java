package fr.ixsou.elo;

import org.bukkit.plugin.java.JavaPlugin;

public final class Elo extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        getLogger().info("§aPlugin Elo activé !");

    }

    @Override
    public void onDisable() {

        saveDefaultConfig();

        getLogger().info("§cPlugin Elo désactivé !");
    }
}
