package me.griphion.AntiNetherRoof.commands.command;

import me.griphion.AntiNetherRoof.ANRMessages;
import me.griphion.AntiNetherRoof.Core;
import me.griphion.AntiNetherRoof.commands.ANRSubCommand;
import me.griphion.AntiNetherRoof.punishments.PunishmentManager;
import me.griphion.AntiNetherRoof.utils.CmdUtils;
import me.griphion.AntiNetherRoof.utils.ConfigUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public class Info_cmd extends ANRSubCommand {
  public Info_cmd() {
    super("info [World]", "Muestra información del mundo actual o del mundo indicado.");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args) {
    if(ANRMessages.noPermission("antinetherroof.command.info", sender)){
      return true;
    }

    String worldName = CmdUtils.consoleOrPlayer_GetWorldName(sender, args,0);
    if (worldName.isEmpty()) return true;

    sender.sendMessage(ANRMessages.SEPARATOR.getMessage());
    if(PunishmentManager.getInstance().worldHasPunishment(Core.instance().getServer().getWorld(worldName))) {
      sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Mundo: " + ChatColor.GOLD + worldName);
      PunishmentManager.getInstance().getPunishmentInfo(Core.instance().getServer().getWorld(worldName), sender);
      if(ConfigUtils.isWorldEnabled(worldName)){
        sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Estado: " + ChatColor.GREEN + "Activado");
      } else {
        sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Estado: " + ChatColor.DARK_RED + "Desactivado");
      }
    } else {
      sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Mundo: " + ChatColor.GOLD + worldName);
      sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Castigo: " + ChatColor.DARK_RED + ChatColor.BOLD +  "Este mundo no tiene castigo!");
    }
    sender.sendMessage(ANRMessages.SEPARATOR.getMessage());
    return true;
  }
}
