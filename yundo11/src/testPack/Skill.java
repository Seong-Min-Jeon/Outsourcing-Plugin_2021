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
		if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("10")) {
			skill0(player, key);
		} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("11")) {
			skill1(player, key);
		} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("12")) {
			skill2(player, key);
		} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("27")) {
			skill2_1(player, key);
		} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("14")) {
			skill3(player, key);
		} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("15")) {
			skill4(player, key);
		} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("16")) {
			skill5(player, key);
		} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("17")) {
			skill6(player, key);
		} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("21")) {
			skill7(player, key);
		} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("22")) {
			skill8(player, key);
		} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("23")) {
			skill9(player, key);
		} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("100")) {
			skill10(player, key);
		}
	}
	
	// 도적
	public void skill0(Player player, String key) {
		if(key.equals("RRR")) {
			player.sendMessage(ChatColor.RED + "해당 커맨드로 사용할 수 있는 스킬이 없습니다.");
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
		} else if(key.equals("RRL")) {
			player.sendMessage(ChatColor.RED + "해당 커맨드로 사용할 수 있는 스킬이 없습니다.");
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
		} else if(key.equals("RLL")) {
			int cool = 200;
			if (new CoolTime().coolCheck(player, cool, key)) {
				
				new BukkitRunnable() {
					
					int time = 0;
					
					@Override
					public void run() {
						time++;
						
						if(time == 1) {
							for(Player p : Bukkit.getOnlinePlayers()) {
								p.hidePlayer(player);
							}
						}
						
						if(time == 120) {
							for(Player p : Bukkit.getOnlinePlayers()) {
								p.showPlayer(player);
							}
							this.cancel();
						}
						
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				player.sendMessage(ChatColor.GREEN + "[스킬]은신이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLR")) {
			player.sendMessage(ChatColor.RED + "해당 커맨드로 사용할 수 있는 스킬이 없습니다.");
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
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
			int cool = 200;
			if (new CoolTime().coolCheck(player, cool, key)) {
				
				new BukkitRunnable() {
					
					int time = 0;
					
					@Override
					public void run() {
						time++;
						
						if(time == 1) {
							for(Player p : Bukkit.getOnlinePlayers()) {
								p.hidePlayer(player);
							}
						}
						
						if(time == 160) {
							for(Player p : Bukkit.getOnlinePlayers()) {
								p.showPlayer(player);
							}
							this.cancel();
						}
						
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				player.sendMessage(ChatColor.GREEN + "[스킬]은신이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
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
			int cool = 200;
			if (new CoolTime().coolCheck(player, cool, key)) {
				
				new BukkitRunnable() {
					
					int time = 0;
					
					@Override
					public void run() {
						time++;
						
						if(time == 1) {
							for(Player p : Bukkit.getOnlinePlayers()) {
								p.hidePlayer(player);
							}
						}
						
						if(time == 200) {
							for(Player p : Bukkit.getOnlinePlayers()) {
								p.showPlayer(player);
							}
							this.cancel();
						}
						
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				player.sendMessage(ChatColor.GREEN + "[스킬]은신이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RLR")) {
			int cool = 200;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect7();
				player.sendMessage(ChatColor.GREEN + "[스킬]독무가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		}
	}
	
	// 다크로드
	public void skill2_1(Player player, String key) {
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
			int cool = 100;
			if (new CoolTime().coolCheck(player, cool, key)) {
				
				new BukkitRunnable() {
					
					int time = 0;
					@Override
					public void run() {
						time++;
						
						if(time > 0) {
							Vector vec = player.getEyeLocation().getDirection().multiply(0.3f);
							player.setVelocity(vec);
						}
						
						if(time >= 25) {
							this.cancel();
						}
						
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				
				new ParticleEffect(player).newEffect8();
				player.sendMessage(ChatColor.GREEN + "[스킬]심연의 암살이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RLL")) {
			int cool = 200;
			if (new CoolTime().coolCheck(player, cool, key)) {
				
				new BukkitRunnable() {
					
					int time = 0;
					
					@Override
					public void run() {
						time++;
						
						if(time == 1) {
							for(Player p : Bukkit.getOnlinePlayers()) {
								p.hidePlayer(player);
							}
						}
						
						if(time == 200) {
							for(Player p : Bukkit.getOnlinePlayers()) {
								p.showPlayer(player);
							}
							this.cancel();
						}
						
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				player.sendMessage(ChatColor.GREEN + "[스킬]은신이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RLR")) {
			int cool = 200;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect7();
				player.sendMessage(ChatColor.GREEN + "[스킬]독무가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		}
	}
	
	// C헌터
	public void skill3(Player player, String key) {
		if(key.equals("RRR")) {
			int cool = 30;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect3();
				player.sendMessage(ChatColor.GREEN + "[스킬]트리플 크러쉬가 발동됩니다.");
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
				player.sendMessage(ChatColor.GREEN + "[스킬]트리플 크러쉬가 발동됩니다.");
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
				player.sendMessage(ChatColor.GREEN + "[스킬]트리플 크러쉬가 발동됩니다.");
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
				player.sendMessage(ChatColor.GREEN + "[스킬]트리플 크러쉬가 발동됩니다.");
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

	// 기사
	public void skill7(Player player, String key) {
		if(key.equals("RRR")) {
			int cool = 50;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect5();
				player.sendMessage(ChatColor.GREEN + "[스킬]베쉬가 발동됩니다.");
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
	
	// 기사장
	public void skill8(Player player, String key) {
		if(key.equals("RRR")) {
			int cool = 50;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect5();
				player.sendMessage(ChatColor.GREEN + "[스킬]베쉬가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RRL")) {
			int cool = 300;
			if(new CoolTime().coolCheck(player, cool, key)) {
				
				new BukkitRunnable() {
					int time = 0;
					
					@Override
					public void run() {
						
						if(time == 0) {
							player.setNoDamageTicks(200);
						}

						if(time >= 200) {
							player.sendMessage(ChatColor.GREEN + "[스킬]굳건한 의지가 종료됩니다.");
							this.cancel();
						}
						
						time++;
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				
				player.sendMessage(ChatColor.GREEN + "[스킬]굳건한 의지가 발동됩니다.");
				player.sendMessage(ChatColor.GREEN + "10초간 무적이 됩니다.");
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
	
	// 기사단장
	public void skill9(Player player, String key) {
		if(key.equals("RRR")) {
			int cool = 50;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect5();
				player.sendMessage(ChatColor.GREEN + "[스킬]베쉬가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RRL")) {
			int cool = 300;
			if(new CoolTime().coolCheck(player, cool, key)) {
				
				new BukkitRunnable() {
					int time = 0;
					
					@Override
					public void run() {
						
						if(time == 0) {
							player.setNoDamageTicks(200);
						}

						if(time >= 200) {
							player.sendMessage(ChatColor.GREEN + "[스킬]굳건한 의지가 종료됩니다.");
							this.cancel();
						}
						
						time++;
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				
				player.sendMessage(ChatColor.GREEN + "[스킬]굳건한 의지가 발동됩니다.");
				player.sendMessage(ChatColor.GREEN + "10초간 무적이 됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLL")) {
			int cool = 120;
			if(new CoolTime().coolCheck(player, cool, key)) {
				
				new BukkitRunnable() {
					int time = 0;
					
					@Override
					public void run() {
						
						if(time == 5 || time == 25) {
							List<Entity> entitylist = player.getNearbyEntities(10, 5, 10);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof LivingEntity) {
									((LivingEntity) nearEntity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 100, true, false));
									((LivingEntity) nearEntity).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 200, true, false));
									ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(nearEntity.getLocation().add(0,8,0), EntityType.ARMOR_STAND);
									as.setVisible(false);
									as.setArms(true);
									as.setItemInHand(new ItemStack(Material.getMaterial("ICEANDFIRE_SILVER_SWORD")));
									as.setRightArmPose(new EulerAngle(Math.toRadians(90), 0, 0));
									as.setRemoveWhenFarAway(true);
								}
							}
						}
						
						if(time == 20 || time == 40) {
							List<Entity> entitylist = player.getNearbyEntities(10, 5, 10);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof ArmorStand) {
									ArmorStand as = (ArmorStand) nearEntity;
									if((as.getItemInHand().getType() == Material.getMaterial("ICEANDFIRE_SILVER_SWORD")) && (as.getRightArmPose().getX() == Math.toRadians(90))) {
										as.remove();
										continue;
									}
								}
								if(nearEntity instanceof LivingEntity) {
									((LivingEntity) nearEntity).damage(10);
								}
							}
						}
						
						if(time == 45) {
							List<Entity> entitylist = player.getNearbyEntities(30, 30, 30);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof ArmorStand) {
									ArmorStand as = (ArmorStand) nearEntity;
									if((as.getItemInHand().getType() == Material.getMaterial("ICEANDFIRE_SILVER_SWORD")) && (as.getRightArmPose().getX() == Math.toRadians(90))) {
										as.remove();
										continue;
									}
								}
							}
						}
						
						if(time == 50) {
							this.cancel();
						}
						
						time++;
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				
				player.sendMessage(ChatColor.GREEN + "[스킬]심판의 칼날이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLR")) {
			player.sendMessage(ChatColor.RED + "해당 커맨드로 사용할 수 있는 스킬이 없습니다.");
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
		}
	}
	
	// 제황
	public void skill10(Player player, String key) {
		if(key.equals("RRR")) {
			int cool = 1;
			if(new CoolTime().coolCheck(player, cool, key)) {

				Vector vec = player.getEyeLocation().getDirection().multiply(3.0f);
				player.setVelocity(vec);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 0.3f, 0.5f);
				
				player.sendMessage(ChatColor.GREEN + "[스킬]섬광이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RRL")) {
			int cool = 30;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect6();
				player.sendMessage(ChatColor.GREEN + "[스킬]신의 심판이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLL")) {
			int cool = 30;
			if(new CoolTime().coolCheck(player, cool, key)) {
				
				new BukkitRunnable() {
					int time = 0;
					
					@Override
					public void run() {
						
						if(time == 5 || time == 25) {
							List<Entity> entitylist = player.getNearbyEntities(10, 5, 10);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof LivingEntity) {
									((LivingEntity) nearEntity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 100, true, false));
									((LivingEntity) nearEntity).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 200, true, false));
									ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(nearEntity.getLocation().add(0,8,0), EntityType.ARMOR_STAND);
									as.setVisible(false);
									as.setArms(true);
									as.setItemInHand(new ItemStack(Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_FIRE")));
									as.setRightArmPose(new EulerAngle(Math.toRadians(90), 0, 0));
									as.setRemoveWhenFarAway(true);
								}
							}
						}
						
						if(time == 20 || time == 40) {
							List<Entity> entitylist = player.getNearbyEntities(10, 5, 10);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof ArmorStand) {
									ArmorStand as = (ArmorStand) nearEntity;
									if((as.getItemInHand().getType() == Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_FIRE")) && (as.getRightArmPose().getX() == Math.toRadians(90))) {
										as.remove();
										continue;
									}
								}
								if(nearEntity instanceof LivingEntity) {
									((LivingEntity) nearEntity).setFireTicks(200);
									((LivingEntity) nearEntity).damage(10);
								}
							}
						}
						
						if(time == 45) {
							List<Entity> entitylist = player.getNearbyEntities(30, 30, 30);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof ArmorStand) {
									ArmorStand as = (ArmorStand) nearEntity;
									if((as.getItemInHand().getType() == Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_FIRE")) && (as.getRightArmPose().getX() == Math.toRadians(90))) {
										as.remove();
										continue;
									}
								}
							}
						}
						
						if(time == 50) {
							this.cancel();
						}
						
						time++;
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				
				player.sendMessage(ChatColor.GREEN + "[스킬]불의 심판이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLR")) {
			int cool = 30;
			if(new CoolTime().coolCheck(player, cool, key)) {
				
				new BukkitRunnable() {
					int time = 0;
					
					@Override
					public void run() {
						
						if(time == 5 || time == 25) {
							List<Entity> entitylist = player.getNearbyEntities(10, 5, 10);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof LivingEntity) {
									((LivingEntity) nearEntity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 100, true, false));
									((LivingEntity) nearEntity).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 200, true, false));
									ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(nearEntity.getLocation().add(0,8,0), EntityType.ARMOR_STAND);
									as.setVisible(false);
									as.setArms(true);
									as.setItemInHand(new ItemStack(Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_ICE")));
									as.setRightArmPose(new EulerAngle(Math.toRadians(90), 0, 0));
									as.setRemoveWhenFarAway(true);
								}
							}
						}
						
						if(time == 20 || time == 40) {
							List<Entity> entitylist = player.getNearbyEntities(10, 5, 10);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof ArmorStand) {
									ArmorStand as = (ArmorStand) nearEntity;
									if((as.getItemInHand().getType() == Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_ICE")) && (as.getRightArmPose().getX() == Math.toRadians(90))) {
										as.remove();
										continue;
									}
								}
								if(nearEntity instanceof LivingEntity) {
									((LivingEntity) nearEntity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2, true, false));
									((LivingEntity) nearEntity).damage(10);
								}
							}
						}
						
						if(time == 45) {
							List<Entity> entitylist = player.getNearbyEntities(30, 30, 30);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof ArmorStand) {
									ArmorStand as = (ArmorStand) nearEntity;
									if((as.getItemInHand().getType() == Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_ICE")) && (as.getRightArmPose().getX() == Math.toRadians(90))) {
										as.remove();
										continue;
									}
								}
							}
						}
						
						if(time == 50) {
							this.cancel();
						}
						
						time++;
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				
				player.sendMessage(ChatColor.GREEN + "[스킬]얼음의 심판이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		}
	}
	
	
}
