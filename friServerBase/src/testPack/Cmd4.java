package testPack;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd4 implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
			
		if(sender instanceof Player) {
			Player player = (Player) sender;
			try {
//				if(player.getDisplayName().equals("yumehama")) {
//					for(Entity ent : player.getWorld().getEntities()){
//						try {
//							if(ent.getType() == EntityType.SKELETON_HORSE) {
//								ent.remove();
//							}
//						} catch(Exception e) {
//							
//						}
//					}
//					return true;
//				}
			} catch(Exception e) {
				
			}
			player.setHealth(0);
		}
		return true;
	}
}
