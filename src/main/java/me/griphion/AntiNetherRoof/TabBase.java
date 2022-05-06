package me.griphion.AntiNetherRoof;

import me.griphion.AntiNetherRoof.commands.ANRCommand;
import me.griphion.AntiNetherRoof.punishments.PunishmentManager;
import me.griphion.AntiNetherRoof.utils.WorldRepo;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TabBase implements TabCompleter {

    private final Set<String> argumentosBase = ANRCommand.instance().getSubCommandsNames();
    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        List<String> result = new ArrayList<>(15);
        double x,y,z;

        if(args.length == 1){
            for(String a : argumentosBase){
                if(a.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(a);
            }
            return result;
        }

        if(args.length == 2 && (args[0].equalsIgnoreCase("toggle") || args[0].equalsIgnoreCase("info") )){
            for(String a : WorldRepo.getInstance().getNetherWorlds()){
                if(a.toLowerCase().startsWith(args[1].toLowerCase()))
                    result.add(a);
            }
            return result;
        }

        if(args.length == 2 && args[0].equalsIgnoreCase("punishment")){
            for(String a : PunishmentManager.getInstance().getAvailablePunishments()){
                if(a.toLowerCase().startsWith(args[1].toLowerCase()))
                    result.add(a);
            }
            return result;
        }

        if(args.length == 2 && args[0].equalsIgnoreCase("setpunishment")){
            if(sender instanceof Player) {
                for (String a : PunishmentManager.getInstance().getAvailablePunishments()) {
                    if (a.toLowerCase().startsWith(args[1].toLowerCase()))
                        result.add(a);
                }
            }else {
                for(String a : WorldRepo.getInstance().getNetherWorlds()){
                    if(a.toLowerCase().startsWith(args[1].toLowerCase()))
                        result.add(a);
                }
            }
            return result;
        }

        if(args.length == 3 && args[0].equalsIgnoreCase("setpunishment") && WorldRepo.getInstance().getNetherWorlds().contains(args[1])){
            for(String a : PunishmentManager.getInstance().getAvailablePunishments()){
                if(a.toLowerCase().startsWith(args[2].toLowerCase()))
                    result.add(a);
            }
            return result;
        }

        if(args.length > 2 && args.length < 7 && args[1].equalsIgnoreCase("tp")){
            switch (args.length){
                case 3:
                    if(sender instanceof Player) {
                        x = getLookingCoords(sender).getX();
                        result.add(String.valueOf(x));
                    }
                    return result;
                case 4:
                    if(sender instanceof Player) {
                        y = getLookingCoords(sender).getY() + 1;
                        result.add(String.valueOf(y));
                    }
                    return result;
                case 5:
                    if(sender instanceof Player) {
                        z = getLookingCoords(sender).getZ();
                        result.add(String.valueOf(z));
                    }
                    return result;
                case 6:
                    if(sender instanceof Player){
                        Player player = (Player) sender;
                        result.add(player.getWorld().getName());
                    }else {
                        for(String a : WorldRepo.getInstance().getAllWorlds()){
                            if(a.toLowerCase().startsWith(args[5].toLowerCase()))
                                result.add(a);
                        }
                    }
                    return result;
            }

        }

        if(args.length > 3 && args.length < 8 && args[2].equalsIgnoreCase("tp") && WorldRepo.getInstance().getNetherWorlds().contains(args[1])){
            switch (args.length){
                case 4:
                    if(sender instanceof Player) {
                        x = getLookingCoords(sender).getX();
                        result.add(String.valueOf(x));
                    }
                    return result;
                case 5:
                    if(sender instanceof Player) {
                        y = getLookingCoords(sender).getY() + 1;
                        result.add(String.valueOf(y));
                    }
                    return result;
                case 6:
                    if(sender instanceof Player) {
                        z = getLookingCoords(sender).getZ();
                        result.add(String.valueOf(z));
                    }
                    return result;
                case 7:
                    if(sender instanceof Player){
                        Player player = (Player) sender;
                        result.add(player.getWorld().getName());
                    }else {
                        for(String a : WorldRepo.getInstance().getAllWorlds()){
                            if(a.toLowerCase().startsWith(args[6].toLowerCase()))
                                result.add(a);
                        }
                    }
                    return result;
            }
        }

        return null;
    }


    public static Location getLookingCoords(final CommandSender sender){
        Player player = (Player) sender;
        Block block = player.getTargetBlock(null, 100);
        return block.getLocation();
    }
}
