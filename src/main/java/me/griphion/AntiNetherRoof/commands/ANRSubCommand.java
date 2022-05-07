package me.griphion.AntiNetherRoof.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public abstract class ANRSubCommand {
  private final String usage;
  private final String description;
  private final String permission;

  public ANRSubCommand(String usage, String description, String permission) {
    this.usage = usage;
    this.description = description;
    this.permission = permission;
  }

  public String getUsage() {
    return usage;
  }

  public String getDescription() {
    return description;
  }

  public String getPermission() {
    return permission;
  }

  public abstract boolean execute(CommandSender sender, String[] args);

  public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
    return Collections.emptyList();
  }
}