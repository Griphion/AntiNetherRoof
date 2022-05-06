package me.griphion.AntiNetherRoof.commands;

import me.griphion.AntiNetherRoof.ANRMessages;
import me.griphion.AntiNetherRoof.commands.command.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;

public class ANRCommand implements CommandExecutor {

  private final static ANRCommand INSTANCE = new ANRCommand();
  public static ANRCommand instance() {
    return INSTANCE;
  }

  private final TreeMap<String, ANRSubCommand> subCommands = new TreeMap<>();
  private ANRCommand() {
    subCommands.put("help", new Help_cmd());
    subCommands.put("info", new Info_cmd());
    subCommands.put("punishment", new Punishment_cmd());
    subCommands.put("reload", new Reload_cmd());
    subCommands.put("setpunishment", new SetPunishment_cmd());
    subCommands.put("toggle", new Toggle_cmd());
    subCommands.put("worlds", new Worlds_cmd());
  }
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
    if(cmd.getName().equalsIgnoreCase("antinetherroof") || cmd.getName().equalsIgnoreCase("anr")) {
      if (args.length == 0) {
        sender.sendMessage(ANRMessages.PLUGIN_PREFIX.getMessage() + ChatColor.WHITE + "Desarrollado por " + ChatColor.RED + "Griphion");
        return true;
      }
      ANRSubCommand subCmd = getSubCommand(args[0]);
      if (subCmd != null) {
        return subCmd.execute(sender, Arrays.copyOfRange(args, 1, args.length));
      } else {
        return usoInvalido(sender);
      }
    }
    return false;
  }
  private ANRSubCommand getSubCommand(String subCommandName) {
    return subCommands.get(subCommandName.toLowerCase());
  }

  public Collection<ANRSubCommand> getSubCommands() {
    return subCommands.values();
  }

  public Set<String> getSubCommandsNames() {
    return subCommands.keySet();
  }

  private static boolean usoInvalido(CommandSender sender) {
    sender.sendMessage(ChatColor.GRAY + "Ayuda: /antinetherroof help");
    return true;
  }
}