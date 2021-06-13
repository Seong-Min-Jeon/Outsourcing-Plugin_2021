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
	private int sleep = 0;
	
	private static HashMap<String, Integer> map = new HashMap<>();
	
	public void effect(Player player, String key) {
		if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("11")) {
			skill1(player, key);
		}
	}
	
	public void skill1(Player player, String key) {
		if(key.equals("RRR")) {
//			new ParticleEffect(player).newEffect49();
			player.sendMessage(ChatColor.GREEN + "[스킬]블링크가 발동됩니다.");

			Vector vec = player.getEyeLocation().getDirection().multiply(3.0f);
			player.setVelocity(vec);
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f);
		} else if(key.equals("RRL")) {
//			new ParticleEffect(player).newEffect49();
			player.sendMessage(ChatColor.GREEN + "[스킬]블링크가 발동됩니다.");

			Vector vec = player.getEyeLocation().getDirection().multiply(3.0f);
			player.setVelocity(vec);
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f);
		} else if(key.equals("RLL")) {
			
		} else if(key.equals("RLR")) {
			
		}
	}

	
}
