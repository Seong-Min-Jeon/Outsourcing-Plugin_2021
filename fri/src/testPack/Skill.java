package testPack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Husk;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Mushroom;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class Skill {

	Random rnd = new Random();
	
	public void effect(Player player, String key) {
		if(player.getInventory().getItem(8).getItemMeta().getDisplayName().equals(ChatColor.GOLD + "마우러스의 증표")) {
			skill1(player, key);
		} else if(player.getInventory().getItem(8).getItemMeta().getDisplayName().equals(ChatColor.GOLD + "나오의 증표")) {
			skill2(player, key);
		}
	}
	
	// 마우러스
	public void skill1(Player player, String key) {
		if(key.equals("RRR")) {
			int cool = 10;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect1();
				player.sendMessage(ChatColor.GREEN + "[스킬]데스 마리오네트가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RRL")) {
			int cool = 20;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect2();
				player.sendMessage(ChatColor.GREEN + "[스킬]클럭워크가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLL")) {
			int cool = 25;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect3();
				player.sendMessage(ChatColor.GREEN + "[스킬]슈레드치퍼가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLR")) {
			if(player.getLevel() >= 10) {
				player.setLevel(0);
				player.setExp(0);
				new ParticleEffect(player).newEffect4();
				player.sendMessage(ChatColor.GREEN + "[스킬]데스트로이 액세스가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "궁극기는 10레벨에 사용할 수 있습니다.");
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		}
	}
	
	// 나오
	public void skill2(Player player, String key) {
		if(key.equals("RRR")) {
			int cool = 10;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect5();
				player.sendMessage(ChatColor.GREEN + "[스킬]바르카롤이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RRL")) {
			int cool = 20;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect6();
				player.sendMessage(ChatColor.GREEN + "[스킬]에델바이스가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLL")) {
			int cool = 30;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect7();
				player.sendMessage(ChatColor.GREEN + "[스킬]스톤가든이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLR")) {
			if(player.getLevel() >= 10) {
				player.setLevel(0);
				player.setExp(0);
				new ParticleEffect(player).newEffect8();
				player.sendMessage(ChatColor.GREEN + "[스킬]알스트로메리아가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "궁극기는 10레벨에 사용할 수 있습니다.");
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		}
	}
	
}
