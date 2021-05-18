package testPack;

import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

import java.util.ArrayList;
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
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
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
	ArrayList<Player> banList = new ArrayList<>();
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
					
					if(timer % 12000 == 0) {
						//폭격
					}
					
					if(timer == 9600) {
						//보급
					}
					
					if(timer == 24000) {
						// 종료버튼
						World world = Bukkit.getWorld("world");
						Location loc = new Location(world, 75, 85, 67);
						loc.getBlock().setType(Material.STONE_BUTTON);
						
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
		
		if(banList.contains(player)) {
			event.setJoinMessage(null);
			player.kickPlayer(ChatColor.RED + "이번 게임에는 다시 참여할 수 없습니다.");
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
		
		for(Player all : Bukkit.getOnlinePlayers()) {
			all.sendMessage(ChatColor.DARK_RED + "" + event.getEntity().getDisplayName() + ChatColor.WHITE + "님이 사망하였습니다.");
		}
		
		Location loc = event.getEntity().getLocation();
		if (!(loc.getX() <= 103 && loc.getY() <= 13 && loc.getZ() <= -46 
				&& loc.getX() >= 80 && loc.getY() >= 0 && loc.getZ() >= -64)) {
			try {
				Player player = (Player) event.getEntity();
				player.kickPlayer(ChatColor.RED + "You Died");
				
				banList.add(player);
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
							
							new BukkitRunnable() {
								int time = 0;
								
								@Override
								public void run() {
									time++;
									
									if(time == 0) {
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
									
									if(time == 20) {
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
									
									if(time == 40) {
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
									
									if(time >= 60) {
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
											for(int j = 64 ; j <= 80 ; j++) {
												for(int k = -128 ; k <= 128 ; k++) {
													loc = new Location(world,i,j,k);
													if(loc.getBlock().getType() == Material.GRASS) {
														ary.add(loc);
													}
												}
											}
										}
										
										for(Player all : Bukkit.getOnlinePlayers()) {
											if(all.getLocation().add(0,-1,0).getBlock().getType() == Material.CONCRETE) {
												if(all.getLocation().add(0,-1,0).getBlock().getData() == 13) {
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

							new BukkitRunnable() {
								int time = 0;
								
								@Override
								public void run() {
									time++;
									
									if(time == 0) {
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
									
									if(time >= 20) {
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
									
									if(time >= 80) {
										for(Player all : Bukkit.getOnlinePlayers()) {
											all.teleport(new Location(player.getWorld(), 83.5,6,-55.5,270,0));
										}
										ary.clear();
										banList.clear();
										start = false;
										timer = 0;
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
	
	public void firework(Location loc) {
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