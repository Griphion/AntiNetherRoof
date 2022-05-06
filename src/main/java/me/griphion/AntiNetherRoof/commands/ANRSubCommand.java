package me.griphion.AntiNetherRoof.commands;

import org.bukkit.command.CommandSender;

public abstract class ANRSubCommand {
  private final String usage;
  private final String description;

  public ANRSubCommand(String usage, String description) {
    this.usage = usage;
    this.description = description;
  }

  public String getUsage() {
    return usage;
  }

  public String getDescription() {
    return description;
  }

  public abstract boolean execute(CommandSender sender, String[] args);
}