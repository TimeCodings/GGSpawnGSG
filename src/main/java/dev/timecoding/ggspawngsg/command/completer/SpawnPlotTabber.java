package dev.timecoding.ggspawngsg.command.completer;

import java.util.ArrayList;
import java.util.List;

import dev.timecoding.ggspawngsg.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.*;

public class SpawnPlotTabber implements TabCompleter {
  public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
    List<String> c = new ArrayList<>();
    if (sender.hasPermission(FileManager.getPermission("AutoCompleter"))) {
      if(args.length == 1) {
        c.add("add");
        c.add("remove");
        c.add("give");
        c.add("givevoucher");
        c.add("reload");
        c.add("rl");
        c.add("help");
      }if(args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("givevoucher")){
        if(args.length == 2){
           for(Player all : Bukkit.getOnlinePlayers()){
             c.add(all.getName());
           }
        }else if(args.length == 3 && args[0].equalsIgnoreCase("givevoucher")){
             c.add("1");
             c.add("2");
             c.add("4");
             c.add("5");
             c.add("10");
             c.add("15");
             c.add("20");
        }
      }
    } 
    return c;
  }
}
