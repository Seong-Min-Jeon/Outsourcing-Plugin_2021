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
					
					if(Bukkit.getOnlinePlayers().contains(target)) {
						if(value == 1) {
							target.setWalkSpeed(0.3f);
							target.setFlySpeed(0.3f);
							player.sendMessage("1단계로 적용되었습니다.");
						} else if(value == 2) {
							target.setWalkSpeed(0.40f);
							target.setFlySpeed(0.40f);
							player.sendMessage("2단계로 적용되었습니다.");
						} else if(value == 3) {
							target.setWalkSpeed(0.50f);
							target.setFlySpeed(0.50f);
							player.sendMessage("3단계로 적용되었습니다.");
						} else if(value == 4) {
							target.setWalkSpeed(0.60f);
							target.setFlySpeed(0.60f);
							player.sendMessage("4단계로 적용되었습니다.");
						} else if(value == 5) {
							target.setWalkSpeed(0.7f);
							target.setFlySpeed(0.7f);
							player.sendMessage("5단계로 적용되었습니다.");
						} else if(value == 0) {
							target.setWalkSpeed(0.1f);
							target.setFlySpeed(0.1f);
							player.sendMessage("-1단계로 적용되었습니다.");
						} else {
							target.setWalkSpeed(0.3f);
							target.setFlySpeed(0.3f);
							player.sendMessage("한계치를 넘어 1단계로 적용되었습니다.");
						}
					} else {
						player.sendMessage(ChatColor.RED + "서버에 해당 캐릭터는 없습니다!");
					}
					
				} catch(Exception e) {
					return true;
				}
			}
		}	
		return true;
	}

}
