package testPack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ParticleEffect {

	private int taskID;
	private final Player player;
	private Entity ent;
	private int damNum = 0;
	
	public ParticleEffect(Player player) {
		this.player = player;
	}
	
	public ParticleEffect(Player player, int damNum) {
		this.player = player;
		this.damNum = damNum;
	}
	
	public ParticleEffect(Player player, Entity ent) {
		this.player = player;
		this.ent = ent;
	}

	// 질풍참
	public void newEffect1() {

		World world = player.getWorld(); 
		
		world.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1.0f, 3.0f);
		
		new BukkitRunnable() {
			int time = 0;
			
		    Location e1;

			@Override
			public void run() {
				
				Location loc = player.getLocation();
				
				if(time == 18) {
					double rot = Math.toRadians(loc.getYaw());
					double var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						e1 = loc.clone().add(Math.cos(var+rot)*1.5, 1.5-(i*0.05), Math.sin(var+rot)*1.5);
						world.spawnParticle(Particle.REDSTONE, e1, 0,0.3,0.7,0.3,1);
						
						var += Math.PI / 32;
					}	
					
					var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						e1 = loc.clone().add(Math.cos(var+rot)*1.5, 1.3-(i*0.05), Math.sin(var+rot)*1.5);
						world.spawnParticle(Particle.REDSTONE, e1, 0,0.2,0.5,0.2,1);
						
						var += Math.PI / 32;
					}
					
					world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 2.0f);
					
					List<Entity> entitylist = player.getNearbyEntities(4, 4, 4);				
					for (Entity nearEntity : entitylist) {
						if (nearEntity instanceof LivingEntity) {
							LivingEntity ent = (LivingEntity) nearEntity;
							ent.damage(10);
						}
					}
				}

				if(time >= 20) {
					this.cancel();
				}
				
				time++;
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	// 발도
	public void newEffect2() {

		Location normal = player.getEyeLocation();
		World world = player.getWorld();

		world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_SPLASH, 3.0f, 2.0f);

		new BukkitRunnable() {
			int time = 0;

			Location e1, e2;

			double rot = normal.getYaw();
			double dirX = Math.cos(Math.toRadians(rot));
			double dirZ = Math.sin(Math.toRadians(rot));

			@Override
			public void run() {

				if (time == 0) {

					double var = 0;

					double arrowAngle1 = 90;
					double totalAngle1 = normal.getYaw() + arrowAngle1;
					double dirX1 = Math.cos(Math.toRadians(totalAngle1));
					double dirZ1 = Math.sin(Math.toRadians(totalAngle1));

					e1 = normal.clone().add(dirX1 * 1, 1, dirZ1 * 1);

					for (int i = 0; i < 16; i++) {
						e2 = e1.clone().add(Math.cos(var) * 1 * dirX, Math.sin(var) * 1, Math.cos(var) * 1 * dirZ);
						world.spawnParticle(Particle.REDSTONE, e2, 0,0.01,0.7,0.9,1);

						var += Math.PI / 8;
					}

					arrowAngle1 = 40;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));

					e1 = normal.clone().add(dirX1 * 2.2, 1.2, dirZ1 * 2.2);

					for (int i = 0; i < 16; i++) {
						e2 = e1.clone().add(Math.cos(var) * 0.8 * dirX, Math.sin(var) * 0.8,
								Math.cos(var) * 0.8 * dirZ);
						world.spawnParticle(Particle.REDSTONE, e2, 0,0.01,0.7,0.9,1);

						var += Math.PI / 8;
					}

					arrowAngle1 = 140;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));

					e1 = normal.clone().add(dirX1 * 2.2, 1.2, dirZ1 * 2.2);

					for (int i = 0; i < 16; i++) {
						e2 = e1.clone().add(Math.cos(var) * 0.8 * dirX, Math.sin(var) * 0.8,
								Math.cos(var) * 0.8 * dirZ);
						world.spawnParticle(Particle.REDSTONE, e2, 0,0.01,0.7,0.9,1);

						var += Math.PI / 8;
					}

					arrowAngle1 = 50;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));

					e1 = normal.clone().add(dirX1 * 1.8, 0.1, dirZ1 * 1.8);

					for (int i = 0; i < 16; i++) {
						e2 = e1.clone().add(Math.cos(var) * 0.7 * dirX, Math.sin(var) * 0.7,
								Math.cos(var) * 0.7 * dirZ);
						world.spawnParticle(Particle.REDSTONE, e2, 0,0.01,0.7,0.9,1);

						var += Math.PI / 8;
					}

					arrowAngle1 = 130;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));

					e1 = normal.clone().add(dirX1 * 1.8, 0.1, dirZ1 * 1.8);

					for (int i = 0; i < 16; i++) {
						e2 = e1.clone().add(Math.cos(var) * 0.7 * dirX, Math.sin(var) * 0.7,
								Math.cos(var) * 0.7 * dirZ);
						world.spawnParticle(Particle.REDSTONE, e2, 0,0.01,0.7,0.9,1);

						var += Math.PI / 8;
					}

					arrowAngle1 = 30;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));

					e1 = normal.clone().add(dirX1 * 3, 0, dirZ1 * 3);

					for (int i = 0; i < 8; i++) {
						e2 = e1.clone().add(Math.cos(var) * 0.4 * dirX, Math.sin(var) * 0.4,
								Math.cos(var) * 0.4 * dirZ);
						world.spawnParticle(Particle.REDSTONE, e2, 0,0.01,0.7,0.9,1);

						var += Math.PI / 4;
					}

					arrowAngle1 = 150;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));

					e1 = normal.clone().add(dirX1 * 3, 0, dirZ1 * 3);

					for (int i = 0; i < 8; i++) {
						e2 = e1.clone().add(Math.cos(var) * 0.4 * dirX, Math.sin(var) * 0.4,
								Math.cos(var) * 0.4 * dirZ);
						world.spawnParticle(Particle.REDSTONE, e2, 0,0.01,0.7,0.9,1);

						var += Math.PI / 4;
					}
				}

				if (time > 10) {
					world.spawnParticle(Particle.REDSTONE, player.getEyeLocation(), 0,0.2,0.2,1,1);
					world.spawnParticle(Particle.REDSTONE, player.getEyeLocation(), 0,1,1,1,1);
					world.spawnParticle(Particle.WATER_DROP, player.getEyeLocation(), 2);
					world.spawnParticle(Particle.WATER_SPLASH, player.getEyeLocation(), 2);
				}

				if (time == 10) {
					ArmorStand tmpEnt = (ArmorStand) world.spawnEntity(normal, EntityType.ARMOR_STAND);
					tmpEnt.setVisible(false);
					tmpEnt.setSmall(true);

					new BukkitRunnable() {
						@Override
						public void run() {
							tmpEnt.remove();
							this.cancel();
						}
					}.runTaskTimer(Main.getPlugin(Main.class), 0, 3);

					List<Entity> entitylist = tmpEnt.getNearbyEntities(4, 4, 4);
					for (Entity nearEntity : entitylist) {
						if (nearEntity instanceof LivingEntity) {
							LivingEntity ent = (LivingEntity) nearEntity;
							ent.damage(15);
						}
					}

					world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_SPLASH, 4.0f, 1.0f);
				}

				if (time >= 20) {
					ArmorStand tmpEnt = (ArmorStand) world.spawnEntity(normal, EntityType.ARMOR_STAND);
					tmpEnt.setVisible(false);
					tmpEnt.setSmall(true);

					new BukkitRunnable() {
						@Override
						public void run() {
							tmpEnt.remove();
							this.cancel();
						}
					}.runTaskTimer(Main.getPlugin(Main.class), 0, 3);

					List<Entity> entitylist = tmpEnt.getNearbyEntities(4, 4, 4);
					for (Entity nearEntity : entitylist) {
						if (nearEntity instanceof LivingEntity) {
							LivingEntity ent = (LivingEntity) nearEntity;
							ent.damage(15);
						}
					}

					this.cancel();
				}

				time++;
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}

	// 3연격
	public void newEffect3() {
		Location normal = player.getEyeLocation();
		World world = player.getWorld(); 
		
		double arrowAngle1 = 73;
        double totalAngle1 = normal.getYaw() + arrowAngle1;
        double dirX1 = Math.cos(Math.toRadians(totalAngle1));
        double dirZ1 = Math.sin(Math.toRadians(totalAngle1));
        
        double arrowAngle2 = 90;
        double totalAngle2 = normal.getYaw() + arrowAngle2;
        double dirX2 = Math.cos(Math.toRadians(totalAngle2));
        double dirZ2 = Math.sin(Math.toRadians(totalAngle2));
        
        double arrowAngle3 = 107;
        double totalAngle3 = normal.getYaw() + arrowAngle3;
        double dirX3 = Math.cos(Math.toRadians(totalAngle3));
        double dirZ3 = Math.sin(Math.toRadians(totalAngle3));
		
		world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 3.0f, 0.5f);
		
		new BukkitRunnable() {
			int time = 0;

			@Override
			public void run() {
				Location e1, e2, e3;
				
				if(time % 1 == 0 && time <= 13) {
					e1 = normal.clone().add(dirX1*4, 1.5-(time*0.15), dirZ1*4);
					player.getWorld().spawnParticle(Particle.REDSTONE, e1, 0,0.8,0.5,0.01,1);
					
					e2 = normal.clone().add(dirX2*4, 1.5-(time*0.15), dirZ2*4);
					player.getWorld().spawnParticle(Particle.REDSTONE, e2, 0,0.8,0.5,0.01,1);
					
					e3 = normal.clone().add(dirX3*4, 1.5-(time*0.15), dirZ3*4);
					player.getWorld().spawnParticle(Particle.REDSTONE, e3, 0,0.8,0.5,0.01,1);
					
					List<Entity> entitylist = player.getNearbyEntities(3, 3, 3);				
					for (Entity nearEntity : entitylist) {
						if (nearEntity instanceof LivingEntity) {
							LivingEntity ent = (LivingEntity) nearEntity;
							ent.damage(5);
						}
					}
				}
				
				if(time == 13) {
					e1 = normal.clone().add(dirX1*4, 1-(time*0.15), dirZ1*4);
					e2 = normal.clone().add(dirX2*4, 1-(time*0.15), dirZ2*4);
					e3 = normal.clone().add(dirX3*4, 1-(time*0.15), dirZ3*4);
					world.playSound(normal, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1.0f);
					world.spawnParticle(Particle.EXPLOSION_LARGE, e1, 0);
					world.spawnParticle(Particle.EXPLOSION_LARGE, e2, 0);
					world.spawnParticle(Particle.EXPLOSION_LARGE, e3, 0);
				}
				
				if(time >= 23) {
					e1 = normal.clone().add(dirX1*4, -1.5, dirZ1*4);
					e2 = normal.clone().add(dirX2*4, -1.5, dirZ2*4);
					e3 = normal.clone().add(dirX3*4, -1.5, dirZ3*4);
					Material mat = player.getLocation().add(0,-1,0).getBlock().getType();
					try {
						player.getWorld().playEffect(e1, Effect.STEP_SOUND, mat);
						player.getWorld().playEffect(e2, Effect.STEP_SOUND, mat);
						player.getWorld().playEffect(e3, Effect.STEP_SOUND, mat);
					} catch(Exception e) {
						player.getWorld().playEffect(e1, Effect.STEP_SOUND, Material.GRASS);
						player.getWorld().playEffect(e2, Effect.STEP_SOUND, Material.GRASS);
						player.getWorld().playEffect(e3, Effect.STEP_SOUND, Material.GRASS);
					}
					
					List<Entity> entitylist = player.getNearbyEntities(3, 3, 3);				
					for (Entity nearEntity : entitylist) {
						if (nearEntity instanceof LivingEntity) {
							LivingEntity ent = (LivingEntity) nearEntity;
							ent.damage(7);
						}
					}
					
					this.cancel();
				}
				
				time++;
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
		
	}
	
	// 그림자 베기
	public void newEffect4() {

		World world = player.getWorld(); 
		
		new BukkitRunnable() {
			int time = 0;
			
		    Location e1;

			@Override
			public void run() {
				
				Location loc = player.getLocation();
				
				if(time == 0) {
					
					Vector vec = player.getEyeLocation().getDirection().multiply(1.6f);
					player.setVelocity(vec);
					
					double rot = Math.toRadians(loc.getYaw());
					double var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						e1 = loc.clone().add(Math.cos(var+rot)*1.5, 1.5-(i*0.05), Math.sin(var+rot)*1.5);
						player.getWorld().spawnParticle(Particle.REDSTONE, e1, 0,0.8,0.5,0.01,1);
						
						var += Math.PI / 32;
					}	
					
					var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						e1 = loc.clone().add(Math.cos(var+rot)*1.5, 1.3-(i*0.05), Math.sin(var+rot)*1.5);
						player.getWorld().spawnParticle(Particle.REDSTONE, e1, 0,0.6,0.3,0.01,1);
						
						var += Math.PI / 32;
					}
					
					world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 2.0f);
					world.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 0.6f, 1.0f);
					
					List<Entity> entitylist = player.getNearbyEntities(3, 3, 3);				
					for (Entity nearEntity : entitylist) {
						if (nearEntity instanceof LivingEntity) {
							LivingEntity ent = (LivingEntity) nearEntity;
							ent.damage(10);
						}
					}
					
				}
				
				if(time == 10) {
					
					Vector vec = player.getEyeLocation().getDirection().multiply(-1.6f);
					player.setVelocity(vec);
					
					Location tel = player.getLocation();
					tel.setPitch(player.getLocation().getPitch()+180);
					player.teleport(tel);
					
					double rot = Math.toRadians(loc.getYaw());
					double var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						e1 = loc.clone().add(Math.cos(var+rot)*1.5, 1.5-(i*0.05), Math.sin(var+rot)*1.5);
						player.getWorld().spawnParticle(Particle.REDSTONE, e1, 0,0.8,0.5,0.01,1);
						
						var += Math.PI / 32;
					}	
					
					var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						e1 = loc.clone().add(Math.cos(var+rot)*1.5, 1.3-(i*0.05), Math.sin(var+rot)*1.5);
						player.getWorld().spawnParticle(Particle.REDSTONE, e1, 0,0.6,0.3,0.01,1);
						
						var += Math.PI / 32;
					}
					
					world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 2.0f);
					world.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 0.6f, 1.0f);
					
					List<Entity> entitylist = player.getNearbyEntities(3, 3, 3);				
					for (Entity nearEntity : entitylist) {
						if (nearEntity instanceof LivingEntity) {
							LivingEntity ent = (LivingEntity) nearEntity;
							ent.damage(10);
						}
					}
					
				}

				if(time >= 20) {
					this.cancel();
				}
				
				time++;
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
		
	}



}
