package me.griphion.AntiNetherRoof.punishments;

import me.griphion.AntiNetherRoof.Core;
import me.griphion.AntiNetherRoof.configs.WorldsConfig;
import me.griphion.AntiNetherRoof.punishments.punishment.*;
import me.griphion.AntiNetherRoof.repos.WorldRepo;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PunishmentManager {
    //Singleton
    private static final PunishmentManager INSTANCE = new PunishmentManager();
    private PunishmentManager(){}
    public static PunishmentManager getInstance(){
        return INSTANCE;
    }
    private final List<String> availablePunishments = Arrays.asList("deny","freeze","tp","spawn","kill");
    private final HashMap<World,Punishment> worldPunishment = new HashMap<>(10);

    public Punishment getPunishmentByName(final String punishmentName){
        if(punishmentName == null) return new Deny_Punishment();
        switch(punishmentName){
            case "deny":
                return new Deny_Punishment();
            case "freeze":
                return new Freeze_Punishment();
            case "tp":
                return new TP_Punishment();
            case "spawn":
                return new Spawn_Punishment();
            case "kill":
                return new Kill_Punishment();
        }
        return new Deny_Punishment();
    }

    public List<String> getAvailablePunishments(){
        return availablePunishments;
    }

    public void setPunishment(World world, Punishment punishment){
        if(worldPunishment.containsKey(world)) worldPunishment.get(world).stop();
        worldPunishment.put(world,punishment);
        punishment.init(world);
    }

    public boolean isAPunishment(final String punishmentName, final CommandSender sender){
        if(isAPunishment(punishmentName)){
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "El castigo '"+ ChatColor.YELLOW + punishmentName + ChatColor.RED + "' no existe");
            return false;
        }
    }

    public boolean isAPunishment(final String punishmentName){
        return availablePunishments.contains(punishmentName);
    }

    public void punish(final Player player, final World world){
        worldPunishment.get(world).punish(player,world);
    }

    public void getPunishmentInfo(final World world, final CommandSender sender){
        final Punishment punishment = worldPunishment.get(world);
        if(punishment == null){
            sender.sendMessage(ChatColor.DARK_RED + "Castigo: INVÁLIDO! (Vea el config.yml y verifique que sea un castigo válido)");
        } else {
            punishment.info(world,sender);
        }
    }

    public void loadPunishments(){
        for (World world : Core.getInstance().getServer().getWorlds()) {
            if(WorldRepo.getInstance().isNether(world)) {
                setPunishment(world, getPunishmentByName(WorldsConfig.getInstance().getWorldPunishment(world.getName())));
            }
        }
    }

    public void unLoadPunishments(){
        for(Punishment p : worldPunishment.values()){
            if(p != null){
                p.stop();
            }
        }
        worldPunishment.clear();
    }

    public boolean worldHasPunishment(World world){
        return worldPunishment.containsKey(world);
    }
}
