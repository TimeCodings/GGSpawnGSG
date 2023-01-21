package dev.timecoding.ggspawngsg.api.reflections;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Location;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PS3 {

    private PlotAPI api = new PlotAPI();
    private PlotPlayer plotPlayer = null;
    private Plot plot = null;

    public PS3(Player p){
        plotPlayer = api.wrapPlayer(p.getName());
        if(plotPlayer.getCurrentPlot() != null){
            plot = plotPlayer.getCurrentPlot();
        }
    }

    public String getPlotID(){
        if(playerOnPlot()){
            return getPlot().getId().toString();
        }
        return "";
    }

    public void setPlotOwner(String uuid){
        if(playerOnPlot()){
            plot.setOwner(UUID.fromString(uuid));
        }
    }

    public void setBorder(Material material){
        if(playerOnPlot()){
            Plot p = getPlot();
            p.setComponent("border", material.name());
        }
    }

    public void setWall(Material material){
        if(playerOnPlot()){
            Plot p = getPlot();
            p.setComponent("wall", material.name());
        }
    }

    public boolean playerOnPlot(){
        return (getPlot() != null);
    }

    public Plot getPlot() {
        return plot;
    }

    public Object getApi() {
        return api;
    }

    public PlotPlayer getPlotPlayer() {
        return plotPlayer;
    }
}
