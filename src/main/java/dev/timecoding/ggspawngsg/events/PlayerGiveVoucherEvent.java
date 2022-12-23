package dev.timecoding.ggspawngsg.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerGiveVoucherEvent extends Event implements Cancellable {
  private static final HandlerList handlers = new HandlerList();
  
  private Player p;
  
  private Player t;
  
  private boolean cancelled;
  
  public PlayerGiveVoucherEvent(Player p, Player t) {
    this.cancelled = false;
    this.p = p;
    this.t = t;
  }
  
  public boolean isCancelled() {
    return this.cancelled;
  }
  
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }
  
  public HandlerList getHandlers() {
    return handlers;
  }
  
  public Player getPlayer() {
    return this.p;
  }
  
  public Player getTarget() {
    return this.t;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
