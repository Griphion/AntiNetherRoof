package me.griphion.AntiNetherRoof.punishments.punishment;

import me.griphion.AntiNetherRoof.ANRMessages;
import me.griphion.AntiNetherRoof.configs.WorldsConfig;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
* Aquí se crean todos los castigos, cuando se cree uno nuevo hay que:
* - Agregarlo a la lista del "PunishmentManager"
* - Agregarlo al SWITCH del "PunishmentManager"
* - Agregarlo aquí como una nueva clase que extienda a Castigos y que implemente todos los métodos necesarios
* */

// Nota: Cuando el castigo recibe parámetros, estos mismos castigos tienen que manejar la respuesta en caso de que sea inválido lo ingresado!

public abstract class Punishment {
    String name;
    String description;

    public Punishment(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /*Para el /anr help */
    public void help(final CommandSender sender) {
        sender.sendMessage(ChatColor.GRAY + this.name + " - " + ChatColor.DARK_GRAY + this.description);
    }

    /* Para el /anr info */
    public void info(final World world, final CommandSender sender){
        sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Castigo: " + ChatColor.RED + this.name);
    }

    /* Configura el castigo en el mundo */
    public boolean setUp(final World world, final CommandSender sender, final String[] args){
        WorldsConfig.getInstance().setWorldPunishment(world.getName(),this.name);
        sender.sendMessage(ANRMessages.PLUGIN_PREFIX_SHORT.getMessage() + ChatColor.GREEN
                + "Castigo cambiado con éxito! " + ChatColor.GRAY
                + "(Mundo: " + ChatColor.GOLD + world.getName() + ChatColor.GRAY + ") Nuevo castigo: " + this.name);
        return true;
    }

    /* Aplica el castigo */
    public abstract void punish(final Player player, final World world);

    /* Detiene el castigo (En caso de necesitarse) */
    public void stop() {
        this.name = null;
        this.description = null;
    }
    public abstract void init(World world);
}
