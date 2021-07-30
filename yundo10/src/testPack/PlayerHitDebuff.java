package testPack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class PlayerHitDebuff {
	
	Random rnd = new Random();
	private int taskID;
	static boolean sw = false;
	static boolean qu = false;

	public void playerHitDebuff(Player player, Entity mob) {
		mob1(player, mob);
		mob2(player, mob);
		mob3(player, mob);
	}

	public void mob1(Player player, Entity mob) {
		if(mob.getCustomName().substring(2).equalsIgnoreCase("[부활한 왕] 둔뮤엘")) {
			int num = rnd.nextInt(50);
			
			if(num < 3) {
				new BukkitRunnable() {
					int time = 0;
					double var = 0;
					Location loc, first, second, first2, second2;

					@Override
					public void run() {
						
						if (time >= 20) {
							this.cancel();
						}

						var += Math.PI / 8;

						loc = mob.getLocation();

						for (int i = 1; i < 10; i++) {
							first = loc.clone().add(i, i, i);
							second = loc.clone().add(-i, i, i);
							first2 = loc.clone().add(-i, i, -i);
							second2 = loc.clone().add(i, i, -i);

							mob.getWorld().spawnParticle(Particle.LAVA, first, 0);
							mob.getWorld().spawnParticle(Particle.LAVA, second, 0);
							mob.getWorld().spawnParticle(Particle.LAVA, first2, 0);
							mob.getWorld().spawnParticle(Particle.LAVA, second2, 0);
						}
						
						time++;
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
//				player.damage(player.getHealth()/2);
//				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,100,1,true,true));
				mob.getWorld().playSound(mob.getLocation(), Sound.ENTITY_CAT_HISS, 4.0f, 1.0f);
				player.sendMessage(ChatColor.RED + "짐의 분노를 받아라!");
				sendMessage(player, ChatColor.RED + "짐의 분노를 받아라!");
				List<Entity> entitylist = mob.getNearbyEntities(10, 10, 10);
				for (Entity nearEntity : entitylist) {
					if (nearEntity.getType() == EntityType.PLAYER) {
						Player nearPlayer = (Player) nearEntity;
						nearPlayer.setFireTicks(600);
					}
				}
			} else if(num < 5 && !sw) {
				sw = true;
				
				new BukkitRunnable() {
					int time = 0;
					
					@Override
					public void run() {
						
						if(time >= 100) {
							List<Entity> entitylist = mob.getNearbyEntities(80, 30, 80);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof ArmorStand) {
									ArmorStand as = (ArmorStand) nearEntity;
									if((as.getItemInHand().getType() == Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_FIRE")) && (as.getRightArmPose().getX() == Math.toRadians(90))) {
										as.remove();
										continue;
									}
								}
							}
							entitylist = player.getNearbyEntities(80, 30, 80);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof ArmorStand) {
									ArmorStand as = (ArmorStand) nearEntity;
									if((as.getItemInHand().getType() == Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_FIRE")) && (as.getRightArmPose().getX() == Math.toRadians(90))) {
										as.remove();
										continue;
									}
								}
							}
							
							sw = false;
							this.cancel();
						}
						
						if(time == 49 || time == 99) {
							List<Entity> entitylist = mob.getNearbyEntities(30, 30, 30);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof ArmorStand) {
									ArmorStand as = (ArmorStand) nearEntity;
									if((as.getItemInHand().getType() == Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_FIRE")) && (as.getRightArmPose().getX() == Math.toRadians(90))) {
										as.remove();
										continue;
									}
								}
								if(nearEntity instanceof Player) {
									Player p = (Player) nearEntity;
									for(Entity ent : p.getNearbyEntities(2, 3, 2)) {
										if(ent instanceof ArmorStand) {
											ArmorStand as = (ArmorStand) ent;
											if((as.getItemInHand().getType() == Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_FIRE")) && (as.getRightArmPose().getX() == Math.toRadians(90))) {
												p.damage(10);
											}
										}
									}
								}
							}
						}
						
						if(time == 30 || time == 80) {
							List<Entity> entitylist = mob.getNearbyEntities(15, 30, 15);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof Player) {
									Player p = (Player) nearEntity;
									p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.5f, 1.0f);
									
									ArmorStand as = (ArmorStand) p.getWorld().spawnEntity(p.getLocation().add(0,8,0), EntityType.ARMOR_STAND);
									as.setVisible(false);
									as.setArms(true);
									as.setItemInHand(new ItemStack(Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_FIRE")));
									as.setRightArmPose(new EulerAngle(Math.toRadians(90), 0, 0));
									as.setRemoveWhenFarAway(true);
								}
							}
						}
						
						time++;
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				
				player.sendMessage(ChatColor.RED + "짐의 심판을 받아라!");
				sendMessage(player, ChatColor.RED + "짐의 심판을 받아라!");
				
			} else if(num < 8 && !qu) {
				
				qu = true;
				
				Location normal = mob.getLocation();
				((LivingEntity) mob).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 200, true,true));
				
				new BukkitRunnable() {
					int time = 0;
					int size = 0;
					Location e1;
					
					@Override
					public void run() {
						
						if(time % 1 == 0) {
							double var = 0;
							
							for(int i = 0 ; i < 128 ; i++) {
								if(size % 2 == 0) {
									e1 = normal.clone().add(Math.cos(var)*size, 0.3, Math.sin(var)*size);
								} else {
									e1 = normal.clone().add(Math.cos(var)*size, 0, Math.sin(var)*size);
								}
								mob.getWorld().spawnParticle(Particle.BLOCK_CRACK, e1, 0, new MaterialData(Material.DIRT));
								
								var += Math.PI / 64;
							}
							
							if(size == 9) {
								size = -1;
							}
							size++;
							
							mob.getWorld().playSound(mob.getLocation(), Sound.BLOCK_GRAVEL_BREAK, 10.0f, 1.0f);
						}
						
						if(time >= 210) {
							qu = false;
							this.cancel();
						}
						
						if(time % 40 == 0) {
							for(int size2 = 0 ; size2 < 10 ; size2++) {
								double var = 0;
								for(int i = 0 ; i < 128 ; i++) {
									e1 = normal.clone().add(Math.cos(var)*size2, 0, Math.sin(var)*size2);
									mob.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, e1, 0);
									
									var += Math.PI / 64;
								}
							}
							
							mob.getWorld().playSound(mob.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 8.0f, 0.6f);
							
							List<Entity> entitylist = mob.getNearbyEntities(10, 10, 10);
							for (Entity nearEntity : entitylist) {
								if (nearEntity.getType() == EntityType.PLAYER) {
									Player nearPlayer = (Player) nearEntity;
									if(nearPlayer.isOnGround()) {
										nearPlayer.damage(5);
									}
								}
							}
						}
						
						time++;
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				
				player.sendMessage(ChatColor.RED + "짐은 절대자이다!");
				sendMessage(player, ChatColor.RED + "짐은 절대자이다!");
				
			}
			
		}
	}
	
	public void mob2(Player player, Entity mob) {
		if(mob.getCustomName().substring(2).equalsIgnoreCase("[분노한 왕] 둔뮤엘")) {
			int num = rnd.nextInt(50);
			
			if(num < 3) {
				new BukkitRunnable() {
					int time = 0;
					double var = 0;
					Location loc, first, second, first2, second2;

					@Override
					public void run() {
						
						if (time >= 20) {
							this.cancel();
						}

						var += Math.PI / 8;

						loc = mob.getLocation();

						for (int i = 1; i < 10; i++) {
							first = loc.clone().add(i, i, i);
							second = loc.clone().add(-i, i, i);
							first2 = loc.clone().add(-i, i, -i);
							second2 = loc.clone().add(i, i, -i);

							mob.getWorld().spawnParticle(Particle.LAVA, first, 0);
							mob.getWorld().spawnParticle(Particle.LAVA, second, 0);
							mob.getWorld().spawnParticle(Particle.LAVA, first2, 0);
							mob.getWorld().spawnParticle(Particle.LAVA, second2, 0);
						}
						
						time++;
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				player.damage(5);
//				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,100,1,true,true));
				mob.getWorld().playSound(mob.getLocation(), Sound.ENTITY_CAT_HISS, 4.0f, 1.0f);
				player.sendMessage(ChatColor.RED + "짐의 분노를 받아라!");
				sendMessage(player, ChatColor.RED + "짐의 분노를 받아라!");
				List<Entity> entitylist = mob.getNearbyEntities(10, 10, 10);
				for (Entity nearEntity : entitylist) {
					if (nearEntity.getType() == EntityType.PLAYER) {
						Player nearPlayer = (Player) nearEntity;
						nearPlayer.setFireTicks(600);
					}
				}
			} else if(num < 5 && !sw) {
				sw = true;
				
				new BukkitRunnable() {
					int time = 0;
					
					@Override
					public void run() {
						
						if(time >= 100) {
							List<Entity> entitylist = mob.getNearbyEntities(80, 30, 80);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof ArmorStand) {
									ArmorStand as = (ArmorStand) nearEntity;
									if((as.getItemInHand().getType() == Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_FIRE")) && (as.getRightArmPose().getX() == Math.toRadians(90))) {
										as.remove();
										continue;
									}
								}
							}
							entitylist = player.getNearbyEntities(80, 30, 80);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof ArmorStand) {
									ArmorStand as = (ArmorStand) nearEntity;
									if((as.getItemInHand().getType() == Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_FIRE")) && (as.getRightArmPose().getX() == Math.toRadians(90))) {
										as.remove();
										continue;
									}
								}
							}
							
							sw = false;
							this.cancel();
						}
						
						if(time == 49 || time == 99) {
							List<Entity> entitylist = mob.getNearbyEntities(30, 30, 30);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof ArmorStand) {
									ArmorStand as = (ArmorStand) nearEntity;
									if((as.getItemInHand().getType() == Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_FIRE")) && (as.getRightArmPose().getX() == Math.toRadians(90))) {
										as.remove();
										continue;
									}
								}
								if(nearEntity instanceof Player) {
									Player p = (Player) nearEntity;
									for(Entity ent : p.getNearbyEntities(2, 3, 2)) {
										if(ent instanceof ArmorStand) {
											ArmorStand as = (ArmorStand) ent;
											if((as.getItemInHand().getType() == Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_FIRE")) && (as.getRightArmPose().getX() == Math.toRadians(90))) {
												p.damage(15);
											}
										}
									}
								}
							}
						}
						
						if(time == 30 || time == 80) {
							List<Entity> entitylist = mob.getNearbyEntities(15, 30, 15);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof Player) {
									Player p = (Player) nearEntity;
									p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.5f, 1.0f);
									
									ArmorStand as = (ArmorStand) p.getWorld().spawnEntity(p.getLocation().add(0,8,0), EntityType.ARMOR_STAND);
									as.setVisible(false);
									as.setArms(true);
									as.setItemInHand(new ItemStack(Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_FIRE")));
									as.setRightArmPose(new EulerAngle(Math.toRadians(90), 0, 0));
									as.setRemoveWhenFarAway(true);
								}
							}
						}
						
						time++;
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				
				player.sendMessage(ChatColor.RED + "짐의 심판을 받아라!");
				sendMessage(player, ChatColor.RED + "짐의 심판을 받아라!");
				
			} else if(num < 8 && !qu) {
				
				qu = true;
				
				Location normal = mob.getLocation();
				((LivingEntity) mob).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 200, true,true));
				
				new BukkitRunnable() {
					int time = 0;
					int size = 0;
					Location e1;
					
					@Override
					public void run() {
						
						if(time % 1 == 0) {
							double var = 0;
							
							for(int i = 0 ; i < 128 ; i++) {
								if(size % 2 == 0) {
									e1 = normal.clone().add(Math.cos(var)*size, 0.3, Math.sin(var)*size);
								} else {
									e1 = normal.clone().add(Math.cos(var)*size, 0, Math.sin(var)*size);
								}
								mob.getWorld().spawnParticle(Particle.BLOCK_CRACK, e1, 0, new MaterialData(Material.DIRT));
								
								var += Math.PI / 64;
							}
							
							if(size == 9) {
								size = -1;
							}
							size++;
							
							mob.getWorld().playSound(mob.getLocation(), Sound.BLOCK_GRAVEL_BREAK, 10.0f, 1.0f);
						}
						
						if(time >= 210) {
							qu = false;
							this.cancel();
						}
						
						if(time % 40 == 0) {
							for(int size2 = 0 ; size2 < 10 ; size2++) {
								double var = 0;
								for(int i = 0 ; i < 128 ; i++) {
									e1 = normal.clone().add(Math.cos(var)*size2, 0, Math.sin(var)*size2);
									mob.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, e1, 0);
									
									var += Math.PI / 64;
								}
							}
							
							mob.getWorld().playSound(mob.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 8.0f, 0.6f);
							
							List<Entity> entitylist = mob.getNearbyEntities(10, 10, 10);
							for (Entity nearEntity : entitylist) {
								if (nearEntity.getType() == EntityType.PLAYER) {
									Player nearPlayer = (Player) nearEntity;
									if(nearPlayer.isOnGround()) {
										nearPlayer.damage(10);
									}
								}
							}
						}
						
						time++;
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				
				player.sendMessage(ChatColor.RED + "짐은 절대자이다!");
				sendMessage(player, ChatColor.RED + "짐은 절대자이다!");
				
			}
			
		}
	}
	
	public void mob3(Player player, Entity mob) {
		if(mob.getCustomName().substring(2).equalsIgnoreCase("[부활한 왕] 둔뮤엘")) {
			int num = rnd.nextInt(50);
			if(num < 8) {
				Location loc = mob.getLocation();
				loc.setPitch(player.getLocation().getPitch() + 180);
				mob.teleport(loc);
			}
			if(num < 3) {
				new BukkitRunnable() {
					int time = 0;
					double var = 0;
					Location loc, first, second, first2, second2;

					@Override
					public void run() {
						
						if (time >= 20) {
							this.cancel();
						}

						var += Math.PI / 8;

						loc = mob.getLocation();

						for (int i = 1; i < 10; i++) {
							first = loc.clone().add(i, i, i);
							second = loc.clone().add(-i, i, i);
							first2 = loc.clone().add(-i, i, -i);
							second2 = loc.clone().add(i, i, -i);

							mob.getWorld().spawnParticle(Particle.LAVA, first, 0);
							mob.getWorld().spawnParticle(Particle.LAVA, second, 0);
							mob.getWorld().spawnParticle(Particle.LAVA, first2, 0);
							mob.getWorld().spawnParticle(Particle.LAVA, second2, 0);
						}
						
						time++;
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				player.damage(player.getHealth());
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,100,1,true,true));
				mob.getWorld().playSound(mob.getLocation(), Sound.ENTITY_CAT_HISS, 4.0f, 1.0f);
				player.sendMessage(ChatColor.RED + "짐의 분노를 받아라!");
				sendMessage(player, ChatColor.RED + "짐의 분노를 받아라!");
				List<Entity> entitylist = mob.getNearbyEntities(10, 10, 10);
				for (Entity nearEntity : entitylist) {
					if (nearEntity.getType() == EntityType.PLAYER) {
						Player nearPlayer = (Player) nearEntity;
						nearPlayer.setFireTicks(600);
					}
				}
			} else if(num < 5 && !sw) {
				sw = true;
				
				new BukkitRunnable() {
					int time = 0;
					
					@Override
					public void run() {
						
						if(time >= 100) {
							List<Entity> entitylist = mob.getNearbyEntities(80, 30, 80);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof ArmorStand) {
									ArmorStand as = (ArmorStand) nearEntity;
									if((as.getItemInHand().getType() == Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_FIRE")) && (as.getRightArmPose().getX() == Math.toRadians(90))) {
										as.remove();
										continue;
									}
								}
							}
							entitylist = player.getNearbyEntities(80, 30, 80);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof ArmorStand) {
									ArmorStand as = (ArmorStand) nearEntity;
									if((as.getItemInHand().getType() == Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_FIRE")) && (as.getRightArmPose().getX() == Math.toRadians(90))) {
										as.remove();
										continue;
									}
								}
							}
							
							sw = false;
							this.cancel();
						}
						
						if(time == 49 || time == 99) {
							List<Entity> entitylist = mob.getNearbyEntities(30, 30, 30);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof ArmorStand) {
									ArmorStand as = (ArmorStand) nearEntity;
									if((as.getItemInHand().getType() == Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_FIRE")) && (as.getRightArmPose().getX() == Math.toRadians(90))) {
										as.remove();
										continue;
									}
								}
								if(nearEntity instanceof Player) {
									Player p = (Player) nearEntity;
									for(Entity ent : p.getNearbyEntities(2, 3, 2)) {
										if(ent instanceof ArmorStand) {
											ArmorStand as = (ArmorStand) ent;
											if((as.getItemInHand().getType() == Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_FIRE")) && (as.getRightArmPose().getX() == Math.toRadians(90))) {
												p.damage(10);
											}
										}
									}
								}
							}
						}
						
						if(time == 30 || time == 80) {
							List<Entity> entitylist = mob.getNearbyEntities(15, 30, 15);
							for(Entity nearEntity : entitylist) {
								if(nearEntity instanceof Player) {
									Player p = (Player) nearEntity;
									p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.5f, 1.0f);
									
									ArmorStand as = (ArmorStand) p.getWorld().spawnEntity(p.getLocation().add(0,8,0), EntityType.ARMOR_STAND);
									as.setVisible(false);
									as.setArms(true);
									as.setItemInHand(new ItemStack(Material.getMaterial("ICEANDFIRE_DRAGONBONE_SWORD_FIRE")));
									as.setRightArmPose(new EulerAngle(Math.toRadians(90), 0, 0));
									as.setRemoveWhenFarAway(true);
									
									p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,50,200,true,true));
								}
							}
						}
						
						time++;
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				
				player.sendMessage(ChatColor.RED + "짐의 심판을 받아라!");
				sendMessage(player, ChatColor.RED + "짐의 심판을 받아라!");
				
			} else if(num < 8 && !qu) {
				
				qu = true;
				
				Location normal = mob.getLocation();
				((LivingEntity) mob).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 200, true,true));
				
				new BukkitRunnable() {
					int time = 0;
					int size = 0;
					Location e1;
					
					@Override
					public void run() {
						
						if(time % 1 == 0) {
							double var = 0;
							
							for(int i = 0 ; i < 128 ; i++) {
								if(size % 2 == 0) {
									e1 = normal.clone().add(Math.cos(var)*size, 0.3, Math.sin(var)*size);
								} else {
									e1 = normal.clone().add(Math.cos(var)*size, 0, Math.sin(var)*size);
								}
								mob.getWorld().spawnParticle(Particle.BLOCK_CRACK, e1, 0, new MaterialData(Material.DIRT));
								
								var += Math.PI / 64;
							}
							
							if(size == 9) {
								size = -1;
							}
							size++;
							
							mob.getWorld().playSound(mob.getLocation(), Sound.BLOCK_GRAVEL_BREAK, 10.0f, 1.0f);
						}
						
						if(time >= 210) {
							qu = false;
							this.cancel();
						}
						
						if(time % 40 == 0) {
							for(int size2 = 0 ; size2 < 10 ; size2++) {
								double var = 0;
								for(int i = 0 ; i < 128 ; i++) {
									e1 = normal.clone().add(Math.cos(var)*size2, 0, Math.sin(var)*size2);
									mob.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, e1, 0);
									
									var += Math.PI / 64;
								}
							}
							
							mob.getWorld().playSound(mob.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 8.0f, 0.6f);
							
							List<Entity> entitylist = mob.getNearbyEntities(10, 10, 10);
							for (Entity nearEntity : entitylist) {
								if (nearEntity.getType() == EntityType.PLAYER) {
									Player nearPlayer = (Player) nearEntity;
									if(nearPlayer.isOnGround()) {
										nearPlayer.damage(20);
									}
								}
							}
						}
						
						time++;
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				
				player.sendMessage(ChatColor.RED + "짐은 절대자이다!");
				sendMessage(player, ChatColor.RED + "짐은 절대자이다!");
				
			}
			
		}
	}

	public void sendMessage(Player player, String msg) {
		List<Entity> entitylist = player.getNearbyEntities(30, 10, 30);
		for (Entity nearEntity : entitylist) {
			if (nearEntity.getType() == EntityType.PLAYER) {
				Player nearPlayer = (Player) nearEntity;
				nearPlayer.sendMessage(msg);
			}
		}
	}
	
}