package red.man10.beamgun;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by takatronix on 2017/03/21.
 */
public class BeamGunCommand  implements CommandExecutor {
    private final BeamGunPlugin plugin;

    public BeamGunCommand(BeamGunPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            CommandSender p = sender;
            p.sendMessage("§e============== §d●§f●§a●§eMan10 BeamGun§d●§f●§a● §e===============");
            p.sendMessage("§c/mbg get [name] - get BeamGun controller");
            p.sendMessage("§e============== §d●§f●§a●§eMan10 BeamGun§d●§f●§a● §e===============");
            p.sendMessage("§ehttp://man10.red Minecraft Man10 Server");
            p.sendMessage("§ecreated by takatronix http://takatronix.com");
            p.sendMessage("§ecreated by takatronix http://twitter.com/takatronix");
            return false;
        }

        if(args[0].equalsIgnoreCase("get")){
            if(args.length != 2){
                sender.sendMessage("/sw get [dronetype:0-10]");
                return false;
            }
            plugin.giveController((Player)sender,args[1]);
            return true;
        }




        return true;
    }
}
