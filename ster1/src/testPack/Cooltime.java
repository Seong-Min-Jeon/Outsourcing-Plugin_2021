package testPack;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class Cooltime {

	private static HashMap<Player, Integer> map = new HashMap<>();
	private static int num = 0;
	
	public void putPlayer(Player player) {
		if(map.containsKey(player)) {
			map.remove(player);
		}
		map.put(player, num);
	}
	
	public boolean effect(Player player) {
		if(map.containsKey(player)) {
			if(num - map.get(player) > 6000) {
				putPlayer(player);
				return true;
			} else {
				player.sendMessage(ChatColor.RED + "아직 쿨타임이 끝나지 않았습니다.");
				return false;
			}
		} else {
			putPlayer(player);
			return true;
		}
	}
	
	public void start() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				num++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
}
