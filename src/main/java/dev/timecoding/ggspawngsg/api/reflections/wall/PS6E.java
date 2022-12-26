package dev.timecoding.ggspawngsg.api.reflections.wall;

import com.plotsquared.core.configuration.ConfigurationUtil;
import com.plotsquared.core.plot.BlockBucket;
import com.plotsquared.core.plot.Plot;
import com.sk89q.worldedit.function.pattern.Pattern;
import dev.timecoding.ggspawngsg.GGSpawnGSG;
import dev.timecoding.ggspawngsg.api.reflections.PSController;
import dev.timecoding.ggspawngsg.events.PlayerGiveSpawnPlotEvent;
import dev.timecoding.ggspawngsg.events.PlayerRedeemSpawnPlotEvent;
import dev.timecoding.ggspawngsg.file.FileManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PS6E implements Listener {

    private GGSpawnGSG pl;

    public PS6E(GGSpawnGSG pl){
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
                    Pattern pattern = ((BlockBucket) ConfigurationUtil.BLOCK_BUCKET.parseString(border)).toPattern();
                    p.getPlotModificationManager().setComponent("border", pattern, null, null);
                }
                if(Material.valueOf(wall) != null){
                    Plot p = (Plot) e.getPlot();
                    Pattern pattern = ((BlockBucket) ConfigurationUtil.BLOCK_BUCKET.parseString(border)).toPattern();
                    p.getPlotModificationManager().setComponent("wall", pattern, null, null);
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
                    Pattern pattern = ((BlockBucket) ConfigurationUtil.BLOCK_BUCKET.parseString(border)).toPattern();
                    p.getPlotModificationManager().setComponent("border", pattern, null, null);
                }
                if(Material.valueOf(wall) != null){
                    Plot p = (Plot) e.getPlot();
                    Pattern pattern = ((BlockBucket) ConfigurationUtil.BLOCK_BUCKET.parseString(border)).toPattern();
                    p.getPlotModificationManager().setComponent("wall", pattern, null, null);
                }
            }
        }
    }

}
