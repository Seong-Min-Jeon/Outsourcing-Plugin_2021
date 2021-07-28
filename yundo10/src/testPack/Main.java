package testPack;

import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.CropState;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.Button;
import org.bukkit.material.Crops;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Main extends JavaPlugin implements Listener{
	
//	HashMap<HashMap<Location, Integer>, Location> map = new HashMap<>();
	HashMap<Location, String> door = new HashMap<>();
	HashMap<Player, Integer> move = new HashMap<>();
	Random rnd = new Random();
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		
		getCommand("giveToken").setExecutor(new Cmd1());
		getCommand("door").setExecutor(new Cmd2());
		getCommand("증표").setExecutor(new Cmd3());
		
		new BukkitRunnable() {
			int time = 0;
			double var = 0;
			Location loc, first, second;

			@Override
			public void run() {
				
				if(time % 20 == 0) {
					new CoolTime().countTime();
					try {
						loadData(Bukkit.getWorld("world"));
					} catch(Exception e) {
						
					}
				}
				
				try {
					for(Player player : Bukkit.getOnlinePlayers()) {
						if(player.getInventory().getItem(34).getItemMeta().getLocalizedName().equals("1")) {
							player.setWalkSpeed(0.35f);
							player.getWorld().spawnParticle(Particle.CRIT, player.getLocation().add(0, 0.3, 0), 0);
						} else if(player.getInventory().getItem(34).getItemMeta().getLocalizedName().equals("2")) {
							player.setWalkSpeed(0.4f);
							player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().add(0, 0.3, 0), 0);
							player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().add(1, 0.3, 0), 0);
							player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().add(-1, 0.3, 0), 0);
							player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().add(1, 0.3, 1), 0);
							player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().add(-1, 0.3, 1), 0);
							player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().add(1, 0.3, -1), 0);
							player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().add(-1, 0.3, -1), 0);
							player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().add(0, 0.3, 1), 0);
							player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().add(0, 0.3, -1), 0);
						} else if(player.getInventory().getItem(34).getItemMeta().getLocalizedName().equals("3")) {
							player.setWalkSpeed(0.45f);
							
							player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().add(0, 0.3, 0), 0);
							player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().add(1, 0.3, 0), 0);
							player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().add(-1, 0.3, 0), 0);
							player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().add(1, 0.3, 1), 0);
							player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().add(-1, 0.3, 1), 0);
							player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().add(1, 0.3, -1), 0);
							player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().add(-1, 0.3, -1), 0);
							player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().add(0, 0.3, 1), 0);
							player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().add(0, 0.3, -1), 0);
							
							var += Math.PI / 16;

							loc = player.getLocation();
							first = loc.clone().add(Math.cos(var) * 0.5, Math.sin(var) + 1, Math.sin(var) * 0.5);
							second = loc.clone().add(Math.cos(var + Math.PI) * 0.5, Math.sin(var) + 1, Math.sin(var + Math.PI) * 0.5);

							player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, first, 0);
							player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, second, 0);
						} else {
							player.setWalkSpeed(0.3f);
						}
					}
				} catch(Exception e) {
					
				}
				
				time++;
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
		
		new Cmd2().setFolder(getDataFolder());
	}
	
	@Override
	public void onDisable() {
		getLogger().info(" ");
	}
	 
	@EventHandler
	public void joinEvent(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		try {
			if(player.getInventory().getItem(34).getItemMeta().getLocalizedName().equals("1")) {
				event.setJoinMessage(ChatColor.GOLD + "[1성 귀족] " + ChatColor.WHITE + "" + player.getDisplayName() + "님이 접속하셨습니다.");
			} else if(player.getInventory().getItem(34).getItemMeta().getLocalizedName().equals("2")) {
				event.setJoinMessage(ChatColor.GOLD + "[2성 귀족] " + ChatColor.WHITE + "" + player.getDisplayName() + "님이 접속하셨습니다.");
			} else if(player.getInventory().getItem(34).getItemMeta().getLocalizedName().equals("3")) {
				event.setJoinMessage(ChatColor.GOLD + "[3성 귀족] " + ChatColor.WHITE + "" + player.getDisplayName() + "님이 접속하셨습니다.");
			}
		} catch(Exception e) {
			
		}
	}
	
	@EventHandler
	public void spawnEvent(EntitySpawnEvent event) {
		if(event.getEntityType() == EntityType.VILLAGER) {
			LivingEntity ent = (LivingEntity) event.getEntity();
			ent.damage(2000);
		}
	}
	
	@EventHandler
	public void damageEvent(EntityDamageByEntityEvent event) {
		try {
			Entity damager = event.getDamager();
			
			if(damager instanceof Player) {
				Player player = (Player) damager;
				if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("13")) {
					event.setDamage(event.getDamage() * 1.5);
				} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("14")) {
					event.setDamage(event.getDamage() * 1.5);
				} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("15")) {
					event.setDamage(event.getDamage() * 1.5);
				} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("16")) {
					event.setDamage(event.getDamage() * 1.5);
				} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("17")) {
					event.setDamage(event.getDamage() * 1.5);
				} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("19")) {
					if(!(event.getEntity() instanceof Player)) {
						event.setDamage(event.getDamage() * 0.5);
					}
				} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("20")) {
					if(!(event.getEntity() instanceof Player)) {
						event.setDamage(event.getDamage() * 0.5);
					}
				}
			}
			
			if(damager instanceof Arrow) {
				Arrow arrow = (Arrow) damager;
				if(arrow.getShooter() instanceof Player) {
					Player player = (Player) arrow.getShooter();
					if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("13")) {
						event.setDamage(event.getDamage() * 1.5);
					} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("14")) {
						event.setDamage(event.getDamage() * 1.5);
					} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("15")) {
						event.setDamage(event.getDamage() * 1.5);
					} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("16")) {
						event.setDamage(event.getDamage() * 1.5);
					} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("17")) {
						event.setDamage(event.getDamage() * 1.5);
					} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("19")) {
						if(!(event.getEntity() instanceof Player)) {
							event.setDamage(event.getDamage() * 0.5);
						}
					} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("20")) {
						if(!(event.getEntity() instanceof Player)) {
							event.setDamage(event.getDamage() * 0.5);
						}
					}
				}
			}
			
			if(damager instanceof Player) {
				Player player = (Player) damager;
				if(new PowerUp().containPlayer(player)) {
					event.setDamage(event.getDamage() * 2);
				} 
			}
			
			if(damager instanceof Arrow) {
				Arrow arrow = (Arrow) damager;
				if(arrow.getShooter() instanceof Player) {
					Player player = (Player) arrow.getShooter();
					if(new PowerUp().containPlayer(player)) {
						event.setDamage(event.getDamage() * 2);
					} 
				}
			}
		} catch(Exception e) {
			
		}
	}
	
	@EventHandler
	public void damage2Event(EntityDamageEvent event) {
		try {
			Entity entity = event.getEntity();
			if(entity instanceof Player) {
				Player player = (Player) entity;
				for(Entity ent : player.getNearbyEntities(20, 30, 20)) {
					if(ent.getName().equals("Fire Dragon")) {
						event.setDamage(event.getDamage() * 2);
					} else if(ent.getName().equals("Ice Dragon")) {
						event.setDamage(event.getDamage() * 2);
					}
				}
			}
		} catch(Exception e) {
			
		}
		
		try {
			Entity entity = event.getEntity();
			if(entity.getName().equals("Fire Dragon")) {
				event.setDamage(event.getDamage() * 0.5);
			} else if(entity.getName().equals("Ice Dragon")) {
				event.setDamage(event.getDamage() * 0.5);
			}
		} catch(Exception e) {
			
		}
	}
	
	@EventHandler
	public void mouseEvent(PlayerInteractEvent event) {
		try {
			EquipmentSlot e = event.getHand();
			if (e.equals(EquipmentSlot.HAND)) {
				Player player = event.getPlayer();
				
				try {
					if(player.getInventory().getItemInMainHand().getType() == Material.getMaterial("ICEANDFIRE_FISHING_SPEAR")) {
						event.setCancelled(true);
					}
					
					if(player.getInventory().getItemInOffHand().getType() == Material.getMaterial("ICEANDFIRE_FISHING_SPEAR")) {
						event.setCancelled(true);
					}
					
					if(player.getInventory().getItemInMainHand().getType() == Material.POTION) {
						PotionMeta pm = (PotionMeta) player.getInventory().getItemInMainHand().getItemMeta();
						if(pm.getBasePotionData().getType() == PotionType.FIRE_RESISTANCE) {
							event.setCancelled(true);
						}
					}
					
					if(player.getInventory().getItemInOffHand().getType() == Material.POTION) {
						PotionMeta pm = (PotionMeta) player.getInventory().getItemInOffHand().getItemMeta();
						if(pm.getBasePotionData().getType() == PotionType.FIRE_RESISTANCE) {
							event.setCancelled(true);
						}
					}
				} catch(Exception e2) {
					
				}
				
				if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

					try {
						if(event.getClickedBlock().getType() == Material.BLACK_SHULKER_BOX || event.getClickedBlock().getType() == Material.BLUE_SHULKER_BOX
								 || event.getClickedBlock().getType() == Material.BROWN_SHULKER_BOX || event.getClickedBlock().getType() == Material.CYAN_SHULKER_BOX
								 || event.getClickedBlock().getType() == Material.GRAY_SHULKER_BOX || event.getClickedBlock().getType() == Material.GREEN_SHULKER_BOX
								 || event.getClickedBlock().getType() == Material.LIGHT_BLUE_SHULKER_BOX || event.getClickedBlock().getType() == Material.LIME_SHULKER_BOX
								 || event.getClickedBlock().getType() == Material.MAGENTA_SHULKER_BOX || event.getClickedBlock().getType() == Material.ORANGE_SHULKER_BOX
								 || event.getClickedBlock().getType() == Material.PINK_SHULKER_BOX || event.getClickedBlock().getType() == Material.PURPLE_SHULKER_BOX
								 || event.getClickedBlock().getType() == Material.RED_SHULKER_BOX || event.getClickedBlock().getType() == Material.SILVER_SHULKER_BOX
								 || event.getClickedBlock().getType() == Material.WHITE_SHULKER_BOX || event.getClickedBlock().getType() == Material.YELLOW_SHULKER_BOX) {
							event.setCancelled(true);
						}
					} catch(Exception e1) {
						
					}
				} else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
					
					if (player.getInventory().contains(Material.NETHER_STAR)) {
						if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("11")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "R");
							} else {
								mc.click(player, "R", time);
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("12")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "R");
							} else {
								mc.click(player, "R", time);
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("14")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "R");
							} else {
								mc.click(player, "R", time);
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("15")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "R");
							} else {
								mc.click(player, "R", time);
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("16")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "R");
							} else {
								mc.click(player, "R", time);
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("17")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "R");
							} else {
								mc.click(player, "R", time);
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("21")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "R");
							} else {
								mc.click(player, "R", time);
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("22")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "R");
							} else {
								mc.click(player, "R", time);
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("23")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "R");
							} else {
								mc.click(player, "R", time);
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("100")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "R");
							} else {
								mc.click(player, "R", time);
							}
						} 
						
					}
				} else if(event.getAction() == Action.LEFT_CLICK_AIR) {
					if (player.getInventory().contains(Material.NETHER_STAR)) {
						if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("11")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "L");
							} else {
								mc.click(player, "L", time);
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("12")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "L");
							} else {
								mc.click(player, "L", time);
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("14")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "L");
							} else {
								mc.click(player, "L", time);
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("15")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "L");
							} else {
								mc.click(player, "L", time);
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("16")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "L");
							} else {
								mc.click(player, "L", time);
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("17")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "L");
							} else {
								mc.click(player, "L", time);
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("21")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "L");
							} else {
								mc.click(player, "L", time);
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("22")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "L");
							} else {
								mc.click(player, "L", time);
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("23")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "L");
							} else {
								mc.click(player, "L", time);
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("100")) {
							MouseClickForSkill mc = new MouseClickForSkill();
							int time = mc.getTime(player);
							if (time == 0) {
								mc.click(player, "L");
							} else {
								mc.click(player, "L", time);
							}
						} 
						
					}
				}
			}
		} catch(Exception e) {
			
		}
	}
	
	@EventHandler
	public void animationEvent(PlayerAnimationEvent event) {
//		Player player = event.getPlayer();
//		Inventory inv = player.getInventory();
//		if (inv.contains(Material.NETHER_STAR)) {
//			MouseClickForSkill mc = new MouseClickForSkill();
//			int time = mc.getTime(player);
//			if (time == 0) {
//				mc.click(player, "L");
//			} else {
//				mc.click(player, "L", time);
//			}
//		}
	}
	
	@EventHandler
	public void dropEvent(PlayerDropItemEvent event) {
//		System.out.println(event.getItemDrop().getItemStack().getType());
		if(event.getItemDrop().getItemStack().getType() == Material.NETHER_STAR) {
			event.setCancelled(true);
		}
		
		try {
			if(event.getItemDrop().getItemStack().getType() == Material.BARRIER) {
				if(event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.RED + " "))
					event.setCancelled(true);
			}
		} catch(Exception e) {
			
		}
	}
	
	@EventHandler
	public void clickInv(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		
		try {
			if(event.getCurrentItem().getType() == Material.BARRIER) {
				if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + " "))
					event.setCancelled(true);
			}
			if(event.getCursor().getType() == Material.BARRIER) {
				if(event.getCursor().getItemMeta().getDisplayName().equals(ChatColor.RED + " "))
					event.setCancelled(true);
			}
		} catch(Exception e) {
			
		}
		
		try {
			
			if(event.getCurrentItem().getType() == Material.NETHER_STAR) {
				event.setCancelled(true);
			}
			if(event.getCursor().getType() == Material.NETHER_STAR) {
				event.setCancelled(true);
			}
			if(event.getClick() != ClickType.MIDDLE) {
				// 핫키 제한
				try {
					if(event.getHotbarButton() != -1) {
						event.setCancelled(true);
					}
				} catch(Exception e) {
					
				}
			}
			
			if (player.getInventory().contains(Material.ELYTRA)) {
				for (ItemStack is : player.getInventory().getContents()) {
					if (is == null)
						continue;
					if (is.getType() == Material.ELYTRA) {
						is.setAmount(0);
					}
				}
			}
			if (player.getInventory().contains(Material.BEACON)) {
				for (ItemStack is : player.getInventory().getContents()) {
					if (is == null)
						continue;
					if (is.getType() == Material.BEACON) {
						is.setAmount(0);
					}
				}
			}
			if (player.getInventory().contains(Material.TOTEM)) {
				for (ItemStack is : player.getInventory().getContents()) {
					if (is == null)
						continue;
					if (is.getType() == Material.TOTEM) {
						is.setAmount(0);
					}
				}
			}
			if (player.getInventory().contains(Material.getMaterial("ICEANDFIRE_FISHING_SPEAR"))) {
				for (ItemStack is : player.getInventory().getContents()) {
					if (is == null)
						continue;
					if (is.getType() == Material.getMaterial("ICEANDFIRE_FISHING_SPEAR")) {
						is.setAmount(0);
					}
				}
			}
			if(player.getInventory().contains(Material.POTION)) {
				for (ItemStack is : player.getInventory().getContents()) {
					if (is == null)
						continue;
					if (is.getType() == Material.POTION) {
						PotionMeta pm = (PotionMeta) is.getItemMeta();
						if(pm.getBasePotionData().getType() == PotionType.FIRE_RESISTANCE) {
							is.setAmount(0);
						}
					}
				}
			}
		} catch (Exception e7) {
			
		}
		
		try {
			if(event.getCurrentItem().getType() == Material.NETHER_STAR) {
				if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "농부의 별")) {
					if(player.getInventory().getItem(35).getItemMeta() == null) {
						new Cmd1().giveToken1(player);
					} else {
						int num = 0;
						if(player.getInventory().contains(Material.getMaterial("ORDINARYCOINS_COINGOLD"))) {
							for (ItemStack is : player.getInventory().getContents()) {
								if(is == null) continue;
							    if (is.getType() == Material.getMaterial("ORDINARYCOINS_COINGOLD")) {			
							         num += is.getAmount();
							         is.setAmount(0);
							    }
							}
						}
						if(num > 10) {
							player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num-10));
							new Cmd1().giveToken1(player);
							player.sendMessage(ChatColor.GREEN + "직업이 변경되었습니다.");
						} else {
							player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num));
							player.sendMessage(ChatColor.RED + "10골드를 보유하고 있지 않습니다.");
						}
					}
				} else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "광부의 별")) {
					if(player.getInventory().getItem(35).getItemMeta() == null) {
						new Cmd1().giveToken4(player);
					} else {
						int num = 0;
						if(player.getInventory().contains(Material.getMaterial("ORDINARYCOINS_COINGOLD"))) {
							for (ItemStack is : player.getInventory().getContents()) {
								if(is == null) continue;
							    if (is.getType() == Material.getMaterial("ORDINARYCOINS_COINGOLD")) {			
							         num += is.getAmount();
							         is.setAmount(0);
							    }
							}
						}
						if(num > 10) {
							player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num-10));
							new Cmd1().giveToken4(player);
							player.sendMessage(ChatColor.GREEN + "직업이 변경되었습니다.");
						} else {
							player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num));
							player.sendMessage(ChatColor.RED + "10골드를 보유하고 있지 않습니다.");
						}
					}
				} else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "어부의 별")) {
					if(player.getInventory().getItem(35).getItemMeta() == null) {
						new Cmd1().giveToken7(player);
					} else {
						int num = 0;
						if(player.getInventory().contains(Material.getMaterial("ORDINARYCOINS_COINGOLD"))) {
							for (ItemStack is : player.getInventory().getContents()) {
								if(is == null) continue;
							    if (is.getType() == Material.getMaterial("ORDINARYCOINS_COINGOLD")) {			
							         num += is.getAmount();
							         is.setAmount(0);
							    }
							}
						}
						if(num > 10) {
							player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num-10));
							new Cmd1().giveToken7(player);
							player.sendMessage(ChatColor.GREEN + "직업이 변경되었습니다.");
						} else {
							player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num));
							player.sendMessage(ChatColor.RED + "10골드를 보유하고 있지 않습니다.");
						}
					}
				} else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "도적의 별")) {
					if(player.getInventory().getItem(35).getItemMeta() == null) {
						new Cmd1().giveToken10(player);
					} else {
						int num = 0;
						if(player.getInventory().contains(Material.getMaterial("ORDINARYCOINS_COINGOLD"))) {
							for (ItemStack is : player.getInventory().getContents()) {
								if(is == null) continue;
							    if (is.getType() == Material.getMaterial("ORDINARYCOINS_COINGOLD")) {			
							         num += is.getAmount();
							         is.setAmount(0);
							    }
							}
						}
						if(num > 10) {
							player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num-10));
							new Cmd1().giveToken10(player);
							player.sendMessage(ChatColor.GREEN + "직업이 변경되었습니다.");
						} else {
							player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num));
							player.sendMessage(ChatColor.RED + "10골드를 보유하고 있지 않습니다.");
						}
					}
				} else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "모험가의 별")) {
					if(player.getInventory().getItem(35).getItemMeta() == null) {
						new Cmd1().giveToken13(player);
					} else {
						int num = 0;
						if(player.getInventory().contains(Material.getMaterial("ORDINARYCOINS_COINGOLD"))) {
							for (ItemStack is : player.getInventory().getContents()) {
								if(is == null) continue;
							    if (is.getType() == Material.getMaterial("ORDINARYCOINS_COINGOLD")) {			
							         num += is.getAmount();
							         is.setAmount(0);
							    }
							}
						}
						if(num > 10) {
							player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num-10));
							new Cmd1().giveToken13(player);
							player.sendMessage(ChatColor.GREEN + "직업이 변경되었습니다.");
						} else {
							player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num));
							player.sendMessage(ChatColor.RED + "10골드를 보유하고 있지 않습니다.");
						}
					}
				} else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "상인의 별")) {
					if(player.getInventory().getItem(35).getItemMeta() == null) {
						new Cmd1().giveToken18(player);
					} else {
						int num = 0;
						if(player.getInventory().contains(Material.getMaterial("ORDINARYCOINS_COINGOLD"))) {
							for (ItemStack is : player.getInventory().getContents()) {
								if(is == null) continue;
							    if (is.getType() == Material.getMaterial("ORDINARYCOINS_COINGOLD")) {			
							         num += is.getAmount();
							         is.setAmount(0);
							    }
							}
						}
						if(num > 10) {
							player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num-10));
							new Cmd1().giveToken18(player);
							player.sendMessage(ChatColor.GREEN + "직업이 변경되었습니다.");
						} else {
							player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num));
							player.sendMessage(ChatColor.RED + "10골드를 보유하고 있지 않습니다.");
						}
					}
				} else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "기사의 별")) {
					if(player.getInventory().getItem(35).getItemMeta() == null) {
						new Cmd1().giveToken21(player);
					} else {
						int num = 0;
						if(player.getInventory().contains(Material.getMaterial("ORDINARYCOINS_COINGOLD"))) {
							for (ItemStack is : player.getInventory().getContents()) {
								if(is == null) continue;
							    if (is.getType() == Material.getMaterial("ORDINARYCOINS_COINGOLD")) {			
							         num += is.getAmount();
							         is.setAmount(0);
							    }
							}
						}
						if(num > 10) {
							player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num-10));
							new Cmd1().giveToken21(player);
							player.sendMessage(ChatColor.GREEN + "직업이 변경되었습니다.");
						} else {
							player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num));
							player.sendMessage(ChatColor.RED + "10골드를 보유하고 있지 않습니다.");
						}
					}
				} else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "직업 업그레이드")) {
					if(player.getInventory().getItem(35).getItemMeta() != null) {
						if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("1")) {
							int num = 0;
							if(player.getInventory().contains(Material.getMaterial("ORDINARYCOINS_COINGOLD"))) {
								for (ItemStack is : player.getInventory().getContents()) {
									if(is == null) continue;
								    if (is.getType() == Material.getMaterial("ORDINARYCOINS_COINGOLD")) {			
								         num += is.getAmount();
								         is.setAmount(0);
								    }
								}
							}
							if(num > 50) {
								player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num-50));
								new Cmd1().giveToken2(player);
								player.sendMessage(ChatColor.GREEN + "직업이 업그레이드 되었습니다.");
							} else {
								player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num));
								player.sendMessage(ChatColor.RED + "50골드를 보유하고 있지 않습니다.");
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("4")) {
							int num = 0;
							if(player.getInventory().contains(Material.getMaterial("ORDINARYCOINS_COINGOLD"))) {
								for (ItemStack is : player.getInventory().getContents()) {
									if(is == null) continue;
								    if (is.getType() == Material.getMaterial("ORDINARYCOINS_COINGOLD")) {			
								         num += is.getAmount();
								         is.setAmount(0);
								    }
								}
							}
							if(num > 50) {
								player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num-50));
								new Cmd1().giveToken5(player);
								player.sendMessage(ChatColor.GREEN + "직업이 업그레이드 되었습니다.");
							} else {
								player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num));
								player.sendMessage(ChatColor.RED + "50골드를 보유하고 있지 않습니다.");
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("7")) {
							int num = 0;
							if(player.getInventory().contains(Material.getMaterial("ORDINARYCOINS_COINGOLD"))) {
								for (ItemStack is : player.getInventory().getContents()) {
									if(is == null) continue;
								    if (is.getType() == Material.getMaterial("ORDINARYCOINS_COINGOLD")) {			
								         num += is.getAmount();
								         is.setAmount(0);
								    }
								}
							}
							if(num > 50) {
								player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num-50));
								new Cmd1().giveToken8(player);
								player.sendMessage(ChatColor.GREEN + "직업이 업그레이드 되었습니다.");
							} else {
								player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num));
								player.sendMessage(ChatColor.RED + "50골드를 보유하고 있지 않습니다.");
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("10")) {
							int num = 0;
							if(player.getInventory().contains(Material.getMaterial("ORDINARYCOINS_COINGOLD"))) {
								for (ItemStack is : player.getInventory().getContents()) {
									if(is == null) continue;
								    if (is.getType() == Material.getMaterial("ORDINARYCOINS_COINGOLD")) {			
								         num += is.getAmount();
								         is.setAmount(0);
								    }
								}
							}
							if(num > 50) {
								player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num-50));
								new Cmd1().giveToken11(player);
								player.sendMessage(ChatColor.GREEN + "직업이 업그레이드 되었습니다.");
							} else {
								player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num));
								player.sendMessage(ChatColor.RED + "50골드를 보유하고 있지 않습니다.");
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("13")) {
							int num = 0;
							if(player.getInventory().contains(Material.getMaterial("ORDINARYCOINS_COINGOLD"))) {
								for (ItemStack is : player.getInventory().getContents()) {
									if(is == null) continue;
								    if (is.getType() == Material.getMaterial("ORDINARYCOINS_COINGOLD")) {			
								         num += is.getAmount();
								         is.setAmount(0);
								    }
								}
							}
							if(num > 50) {
								player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num-50));
								new Cmd1().giveToken14(player);
								player.sendMessage(ChatColor.GREEN + "직업이 업그레이드 되었습니다.");
							} else {
								player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num));
								player.sendMessage(ChatColor.RED + "50골드를 보유하고 있지 않습니다.");
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("18")) {
							int num = 0;
							if(player.getInventory().contains(Material.getMaterial("ORDINARYCOINS_COINGOLD"))) {
								for (ItemStack is : player.getInventory().getContents()) {
									if(is == null) continue;
								    if (is.getType() == Material.getMaterial("ORDINARYCOINS_COINGOLD")) {			
								         num += is.getAmount();
								         is.setAmount(0);
								    }
								}
							}
							if(num > 50) {
								player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num-50));
								new Cmd1().giveToken19(player);
								player.sendMessage(ChatColor.GREEN + "직업이 업그레이드 되었습니다.");
							} else {
								player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num));
								player.sendMessage(ChatColor.RED + "50골드를 보유하고 있지 않습니다.");
							}
						} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("21")) {
							int num = 0;
							if(player.getInventory().contains(Material.getMaterial("ORDINARYCOINS_COINGOLD"))) {
								for (ItemStack is : player.getInventory().getContents()) {
									if(is == null) continue;
								    if (is.getType() == Material.getMaterial("ORDINARYCOINS_COINGOLD")) {			
								         num += is.getAmount();
								         is.setAmount(0);
								    }
								}
							}
							if(num > 50) {
								player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num-50));
								new Cmd1().giveToken22(player);
								player.sendMessage(ChatColor.GREEN + "직업이 업그레이드 되었습니다.");
							} else {
								player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINGOLD"), num));
								player.sendMessage(ChatColor.RED + "50골드를 보유하고 있지 않습니다.");
							}
						} else {
							player.sendMessage(ChatColor.RED + "업그레이드 가능한 직업이 아닙니다.");
						}
					} else {
						player.sendMessage(ChatColor.RED + "직업을 보유하고 있지 않습니다.");
					}
				}
			}
		} catch(Exception e) {
			
		}
	}
	
	@EventHandler
	public void breakEvent(BlockBreakEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();

		if(block.getType() == Material.CROPS || block.getType() == Material.CARROT || block.getType() == Material.POTATO) {
			if(block.getState().getData().getData() == 7) {
				if(block.getType() == Material.CROPS) {
					if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("2")) {
						block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.WHEAT, 1));
					} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("3")) {
						block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.WHEAT, 2));
					}
				} else if(block.getType() == Material.CARROT) {
					if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("2")) {
						block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.CARROT, 1));
					} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("3")) {
						block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.CARROT, 3));
					}
				} else if(block.getType() == Material.POTATO) {
					if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("2")) {
						block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.POTATO, 1));
					} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("3")) {
						block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.POTATO, 3));
					}
				}
			}
		}
		
		try {
			if(player.getInventory().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE) {
				if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("5")) {
					if(block.getType() == Material.IRON_ORE) {
						block.setType(Material.AIR);
						block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.IRON_INGOT, 2));
						event.setCancelled(true);
					} else if(block.getType() == Material.GOLD_ORE) {
						block.setType(Material.AIR);
						block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.GOLD_INGOT, 2));
						event.setCancelled(true);
					}
				} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("6")) {
					if(block.getType() == Material.IRON_ORE) {
						block.setType(Material.AIR);
						block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.IRON_INGOT, 3));
						event.setCancelled(true);
					} else if(block.getType() == Material.GOLD_ORE) {
						block.setType(Material.AIR);
						block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.GOLD_INGOT, 3));
						event.setCancelled(true);
					}
				}
			} 
		} catch(Exception e) {
			
		}
		
	}
	
	@EventHandler
	public void fishingEvent(PlayerFishEvent event) {
		try {
			if(event.getState() == State.CAUGHT_FISH) {
				Player player = event.getPlayer();
				int num1 = rnd.nextInt(5) + 1;
				int num2 = rnd.nextInt(6) + 5;
				if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("8")) {
					player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINSILVER"), num1));
				} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("9")) {
					player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINSILVER"), num2));
				}
			} else if(event.getState() == State.CAUGHT_ENTITY) {
				Player player = event.getPlayer();
				int num1 = rnd.nextInt(5) + 1;
				int num2 = rnd.nextInt(6) + 5;
				if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("8")) {
					player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINSILVER"), num1));
				} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("9")) {
					player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINSILVER"), num2));
				}
			}
		} catch(Exception e) {
			
		}
	}
	
	@EventHandler
	public void moveEvent(PlayerMoveEvent event) {
		try {
			Player player = event.getPlayer();
			if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("19")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2, true, false));
			} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("20")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2, true, false));
			}
		} catch(Exception e) {
			
		}
		
		try {
			if(event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
				Player player = event.getPlayer();
				int num = 0;
				if(move.containsKey(player)) {
					num = move.get(player);
					move.remove(player);
				}
				num++;
				move.put(player, num);
				
				if(num % 500 == 0) {
					int num1 = rnd.nextInt(10) + 1;
					int num2 = rnd.nextInt(16) + 5;
					if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("19")) {
						player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINSILVER"), num1));
					} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("20")) {
						player.getInventory().addItem(new ItemStack(Material.getMaterial("ORDINARYCOINS_COINSILVER"), num2));
					}
				}
			}
		} catch(Exception e) {
			
		}
		
		try {
			Player player = event.getPlayer();
			Location pLoc = player.getLocation();
			String val = player.getInventory().getItem(35).getItemMeta().getLocalizedName();
			if(!val.equals("100")) {
				boolean pass = false;
				
				for(Location loc : door.keySet()) {
					if(pLoc.getBlockX() == loc.getBlockX() && pLoc.getBlockY() == loc.getBlockY() && pLoc.getBlockZ() == loc.getBlockZ()) {
						try {
							String[] moAry =  door.get(loc).split(" ");
							
							for(String mo : moAry) {
								if(mo.equals(val)) {
									pass = true;
								}
							}
							
						} catch(Exception e) {
							blockTp(player, loc);
						}
					}
				}
				
				if(!pass && isNear(player)) {
					blockTp(player, nearestLoc(player));
				}
			}
		} catch(Exception e) {
			
		}
	}
	
	public void loadData(World world) {
		door.clear();
		try {
			File dataFolder = getDataFolder();
            if(!dataFolder.exists()) {
                dataFolder.mkdir();
            } else {
            	File dir = new File(getDataFolder() + "/save");
            	if(!dir.exists()) {
            		try{
            		    dir.mkdir(); 
            		} catch(Exception e) {
            		    e.getStackTrace();
            		}
            	}
            	try {
            		File file = new File(dir, "door.txt");
    				if (!file.exists()) {
    					try {
    						file.createNewFile();
    					} catch (IOException e) {
    						e.printStackTrace();
    					}
    				}
    				FileReader filereader = new FileReader(file);
    				BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
    				String name;
    				while((name = bufReader.readLine()) != null) {
        				try {
        					if(name.substring(0, 1).equals("#")) {
        						continue;
        					}
        					
        					String[] aryLoc = name.split("/");
        					
        					if(door.containsKey(new Location(world, 
                							Double.parseDouble(aryLoc[0].split(" ")[0]),
                							Double.parseDouble(aryLoc[0].split(" ")[1]),
                							Double.parseDouble(aryLoc[0].split(" ")[2])))) {
        						door.put(new Location(world, 
            							Double.parseDouble(aryLoc[0].split(" ")[0]),
            							Double.parseDouble(aryLoc[0].split(" ")[1]),
            							Double.parseDouble(aryLoc[0].split(" ")[2])), door.get(new Location(world, 
                							Double.parseDouble(aryLoc[0].split(" ")[0]),
                							Double.parseDouble(aryLoc[0].split(" ")[1]),
                							Double.parseDouble(aryLoc[0].split(" ")[2]))) + " " + aryLoc[1]);
        						
        					} else {
        						door.put(new Location(world, 
            							Double.parseDouble(aryLoc[0].split(" ")[0]),
            							Double.parseDouble(aryLoc[0].split(" ")[1]),
            							Double.parseDouble(aryLoc[0].split(" ")[2])), aryLoc[1]);
        					}
        					
        				} catch(Exception e) {
        					
        				}
    				}
    				bufReader.close();
            	} catch(Exception e) {
            		
            	}
			}
		} catch(Exception e) {
			
		}
	}
	
	public boolean isNear(Player player) {
		Location pLoc = player.getLocation();
		double dist = 200000000;
		
		for(Location loc : door.keySet()) {
			Location newLoc = loc.clone().add(0.5,0.5,0.5);
			dist = Math.min(Math.pow(newLoc.getX()-pLoc.getX(), 2) + Math.pow(newLoc.getY()-pLoc.getY(), 2) + Math.pow(newLoc.getZ()-pLoc.getZ(), 2), dist);
		}
		
		if(dist <= 0.5) {
			return true;
		} 
		
		return false;
	}
	
	public Location nearestLoc(Player player) {
		Location pLoc = player.getLocation();
		int dist = 200000000;
		Location arr = null;
		
		for(Location loc : door.keySet()) {
			dist = Math.min((int)(Math.pow(loc.getX()-pLoc.getX(), 2) + Math.pow(loc.getY()-pLoc.getY(), 2) + Math.pow(loc.getZ()-pLoc.getZ(), 2)), dist);
		}
		for(Location loc : door.keySet()) {
			if((int)(Math.pow(loc.getX()-pLoc.getX(), 2) + Math.pow(loc.getY()-pLoc.getY(), 2) + Math.pow(loc.getZ()-pLoc.getZ(), 2)) == dist) {
				arr = loc;
			}
		}
		
		return arr;
	}
	
	public void blockTp(Player player, Location loc) {
		if(loc == null) {
			return;
		}
		
		double bX = loc.getBlockX() + 0.5;
		double bZ = loc.getBlockZ() + 0.5;
		double pX = player.getLocation().getX();
		double pZ = player.getLocation().getZ();
		
		if(bX < pX) {
			player.teleport(player.getLocation().add(1,0,0));
		}
		if(bX > pX) {
			player.teleport(player.getLocation().add(-1,0,0));
		}
		if(bZ < pZ) {
			player.teleport(player.getLocation().add(0,0,1));
		}
		if(bZ > pZ) {
			player.teleport(player.getLocation().add(0,0,-1));
		}
	}
	
	public static <K, V> K getKey(Map<K, V> map, V value) {
		 
        for (K key : map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }
        return null;
    }
	
	public static int ud(double num) {
		int result;
		
		if(num >= 0) {
			result = (int)num;
		} else {
			result = (int)num + 1;
		}
		return result;
	}

}