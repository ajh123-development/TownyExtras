package towny_extras.towny_extras;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyCommandAddonAPI;
import com.palmergames.bukkit.towny.event.NewTownEvent;
import com.palmergames.bukkit.towny.event.DeleteTownEvent;
import com.palmergames.bukkit.towny.object.AddonCommand;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import com.palmergames.bukkit.towny.TownyCommandAddonAPI.CommandType;
import towny_extras.towny_extras.commands.PlotClaimCommand;

import java.util.ArrayList;
import java.util.List;

public final class Towny_extras extends JavaPlugin {
    AddonCommand claim_plot;

    @Override
    public void onEnable() {
        // Plugin startup logic
        buildClaimPlot();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void buildClaimPlot(){
        claim_plot = new AddonCommand(CommandType.TOWNYADMIN, "claim_plot", new PlotClaimCommand());
        List<String> town_names;
        town_names = new ArrayList<>();
        for (Town town : TownyAPI.getInstance().getDataSource().getTowns()) {
            town_names.add(town.getName());
        }
        claim_plot.setTabCompletion(0, town_names);
        TownyCommandAddonAPI.addSubCommand(claim_plot);
    }


    @EventHandler
    public void onTownDestroy(DeleteTownEvent event){
        buildClaimPlot();
    }

    @EventHandler
    public void onTownCreate(NewTownEvent event){
        buildClaimPlot();
    }
}
