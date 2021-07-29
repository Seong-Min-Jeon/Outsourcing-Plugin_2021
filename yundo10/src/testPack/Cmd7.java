package testPack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Cmd7 implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(!player.isOp()) {
				player.sendMessage("오피 명령어 입니다!");
				return false;
			}
			
			try {
				Location loc = player.getLocation();
				
				String raid = args[0];
				String level = args[1];
				
				if(raid.equals("뮨듀엘")) {
					Location chestLoc = new Location(player.getWorld(), 10000, 1, 10000);
					Block block = chestLoc.getBlock();
					Chest chest = (Chest) block.getState();
					
					if(level.equals("1")) {
						Skeleton entity = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
						entity.setCustomName(ChatColor.DARK_RED + "[부활한 왕] 듄뮤엘");
						entity.setCustomNameVisible(false);
						entity.setMaxHealth(1000);
						entity.setHealth(1000);
						entity.setRemoveWhenFarAway(false);
						entity.setAI(false);
						EntityEquipment head = entity.getEquipment();
						ItemStack headItem = chest.getInventory().getItem(0);
						head.setHelmet(headItem);
						EntityEquipment chestplate = entity.getEquipment();
						ItemStack chestplateItem = chest.getInventory().getItem(1);
						chestplate.setChestplate(chestplateItem);
						entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
						
						for(Player p : Bukkit.getOnlinePlayers()) {
							p.sendMessage(ChatColor.DARK_RED + "[부활한 왕] 듄뮤엘이 소환됩니다.");
							p.sendMessage("좌표: " + (int)(loc.getX()) + " " +  + (int)(loc.getY()) + " "  + (int)(loc.getZ()));
							new Bar().bar1.setProgress(1.0);
							new Bar().bar1.addPlayer(p);
						}
					} else if(level.equals("2")) {
						Skeleton entity = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
						entity.setCustomName(ChatColor.DARK_RED + "[분노한 왕] 듄뮤엘");
						entity.setCustomNameVisible(false);
						entity.setMaxHealth(1000);
						entity.setHealth(1000);
						entity.setRemoveWhenFarAway(false);
						entity.setAI(false);
						EntityEquipment head = entity.getEquipment();
						ItemStack headItem = chest.getInventory().getItem(0);
						head.setHelmet(headItem);
						EntityEquipment chestplate = entity.getEquipment();
						ItemStack chestplateItem = chest.getInventory().getItem(1);
						chestplate.setChestplate(chestplateItem);
						entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
						
						for(Player p : Bukkit.getOnlinePlayers()) {
							p.sendMessage(ChatColor.DARK_RED + "[분노한 왕] 듄뮤엘이 소환됩니다.");
							p.sendMessage("좌표: " + (int)(loc.getX()) + " " +  + (int)(loc.getY()) + " "  + (int)(loc.getZ()));
							new Bar().bar1.setProgress(1.0);
							new Bar().bar1.addPlayer(p);
						}
					} else if(level.equals("3")) {
						Skeleton entity = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
						entity.setCustomName(ChatColor.DARK_RED + "[진정한 왕] 듄뮤엘");
						entity.setCustomNameVisible(false);
						entity.setMaxHealth(1000);
						entity.setHealth(1000);
						entity.setRemoveWhenFarAway(false);
						entity.setAI(false);
						EntityEquipment head = entity.getEquipment();
						ItemStack headItem = chest.getInventory().getItem(0);
						head.setHelmet(headItem);
						EntityEquipment chestplate = entity.getEquipment();
						ItemStack chestplateItem = chest.getInventory().getItem(1);
						chestplate.setChestplate(chestplateItem);
						entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
						
						for(Player p : Bukkit.getOnlinePlayers()) {
							p.sendMessage(ChatColor.DARK_RED + "[진정한 왕] 듄뮤엘이 소환됩니다.");
							p.sendMessage("좌표: " + (int)(loc.getX()) + " " +  + (int)(loc.getY()) + " "  + (int)(loc.getZ()));
							new Bar().bar1.setProgress(1.0);
							new Bar().bar1.addPlayer(p);
						}
					} else {
						player.sendMessage("해당 레벨의 레이드 보스는 없습니다.");
					}
				} else {
					player.sendMessage("해당 이름의 레이드 보스는 없습니다.");
				}
			} catch(Exception e) {
				
			}
			
		}	
		return true;
	}

}
