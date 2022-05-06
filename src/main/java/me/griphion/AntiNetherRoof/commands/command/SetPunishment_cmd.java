package me.griphion.AntiNetherRoof.commands.command;

import me.griphion.AntiNetherRoof.ANRMessages;
import me.griphion.AntiNetherRoof.Core;
import me.griphion.AntiNetherRoof.commands.ANRSubCommand;
import me.griphion.AntiNetherRoof.punishments.PunishmentManager;
import me.griphion.AntiNetherRoof.punishments.punishment.Punishment;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SetPunishment_cmd extends ANRSubCommand {
  public SetPunishment_cmd() {
    super("setpunishment [World] <Punishment> [Param]", "Asigna el castigo para el mundo actual o para el mundo indicado.");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args) {
    if(ANRMessages.noPermission("antinetherroof.command.setpunishment", sender)){
      return true;
    }

    if(args.length == 0) {
      sender.sendMessage(ChatColor.GRAY + "Uso: /antinetherroof setpunishment [World] <Punishment> [Param]");
      return true;
    }

    short ppos = 0;
    World world = Core.instance().getServer().getWorld(args[0]);

    if(world != null){ // Si el mundo está en el primer elemento
      if(args.length > 1){
        ppos++;
      } else {
        sender.sendMessage(ANRMessages.NEED_PUNISHMENT_NAME.getMessageWithPrefix());
        return true;
      }
    } else { // Si el mundo no está en el primer elemento
      if(sender instanceof Player){
        world = ((Player) sender).getWorld();
      } else {
        sender.sendMessage(ANRMessages.NEED_WORLD_NAME.getMessageWithPrefix());
        return true;
      }
    }

    if(ANRMessages.notAValidWorld(world.getName(), sender)) return true;
    if(!PunishmentManager.getInstance().isAPunishment(args[ppos], sender)) return true;

    Punishment punishment = PunishmentManager.getPunishmentByName(args[ppos]);
    if(punishment.setUp(world,sender, Arrays.copyOfRange(args,1 + ppos,args.length))){
      PunishmentManager.getInstance().setPunishment(world,punishment);
    }
    return true;
  }
}
