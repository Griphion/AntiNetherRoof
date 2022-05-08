package me.griphion.AntiNetherRoof.commands.command;

import me.griphion.AntiNetherRoof.ANRMessages;
import me.griphion.AntiNetherRoof.Core;
import me.griphion.AntiNetherRoof.commands.ANRSubCommand;
import me.griphion.AntiNetherRoof.punishments.PunishmentManager;
import me.griphion.AntiNetherRoof.punishments.punishment.Punishment;
import me.griphion.AntiNetherRoof.repos.WorldRepo;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SetPunishment_cmd extends ANRSubCommand {
  public SetPunishment_cmd() {
    super("setpunishment [World] <Punishment> [Param]",
            "Asigna el castigo para el mundo actual o para el mundo indicado.",
            "antinetherroof.command.setpunishment");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args) {
    if(ANRMessages.noPermission(getPermission(), sender)){
      return true;
    }

    if(args.length == 0) {
      sender.sendMessage(ChatColor.GRAY + "Uso: /antinetherroof " + this.getUsage());
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

  @Override
  public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
    List<String> result = new ArrayList<>(15);

    if(commandSender instanceof Player) { //Si es un player
      if(args.length == 2){
        getTabPunishments(args,result,1);
      } else if (args.length == 3 && WorldRepo.getInstance().isANetherWorldName(args[1])) {
        getTabPunishments(args,result,2);
      } else { //Autocompletar parametros
        if (Objects.equals(args[1], "tp")) {
          getTabTpParameters(commandSender,args,result,false);
        } else if (WorldRepo.getInstance().isANetherWorldName(args[1]) && Objects.equals(args[2], "tp")) {
          getTabTpParameters(commandSender,args,result,true);
        }
      }
    } else { //Si es la consola le completa "<World> <Punishment> [Param]"
      if(args.length == 2){
        getTabNetherWorld(args,result,1);
      } else if (args.length == 3){
        getTabPunishments(args,result,2);
      }
    }

    return result;
  }

  private void getTabPunishments(String[] args, List<String> result, int arrayPosition){
    PunishmentManager.getInstance().getAvailablePunishments().forEach(punish -> {
      if (punish.toLowerCase().startsWith(args[arrayPosition].toLowerCase())) result.add(punish);
    });
  }

  private void getTabNetherWorld(String[] args, List<String> result, int arrayPosition){
    WorldRepo.getInstance().getNetherWorlds().forEach(world -> {
      if (world.toLowerCase().startsWith(args[arrayPosition].toLowerCase())) result.add(world);
    });
  }

  private void getTabTpParameters(CommandSender commandSender, String[] args, List<String> result, boolean hasWorld){
    double x,y,z;
    int argsLength = hasWorld ? args.length - 1 : args.length;
    switch (argsLength){
      case 3:
        x = getLookingCoords(commandSender).getX();
        result.add(String.valueOf(x));
        break;
      case 4:
        y = getLookingCoords(commandSender).getY() + 1;
        result.add(String.valueOf(y));
        break;
      case 5:
        z = getLookingCoords(commandSender).getZ();
        result.add(String.valueOf(z));
        break;
      case 6:
        Player player = (Player) commandSender;
        result.add(player.getWorld().getName());
        break;
    }
  }
  private Location getLookingCoords(final CommandSender sender){
    Player player = (Player) sender;
    Block block = player.getTargetBlock(null, 100);
    return block.getLocation();
  }
}
