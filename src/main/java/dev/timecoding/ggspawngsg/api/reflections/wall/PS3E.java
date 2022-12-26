package dev.timecoding.ggspawngsg.api.reflections.wall;

import com.intellectualcrafters.plot.object.Plot;
import dev.timecoding.ggspawngsg.GGSpawnGSG;
import dev.timecoding.ggspawngsg.api.reflections.PSController;
import dev.timecoding.ggspawngsg.events.PlayerGiveSpawnPlotEvent;
import dev.timecoding.ggspawngsg.events.PlayerRedeemSpawnPlotEvent;
import dev.timecoding.ggspawngsg.file.FileManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PS3E implements Listener {

    private GGSpawnGSG pl;

    public PS3E(GGSpawnGSG pl){
        this.pl = pl;
    }

    @EventHandler
    public void onPlayerGiveSpawnPlot(PlayerGiveSpawnPlotEvent e){
        if(!e.isCancelled()){
            PSController controller = pl.getPlotWrapper().getController(e.getTarget());
            FileManager fileManager = new FileManager();
            if(FileManager.cfg.getBoolean("Walls.OnSpawnPlotClaim.Enabled")){
                String border = FileManager.cfg.getString("Walls.OnSpawnPlotClaim.Border");
                String wall = FileManager.cfg.getString("Walls.OnSpawnPlotClaim.Wall");
                if(Material.valueOf(border) != null){
                    Plot p = (Plot) e.getPlot();
                    p.setComponent("border", border);
                }
                if(Material.valueOf(wall) != null){
                    Plot p = (Plot) e.getPlot();
                    p.setComponent("wall", wall);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerRedeemSpawnPlot(PlayerRedeemSpawnPlotEvent e){
        if(!e.isCancelled()){
            PSController controller = pl.getPlotWrapper().getController(e.getPlayer());
            FileManager fileManager = new FileManager();
            if(FileManager.cfg.getBoolean("Walls.OnSpawnPlotClaim.Enabled")){
                String border = FileManager.cfg.getString("Walls.OnSpawnPlotClaim.Border");
                String wall = FileManager.cfg.getString("Walls.OnSpawnPlotClaim.Wall");
                if(Material.valueOf(border) != null){
                    Plot p = (Plot) e.getPlot();
                    p.setComponent("border", border);
                }
                if(Material.valueOf(wall) != null){
                    Plot p = (Plot) e.getPlot();
                    p.setComponent("wall", wall);
                }
            }
        }
    }

}
