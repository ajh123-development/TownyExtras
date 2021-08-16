package towny_extras.towny_extras.commands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class PlotClaimCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equals("claim_plot")){
            if (args.length < 2){
                sender.sendMessage("There must be a town name as first arg");
                return false;
            }
            try {
                Location loc = null;
                Town town = TownyAPI.getInstance().getDataSource().getTown(args[1]);
                if(sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender){
                    //TODO: stuff here
                }
                if(sender instanceof Player){
                    Player player = (Player) sender;
                    loc = player.getLocation();
                }
                if(loc == null){
                    sender.sendMessage("Location is invalid (null)");
                    return false;
                }
                TownBlock block = TownyAPI.getInstance().getTownBlock(loc);

                if(block == null){
                    block = new TownBlock(loc.getChunk().getX(), loc.getChunk().getZ(), town.getHomeblockWorld());
                    block.save();
                }
                    
                if(block.hasTown()){
                    Town blockTown = block.getTownOrNull();
                    if(blockTown == null){
                        sender.sendMessage("Location is not part of town");
                        return false;
                    }
                    sender.sendMessage("Location is part of "+blockTown.getName());
                    return false;
                }
                
                block.setTown(town);
                block.save();
                sender.sendMessage("Location is now part of "+town.getName());
                return true;

            } catch (NotRegisteredException e) {
                sender.sendMessage(e.getMessage());
                return false;
            }
        }
        return true;
    }
}
