package me.griphion.AntiNetherRoof.repos;

import me.griphion.AntiNetherRoof.Core;
import me.griphion.AntiNetherRoof.utils.ConfigUtils;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashSet;
import java.util.Set;

public class WorldRepo {
  //Singleton
  private static final WorldRepo INSTANCE = new WorldRepo();
  public static WorldRepo getInstance() {
    return INSTANCE;
  }

  private final Set<String> netherWorlds = new HashSet<>(2);
  private final Set<String> allWorlds = new HashSet<>(10);

  public static boolean isInNetherRoof(Location location){
    if(location == null) return false;
    World world = location.getWorld();
    if(world == null) return false;
    double worldActivationHeight = ConfigUtils.getWorldActivationHeight(world.getName());
    return (location.getY() >= worldActivationHeight && isNether(location.getWorld()));
  }

  // HotScan -> Se ejecuta mientras el plugin ya esta cargado (Reload)
  public void scanWorlds(boolean hotScan) {
      for (World world : Core.instance().getServer().getWorlds()) {
        allWorlds.add(world.getName());
          if (isNether(world) && !netherWorlds.contains(world.getName())) {
              ConfigUtils.setDefaultWorldPunishment(world.getName(),hotScan);
              ConfigUtils.setDefaultWorldToggle(world.getName(),hotScan);
              ConfigUtils.setDefaultWorldActivationHeight(world.getName(),hotScan);
              netherWorlds.add(world.getName());
          }
      }
  }

  public static boolean isNether(World world){
    if(world == null) return false;
    return world.getEnvironment() == World.Environment.NETHER;
  }

  public Set<String> getNetherWorlds() {
    return netherWorlds;
  }

  public Set<String> getAllWorlds() {
    return allWorlds;
  }
}
