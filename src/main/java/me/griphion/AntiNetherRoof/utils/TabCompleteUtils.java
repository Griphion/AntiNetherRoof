package me.griphion.AntiNetherRoof.utils;

import me.griphion.AntiNetherRoof.punishments.PunishmentManager;
import me.griphion.AntiNetherRoof.repos.WorldRepo;

import java.util.List;

public class TabCompleteUtils {

    public static void addTabPunishmentsToResult(String[] args, List<String> result, int argsPosition){
      PunishmentManager.getInstance().getAvailablePunishments().forEach(punish -> {
        if (punish.toLowerCase().startsWith(args[argsPosition].toLowerCase())) result.add(punish);
      });
    }

    public static void addTabNetherWorldsToResult(String[] args, List<String> result, int argsPosition){
      WorldRepo.getInstance().getNetherWorlds().forEach(world -> {
        if (world.toLowerCase().startsWith(args[argsPosition].toLowerCase())) result.add(world);
      });
    }
}
