package dev.timecoding.ggspawngsg.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerGiveSpawnPlotEvent extends Event implements Cancellable {
  private static final HandlerList handlers = new HandlerList();
  
  private Player p;
  
  private Player t;
  
  private String plotid;
  
  private Object plot;
  
  private boolean cancelled;
  
  public PlayerGiveSpawnPlotEvent(Player p, Player t, String plotid, Object plot) {
    this.cancelled = false;
    this.p = p;
    this.t = t;
    this.plotid = plotid;
    this.plot = plot;
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
  
  public String getPlotID() {
    return this.plotid;
  }
  
  public Object getPlot() {
    return this.plot;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
