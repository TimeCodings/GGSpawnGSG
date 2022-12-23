package dev.timecoding.ggspawngsg.command;

import java.util.List;

import dev.timecoding.ggspawngsg.api.reflections.PSController;
import dev.timecoding.ggspawngsg.file.FileManager;
import dev.timecoding.ggspawngsg.GGSpawnGSG;
import dev.timecoding.ggspawngsg.events.PlayerAddSpawnPlotEvent;
import dev.timecoding.ggspawngsg.events.PlayerGiveSpawnPlotEvent;
import dev.timecoding.ggspawngsg.events.PlayerGiveVoucherEvent;
import dev.timecoding.ggspawngsg.events.PlayerRemoveSpawnPlotEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpawnPlotCommand implements CommandExecutor {
  private GGSpawnGSG pl;
  
  public SpawnPlotCommand(GGSpawnGSG pl) {
    this.pl = pl;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof Player) {
      Player p = (Player)sender;
      PSController controller = pl.getPlotWrapper().getController(p);
      if (args.length == 1) {
        if (args[0].equalsIgnoreCase("add")) {
          if (p.hasPermission(FileManager.getPermission("Add")) || p.hasPermission(FileManager.getPermission("Admin"))) {
            if (controller.playerOnPlot(p)) {
              List<String> list = FileManager.plots.getStringList("Plots");
              if (list.contains(controller.getPlotID(p))) {
                p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("PlotIsSpawnPlot").replace("&", "§"));
              } else {
                if (FileManager.cfg.getBoolean("SetOwnerOnAddPlot"))
                  controller.setPlotOwner(p, p.getUniqueId().toString());
                PlayerAddSpawnPlotEvent paspe = new PlayerAddSpawnPlotEvent(p, String.valueOf(controller.getPlotID(p)), controller.getPlotObject(p));
                Bukkit.getPluginManager().callEvent(paspe);
                if (!paspe.isCancelled()) {
                  list.add(String.valueOf(controller.getPlotID(p)));
                  FileManager.plots.set("Plots", list);
                  FileManager.savePlots();
                  p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("PlotAdded").replace("&", "§").replace("{id}", controller.getPlotID(p)));
                } 
              } 
            } else {
              p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("NotOnPlot").replace("&", "§"));
            } 
          } else {
            p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("NoPerm").replace("&", "§"));
          } 
        } else if (args[0].equalsIgnoreCase("remove")) {
          if (p.hasPermission(FileManager.getPermission("Remove")) || p.hasPermission(FileManager.getPermission("Admin"))) {
            if (controller.playerOnPlot(p)) {
              List<String> list = FileManager.plots.getStringList("Plots");
              if (list.contains(controller.getPlotID(p))) {
                PlayerRemoveSpawnPlotEvent prspe = new PlayerRemoveSpawnPlotEvent(p, String.valueOf(controller.getPlotID(p)), controller.getPlotObject(p));
                Bukkit.getPluginManager().callEvent(prspe);
                if (!prspe.isCancelled()) {
                  list.remove(controller.getPlotID(p));
                  FileManager.plots.set("Plots", list);
                  FileManager.savePlots();
                  p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("PlotRemoved").replace("&", "§").replace("{id}", controller.getPlotID(p)));
                } 
              } else {
                p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("PlotNotSpawnPlot").replace("&", "§"));
              } 
            } else {
              p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("NotOnPlot").replace("&", "§"));
            } 
          } else {
            p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("NoPerm").replace("&", "§"));
          } 
        } else if (args[0].equalsIgnoreCase("rl") || args[0].equalsIgnoreCase("reload")) {
          if (p.hasPermission(FileManager.getPermission("Reload")) || p.hasPermission(FileManager.getPermission("Admin"))) {
            p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("Reload").replace("&", "§"));
            this.pl.reloadConfig();
            this.pl.saveDefaultConfig();
            FileManager.loadAll();
          } else {
            p.sendMessage(FileManager.getMessage("NoPerm").replace("&", "§"));
          } 
        } else if (args[0].equalsIgnoreCase("give")) {
          if (p.hasPermission(FileManager.getPermission("GivePlot")) || p.hasPermission(FileManager.getPermission("Admin"))) {
            p.sendMessage(String.valueOf(FileManager.getPrefix()) + "§e/spawnplot give <Player>");
          } else {
            p.sendMessage(FileManager.getMessage("NoPerm").replace("&", "§"));
          } 
        } else if (args[0].equalsIgnoreCase("givevoucher")) {
          if (p.hasPermission(FileManager.getPermission("GiveVoucher")) || p.hasPermission(FileManager.getPermission("Admin"))) {
            p.sendMessage(String.valueOf(FileManager.getPrefix()) + "§e/spawnplot givevoucher <Player> <Anzahl/Amount>");
          } else {
            p.sendMessage(FileManager.getMessage("NoPerm").replace("&", "§"));
          } 
        } else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("hilfe")) {
          if (p.hasPermission(FileManager.getPermission("Help"))) {
            List<String> help = FileManager.cfg.getStringList("Helpsite");
            for (String lines : help)
              p.sendMessage(lines.replace("&", "§")); 
          } else {
            p.sendMessage("§bSpawnPlot Voucher v" + this.pl.getDescription().getVersion() + " §aPlugin by TimeCode");
          } 
        } else {
          p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("SyntaxError").replace("&", "§"));
        } 
      } else if (args.length != 0 && args.length != 1 && args.length == 2 || args.length == 3) {
        if (args[0].equalsIgnoreCase("givevoucher")) {
          if (p.hasPermission(FileManager.getPermission("GiveVoucher")) || p.hasPermission(FileManager.getPermission("Admin"))) {
            Player t = Bukkit.getPlayer(args[1]);
            PlayerGiveVoucherEvent pgve = new PlayerGiveVoucherEvent(p, t);
            Bukkit.getPluginManager().callEvent(pgve);
            if (t != null) {
              if(args.length != 3) {
                pl.giveVoucher(t);
              }else{
                ItemStack voucher = pl.getVoucher();
                voucher.setAmount(Integer.valueOf(args[2]));
                p.getInventory().addItem(voucher);
              }
              if (t.getName() == p.getName()) {
                t.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("GiveSpawnPlotVoucher").replace("&", "§").replace("{player}", p.getName()));
              } else {
                p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("GetSpawnPlotVoucher").replace("&", "§").replace("{player}", t.getName()));
                t.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("GiveSpawnPlotVoucher").replace("&", "§").replace("{player}", p.getName()));
              } 
            } else {
              p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("PlayerNotOnline").replace("&", "§"));
            } 
          } else {
            p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("NoPerm").replace("&", "§"));
          } 
        } else if (args[0].equalsIgnoreCase("give")) {
          if (p.hasPermission(FileManager.getPermission("GivePlot")) || p.hasPermission(FileManager.getPermission("Admin"))) {
            if (controller.playerOnPlot(p)) {
              List<String> list = FileManager.plots.getStringList("Plots");
              if (list.contains(controller.getPlotID(p))) {
                PlayerGiveSpawnPlotEvent pgspe = new PlayerGiveSpawnPlotEvent(p, Bukkit.getPlayer(args[1]), String.valueOf(controller.getPlotID(p)), controller.getPlotObject(p));
                Bukkit.getPluginManager().callEvent(pgspe);
                if (!pgspe.isCancelled()) {
                  Player t = Bukkit.getPlayer(args[1]);
                  list.remove(controller.getPlotID(p));
                  FileManager.plots.set("Plots", list);
                  FileManager.savePlots();
                  controller.setPlotOwner(t, String.valueOf(t.getUniqueId()));
                  if (t.getName() == p.getName()) {
                    t.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("BecomeSpawnPlot").replace("{player}", p.getName()).replace("&", "§").replace("{id}", controller.getPlotID(p)));
                  } else {
                    t.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("BecomeSpawnPlot").replace("{player}", p.getName()).replace("&", "§").replace("{id}", controller.getPlotID(p)));
                    p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("GiveSpawnPlot").replace("{player}", t.getName()).replace("&", "§").replace("{id}", controller.getPlotID(p)));
                  } 
                } 
              } else {
                p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("PlotNotSpawnPlot").replace("&", "§"));
              } 
            } else {
              p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("NotOnPlot").replace("&", "§"));
            } 
          } else {
            p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("NoPerm").replace("&", "§"));
          } 
        } else {
          p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("SyntaxError").replace("&", "§"));
        } 
      } else {
        p.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("SyntaxError").replace("&", "§"));
      } 
    } else {
      sender.sendMessage(String.valueOf(FileManager.getPrefix()) + FileManager.getMessage("Console").replace("&", "§"));
    } 
    return false;
  }
}
