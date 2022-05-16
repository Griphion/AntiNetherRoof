package me.griphion.AntiNetherRoof;

import me.griphion.AntiNetherRoof.commands.ANRCommand;
import me.griphion.AntiNetherRoof.listeners.PlayerBuildListener;
import me.griphion.AntiNetherRoof.listeners.PlayerMoveListener;
import me.griphion.AntiNetherRoof.listeners.PlayerPlaceEntityListener;
import me.griphion.AntiNetherRoof.listeners.PlayerUseItemListener;
import me.griphion.AntiNetherRoof.punishments.PunishmentManager;
import me.griphion.AntiNetherRoof.repos.WorldRepo;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Core extends JavaPlugin {
    //Singleton
    private static Core INSTANCE;

    public static Core getInstance() {
        return INSTANCE;
    }

    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();

        WorldRepo.getInstance().scanWorlds(false);
        PunishmentManager.getInstance().loadPunishments();

        // Listeners
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), INSTANCE);
        getServer().getPluginManager().registerEvents(new PlayerPlaceEntityListener(), INSTANCE);
        getServer().getPluginManager().registerEvents(new PlayerBuildListener(), INSTANCE);
        getServer().getPluginManager().registerEvents(new PlayerUseItemListener(), INSTANCE);
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
