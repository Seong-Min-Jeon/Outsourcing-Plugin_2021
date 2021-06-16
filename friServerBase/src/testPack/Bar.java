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

public class Bar {
	
	public static BossBar bar1 = Bukkit.createBossBar(ChatColor.RED + "Red Team", BarColor.RED, BarStyle.SEGMENTED_10);
	public static BossBar bar2 = Bukkit.createBossBar(ChatColor.AQUA + "Blue Team", BarColor.BLUE, BarStyle.SEGMENTED_10);

	public void setTeamNameRed(String str) {
		for(Player all : Bukkit.getOnlinePlayers()) {
			all.sendMessage(ChatColor.RED + "레드팀" + ChatColor.WHITE + "의 팀명이 [" + ChatColor.RED + str + ChatColor.WHITE + "]으로 변경되었습니다.");
		}
		bar1.setTitle(ChatColor.RED + str);
	}
	
	public void setTeamNameBlue(String str) {
		for(Player all : Bukkit.getOnlinePlayers()) {
			all.sendMessage(ChatColor.BLUE + "블루팀" + ChatColor.WHITE + "의 팀명이 [" + ChatColor.BLUE + str + ChatColor.WHITE + "]으로 변경되었습니다.");
		}
		bar2.setTitle(ChatColor.BLUE + str);
	}
	
}
