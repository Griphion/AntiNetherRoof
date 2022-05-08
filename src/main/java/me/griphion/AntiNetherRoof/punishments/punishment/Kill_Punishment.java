package me.griphion.AntiNetherRoof.punishments.punishment;

import me.griphion.AntiNetherRoof.configs.WorldsConfig;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Kill_Punishment extends Punishment {

  public Kill_Punishment() {
    super("kill", "Mata al jugador.");
  }

  @Override
  public void punish(final Player player, final World world) {
    Location loc = player.getLocation();
    loc.setY(WorldsConfig.getInstance().getWorldActivationHeight(world.getName()) - 2);
    player.teleport(loc);
    player.setHealth(0);
  }

  @Override
  public void init(World world) {

  }
}
