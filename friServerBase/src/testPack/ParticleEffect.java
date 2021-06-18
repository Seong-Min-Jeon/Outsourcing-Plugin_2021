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
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Ocelot.Type;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class ParticleEffect {

	private int sleep;
	private final Player player;
	private Player target;
	Scoreboard board;
	
	public ParticleEffect(Player player) {
		this.player = player;
	}
	
	public ParticleEffect(Player player, Player ent) {
		this.player = player;
		this.target = ent;
	}

	// 데스 마리오네트
	public void newEffect1() {

		Location normal = player.getLocation();
		World world = player.getWorld(); 
		
		world.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 3.0f, 1.0f);
		
		if(player.getHealth() <= 5) {
			player.setHealth(0);
		} else {
			player.setHealth(player.getHealth() - 5);
		}
		
		String myTeam = getTeam(player);
		
		for(Entity ent : player.getNearbyEntities(4, 4, 4)) {
			if(ent instanceof Player) {
				Player p = (Player) ent;
				String yourTeam = getTeam(p);
				
				if(myTeam.equals(yourTeam)) {
					if(p.getHealth() >= 17) {
						p.setHealth(20);
					} else {
						p.setHealth(p.getHealth() + 3);
					}
					break;
				}
			}
		}
        
		new BukkitRunnable() {
			int time = 0;
			int size = 0;
			
		    Location e1, e2, e3;

			@Override
			public void run() {
				
				if(time % 1 == 0) {
					double var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						if(size % 2 == 0) {
							e2 = normal.clone().add(Math.cos(var)*size, 0.5, Math.sin(var)*size);
						} else {
							e2 = normal.clone().add(Math.cos(var)*size, 0, Math.sin(var)*size);
						}
						world.spawnParticle(Particle.REDSTONE, e2, 0, 0.4,0.01,0.4,1);
						
						var += Math.PI / 16;
					}
					
					if(size == 3) {
						size = -1;
					}
					size++;
				}
				
				if(time % 3 == 0) {
					double var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						e1 = normal.clone().add(Math.cos(var)*4, 0, Math.sin(var)*4);
						world.spawnParticle(Particle.REDSTONE, e1, 0 ,0.3,0.01,0.3,1);
						
						if(i % 4 == 0) {
							e3 = e1.clone().add(0,5,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.35,0.1,0.35,1);
							e3 = e1.clone().add(0,4.5,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.35,0.1,0.35,1);
							e3 = e1.clone().add(0,4,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.35,0.1,0.35,1);
							e3 = e1.clone().add(0,3.5,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.35,0.1,0.35,1);
							e3 = e1.clone().add(0,3,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.35,0.1,0.35,1);
							e3 = e1.clone().add(0,2.5,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.35,0.1,0.35,1);
							e3 = e1.clone().add(0,2,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.35,0.1,0.35,1);
							e3 = e1.clone().add(0,1.5,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.35,0.1,0.35,1);
							e3 = e1.clone().add(0,1,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.35,0.1,0.35,1);
							e3 = e1.clone().add(0,0.5,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.35,0.1,0.35,1);
						}
						
						var += Math.PI / 16;
					}
				}

				if(time >= 10) {
					this.cancel();
				}
				
				time++;
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	// 클럭워크
	public void newEffect2() {

		World world = player.getWorld();
		
		String myTeam = getTeam(player);
		
		for(Entity ent : player.getNearbyEntities(3, 3, 3)) {
			if(ent instanceof Player) {
				Player p = (Player) ent;
				String yourTeam = getTeam(p);
				
				if(!myTeam.equals(yourTeam)) {
					p.damage(5);
					
					if(player.getHealth() >= 15) {
						player.setHealth(20);
					} else {
						player.setHealth(player.getHealth() + 5);
					}
					
					break;
				}
			}
		}
		
		world.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 0.5f, 2.0f);
	}

	// 슈레드치퍼
	public void newEffect3() {
		
		World world = player.getWorld();
		
		Arrow arrow = player.launchProjectile(Arrow.class);
		arrow.setShooter(player);
		arrow.setVelocity(player.getLocation().getDirection().multiply(2.0f));		
		world.spawnParticle(Particle.FLAME, arrow.getLocation(), 2);
		world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.5f, 1.0f);
		
		Item dItem = arrow.getWorld().dropItem(arrow.getLocation(), new ItemStack(Material.BLAZE_POWDER));
		dItem.setPickupDelay(10000000);
		arrow.addPassenger(dItem);
		
		Thread t = new Thread(player.getUniqueId());
		sleep = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
			
			@Override
			public void run() {
				if (!t.hasID()) {
					t.setID(sleep);
				}
			
				if(arrow.isDead()) {	
					String myTeam = getTeam(player);
					
					for(Entity ent : arrow.getNearbyEntities(1, 1, 1)) {
						if(ent instanceof Player) {
							Player p = (Player) ent;
							String yourTeam = getTeam(p);
							
							if(!myTeam.equals(yourTeam)) {
								p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 100));
								p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 200));
								break;
							}
						}
					}
					t.endTask();
					t.removeID();
				}
			}						
			
		}, 0, 1);
		
	}
	
	// 데스트로이 액세스
	public void newEffect4() {

		String myTeam = getTeam(player);
		
		for(Entity ent : player.getNearbyEntities(5, 5, 5)) {
			if(ent instanceof Player) {
				Player p = (Player) ent;
				String yourTeam = getTeam(p);
				
				if(!myTeam.equals(yourTeam)) {
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 3.0f, 1.0f);
					p.getWorld().playEffect(p.getLocation(), Effect.EXPLOSION_HUGE, 1);
					p.damage(1000);
					break;
				}
			}
		}
		
	}

	// 바르카롤
	public void newEffect5() {

		World world = player.getWorld();
		
		Arrow arrow = player.launchProjectile(Arrow.class);
		arrow.setShooter(player);
		arrow.setVelocity(player.getLocation().getDirection().multiply(2.0f));		
		world.spawnParticle(Particle.CRIT_MAGIC, arrow.getLocation(), 10);
		world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.5f, 1.0f);
		
		Item dItem = arrow.getWorld().dropItem(arrow.getLocation(), new ItemStack(Material.ENDER_PEARL));
		dItem.setPickupDelay(10000000);
		arrow.addPassenger(dItem);
		
		Thread t = new Thread(player.getUniqueId());
		sleep = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
			
			@Override
			public void run() {
				if (!t.hasID()) {
					t.setID(sleep);
				}
			
				if(arrow.isDead()) {	
					String myTeam = getTeam(player);
					
					for(Entity ent : arrow.getNearbyEntities(2, 2, 2)) {
						if(ent instanceof Player) {
							Player p = (Player) ent;
							String yourTeam = getTeam(p);
							
							if(myTeam.equals(yourTeam)) {
								if(p.getHealth() >= 16) {
									p.setHealth(20);
								} else {
									p.setHealth(p.getHealth() + 4);
								}
							}
						}
					}
					
					t.endTask();
					t.removeID();
				}
			}						
			
		}, 0, 1);
		
	}

	// 에델바이스
	public void newEffect6() {
		Location normal = player.getLocation();
		World world = player.getWorld(); 
		
		if(player.getHealth() >= 16) {
			player.setHealth(20);
		} else {
			player.setHealth(player.getHealth() + 4);
		}
		
		String myTeam = getTeam(player);
		
		for(Entity ent : player.getNearbyEntities(4, 4, 4)) {
			if(ent instanceof Player) {
				Player p = (Player) ent;
				String yourTeam = getTeam(p);
				
				if(myTeam.equals(yourTeam)) {
					if(p.getHealth() >= 16) {
						p.setHealth(20);
					} else {
						p.setHealth(p.getHealth() + 4);
					}
					break;
				}
			}
		}
		
		world.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 3.0f, 1.0f);
        
		new BukkitRunnable() {
			int time = 0;
			int size = 0;
			
		    Location e1, e2, e3;

			@Override
			public void run() {
				
				if(time % 1 == 0) {
					double var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						if(size % 2 == 0) {
							e2 = normal.clone().add(Math.cos(var)*size, 0.5, Math.sin(var)*size);
						} else {
							e2 = normal.clone().add(Math.cos(var)*size, 0, Math.sin(var)*size);
						}
						world.spawnParticle(Particle.REDSTONE, e2, 2, 0,0.01,0.8,0.5,1);
						
						var += Math.PI / 16;
					}
					
					if(size == 3) {
						size = -1;
					}
					size++;
				}
				
				if(time % 3 == 0) {
					double var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						e1 = normal.clone().add(Math.cos(var)*4, 0, Math.sin(var)*4);
						world.spawnParticle(Particle.REDSTONE, e1, 2, 0,0.01,0.7,0.3,1);
						
						if(i % 4 == 0) {
							e3 = e1.clone().add(0,5,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.01,0.9,0.4,1);
							e3 = e1.clone().add(0,4.5,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.01,0.9,0.4,1);
							e3 = e1.clone().add(0,4,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.01,0.9,0.4,1);
							e3 = e1.clone().add(0,3.5,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.01,0.9,0.4,1);
							e3 = e1.clone().add(0,3,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.01,0.9,0.4,1);
							e3 = e1.clone().add(0,2.5,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.01,0.9,0.4,1);
							e3 = e1.clone().add(0,2,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.01,0.9,0.4,1);
							e3 = e1.clone().add(0,1.5,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.01,0.9,0.4,1);
							e3 = e1.clone().add(0,1,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.01,0.9,0.4,1);
							e3 = e1.clone().add(0,0.5,0);
							world.spawnParticle(Particle.REDSTONE, e3, 0,0.01,0.9,0.4,1);
						}
						
						var += Math.PI / 16;
					}
				}

				if(time >= 10) {
					this.cancel();
				}
				
				time++;
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	// 스톤가든
	public void newEffect7() {
		
		String myTeam = getTeam(player);
		
		for(Entity ent : player.getNearbyEntities(3, 3, 3)) {
			if(ent instanceof Player) {
				Player p = (Player) ent;
				String yourTeam = getTeam(p);
				
				if(!myTeam.equals(yourTeam)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 100));
					Location loc = p.getLocation();
					loc.setPitch(p.getLocation().getPitch() + 180);
					p.teleport(loc);
					break;
				}
			}
		}
	}

	// 알스트로메리아
	public void newEffect8() {
		World world = player.getWorld(); 
		
		world.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 3.0f, 1.0f);
        
		new BukkitRunnable() {
			int time = 0;
			int size = 0;
			
			Location normal = player.getLocation();
			
		    Location e1, e2, e3;

			@Override
			public void run() {
				
				if(time % 1 == 0) {
					double var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						if(size % 2 == 0) {
							e2 = normal.clone().add(Math.cos(var)*size, 0.5, Math.sin(var)*size);
						} else {
							e2 = normal.clone().add(Math.cos(var)*size, 0, Math.sin(var)*size);
						}
						world.spawnParticle(Particle.REDSTONE, e2, 2, 0,0.01,0.8,0.5,1);
						
						var += Math.PI / 16;
					}
					
					if(size == 5) {
						size = -1;
					}
					size++;
				}
				
				if(time % 3 == 0) {
					double var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						e1 = normal.clone().add(Math.cos(var)*6, 0, Math.sin(var)*6);
						world.spawnParticle(Particle.REDSTONE, e1, 2, 0,0.01,0.7,0.3,1);
						
						var += Math.PI / 16;
					}
					
					if(player.getHealth() >= 17) {
						player.setHealth(20);
					} else {
						player.setHealth(player.getHealth() + 3);
					}
					
					String myTeam = getTeam(player);
					
					for(Entity ent : player.getNearbyEntities(6, 6, 6)) {
						if(ent instanceof Player) {
							Player p = (Player) ent;
							String yourTeam = getTeam(p);
							
							if(myTeam.equals(yourTeam)) {
								if(p.getHealth() >= 17) {
									p.setHealth(20);
								} else {
									p.setHealth(p.getHealth() + 3);
								}
								break;
							}
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
	
	// 파이라
	public void newEffect9() {
		
		World world = player.getWorld();
		
		Arrow arrow = player.launchProjectile(Arrow.class);
		arrow.setShooter(player);
		arrow.setVelocity(player.getLocation().getDirection().multiply(2.0f));		
		world.spawnParticle(Particle.FLAME, arrow.getLocation(), 2);
		world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.5f, 1.0f);
		
		Item dItem = arrow.getWorld().dropItem(arrow.getLocation(), new ItemStack(Material.FIREBALL));
		dItem.setPickupDelay(10000000);
		arrow.addPassenger(dItem);
		
		Thread t = new Thread(player.getUniqueId());
		sleep = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
			
			@Override
			public void run() {
				if (!t.hasID()) {
					t.setID(sleep);
				}
			
				if(arrow.isDead()) {	
					String myTeam = getTeam(player);
					
					for(Entity ent : arrow.getNearbyEntities(3, 3, 3)) {
						if(ent instanceof Player) {
							Player p = (Player) ent;
							String yourTeam = getTeam(p);
							
							if(!myTeam.equals(yourTeam)) {
								if(p.getPotionEffect(PotionEffectType.SLOW).getDuration() != 0) {
									p.damage(6);
								} else {
									p.damage(3);
								}
								p.setFireTicks(100);
								break;
							}
						}
					}
					t.endTask();
					t.removeID();
				}
			}						
			
		}, 0, 1);
		
	}
	
	// 블리자드
	public void newEffect10() {
		
		Location normal = player.getLocation();
		World world = player.getWorld(); 
		
		world.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 3.0f, 1.0f);
		
		String myTeam = getTeam(player);
		
		for(Entity ent : player.getNearbyEntities(4, 4, 4)) {
			if(ent instanceof Player) {
				Player p = (Player) ent;
				String yourTeam = getTeam(p);
				
				if(!myTeam.equals(yourTeam)) {
					p.damage(2);
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 1));
				}
			}
		}
        
		new BukkitRunnable() {
			int time = 0;
			int size = 0;
			
		    Location e1, e2;

			@Override
			public void run() {
				
				if(time % 1 == 0) {
					double var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						if(size % 2 == 0) {
							e2 = normal.clone().add(Math.cos(var)*size, 0.5, Math.sin(var)*size);
						} else {
							e2 = normal.clone().add(Math.cos(var)*size, 0, Math.sin(var)*size);
						}
						world.spawnParticle(Particle.REDSTONE, e2, 0, 0.2,0.6,0.9,1);
						
						var += Math.PI / 16;
					}
					
					if(size == 3) {
						size = -1;
					}
					size++;
				}
				
				if(time % 3 == 0) {
					double var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						e1 = normal.clone().add(Math.cos(var)*4, 0, Math.sin(var)*4);
						world.spawnParticle(Particle.REDSTONE, e1, 0 ,0.1,0.4,0.8,1);
						
						var += Math.PI / 16;
					}
				}

				if(time >= 10) {
					this.cancel();
				}
				
				time++;
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
		
	}
	
	// 시프트 브레이크
	public void newEffect11() {
		
		World world = player.getWorld();

		world.playSound(player.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1.0f, 0.7f);

		String myTeam = getTeam(player);

		for (Entity ent : player.getNearbyEntities(4, 4, 4)) {
			if (ent instanceof Player) {
				Player p = (Player) ent;
				String yourTeam = getTeam(p);

				if (!myTeam.equals(yourTeam)) {
					Location loc = p.getLocation();
					loc.add(0,1,0);
					p.setVelocity(loc.getDirection().multiply(1.8f));
				}
			}
		}

	}

	// 썬더가
	public void newEffect12() {
		
		World world = player.getWorld();

		world.playSound(player.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1.0f, 0.7f);
		world.spawnEntity(player.getLocation(), EntityType.LIGHTNING);

		String myTeam = getTeam(player);

		for (Entity ent : player.getNearbyEntities(7, 7, 7)) {
			if (ent instanceof Player) {
				Player p = (Player) ent;
				String yourTeam = getTeam(p);

				if (!myTeam.equals(yourTeam)) {
					p.damage(10);
				}
			}
		}

	}
	
	// 워커메이커
	public void newEffect13() {
		
		Location normal = player.getLocation();
		World world = player.getWorld(); 
		
		world.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 1.0f, 1.5f);
		
		String myTeam = getTeam(player);
		
		for(Entity ent : player.getNearbyEntities(2, 2, 2)) {
			if(ent instanceof Player) {
				Player p = (Player) ent;
				String yourTeam = getTeam(p);
				
				if(!myTeam.equals(yourTeam)) {
					p.damage(8);
				}
			}
		}
        
		new BukkitRunnable() {
			int time = 0;
			int size = 0;
			
		    Location e1, e2;

			@Override
			public void run() {
				
				if(time % 1 == 0) {
					double var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						if(size % 2 == 0) {
							e2 = normal.clone().add(Math.cos(var)*size, 0.5, Math.sin(var)*size);
						} else {
							e2 = normal.clone().add(Math.cos(var)*size, 0, Math.sin(var)*size);
						}
						world.spawnParticle(Particle.REDSTONE, e2, 0, 0.4,0.4,0.4,1);
						
						var += Math.PI / 16;
					}
					
					if(size == 1) {
						size = -1;
					}
					size++;
				}
				
				if(time % 3 == 0) {
					double var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						e1 = normal.clone().add(Math.cos(var)*2, 0, Math.sin(var)*2);
						world.spawnParticle(Particle.REDSTONE, e1, 0 ,0.3,0.3,0.3,1);
						
						var += Math.PI / 16;
					}
				}

				if(time >= 10) {
					this.cancel();
				}
				
				time++;
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
		
	}
	
	// 인티미데이션
	public void newEffect14() {
		
		World world = player.getWorld(); 
		
		world.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 0.6f, 0.6f);
		
		String myTeam = getTeam(player);
		
		for(Entity ent : player.getNearbyEntities(1, 1, 1)) {
			if(ent instanceof Player) {
				Player p = (Player) ent;
				String yourTeam = getTeam(p);
				
				if(!myTeam.equals(yourTeam)) {
					p.damage(2);
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 1));
					p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 30, 1));
				}
			}
		}
        
	}
	
	// 러시다운
	public void newEffect15() {
		Vector vec = player.getEyeLocation().getDirection().multiply(1.7f);
		player.setVelocity(vec);
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 0.3f, 0.5f);
	}
	
	// 마다토르
	public void newEffect16() {
		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 2));
	}
	
	// 소환1
	public void newEffect17() {
		
		new BukkitRunnable() {
			int time = 0;
			
			Snowman ent;

			@Override
			public void run() {
				
				if(time == 0) {
					ent = (Snowman) player.getWorld().spawnEntity(player.getLocation(), EntityType.SNOWMAN);
					ent.setHealth(5);
					ent.setCustomName(ChatColor.WHITE + "아이스 골렘");
					ent.setCustomNameVisible(true);
					
					String myTeam = getTeam(player);
					
					for(Entity ent2 : player.getNearbyEntities(10, 10, 10)) {
						if(ent2 instanceof Player) {
							Player p = (Player) ent2;
							String yourTeam = getTeam(p);
							
							if(!myTeam.equals(yourTeam)) {
								ent.setTarget(p);
								break;
							}
						}
					}
				}
				
				if(time >= 400) {
					ent.setHealth(0);
					this.cancel();
				}
				
				time++;
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
		
	}
	
	// 소환2
	public void newEffect18() {
		
		new BukkitRunnable() {
			int time = 0;
			
			Wolf ent;

			@Override
			public void run() {
				
				if(time == 0) {
					ent = (Wolf) player.getWorld().spawnEntity(player.getLocation(), EntityType.WOLF);
					ent.setHealth(5);
					ent.setCustomName(ChatColor.WHITE + "울프 워리어");
					ent.setCustomNameVisible(true);
					
					String myTeam = getTeam(player);
					
					for(Entity ent2 : player.getNearbyEntities(10, 10, 10)) {
						if(ent2 instanceof Player) {
							Player p = (Player) ent2;
							String yourTeam = getTeam(p);
							
							if(!myTeam.equals(yourTeam)) {
								ent.setTarget(p);
								break;
							}
						}
					}
				}
				
				if(time >= 400) {
					ent.setHealth(0);
					this.cancel();
				}
				
				time++;
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
		
	}
	
	// 소환3
	public void newEffect19() {
		
		new BukkitRunnable() {
			int time = 0;
			
			Ocelot ent;
			
			@Override
			public void run() {
				
				if(time == 0) {
					ent = (Ocelot) player.getWorld().spawnEntity(player.getLocation(), EntityType.OCELOT);
					ent.setHealth(5);
					ent.setCatType(Type.BLACK_CAT);
					ent.setCustomName(ChatColor.WHITE + "캣시 위저드");
					ent.setCustomNameVisible(true);
					
					String myTeam = getTeam(player);
					
					for(Entity ent2 : ent.getNearbyEntities(2, 2, 2)) {
						if(ent2 instanceof Player) {
							Player p = (Player) ent2;
							String yourTeam = getTeam(p);
							
							if(myTeam.equals(yourTeam)) {
								ent.setTarget(p);
								break;
							}
						}
					}
				}
				
				if(time % 80 == 0) {
					String myTeam = getTeam(player);
					
					for(Entity ent2 : ent.getNearbyEntities(3, 3, 3)) {
						if(ent2 instanceof Player) {
							Player p = (Player) ent2;
							String yourTeam = getTeam(p);
							
							if(myTeam.equals(yourTeam)) {
								if(player.getHealth() >= 18) {
									player.setHealth(20);
								} else {
									player.setHealth(player.getHealth() + 2);
								}
								break;
							}
						}
					}
				}
				
				if(time >= 400) {
					ent.setHealth(0);
					this.cancel();
				}
				
				time++;
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
		
	}
	
	// 소환4
	public void newEffect20() {
		
		new BukkitRunnable() {
			int time = 0;
			
			IronGolem ent;
			
			@Override
			public void run() {
				
				if(time == 0) {
					ent = (IronGolem) player.getWorld().spawnEntity(player.getLocation(), EntityType.IRON_GOLEM);
					ent.setHealth(20);
					ent.setCustomName(ChatColor.WHITE + "팬텀 나이트");
					ent.setCustomNameVisible(true);
					
					String myTeam = getTeam(player);
					
					for(Entity ent2 : ent.getNearbyEntities(10, 10, 10)) {
						if(ent2 instanceof Player) {
							Player p = (Player) ent2;
							String yourTeam = getTeam(p);
							
							if(!myTeam.equals(yourTeam)) {
								ent.setTarget(p);
								break;
							}
						}
					}
				}
				
				if(time >= 400) {
					ent.setHealth(0);
					this.cancel();
				}
				
				time++;
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
		
	}
	
	// 익스플로더
	public void newEffect21() {
		World world = player.getWorld();
		
		Arrow arrow = player.launchProjectile(Arrow.class);
		arrow.setShooter(player);
		arrow.setVelocity(player.getLocation().getDirection().multiply(2.0f));		
		world.spawnParticle(Particle.FLAME, arrow.getLocation(), 2);
		world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.5f, 1.0f);
		
		Item dItem = arrow.getWorld().dropItem(arrow.getLocation(), new ItemStack(Material.CLAY_BALL));
		dItem.setPickupDelay(10000000);
		arrow.addPassenger(dItem);
		
		Thread t = new Thread(player.getUniqueId());
		sleep = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
			
			@Override
			public void run() {
				if (!t.hasID()) {
					t.setID(sleep);
				}
			
				if(arrow.isDead()) {	
					player.teleport(arrow.getLocation());
					t.endTask();
					t.removeID();
				}
			}						
			
		}, 0, 1);
	}
	
	// 퀵해머러시
	public void newEffect22() {
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
	}
	
	// 블러드팬
	public void newEffect23() {
		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
	}
	
	// 더블콤바인
	public void newEffect24() {
		World world = player.getWorld(); 
		
		world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 2.0f);
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400, 1));
		
		String myTeam = getTeam(player);
		
		for(Entity ent : player.getNearbyEntities(8, 8, 8)) {
			if(ent instanceof Player) {
				Player p = (Player) ent;
				String yourTeam = getTeam(p);
				
				if(!myTeam.equals(yourTeam)) {
					p.damage(2);
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400, 1));
				}
			}
		}
	}
	
	// 기간틱앵거 
	public void newEffect25() {
		World world = player.getWorld(); 
		
		world.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 2.0f, 0.6f);
		
		String myTeam = getTeam(player);
		
		for(Entity ent : player.getNearbyEntities(3, 3, 3)) {
			if(ent instanceof Player) {
				Player p = (Player) ent;
				String yourTeam = getTeam(p);
				
				if(!myTeam.equals(yourTeam)) {
					p.teleport(player);
				}
			}
		}
	}
	
	// 파워슬램
	public void newEffect26() {
		World world = player.getWorld(); 
		
		world.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 0.6f, 0.6f);
		
		String myTeam = getTeam(player);
		
		for(Entity ent : player.getNearbyEntities(4, 4, 4)) {
			if(ent instanceof Player) {
				Player p = (Player) ent;
				String yourTeam = getTeam(p);
				
				if(!myTeam.equals(yourTeam)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 1));
					p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 30, 1));
				}
			}
		}
	}
	
	// 아토믹버스터
	public void newEffect27() {
		World world = player.getWorld(); 
		
		if(player.getHealth() <= 5) {
			player.setHealth(0);
		} else {
			player.setHealth(player.getHealth() - 5);
		}
		
		world.playSound(player.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1.0f, 1.0f);
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 2));
	}
	
	// 크레이그혼
	public void newEffect28() {
		World world = player.getWorld(); 
		
		world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 0.5f);
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 1));
		
		String myTeam = getTeam(player);
		
		for(Entity ent : player.getNearbyEntities(5, 5, 5)) {
			if(ent instanceof Player) {
				Player p = (Player) ent;
				String yourTeam = getTeam(p);
				
				if(myTeam.equals(yourTeam)) {
					p.damage(2);
					p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 1));
				}
			}
		}
	}
	
	// 이글훅
	public void newEffect29() {
		
		Vector vec = player.getEyeLocation().getDirection().multiply(2.0f);
		player.setVelocity(vec);
		
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
						world.spawnParticle(Particle.REDSTONE, e1, 0,0.9,0.9,0.9,1);
						
						var += Math.PI / 32;
					}	
					
					var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						e1 = loc.clone().add(Math.cos(var+rot)*1.5, 1.3-(i*0.05), Math.sin(var+rot)*1.5);
						world.spawnParticle(Particle.REDSTONE, e1, 0,0.8,0.8,0.8,1);
						
						var += Math.PI / 32;
					}
					
					world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 2.0f);
					
					String myTeam = getTeam(player);
					
					for(Entity ent : player.getNearbyEntities(2, 2, 2)) {
						if(ent instanceof Player) {
							Player p = (Player) ent;
							String yourTeam = getTeam(p);
							
							if(!myTeam.equals(yourTeam)) {
								p.damage(5);
							}
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
	
	// 플리커
	public void newEffect30() {
		
		Vector vec = player.getEyeLocation().getDirection().multiply(-1.6f);
		player.setVelocity(vec);
		
		World world = player.getWorld(); 
		
		world.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 2.0f, 0.6f);
		
		String myTeam = getTeam(player);
		
		for(Entity ent : player.getNearbyEntities(3, 3, 3)) {
			if(ent instanceof Player) {
				Player p = (Player) ent;
				String yourTeam = getTeam(p);
				
				if(!myTeam.equals(yourTeam)) {
					p.teleport(player);
				}
			}
		}
	}
	
	// 소닉팡
	public void newEffect31() {
		
		World world = player.getWorld(); 
		
		world.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 4.0f);
		
		String myTeam = getTeam(player);
		
		for(Entity ent : player.getNearbyEntities(2, 2, 2)) {
			if(ent instanceof Player) {
				Player p = (Player) ent;
				String yourTeam = getTeam(p);
				
				if(!myTeam.equals(yourTeam)) {
					p.damage(5);
					p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, 0));
				}
			}
		}
	}
	
	// 파워 크래시
	public void newEffect32() {
		player.setNoDamageTicks(40);
		player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 40, 0));
	}
	
	// 포이즈너
	public void newEffect33() {
		
		World world = player.getWorld();
		Location loc = player.getLocation();
		
		Arrow arrow = player.launchProjectile(Arrow.class);
		arrow.setShooter(player);
		arrow.setCustomName("독");
		arrow.setVelocity(player.getEyeLocation().getDirection().multiply(3.0f));
		player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc.add(0,1,0), 0);			
		world.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0f, 1.0f);
		
		SpectralArrow sarrow = (SpectralArrow) arrow.getWorld().spawnEntity(player.getEyeLocation(), EntityType.SPECTRAL_ARROW);
		sarrow.setVelocity(arrow.getVelocity());
		
	}
	
	// 에로우 실드
	public void newEffect34() {
		
		World world = player.getWorld();
		
		world.playSound(player.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1.0f, 1.0f);
		Thread t = new Thread(player.getUniqueId());
		sleep = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {

			int time = 0;
			double var = 0;
			Location loc, first, second;
			
			@Override
			public void run() {
				if (!t.hasID()) {
					t.setID(sleep);
				}
			
				if(time>=200) {								
					t.endTask();
					t.removeID();
				} 

				String myTeam = getTeam(player);
				
				for(Entity ent : player.getNearbyEntities(2, 2, 2)) {
					if(ent instanceof Player) {
						Player p = (Player) ent;
						String yourTeam = getTeam(p);
						
						if(!myTeam.equals(yourTeam)) {
							p.damage(1);
							p.setVelocity(new Vector(0.3,1,0.3));
							time += 49;
						}
					}
				}
			
				time++;
				
				var += Math.PI / 16;
				loc = player.getLocation();
				first = loc.clone().add(Math.cos(var), Math.sin(var) + 1, Math.sin(var));
				second = loc.clone().add(Math.cos(var + Math.PI), Math.sin(var) + 1, Math.sin(var + Math.PI));
				
				
				world.spawnParticle(Particle.TOTEM, first, 0);
				world.spawnParticle(Particle.TOTEM, second, 0);
			}						
			
		}, 0, 1);
	}
	
	// 저지먼트 스크류
	public void newEffect35() {
		
		World world = player.getWorld();
		Location loc = player.getLocation();
		
		Arrow arrow = player.launchProjectile(Arrow.class);
		arrow.setShooter(player);
		arrow.setCustomName("저지");
		arrow.setVelocity(player.getEyeLocation().getDirection().multiply(3.0f));
		player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc.add(0,1,0), 0);			
		world.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0f, 1.0f);
		
		SpectralArrow sarrow = (SpectralArrow) arrow.getWorld().spawnEntity(player.getEyeLocation(), EntityType.SPECTRAL_ARROW);
		sarrow.setVelocity(arrow.getVelocity());
		
	}
	
	// 밤 에로우
	public void newEffect36() {
		
		World world = player.getWorld();
		Location loc = player.getLocation();
		
		Arrow arrow = player.launchProjectile(Arrow.class);
		arrow.setShooter(player);
		arrow.setVelocity(player.getEyeLocation().getDirection().multiply(3.0f));
		player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc.add(0,1,0), 0);			
		world.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0f, 1.0f);
		
		SpectralArrow sarrow = (SpectralArrow) arrow.getWorld().spawnEntity(player.getEyeLocation(), EntityType.SPECTRAL_ARROW);
		sarrow.setVelocity(arrow.getVelocity());
		
		Thread t = new Thread(player.getUniqueId());
		sleep = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
			
			@Override
			public void run() {
				if (!t.hasID()) {
					t.setID(sleep);
				}
			
				if(arrow.isDead()) {	
					String myTeam = getTeam(player);
					
					for(Entity ent : arrow.getNearbyEntities(4, 4, 4)) {
						if(ent instanceof Player) {
							Player p = (Player) ent;
							String yourTeam = getTeam(p);
							
							if(!myTeam.equals(yourTeam)) {
								p.damage(8);
								break;
							}
						}
					}
					t.endTask();
					t.removeID();
				}
			}						
			
		}, 0, 1);
		
	}
	
	// 스타브에리어
	public void newEffect37() {

		World world = player.getWorld(); 
		
		world.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 3.0f, 1.0f);
		
		new BukkitRunnable() {
			int time = 0;
			int size = 0;
			
			Location normal = player.getLocation();
			
		    Location e1, e2;

			@Override
			public void run() {
				
				if(time % 1 == 0) {
					double var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						if(size % 2 == 0) {
							e2 = normal.clone().add(Math.cos(var)*size, 0.5, Math.sin(var)*size);
						} else {
							e2 = normal.clone().add(Math.cos(var)*size, 0, Math.sin(var)*size);
						}
						world.spawnParticle(Particle.REDSTONE, e2, 0, 0.4,0.01,0.4,1);
						
						var += Math.PI / 16;
					}
					
					if(size == 3) {
						size = -1;
					}
					size++;
				}
				
				if(time % 8 == 0) {
					double var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						e1 = normal.clone().add(Math.cos(var)*4, 0, Math.sin(var)*4);
						world.spawnParticle(Particle.REDSTONE, e1, 0 ,0.3,0.01,0.3,1);
						
						var += Math.PI / 16;
					}
					
					String myTeam = getTeam(player);
					
					for(Entity ent : player.getNearbyEntities(4, 4, 4)) {
						if(ent instanceof Player) {
							Player p = (Player) ent;
							String yourTeam = getTeam(p);
							
							if(!myTeam.equals(yourTeam)) {
								p.damage(3);
							}
						}
					}
				}

				if(time >= 40) {
					this.cancel();
				}
				
				time++;
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	// 아이스본
	public void newEffect38() {
		
		World world = player.getWorld();
		Location loc = player.getLocation();
		
		Arrow arrow = player.launchProjectile(Arrow.class);
		arrow.setShooter(player);
		arrow.setCustomName("아이스");
		arrow.setVelocity(player.getEyeLocation().getDirection().multiply(3.0f));
		player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc.add(0,1,0), 0);			
		world.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0f, 1.0f);
		
		SpectralArrow sarrow = (SpectralArrow) arrow.getWorld().spawnEntity(player.getEyeLocation(), EntityType.SPECTRAL_ARROW);
		sarrow.setVelocity(arrow.getVelocity());
		
	}
	
	// 리벨리온윙
	public void newEffect39() {
		
		Vector vec = player.getEyeLocation().getDirection().multiply(2.0f);
		player.setVelocity(vec);
		
		Location loc = player.getLocation();
		World world = player.getWorld(); 
		
		world.playSound(loc, Sound.ENTITY_ENDERDRAGON_FLAP, 1.0f, 1.8f);
        
		new BukkitRunnable() {
			int time = 0;
			
		    Location e1;

			@Override
			public void run() {
				
				Location normal = player.getEyeLocation();
				
				if(time % 4 == 0) {
					double var = 0;
					
					for(int i = 0 ; i < 16 ; i++) {
						e1 = loc.clone().add(Math.cos(var), 0.3, Math.sin(var));
						world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
						
						var += Math.PI / 8;
					}
					
					for(int i = 0 ; i < 16 ; i++) {
						e1 = loc.clone().add(Math.cos(var)*1.5, 0.6, Math.sin(var)*1.5);
						world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
						
						var += Math.PI / 8;
					}
				}
				
				if(time % 3 == 0) {
					
					double arrowAngle1 = -80;
					double totalAngle1 = normal.getYaw() + arrowAngle1;
					double dirX1 = Math.cos(Math.toRadians(totalAngle1));
					double dirZ1 = Math.sin(Math.toRadians(totalAngle1));
					
					e1 = normal.clone().add(dirX1*0.1, -0.3, dirZ1*0.1);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle1 = -60;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));
					
					e1 = normal.clone().add(dirX1*0.2, -0.25, dirZ1*0.2);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle1 = -40;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));
					
					e1 = normal.clone().add(dirX1*0.4, -0.2, dirZ1*0.4);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle1 = -32;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));
					
					e1 = normal.clone().add(dirX1*0.65, -0.1, dirZ1*0.65);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle1 = -28;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));
					
					e1 = normal.clone().add(dirX1*0.9, 0, dirZ1*0.9);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle1 = -28;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));
					
					e1 = normal.clone().add(dirX1*1.1, 0.03, dirZ1*1.1);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle1 = -28;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));
					
					e1 = normal.clone().add(dirX1*1.3, 0.06, dirZ1*1.3);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle1 = -28;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));
					
					e1 = normal.clone().add(dirX1*1.5, 0.09, dirZ1*1.5);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle1 = -28;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));
					
					e1 = normal.clone().add(dirX1*1.7, 0.12, dirZ1*1.7);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle1 = -28;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));
					
					e1 = normal.clone().add(dirX1*1.9, 0.15, dirZ1*1.9);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle1 = -28;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));
					
					e1 = normal.clone().add(dirX1*2.1, 0.18, dirZ1*2.1);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle1 = -28;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));
					
					e1 = normal.clone().add(dirX1*2.0, 0.13, dirZ1*2.0);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle1 = -28;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));
					
					e1 = normal.clone().add(dirX1*1.9, 0.08, dirZ1*1.9);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);

					arrowAngle1 = -28;
					totalAngle1 = normal.getYaw() + arrowAngle1;
					dirX1 = Math.cos(Math.toRadians(totalAngle1));
					dirZ1 = Math.sin(Math.toRadians(totalAngle1));
					
					e1 = normal.clone().add(dirX1*1.8, 0.03, dirZ1*1.8);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					for(int i = 0 ; i < 16 ; i++) {
						arrowAngle1 = -28;
						totalAngle1 = normal.getYaw() + arrowAngle1;
						dirX1 = Math.cos(Math.toRadians(totalAngle1));
						dirZ1 = Math.sin(Math.toRadians(totalAngle1));
						
						e1 = normal.clone().add(dirX1*(2.0-(i*0.1)), (-0.13-(i*0.01)), dirZ1*(2.0-(i*0.1)));
						world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					}
					
					for(int i = 0 ; i < 14 ; i++) {
						arrowAngle1 = -28;
						totalAngle1 = normal.getYaw() + arrowAngle1;
						dirX1 = Math.cos(Math.toRadians(totalAngle1));
						dirZ1 = Math.sin(Math.toRadians(totalAngle1));
						
						e1 = normal.clone().add(dirX1*(1.6-(i*0.1)), (-0.4-(i*0.01)), dirZ1*(1.6-(i*0.1)));
						world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					}
					
					for(int i = 0 ; i < 10 ; i++) {
						arrowAngle1 = -28;
						totalAngle1 = normal.getYaw() + arrowAngle1;
						dirX1 = Math.cos(Math.toRadians(totalAngle1));
						dirZ1 = Math.sin(Math.toRadians(totalAngle1));
						
						e1 = normal.clone().add(dirX1*(1-(i*0.1)), (-0.65+(i*0.02)), dirZ1*(1-(i*0.1)));
						world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					}
					
					double arrowAngle3 = 260;
					double totalAngle3 = normal.getYaw() + arrowAngle3;
					double dirX3 = Math.cos(Math.toRadians(totalAngle3));
					double dirZ3 = Math.sin(Math.toRadians(totalAngle3));
					
					e1 = normal.clone().add(dirX3*0.1, -0.3, dirZ3*0.1);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle3 = 240;
					totalAngle3 = normal.getYaw() + arrowAngle3;
					dirX3 = Math.cos(Math.toRadians(totalAngle3));
					dirZ3 = Math.sin(Math.toRadians(totalAngle3));
					
					e1 = normal.clone().add(dirX3*0.2, -0.25, dirZ3*0.2);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle3 = 220;
					totalAngle3 = normal.getYaw() + arrowAngle3;
					dirX3 = Math.cos(Math.toRadians(totalAngle3));
					dirZ3 = Math.sin(Math.toRadians(totalAngle3));
					
					e1 = normal.clone().add(dirX3*0.4, -0.2, dirZ3*0.4);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle3 = 212;
					totalAngle3 = normal.getYaw() + arrowAngle3;
					dirX3 = Math.cos(Math.toRadians(totalAngle3));
					dirZ3 = Math.sin(Math.toRadians(totalAngle3));
					
					e1 = normal.clone().add(dirX3*0.65, -0.1, dirZ3*0.65);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle3 = 212;
					totalAngle3 = normal.getYaw() + arrowAngle3;
					dirX3 = Math.cos(Math.toRadians(totalAngle3));
					dirZ3 = Math.sin(Math.toRadians(totalAngle3));
					
					e1 = normal.clone().add(dirX3*0.9, 0, dirZ3*0.9);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle3 = 212;
					totalAngle3 = normal.getYaw() + arrowAngle3;
					dirX3 = Math.cos(Math.toRadians(totalAngle3));
					dirZ3 = Math.sin(Math.toRadians(totalAngle3));
					
					e1 = normal.clone().add(dirX3*1.1, 0.03, dirZ3*1.1);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle3 = 212;
					totalAngle3 = normal.getYaw() + arrowAngle3;
					dirX3 = Math.cos(Math.toRadians(totalAngle3));
					dirZ3 = Math.sin(Math.toRadians(totalAngle3));
					
					e1 = normal.clone().add(dirX3*1.3, 0.06, dirZ3*1.3);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle3 = 212;
					totalAngle3 = normal.getYaw() + arrowAngle3;
					dirX3 = Math.cos(Math.toRadians(totalAngle3));
					dirZ3 = Math.sin(Math.toRadians(totalAngle3));
					
					e1 = normal.clone().add(dirX3*1.5, 0.09, dirZ3*1.5);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle3 = 212;
					totalAngle3 = normal.getYaw() + arrowAngle3;
					dirX3 = Math.cos(Math.toRadians(totalAngle3));
					dirZ3 = Math.sin(Math.toRadians(totalAngle3));
					
					e1 = normal.clone().add(dirX3*1.7, 0.12, dirZ3*1.7);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle3 = 212;
					totalAngle3 = normal.getYaw() + arrowAngle3;
					dirX3 = Math.cos(Math.toRadians(totalAngle3));
					dirZ3 = Math.sin(Math.toRadians(totalAngle3));
					
					e1 = normal.clone().add(dirX3*1.9, 0.15, dirZ3*1.9);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle3 = 212;
					totalAngle3 = normal.getYaw() + arrowAngle3;
					dirX3 = Math.cos(Math.toRadians(totalAngle3));
					dirZ3 = Math.sin(Math.toRadians(totalAngle3));
					
					e1 = normal.clone().add(dirX3*2.1, 0.18, dirZ3*2.1);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle3 = 212;
					totalAngle3 = normal.getYaw() + arrowAngle3;
					dirX3 = Math.cos(Math.toRadians(totalAngle3));
					dirZ3 = Math.sin(Math.toRadians(totalAngle3));
					
					e1 = normal.clone().add(dirX3*2.0, 0.13, dirZ3*2.0);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					arrowAngle3 = 212;
					totalAngle3 = normal.getYaw() + arrowAngle3;
					dirX3 = Math.cos(Math.toRadians(totalAngle3));
					dirZ3 = Math.sin(Math.toRadians(totalAngle3));
					
					e1 = normal.clone().add(dirX3*1.9, 0.08, dirZ3*1.9);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);

					arrowAngle3 = 212;
					totalAngle3 = normal.getYaw() + arrowAngle3;
					dirX3 = Math.cos(Math.toRadians(totalAngle3));
					dirZ3 = Math.sin(Math.toRadians(totalAngle3));
					
					e1 = normal.clone().add(dirX3*1.8, 0.03, dirZ3*1.8);
					world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					
					for(int i = 0 ; i < 16 ; i++) {
						arrowAngle3 = 212;
						totalAngle3 = normal.getYaw() + arrowAngle3;
						dirX3 = Math.cos(Math.toRadians(totalAngle3));
						dirZ3 = Math.sin(Math.toRadians(totalAngle3));
						
						e1 = normal.clone().add(dirX3*(2.0-(i*0.1)), (-0.13-(i*0.01)), dirZ3*(2.0-(i*0.1)));
						world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					}
					
					for(int i = 0 ; i < 14 ; i++) {
						arrowAngle3 = 212;
						totalAngle3 = normal.getYaw() + arrowAngle3;
						dirX3 = Math.cos(Math.toRadians(totalAngle3));
						dirZ3 = Math.sin(Math.toRadians(totalAngle3));
						
						e1 = normal.clone().add(dirX3*(1.6-(i*0.1)), (-0.4-(i*0.01)), dirZ3*(1.6-(i*0.1)));
						world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					}
					
					for(int i = 0 ; i < 10 ; i++) {
						arrowAngle3 = 212;
						totalAngle3 = normal.getYaw() + arrowAngle3;
						dirX3 = Math.cos(Math.toRadians(totalAngle3));
						dirZ3 = Math.sin(Math.toRadians(totalAngle3));
						
						e1 = normal.clone().add(dirX3*(1-(i*0.1)), (-0.65+(i*0.02)), dirZ3*(1-(i*0.1)));
						world.spawnParticle(Particle.REDSTONE, e1, 0, 0.01,0.01,0.01,1);
					}
					
				}

				if(time >= 20) {
					this.cancel();
				}
				
				time++;
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
		
	}
	
	// 패왕룡 즈아크
	public void newEffect40() {

		World world = player.getWorld(); 
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 150, 0));
		player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 150, 0));
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 150, 100));
		player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 150, 200));
		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 150, 3));
		
		world.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 3.0f, 1.0f);
		
		new BukkitRunnable() {
			int time = 0;
			int size = 0;
			
			Location normal = player.getLocation();
			
		    Location e1, e2;

			@Override
			public void run() {
				
				if(time % 1 == 0) {
					double var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						if(size % 2 == 0) {
							e2 = normal.clone().add(Math.cos(var)*size, 0.5, Math.sin(var)*size);
						} else {
							e2 = normal.clone().add(Math.cos(var)*size, 0, Math.sin(var)*size);
						}
						world.spawnParticle(Particle.REDSTONE, e2, 0, 0.7,0.7,0.1,1);
						
						var += Math.PI / 16;
					}
					
					if(size == 4) {
						size = -1;
					}
					size++;
				}
				
				if(time % 8 == 0) {
					double var = 0;
					
					for(int i = 0 ; i < 32 ; i++) {
						e1 = normal.clone().add(Math.cos(var)*5, 0, Math.sin(var)*5);
						world.spawnParticle(Particle.REDSTONE, e1, 0 ,0.8,0.8,0.1,1);
						
						var += Math.PI / 16;
					}
					
					String myTeam = getTeam(player);
					
					for(Entity ent : player.getNearbyEntities(4, 4, 4)) {
						if(ent instanceof Player) {
							Player p = (Player) ent;
							String yourTeam = getTeam(p);
							
							if(!myTeam.equals(yourTeam)) {
								p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10, 1));
								p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10, 0));
							}
						}
					}
				}
				
				if(time >= 140) {
					this.cancel();
				}
				
				time++;
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	public String getTeam(Player player) {
		board = new BoardManager().getBoard();
		Team red = new BoardManager().getRed();
		if(red.getPlayers().contains(player)) {
			return "red";
		} else {
			return "blue";
		}
	}
	
}
