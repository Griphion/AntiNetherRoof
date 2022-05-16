package me.griphion.AntiNetherRoof.utils;

import me.griphion.AntiNetherRoof.ANRMessages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdUtils {

  // Devulelve el nombre del mundo o vacío en caso de fallar
  // Nota: Solo funciona si el nombre del mundo es el último elemento del args a la derecha
  public static String consoleOrPlayerGetWorldName(CommandSender sender, String[] args, int worldNamePosition) {
    String worldName;
    if(args.length <= worldNamePosition){
      if(sender instanceof Player){
        Player player = (Player) sender;
        worldName = player.getWorld().getName();
      }else {
        sender.sendMessage(ANRMessages.PLUGIN_PREFIX_SHORT.getMessage() + ANRMessages.NEED_WORLD_NAME.getMessage());
        return "";
      }
    } else {
      worldName = args[worldNamePosition];
    }

    if(ANRMessages.notAValidWorld(worldName, sender)){
      return "";
    }

    return worldName;
  }
}
