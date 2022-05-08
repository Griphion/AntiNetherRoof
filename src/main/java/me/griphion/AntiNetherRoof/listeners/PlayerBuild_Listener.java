package me.griphion.AntiNetherRoof.listeners;

import me.griphion.AntiNetherRoof.ANRMessages;
import me.griphion.AntiNetherRoof.configs.WorldsConfig;
import me.griphion.AntiNetherRoof.repos.WorldRepo;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerBuild_Listener implements Listener {

  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
  public static void onBreakBlock(final BlockBreakEvent event) {
    checkBlockEvent(event, event.getPlayer(), event.getBlock(), ANRMessages.NO_PERMISSION_BREAK_BLOCK.getMessage());
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
  public static void onPlaceBlock(final BlockPlaceEvent event) {
    checkBlockEvent(event, event.getPlayer(), event.getBlock(), ANRMessages.NO_PERMISSION_PLACE_BLOCK.getMessage());
  }

  private static void checkBlockEvent(Cancellable event, Player player, Block block, String message){
    if (WorldsConfig.getInstance().isWorldEnabled(block.getWorld().getName())
        && WorldRepo.getInstance().isInNetherRoof(block.getLocation())
        && ( !player.hasPermission("antinetherroof.bypass.build." + block.getWorld().getName())
          || !player.hasPermission("antinetherroof.bypass.build.*") )) {

        player.sendMessage(message);
        event.setCancelled(true);
    }
  }
}
