package testPack;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
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

public class Cmd1 implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(!player.isOp()) {
				player.sendMessage("오피만 사용할 수 있는 명령어입니다.");
			}
			
			if(cmd.getName().equalsIgnoreCase("speed")) {
				if(args.length == 0) {
					return true;
				}
				try { 
					String name = args[0];
					int value = Integer.parseInt(args[1]);
					Player target = Bukkit.getPlayer(name);
					
					if(value == 1) {
						new Speed().addMap(target, 50);
						player.sendMessage("1단계로 적용되었습니다.");
					} else if(value == 2) {
						new Speed().addMap(target, 60);
						player.sendMessage("2단계로 적용되었습니다.");
					} else if(value == 3) {
						new Speed().addMap(target, 70);
						player.sendMessage("3단계로 적용되었습니다.");
					} else if(value == 4) {
						new Speed().addMap(target, 80);
						player.sendMessage("4단계로 적용되었습니다.");
					} else if(value == 5) {
						new Speed().addMap(target, 90);
						player.sendMessage("5단계로 적용되었습니다.");
					} else {
						new Speed().addMap(target, 50);
						player.sendMessage("한계치를 넘어 1단계로 적용되었습니다.");
					}
					
				} catch(Exception e) {
					return true;
				}
			}
		}	
		return true;
	}

}
