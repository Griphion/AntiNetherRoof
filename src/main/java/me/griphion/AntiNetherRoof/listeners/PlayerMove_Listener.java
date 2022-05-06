package me.griphion.AntiNetherRoof.listeners;

import me.griphion.AntiNetherRoof.ANRMessages;
import me.griphion.AntiNetherRoof.Core;
import me.griphion.AntiNetherRoof.punishments.PunishmentManager;
import me.griphion.AntiNetherRoof.utils.ConfigUtils;
import me.griphion.AntiNetherRoof.utils.WorldRepo;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PlayerMove_Listener implements Listener {

    private final Set<Player> antiSpamList = new HashSet<>(3);

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerMove(final PlayerMoveEvent event) {
        if (cantEnter(event)) {
            PunishmentManager.getInstance().punish(event.getPlayer(), Objects.requireNonNull(event.getTo()).getWorld());
            if (!antiSpamList.contains(event.getPlayer())) {
                event.getPlayer().sendMessage(ANRMessages.NO_PERMISSION_ENTER.getMessage());
                antiSpamList.add(event.getPlayer());
                Core.instance().getServer().getScheduler().runTaskLater(Core.instance(), () -> antiSpamList.remove(event.getPlayer()), 100L);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public static void onPlayerTeleport(PlayerTeleportEvent event) {
        if (cantEnter(event)) {
            event.getPlayer().sendMessage(ANRMessages.NO_PERMISSION_ENTER.getMessage());
            event.setCancelled(true);
        }
    }

    private static boolean cantEnter(PlayerMoveEvent event) {
        Location loc = event.getTo();
        if (null == loc) return false;

        World world = loc.getWorld();
        if (null == world) return false;

        String worldName = world.getName();

        return ConfigUtils.isWorldEnabled(worldName)
                && WorldRepo.isInNetherRoof(loc)
                && (!event.getPlayer().hasPermission("antinetherroof.bypass.punishment." + worldName)
                || !event.getPlayer().hasPermission("antinetherroof.bypass.punishment.*"));
    }


}
