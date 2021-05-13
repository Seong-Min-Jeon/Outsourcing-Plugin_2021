package testPack;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Speed {
	
	public static HashMap<Player, Double> map = new HashMap<>();

	public void addMap(Player player, double num) {
		if(map.containsKey(player)) {
			map.remove(player);
		}
		map.put(player, num);
	}
	
	public double getSpeed(Player player) {
		if(map.containsKey(player)) {
			return map.get(player);
		} else {
			return 18;
		}
	}
	
	public void clear() {
		map.clear();
	}
	
}
