package me.griphion.AntiNetherRoof.punishments.punishment;

import me.griphion.AntiNetherRoof.ANRMessages;
import me.griphion.AntiNetherRoof.Core;
import me.griphion.AntiNetherRoof.utils.ConfigUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TP_Punishment extends Punishment {

  Location tpLocation = null;

  public TP_Punishment() {
    super("tp", "Teletransporta al jugador al lugar designado.");
  }


  @Override
  public boolean setUp(final World world, final CommandSender sender, final String[] args) { //setpunishment mundo tp [x y z mundo]
    double x, y, z;
    if(args.length == 0){
      sender.sendMessage(ChatColor.GRAY + "Uso: /antinetherroof setpunishment " + this.name + " <CoordX> <CoordY> <CoordZ> <World>");
      return false;
    }

    try {
      x = Double.parseDouble(args[0]);
      y = Double.parseDouble(args[1]);
      z = Double.parseDouble(args[2]);
    } catch (NullPointerException npe) {
      sender.sendMessage(ANRMessages.PLUGIN_PREFIX_SHORT.getMessage() + ChatColor.RED + "No se ha ingresado una coordenada!");
      return false;
    } catch (NumberFormatException nfe) {
      sender.sendMessage(ANRMessages.PLUGIN_PREFIX_SHORT.getMessage() + ChatColor.RED + "Se ha ingresado mal una coordenada!");
      return false;
    }

    if(args.length < 4){
      sender.sendMessage(ANRMessages.NEED_WORLD_NAME.getMessageWithPrefix());
      return false;
    }
    if(ANRMessages.worldDoesNotExist(args[3], sender)){
      return false;
    }

    ConfigUtils.setWorldPunishment(world.getName(),name);
    ConfigUtils.setWorldTPPunishment(world.getName(), x, y, z, args[3]);

    sender.sendMessage(ANRMessages.PLUGIN_PREFIX_SHORT.getMessage()
        + ChatColor.GREEN + "Castigo cambiado con éxito! "
        + ChatColor.GRAY + "(Mundo: "
        + ChatColor.GOLD + world.getName()
        + ChatColor.GRAY + ") Nuevo castigo: TP hacia "
        + ChatColor.DARK_GRAY + "[" + x + ", " + y + ", " + z + "]"
        + ChatColor.GRAY + " en "
        + ChatColor.DARK_GRAY + args[3]);

    return true;
  }

  @Override
  public void punish(final Player player, final World world) {
    player.teleport(tpLocation);
  }

  @Override
  public void init(World world) {
    setUpTPLocation(world);
  }

  @Override
  public void info(final World world, final CommandSender sender) {
    World tpWorld = tpLocation.getWorld();
    if (tpWorld == null) sender.sendMessage(ChatColor.DARK_RED + "Error al obtener el mundo al que hacer TP!");
    sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Castigo: "
        + ChatColor.RED + this.name
        + ChatColor.GRAY + " -> "
        + ChatColor.RED + "[" + tpLocation.getX()
        + ", " + tpLocation.getY()
        + ", " + tpLocation.getZ() + "]"
        + ChatColor.GRAY + " en "
        + ChatColor.RED + tpLocation.getWorld().getName());
  }

  private void setUpTPLocation(World world){
    Location location = ConfigUtils.getWorldTPPunishmentLocation(world.getName());
    if(location == null || location.getWorld() == null){
      tpLocation = world.getSpawnLocation();
      Core.instance().getLogger().warning(ANRMessages.PLUGIN_PREFIX.getMessage() + ChatColor.RED + "La coordenada configurada para el mundo " + world.getName() + " es inválida (No existe ese mundo)");
      return;
    }
    tpLocation = location;
  }
}
