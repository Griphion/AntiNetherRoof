package me.griphion.AntiNetherRoof.punishments.punishment;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Deny_Punishment extends Punishment {

  public Deny_Punishment() {
    super("deny", "Teletransporta al jugador a la posicion 127 en Y");
  }

  @Override
  public void punish(final Player player, final World world) {
    Location loc = player.getLocation();
    loc.setY(126);
    player.teleport(loc);
  }

  @Override
  public void init(World world) {

  }
}