package me.griphion.AntiNetherRoof.commands.command;

import me.griphion.AntiNetherRoof.ANRMessages;
import me.griphion.AntiNetherRoof.Core;
import me.griphion.AntiNetherRoof.commands.ANRSubCommand;
import me.griphion.AntiNetherRoof.configs.WorldsConfig;
import me.griphion.AntiNetherRoof.punishments.PunishmentManager;
import me.griphion.AntiNetherRoof.utils.CmdUtils;
import me.griphion.AntiNetherRoof.utils.TabCompleteUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class InfoCmd extends ANRSubCommand {
  public InfoCmd() {
    super("info [World]",
            "Muestra información del mundo actual o del mundo indicado.",
            "antinetherroof.command.info");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args) {
    if(ANRMessages.noPermission(getPermission(), sender)){
      return true;
    }

    String worldName = CmdUtils.consoleOrPlayerGetWorldName(sender, args,0);
    if (worldName.isEmpty()) return true;

    sender.sendMessage(ANRMessages.SEPARATOR.getMessage());
    if(PunishmentManager.getInstance().worldHasPunishment(Core.getInstance().getServer().getWorld(worldName))) {
      sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Mundo: " + ChatColor.GOLD + worldName);
      PunishmentManager.getInstance().getPunishmentInfo(Core.getInstance().getServer().getWorld(worldName), sender);
      if(WorldsConfig.getInstance().isWorldEnabled(worldName)){
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

  @Override
  public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
    List<String> result = new ArrayList<>(15);
    TabCompleteUtils.addTabNetherWorldsToResult(args,result,1);
    return result;
  }
}
