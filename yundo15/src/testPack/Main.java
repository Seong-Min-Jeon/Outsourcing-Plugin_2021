package testPack;

import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
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
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.Button;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

//gamerule doImmediateRespawn true
// -642 90 -257  257 50 642
// 총(엔티티 어택, 평타랑 같은 개념)
// 수류탄(블럭 폭발)
// -404 80 -277

public class Main extends JavaPlugin implements Listener{
	
	ArrayList<Location> aryBlack = new ArrayList<>();
	ArrayList<Location> aryBlue = new ArrayList<>();
	ArrayList<Location> aryYellow = new ArrayList<>();
	ArrayList<Location> aryOrange = new ArrayList<>();
	ArrayList<Location> aryGreen = new ArrayList<>();
	ArrayList<Location> aryPurple = new ArrayList<>();
	ArrayList<Location> aryWhite = new ArrayList<>();
	ArrayList<Location> aryPink = new ArrayList<>();
	
	ArrayList<Player> pBlack = new ArrayList<>();
	ArrayList<Player> pBlue = new ArrayList<>();
	ArrayList<Player> pYellow = new ArrayList<>();
	ArrayList<Player> pOrange = new ArrayList<>();
	ArrayList<Player> pGreen = new ArrayList<>();
	ArrayList<Player> pPurple = new ArrayList<>();
	ArrayList<Player> pWhite = new ArrayList<>();
	ArrayList<Player> pPink = new ArrayList<>();
	
	ArrayList<String> banList = new ArrayList<>();
	HashMap<Player, Block> map = new HashMap<>();
	boolean start = false;
	Location btnLoc = null;
	int timer = 0;
 
	Random rnd = new Random();
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		
		getCommand("직업").setExecutor(new Cmd1());
		
		new BukkitRunnable() {
			int time = 0;

			@Override
			public void run() {
				time++;

				if (time % 20 == 0) {
					new CoolTime().countTime();
				}
				
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(start) {
					timer++;
					
					if(timer % 8400 == 0) {
						//폭격
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendMessage(ChatColor.RED + "도니버스 폭격기가 폭격합니다!");
						}
						World world = Bukkit.getWorld("world");
						
						new BukkitRunnable() {
							
							int cnt = 0;
							
							@Override
							public void run() {
								if(cnt == 0) {
									for(int i = 0 ; i < 100 ; i++) {
										int x = rnd.nextInt(810) - 650;
										int z = rnd.nextInt(810) - 260;
										Location loc = new Location(world, x, 255, z);
										
										TNTPrimed tnt = (TNTPrimed) loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
										tnt.setFuseTicks(Integer.MAX_VALUE);
									}
									
									for(Player all : Bukkit.getOnlinePlayers()) {
										int x = all.getLocation().getBlockX();
										int z = all.getLocation().getBlockZ();
										Location loc = new Location(world, x, 255, z);
										
										TNTPrimed tnt = (TNTPrimed) loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
										tnt.setFuseTicks(Integer.MAX_VALUE);
									}
								}
								
								if(cnt == 200) {
									ArmorStand as = (ArmorStand) world.spawnEntity(new Location(world, 0, 128, 0), EntityType.ARMOR_STAND);
									for(Entity ent : as.getNearbyEntities(700, 128, 700)) {
										if(ent instanceof TNTPrimed) {
											ent.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, ent.getLocation(), 0);
											ent.getWorld().playSound(ent.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5.0f, 1.0f);
											for(Entity p : ent.getNearbyEntities(8, 5, 8)) {
												if(p instanceof Player) {
													Player all = (Player) p;
													all.damage(15);
												}
											}
											ent.remove();
										}
									}
									for(Player all : Bukkit.getOnlinePlayers()) {
										all.playSound(all.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.6f, 1.0f);
									}
									as.remove();
									this.cancel();
								}
								cnt++;
							}
						}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
					}
					
					if(timer % 8610 == 0) {
						World world = Bukkit.getWorld("world");
						
						new BukkitRunnable() {
							
							int cnt = 0;
							
							@Override
							public void run() {
								if(cnt == 0) {
									for(int i = 0 ; i < 100 ; i++) {
										int x = rnd.nextInt(810) - 650;
										int z = rnd.nextInt(810) - 260;
										Location loc = new Location(world, x, 255, z);
										
										TNTPrimed tnt = (TNTPrimed) loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
										tnt.setFuseTicks(Integer.MAX_VALUE);
									}
									
									for(Player all : Bukkit.getOnlinePlayers()) {
										int x = all.getLocation().getBlockX();
										int z = all.getLocation().getBlockZ();
										Location loc = new Location(world, x, 255, z);
										
										TNTPrimed tnt = (TNTPrimed) loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
										tnt.setFuseTicks(Integer.MAX_VALUE);
									}
								}
								
								if(cnt == 200) {
									ArmorStand as = (ArmorStand) world.spawnEntity(new Location(world, 0, 128, 0), EntityType.ARMOR_STAND);
									for(Entity ent : as.getNearbyEntities(700, 128, 700)) {
										if(ent instanceof TNTPrimed) {
											ent.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, ent.getLocation(), 0);
											ent.getWorld().playSound(ent.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5.0f, 1.0f);
											for(Entity p : ent.getNearbyEntities(8, 5, 8)) {
												if(p instanceof Player) {
													Player all = (Player) p;
													all.damage(15);
												}
											}
											ent.remove();
										}
									}
									for(Player all : Bukkit.getOnlinePlayers()) {
										all.getWorld().playSound(all.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.6f, 1.0f);
									}
									as.remove();
									this.cancel();
								}
								cnt++;
							}
						}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
						
					}
					
