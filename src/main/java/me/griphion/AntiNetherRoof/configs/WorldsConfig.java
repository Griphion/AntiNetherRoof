package me.griphion.AntiNetherRoof.configs;

import me.griphion.AntiNetherRoof.Core;
import org.bukkit.Location;

public class WorldsConfig extends CustomConfigFile{
    //Singleton
    private static final WorldsConfig INSTANCE = new WorldsConfig();

    private WorldsConfig() {
        super("worlds", "");
    }

    public static WorldsConfig getInstance() {
        return INSTANCE;
    }


    public String getWorldPunishment(String worldName){
      return getConfig().getString(worldName + ".punishment","deny");
    }

    public double getWorldActivationHeight(String worldName){
      return getConfig().getDouble(worldName + ".activationHeight",127.0);
    }

    public boolean isWorldEnabled(String worldName){
      return getConfig().getBoolean(worldName + ".enabled", false);
    }

    public void setWorldToggle(String worldName, boolean state){
      getConfig().set(worldName + ".enabled", state);
      saveConfig();
    }

    public void setWorldPunishment(String worldName, String punishmentName){
      getConfig().set(worldName + ".punishment", punishmentName);
      saveConfig();
    }

    public void setWorldActivationHeight(String worldName, Double height){
      getConfig().set(worldName + ".activationHeight", height);
      saveConfig();
    }

    public void setWorldTPPunishment(String worldName, Double x, Double y, Double z, String teleportWorldName) {
      getConfig().set(worldName + ".tpCoord.x", x);
      getConfig().set(worldName + ".tpCoord.y", y);
      getConfig().set(worldName + ".tpCoord.z", z);
      getConfig().set(worldName + ".tpCoord.world", teleportWorldName);
      saveConfig();
    }

    // isHotSet -> Si se ejecuta mientras corre el plugin (true)
    public void setDefaultWorldToggle(String worldName, boolean isHotSet) {
      if(isHotSet){
        setWorldToggle(worldName,true);
      } else {
        getConfig().addDefault(worldName + ".enabled", Boolean.TRUE);
      }
    }

    public void setDefaultWorldPunishment(String worldName, boolean isHotSet) {
      if(isHotSet){
        setWorldPunishment(worldName,"deny");
      } else {
        getConfig().addDefault(worldName + ".punishment", "deny");
      }
    }

    public void setDefaultWorldActivationHeight(String worldName, boolean isHotSet) {
      if(isHotSet){
        setWorldActivationHeight(worldName,127.0);
      } else {
        getConfig().addDefault(worldName + ".activationHeight", 127.0);
      }
    }

    public void setUpAllDefaults(String worldName, boolean isHotSet){
        setDefaultWorldToggle(worldName,isHotSet);
        setDefaultWorldPunishment(worldName,isHotSet);
        setDefaultWorldActivationHeight(worldName,isHotSet);
        saveCopyDefaults();
        reloadConfig();
    }

    public Location getWorldTPPunishmentLocation(String worldName){
      String tpWorldName = getConfig().getString(worldName + ".tpCoord.world");
      if(tpWorldName == null) return null;
      return new Location(
              Core.getInstance().getServer().getWorld(tpWorldName),
              getConfig().getDouble(worldName + ".tpCoord.x"),
              getConfig().getDouble(worldName + ".tpCoord.y"),
              getConfig().getDouble(worldName + ".tpCoord.z"));
    }
}
