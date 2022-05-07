package me.griphion.AntiNetherRoof;

import me.griphion.AntiNetherRoof.commands.ANRCommand;
import me.griphion.AntiNetherRoof.listeners.PlayerBuild_Listener;
import me.griphion.AntiNetherRoof.listeners.PlayerMove_Listener;
import me.griphion.AntiNetherRoof.listeners.PlayerPlaceEntity_Listener;
import me.griphion.AntiNetherRoof.listeners.PlayerUseItem_Listener;
import me.griphion.AntiNetherRoof.punishments.PunishmentManager;
import me.griphion.AntiNetherRoof.repos.WorldRepo;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Core extends JavaPlugin {
    //Singleton
    static private Core INSTANCE;

    static public Core instance() {
        return INSTANCE;
    }

    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();

        WorldRepo.getInstance().scanWorlds(false);
        PunishmentManager.getInstance().loadPunishments();

        // Save and load config files
        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();
        // ---------------

        // Listeners
        getServer().getPluginManager().registerEvents(new PlayerMove_Listener(), INSTANCE);
        getServer().getPluginManager().registerEvents(new PlayerPlaceEntity_Listener(), INSTANCE);
        getServer().getPluginManager().registerEvents(new PlayerBuild_Listener(), INSTANCE);
        getServer().getPluginManager().registerEvents(new PlayerUseItem_Listener(), INSTANCE);
        // ---------------

        Objects.requireNonNull(getCommand("antinetherroof")).setExecutor(ANRCommand.instance());
        Objects.requireNonNull(getCommand("antinetherroof")).setTabCompleter(ANRCommand.instance());

        Bukkit.getConsoleSender().sendMessage(ANRMessages.PLUGIN_ENABLED.getMessageWithLongPrefix());
    }

    public void onDisable() {
        Bukkit.getServer().getScheduler().cancelTasks(INSTANCE);
        PunishmentManager.getInstance().unLoadPunishments();

        Bukkit.getConsoleSender().sendMessage(ANRMessages.PLUGIN_DISABLED.getMessageWithLongPrefix());
    }

}