					if(timer % 8820 == 0) {
						World world = Bukkit.getWorld("world");
						
						new BukkitRunnable() {
							
							int cnt = 0;
							
							@Override
							public void run() {
								if(cnt == 0) {
									for(int i = 0 ; i < 100 ; i++) {
										int x = rnd.nextInt(810) - 650;
										int z = rnd.nextInt(810) - 260;
										Location loc = new Location(world, x, 255, z);
										
										TNTPrimed tnt = (TNTPrimed) loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
										tnt.setFuseTicks(Integer.MAX_VALUE);
									}
									
									for(Player all : Bukkit.getOnlinePlayers()) {
										int x = all.getLocation().getBlockX();
										int z = all.getLocation().getBlockZ();
										Location loc = new Location(world, x, 255, z);
										
										TNTPrimed tnt = (TNTPrimed) loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
										tnt.setFuseTicks(Integer.MAX_VALUE);
									}
								}
								
								if(cnt == 200) {
									ArmorStand as = (ArmorStand) world.spawnEntity(new Location(world, 0, 128, 0), EntityType.ARMOR_STAND);
									for(Entity ent : as.getNearbyEntities(700, 128, 700)) {
										if(ent instanceof TNTPrimed) {
											ent.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, ent.getLocation(), 0);
											ent.getWorld().playSound(ent.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5.0f, 1.0f);
											for(Entity p : ent.getNearbyEntities(8, 5, 8)) {
												if(p instanceof Player) {
													Player all = (Player) p;
													all.damage(15);
												}
											}
											ent.remove();
										}
									}
									for(Player all : Bukkit.getOnlinePlayers()) {
										all.getWorld().playSound(all.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.6f, 1.0f);
									}
									as.remove();
									this.cancel();
								}
								cnt++;
							}
						}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
						
					}
					
					if(timer == 6000) {
						//보급
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendMessage(ChatColor.GREEN + "보급이 활성화되었습니다!");
						}
						
						World world = Bukkit.getWorld("world");
						Location loc = null;
						// -642 90 -257  257 50 642
						for(int i = -642 ; i <= 257 ; i++) {
							for(int j = 50 ; j <= 90 ; j++) {
								for(int k = -257 ; k <= 642 ; k++) {
									loc = new Location(world,i,j,k);
									if(loc.getBlock().getType() == Material.CONCRETE) {
										if(loc.getBlock().getData() == 14) {
											Location chestLoc = loc.clone().add(0,1,0);
											chestLoc.getBlock().setType(Material.CHEST);
											Block block = chestLoc.getBlock();
											Chest chest = (Chest) block.getState();
											Inventory inv = chest.getInventory();
											inv.setItem(0, new ItemStack(Material.WOOD_SWORD));
											inv.setItem(1, new ItemStack(Material.LEATHER_HELMET));
											inv.setItem(2, new ItemStack(Material.LEATHER_CHESTPLATE));
											inv.setItem(3, new ItemStack(Material.LEATHER_LEGGINGS));
											inv.setItem(4, new ItemStack(Material.LEATHER_BOOTS));
											inv.setItem(5, new ItemStack(Material.COOKED_CHICKEN));
										}
									}
								}
							}
						}
					}
					
					// 앞으로 점프
					for(Player all : Bukkit.getOnlinePlayers()) {
						Block ground = all.getLocation().add(0,-1,0).getBlock();
						Location loc = ground.getLocation();
						if (loc.getX() <= 257 && loc.getY() <= 90 && loc.getZ() <= 642 
								&& loc.getX() >= -642 && loc.getY() >= 50 && loc.getZ() >= -257) {
							if(ground.getType() == Material.CONCRETE) {
								if(ground.getData() == 1) {
									try {
										if(map.get(all) != ground) {
											map.remove(all);
											map.put(all, ground);
											all.getWorld().playSound(all.getLocation(), Sound.BLOCK_NOTE_BASEDRUM, 2.0f, 1.89f);
											all.setVelocity(all.getLocation().getDirection().multiply(3.0f).add(new Vector(0,0.6,0)));
										}
									} catch(Exception e) {
										
									}
								}
							}
						}
					}
					
				}
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	@Override
	public void onDisable() {
		getLogger().info("===============");
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer(); 
		player.setGameMode(GameMode.ADVENTURE);
		
		if(banList.contains(player.getDisplayName()) || start) {
			event.setJoinMessage(null);
			player.kickPlayer(ChatColor.RED + "이번 게임에는 참여할 수 없습니다.");
		}
		
		player.getInventory().clear();
		player.teleport(player.getWorld().getSpawnLocation());
		
		player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "도니버스 유튜브" + ChatColor.WHITE + "" + ChatColor.BOLD + " / " + ChatColor.DARK_RED + "" + ChatColor.BOLD + "유메하마 플러그인" );
	}
	
	@EventHandler
	public void respawn(PlayerRespawnEvent event) {
		event.getPlayer().getInventory().clear();
		event.setRespawnLocation(event.getPlayer().getWorld().getSpawnLocation());
	}
	
	@EventHandler
	public void die(PlayerDeathEvent event) {
		try {
			Player player = (Player) event.getEntity();
			player.kickPlayer(ChatColor.RED + "You Died");
			
			if(start) {
				banList.add(player.getDisplayName());
			}
		} catch(Exception e) {
			
		}
		
	}
	
	@EventHandler
	public void dropEvent(PlayerDropItemEvent event) {
		System.out.println(event.getItemDrop().getItemStack().getType());
		if(event.getItemDrop().getItemStack().getType() == Material.BARRIER) {
			event.setCancelled(true);
		}
		if(event.getItemDrop().getItemStack().getType() == Material.NETHER_STAR) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void clickEvent(InventoryClickEvent event) {
		try {
			if(event.getCurrentItem().getType() == Material.BARRIER) {
				event.setCancelled(true);
			}
			if(event.getCurrentItem().getType() == Material.NETHER_STAR) {
				try {
					if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "탱커")) {
						new Cmd1().jobMap.put((Player) event.getWhoClicked(), 1);
						((Player) event.getWhoClicked()).sendMessage(ChatColor.GREEN + "탱커가 선택되었습니다.");
					} else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "딜러")) {
						new Cmd1().jobMap.put((Player) event.getWhoClicked(), 2);
						((Player) event.getWhoClicked()).sendMessage(ChatColor.GREEN + "딜러가 선택되었습니다.");
					} else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "힐러")) {
						new Cmd1().jobMap.put((Player) event.getWhoClicked(), 3);
						((Player) event.getWhoClicked()).sendMessage(ChatColor.GREEN + "힐러가 선택되었습니다.");
					}
				} catch(Exception e) {
					
				}
				event.setCancelled(true);
			}
			if(event.getCursor().getType() == Material.BARRIER) {
				event.setCancelled(true);
			}
			if(event.getCursor().getType() == Material.NETHER_STAR) {
				event.setCancelled(true);
			}
		} catch(Exception e) {
			
		}
	}
	
	@EventHandler
	public void damageEvent(EntityDamageByEntityEvent event) {
		if(pBlack.contains(event.getDamager()) && pBlack.contains(event.getEntity())) {
			event.setCancelled(true);
		} else if(pBlue.contains(event.getDamager()) && pBlue.contains(event.getEntity())) {
			event.setCancelled(true);
		} else if(pYellow.contains(event.getDamager()) && pYellow.contains(event.getEntity())) {
			event.setCancelled(true);
		} else if(pOrange.contains(event.getDamager()) && pOrange.contains(event.getEntity())) {
			event.setCancelled(true);
		} else if(pGreen.contains(event.getDamager()) && pGreen.contains(event.getEntity())) {
			event.setCancelled(true);
		} else if(pPurple.contains(event.getDamager()) && pPurple.contains(event.getEntity())) {
			event.setCancelled(true);
		} else if(pWhite.contains(event.getDamager()) && pWhite.contains(event.getEntity())) {
			event.setCancelled(true);
		} else if(pPink.contains(event.getDamager()) && pPink.contains(event.getEntity())) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void damageEvent2(EntityDamageEvent event) {
//		System.out.println(event.getCause());
	}
	
	@EventHandler
	public void mouseEvent(PlayerInteractEvent event) {
		try {
			EquipmentSlot e = event.getHand();
			if (e.equals(EquipmentSlot.HAND)) {
				Player player = event.getPlayer();
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if(event.getClickedBlock().getType() == Material.STONE_BUTTON) {
						if(start) {
							boolean allDied = false;
							for(Player all : Bukkit.getOnlinePlayers()) {
								Location loc2 = all.getLocation();
								if (!(loc2.getX() <= 257 && loc2.getY() <= 255 && loc2.getZ() <= 642 
										&& loc2.getX() >= -642 && loc2.getY() >= 0 && loc2.getZ() >= -257)) {
									allDied = true;
								}
							}
							
							if(!allDied) {
								player.sendMessage(ChatColor.RED + "경기장 내부에 플레이어가 존재합니다.");
								return;
							}
						}
						
						btnLoc = event.getClickedBlock().getLocation().add(0,1,0);
						start = true;
						
						World world = Bukkit.getWorld("world");
						Location loc = null;
						// -642 90 -257  257 50 642
						for(int i = -642 ; i <= 257 ; i++) {
							for(int j = 50 ; j <= 90 ; j++) {
								for(int k = -257 ; k <= 642 ; k++) {
									loc = new Location(world,i,j,k);
									if(loc.getBlock().getType() == Material.CONCRETE) {
										if(loc.getBlock().getData() == 14) {
											Location chestLoc = loc.clone().add(0,1,0);
											chestLoc.getBlock().setType(Material.AIR);
										}
									} else if(loc.getBlock().getType() == Material.CHEST) {
										Location chestLoc = loc.clone();
										Block block = chestLoc.getBlock();
										Chest chest = (Chest) block.getState();
										Inventory inv = chest.getInventory();
										if(rnd.nextInt(10) < 6) {
											int r = rnd.nextInt(9);
											if(r == 0) {
												inv.setItem(0, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEMP5"), 1));
												inv.setItem(1, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEMP5AMMO"), 1));
												inv.setItem(2, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPE9X19"), 60));
												inv.setItem(3, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEHIKINGPACK"), 1));
												inv.setItem(4, new ItemStack(Material.BAKED_POTATO, 3));
											} else if(r == 1) {
												inv.setItem(0, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEP88"), 1));
												inv.setItem(1, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEP88AMMO"), 1));
												inv.setItem(2, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPE9X19"), 60));
												inv.setItem(3, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEHIKINGPACK"), 1));
												inv.setItem(4, new ItemStack(Material.BAKED_POTATO, 4));
											} else if(r == 2) {
												inv.setItem(0, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEAK74U"), 1));
												inv.setItem(1, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEAK74UAMMO"), 1));
												inv.setItem(2, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPE7_62X39"), 60));
												inv.setItem(3, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEHIKINGPACK"), 1));
												inv.setItem(4, new ItemStack(Material.BREAD, 3));
											} else if(r == 3) {
												inv.setItem(0, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEHIKINGPACK"), 1));
												inv.setItem(1, new ItemStack(Material.BREAD, 2));
												inv.setItem(2, new ItemStack(Material.BAKED_POTATO, 2));
												inv.setItem(3, new ItemStack(Material.LEATHER_HELMET, 1));
												inv.setItem(4, new ItemStack(Material.LEATHER_CHESTPLATE, 1));
											} else if(r == 4) {
												inv.setItem(0, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEDOCTER_SIGHT"), 1));
												inv.setItem(1, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEPISTOL_SUPPRESSOR"), 1));
												inv.setItem(2, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPETACTICAL_FLASHLIGHT"), 1));
												inv.setItem(3, new ItemStack(Material.LEATHER_CHESTPLATE, 1));
												inv.setItem(4, new ItemStack(Material.BREAD, 2));
											} else if(r == 5) {
												inv.setItem(0, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEAK47_SUPPRESSOR"), 1));
												inv.setItem(1, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEDOCTER_SIGHT_MINI"), 1));
												inv.setItem(2, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEERGO_FOREGRIP"), 1));
												inv.setItem(3, new ItemStack(Material.LEATHER_HELMET, 1));
											} else if(r == 6) {
												inv.setItem(0, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPESHOTGUN_SUPPRESSOR"), 1));
												inv.setItem(1, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEACOG_SCOPE"), 1));
												inv.setItem(2, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEAK74U_FLASHLIGHT"), 1));
												inv.setItem(3, new ItemStack(Material.LEATHER_HELMET, 1));
											} else if(r == 7) {
												inv.setItem(0, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEAR_SUPPRESSOR"), 1));
												inv.setItem(1, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPELONG_SCOPE"), 1));
												inv.setItem(2, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEACOG_SCOPE"), 1));
												inv.setItem(3, new ItemStack(Material.LEATHER_CHESTPLATE, 1));
												inv.setItem(4, new ItemStack(Material.BREAD, 3));
											} else if(r == 8) {
												inv.setItem(0, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPESR_SUPPRESSOR"), 1));
												inv.setItem(1, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEOBZOR_SIGHT"), 1));
												inv.setItem(2, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEDOCTER_SIGHT"), 1));
												inv.setItem(3, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEHIKINGPACK"), 1));
												inv.setItem(4, new ItemStack(Material.BAKED_POTATO, 3));
											}
										} else {
											int r = rnd.nextInt(9);
											if(r == 0) {
												inv.setItem(0, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEHK416"), 1));
												inv.setItem(1, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEHK416AMMO"), 1));
												inv.setItem(2, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPE5_56X45"), 120));
											} else if(r == 1) {
												inv.setItem(0, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEM4A1"), 1));
												inv.setItem(1, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEM4A1AMMO"), 1));
												inv.setItem(2, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPE5_56X45"), 120));
											} else if(r == 2) {
												inv.setItem(0, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEAK47"), 1));
												inv.setItem(1, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEAK47AMMO"), 1));
												inv.setItem(2, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPE7_62X39"), 120));
											} else if(r == 3) {
												inv.setItem(0, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPESPAS12"), 1));
												inv.setItem(1, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPE12GAUGE"), 60));
											} else if(r == 4) {
												inv.setItem(0, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEUZI"), 1));
												inv.setItem(1, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEUZIAMMO"), 1));
												inv.setItem(2, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPE9X19"), 150));
											} else if(r == 5) {
												inv.setItem(0, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPETAURUS"), 1));
												inv.setItem(1, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPE44MAGNUM"), 30));
											} else if(r == 6) {
												inv.setItem(0, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEMILITARY_CAP_BLACK"), 1));
												inv.setItem(1, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPESMOKE_GRENADE"), 1));
											} else if(r == 7) {
												inv.setItem(0, new ItemStack(Material.GOLDEN_APPLE, 2));
												inv.setItem(1, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPESTUN_GRENADE"), 1));
											} else if(r == 8) {
												inv.setItem(0, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEFRAG_GRENADE"), 1));
												inv.setItem(1, new ItemStack(Material.getMaterial("MODULARWARFARE_PROTOTYPEUNIFORM_FEET"), 1));
											}
										}
										
									}
								}
							}
						}
						
						new BukkitRunnable() {
							int time = 0;
							
							@Override
							public void run() {
								time++;
								
								if(time == 1) {
									try {
										for(Player all : Bukkit.getOnlinePlayers()) {
											PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
													ChatSerializer.a("{\"text\":\"§73\"}"));
											Object handle = all.getClass().getMethod("getHandle").invoke(all);
									        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
									        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
										}
									} catch(Exception e) {
										
									}
								}
								
								if(time == 21) {
									try {
										for(Player all : Bukkit.getOnlinePlayers()) {
											PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
													ChatSerializer.a("{\"text\":\"§72\"}"));
											Object handle = all.getClass().getMethod("getHandle").invoke(all);
									        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
									        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
										}
									} catch(Exception e) {
										
									}
								}
								
								if(time == 41) {
									try {
										for(Player all : Bukkit.getOnlinePlayers()) {
											PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
													ChatSerializer.a("{\"text\":\"§71\"}"));
											Object handle = all.getClass().getMethod("getHandle").invoke(all);
									        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
									        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
										}
									} catch(Exception e) {
										
									}
								}
								
								if(time >= 61) {
									
									try {
										for(Player all : Bukkit.getOnlinePlayers()) {
											PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
													ChatSerializer.a("{\"text\":\"§cbattle start!\"}"));
											Object handle = all.getClass().getMethod("getHandle").invoke(all);
									        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
									        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
										}
									} catch(Exception e) {
										
									}
									
									int cnt = 0;
									World world = player.getWorld();
									Location loc;
									
									for(int i = -642 ; i <= 257 ; i++) {
										for(int j = 50 ; j <= 90 ; j++) {
											for(int k = -257 ; k <= 642 ; k++) {
												loc = new Location(world,i,j,k);
												if(loc.getBlock().getType() == Material.WOOL) {
													if(loc.getBlock().getData() == 15) {
														aryBlack.add(loc);
													} else if(loc.getBlock().getData() == 11) {
														aryBlue.add(loc);
													} else if(loc.getBlock().getData() == 4) {
														aryYellow.add(loc);
													} else if(loc.getBlock().getData() == 1) {
														aryOrange.add(loc);
													} else if(loc.getBlock().getData() == 13) {
														aryGreen.add(loc);
													} else if(loc.getBlock().getData() == 10) {
														aryPurple.add(loc);
													} else if(loc.getBlock().getData() == 0) {
														aryWhite.add(loc);
													} else if(loc.getBlock().getData() == 6) {
														aryPink.add(loc);
													}
												}
											}
										}
									}
									
									for(Player all : Bukkit.getOnlinePlayers()) {
										if(all.getLocation().add(0,-1,0).getBlock().getType() == Material.CONCRETE || all.getLocation().add(0,-2,0).getBlock().getType() == Material.CONCRETE) {
											if(all.getLocation().add(0,-1,0).getBlock().getData() == 15 || all.getLocation().add(0,-2,0).getBlock().getData() == 15) {
												int num = rnd.nextInt(aryBlack.size());
												Location startLoc = aryBlack.get(num).add(0.5,1,0.5);
												all.teleport(startLoc);
												pBlack.add(all);
												cnt++;
											} else if(all.getLocation().add(0,-1,0).getBlock().getData() == 11 || all.getLocation().add(0,-2,0).getBlock().getData() == 11) {
												int num = rnd.nextInt(aryBlue.size());
												Location startLoc = aryBlue.get(num).add(0.5,1,0.5);
												all.teleport(startLoc);
												pBlue.add(all);
												cnt++;
											} else if(all.getLocation().add(0,-1,0).getBlock().getData() == 4 || all.getLocation().add(0,-2,0).getBlock().getData() == 4) {
												int num = rnd.nextInt(aryYellow.size());
												Location startLoc = aryYellow.get(num).add(0.5,1,0.5);
												all.teleport(startLoc);
												pYellow.add(all);
												cnt++;
											} else if(all.getLocation().add(0,-1,0).getBlock().getData() == 1 || all.getLocation().add(0,-2,0).getBlock().getData() == 1) {
												int num = rnd.nextInt(aryOrange.size());
												Location startLoc = aryOrange.get(num).add(0.5,1,0.5);
												all.teleport(startLoc);
												pOrange.add(all);
												cnt++;
											} else if(all.getLocation().add(0,-1,0).getBlock().getData() == 13 || all.getLocation().add(0,-2,0).getBlock().getData() == 13) {
												int num = rnd.nextInt(aryGreen.size());
												Location startLoc = aryGreen.get(num).add(0.5,1,0.5);
												all.teleport(startLoc);
												pGreen.add(all);
												cnt++;
											} else if(all.getLocation().add(0,-1,0).getBlock().getData() == 10 || all.getLocation().add(0,-2,0).getBlock().getData() == 10) {
												int num = rnd.nextInt(aryPurple.size());
												Location startLoc = aryPurple.get(num).add(0.5,1,0.5);
												all.teleport(startLoc);
												pPurple.add(all);
												cnt++;
											} else if(all.getLocation().add(0,-1,0).getBlock().getData() == 0 || all.getLocation().add(0,-2,0).getBlock().getData() == 0) {
												int num = rnd.nextInt(aryWhite.size());
												Location startLoc = aryWhite.get(num).add(0.5,1,0.5);
												all.teleport(startLoc);
												pWhite.add(all);
												cnt++;
											} else if(all.getLocation().add(0,-1,0).getBlock().getData() == 6 || all.getLocation().add(0,-2,0).getBlock().getData() == 6) {
												int num = rnd.nextInt(aryPink.size());
												Location startLoc = aryPink.get(num).add(0.5,1,0.5);
												all.teleport(startLoc);
												pPink.add(all);
												cnt++;
											}
										}
									}
									
									if(cnt == 0) {
										for(Player all : Bukkit.getOnlinePlayers()) {
											all.sendMessage(ChatColor.RED + "참가자가 없어 게임이 초기화됩니다.");
										}
										start = false;
										this.cancel();
									}

									this.cancel();
								}
								
							}
						}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
					}
				}
			}
		} catch(Exception e) {
			
		}
	}
	
	@EventHandler
	public void swapEvent(PlayerSwapHandItemsEvent event) {
		event.setCancelled(true);
		Player player = event.getPlayer();
		
		if(new Cmd1().jobMap.get(player) == 1) {
			int cool = 200;
			if (new CoolTime().coolCheck(player, cool, "F")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 40, 4, true, false));
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, "F"));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(new Cmd1().jobMap.get(player) == 2) {
			int cool = 100;
			if (new CoolTime().coolCheck(player, cool, "F")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 1, true, false));
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, "F"));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(new Cmd1().jobMap.get(player) == 3) {
			int cool = 100;
			if (new CoolTime().coolCheck(player, cool, "F")) {
				if(pBlack.contains(player)) {
					for(Entity ent : player.getNearbyEntities(10, 10, 10)) {
						if(ent instanceof Player) {
							Player p = (Player) ent;
							if(pBlack.contains(p)) {
								if(p.getHealth() >= 0) {
									p.setHealth(20);
								} else {
									p.setHealth(player.getHealth() + 20);
								}
								p.sendMessage(ChatColor.GREEN + "" + player.getDisplayName() + "님이 회복시켜주었습니다.");
							}
						}
					}
				} else if(pBlue.contains(player)) {
					for(Entity ent : player.getNearbyEntities(10, 10, 10)) {
						if(ent instanceof Player) {
							Player p = (Player) ent;
							if(pBlue.contains(p)) {
								if(p.getHealth() >= 0) {
									p.setHealth(20);
								} else {
									p.setHealth(player.getHealth() + 20);
								}
								p.sendMessage(ChatColor.GREEN + "" + player.getDisplayName() + "님이 회복시켜주었습니다.");
							}
						}
					}
				} else if(pGreen.contains(player)) {
					for(Entity ent : player.getNearbyEntities(10, 10, 10)) {
						if(ent instanceof Player) {
							Player p = (Player) ent;
							if(pGreen.contains(p)) {
								if(p.getHealth() >= 0) {
									p.setHealth(20);
								} else {
									p.setHealth(player.getHealth() + 20);
								}
								p.sendMessage(ChatColor.GREEN + "" + player.getDisplayName() + "님이 회복시켜주었습니다.");
							}
						}
					}
				} else if(pOrange.contains(player)) {
					for(Entity ent : player.getNearbyEntities(10, 10, 10)) {
						if(ent instanceof Player) {
							Player p = (Player) ent;
							if(pOrange.contains(p)) {
								if(p.getHealth() >= 0) {
									p.setHealth(20);
								} else {
									p.setHealth(player.getHealth() + 20);
								}
								p.sendMessage(ChatColor.GREEN + "" + player.getDisplayName() + "님이 회복시켜주었습니다.");
							}
						}
					}
				} else if(pPink.contains(player)) {
					for(Entity ent : player.getNearbyEntities(10, 10, 10)) {
						if(ent instanceof Player) {
							Player p = (Player) ent;
							if(pPink.contains(p)) {
								if(p.getHealth() >= 0) {
									p.setHealth(20);
								} else {
									p.setHealth(player.getHealth() + 20);
								}
								p.sendMessage(ChatColor.GREEN + "" + player.getDisplayName() + "님이 회복시켜주었습니다.");
							}
						}
					}
				} else if(pPurple.contains(player)) {
					for(Entity ent : player.getNearbyEntities(10, 10, 10)) {
						if(ent instanceof Player) {
							Player p = (Player) ent;
							if(pPurple.contains(p)) {
								if(p.getHealth() >= 0) {
									p.setHealth(20);
								} else {
									p.setHealth(player.getHealth() + 20);
								}
								p.sendMessage(ChatColor.GREEN + "" + player.getDisplayName() + "님이 회복시켜주었습니다.");
							}
						}
					}
				} else if(pWhite.contains(player)) {
					for(Entity ent : player.getNearbyEntities(10, 10, 10)) {
						if(ent instanceof Player) {
							Player p = (Player) ent;
							if(pWhite.contains(p)) {
								if(p.getHealth() >= 0) {
									p.setHealth(20);
								} else {
									p.setHealth(player.getHealth() + 20);
								}
								p.sendMessage(ChatColor.GREEN + "" + player.getDisplayName() + "님이 회복시켜주었습니다.");
							}
						}
					}
				} else if(pYellow.contains(player)) {
					for(Entity ent : player.getNearbyEntities(10, 10, 10)) {
						if(ent instanceof Player) {
							Player p = (Player) ent;
							if(pYellow.contains(p)) {
								if(p.getHealth() >= 0) {
									p.setHealth(20);
								} else {
									p.setHealth(player.getHealth() + 20);
								}
								p.sendMessage(ChatColor.GREEN + "" + player.getDisplayName() + "님이 회복시켜주었습니다.");
							}
						}
					}
				}
				
				if(player.getHealth() >= 0) {
					player.setHealth(20);
				} else {
					player.setHealth(player.getHealth() + 20);
				}
				player.sendMessage(ChatColor.GREEN + "주변 아군을 회복시켰습니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, "F"));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		}
		
	}
	
	@EventHandler
	public void chatEvent(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if(start) {
			if(pBlack.contains(player)) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(pBlack.contains(p)) {
						p.sendMessage("<" + player.getDisplayName() + "> " + event.getMessage());
					}
				}
			} else if(pBlue.contains(player)) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(pBlue.contains(p)) {
						p.sendMessage("<" + player.getDisplayName() + "> " + event.getMessage());
					}
				}
			} else if(pYellow.contains(player)) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(pYellow.contains(p)) {
						p.sendMessage("<" + player.getDisplayName() + "> " + event.getMessage());
					}
				}
			} else if(pOrange.contains(player)) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(pOrange.contains(p)) {
						p.sendMessage("<" + player.getDisplayName() + "> " + event.getMessage());
					}
				}
			} else if(pGreen.contains(player)) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(pGreen.contains(p)) {
						p.sendMessage("<" + player.getDisplayName() + "> " + event.getMessage());
					}
				}
			} else if(pPurple.contains(player)) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(pPurple.contains(p)) {
						p.sendMessage("<" + player.getDisplayName() + "> " + event.getMessage());
					}
				}
			} else if(pWhite.contains(player)) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(pWhite.contains(p)) {
						p.sendMessage("<" + player.getDisplayName() + "> " + event.getMessage());
					}
				}
			} else if(pPink.contains(player)) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(pPink.contains(p)) {
						p.sendMessage("<" + player.getDisplayName() + "> " + event.getMessage());
					}
				}
			}
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void blockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if(!player.isOp()) {
			if(event.getBlock().getType() == Material.STONE_BUTTON) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void closeEvent(InventoryCloseEvent event) {
		try {
			if(event.getInventory().getLocation().add(0,-1,0).getBlock().getData() == 14) {
				if(event.getInventory().getType().equals(InventoryType.CHEST)) {
		            Block chest = event.getInventory().getLocation().getBlock();
					event.getInventory().clear();
		            chest.setType(Material.AIR);
		        }
			}
		} catch(Exception e) {
			
		}
	}
	
	public void firework(Location loc) {
		Location normal = loc;
		World world = loc.getWorld(); 
		
		new BukkitRunnable() {
			int time = 0;

			@Override
			public void run() {
				
				if(time == 10) {
					loc.getWorld().playSound(loc, Sound.ENTITY_FIREWORK_LAUNCH, 4.0f, 1.0f);
				}
				
				if(time == 20) {
					loc.getWorld().playSound(loc, Sound.ENTITY_FIREWORK_LARGE_BLAST, 2.0f, 1.0f);
				}
				
				if(time == 25) {
					loc.getWorld().playSound(loc, Sound.ENTITY_FIREWORK_TWINKLE, 2.0f, 1.0f);
				}
				
				if(time >= 40) {
					this.cancel();
				}
				
				time++;
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
		
		new BukkitRunnable() {
			int time = 0;
			int size = 0;

			@Override
			public void run() {
				Location e1;
				
				if(time % 2 == 0 && time < 20) {
					e1 = normal.clone().add(0, time/2, 0);
					world.spawnParticle(Particle.BLOCK_DUST, e1, 10,0,0,0,1);
				}
				
				if(time % 3 == 0 && time >= 20) {
					size++;
					double r = size;
					for (double pi = 0; pi <= Math.PI; pi += Math.PI / 5) {
						double y = r * Math.cos(pi) + 1.5;
						for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 10) {
							double x = r * Math.cos(theta) * Math.sin(pi);
							double z = r * Math.sin(theta) * Math.sin(pi);

							e1 = loc.clone().add(x, y+10, z);
							world.spawnParticle(Particle.REDSTONE, e1, 10,0,0,0,1);
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
	
	private Class<?> getNMSClass(String name) {
		try {
	        return Class.forName("net.minecraft.server."
	                + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
}