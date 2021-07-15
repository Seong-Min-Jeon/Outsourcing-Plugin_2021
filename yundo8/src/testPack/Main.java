package testPack;

import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;
import org.spigotmc.event.entity.EntityDismountEvent;

public class Main extends JavaPlugin implements Listener{
	
	Location button = null;
	boolean start = false;
	boolean hajimeru = false;
	int record = 0;
	int rate = 0;
	ArrayList<Player> part = new ArrayList<>();
	HashMap<Player, Integer> repeatSave = new HashMap<>(); 
	HashMap<Player, Integer> repeat = new HashMap<>();
	HashMap<Player, Integer> pit = new HashMap<>();
	ArrayList<Location> ary = new ArrayList<>();
	HashMap<Player, Block> map = new HashMap<>();
	HashMap<Player, Boolean> jump = new HashMap<>();
	ArrayList<Player> rank = new ArrayList<>();
	Random rnd = new Random();
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		
		getCommand("speed").setExecutor(new Cmd1());
		getCommand("speedall").setExecutor(new Cmd2());
		
		new BukkitRunnable() {
			boolean finish = false;
			
			@Override
			public void run() {
				
				for(Player player : Bukkit.getOnlinePlayers()) {
					if(repeat.containsKey(player)) {
						repeat.put(player, repeat.get(player)+1);
					} else {
						repeat.put(player, 0);
					}
				}
				
				if(hajimeru) {
					record++;
				}
				
				for(Player all : Bukkit.getOnlinePlayers()) {
					
					if(hajimeru) {
						all.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10, 3, true, false));
						
						if(all.isOnGround()) {
							jump.put(all, true);
						}
					}
					
					if(hajimeru) {
						
						if(hajimeru && rank.contains(all)) {
							all.setWalkSpeed(0);
						}
						
						// 부스터
						Block ground = all.getLocation().add(0,-1,0).getBlock();
						if(ground.getType() == Material.CONCRETE) {
							if(ground.getData() == 14) {
								
								try {
									if(map.get(all) != ground) {
										map.remove(all);
										map.put(all, ground);
										all.setWalkSpeed(all.getWalkSpeed() + 0.1F);
										all.getWorld().playSound(all.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3.0f, 2.0f);
										new BukkitRunnable() {
											int time = 0;
											
											@Override
											public void run() {
												time++;
												
												if(time >= 60) {
													all.setWalkSpeed(all.getWalkSpeed() - 0.1F);
													this.cancel();
												}
												
											}
										}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
									}
								} catch(Exception e) {
									
								}
							}
						}
						
						// 뒤로 점프
						if(ground.getType() == Material.CONCRETE) {
							if(ground.getData() == 10) {
								
								try {
									if(map.get(all) != ground) {
										map.remove(all);
										map.put(all, ground);
										all.getWorld().playSound(all.getLocation(), Sound.UI_BUTTON_CLICK, 3.0f, 2.0f);
										all.setVelocity(all.getLocation().getDirection().multiply(-3.0f).add(new Vector(0,0.2,0)));
									}
								} catch(Exception e) {
									
								}
							}
						}
						
//						// 피트스탑
//						if(ground.getType() == Material.CONCRETE) {
//							if(ground.getData() == 4) {
//								
//								if(repeatSave.containsKey(all)) {
//									if(pit.containsKey(all)) {
//										if(pit.get(all) >= 1 && pit.get(all) != repeatSave.get(all)) {
//											pit.put(all, 0);
//											for(Player all2 : Bukkit.getOnlinePlayers()) {
//												all2.sendMessage(ChatColor.RED + "[" + all.getDisplayName() + "] 실격! (사유: 피트스탑 2회)");
//											}
//										} else {
//											pit.put(all, repeatSave.get(all));
//											
//											if(rate == 0) {
//												all.setWalkSpeed(0.3F);
//											} else if(rate == 1) {
//												all.setWalkSpeed(0.5F);
//											}
//											all.getWorld().playSound(all.getLocation(), Sound.BLOCK_ANVIL_USE, 2.0f, 2.0f);
//										}
//									} else {
//										pit.put(all, repeatSave.get(all));
//										
//										for(Player all2 : Bukkit.getOnlinePlayers()) {
//											all2.sendMessage(ChatColor.YELLOW + "[" + all.getDisplayName() + "] 피트스탑! (다음 피트스탑에는 실격)");
//										}
//										
//										if(rate == 0) {
//											all.setWalkSpeed(0.3F);
//										} else if(rate == 1) {
//											all.setWalkSpeed(0.5F);
//										}
//										all.getWorld().playSound(all.getLocation(), Sound.BLOCK_ANVIL_USE, 2.0f, 2.0f);
//									}
//								}
//								
//							}
//						}
						
						// 결승점
						if(ground.getType() == Material.CONCRETE) {
							if(ground.getData() == 15) {
								if(repeat.containsKey(all)) {
									if(repeatSave.containsKey(all)) {
										if(repeat.get(all) - repeatSave.get(all) > 200 || repeatSave.get(all) > repeat.get(all)) {
											repeat.put(all, repeat.get(all)+10000000);
											repeatSave.put(all, repeat.get(all));
//											all.setWalkSpeed(all.getWalkSpeed() - 0.07F);
											for(Player all2 : Bukkit.getOnlinePlayers()) {
												int tmp = repeat.get(all)/10000000;
												if(tmp < 3) {
													all2.sendMessage(ChatColor.WHITE + "[" + all.getDisplayName() + "] " + tmp + "바퀴 통과!");
												}
											}
										}
									} else {
										repeat.put(all, repeat.get(all)+10000000);
										repeatSave.put(all, repeat.get(all));
//										all.setWalkSpeed(all.getWalkSpeed() - 0.07F);
										for(Player all2 : Bukkit.getOnlinePlayers()) {
											int tmp = repeat.get(all)/10000000;
											if(tmp < 3) {
												all2.sendMessage(ChatColor.WHITE + "[" + all.getDisplayName() + "] " + tmp + "바퀴 통과!");
											}
										}
									}
								}
								
								if(repeat.get(all) > 30000000) {
									if(!rank.contains(all)) {
										
										rank.add(all);
										all.getWorld().playSound(all.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3.0f, 2.0f);
										for(Player all2 : Bukkit.getOnlinePlayers()) {
											all2.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + all.getDisplayName() + " ("  + record/20 + "." + (record%20)/2 + "초)");
										}
										
										try {
											if(rank.size() == 1) {
												Location loc = all.getLocation();
												
												goldFirework(loc.clone().add(0,0,3));
												goldFirework(loc.clone().add(0,0,-3));
												redFirework(loc.clone().add(0,0,5));
												redFirework(loc.clone().add(0,0,-5));
											}
										} catch(Exception e) {
											
										}
									}
								}
							}
							if(ground.getData() == 15 && !finish && repeat.get(all) > 30000000) {

								finish = true;
								
								new BukkitRunnable() {
									int time = 0;

									@Override
									public void run() {
										time++;

										if (time == 141) {
											try {
												for(Player all : Bukkit.getOnlinePlayers()) {
													PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"§73\"}"));
													Object handle = all.getClass().getMethod("getHandle").invoke(all);
											        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
											        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
												}
											} catch(Exception e) {
												
											}
										}
										
										if (time == 161) {
											try {
												for(Player all : Bukkit.getOnlinePlayers()) {
													PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"§72\"}"));
													Object handle = all.getClass().getMethod("getHandle").invoke(all);
											        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
											        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
												}
											} catch(Exception e) {
												
											}
										}
										
										if (time == 181) {
											try {
												for(Player all : Bukkit.getOnlinePlayers()) {
													PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"§71\"}"));
													Object handle = all.getClass().getMethod("getHandle").invoke(all);
											        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
											        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
												}
											} catch(Exception e) {
												
											}
										}
										
										if (time == 200) {
											hajimeru = false;
											try {
												for(Player all : Bukkit.getOnlinePlayers()) {
													PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"§61등: " + rank.get(0).getDisplayName() + "\"}"));
													Object handle = all.getClass().getMethod("getHandle").invoke(all);
											        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
											        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
												}
											} catch(Exception e) {
												
											}
											firework(rank.get(0).getLocation());
										}

										if (time == 260) {
											try {
												start = false;
												hajimeru = false;
												finish = false;
												record = 0;
												rate = 0;
												ary.clear();
												map.clear();
												rank.clear();
												repeat.clear();
												repeatSave.clear();
												part.clear();
												pit.clear();
												new Speed().clear();
												for(Player target : Bukkit.getOnlinePlayers()) {
													target.setWalkSpeed(0.3f);
													target.setFlySpeed(0.1f);
												}
												
												for(Player all : Bukkit.getOnlinePlayers()) {
													all.teleport(button.add(0,2,0));
												}
											} catch(Exception e) {
												
											}
										}
										
									}
								}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);

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
	public void joinEvent(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.teleport(player.getWorld().getSpawnLocation());
		player.setWalkSpeed(0.3f);
	}
	
	@EventHandler
	public void mouseEvent(PlayerInteractEvent event) {
		try {
			EquipmentSlot e = event.getHand();
			if (e.equals(EquipmentSlot.HAND)) {
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					
					if(hajimeru && part.contains(event.getPlayer())) {
						if(event.getPlayer().isOnGround()) {
							event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().add(new Vector(0, 1.0, 0)));
						} else {
							if(jump.get(event.getPlayer())) {
								event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().add(new Vector(0, 1.5, 0)));
								jump.put(event.getPlayer(), false);
							}
						}
					}
					
					if(event.getClickedBlock().getType() == Material.STONE_BUTTON) {
						
						if(start) {
							return;
						}
						
						start = true;
						ary.clear();
						map.clear();
						
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "5초 후에 출발선으로 이동됩니다.");
						}
						
						Player player = event.getPlayer();
						World world = player.getWorld();
						Location loc;
						
						for(int i = -100 ; i <= 100 ; i++) {
							for(int j = 60 ; j <= 110 ; j++) {
								for(int k = -100 ; k <= 100 ; k++) {
									loc = new Location(world,i,j,k);
									if(loc.getBlock().getType() == Material.CONCRETE) {
										if(loc.getBlock().getData() == 0) {
											ary.add(loc);
										}
									}
								}
							}
						}
						
						if(ary.size() == 0) {
							player.sendMessage(ChatColor.RED + "출발선이 없습니다. 출발선은 하얀 콘크리트로 만들어주세요.");
							start = false;
							return;
						}
						
						button = event.getClickedBlock().getLocation();
						
						new BukkitRunnable() {
							int time = 0;
							
							@Override
							public void run() {
								time++;
								
								if(time == 100) {
									
									int cnt = 0;
									
									try {
										for(Player all : Bukkit.getOnlinePlayers()) {
											if(all.getLocation().add(0,-1,0).getBlock().getType() == Material.CONCRETE) {
												if(all.getLocation().add(0,-1,0).getBlock().getData() == 13) {
													int num = rnd.nextInt(ary.size());
													Location startLoc = ary.get(num).add(0.5,1,0.5);
													startLoc.setPitch(0);
													startLoc.setYaw(180);
													all.teleport(startLoc);
													all.setWalkSpeed(0);
													ary.remove(ary.get(num));
													part.add(all);
													cnt++;
												}
											}
										}
									} catch(Exception e) {
										
									}
									
									if(cnt == 0) {
										for(Player all : Bukkit.getOnlinePlayers()) {
											all.sendMessage(ChatColor.RED + "참가자가 없어 게임이 초기화됩니다.");
										}
										start = false;
										this.cancel();
									}
									
								}
								
								if(time == 180) {
									try {
										for(Player all : Bukkit.getOnlinePlayers()) {
											if(all.getLocation().add(0,-1,0).getBlock().getType() == Material.CONCRETE) {
												if(all.getLocation().add(0,-1,0).getBlock().getData() == 0) {
													PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
															ChatSerializer.a("{\"text\":\"§c·\"}"));
													Object handle = all.getClass().getMethod("getHandle").invoke(all);
											        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
											        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
											        
											        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 0.4f);
												}
											}
										}
									} catch(Exception e) {
										
									}
								}
								
								if(time == 220) {
									try {
										for(Player all : Bukkit.getOnlinePlayers()) {
											if(all.getLocation().add(0,-1,0).getBlock().getType() == Material.CONCRETE) {
												if(all.getLocation().add(0,-1,0).getBlock().getData() == 0) {
													PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
															ChatSerializer.a("{\"text\":\"§c··\"}"));
													Object handle = all.getClass().getMethod("getHandle").invoke(all);
											        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
											        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
											        
											        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 0.4f);
												}
											}
										}
									} catch(Exception e) {
										
									}
								}
								
								if(time == 260) {
									try {
										for(Player all : Bukkit.getOnlinePlayers()) {
											if(all.getLocation().add(0,-1,0).getBlock().getType() == Material.CONCRETE) {
												if(all.getLocation().add(0,-1,0).getBlock().getData() == 0) {
													PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
															ChatSerializer.a("{\"text\":\"§c···\"}"));
													Object handle = all.getClass().getMethod("getHandle").invoke(all);
											        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
											        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
											        
											        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 0.4f);
												}
											}
										}
									} catch(Exception e) {
										
									}
								}
								
								if(time >= 300) {
									try {
										for(Player all : Bukkit.getOnlinePlayers()) {
											if(all.getLocation().add(0,-1,0).getBlock().getType() == Material.CONCRETE) {
												if(all.getLocation().add(0,-1,0).getBlock().getData() == 0) {
													PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
															ChatSerializer.a("{\"text\":\"§a···\"}"), 1, 10, 1);
													Object handle = all.getClass().getMethod("getHandle").invoke(all);
											        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
											        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
											        
											        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 2.0f);
												}
											}
											
											all.setWalkSpeed((float) new Speed().getSpeed(player));
										}
									} catch(Exception e) {
										
									}
									
									hajimeru = true;
									rate = 0;
									
									this.cancel();
								}
								
							}
						}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
						
					} else if(event.getClickedBlock().getType() == Material.WOOD_BUTTON) {
						
						if(start) {
							return;
						}
						
						start = true;
						ary.clear();
						map.clear();
						
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "5초 후에 출발선으로 이동됩니다.");
						}
						
						Player player = event.getPlayer();
						World world = player.getWorld();
						Location loc;
						
						for(int i = -100 ; i <= 100 ; i++) {
							for(int j = 60 ; j <= 110 ; j++) {
								for(int k = -100 ; k <= 100 ; k++) {
									loc = new Location(world,i,j,k);
									if(loc.getBlock().getType() == Material.CONCRETE) {
										if(loc.getBlock().getData() == 0) {
											ary.add(loc);
										}
									}
								}
							}
						}
						
						if(ary.size() == 0) {
							player.sendMessage(ChatColor.RED + "출발선이 없습니다. 출발선은 하얀 콘크리트로 만들어주세요.");
							start = false;
							return;
						}
						
						button = event.getClickedBlock().getLocation();
						
						for(Player target : Bukkit.getOnlinePlayers()) {
							new Speed().addMap(target, 0.5F);
							target.setFlySpeed(0.5f);
						}
						
						new BukkitRunnable() {
							int time = 0;
							
							@Override
							public void run() {
								time++;
								
								if(time == 100) {
									
									int cnt = 0;
									
									try {
										for(Player all : Bukkit.getOnlinePlayers()) {
											if(all.getLocation().add(0,-1,0).getBlock().getType() == Material.CONCRETE) {
												if(all.getLocation().add(0,-1,0).getBlock().getData() == 13) {
													int num = rnd.nextInt(ary.size());
													Location startLoc = ary.get(num).add(0.5,1,0.5);
													startLoc.setPitch(0);
													startLoc.setYaw(180);
													all.teleport(startLoc);
													all.setWalkSpeed(0);
													part.add(all);
													ary.remove(ary.get(num));
													cnt++;
												}
											}
										}
									} catch(Exception e) {
										
									}
									
									if(cnt == 0) {
										for(Player all : Bukkit.getOnlinePlayers()) {
											all.sendMessage(ChatColor.RED + "참가자가 없어 게임이 초기화됩니다.");
										}
										start = false;
										this.cancel();
									}
									
								}
								
								if(time == 180) {
									try {
										for(Player all : Bukkit.getOnlinePlayers()) {
											if(all.getLocation().add(0,-1,0).getBlock().getType() == Material.CONCRETE) {
												if(all.getLocation().add(0,-1,0).getBlock().getData() == 0) {
													PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
															ChatSerializer.a("{\"text\":\"§c·\"}"));
													Object handle = all.getClass().getMethod("getHandle").invoke(all);
											        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
											        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
											        
											        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 0.4f);
												}
											}
										}
									} catch(Exception e) {
										
									}
								}
								
								if(time == 220) {
									try {
										for(Player all : Bukkit.getOnlinePlayers()) {
											if(all.getLocation().add(0,-1,0).getBlock().getType() == Material.CONCRETE) {
												if(all.getLocation().add(0,-1,0).getBlock().getData() == 0) {
													PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
															ChatSerializer.a("{\"text\":\"§c··\"}"));
													Object handle = all.getClass().getMethod("getHandle").invoke(all);
											        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
											        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
											        
											        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 0.4f);
												}
											}
										}
									} catch(Exception e) {
										
									}
								}
								
								if(time == 260) {
									try {
										for(Player all : Bukkit.getOnlinePlayers()) {
											if(all.getLocation().add(0,-1,0).getBlock().getType() == Material.CONCRETE) {
												if(all.getLocation().add(0,-1,0).getBlock().getData() == 0) {
													PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
															ChatSerializer.a("{\"text\":\"§c···\"}"));
													Object handle = all.getClass().getMethod("getHandle").invoke(all);
											        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
											        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
											        
											        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 0.4f);
												}
											}
										}
									} catch(Exception e) {
										
									}
								}
								
								if(time >= 300) {
									try {
										for(Player all : Bukkit.getOnlinePlayers()) {
											if(all.getLocation().add(0,-1,0).getBlock().getType() == Material.CONCRETE) {
												if(all.getLocation().add(0,-1,0).getBlock().getData() == 0) {
													PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
															ChatSerializer.a("{\"text\":\"§a···\"}"), 1, 10, 1);
													Object handle = all.getClass().getMethod("getHandle").invoke(all);
											        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
											        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
											        
											        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 2.0f);
												}
											}
											
											all.setWalkSpeed((float) new Speed().getSpeed(player));
										}
									} catch(Exception e) {
										
									}
									
									hajimeru = true;
									rate = 1;
									
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
	public void damageEvent(EntityDamageEvent event) {
		if(event.getCause() == DamageCause.FALL) {
			event.setCancelled(true);
		} else if(event.getCause() == DamageCause.ENTITY_ATTACK) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void consoleEvent(ServerCommandEvent event) {
		try { 
			if(event.getCommand().split(" ")[0].equals("speed")) {
				String name = event.getCommand().split(" ")[1];
				int value = Integer.parseInt(event.getCommand().split(" ")[2]);
				Player target = Bukkit.getPlayer(name);
				
				if(Bukkit.getOnlinePlayers().contains(target)) {
					if(value == 1) {
						target.setWalkSpeed(0.3f);
						target.setFlySpeed(0.3f);
						System.out.println("1단계로 적용되었습니다.");
					} else if(value == 2) {
						target.setWalkSpeed(0.4f);
						target.setFlySpeed(0.4f);
						System.out.println("2단계로 적용되었습니다.");
					} else if(value == 3) {
						target.setWalkSpeed(0.5f);
						target.setFlySpeed(0.5f);
						System.out.println("3단계로 적용되었습니다.");
					} else if(value == 4) {
						target.setWalkSpeed(0.6f);
						target.setFlySpeed(0.6f);
						System.out.println("4단계로 적용되었습니다.");
					} else if(value == 5) {
						target.setWalkSpeed(0.7f);
						target.setFlySpeed(0.7f);
						System.out.println("5단계로 적용되었습니다.");
					} else if(value == 0) {
						target.setWalkSpeed(0.1f);
						target.setFlySpeed(0.1f);
						System.out.println("-1단계로 적용되었습니다.");
					} else {
						target.setWalkSpeed(0.3f);
						target.setFlySpeed(0.3f);
						System.out.println("한계치를 넘어 1단계로 적용되었습니다.");
					}
				} else {
					System.out.println("서버에 해당 캐릭터는 없습니다!");
				}
			}
		} catch(Exception e) {
			
		}
		
		try { 
			if(event.getCommand().split(" ")[0].equals("speedall")) {
				int value = Integer.parseInt(event.getCommand().split(" ")[1]);

				for(Player target : Bukkit.getOnlinePlayers()) {
					if(value == 1) {
						target.setWalkSpeed(0.3f);
						target.setFlySpeed(0.3f);
					} else if(value == 2) {
						target.setWalkSpeed(0.4f);
						target.setFlySpeed(0.4f);
					} else if(value == 3) {
						target.setWalkSpeed(0.5f);
						target.setFlySpeed(0.5f);
					} else if(value == 4) {
						target.setWalkSpeed(0.6f);
						target.setFlySpeed(0.6f);
					} else if(value == 5) {
						target.setWalkSpeed(0.7f);
						target.setFlySpeed(0.7f);
					} else if(value == 0) {
						target.setWalkSpeed(0.1f);
						target.setFlySpeed(0.1f);
					} else {
						target.setWalkSpeed(0.3f);
						target.setFlySpeed(0.3f);
					}
				}
				if(value == 1) {
					System.out.println("모두의 속도가 1단계로 적용되었습니다.");
				} else if(value == 2) {
					System.out.println("모두의 속도가 2단계로 적용되었습니다.");
				} else if(value == 3) {
					System.out.println("모두의 속도가 3단계로 적용되었습니다.");
				} else if(value == 4) {
					System.out.println("모두의 속도가 4단계로 적용되었습니다.");
				} else if(value == 5) {
					System.out.println("모두의 속도가 5단계로 적용되었습니다.");
				} else if(value == 0) {
					System.out.println("모두의 속도가 -1단계로 적용되었습니다.");
				} else {
					System.out.println("모두의 속도가 한계치를 넘어 1단계로 적용되었습니다.");
				}
			}
		} catch(Exception e) {
			
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
//		if(part.contains(event.getPlayer())) {
//			if (event.getTo().getY() > event.getFrom().getY()) {
//				event.getTo().setY(event.getFrom().getY());
//			}
//		}
		if(event.getPlayer().getWalkSpeed() == 0 &&
				(event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockY() != event.getTo().getBlockY() || event.getFrom().getBlockZ() != event.getTo().getBlockZ())) {
			event.setCancelled(true);
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

							e1 = loc.clone().add(x, y+5, z);
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
	
	public void redFirework(Location loc) {
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
					world.spawnParticle(Particle.BLOCK_DUST, e1, 0,1,0,0,1);
				}
				
				if(time % 3 == 0 && time >= 20) {
					size++;
					double r = size;
					for (double pi = 0; pi <= Math.PI; pi += Math.PI / 5) {
						double y = r * Math.cos(pi) + 1.5;
						for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 10) {
							double x = r * Math.cos(theta) * Math.sin(pi);
							double z = r * Math.sin(theta) * Math.sin(pi);

							e1 = loc.clone().add(x, y+5, z);
							world.spawnParticle(Particle.REDSTONE, e1, 0,1,0,0,1);
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
	
	public void goldFirework(Location loc) {
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
					world.spawnParticle(Particle.BLOCK_DUST, e1, 0,0.6,0.6,0,1);
				}
				
				if(time % 3 == 0 && time >= 20) {
					size++;
					double r = size;
					for (double pi = 0; pi <= Math.PI; pi += Math.PI / 5) {
						double y = r * Math.cos(pi) + 1.5;
						for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 10) {
							double x = r * Math.cos(theta) * Math.sin(pi);
							double z = r * Math.sin(theta) * Math.sin(pi);

							e1 = loc.clone().add(x, y+5, z);
							world.spawnParticle(Particle.REDSTONE, e1, 0,0.6,0.6,0,1);
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