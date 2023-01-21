package dev.timecoding.ggspawngsg;

import java.util.List;

import dev.timecoding.ggspawngsg.api.Metrics;
import dev.timecoding.ggspawngsg.api.PlotWrapper;
import dev.timecoding.ggspawngsg.api.UpdateChecker;
import dev.timecoding.ggspawngsg.api.reflections.wall.PS3E;
import dev.timecoding.ggspawngsg.api.reflections.wall.PS4E;
import dev.timecoding.ggspawngsg.api.reflections.wall.PS6E;
import dev.timecoding.ggspawngsg.command.SpawnPlotCommand;
import dev.timecoding.ggspawngsg.command.completer.SpawnPlotTabber;
import dev.timecoding.ggspawngsg.file.FileManager;
import dev.timecoding.ggspawngsg.listener.RedeemListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GGSpawnGSG extends JavaPlugin {
  private int pluginid = 7836;
  
  public UpdateChecker updateChecker = null;
  public UpdateChecker uf = null;

  private ConsoleCommandSender commandsender;

  private PlotWrapper plotWrapper = new PlotWrapper();
  private int resourceId = 80177;
  
  public void onEnable() {

    this.commandsender = Bukkit.getConsoleSender();
    this.updateChecker = new UpdateChecker(this, resourceId);
    boolean disable = false;

    PluginManager pluginManager = this.getServer().getPluginManager();
    if (pluginManager.getPlugin("PlotSquared") == null) {
      this.commandsender.sendMessage("");
      this.commandsender.sendMessage("");
      this.commandsender.sendMessage("§cEs wurde keine ladbare Version von PlotSquared auf diesem Server gefunden! Bitte installiere zuerst PlotSquared um dieses Addon nutzen zu können!");
      this.commandsender.sendMessage("§fDownload: §ehttps://intellectualsites.github.io/download/plots.html");
      this.commandsender.sendMessage("");
      this.commandsender.sendMessage("");
      disable = true;
    }else if(!plotWrapper.canInit()){
      this.commandsender.sendMessage("");
      this.commandsender.sendMessage("");
      this.commandsender.sendMessage("§cEs ist ein Fehler beim suchen der PlotSquared Version aufgetreten! Das Plugin wird nun deaktiviert...");
      this.commandsender.sendMessage("");
      this.commandsender.sendMessage("");
      disable = true;
    }
    if(!disable) {
      this.commandsender.sendMessage("§fLade PlotSquared Daten... (PlotSquared "+plotWrapper.getPlotSquaredVersion().toString().toLowerCase()+")");
      this.commandsender.sendMessage("§e--------------------------------------");
      this.commandsender.sendMessage("§eGrieferGames §aSpawnGS Gutscheine / SpawnPlot Voucher");
      this.commandsender.sendMessage("§bVersion: §f" + getDescription().getVersion());
      this.commandsender.sendMessage("§bDeveloper: §fTimeCode");
      this.commandsender.sendMessage("§e--------------------------------------");

      PluginCommand spawnplot = this.getCommand("spawnplot");
      spawnplot.setExecutor(new SpawnPlotCommand(this));
      spawnplot.setTabCompleter(new SpawnPlotTabber());

      pluginManager.registerEvents(new RedeemListener(this), this);
      switch(plotWrapper.getPlotSquaredVersion()){
        case V3:
          pluginManager.registerEvents(new PS3E(this), this);
          this.commandsender.sendMessage("§7Registered PlotSquared v3 Listener!");
          break;
        case V4:
          pluginManager.registerEvents(new PS4E(this), this);
          this.commandsender.sendMessage("§7Registered PlotSquared v4 Listener!");
          break;
        case V6:
          pluginManager.registerEvents(new PS6E(this), this);
          this.commandsender.sendMessage("§7Registered PlotSquared v6 Listener!");
          break;
      }

      FileManager.setup(this);

      if (FileManager.cfg.getBoolean("bStats")) {
        Metrics metrics = new Metrics(this, pluginid);
      }
      try {
        if (this.updateChecker.checkForUpdates()) {
          this.commandsender.sendMessage("");
          this.commandsender.sendMessage("");
          this.commandsender.sendMessage("§cEin neues Update wurde gefunden / A new update was found:");
          this.commandsender.sendMessage("§aVersion: §f" + this.updateChecker.getLatestVersion());
          this.commandsender.sendMessage("§aDownload: §f" + this.updateChecker.getResourceURL());
          this.commandsender.sendMessage("");
          this.commandsender.sendMessage("");
        }
      } catch (Exception e) {
        this.commandsender.sendMessage("Can't find this SpigotMC resource to update this plugin! Is the Website down?");
        this.commandsender.sendMessage("Es konnte nicht zu SpigotMC Verbindung hergestellt werden! Ist die Website offline?");
      }
    }else{
      this.getServer().getPluginManager().disablePlugin(this);
    }
  }
  
  public ItemStack getVoucher() {
    List<String> lore = FileManager.item.getStringList("Lore");
    ItemStack is = new ItemStack(Material.getMaterial(FileManager.item.getString("Material")), 1, (short)FileManager.item.getInt("SubID"));
    ItemMeta im = is.getItemMeta();
    im.setDisplayName(FileManager.item.getString("Displayname").replace("&", "§"));
    im.setLore(lore);
    if (FileManager.item.getBoolean("Enchantment.enabled"))
      im.addEnchant(Enchantment.getByName(FileManager.item.getString("Enchantment.enchantname")), FileManager.item.getInt("Enchantment.enchantlevel"), true); 
    is.setItemMeta(im);
    return is;
  }
  
  public void giveVoucher(Player p) {
    p.getInventory().addItem(new ItemStack[] { getVoucher() });
  }

  public PlotWrapper getPlotWrapper() {
    return plotWrapper;
  }
}
