package me.griphion.AntiNetherRoof.commands.command;

import me.griphion.AntiNetherRoof.ANRMessages;
import me.griphion.AntiNetherRoof.Core;
import me.griphion.AntiNetherRoof.commands.ANRSubCommand;
import me.griphion.AntiNetherRoof.punishments.PunishmentManager;
import me.griphion.AntiNetherRoof.utils.ConfigUtils;
import me.griphion.AntiNetherRoof.utils.WorldRepo;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public class Reload_cmd extends ANRSubCommand {
  public Reload_cmd() {
    super("reload", "Recarga el archivo config y agrega todos los mundos (En caso de que falte alguno).");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args) {
    if(ANRMessages.noPermission("antinetherroof.command.reload", sender)){
      return true;
    }
    WorldRepo.getInstance().scanWorlds(true);
    ConfigUtils.reloadConfig();
    PunishmentManager.getInstance().loadPunishments();

    sender.sendMessage(ANRMessages.PLUGIN_PREFIX.getMessage() + ChatColor.GREEN + "Plugin reloaded!");
    return true;
  }
}
