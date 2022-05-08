package me.griphion.AntiNetherRoof;

import me.griphion.AntiNetherRoof.repos.WorldRepo;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public enum ANRMessages {

  PLUGIN_PREFIX("&8[&7Anti&8-&7NetherRoof&8] "),
  PLUGIN_PREFIX_SHORT("&8[&7ANR&8] "),
  PLUGIN_TITLE("&7=&m======&r&7=&8 [&7Anti&8-&7NetherRoof&8] &7=&m======&r&7="),
  SEPARATOR("&7=&m==============================&r&7="),
  PLUGIN_ENABLED("&aSuccessfully enabled!"),
  PLUGIN_DISABLED("&cDisabled!"),
  NO_PERMISSION_COMMAND("&cNo tienes permiso para ejecutar ese comando!"),
  NO_PERMISSION_ENTER("&cNo tienes permiso para entrar aquí!"),
  NO_PERMISSION_PLACE_BLOCK("&cNo tienes permiso para poner bloques aquí!"),
  NO_PERMISSION_BREAK_BLOCK("&cNo tienes permiso para romper bloques aquí!"),
  NO_PERMISSION_PLACE_ENTITY("&cNo tienes permiso para poner entidades/líquidos aquí!"),
  NO_PERMISSION_USE("&cNo tienes permiso para usar eso aquí!"),
  NEED_WORLD_NAME("&cNecesitas escribir el nombre del mundo!"),
  NEED_PUNISHMENT_NAME("&cNecesitas escribir el nombre del castigo!");

  private final String message;

  ANRMessages(String message){
    this.message = ChatColor.translateAlternateColorCodes('&',message);
  }

  public static boolean noPermission(final String permission, final CommandSender sender){
      if(!sender.hasPermission(permission)){
          sender.sendMessage(NO_PERMISSION_COMMAND.message);
          return true;
      }
      return false;
  }

  public static boolean notAValidWorld(final String name, final CommandSender sender){
    if(worldDoesNotExist(name, sender)) return true;
    World world = Core.getInstance().getServer().getWorld(name);
    if(WorldRepo.getInstance().isNether(world)){
      return false;
    }
    sender.sendMessage(PLUGIN_PREFIX_SHORT.message + org.bukkit.ChatColor.RED + "El mundo '"+ org.bukkit.ChatColor.GOLD + name + org.bukkit.ChatColor.RED + "' no es un mundo válido!");
    return true;
  }

  public static boolean worldDoesNotExist(final String name, final CommandSender sender){
    if(name != null && Core.getInstance().getServer().getWorld(name) != null){
      return false;
    }
    sender.sendMessage(PLUGIN_PREFIX_SHORT.message + org.bukkit.ChatColor.RED + "El mundo '"+ org.bukkit.ChatColor.GOLD + name + org.bukkit.ChatColor.RED + "' no existe!");
    return true;
  }

  public String getMessage() {
    return message;
  }

  public String getMessageWithPrefix() {
    return PLUGIN_PREFIX_SHORT.message + message;
  }

  public String getMessageWithLongPrefix() {
    return PLUGIN_PREFIX.message + message;
  }

  @Override
  public String toString() {
    return this.message;
  }
}