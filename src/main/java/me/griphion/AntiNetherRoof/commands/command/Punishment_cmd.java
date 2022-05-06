package me.griphion.AntiNetherRoof.commands.command;

import me.griphion.AntiNetherRoof.ANRMessages;
import me.griphion.AntiNetherRoof.commands.ANRSubCommand;
import me.griphion.AntiNetherRoof.punishments.PunishmentManager;
import me.griphion.AntiNetherRoof.utils.StringUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public class Punishment_cmd extends ANRSubCommand {
  public Punishment_cmd() {
    super("punishment [Punishment]", "Te muestra los castigos disponibles o lo que hace el castigo (Si se indica).");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args) {
    if(ANRMessages.noPermission("antinetherroof.command.punishment", sender)){
      return true;
    }

    if(args.length > 0){
      if(PunishmentManager.getInstance().isAPunishment(args[0],sender)){
        sender.sendMessage(ANRMessages.SEPARATOR.getMessage());
        sender.sendMessage(ChatColor.DARK_AQUA + "- Castigo - ");
        PunishmentManager.getPunishmentByName(args[0]).help(sender);
      }else {
        return true;
      }
    }else {
      sender.sendMessage(ANRMessages.SEPARATOR.getMessage());
      sender.sendMessage(ChatColor.DARK_AQUA + "Castigos disponibles: " +
          ChatColor.WHITE + StringUtils.separateWithCommas(PunishmentManager.getInstance().getAvailablePunishments()));
    }
    sender.sendMessage(ANRMessages.SEPARATOR.getMessage());

    return true;
  }
}
