package testPack;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Cmd2 implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			Location loc2 = player.getLocation();
			if (!(loc2.getX() <= -51 && loc2.getY() <= 255 && loc2.getZ() <= 47 
					&& loc2.getX() >= -57 && loc2.getY() >= 0 && loc2.getZ() >= 41)) {
				player.sendMessage("팀명은 대기실의 블루팀 영역에서만 바꿀 수 있습니다.");
				return false;
			}
			
			if(cmd.getName().equalsIgnoreCase("blueteam")) {
				if(args.length == 0) {
					return true;
				}
				try { 
					String name = args[0];
				} catch(Exception e) {
					return true;
				}
			}
		}	
		return true;
	}

}
