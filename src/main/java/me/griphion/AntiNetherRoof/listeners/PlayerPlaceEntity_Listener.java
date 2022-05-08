package me.griphion.AntiNetherRoof.listeners;

import me.griphion.AntiNetherRoof.ANRMessages;
import me.griphion.AntiNetherRoof.configs.WorldsConfig;
import me.griphion.AntiNetherRoof.repos.WorldRepo;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerPlaceEntity_Listener implements Listener {

  @EventHandler(ignoreCancelled = true)
  public static void onPlaceEntity(final PlayerInteractEvent event) {
    if (event.getClickedBlock() == null) return;
    if (WorldsConfig.getInstance().isWorldEnabled(event.getClickedBlock().getWorld().getName())
        && ( !event.getPlayer().hasPermission("antinetherroof.bypass.placeentity." + event.getClickedBlock().getWorld().getName())
        || !event.getPlayer().hasPermission("antinetherroof.bypass.placeentity.*") )) {
      final ItemStack item;

      if(event.getHand() == EquipmentSlot.HAND){
        item = event.getPlayer().getInventory().getItemInMainHand();
      }else {
        item = event.getPlayer().getInventory().getItemInOffHand();
      }

      if (WorldRepo.getInstance().isInNetherRoof(event.getClickedBlock().getRelative(event.getBlockFace()).getLocation())
          && (event.getAction() == Action.RIGHT_CLICK_BLOCK))

        if (isOther(item.getType())
            || isBucket(item.getType())
            || isBoat(item.getType())
            || isSpawnEgg(item.getType())
            || isMinecart(item.getType()))
        {
          event.getPlayer().sendMessage(ANRMessages.NO_PERMISSION_PLACE_ENTITY.getMessage());
          event.setCancelled(true);
        }
    }
  }

  private static boolean isOther(Material material) {
    switch (material) {
      case LAVA:
      case WATER:
      case END_CRYSTAL:
      case ARMOR_STAND:
        return true;
      default:
        return false;
    }
  }

  private static boolean isSpawnEgg(Material material) {
    return material.toString().endsWith("SPAWN_EGG");
  }

  private static boolean isBoat(Material material) {
    return Tag.ITEMS_BOATS.isTagged(material);
  }

  private static boolean isMinecart(Material material) {
    return material.toString().endsWith("MINECART");
  }

  private static boolean isBucket(Material material) { // No invoca una entidad -> (MILK_BUCKET)
    return material.toString().endsWith("BUCKET") && !material.toString().equalsIgnoreCase("MILK_BUCKET");
  }
}
