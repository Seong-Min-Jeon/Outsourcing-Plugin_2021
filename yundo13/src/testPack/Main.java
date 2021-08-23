package testPack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.nametagedit.plugin.NametagEdit;

import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutAnimation;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

//gamerule doImmediateRespawn true

public class Main extends JavaPlugin implements Listener{
	
	ArrayList<Location> aryR = new ArrayList<>();
	ArrayList<Location> aryB = new ArrayList<>();
	Location startLoc = null;
	Player blue = null;
	Player red = null;
	boolean stunR = false;
	boolean stunB = false;
	boolean stunLockR = false;
	boolean stunLockB = false;
	boolean atkLockR = false;
	boolean atkLockB = false;

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		
		new BukkitRunnable() {
			int time = 0;

			@Override
			public void run() {
				time++;

				if (time % 20 == 0) {
					new CoolTime().countTime();
					
					for(Player all : Bukkit.getOnlinePlayers()) {
						if(!isFighting(all)) {
							all.setNoDamageTicks(40);
						}
					}
				}
				
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				try {
					new Bar().bar1.setProgress(red.getHealth()/red.getMaxHealth());
					new Bar().bar1.setTitle(ChatColor.RED + "" + red.getDisplayName());
				} catch(Exception e) {
					new Bar().bar1.setProgress(0);
					new Bar().bar1.setTitle(ChatColor.RED + "Health");
				}
				
				try {
					new Bar().bar2.setProgress(blue.getHealth()/blue.getMaxHealth());
					new Bar().bar2.setTitle(ChatColor.AQUA + "" + blue.getDisplayName());
				} catch(Exception e) {
					new Bar().bar2.setProgress(0);
					new Bar().bar2.setTitle(ChatColor.AQUA + "Health");
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
		
		new Bar().bar1.addPlayer(player);
		new Bar().bar2.addPlayer(player);
		new Bar().bar1.setVisible(true);
		new Bar().bar2.setVisible(true);
	}
	
	@EventHandler
	public void die(PlayerDeathEvent event) {
		event.setDeathMessage(null);
		
		if(event.getEntity() == red) {
			try {
				for(Player all : Bukkit.getOnlinePlayers()) {
					PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
							ChatSerializer.a("{\"text\":\"§b" + blue.getDisplayName() + " 승리!\"}"));
					Object handle = all.getClass().getMethod("getHandle").invoke(all);
			        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
				}
				
				red.teleport(startLoc);
				blue.teleport(startLoc);
				aryR.clear();
				aryB.clear();
				blue = null;
				red = null;
				stunR = false;
				stunB = false;
				stunLockR = false;
				stunLockB = false;
				
			} catch(Exception e) {
				
			}
		} else if(event.getEntity() == blue) {
			try {
				for(Player all : Bukkit.getOnlinePlayers()) {
					PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
							ChatSerializer.a("{\"text\":\"§c" + red.getDisplayName() + " 승리!\"}"));
					Object handle = all.getClass().getMethod("getHandle").invoke(all);
			        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
				}
				
				red.teleport(startLoc);
				blue.teleport(startLoc);
				aryR.clear();
				aryB.clear();
				blue = null;
				red = null;
				stunR = false;
				stunB = false;
				stunLockR = false;
				stunLockB = false;
				
			} catch(Exception e) {
				
			}
		}
	}
	
	@EventHandler
	public void damageEvent(EntityDamageByEntityEvent event) {
		try {
			if(event.getDamager() instanceof Player) {
				Player damager = (Player) event.getDamager();
				if(event.getEntity() instanceof Player) {
					Player entity = (Player) event.getEntity();
					event.setCancelled(true);
				}
			}
		} catch(Exception e) {
			
		}
	}
	
