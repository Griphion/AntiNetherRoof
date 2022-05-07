package me.griphion.AntiNetherRoof.utils;

import me.griphion.AntiNetherRoof.Core;
import org.bukkit.Location;

public class ConfigUtils {

  public static String getWorldPunishment(String worldName){
    return Core.instance().getConfig().getString(worldName + ".punishment","deny");
  }

  public static double getWorldActivationHeight(String worldName){
    return Core.instance().getConfig().getDouble(worldName + ".activationHeight",127.0);
  }

  public static boolean isWorldEnabled(String worldName){
    return Core.instance().getConfig().getBoolean(worldName + ".enabled", false);
  }

  public static void setWorldToggle(String worldName, boolean state){
    Core.instance().getConfig().set(worldName + ".enabled", state);
    Core.instance().saveConfig();
  }

  public static void setWorldPunishment(String worldName, String punishmentName){
    Core.instance().getConfig().set(worldName + ".punishment", punishmentName);
    Core.instance().saveConfig();
  }

  public static void setWorldActivationHeight(String worldName, Double height){
    Core.instance().getConfig().set(worldName + ".activationHeight", height);
    Core.instance().saveConfig();
  }

  public static void setWorldTPPunishment(String worldName, Double x, Double y, Double z, String teleportWorldName) {
    Core.instance().getConfig().set(worldName + ".tpCoord.x", x);
    Core.instance().getConfig().set(worldName + ".tpCoord.y", y);
    Core.instance().getConfig().set(worldName + ".tpCoord.z", z);
    Core.instance().getConfig().set(worldName + ".tpCoord.world", teleportWorldName);
    Core.instance().saveConfig();
  }

  // isHotSet -> Si se ejecuta mientras corre el plugin (true)
  public static void setDefaultWorldToggle(String worldName, boolean isHotSet) {
    if(isHotSet){
      setWorldToggle(worldName,true);
    } else {
      Core.instance().getConfig().addDefault(worldName + ".enabled", Boolean.TRUE);
    }
  }

  public static void setDefaultWorldPunishment(String worldName, boolean isHotSet) {
    if(isHotSet){
      setWorldPunishment(worldName,"deny");
    } else {
      Core.instance().getConfig().addDefault(worldName + ".punishment", "deny");
    }
  }

  public static void setDefaultWorldActivationHeight(String worldName, boolean isHotSet) {
    if(isHotSet){
      setWorldActivationHeight(worldName,127.0);
    } else {
      Core.instance().getConfig().addDefault(worldName + ".activationHeight", 127.0);
    }
  }

  public static Location getWorldTPPunishmentLocation(String worldName){
    String tpWorldName = Core.instance().getConfig().getString(worldName + ".tpCoord.world");
    if(tpWorldName == null) return null;
    return new Location(
            Core.instance().getServer().getWorld(tpWorldName),
            Core.instance().getConfig().getDouble(worldName + ".tpCoord.x"),
            Core.instance().getConfig().getDouble(worldName + ".tpCoord.y"),
            Core.instance().getConfig().getDouble(worldName + ".tpCoord.z"));
  }
  public static void reloadConfig(){
    Core.instance().reloadConfig();
  }

}
