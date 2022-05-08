package me.griphion.AntiNetherRoof.commands.command;

import me.griphion.AntiNetherRoof.ANRMessages;
import me.griphion.AntiNetherRoof.commands.ANRSubCommand;
import me.griphion.AntiNetherRoof.configs.WorldsConfig;
import me.griphion.AntiNetherRoof.utils.CmdUtils;
import me.griphion.AntiNetherRoof.utils.TabCompleteUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Toggle_cmd extends ANRSubCommand {
  public Toggle_cmd() {
    super("toggle [World]",
            "Activa/Desactiva el castigo del mundo actual o del mundo indicado.",
            "antinetherroof.command.toggle");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args) {
    if(ANRMessages.noPermission(getPermission(), sender)){
      return true;
    }

    String worldName = CmdUtils.consoleOrPlayer_GetWorldName(sender, args,0);
    if (worldName.isEmpty()) return true;
    if(WorldsConfig.getInstance().isWorldEnabled(worldName)){
      sender.sendMessage(ANRMessages.PLUGIN_PREFIX_SHORT.getMessage() + ChatColor.GRAY + "Castigo " + ChatColor.DARK_RED + "DESACTIVADO" + ChatColor.GRAY + " con éxito! En el mundo: " + ChatColor.GOLD + worldName);
      WorldsConfig.getInstance().setWorldToggle(worldName,false);
    } else {
      sender.sendMessage(ANRMessages.PLUGIN_PREFIX_SHORT.getMessage() + ChatColor.GRAY + "Castigo " + ChatColor.GREEN + "ACTIVADO" + ChatColor.GRAY + " con éxito! En el mundo: " + ChatColor.GOLD + worldName);
      WorldsConfig.getInstance().setWorldToggle(worldName,true);
    }
    return true;
  }
  @Override
  public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
    List<String> result = new ArrayList<>(15);
    TabCompleteUtils.addTabNetherWorldsToResult(args,result,1);
    return result;
  }
}
