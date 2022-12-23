package dev.timecoding.ggspawngsg.api.reflections;

import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PS6 {

    private PlotAPI api = new PlotAPI();
    private PlotPlayer plotPlayer = null;
    private Plot plot = null;

    public PS6(Player p){
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
