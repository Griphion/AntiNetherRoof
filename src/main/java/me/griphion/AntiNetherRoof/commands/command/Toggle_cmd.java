package me.griphion.AntiNetherRoof.commands.command;

import me.griphion.AntiNetherRoof.ANRMessages;
import me.griphion.AntiNetherRoof.Core;
import me.griphion.AntiNetherRoof.commands.ANRSubCommand;
import me.griphion.AntiNetherRoof.utils.CmdUtils;
import me.griphion.AntiNetherRoof.utils.ConfigUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public class Toggle_cmd extends ANRSubCommand {
  public Toggle_cmd() {
    super("toggle [World]", "Activa/Desactiva el castigo del mundo actual o del mundo indicado.");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args) {
    if(ANRMessages.noPermission("antinetherroof.command.toggle", sender)){
      return true;
    }

    String worldName = CmdUtils.consoleOrPlayer_GetWorldName(sender, args,0);
    if (worldName.isEmpty()) return true;
    if(ConfigUtils.isWorldEnabled(worldName)){
      sender.sendMessage(ANRMessages.PLUGIN_PREFIX_SHORT.getMessage() + ChatColor.GRAY + "Castigo " + ChatColor.DARK_RED + "DESACTIVADO" + ChatColor.GRAY + " con éxito! En el mundo: " + ChatColor.GOLD + worldName);
      ConfigUtils.setWorldToggle(worldName,false);
    } else {
      sender.sendMessage(ANRMessages.PLUGIN_PREFIX_SHORT.getMessage() + ChatColor.GRAY + "Castigo " + ChatColor.GREEN + "ACTIVADO" + ChatColor.GRAY + " con éxito! En el mundo: " + ChatColor.GOLD + worldName);
      ConfigUtils.setWorldToggle(worldName,true);
    }
    return true;
  }

}