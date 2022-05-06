package me.griphion.AntiNetherRoof.punishments.punishment;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class Spawn_Punishment extends Punishment {

  public Spawn_Punishment() {
    super("spawn", "Envía al jugador al spawn natural (Vanilla) del Nether.");
  }

  @Override
  public void punish(final Player player, final World world) {
    player.teleport(world.getSpawnLocation());
  }

  @Override
  public void init(World world) {

  }
}