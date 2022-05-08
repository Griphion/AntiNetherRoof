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

public class PlayerUseItem_Listener implements Listener {

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
    switch (material) {
      case ENDER_PEARL:
      case ENDER_EYE:
      case MILK_BUCKET:
      case SPLASH_POTION:
      case LINGERING_POTION:
      case POTION:
      case SHIELD:
      case FIREWORK_ROCKET:
      case EXPERIENCE_BOTTLE:
      case LEAD:
      case BONE_MEAL:
        return true;
      default:
        return false;
    }
  }

  public static boolean isTool(Material material) {
    switch (material) {
      case FISHING_ROD:
      case WOODEN_HOE:
      case STONE_HOE:
      case IRON_HOE:
      case GOLDEN_HOE:
      case DIAMOND_HOE:
      case NETHERITE_HOE:
      case WOODEN_AXE:
      case STONE_AXE:
      case IRON_AXE:
      case GOLDEN_AXE:
      case DIAMOND_AXE:
      case NETHERITE_AXE:
      case WOODEN_SHOVEL:
      case STONE_SHOVEL:
      case IRON_SHOVEL:
      case GOLDEN_SHOVEL:
      case DIAMOND_SHOVEL:
      case NETHERITE_SHOVEL:
      case SHEARS:
        return true;
      default:
        return false;
    }
  }

  public static boolean isWeapon(Material material) {
    switch (material) {
      case WOODEN_SWORD:
      case STONE_SWORD:
      case IRON_SWORD:
      case GOLDEN_SWORD:
      case DIAMOND_SWORD:
      case NETHERITE_SWORD:
      case BOW:
      case CROSSBOW:
      case TRIDENT:
        return true;
      default:
        return false;
    }
  }
}
