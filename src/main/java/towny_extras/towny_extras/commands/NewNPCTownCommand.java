package towny_extras.towny_extras.commands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyWorld;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class NewNPCTownCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equals("new_npc_town")){
            if (args.length < 2){
                sender.sendMessage("There must be a town name as first arg");
                return false;
            }
            if (args.length < 3){
                sender.sendMessage("There must be a npc name as second arg");
                return false;
            }

            Location loc = null;
            if(sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender){
                sender.sendMessage("Console cannot use this command :(");
                return false;
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
            TownyWorld world = TownyAPI.getInstance().getTownyWorld(loc.getWorld());

            if(block == null){
                block = new TownBlock(loc.getChunk().getX(), loc.getChunk().getZ(), world);
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
            Town town = new Town(args[1]);
            town.setWorld(world);
            TownBlock home = TownyAPI.getInstance().getTownBlock(loc);
            town.setHomeBlock(home);
            Resident mayor = new Resident(args[2]);
            mayor.setNPC(true);
            try {
                mayor.setTown(town);
            } catch (AlreadyRegisteredException e) {
               sender.sendMessage(e.getMessage());
               return false;
            }
            town.setMayor(mayor);
            try {
                TownyUniverse.getInstance().registerTown(town);
            } catch (AlreadyRegisteredException e) {
                sender.sendMessage(e.getMessage());
                return false;
            }
            mayor.save();
            town.save();

            block.setTown(town);
            block.save();
            sender.sendMessage("New town "+town.getName());
            return true;

        }
        return true;
    }
}
