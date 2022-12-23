package dev.timecoding.ggspawngsg.api.reflections;

import dev.timecoding.ggspawngsg.enums.PSVersion;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PSController {

    private PSVersion version = null;

    public PSController(PSVersion version){
        this.version = version;
    }

    public String getPlotID(Player p){
        switch(version){
            case V3:
                return new PS3(p).getPlotID();
            case V4:
                return new PS4(p).getPlotID();
            case V6:
                return new PS6(p).getPlotID();
        }
        return "";
    }

    public void setPlotOwner(Player p, String uuid){
        switch(version){
            case V3:
                new PS3(p).setPlotOwner(uuid);
                break;
            case V4:
                new PS4(p).setPlotOwner(uuid);
                break;
            case V6:
                new PS6(p).setPlotOwner(uuid);
                break;
        }
    }

    public boolean playerOnPlot(Player p){
        switch(version){
            case V3:
                return new PS3(p).playerOnPlot();
            case V4:
                return new PS4(p).playerOnPlot();
            case V6:
                return new PS6(p).playerOnPlot();
        }
        return false;
    }

    public Object getPlotObject(Player p){
        switch(version){
            case V3:
                return new PS3(p).getPlot();
            case V4:
                return new PS4(p).getPlot();
            case V6:
                return new PS6(p).getPlot();
        }
        return null;
    }

    public Object getAPIObject(Player p){
        switch(version){
            case V3:
                return new PS3(p).getApi();
            case V4:
                return new PS4(p).getApi();
            case V6:
                return new PS6(p).getApi();
        }
        return null;
    }

}
