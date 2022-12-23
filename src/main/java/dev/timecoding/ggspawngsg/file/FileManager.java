package dev.timecoding.ggspawngsg.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import dev.timecoding.ggspawngsg.GGSpawnGSG;
import dev.timecoding.ggspawngsg.api.PlotWrapper;
import dev.timecoding.ggspawngsg.enums.PSVersion;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class FileManager {


  public static File f1 = new File("plugins//GGSpawnGSG", "config.yml");
  
  public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f1);
  
  public static File f2 = new File("plugins//GGSpawnGSG", "plots.yml");
  
  public static YamlConfiguration plots = YamlConfiguration.loadConfiguration(f2);
  
  public static File f3 = new File("plugins//GGSpawnGSG", "item.yml");
  
  public static YamlConfiguration item = YamlConfiguration.loadConfiguration(f3);

  private static GGSpawnGSG instance;
  
  public static void setup(GGSpawnGSG plugininstance) {
    instance = plugininstance;
    cfg.options().copyDefaults(true);
    plots.options().copyDefaults(true);
    item.options().copyDefaults(true);
    item.options().copyHeader(true);
    item.options().header("Ändere hier die Eigenschaften des SpawnPlot Gutscheines! | Change here the properties for the SpawnPlot Voucher Items!");
    cfg.options().header("Stelle hier Commands, Permissions und sonstiges für das Plugin ein! | Change here Commands, Permissions and other things for this plugin!");
    plots.options().header("In dieser Datei sind die SpawnPlots abgespeichert! Bitte nicht ändern wenn du dich nicht auskennst! | In this File are all Spawnplots saved! Please do not change this text!");
    plots.options().copyHeader(true);
    cfg.options().copyHeader(true);
    cfg.addDefault("bStats", Boolean.valueOf(true));
    cfg.addDefault("Prefix", "&f[&bServer&f]");
    cfg.addDefault("TwoClickRedeem", Boolean.valueOf(true));
    cfg.addDefault("SetOwnerOnAddPlot", Boolean.valueOf(true));
    addPermission("Admin", "spawnplot.admin");
    addPermission("Help", "spawnplot.openhelp");
    addPermission("Add", "spawnplot.add");
    addPermission("Remove", "spawnplot.remove");
    addPermission("AutoCompleter", "spawnplot.autocompleter");
    addPermission("GiveVoucher", "spawnplot.addvoucher");
    addPermission("Reload", "spawnplot.reload");
    addPermission("GivePlot", "spawnplot.giveplot");
    ArrayList<String> help = new ArrayList<>();
    help.add("&e------------------------------------------------------");
    help.add("&b/spawnplot add &f| §eDas Plot worauf du stehst wird nun zum SpawnPlot");
    help.add("&b/spawnplot remove &f| &eDas Plot worauf du stehst ist nun kein SpawnPlot mehr");
    help.add("&b/spawnplot give <Spieler> &f| &eGebe einen Spieler das aktuelle SpawnPlot");
    help.add("&b/spawnplot givevoucher <Spieler> &f| &eGebe einen Spieler einen SpawnGS Gutschein!");
    help.add("&b/spawnplot help &f| &eÖffne die Hilfeseite");
    help.add("&b/spawnplot rl &f| &eReloade alle Einstellungen");
    help.add("&e------------------------------------------------------");
    ArrayList<String> one = new ArrayList<>();
    one.add("&aKlicke noch einmal zum einlösen!");
    one.add("");
    one.add("&f&lÜberlege es dir gut ;)");
    ArrayList<String> two = new ArrayList<>();
    two.add("&a§lDu besitzt nun ein Spawnplot!");
    two.add("&7(ID: {id})");
    two.add("&f&lViel Spaß damit ;D");
    cfg.addDefault("TwoClickMessage", one);
    cfg.addDefault("YourPlot", two);
    cfg.addDefault("Helpsite", help);
    addMessage("PlotAdded", "&aDas Plot &b{id} &aist nun ein SpawnPlot!");
    addMessage("PlotRemoved", "&aDas Plot &b{id} &aist nun kein SpawnPlot mehr!");
    addMessage("NotOnPlot", "&cDu stehst auf keinem Plot!");
    addMessage("NoPerm", "&4Dazu hast du leider keine Berechtigung!");
    addMessage("PlotIsSpawnPlot", "§4Dieses Plot ist bereits ein SpawnPlot!");
    addMessage("PlotNotSpawnPlot", "&4Dieses Plot ist kein SpawnPlot oder ist bereits belegt!");
    addMessage("Reload", "&aEs wurden soeben alle Einstellungen neu geladen!");
    addMessage("PlayerNotOnline", "&cDieser Spieler ist aktuell leider nicht online!");
    addMessage("SyntaxError", "&cFehler bei der Eingabe! &aAlle Commands bei /spawnplot help");
    addMessage("BecomeSpawnPlot", "&b{player} &ahat dir ein Spawn-Plot gegeben! &7(ID: {id})");
    addMessage("GiveSpawnPlot", "&aDu hast &b{player} ein Spawn-Plot gegeben! &7(ID: {id})");
    addMessage("GetSpawnPlotVoucher", "&aDu hast einen SpawnGS Gutschein von &e{player} &aerhalten!");
    addMessage("GiveSpawnPlotVoucher", "&aDu hast einen SpawnGS Gutschein &e{player} &agegeben!");
    addMessage("Console", "&fDies darf nur ein Spieler!");
    ArrayList<String> list = new ArrayList<>();
    list.add("");
    plots.addDefault("Plots", list);
    ArrayList<String> lore = new ArrayList<>();
    lore.add("§aClick to Redeem");
    lore.add("§aKlicke zum einlösen!");
    lore.add("§cChange this in the item.yml");
    lore.add("§cÄndere dies in der item.yml");
    PlotWrapper plotWrapper = plugininstance.getPlotWrapper();
    if(plotWrapper.getPlotSquaredVersion().equals(PSVersion.V3)){
      item.addDefault("Material", "SAPLING");
      item.addDefault("SubID", Integer.valueOf(5));
    }else {
      item.addDefault("Material", "JUNGLE_SAPLING");
      item.addDefault("SubID", Integer.valueOf(3));
    }
    item.addDefault("Displayname", "&a&lSpawnGS Gutschein &f&l| &c&lSpawnPlot Voucher");
    item.addDefault("Lore", lore);
    item.addDefault("Enchantment.enabled", Boolean.valueOf(true));
    item.addDefault("Enchantment.enchantname", "LUCK");
    item.addDefault("Enchantment.enchantlevel", Integer.valueOf(100));
    saveConfig();
    savePlots();
    saveItem();
  }
  
  public static String getPrefix() {
    return String.valueOf(cfg.getString("Prefix").replace("&", "§")) + " ";
  }
  
  public static void savePlots() {
    try {
      plots.save(f2);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static void loadAll() {
    try {
      plots.load(f2);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InvalidConfigurationException e) {
      e.printStackTrace();
    } 
    try {
      cfg.load(f1);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InvalidConfigurationException e) {
      e.printStackTrace();
    } 
    try {
      item.load(f3);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InvalidConfigurationException e) {
      e.printStackTrace();
    } 
  }
  
  public static void saveConfig() {
    try {
      cfg.save(f1);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static void saveItem() {
    try {
      item.save(f3);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static String getPermission(String s) {
    return cfg.getString("Permissions." + s);
  }
  
  public static void addPermission(String s, String s2) {
    cfg.addDefault("Permissions." + s, s2);
    saveConfig();
  }
  
  public static void addMessage(String s, String s2) {
    cfg.addDefault("Messages." + s, s2);
    saveConfig();
  }
  
  public static String getMessage(String s) {
    return cfg.getString("Messages." + s);
  }
}
