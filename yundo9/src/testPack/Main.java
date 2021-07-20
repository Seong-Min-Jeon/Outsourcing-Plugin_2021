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
import org.bukkit.enchantments.Enchantment;
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
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.Button;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

//gamerule doImmediateRespawn true

public class Main extends JavaPlugin implements Listener{
	
	HashMap<Player, Integer> chestCntMap = new HashMap<>();
	ArrayList<Location> ary = new ArrayList<>();
	ArrayList<Player> killerList = new ArrayList<>();
	Player killer = null;
	boolean start = false;
	Location startLoc = null;
	int leave = 100;
	int timer = -200;
 
	Random rnd = new Random();
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		
		getCommand("부활").setExecutor(new Cmd1());
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if(new Bar().bar1.getProgress() != 1 && timer >= 0 && leave <= 2) {
					new Bar().bar1.setProgress(timer/600.0);
					new Bar().bar1.setTitle(ChatColor.RED + "" + timer%1200/20 + "초");
				}
				
				if(start) {
					
					if(new Bar().bar1.getProgress() != 1 && timer >= 0) {
						new Bar().bar1.setProgress(timer/1200.0);
						new Bar().bar1.setTitle(ChatColor.RED + "" + timer%1200/20 + "초");
					}
					
					if(timer == 0) {
						World world = Bukkit.getWorld("world");
						world.setTime(0);
						
						ary.clear();
						
						Location loc = null;
						for(int i = -300 ; i <= 300 ; i++) {
							for(int j = 40 ; j <= 80 ; j++) {
								for(int k = -300 ; k <= 300 ; k++) {
									loc = new Location(world,i,j,k);
									if(loc.getBlock().getType() == Material.CONCRETE) {
										if(loc.getBlock().getData() == 4) {
											ary.add(loc);
										}
									}
								}
							}
						}
						
						for(Player all : new PlayerList().playerList) {
							int num = rnd.nextInt(ary.size());
							Location startLoc = ary.get(num).add(0.5,1,0.5);
							all.teleport(startLoc);
							ary.remove(ary.get(num));
							
							for(PotionEffect effect : all.getActivePotionEffects()){
								all.removePotionEffect(effect.getType());
						    }
						}
						
						//상자 배치
						for(int i = -300 ; i <= 300 ; i++) {
							for(int j = 40 ; j <= 80 ; j++) {
								for(int k = -300 ; k <= 300 ; k++) {
									loc = new Location(world,i,j,k);
									if(loc.getBlock().getType() == Material.CONCRETE) {
										if(loc.getBlock().getData() == 14) {
											Location chestLoc = loc.clone().add(0,1,0);
											chestLoc.getBlock().setType(Material.CHEST);
											Block block = chestLoc.getBlock();
											Chest chest = (Chest) block.getState();
											Inventory inv = chest.getInventory();
											
											int cnt = rnd.nextInt(10);
											
											if(cnt < 3) {
												ItemStack we = new ItemStack(Material.IRON_SWORD);
												ItemMeta im = we.getItemMeta();
												im.setDisplayName(ChatColor.YELLOW + "칼");
												we.setItemMeta(im);
												inv.setItem(0, we);
											} else if(cnt < 4) {
												ItemStack we = new ItemStack(Material.NETHER_STAR);
												ItemMeta im = we.getItemMeta();
												im.setDisplayName(ChatColor.YELLOW + "무적의 증표");
												we.setItemMeta(im);
												inv.setItem(0, we);
											} else if(cnt < 5) {
												ItemStack we = new ItemStack(Material.BONE);
												ItemMeta im = we.getItemMeta();
												im.setDisplayName(ChatColor.YELLOW + "부활의 증표");
												we.setItemMeta(im);
												inv.setItem(0, we);
											} 
											
										}
									}
								}
							}
						}
						
					}
					
					if(timer == 1200) {
						//상자 삭제
						World world = Bukkit.getWorld("world");
						Location loc = null;
						for(int i = -300 ; i <= 300 ; i++) {
							for(int j = 40 ; j <= 80 ; j++) {
								for(int k = -300 ; k <= 300 ; k++) {
									loc = new Location(world,i,j,k);
									if(loc.getBlock().getType() == Material.CHEST) {
										Chest chest = (Chest) loc.getBlock().getState();
										chest.getInventory().clear();
										loc.getBlock().setType(Material.AIR);
									}
								}
							}
						}
						
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendMessage(ChatColor.RED + "밤이 깊어갑니다..");
							new Bar().bar1.setVisible(false);
						}
						
						for(Player all : Bukkit.getOnlinePlayers()) {
							if(all.getInventory().contains(Material.IRON_SWORD)) {
								killerList.add(all);
							}
						}
						
						new BukkitRunnable() {
							
							int time = 0;
							
							@Override
							public void run() {
								
								if(time >= 40) {
									
									Bukkit.getWorld("world").setTime(18000);
									
									if(time % 400 == 40) {
										
										//이동
										ary.clear();
										
										Location loc = null;
										for(int i = -300 ; i <= 300 ; i++) {
											for(int j = 40 ; j <= 80 ; j++) {
												for(int k = -300 ; k <= 300 ; k++) {
													loc = new Location(world,i,j,k);
													if(loc.getBlock().getType() == Material.CONCRETE) {
														if(loc.getBlock().getData() == 15) {
															ary.add(loc);
														}
													}
												}
											}
										}
										
										for(Player all : new PlayerList().playerList) {
											int num = rnd.nextInt(ary.size());
											Location startLoc = ary.get(num).add(0.5,1,0.5);
											all.teleport(startLoc);
											ary.remove(ary.get(num));
										}
										////////////////////
										
										//킬러 목록 제거
										if(killerList.contains(killer)) {
											killerList.remove(killer);
										}
										
										// 아침
										if(killerList.size() == 0) {
											for(Player all : Bukkit.getOnlinePlayers()) {
												all.sendMessage(ChatColor.GREEN + "아침이 밝아옵니다..");
												new Bar().bar1.setProgress(0);
												new Bar().bar1.setVisible(true);
												killer = null;
												chestCntMap.clear();
												timer = -40;
												
												if(new PlayerList().playerList.size() == 2) {
													leave = 2;
												} else if(new PlayerList().playerList.size() == 1) {
													leave = 1;
												}
												
												if(leave <= 2) {
													chestCntMap.clear();
													ary.clear();
													killerList.clear();
													killer = null;
													start = false;
													timer = -200;
												}
											}
											this.cancel();
										}
										
										// 실명
										for(Player all : new PlayerList().playerList) {
											all.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 500, 0, true));
										}
										
										// 새로운 킬러
										try {
											killer = killerList.get(rnd.nextInt(killerList.size()));
											for(PotionEffect effect : killer.getActivePotionEffects()){
												killer.removePotionEffect(effect.getType());
												
												// 사냥 메세지
												for(Player all : Bukkit.getOnlinePlayers()) {
													all.sendMessage(ChatColor.RED + "누군가의 사냥 시간이 시작되었습니다.");
												}
										    }
										} catch(Exception e) {
											
										}
										
									}
									
								}
								
								time++;
							}
						}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
						
					}
					
					timer++;
				}
				
				if(!start && leave == 2) {
					new BukkitRunnable() {
						
						int time = 0;
						
						@Override
						public void run() {
							
							if(time == 0) {
								Bukkit.getWorld("world").setTime(18000);
								//결투
								ary.clear();
								
								Location loc = null;
								for(int i = -300 ; i <= 300 ; i++) {
									for(int j = 40 ; j <= 80 ; j++) {
										for(int k = -300 ; k <= 300 ; k++) {
											loc = new Location(Bukkit.getWorld("world"),i,j,k);
											if(loc.getBlock().getType() == Material.CONCRETE) {
												if(loc.getBlock().getData() == 11) {
													ary.add(loc);
												}
											}
										}
									}
								}
								
								for(Player all : new PlayerList().playerList) {
									int num = rnd.nextInt(ary.size());
									Location startLoc = ary.get(num).add(0.5,1,0.5);
									all.teleport(startLoc);
									ary.remove(ary.get(num));
									
									ItemStack we = new ItemStack(Material.IRON_SWORD);
									ItemMeta im = we.getItemMeta();
									im.setDisplayName(ChatColor.YELLOW + "칼");
									we.setItemMeta(im);
									
									all.getInventory().setItem(0, we);
									all.getInventory().setItem(1, we);
									all.getInventory().setItem(2, we);
									all.getInventory().setItem(3, we);
									all.getInventory().setItem(4, we);
									all.getInventory().setItem(5, we);
									all.getInventory().setItem(6, we);
									all.getInventory().setItem(7, we);
									all.getInventory().setItem(8, we);
								}
							}
							
							if(time == 600) {
								if(new PlayerList().playerList.size() == 2) {
									String name1 = new PlayerList().playerList.get(0).getDisplayName();
									String name2 = new PlayerList().playerList.get(1).getDisplayName();
									firework(new PlayerList().playerList.get(0).getLocation());
									firework(new PlayerList().playerList.get(1).getLocation());
									try {
										for(Player all : Bukkit.getOnlinePlayers()) {
											PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
													ChatSerializer.a("{\"text\":\"§6"+ name1 + ", " + name2 + " 공동 우승입니다!"  +"\"}"));
											Object handle = all.getClass().getMethod("getHandle").invoke(all);
									        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
									        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
										}
									} catch(Exception e) {
										
									}
								} else {
									String name1 = new PlayerList().playerList.get(0).getDisplayName();
									firework(new PlayerList().playerList.get(0).getLocation());
									try {
										for(Player all : Bukkit.getOnlinePlayers()) {
											PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
													ChatSerializer.a("{\"text\":\"§6"+ name1 + " 우승입니다!"  +"\"}"));
											Object handle = all.getClass().getMethod("getHandle").invoke(all);
									        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
									        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
										}
									} catch(Exception e) {
										
									}
								}
							}
							
							if(time == 740) {
								try {
									for(Player all : Bukkit.getOnlinePlayers()) {
										all.teleport(startLoc);
									}
								} catch(Exception e) {
									
								}
							}
							
							if(time >= 750) {
								new Bar().bar1.setProgress(0);
								new Bar().bar1.setVisible(true);
								chestCntMap.clear();
								ary.clear();
								killerList.clear();
								killer = null;
								start = false;
								timer = -200;
								startLoc = null;
								leave = 100;
								new PlayerList().playerList.clear();
								Bukkit.getWorld("world").setTime(0);
								
								this.cancel();
							}
							
							time++;
						}
					}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
					
				} else if(!start && leave == 1) {
					Bukkit.getWorld("world").setTime(18000);
					//우승
					new BukkitRunnable() {
						
						int time = 0;
						
						@Override
						public void run() {
							
							if(time == 0) {
								String name1 = new PlayerList().playerList.get(0).getDisplayName();
								firework(new PlayerList().playerList.get(0).getLocation());
								try {
									for(Player all : Bukkit.getOnlinePlayers()) {
										PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
												ChatSerializer.a("{\"text\":\"§6"+ name1 + " 우승입니다!"  +"\"}"));
										Object handle = all.getClass().getMethod("getHandle").invoke(all);
								        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
								        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
									}
								} catch(Exception e) {
									
								}
							}
							
							if(time == 140) {
								try {
									for(Player all : Bukkit.getOnlinePlayers()) {
										all.teleport(startLoc);
									}
								} catch(Exception e) {
									
								}
							}
							
							if(time >= 150) {
								new Bar().bar1.setProgress(0);
								new Bar().bar1.setVisible(true);
								chestCntMap.clear();
								ary.clear();
								killerList.clear();
								killer = null;
								start = false;
								timer = -200;
								startLoc = null;
								leave = 100;
								new PlayerList().playerList.clear();
								Bukkit.getWorld("world").setTime(0);
								
								this.cancel();
							}
							
							time++;
							
						}
					}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
					
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
		player.teleport(player.getWorld().getSpawnLocation());
		
		new Bar().bar1.addPlayer(player);
		
		player.getInventory().clear();
		
		for(PotionEffect effect : player.getActivePotionEffects()){
			player.removePotionEffect(effect.getType());
	    }
		
	}
	
	@EventHandler
	public void dropEvent(PlayerDropItemEvent event) {
		try {
			if(event.getItemDrop().getItemStack().getType() == Material.IRON_SWORD) {
				event.setCancelled(true);
			}
			if(event.getItemDrop().getItemStack().getType() == Material.NETHER_STAR) {
				event.setCancelled(true);
			}
			if(event.getItemDrop().getItemStack().getType() == Material.BONE) {
				event.setCancelled(true);
			}
		} catch(Exception e) {
			
		}
	}
	
	@EventHandler 
	public void respawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		
		if(startLoc == null) {
			event.setRespawnLocation(player.getWorld().getSpawnLocation());
		} else {
			event.setRespawnLocation(startLoc);
		}
		
		for(PotionEffect effect : player.getActivePotionEffects ()){
			player.removePotionEffect(effect.getType());
	    }
	}
	
	@EventHandler
	public void die(PlayerDeathEvent event) {
		event.setDeathMessage(ChatColor.RED + "" + event.getEntity().getDisplayName() + "님이 살해당했습니다!");
		
		ItemStack item = null;
		if(event.getEntity().getInventory().contains(Material.BONE)) {
			item = event.getEntity().getInventory().getItem(10);
		}
		
		event.getEntity().getInventory().clear();
		
		if(killerList.contains(event.getEntity())) {
			killerList.remove(event.getEntity());
		}
		
		if(item != null) {
			event.getEntity().getInventory().setItem(10, item);
		}
		
		if(new PlayerList().playerList.contains(event.getEntity())) {
			new PlayerList().playerList.remove(event.getEntity());
		}
		
	}
	
	@EventHandler
	public void damageEvent(EntityDamageByEntityEvent event) {
		try {
			if(event.getDamager() instanceof Player) {
				Player damager = (Player) event.getDamager();
				if(damager.getWorld().getTime() == 0) {
					event.setCancelled(true);
					return;
				}
				
				if(damager.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "칼")) {
					if(rnd.nextInt(3) == 0) {
						if(event.getEntity() instanceof Player) {
							Player entity = (Player) event.getEntity();
							if(entity.getInventory().contains(Material.NETHER_STAR)) {
								entity.getInventory().getItem(9).setAmount(entity.getInventory().getItem(9).getAmount() - 1);
								damager.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
								damager.sendMessage("응~ 나 무적이야~수공~");
								entity.sendMessage("무적의 증표 덕분에 살아남았다..");
								event.setCancelled(true);
								return;
							} 
						}
						event.setDamage(2000);
						damager.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
					} else {
						event.setDamage(0);
						damager.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
						damager.sendMessage(ChatColor.GRAY + "칼이 무뎌졌군..");
						event.setCancelled(true);
					}
				} else {
					event.setCancelled(true);
				}
			}
		} catch(Exception e) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void openInv(InventoryOpenEvent event) {
		Player player = (Player) event.getPlayer();
		if(event.getInventory().getType() == InventoryType.CHEST) {
			if(chestCntMap.containsKey(player)) {
				if(chestCntMap.get(player) >= 2) {
					event.setCancelled(true);
					return;
				}
				chestCntMap.put(player, chestCntMap.get(player) + 1);
			} else {
				chestCntMap.put(player, 1);
			}
		}
	}
	
	@EventHandler
	public void closeInv(InventoryCloseEvent event) {
		if(event.getInventory().getType().equals(InventoryType.CHEST)) {
            Block chest = event.getInventory().getLocation().getBlock();
			event.getInventory().clear();
            chest.setType(Material.AIR);
        }
	}
	
	@EventHandler
	public void clickInv(InventoryClickEvent event) {
		try {
			if(event.getCurrentItem().getType() == Material.NETHER_STAR) {
				if(event.getSlot() != 9) {
					if(event.getWhoClicked().getInventory().contains(Material.NETHER_STAR)) {
						event.getWhoClicked().getInventory().addItem(event.getCurrentItem());
					} else {
						event.getWhoClicked().getInventory().setItem(9, event.getCurrentItem());
					}
					event.getCurrentItem().setAmount(0);
				}
				event.setCancelled(true);
			}
			if(event.getCursor().getType() == Material.NETHER_STAR) {
				event.setCancelled(true);
			}
			if(event.getCurrentItem().getType() == Material.BONE) {
				if(event.getSlot() != 10) {
					if(event.getWhoClicked().getInventory().contains(Material.BONE)) {
						event.getWhoClicked().getInventory().addItem(event.getCurrentItem());
					} else {
						event.getWhoClicked().getInventory().setItem(10, event.getCurrentItem());
					}
					event.getCurrentItem().setAmount(0);
				}
				event.setCancelled(true);
			}
			if(event.getCursor().getType() == Material.BONE) {
				event.setCancelled(true);
			}
		} catch (Exception e7) {

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
						startLoc = loc;
						if (loc.getX() <= 1000 && loc.getY() <= 255 && loc.getZ() <= 1000 
								&& loc.getX() >= -1000 && loc.getY() >= 0 && loc.getZ() >= -1000) {
							
							if(start) {
								player.sendMessage(ChatColor.RED + "아직 경기가 진행 중입니다.");
								return;
							}
							
							start = true;
							
							new Bar().bar1.setVisible(true);
							new Bar().bar1.setProgress(0);
							
							World world = loc.getWorld();
							
							ary.clear();
							
							for(int i = -300 ; i <= 300 ; i++) {
								for(int j = 40 ; j <= 80 ; j++) {
									for(int k = -300 ; k <= 300 ; k++) {
										loc = new Location(world,i,j,k);
										if(loc.getBlock().getType() == Material.CONCRETE) {
											if(loc.getBlock().getData() == 4) {
												ary.add(loc);
											}
										}
									}
								}
							}
							
							if(ary.size() == 0) {
								player.sendMessage(ChatColor.RED + "출발 포인트가 없습니다. 출발 포인트는 노란 콘크리트로 만들어주세요.");
								start = false;
								return;
							}
							
							for(Player all : Bukkit.getOnlinePlayers()) {
								all.sendMessage(ChatColor.WHITE + "10초 후 게임이 시작됩니다.");
							}
							
							new BukkitRunnable() {
								int time = 0;
								
								@Override
								public void run() {
									time++;
									
									if(time == 181) {
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
									
									if(time == 201) {
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
									
									if(time == 221) {
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
									
									if(time == 190) {
										
										int cnt = 0;
										
										try {
											for(Player all : Bukkit.getOnlinePlayers()) {
												if(all.getLocation().add(0,-1,0).getBlock().getType() == Material.CONCRETE) {
													if(all.getLocation().add(0,-1,0).getBlock().getData() == 13) {
//														int num = rnd.nextInt(ary.size());
//														Location startLoc = ary.get(num).add(0.5,1,0.5);
//														all.teleport(startLoc);
//														ary.remove(ary.get(num));
														
														new PlayerList().playerList.add(all);
														cnt++;
													}
												}
											}
										} catch(Exception e1) {
											
										}
										
										if(cnt == 0) {
											for(Player all : Bukkit.getOnlinePlayers()) {
												all.sendMessage(ChatColor.RED + "참가자가 없어 게임이 초기화됩니다.");
											}
											start = false;
										} else {
											timer = -200;
										}

									}
									
									if(time == 250) {
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
	public void chatEvent(AsyncPlayerChatEvent event) {
		if(start) {
			if(!new PlayerList().playerList.contains(event.getPlayer())) {
				if(!event.getPlayer().isOp()) {
					event.setCancelled(true);
				}
			}
		} 
	}
	
	@EventHandler
	public void moveEvent(PlayerMoveEvent event) {
		if(leave > 2) {
			if(Bukkit.getWorld("world").getTime() == 18000 && start) {
	            if(event.getPlayer() != killer) {
	            	event.setCancelled(true);
	            }
	        }
		}
	}
	
	@EventHandler
	public void offEvent(PlayerQuitEvent event) {
		try {
			killerList.remove(event.getPlayer());
		} catch(Exception e) {
			
		}
		
		try {
			new PlayerList().playerList.remove(event.getPlayer());
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