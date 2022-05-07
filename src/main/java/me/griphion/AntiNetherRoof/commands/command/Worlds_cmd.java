package me.griphion.AntiNetherRoof.commands.command;

import me.griphion.AntiNetherRoof.ANRMessages;
import me.griphion.AntiNetherRoof.commands.ANRSubCommand;
import me.griphion.AntiNetherRoof.repos.WorldRepo;
import me.griphion.AntiNetherRoof.utils.StringUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public class Worlds_cmd extends ANRSubCommand {
  public Worlds_cmd() {
    super("worlds", "Muestra los mundos disponibles.", "antinetherroof.command.worlds");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args) {
    if(ANRMessages.noPermission(getPermission(), sender)){
      return true;
    }
    sender.sendMessage(ANRMessages.SEPARATOR.getMessage());
    sender.sendMessage(ChatColor.DARK_AQUA + "Mundos disponibles:"+ ChatColor.GRAY + StringUtils.separateWithCommas(WorldRepo.getInstance().getNetherWorlds()));
    sender.sendMessage(ANRMessages.SEPARATOR.getMessage());
    return true;
  }
}
