package me.griphion.AntiNetherRoof.commands.command;

import me.griphion.AntiNetherRoof.ANRMessages;
import me.griphion.AntiNetherRoof.commands.ANRCommand;
import me.griphion.AntiNetherRoof.commands.ANRSubCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public class Help_cmd extends ANRSubCommand {
  public Help_cmd() {
    super("help", "Muestra los comandos disponibles.");
  }

  private final String helpUsagePrefix = ChatColor.DARK_AQUA + "/anr ";
  private final String helpDescriptionPrefix = ChatColor.GRAY + ">" + ChatColor.DARK_GRAY + ">" + ChatColor.WHITE + " ";

  @Override
  public boolean execute(CommandSender sender, String[] args) {
    if(ANRMessages.noPermission("antinetherroof.command.help", sender)){
      return true;
    }
    sender.sendMessage(ANRMessages.PLUGIN_TITLE.getMessage());
    for(ANRSubCommand cmd : ANRCommand.instance().getSubCommands()){
      sender.sendMessage(helpUsagePrefix + cmd.getUsage());
      sender.sendMessage(helpDescriptionPrefix + cmd.getDescription());
    }
    sender.sendMessage(ANRMessages.SEPARATOR.getMessage());
    return true;
  }
}
