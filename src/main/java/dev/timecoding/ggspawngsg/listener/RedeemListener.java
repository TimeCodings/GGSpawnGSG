package dev.timecoding.ggspawngsg.listener;

import java.util.List;

import dev.timecoding.ggspawngsg.GGSpawnGSG;
import dev.timecoding.ggspawngsg.api.PlotWrapper;
import dev.timecoding.ggspawngsg.api.UpdateChecker;
import dev.timecoding.ggspawngsg.api.reflections.PSController;
import dev.timecoding.ggspawngsg.events.PlayerRedeemSpawnPlotEvent;
import dev.timecoding.ggspawngsg.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class RedeemListener implements Listener {
  private GGSpawnGSG pl;
  private PlotWrapper plotWrapper;
  
  public RedeemListener(GGSpawnGSG pl) {
    this.pl = pl;
    this.plotWrapper = pl.getPlotWrapper();
  }
  
  @EventHandler
  public void onRedeem(PlayerInteractEvent e) {
    final Player p = e.getPlayer();
    PSController controller = pl.getPlotWrapper().getController(p);
    if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
      MaterialData md = new MaterialData(Material.getMaterial(FileManager.item.getString("Material")), (byte)FileManager.item.getInt("SubID"));
      if (e.getPlayer().getItemInHand().getType() == Material.getMaterial(FileManager.item.getString("Material")) && e.getPlayer().getItemInHand().getData().getData() == (byte)FileManager.item.getInt("SubID") && e.getPlayer().getItemInHand().getItemMeta().hasDisplayName() && e.getPlayer().getItemInHand().getItemMeta().hasLore()) {
        p.setMetadata("RedeemItem", (MetadataValue)new FixedMetadataValue((Plugin)this.pl, Boolean.valueOf(true)));
        if (FileManager.cfg.getBoolean("TwoClickRedeem")) {
          if (p.hasMetadata("Redeem")) {
            if (controller.playerOnPlot(p)) {
              List<String> list = FileManager.plots.getStringList("Plots");
              if (list.contains(controller.getPlotID(p))) {
                PlayerRedeemSpawnPlotEvent prpe = new PlayerRedeemSpawnPlotEvent(p, String.valueOf(controller.getPlotID(p)), controller.getPlotObject(p));
                Bukkit.getPluginManager().callEvent(prpe);
                if (!prpe.isCancelled()) {
                  for (String lines : FileManager.cfg.getStringList("YourPlot")) {
                    p.sendMessage(lines.replace("&", "§").replace("{id}", String.valueOf(controller.getPlotID(p))));
                  }
                  list.remove(controller.getPlotID(p));
                  FileManager.plots.set("Plots", list);
                  FileManager.savePlots();
                  controller.setPlotOwner(p, String.valueOf(p.getUniqueId()));
                  int amount = e.getPlayer().getItemInHand().getAmount();
                  if(amount == 1) {
                    p.getInventory().setItemInHand(new ItemStack(Material.AIR));
                    p.getInventory().getItemInHand().setAmount(0);
                  }else if(amount >= 2){
                    p.getInventory().getItemInHand().setAmount((amount-1));
                  }
                  Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.pl, new Runnable() {
                        public void run() {
                          p.removeMetadata("Redeem", (Plugin) RedeemListener.this.pl);
                        }
                      },10L);
                } 
              } else {
                p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("PlotNotSpawnPlot").replace("&", "§"));
              } 
            } else {
              p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("NotOnPlot").replace("&", "§"));
            } 
          } else if (controller.playerOnPlot(p)) {
            List<String> list = FileManager.plots.getStringList("Plots");
            if (list.contains(controller.getPlotID(p))) {
              for (String lines : FileManager.cfg.getStringList("TwoClickMessage")) {
                p.sendMessage(lines.replace("&", "§").replace("{id}", String.valueOf(controller.getPlotID(p))));
              }
              p.setMetadata("Redeem", new FixedMetadataValue(pl, true));
            } else {
              p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("PlotNotSpawnPlot").replace("&", "§"));
            } 
          } else {
            p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("NotOnPlot").replace("&", "§"));
          } 
        } else if (controller.playerOnPlot(p)) {
          List<String> list = FileManager.plots.getStringList("Plots");
          if (list.contains(controller.getPlotID(p))) {
            PlayerRedeemSpawnPlotEvent prpe = new PlayerRedeemSpawnPlotEvent(p, String.valueOf(controller.getPlotID(p)), controller.getPlotObject(p));
            Bukkit.getPluginManager().callEvent(prpe);
            if (!prpe.isCancelled()) {
              for (String lines : FileManager.cfg.getStringList("YourPlot")) {
                p.sendMessage(lines.replace("&", "§").replace("{id}", String.valueOf(controller.getPlotID(p))));
              }
              list.remove(controller.getPlotID(p));
              FileManager.plots.set("Plots", list);
              FileManager.savePlots();
              int amount = e.getPlayer().getItemInHand().getAmount();
              if(amount == 1) {
                p.getInventory().setItemInHand(new ItemStack(Material.AIR));
                p.getInventory().getItemInHand().setAmount(0);
              }else if(amount >= 2){
                p.getInventory().getItemInHand().setAmount((amount-1));
              }
              controller.setPlotOwner(p, String.valueOf(p.getUniqueId()));
              Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.pl, new Runnable() {
                    public void run() {
                      p.removeMetadata("Redeem", (Plugin) RedeemListener.this.pl);
                    }
                  },10L);
            } 
          } else {
            p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("PlotNotSpawnPlot").replace("&", "§"));
          } 
        } else {
          p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("NotOnPlot").replace("&", "§"));
        } 
      } 
    } 
  }
  
  @EventHandler
  public void onMove(PlayerMoveEvent e) throws ClassNotFoundException {
    Player p = e.getPlayer();
    PSController controller = pl.getPlotWrapper().getController(p);
    if (!controller.playerOnPlot(p) &&
      p.hasMetadata("Redeem"))
      p.removeMetadata("Redeem", (Plugin)this.pl);
  }
  
  @EventHandler
  public void onPlace(BlockPlaceEvent e) {
    Player p = e.getPlayer();
    ItemStack is = new ItemStack(Material.getMaterial(FileManager.item.getString("Material")), 1, (short)FileManager.item.getInt("SubID"));
    ItemMeta im = is.getItemMeta();
    im.setLore(FileManager.item.getStringList("Lore"));
    im.setDisplayName(FileManager.item.getString("Displayname").replace("&", "§"));
    if (FileManager.item.getBoolean("Enchantment.enabled"))
      im.addEnchant(Enchantment.getByName(FileManager.item.getString("Enchantment.enchantname")), FileManager.item.getInt("Enchantment.enchantlevel"), true); 
    is.setItemMeta(im);
    if (p.hasMetadata("RedeemItem")) {
      p.removeMetadata("RedeemItem", (Plugin)this.pl);
      e.setCancelled(true);
    } 
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    if (e.getPlayer().hasPermission(FileManager.getPermission("Admin"))) {
      pl.updateChecker = new UpdateChecker(this.pl, 80177);
      try {
        if (pl.updateChecker.checkForUpdates()) {
          e.getPlayer().sendMessage("");
          e.getPlayer().sendMessage("");
          e.getPlayer().sendMessage("§cEin neues Update wurde gefunden / A new update was found:");
          e.getPlayer().sendMessage("§aVersion: §f" + pl.updateChecker.getLatestVersion());
          e.getPlayer().sendMessage("§aDownload: §f" + pl.updateChecker.getResourceURL());
          e.getPlayer().sendMessage("");
          e.getPlayer().sendMessage("");
          pl.uf = pl.updateChecker;
        } 
      } catch (Exception ex) {
        Bukkit.getConsoleSender().sendMessage("Can't find this spigotmc resource to update this plugin! Is the Website down?");
        Bukkit.getConsoleSender().sendMessage("Es konnte nicht zu spigotmc Verbindung hergestellt werden! Ist die Website offline?");
      } 
    } 
    if (e.getPlayer().hasMetadata("Redeem"))
      e.getPlayer().removeMetadata("Redeem", (Plugin)this.pl); 
    if (e.getPlayer().hasMetadata("RedeemItem"))
      e.getPlayer().removeMetadata("RedeemItem", (Plugin)this.pl); 
  }

}
