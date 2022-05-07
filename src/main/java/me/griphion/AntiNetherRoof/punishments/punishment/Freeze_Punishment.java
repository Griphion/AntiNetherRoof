package me.griphion.AntiNetherRoof.punishments.punishment;

import me.griphion.AntiNetherRoof.Core;
import me.griphion.AntiNetherRoof.repos.WorldRepo;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class Freeze_Punishment extends Punishment {

  protected Set<Player> players = new HashSet<>(3);
  protected Iterator<Player> playersIterator;
  protected BukkitTask task;

  public Freeze_Punishment() {
    super("freeze", "Congela al jugador en el lugar.");
  }

  @Override
  public void punish(final Player player, final World world) {
    players.add(player);
    if(player.isFlying()) player.setFlying(false);
    if(player.isGliding()) player.setGliding(false);
  }

  @Override
  public void stop() {
    super.stop();
    task.cancel();
    for(Player player : players){
      removeEffects(player);
    }
    players.clear();
    players = null;
  }

  @Override
  public void init(World world) {
    startTaskTimer();
  }

  private final List<PotionEffect> effects = Arrays.asList(
      new PotionEffect(PotionEffectType.JUMP, 100, 250),
      new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, 127),
      new PotionEffect(PotionEffectType.BLINDNESS, 100,127),
      new PotionEffect(PotionEffectType.SLOW, 100,127));

  protected void applyEffects(Player player){
    player.addPotionEffects(effects);
    //Agregar efecto de congelado (Freeze)
    // Nota: Hasta el momento solo se puede lograr usando NMS
  }

  protected void removeEffects(Player player) {
    for(PotionEffect effect : effects) {
      player.removePotionEffect(effect.getType());
    }
  }

  private void startTaskTimer(){
    task = new BukkitRunnable(){
      @Override
      public void run() {
        playersIterator = players.iterator();
        while (playersIterator.hasNext()) {
          Player player = playersIterator.next();
          if(WorldRepo.isInNetherRoof(player.getLocation()) && !player.hasPermission("antinetherroof.bypass.punishment.freeze")){
            applyEffects(player);
          } else {
            removeEffects(player);
            playersIterator.remove();
          }
        }
      }
    }.runTaskTimer(Core.instance(),0,20L);
  }


}
