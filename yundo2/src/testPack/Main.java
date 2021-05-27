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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
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
	ArrayList<Location> ary = new ArrayList<>();
	HashMap<Player, Block> map = new HashMap<>();
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
				
				if(hajimeru) {
					record++;
				}
				
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(all.getVehicle() != null) {
						// 움직임
						if(hajimeru && !rank.contains(all)) {
							all.getVehicle().setVelocity(all.getVelocity().multiply(
									new Vector(
											(new Speed()).getSpeed(all),
											0,
											(new Speed()).getSpeed(all)
											)));
						}
						
						// 부스터
						Block ground = all.getLocation().add(0,-1,0).getBlock();
						if(ground.getType() == Material.CONCRETE) {
							if(ground.getData() == 14) {
								
								if(map.get(all) != ground) {
									map.remove(all);
									map.put(all, ground);
									new Speed().addMap(all, new Speed().getSpeed(all) + 10);
									new BukkitRunnable() {
										int time = 0;
										
										@Override
										public void run() {
											time++;
											
											if(time >= 60) {
												new Speed().addMap(all, new Speed().getSpeed(all) - 10);
												this.cancel();
											}
											
										}
									}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
								}
							}
						}
						
						// 결승점
						if(ground.getType() == Material.CONCRETE) {
							if(ground.getData() == 15) {
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
							if(ground.getData() == 15 && !finish) {

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
												ary.clear();
												map.clear();
												rank.clear();
												new Speed().clear();
												
												for(Player all : Bukkit.getOnlinePlayers()) {
													try {
														all.getVehicle().remove();
													} catch(Exception e) {
														
													}
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
	}
	
	@EventHandler
	public void mouseEvent(PlayerInteractEvent event) {
		try {
			EquipmentSlot e = event.getHand();
			if (e.equals(EquipmentSlot.HAND)) {
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
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
							for(int j = 30 ; j <= 80 ; j++) {
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
													ary.remove(ary.get(num));
													cnt++;
													
													Pig horse = (Pig) player.getWorld().spawnEntity(startLoc.add(0,0.1,0), EntityType.PIG);
													horse.setMaxHealth(1);
													horse.setBaby();
													horse.setSilent(true);
													horse.setGravity(false);
													horse.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false));
													
													horse.addPassenger(all);
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
										}
									} catch(Exception e) {
										
									}
									
									hajimeru = true;
									
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
							for(int j = 30 ; j <= 80 ; j++) {
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
							new Speed().addMap(target, 100);
							target.setFlySpeed(0.7f);
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
													ary.remove(ary.get(num));
													cnt++;
													
													Pig horse = (Pig) player.getWorld().spawnEntity(startLoc.add(0,0.1,0), EntityType.PIG);
													horse.setMaxHealth(1);
													horse.setBaby();
													horse.setSilent(true);
													horse.setGravity(false);
													horse.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false));
													
													horse.addPassenger(all);
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
										}
									} catch(Exception e) {
										
									}
									
									hajimeru = true;
									
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
	public void dismountEvent(EntityDismountEvent event) {
		if(start) 
			event.getDismounted().addPassenger(event.getEntity());
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
						new Speed().addMap(target, 70);
						target.setFlySpeed(0.4f);
						System.out.println("1단계로 적용되었습니다.");
					} else if(value == 2) {
						new Speed().addMap(target, 85);
						target.setFlySpeed(0.55f);
						System.out.println("2단계로 적용되었습니다.");
					} else if(value == 3) {
						new Speed().addMap(target, 100);
						target.setFlySpeed(0.7f);
						System.out.println("3단계로 적용되었습니다.");
					} else if(value == 4) {
						new Speed().addMap(target, 115);
						target.setFlySpeed(0.85f);
						System.out.println("4단계로 적용되었습니다.");
					} else if(value == 5) {
						new Speed().addMap(target, 130);
						target.setFlySpeed(1.0f);
						System.out.println("5단계로 적용되었습니다.");
					} else if(value == 0) {
						new Speed().addMap(target, 30);
						target.setFlySpeed(0.1f);
						System.out.println("-1단계로 적용되었습니다.");
					} else {
						new Speed().addMap(target, 70);
						target.setFlySpeed(0.4f);
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
						new Speed().addMap(target, 70);
						target.setFlySpeed(0.4f);
					} else if(value == 2) {
						new Speed().addMap(target, 85);
						target.setFlySpeed(0.55f);
					} else if(value == 3) {
						new Speed().addMap(target, 100);
						target.setFlySpeed(0.7f);
					} else if(value == 4) {
						new Speed().addMap(target, 115);
						target.setFlySpeed(0.85f);
					} else if(value == 5) {
						new Speed().addMap(target, 130);
						target.setFlySpeed(1.0f);
					} else if(value == 0) {
						new Speed().addMap(target, 30);
						target.setFlySpeed(0.1f);
					} else {
						new Speed().addMap(target, 70);
						target.setFlySpeed(0.4f);
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