package testPack;

import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

import java.util.ArrayList;
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
import org.bukkit.material.Button;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

//gamerule doImmediateRespawn true

public class Main extends JavaPlugin implements Listener{
	
	ArrayList<Location> ary = new ArrayList<>();
	ArrayList<String> banList = new ArrayList<>();
	boolean start = false;
	int timer = 0;
 
	Random rnd = new Random();
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(start) {
					timer++;
					
					if(new Bar().bar1.getProgress() != 1) {
						new Bar().bar1.setProgress(timer/12000.0);
						new Bar().bar1.setTitle(ChatColor.RED + "" + timer/1200 + "분 " + timer%1200/20 + "초");
					}
					
					if(timer % 4000 == 0) {
						//폭격
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendMessage(ChatColor.RED + "유니버스 폭격기가 무인도를 폭격합니다!");
						}
						World world = Bukkit.getWorld("world");
						
						new BukkitRunnable() {
							
							int cnt = 0;
							
							@Override
							public void run() {
								if(cnt == 0) {
									for(int i = 0 ; i < 100 ; i++) {
										int x = rnd.nextInt(256) - 128;
										int z = rnd.nextInt(256) - 128;
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
									for(Entity ent : as.getNearbyEntities(128, 128, 128)) {
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
					
					if(timer % 4210 == 0) {
						World world = Bukkit.getWorld("world");
						
						new BukkitRunnable() {
							
							int cnt = 0;
							
							@Override
							public void run() {
								if(cnt == 0) {
									for(int i = 0 ; i < 100 ; i++) {
										int x = rnd.nextInt(256) - 128;
										int z = rnd.nextInt(256) - 128;
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
									for(Entity ent : as.getNearbyEntities(128, 128, 128)) {
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
					
					if(timer % 4420 == 0) {
						World world = Bukkit.getWorld("world");
						
						new BukkitRunnable() {
							
							int cnt = 0;
							
							@Override
							public void run() {
								if(cnt == 0) {
									for(int i = 0 ; i < 100 ; i++) {
										int x = rnd.nextInt(256) - 128;
										int z = rnd.nextInt(256) - 128;
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
									for(Entity ent : as.getNearbyEntities(128, 128, 128)) {
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
					
					if(timer == 9600) {
						//보급
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendMessage(ChatColor.GREEN + "보급이 활성화되었습니다!");
						}
						
						World world = Bukkit.getWorld("world");
						Location loc = null;
						for(int i = -128 ; i <= 128 ; i++) {
							for(int j = 40 ; j <= 200 ; j++) {
								for(int k = -128 ; k <= 128 ; k++) {
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
					
					if(timer == 12000) {
						// 종료버튼
						World world = Bukkit.getWorld("world");
						Location loc = new Location(world, 75, 85, 67);
						loc.getBlock().setType(Material.STONE_BUTTON);
						Block block = loc.getBlock();
						BlockState bs = block.getState();
						Button but = (Button) bs.getData();
						but.setFacingDirection(BlockFace.UP);
						bs.setData(but);
						bs.update(true);
						
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendMessage(ChatColor.BOLD + "맵 중앙 헬기장에 탈출을 위한 돌 버튼이 생성됩니다.");
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
		player.setGameMode(GameMode.SURVIVAL);
		
		if(banList.contains(player.getDisplayName()) || start) {
			event.setJoinMessage(null);
			player.kickPlayer(ChatColor.RED + "이번 게임에는 참여할 수 없습니다.");
		} else {
			new Bar().bar1.addPlayer(player);
			new Bar().bar1.setVisible(false);
		}
		
		player.getInventory().clear();
		player.teleport(new Location(player.getWorld(),83.5,6,-55.5,270,0));
	}
	
	@EventHandler
	public void respawn(PlayerRespawnEvent event) {
		event.getPlayer().getInventory().clear();
		event.setRespawnLocation(new Location(event.getPlayer().getWorld(),83.5,6,-55.5,270,0));
	}
	
	@EventHandler
	public void die(PlayerDeathEvent event) {
		event.setDeathMessage(null);
		
		try {
			Player player = event.getEntity();
			EntityDamageEvent death = player.getLastDamageCause();
			DamageCause deathCause = player.getLastDamageCause().getCause();
			
			if(deathCause == DamageCause.ENTITY_ATTACK) {
				Entity entity = (((EntityDamageByEntityEvent)death).getDamager());
				if(entity instanceof Player) {
					Player p = (Player) entity;
					for(Player all : Bukkit.getOnlinePlayers()) {
						all.sendMessage(ChatColor.DARK_RED + "" + event.getEntity().getDisplayName() + "님이 "+ p.getDisplayName() + "님에게 살해당하였습니다.");
						System.out.println(event.getEntity().getDisplayName() + "님이 "+ p.getDisplayName() + "님에게 살해당하였습니다.");
					}
				} else {
					for(Player all : Bukkit.getOnlinePlayers()) {
						all.sendMessage(ChatColor.DARK_RED + "" + event.getEntity().getDisplayName() + "님이 의문사하였습니다.");
						System.out.println(event.getEntity().getDisplayName() + "님이 의문사하였습니다.");
					}
				}
			} else if(deathCause == DamageCause.BLOCK_EXPLOSION) {
				for(Player all : Bukkit.getOnlinePlayers()) {
					all.sendMessage(ChatColor.DARK_RED + "" + event.getEntity().getDisplayName() + "님이 폭격으로 사망하였습니다.");
					System.out.println(event.getEntity().getDisplayName() + "님이 폭격으로 사망하였습니다.");
				}
			} else if(deathCause == DamageCause.ENTITY_EXPLOSION) {
				for(Player all : Bukkit.getOnlinePlayers()) {
					all.sendMessage(ChatColor.DARK_RED + "" + event.getEntity().getDisplayName() + "님이 폭격으로 사망하였습니다.");
					System.out.println(event.getEntity().getDisplayName() + "님이 폭격으로 사망하였습니다.");
				}
			} else if(deathCause == DamageCause.CUSTOM) {
				for(Player all : Bukkit.getOnlinePlayers()) {
					all.sendMessage(ChatColor.DARK_RED + "" + event.getEntity().getDisplayName() + "님이 폭격으로 사망하였습니다.");
					System.out.println(event.getEntity().getDisplayName() + "님이 폭격으로 사망하였습니다.");
				}
			} else if(deathCause == DamageCause.FALL) {
				for(Player all : Bukkit.getOnlinePlayers()) {
					all.sendMessage(ChatColor.DARK_RED + "" + event.getEntity().getDisplayName() + "님이 낙사하였습니다.");
					System.out.println(event.getEntity().getDisplayName() + "님이 낙사하였습니다.");
				}
			} else {
				for(Player all : Bukkit.getOnlinePlayers()) {
					all.sendMessage(ChatColor.DARK_RED + "" + event.getEntity().getDisplayName() + "님이 의문사하였습니다.");
					System.out.println(event.getEntity().getDisplayName() + "님이 의문사하였습니다.");
				}
			}
		} catch(Exception e) {
			
		}
		
		Location loc = event.getEntity().getLocation();
		if (!(loc.getX() <= 103 && loc.getY() <= 13 && loc.getZ() <= -46 
				&& loc.getX() >= 80 && loc.getY() >= 0 && loc.getZ() >= -64)) {
			try {
				Player player = (Player) event.getEntity();
				player.kickPlayer(ChatColor.RED + "You Died");
				
				banList.add(player.getDisplayName());
			} catch(Exception e) {
				
			}
		}
		
	}
	
	@EventHandler
	public void mouseEvent(PlayerInteractEvent event) {
		try {
			EquipmentSlot e = event.getHand();
			if (e.equals(EquipmentSlot.HAND)) {
				Player player = event.getPlayer();
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if(event.getClickedBlock().getType() == Material.STONE_BUTTON) {
						Block b = event.getClickedBlock();
						Location loc = b.getLocation();
						if (loc.getX() <= 103 && loc.getY() <= 13 && loc.getZ() <= -46 
								&& loc.getX() >= 80 && loc.getY() >= 0 && loc.getZ() >= -64) {
							
							if(start) {
								boolean allDied = false;
								for(Player all : Bukkit.getOnlinePlayers()) {
									Location loc2 = all.getLocation();
									if (!(loc2.getX() <= 103 && loc2.getY() <= 13 && loc2.getZ() <= -46 
											&& loc2.getX() >= 80 && loc2.getY() >= 0 && loc2.getZ() >= -64)) {
										allDied = true;
									}
								}
								
								if(!allDied) {
									player.sendMessage(ChatColor.RED + "아직 경기가 진행 중입니다.");
									return;
								}
							}
							
							start = true;
							
							new Bar().bar1.setVisible(true);
							new Bar().bar1.setProgress(0);
							
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
														ChatSerializer.a("{\"text\":\"§7Survive!\"}"));
												Object handle = all.getClass().getMethod("getHandle").invoke(all);
										        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
										        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
											}
										} catch(Exception e) {
											
										}
										
										int cnt = 0;
										World world = player.getWorld();
										Location loc;
										
										for(int i = -128 ; i <= 128 ; i++) {
											for(int j = 64 ; j <= 70 ; j++) {
												for(int k = -128 ; k <= 128 ; k++) {
													loc = new Location(world,i,j,k);
													if(loc.getBlock().getType() == Material.GRASS) {
														if(loc.getBlock().getLocation().add(0,1,0).getBlock().getType() == Material.AIR) {
															ary.add(loc);
														}
													}
												}
											}
										}
										
										for(Player all : Bukkit.getOnlinePlayers()) {
											if(all.getLocation().add(0,-1,0).getBlock().getType() == Material.CONCRETE || all.getLocation().add(0,-2,0).getBlock().getType() == Material.CONCRETE) {
												if(all.getLocation().add(0,-1,0).getBlock().getData() == 13 || all.getLocation().add(0,-2,0).getBlock().getData() == 13) {
													int num = rnd.nextInt(ary.size());
													Location startLoc = ary.get(num).add(0.5,1,0.5);
													all.teleport(startLoc);
													ary.remove(ary.get(num));
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
						} else if(loc.getX() <= 80 && loc.getY() <= 86 && loc.getZ() <= 72 
								&& loc.getX() >= 70 && loc.getY() >= 84 && loc.getZ() >= 62) {
							
							new Bar().bar1.setVisible(false);
							new Bar().bar1.setProgress(0);

							event.getClickedBlock().setType(Material.AIR);
							
							new BukkitRunnable() {
								int time = 0;
								
								@Override
								public void run() {
									time++;
									
									if(time == 1) {
										try {
											for(Player all : Bukkit.getOnlinePlayers()) {
												PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
														ChatSerializer.a("{\"text\":\"§7게임이 종료되었습니다.\"}"));
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
														ChatSerializer.a("{\"text\":\"§7우승자: " + player.getDisplayName() + "\"}"));
												Object handle = all.getClass().getMethod("getHandle").invoke(all);
										        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
										        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
											}
										} catch(Exception e) {
											
										}
										firework(player.getLocation());
										firework(new Location(player.getWorld(), 80, 85, 72));
										firework(new Location(player.getWorld(), 80, 85, 62));
										firework(new Location(player.getWorld(), 70, 85, 72));
										firework(new Location(player.getWorld(), 70, 85, 62));
									}
									
									if(time >= 81) {
										for(Player all : Bukkit.getOnlinePlayers()) {
											all.teleport(new Location(player.getWorld(), 83.5,6,-55.5,270,0));
										}
										ary.clear();
										banList.clear();
										start = false;
										timer = 0;
										
										World world = Bukkit.getWorld("world");
										Location loc = null;
										for(int i = -128 ; i <= 128 ; i++) {
											for(int j = 40 ; j <= 200 ; j++) {
												for(int k = -128 ; k <= 128 ; k++) {
													loc = new Location(world,i,j,k);
													if(loc.getBlock().getType() == Material.CHEST) {
														Block block = loc.getBlock();
														Chest chest = (Chest) block.getState();
														chest.getInventory().clear();
														loc.getBlock().setType(Material.AIR);
													}
												}
											}
										}
										
										this.cancel();
									}
									
								}
							}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
						}
					}
				}
			}
		} catch(Exception e) {
			
		}
	}
	
	@EventHandler
	public void blockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if(!player.isOp()) {
			if(event.getBlock().getType() == Material.STONE_BUTTON || event.getBlock().getType() == Material.WOOD_BUTTON) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void closeEvent(InventoryCloseEvent event) {
//		if(event.getInventory().getType() == InventoryType.CHEST) {
//			Player player = (Player) event.getPlayer();
//			player.getTargetBlock(null, 100);
//		}
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