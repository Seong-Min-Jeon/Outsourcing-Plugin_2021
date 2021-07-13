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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
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
	
	boolean finish = false;
	boolean start = false;
	boolean halfR = false;
	boolean halfB = false;
	ArrayList<Player> redT = new ArrayList<>();
	ArrayList<Player> blueT = new ArrayList<>();
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		
		new BukkitRunnable() {
			
			int time = 0;
			
			@Override
			public void run() {
				
				if(time % 10 == 0) {
					try {
						World world = Bukkit.getWorld("world");
						Location loc = null;
						int cnt = 0;
						for(int i = -80 ; i <= -51 ; i++) {
							for(int j = 95 ; j <= 120 ; j++) {
								for(int k = -48 ; k <= -30 ; k++) {
									loc = new Location(world,i,j,k);
									if(loc.getBlock().getType() == Material.WOOL) {
										if(loc.getBlock().getData() == 15) {
											cnt++;
										}
									}
								}
							}
						}
						new Bar().bar2.setProgress(cnt/1116.0);
//						new Bar().bar2.setTitle(ChatColor.RED + "");
					} catch(Exception e) {
						
					}
					
					try {
						World world = Bukkit.getWorld("world");
						Location loc = null;
						int cnt = 0;
						for(int i = 60 ; i <= 89 ; i++) {
							for(int j = 95 ; j <= 120 ; j++) {
								for(int k = -48 ; k <= -30 ; k++) {
									loc = new Location(world,i,j,k);
									if(loc.getBlock().getType() == Material.WOOL) {
										if(loc.getBlock().getData() == 15) {
											cnt++;
										}
									}
								}
							}
						}
						new Bar().bar1.setProgress(cnt/1116.0);
//						new Bar().bar1.setTitle(ChatColor.RED + "");
					} catch(Exception e) {
						
					}
				}
				
				if(new Bar().bar1.getProgress() == 558/1116.0 && halfR == false) {
					//레드 절반
					halfR = true;
					for(Player all : Bukkit.getOnlinePlayers()) {
						all.sendMessage("============================");
						all.sendMessage(ChatColor.RED + "레드팀 머리카락 절반 남았습니다!");
						all.sendMessage("============================");
					}
				}
				
				if(new Bar().bar2.getProgress() == 558/1116.0 && halfB == false) {
					//블루 절반
					halfB = true;
					for(Player all : Bukkit.getOnlinePlayers()) {
						all.sendMessage("============================");
						all.sendMessage(ChatColor.AQUA + "블루팀 머리카락 절반 남았습니다!");
						all.sendMessage("============================");
					}
				}
				
				if(new Bar().bar1.getProgress() == 0 && finish == false) {
					// 블루승리
					new BukkitRunnable() {
						
						int time = 0;
						
						@Override
						public void run() {
							
							if(time == 0) {
								finish = true;
								for(Player all : Bukkit.getOnlinePlayers()) {
									if(blueT.contains(all)) {
										try {
											PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
													ChatSerializer.a("{\"text\":\"§C승리하였습니다!\"}"));
											Object handle = all.getClass().getMethod("getHandle").invoke(all);
									        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
									        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
										} catch(Exception e) {
											
										}
										all.getWorld().playSound(all.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 2.0f);
										blueFirework(all.getLocation());
									} else if(redT.contains(all)) {
										try {
											PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
													ChatSerializer.a("{\"text\":\"§9패배하였습니다.\"}"));
											Object handle = all.getClass().getMethod("getHandle").invoke(all);
									        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
									        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
										} catch(Exception e) {
											
										}
										all.getWorld().playSound(all.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1.0f);
									}
								}
							}
							
							if(time == 60) {
								for(Player all : Bukkit.getOnlinePlayers()) {
									all.teleport(new Location(all.getWorld(),4,65,-104));
								}
								this.cancel();
							}
							
							time++;
						}
					}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
				}
				
				if(new Bar().bar2.getProgress() == 0 && finish == false) {
					// 레드승리
					new BukkitRunnable() {
						
						int time = 0;
						
						@Override
						public void run() {
							
							if(time == 0) {
								finish = true;
								for(Player all : Bukkit.getOnlinePlayers()) {
									if(redT.contains(all)) {
										try {
											PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
													ChatSerializer.a("{\"text\":\"§C승리하였습니다!\"}"));
											Object handle = all.getClass().getMethod("getHandle").invoke(all);
									        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
									        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
										} catch(Exception e) {
											
										}
										all.getWorld().playSound(all.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 2.0f);
										redFirework(all.getLocation());
									} else if(blueT.contains(all)) {
										try {
											PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
													ChatSerializer.a("{\"text\":\"§9패배하였습니다.\"}"));
											Object handle = all.getClass().getMethod("getHandle").invoke(all);
									        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
									        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
										} catch(Exception e) {
											
										}
										all.getWorld().playSound(all.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1.0f);
									}
								}
							}
							
							if(time == 60) {
								for(Player all : Bukkit.getOnlinePlayers()) {
									all.teleport(new Location(all.getWorld(),4,65,-104));
								}
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
		new Bar().bar1.addPlayer(player);
		new Bar().bar2.addPlayer(player);
		new Bar().bar1.setVisible(true);
		new Bar().bar2.setVisible(true);

		player.teleport(new Location(player.getWorld(),4,65,-104));
		player.getInventory().clear();
	}
	
	@EventHandler 
	public void respawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		
		if(redT.contains(player)) {
			event.setRespawnLocation(new Location(player.getWorld(),40.5,100,-39.5,90,0));
			EntityEquipment helmet = player.getEquipment();
			ItemStack helmetItem = new ItemStack(Material.LEATHER_HELMET);
			LeatherArmorMeta helmetmeta = (LeatherArmorMeta) helmetItem.getItemMeta();
			helmetmeta.setColor(Color.fromRGB(255, 0, 0));
			helmetItem.setItemMeta(helmetmeta);
			helmet.setHelmet(helmetItem);
			EntityEquipment chestplate = player.getEquipment();
			ItemStack chestplateItem = new ItemStack(Material.LEATHER_CHESTPLATE);
			LeatherArmorMeta chestmeta = (LeatherArmorMeta) chestplateItem.getItemMeta();
			chestmeta.setColor(Color.fromRGB(255, 0, 0));
			chestplateItem.setItemMeta(chestmeta);
			chestplate.setChestplate(chestplateItem);
			EntityEquipment leggings = player.getEquipment();
			ItemStack leggingsItem = new ItemStack(Material.LEATHER_LEGGINGS);
			LeatherArmorMeta leggingsmeta = (LeatherArmorMeta) leggingsItem.getItemMeta();
			leggingsmeta.setColor(Color.fromRGB(255, 0, 0));
			leggingsItem.setItemMeta(leggingsmeta);
			leggings.setLeggings(leggingsItem);
			EntityEquipment boots = player.getEquipment();
			ItemStack bootsItem = new ItemStack(Material.LEATHER_BOOTS);
			LeatherArmorMeta bootsmeta = (LeatherArmorMeta) bootsItem.getItemMeta();
			bootsmeta.setColor(Color.fromRGB(255, 0, 0));
			bootsItem.setItemMeta(bootsmeta);
			boots.setBoots(bootsItem);
		} else if(blueT.contains(player)) {
			event.setRespawnLocation(new Location(player.getWorld(),-31.5,100,-39.5, 270, 0));
			EntityEquipment helmet = player.getEquipment();
			ItemStack helmetItem = new ItemStack(Material.LEATHER_HELMET);
			LeatherArmorMeta helmetmeta = (LeatherArmorMeta) helmetItem.getItemMeta();
			helmetmeta.setColor(Color.fromRGB(0, 0, 255));
			helmetItem.setItemMeta(helmetmeta);
			helmet.setHelmet(helmetItem);
			EntityEquipment chestplate = player.getEquipment();
			ItemStack chestplateItem = new ItemStack(Material.LEATHER_CHESTPLATE);
			LeatherArmorMeta chestmeta = (LeatherArmorMeta) chestplateItem.getItemMeta();
			chestmeta.setColor(Color.fromRGB(0, 0, 255));
			chestplateItem.setItemMeta(chestmeta);
			chestplate.setChestplate(chestplateItem);
			EntityEquipment leggings = player.getEquipment();
			ItemStack leggingsItem = new ItemStack(Material.LEATHER_LEGGINGS);
			LeatherArmorMeta leggingsmeta = (LeatherArmorMeta) leggingsItem.getItemMeta();
			leggingsmeta.setColor(Color.fromRGB(0, 0, 255));
			leggingsItem.setItemMeta(leggingsmeta);
			leggings.setLeggings(leggingsItem);
			EntityEquipment boots = player.getEquipment();
			ItemStack bootsItem = new ItemStack(Material.LEATHER_BOOTS);
			LeatherArmorMeta bootsmeta = (LeatherArmorMeta) bootsItem.getItemMeta();
			bootsmeta.setColor(Color.fromRGB(0, 0, 255));
			bootsItem.setItemMeta(bootsmeta);
			boots.setBoots(bootsItem);
		} else {
			event.setRespawnLocation(new Location(player.getWorld(),4,65,-104));
		}
	}
	
	@EventHandler
	public void mouseEvent(PlayerInteractEvent event) {
		try {
			EquipmentSlot e = event.getHand();
			if (e.equals(EquipmentSlot.HAND)) {
				Player player = event.getPlayer();
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if(event.getClickedBlock().getType() == Material.STONE_BUTTON || event.getClickedBlock().getType() == Material.WOOD_BUTTON) {
						
						Block b = event.getClickedBlock();
						Location loc = b.getLocation();
						if(start) {
							player.sendMessage(ChatColor.RED + "아직 경기가 진행 중입니다.");
							return;
						}
						
						start = true;
						
						new Bar().bar1.setProgress(1);
						new Bar().bar2.setProgress(1);
						
						World world = loc.getWorld();
						
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendMessage(ChatColor.WHITE + "10초 후 게임이 시작됩니다.");
						}
						
						new BukkitRunnable() {
							int time = 0;
							
							@Override
							public void run() {
								time++;
								
								if(time == 141) {
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
								
								if(time == 161) {
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
								
								if(time == 181) {
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
								
								if(time >= 201) {
									
									try {
										for(Player all : Bukkit.getOnlinePlayers()) {
											Location loc = all.getLocation();
											if(loc.getX() <= 24 && loc.getY() <= 70 && loc.getZ() <= -99 
													&& loc.getX() >= 15 && loc.getY() >= 50 && loc.getZ() >= -108) {
												redT.add(all);
												EntityEquipment helmet = all.getEquipment();
												ItemStack helmetItem = new ItemStack(Material.LEATHER_HELMET);
												LeatherArmorMeta helmetmeta = (LeatherArmorMeta) helmetItem.getItemMeta();
												helmetmeta.setColor(Color.fromRGB(255, 0, 0));
												helmetItem.setItemMeta(helmetmeta);
												helmet.setHelmet(helmetItem);
												EntityEquipment chestplate = all.getEquipment();
												ItemStack chestplateItem = new ItemStack(Material.LEATHER_CHESTPLATE);
												LeatherArmorMeta chestmeta = (LeatherArmorMeta) chestplateItem.getItemMeta();
												chestmeta.setColor(Color.fromRGB(255, 0, 0));
												chestplateItem.setItemMeta(chestmeta);
												chestplate.setChestplate(chestplateItem);
												EntityEquipment leggings = all.getEquipment();
												ItemStack leggingsItem = new ItemStack(Material.LEATHER_LEGGINGS);
												LeatherArmorMeta leggingsmeta = (LeatherArmorMeta) leggingsItem.getItemMeta();
												leggingsmeta.setColor(Color.fromRGB(255, 0, 0));
												leggingsItem.setItemMeta(leggingsmeta);
												leggings.setLeggings(leggingsItem);
												EntityEquipment boots = all.getEquipment();
												ItemStack bootsItem = new ItemStack(Material.LEATHER_BOOTS);
												LeatherArmorMeta bootsmeta = (LeatherArmorMeta) bootsItem.getItemMeta();
												bootsmeta.setColor(Color.fromRGB(255, 0, 0));
												bootsItem.setItemMeta(bootsmeta);
												boots.setBoots(bootsItem);
												all.teleport(new Location(player.getWorld(),40.5,100,-39.5,90,0));
												all.setHealth(20);
												all.setFoodLevel(20);
												for(PotionEffect effect : all.getActivePotionEffects()){
											        all.removePotionEffect(effect.getType());
											    }
												all.setFireTicks(0);
											} else if(loc.getX() <= -6 && loc.getY() <= 70 && loc.getZ() <= -99 
													&& loc.getX() >= -15 && loc.getY() >= 50 && loc.getZ() >= -108) {
												blueT.add(all);
												EntityEquipment helmet = all.getEquipment();
												ItemStack helmetItem = new ItemStack(Material.LEATHER_HELMET);
												LeatherArmorMeta helmetmeta = (LeatherArmorMeta) helmetItem.getItemMeta();
												helmetmeta.setColor(Color.fromRGB(0, 0, 255));
												helmetItem.setItemMeta(helmetmeta);
												helmet.setHelmet(helmetItem);
												EntityEquipment chestplate = all.getEquipment();
												ItemStack chestplateItem = new ItemStack(Material.LEATHER_CHESTPLATE);
												LeatherArmorMeta chestmeta = (LeatherArmorMeta) chestplateItem.getItemMeta();
												chestmeta.setColor(Color.fromRGB(0, 0, 255));
												chestplateItem.setItemMeta(chestmeta);
												chestplate.setChestplate(chestplateItem);
												EntityEquipment leggings = all.getEquipment();
												ItemStack leggingsItem = new ItemStack(Material.LEATHER_LEGGINGS);
												LeatherArmorMeta leggingsmeta = (LeatherArmorMeta) leggingsItem.getItemMeta();
												leggingsmeta.setColor(Color.fromRGB(0, 0, 255));
												leggingsItem.setItemMeta(leggingsmeta);
												leggings.setLeggings(leggingsItem);
												EntityEquipment boots = all.getEquipment();
												ItemStack bootsItem = new ItemStack(Material.LEATHER_BOOTS);
												LeatherArmorMeta bootsmeta = (LeatherArmorMeta) bootsItem.getItemMeta();
												bootsmeta.setColor(Color.fromRGB(0, 0, 255));
												bootsItem.setItemMeta(bootsmeta);
												boots.setBoots(bootsItem);
												all.teleport(new Location(player.getWorld(),-31.5,100,-39.5, 270, 0));
												all.setHealth(20);
												all.setFoodLevel(20);
												for(PotionEffect effect : all.getActivePotionEffects()){
											        all.removePotionEffect(effect.getType());
											    }
												all.setFireTicks(0);
											}
										}
									} catch(Exception e1) {
										
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
	public void chatEvent(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if(redT.contains(player)) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.sendMessage(ChatColor.RED + "<" + event.getPlayer().getDisplayName() + "> " + ChatColor.WHITE + event.getMessage());
			}
		} else if(blueT.contains(player)) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.sendMessage(ChatColor.AQUA + "<" + event.getPlayer().getDisplayName() + "> " + ChatColor.WHITE + event.getMessage());
			}
		} else {
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.sendMessage(ChatColor.WHITE + "<" + event.getPlayer().getDisplayName() + "> " + ChatColor.WHITE + event.getMessage());
			}
		}
		event.setCancelled(true);
	}
	
	@EventHandler
	public void breakEvent(BlockBreakEvent event) {
		if(event.getBlock().getType() == Material.WOOL) {
			if(event.getBlock().getData() == 15) {
				event.getBlock().setType(Material.AIR);
				event.setCancelled(true);
			}
		}
	}
	
	public void redFirework(Location loc) {
		Location normal = loc;
		World world = loc.getWorld(); 
		
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

							e1 = loc.clone().add(x, y+10, z);
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
	
	public void blueFirework(Location loc) {
		Location normal = loc;
		World world = loc.getWorld(); 
		
		new BukkitRunnable() {
			int time = 0;
			int size = 0;

			@Override
			public void run() {
				Location e1;
				
				if(time % 2 == 0 && time < 20) {
					e1 = normal.clone().add(0, time/2, 0);
					world.spawnParticle(Particle.REDSTONE, e1, 0,0.01,0,1,1);
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
							world.spawnParticle(Particle.REDSTONE, e1, 0,0.01,0,1,1);
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