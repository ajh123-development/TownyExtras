package towny_extras.towny_extras;

import com.palmergames.bukkit.towny.event.DeleteTownEvent;
import com.palmergames.bukkit.towny.event.NewTownEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TownyExtraTownyEventListener implements Listener {
    
    private final Towny_extras plugin;

    public TownyExtraTownyEventListener(Towny_extras plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTownDestroy(DeleteTownEvent event){
        plugin.buildClaimPlot();
    }

    @EventHandler
    public void onTownCreate(NewTownEvent event){
        plugin.buildClaimPlot();
    }
}
