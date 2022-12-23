package dev.timecoding.ggspawngsg.api;

import com.intellectualcrafters.plot.PS;
import dev.timecoding.ggspawngsg.api.reflections.PS3;
import dev.timecoding.ggspawngsg.api.reflections.PS4;
import dev.timecoding.ggspawngsg.api.reflections.PS6;
import dev.timecoding.ggspawngsg.api.reflections.PSController;
import dev.timecoding.ggspawngsg.enums.PSVersion;
import org.bukkit.entity.Player;

public class PlotWrapper {

    private PSController controller = new PSController(getPlotSquaredVersion());

    public String getPlotAPIPackageString(PSVersion psVersion){
        switch(psVersion){
            case V3:
                return "com.intellectualcrafters.plot.api.PlotAPI";
            case V4:
                return "com.github.intellectualsites.plotsquared.api.PlotAPI";
            case V6:
                return "com.plotsquared.core.PlotAPI";
            case UNKNOWN:
                return null;
        }
        return null;
    }

    public PSController getController(Player p){
        return this.controller;
    }

    public Object getAvailablePlotAPI(){
        if(canInit()){
            try {
                return Class.forName(getPlotAPIPackageString(getPlotSquaredVersion())).newInstance();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public PSVersion getPlotSquaredVersion(){
        String version3 = "com.intellectualcrafters.plot.object.Plot";
        String version4 = "com.github.intellectualsites.plotsquared.plot.object.Plot";
        String version6 = "com.plotsquared.core.plot.Plot";
        if(classExists(version3)){
            return PSVersion.V3;
        }else if(classExists(version4)){
            return PSVersion.V4;
        }else if(classExists(version6)){
            return PSVersion.V6;
        }else{
            return PSVersion.UNKNOWN;
        }
    }

    public boolean canInit(){
        return !(getPlotSquaredVersion().equals(PSVersion.UNKNOWN));
    }

    private boolean classExists(String name){
        try {
            Class.forName(name);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
