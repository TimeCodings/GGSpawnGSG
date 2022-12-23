package dev.timecoding.ggspawngsg.api.reflections;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PS3 {

    private PlotAPI api = new PlotAPI();
    private PlotPlayer plotPlayer = null;
    private Plot plot = null;

    public PS3(Player p){
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
