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
							target.setWalkSpeed(0.3f);
							target.setFlySpeed(0.3f);
						} else if(value == 2) {
							target.setWalkSpeed(0.4f);
							target.setFlySpeed(0.4f);
						} else if(value == 3) {
							target.setWalkSpeed(0.5f);
							target.setFlySpeed(0.5f);
						} else if(value == 4) {
							target.setWalkSpeed(0.6f);
							target.setFlySpeed(0.6f);
						} else if(value == 5) {
							target.setWalkSpeed(0.7f);
							target.setFlySpeed(0.7f);
						} else if(value == 0) {
							target.setWalkSpeed(0.1f);
							target.setFlySpeed(0.1f);
						} else {
							target.setWalkSpeed(0.3f);
							target.setFlySpeed(0.3f);
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
