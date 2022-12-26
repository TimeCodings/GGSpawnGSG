package dev.timecoding.ggspawngsg.api.reflections;

import com.github.intellectualsites.plotsquared.api.PlotAPI;
import com.github.intellectualsites.plotsquared.plot.object.Plot;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PS4 {

    private PlotAPI api = new PlotAPI();
    private PlotPlayer plotPlayer = null;
    private Plot plot = null;

    public PS4(Player p){
        plotPlayer = api.wrapPlayer(UUID.fromString(p.getUniqueId().toString()));
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

    public void setPlotOwner(String uuid){
        if(playerOnPlot()){
            plot.setOwner(UUID.fromString(uuid));
        }
    }

    public boolean playerOnPlot(){
        return (getPlot() != null);
    }

    public Plot getPlot() {
        return plot;
    }

    public PlotAPI getApi() {
        return api;
    }

    public PlotPlayer getPlotPlayer() {
        return plotPlayer;
    }
}
