package towny_extras.towny_extras;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyCommandAddonAPI;
import com.palmergames.bukkit.towny.object.AddonCommand;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.palmergames.bukkit.towny.TownyCommandAddonAPI.CommandType;
import towny_extras.towny_extras.commands.NewNPCTownCommand;
import towny_extras.towny_extras.commands.PlotClaimCommand;

import java.util.ArrayList;
import java.util.List;

public final class Towny_extras extends JavaPlugin {
    AddonCommand claim_plot;
    AddonCommand new_npc_town;

    @Override
    public void onEnable() {
        // Plugin startup logic
        buildClaimPlot();
        buildNewNPCTown();

        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new TownyExtraTownyEventListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void buildClaimPlot(){
        claim_plot = new AddonCommand(CommandType.TOWNYADMIN, "claim_plot", new PlotClaimCommand());
        List<String> town_names;
        town_names = new ArrayList<>();
        for (Town town : TownyAPI.getInstance().getDataSource().getTowns()) {
            town_names.add(town.getName());
        }
        claim_plot.setTabCompletion(0, town_names);
        TownyCommandAddonAPI.addSubCommand(claim_plot);
    }
    public void buildNewNPCTown(){
        new_npc_town = new AddonCommand(CommandType.TOWNYADMIN, "new_npc_town", new NewNPCTownCommand());
        TownyCommandAddonAPI.addSubCommand(new_npc_town);
    }



}