	@EventHandler
	public void damageEvent2(EntityDamageEvent event) {
		try {
			if(event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				if(player == red) {
					if(player.isSneaking() && !stunB && !stunLockR) {
						sendPacket(player, ChatColor.GOLD + "가드 성공!");
						sendPacket(blue, ChatColor.DARK_RED + "상대의 가드!");
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1.0f, 1.0f);
						
						new BukkitRunnable() {
							int time = 0;

							@Override
							public void run() {
								time++;
								
								if(time < 20) {
									stunB = true;
								}
								
								if(time == 20) {
									stunB = false;
								}
								
								if(time < 60) {
									stunLockR = true;
								}
								
								if(time >= 60) {
									stunB = false;
									stunLockR = false;
									
									this.cancel();
								}
								
							}
						}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
					}
				} else if(player == blue) {
					if(player.isSneaking() && !stunR && !stunLockB) {
						sendPacket(player, ChatColor.GOLD + "가드 성공!");
						sendPacket(red, ChatColor.DARK_RED + "상대의 가드!");
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1.0f, 1.0f);
						
						new BukkitRunnable() {
							int time = 0;

							@Override
							public void run() {
								time++;
								
								if(time < 20) {
									stunR = true;
								}
								
								if(time == 20) {
									stunR = false;
								}
								
								if(time < 60) {
									stunLockB = true;
								}
								
								if(time >= 60) {
									stunR = false;
									stunLockB = false;
									
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
	public void mouseEvent(PlayerInteractEvent event) {
		try {
			EquipmentSlot e = event.getHand();
			if (e.equals(EquipmentSlot.HAND)) {
				Player player = event.getPlayer();
				
				try {
					if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						if(event.getClickedBlock().getType() == Material.STONE_BUTTON || event.getClickedBlock().getType() == Material.WOOD_BUTTON) {
							
							Block b = event.getClickedBlock();
							Location loc = b.getLocation();
							startLoc = loc;
							if (loc.getX() <= 1000 && loc.getY() <= 255 && loc.getZ() <= 1000 
									&& loc.getX() >= -1000 && loc.getY() >= 0 && loc.getZ() >= -1000) {
								
								if(red != null && blue != null) {
									player.sendMessage(ChatColor.RED + "아직 경기가 진행 중입니다.");
									return;
								}
								
								World world = loc.getWorld();
								
								for(int i = -300 ; i <= 300 ; i++) {
									for(int j = 10 ; j <= 150 ; j++) {
										for(int k = -300 ; k <= 300 ; k++) {
											loc = new Location(world,i,j,k);
											if(loc.getBlock().getType() == Material.WOOL) {
												if(loc.getBlock().getData() == 14) {
													aryR.add(loc);
												}
											}
										}
									}
								}
								
								for(int i = -300 ; i <= 300 ; i++) {
									for(int j = 10 ; j <= 150 ; j++) {
										for(int k = -300 ; k <= 300 ; k++) {
											loc = new Location(world,i,j,k);
											if(loc.getBlock().getType() == Material.WOOL) {
												if(loc.getBlock().getData() == 11) {
													aryB.add(loc);
												}
											}
										}
									}
								}
								
								if(aryR.size() == 0 || aryB.size() == 0) {
									player.sendMessage(ChatColor.RED + "출발 포인트가 없습니다. 출발 포인트는 빨간 양털/파란 양털로 만들어주세요.");
									event.setCancelled(true);
									return;
								}
								
								for(Player all : Bukkit.getOnlinePlayers()) {
									all.sendMessage(ChatColor.WHITE + "5초 후 게임이 시작됩니다.");
								}
								
								new BukkitRunnable() {
									int time = 0;
									
									@Override
									public void run() {
										time++;
										
										if(time == 41) {
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
										
										if(time == 61) {
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
										
										if(time == 81) {
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
										
										if(time >= 101) {
											
											int cnt1 = 0;
											int cnt2 = 0;
											
											try {
												for(Player all : Bukkit.getOnlinePlayers()) {
													if(all.getLocation().add(0,-1,0).getBlock().getType() == Material.CONCRETE) {
														if(all.getLocation().add(0,-1,0).getBlock().getData() == 14) {
															red = all;
															cnt1++;
														}
													}
												}
											} catch(Exception e1) {
												
											}
											
											try {
												for(Player all : Bukkit.getOnlinePlayers()) {
													if(all.getLocation().add(0,-1,0).getBlock().getType() == Material.CONCRETE) {
														if(all.getLocation().add(0,-1,0).getBlock().getData() == 11) {
															blue = all;
															cnt2++;
														}
													}
												}
											} catch(Exception e1) {
												
											}
											
											if(cnt1 != 1 || cnt2 != 1) {
												for(Player all : Bukkit.getOnlinePlayers()) {
													all.sendMessage(ChatColor.RED + "참가자는 각 팀당 1명씩입니다.");
													all.sendMessage(ChatColor.RED + "현재, 참가자 수에 문제가 있습니다.");
												}
												red = null;
												blue = null;
											} else {
												red.teleport(aryR.get(0));
												blue.teleport(aryB.get(0));
												
												red.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 100, true));
												blue.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 100, true));
												red.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 100, true));
												blue.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 100, true));
												
												new BukkitRunnable() {
													int time = 0;
													
													@Override
													public void run() {
														
														if (time == 0) {
															for(Player all : Bukkit.getOnlinePlayers()) {
																all.sendMessage(ChatColor.GREEN + "잠시 후 대결이 시작됩니다.");
															}
														}
														
														if (time == 100) {
															for(Player all : Bukkit.getOnlinePlayers()) {
																all.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "5..");
															}
														}
														
														if (time == 120) {
															for(Player all : Bukkit.getOnlinePlayers()) {
																all.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "4..");
															}
														}
														
														if (time == 140) {
															for(Player all : Bukkit.getOnlinePlayers()) {
																all.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "3..");
															}
														}
														
														if (time == 160) {
															for(Player all : Bukkit.getOnlinePlayers()) {
																all.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "2..");
															}
														}
														
														if (time == 180) {
															for(Player all : Bukkit.getOnlinePlayers()) {
																all.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "1..");
															}
														}
														
														if (time == 200) {
															for(Player all : Bukkit.getOnlinePlayers()) {
																all.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "경기 시작합니다!");
															}
															this.cancel();
														}
														
														time++;
														
													}
												}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
											}

											this.cancel();
										}
										
									}
								}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
								
							}
						}
					}
				} catch(Exception e1) {
					
				}
				
				if(player == red) {
					event.setCancelled(true);
					if(atkLockR) {
						return;
					}
				} else if(player == blue) {
					event.setCancelled(true);
					if(atkLockB) {
						return;
					}
				}
				
				try {
					if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						if(isFighting(player)) {
							if(!isStun(player)) {
								
								int cool = 2;
								if (new CoolTime().coolCheck(player, cool, "R")) {
									for(Player all : Bukkit.getOnlinePlayers()) {
										Object handle = all.getClass().getMethod("getHandle").invoke(all);
								        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
								        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, new PacketPlayOutAnimation(((CraftPlayer) player).getHandle(), 3));
									}
									
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 10.0f, 2.0f);
									
									List<Entity> entitylist = player.getNearbyEntities(2, 2, 2);				
									for (Entity nearEntity : entitylist) {
										if (nearEntity instanceof Player) {
											Player ent = (Player) nearEntity;
											ent.damage(3);
										}
									}
									
									sendPacket(player, ChatColor.RED + "스트레이트!");
								} else {
									player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, "R"));
									player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
								}
								
								event.setCancelled(true);
							}
							event.setCancelled(true);
						}
					} else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
						if(isFighting(player)) {
							if(!isStun(player)) {
								for(Player all : Bukkit.getOnlinePlayers()) {
									Object handle = all.getClass().getMethod("getHandle").invoke(all);
							        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
							        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, new PacketPlayOutAnimation(((CraftPlayer) player).getHandle(), 0));
								}
								
								player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 10.0f, 1.5f);
								
								List<Entity> entitylist = player.getNearbyEntities(2, 2, 2);				
								for (Entity nearEntity : entitylist) {
									if (nearEntity instanceof Player) {
										Player ent = (Player) nearEntity;
										ent.damage(1);
									}
								}
								
								sendPacket(player, ChatColor.YELLOW + "잽!");
								
								event.setCancelled(true);
							}
							event.setCancelled(true);
						}
					}
					
					if(player == red) {
						if(!atkLockR) {
							
							atkLockR = true;
							new BukkitRunnable() {
								int time = 0;

								@Override
								public void run() {
									time++;

									if (time == 20) {
										atkLockR = false;
										this.cancel();
									}
									
								}
							}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
							
						}
					} else if(player == blue) {
						if(!atkLockB) {
							
							atkLockB = true;
							new BukkitRunnable() {
								int time = 0;

								@Override
								public void run() {
									time++;

									if (time >= 5) {
										atkLockB = false;
										this.cancel();
									}
									
								}
							}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
							
						}
					}
					
				} catch(Exception e1) {
					
				}
				
			}
		} catch(Exception e) {
			
		}
	}
	
	@EventHandler
	public void animationEvent(PlayerAnimationEvent event) {
//		Player player = event.getPlayer();
//		try {
//			if (event.getAnimationType() == PlayerAnimationType.ARM_SWING) {
//				if(isFighting(player)) {
//					if(!isStun(player)) {
//						for(Player all : Bukkit.getOnlinePlayers()) {
//							Object handle = all.getClass().getMethod("getHandle").invoke(all);
//					        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
//					        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, new PacketPlayOutAnimation(((CraftPlayer) player).getHandle(), 3));
//						}
//						
//						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 10.0f, 1.5f);
//						
//						List<Entity> entitylist = player.getNearbyEntities(2, 2, 2);				
//						for (Entity nearEntity : entitylist) {
//							if (nearEntity instanceof Player) {
//								Player ent = (Player) nearEntity;
//								ent.damage(1);
//							}
//						}
//						
//						sendPacket(player, ChatColor.YELLOW + "잽!");
//						
//						event.setCancelled(true);
//					}
//					event.setCancelled(true);
//				}
//			}
//		} catch(Exception e1) {
//			
//		}
	}
	
	@EventHandler
	public void swapEvent(PlayerSwapHandItemsEvent event) {
		event.setCancelled(true);
		Player player = event.getPlayer();
		
		if(isFighting(player) && !isStun(player)) {
			int cool = 1;
			if (new CoolTime().coolCheck(player, cool, "F")) {
				new Weave().effect(player);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 10.0f, 0.4f);
			} else {
//				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, "F"));
//				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
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
	
	public boolean isFighting(Player player) {
		if(player.getLocation().getBlock().getType() == Material.CONCRETE) {
			if(player.getLocation().getBlock().getData() == 0) {
				return true;
			}
		}
		if(player.getLocation().add(0,-1,0).getBlock().getType() == Material.CONCRETE) {
			if(player.getLocation().add(0,-1,0).getBlock().getData() == 0) {
				return true;
			}
		}
		if(player.getLocation().add(0,-2,0).getBlock().getType() == Material.CONCRETE) {
			if(player.getLocation().add(0,-2,0).getBlock().getData() == 0) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isStun(Player player) {
		if(player == red) {
			return stunR;
		}
		if(player == blue) {
			return stunB;
		}
		return false;
	}
	
	public void sendPacket(Player player, String msg) {
		try {
			String message = msg;
			IChatBaseComponent chatComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}");
	        PacketPlayOutChat packet = new PacketPlayOutChat(chatComponent, ChatMessageType.GAME_INFO);
	        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
			
		} catch (Exception e) {

		}
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