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

public class Cmd2 implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(!player.isOp()) {
				player.sendMessage("오피만 사용할 수 있는 명령어입니다.");
			}
			
			if(cmd.getName().equalsIgnoreCase("speedall")) {
				if(args.length == 0) {
					return true;
				}
				try { 
					int value = Integer.parseInt(args[0]);

					for(Player target : Bukkit.getOnlinePlayers()) {
						if(value == 1) {
							new Speed().addMap(target, 70);
							target.setFlySpeed(0.4f);
						} else if(value == 2) {
							new Speed().addMap(target, 85);
							target.setFlySpeed(0.55f);
						} else if(value == 3) {
							new Speed().addMap(target, 100);
							target.setFlySpeed(0.7f);
						} else if(value == 4) {
							new Speed().addMap(target, 115);
							target.setFlySpeed(0.85f);
						} else if(value == 5) {
							new Speed().addMap(target, 130);
							target.setFlySpeed(1.0f);
						} else if(value == 0) {
							new Speed().addMap(target, 30);
							target.setFlySpeed(0.1f);
						} else {
							new Speed().addMap(target, 70);
							target.setFlySpeed(0.4f);
						}
					}
					if(value == 1) {
						player.sendMessage("모두의 속도가 1단계로 적용되었습니다.");
					} else if(value == 2) {
						player.sendMessage("모두의 속도가 2단계로 적용되었습니다.");
					} else if(value == 3) {
						player.sendMessage("모두의 속도가 3단계로 적용되었습니다.");
					} else if(value == 4) {
						player.sendMessage("모두의 속도가 4단계로 적용되었습니다.");
					} else if(value == 5) {
						player.sendMessage("모두의 속도가 5단계로 적용되었습니다.");
					} else if(value == 0) {
						player.sendMessage("모두의 속도가 -1단계로 적용되었습니다.");
					} else {
						player.sendMessage("모두의 속도가 한계치를 넘어 1단계로 적용되었습니다.");
					}
					return true;
				} catch(Exception e) {
					return true;
				}
			}
		}	
		return true;
	}

}
