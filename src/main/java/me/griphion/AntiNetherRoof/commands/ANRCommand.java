package me.griphion.AntiNetherRoof.commands;

import me.griphion.AntiNetherRoof.ANRMessages;
import me.griphion.AntiNetherRoof.commands.command.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class ANRCommand implements CommandExecutor, TabCompleter {

  private final static ANRCommand INSTANCE = new ANRCommand();
  private final TreeMap<String, ANRSubCommand> subCommands = new TreeMap<>();
  public static ANRCommand instance() {
    return INSTANCE;
  }
  private ANRCommand() {
    subCommands.put("help", new HelpCmd());
    subCommands.put("info", new InfoCmd());
    subCommands.put("punishment", new PunishmentCmd());
    subCommands.put("reload", new ReloadCmd());
    subCommands.put("setpunishment", new SetPunishmentCmd());
    subCommands.put("toggle", new ToggleCmd());
    subCommands.put("worlds", new WorldsCmd());
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
    if(cmd.getName().equalsIgnoreCase("antinetherroof") || cmd.getName().equalsIgnoreCase("anr")) {
      if (args.length == 0) {
        sender.sendMessage(ANRMessages.PLUGIN_PREFIX.getMessage() + ChatColor.WHITE + "Designed by " + ChatColor.RED + "Griphion");
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

  @Override
  public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
    List<String> result = new ArrayList<>(15);

    if(args.length == 1){
      for(String sc : subCommands.keySet()){
        if(sc.toLowerCase().startsWith(args[0].toLowerCase())
                && hasSubCommandPermission(sc,commandSender)) result.add(sc);
      }
    } else if (subCommands.containsKey(args[0]) && hasSubCommandPermission(args[0],commandSender)) {
      result.addAll(subCommands.get(args[0]).onTabComplete(commandSender, command, s, args));
    }

    return result;
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

  private boolean usoInvalido(CommandSender sender) {
    sender.sendMessage(ChatColor.GRAY + "Ayuda: /antinetherroof help");
    return true;
  }

  private boolean hasSubCommandPermission(String subCommand, CommandSender commandSender) {
    return commandSender.hasPermission(subCommands.get(subCommand).getPermission());
  }

}