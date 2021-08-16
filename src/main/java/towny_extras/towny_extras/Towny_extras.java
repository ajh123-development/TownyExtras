package towny_extras.towny_extras;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.AddonCommand;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.plugin.java.JavaPlugin;
import com.palmergames.bukkit.towny.TownyCommandAddonAPI.CommandType;
import towny_extras.towny_extras.commands.PlotClaimCommand;

import java.util.ArrayList;
import java.util.List;

public final class Towny_extras extends JavaPlugin {
    
    @Override
    public void onEnable() {
        // Plugin startup logic
        AddonCommand myCommand = new AddonCommand(CommandType.TOWNYADMIN, "claim_plot", new PlotClaimCommand());
        List<String> town_names;
        town_names = new ArrayList<>();
        for (Town town : TownyAPI.getInstance().getDataSource().getTowns()) {
            town_names.add(town.getFormattedName());
        }
        myCommand.setTabCompletion(0, town_names);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
