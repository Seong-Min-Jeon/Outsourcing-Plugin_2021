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
		if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("11")) {
			skill1(player, key);
		} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("12")) {
			skill2(player, key);
		} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("14")) {
			skill3(player, key);
		} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("15")) {
			skill4(player, key);
		} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("16")) {
			skill5(player, key);
		} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("17")) {
			skill6(player, key);
		} 
	}
	
	// 대도적
	public void skill1(Player player, String key) {
		if(key.equals("RRR")) {
			int cool = 30;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect1();
				player.sendMessage(ChatColor.GREEN + "[스킬]질풍참이 발동됩니다.");

				Vector vec = player.getEyeLocation().getDirection().multiply(2.0f);
				player.setVelocity(vec);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 0.3f, 0.5f);
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RRL")) {
			player.sendMessage(ChatColor.RED + "해당 커맨드로 사용할 수 있는 스킬이 없습니다.");
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
		} else if(key.equals("RLL")) {
			player.sendMessage(ChatColor.RED + "해당 커맨드로 사용할 수 있는 스킬이 없습니다.");
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
		} else if(key.equals("RLR")) {
			player.sendMessage(ChatColor.RED + "해당 커맨드로 사용할 수 있는 스킬이 없습니다.");
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
		}
	}

	// 도제
	public void skill2(Player player, String key) {
		if (key.equals("RRR")) {
			int cool = 30;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect1();
				player.sendMessage(ChatColor.GREEN + "[스킬]질풍참이 발동됩니다.");

				Vector vec = player.getEyeLocation().getDirection().multiply(2.0f);
				player.setVelocity(vec);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 0.3f, 0.5f);
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RRL")) {
			int cool = 120;
			if (new CoolTime().coolCheck(player, cool, key)) {
				
				new BukkitRunnable() {
					
					int time = 0;
					@Override
					public void run() {
						time++;
						
						if(time > 10) {
							Vector vec = player.getEyeLocation().getDirection().multiply(2.0f);
							player.setVelocity(vec);
							this.cancel();
						}
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				
				new ParticleEffect(player).newEffect2();
				player.sendMessage(ChatColor.GREEN + "[스킬]파문 찌르기가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RLL")) {
			player.sendMessage(ChatColor.RED + "해당 커맨드로 사용할 수 있는 스킬이 없습니다.");
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
		} else if (key.equals("RLR")) {
			player.sendMessage(ChatColor.RED + "해당 커맨드로 사용할 수 있는 스킬이 없습니다.");
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
		}
	}
	
	// C헌터
	public void skill3(Player player, String key) {
		if(key.equals("RRR")) {
			int cool = 30;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect3();
				player.sendMessage(ChatColor.GREEN + "[스킬]3연격이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RRL")) {
			player.sendMessage(ChatColor.RED + "해당 커맨드로 사용할 수 있는 스킬이 없습니다.");
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
		} else if(key.equals("RLL")) {
			player.sendMessage(ChatColor.RED + "해당 커맨드로 사용할 수 있는 스킬이 없습니다.");
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
		} else if(key.equals("RLR")) {
			player.sendMessage(ChatColor.RED + "해당 커맨드로 사용할 수 있는 스킬이 없습니다.");
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
		}
	}
	
	// B헌터
	public void skill4(Player player, String key) {
		if(key.equals("RRR")) {
			int cool = 30;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect3();
				player.sendMessage(ChatColor.GREEN + "[스킬]3연격이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RRL")) {
			int cool = 60;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect4();
				player.sendMessage(ChatColor.GREEN + "[스킬]그림자 베기가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLL")) {
			player.sendMessage(ChatColor.RED + "해당 커맨드로 사용할 수 있는 스킬이 없습니다.");
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
		} else if(key.equals("RLR")) {
			player.sendMessage(ChatColor.RED + "해당 커맨드로 사용할 수 있는 스킬이 없습니다.");
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
		}
	}
	
	// A헌터
	public void skill5(Player player, String key) {
		if(key.equals("RRR")) {
			int cool = 30;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect3();
				player.sendMessage(ChatColor.GREEN + "[스킬]3연격이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RRL")) {
			int cool = 60;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect4();
				player.sendMessage(ChatColor.GREEN + "[스킬]그림자 베기가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLL")) {
			int cool = 300;
			if(new CoolTime().coolCheck(player, cool, key)) {
				
				new BukkitRunnable() {
					int time = 0;
					
					@Override
					public void run() {
						
						if(time == 0) {
							new PowerUp().addAry(player);
						}

						if(time >= 600) {
							player.sendMessage(ChatColor.GREEN + "[스킬]버서커 모드가 종료됩니다.");
							new PowerUp().removeAry(player);
							this.cancel();
						}
						
						time++;
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				
				player.sendMessage(ChatColor.GREEN + "[스킬]버서커 모드가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLR")) {
			player.sendMessage(ChatColor.RED + "해당 커맨드로 사용할 수 있는 스킬이 없습니다.");
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
		}
	}
	
	// S헌터
	public void skill6(Player player, String key) {
		if(key.equals("RRR")) {
			int cool = 30;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect3();
				player.sendMessage(ChatColor.GREEN + "[스킬]3연격이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RRL")) {
			int cool = 60;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect4();
				player.sendMessage(ChatColor.GREEN + "[스킬]그림자 베기가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLL")) {
			int cool = 300;
			if(new CoolTime().coolCheck(player, cool, key)) {
				
				new BukkitRunnable() {
					int time = 0;
					
					@Override
					public void run() {
						
						if(time == 0) {
							new PowerUp().addAry(player);
						}

						if(time >= 600) {
							player.sendMessage(ChatColor.GREEN + "[스킬]버서커 모드가 종료됩니다.");
							new PowerUp().removeAry(player);
							this.cancel();
						}
						
						time++;
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				
				player.sendMessage(ChatColor.GREEN + "[스킬]버서커 모드가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLR")) {
			int cool = 30;
			if(new CoolTime().coolCheck(player, cool, key)) {
				Vector vec = player.getEyeLocation().getDirection().multiply(2.3f);
				player.setVelocity(vec);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 0.3f, 0.5f);
				player.sendMessage(ChatColor.GREEN + "[스킬]진격이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		}
	}

	
}
