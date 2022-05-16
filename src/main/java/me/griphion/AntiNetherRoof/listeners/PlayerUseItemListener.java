package me.griphion.AntiNetherRoof.listeners;

import me.griphion.AntiNetherRoof.ANRMessages;
import me.griphion.AntiNetherRoof.configs.WorldsConfig;
import me.griphion.AntiNetherRoof.repos.WorldRepo;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerUseItemListener implements Listener {

  @EventHandler(ignoreCancelled = true)
  public static void onItemUse(final PlayerInteractEvent event) {
    if (WorldsConfig.getInstance().isWorldEnabled(event.getPlayer().getWorld().getName())
        && ( !event.getPlayer().hasPermission("antinetherroof.bypass.useitem." + event.getPlayer().getWorld().getName())
        || !event.getPlayer().hasPermission("antinetherroof.bypass.useitem.*") )) {

      final ItemStack item;

      if(event.getHand() == EquipmentSlot.HAND){
        item = event.getPlayer().getInventory().getItemInMainHand();
      }else {
        item = event.getPlayer().getInventory().getItemInOffHand();
      }

      if (
          (event.getAction() == Action.RIGHT_CLICK_AIR
              && WorldRepo.getInstance().isInNetherRoof(event.getPlayer().getLocation()) )
          || (event.getClickedBlock() != null
              && WorldRepo.getInstance().isInNetherRoof(event.getClickedBlock().getRelative(event.getBlockFace()).getLocation())
              && event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
        if (isOther(item.getType())
            || isTool(item.getType())
            || isWeapon(item.getType()))
        {
          event.getPlayer().sendMessage(ANRMessages.NO_PERMISSION_USE.getMessage());
          event.setCancelled(true);
        }
      }


    }
  }

  public static boolean isOther(Material material) {
    return switch (material) {
      case ENDER_PEARL,
              ENDER_EYE,
              MILK_BUCKET,
              SPLASH_POTION,
              LINGERING_POTION,
              POTION,
              SHIELD,
              FIREWORK_ROCKET,
              EXPERIENCE_BOTTLE,
              LEAD,
              BONE_MEAL
              -> true;
      default -> false;
    };
  }

  public static boolean isTool(Material material) {
    return switch (material) {
      case FISHING_ROD,
              WOODEN_HOE,
              STONE_HOE,
              IRON_HOE,
              GOLDEN_HOE,
              DIAMOND_HOE,
              NETHERITE_HOE,
              WOODEN_AXE,
              STONE_AXE,
              IRON_AXE,
              GOLDEN_AXE,
              DIAMOND_AXE,
              NETHERITE_AXE,
              WOODEN_SHOVEL,
              STONE_SHOVEL,
              IRON_SHOVEL,
              GOLDEN_SHOVEL,
              DIAMOND_SHOVEL,
              NETHERITE_SHOVEL,
              SHEARS
              -> true;
      default -> false;
    };
  }

  public static boolean isWeapon(Material material) {
    return switch (material) {
      case WOODEN_SWORD,
              STONE_SWORD,
              IRON_SWORD,
              GOLDEN_SWORD,
              DIAMOND_SWORD,
              NETHERITE_SWORD,
              BOW,
              CROSSBOW,
              TRIDENT
              ->true;
      default -> false;
    };
  }
}
