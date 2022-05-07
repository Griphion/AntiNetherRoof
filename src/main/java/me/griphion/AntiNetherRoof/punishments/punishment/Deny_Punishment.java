package me.griphion.AntiNetherRoof.punishments.punishment;

import me.griphion.AntiNetherRoof.utils.ConfigUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Deny_Punishment extends Punishment {

  public Deny_Punishment() {
    super("deny", "Teletransporta al jugador dos bloques por debajo del límite");
  }

  @Override
  public void punish(final Player player, final World world) {
    Location loc = player.getLocation();
    loc.setY(ConfigUtils.getWorldActivationHeight(world.getName()) - 2);
    player.teleport(loc);
  }

  @Override
  public void init(World world) {

  }
}