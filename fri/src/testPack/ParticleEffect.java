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
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

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
						world.spawnParticle(Particle.REDSTONE, e2, 2, 0,0.4,0.01,0.4,1);
						
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
						world.spawnParticle(Particle.REDSTONE, e1, 2, 0,0.3,0.01,0.3,1);
						
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
					break;
				}
			}
		}
		
		if(player.getHealth() >= 15) {
			player.setHealth(20);
		} else {
			player.setHealth(player.getHealth() + 5);
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
				
				if(myTeam.equals(yourTeam)) {
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
	
	public String getTeam(Player player) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		
		Team red = board.getEntryTeam("red");
		
		if(red.getPlayers().contains(player)) {
			return "red";
		} else {
			return "blue";
		}
	}
	
}
