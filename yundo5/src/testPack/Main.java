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
	
	ArrayList<Location> ary = new ArrayList<>();
	ArrayList<String> banList = new ArrayList<>();
	ArrayList<Player> playerList = new ArrayList<>();
	boolean start = false;
	Location startLoc = null;
	int timer = 0;
	double limit = 12000.0;
 
	Random rnd = new Random();
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		
		getCommand("joker").setExecutor(new Cmd1());
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(start) {
					
					if(new Bar().bar1.getProgress() != 1) {
						new Bar().bar1.setProgress(timer/limit);
						new Bar().bar1.setTitle(ChatColor.RED + "" + timer/1200 + "분 " + timer%1200/20 + "초");
					}
					
					if(timer % 20 == 0) {
						int cnt = 0;
						for(Player all2 : playerList) {
							for(Player all : Bukkit.getOnlinePlayers()) {
								if(all2 == all) {
									cnt++;
								}
							}
						}
						if(cnt == 1) {
							// 조커 승리
							for(Player all : Bukkit.getOnlinePlayers()) {
								all.sendMessage("조커의 승리로 게임이 끝났습니다.");
							}
							ary.clear();
							banList.clear();
							playerList.clear();
							start = false;
							timer = 0;
							new Joker().setJoker(null);
							new Bar().bar1.setVisible(false);
							
							for(Player all : Bukkit.getOnlinePlayers()) {
								all.teleport(startLoc);
								all.getInventory().clear();
								for(PotionEffect effect : all.getActivePotionEffects ()){
							        all.removePotionEffect(effect.getType());
							    }
							}
							
							firework(startLoc);
						}
					}
					
					if(timer % 60 == 0) {
						for(Player all : Bukkit.getOnlinePlayers()) {
							if(all == new Joker().getJoker()) {
								all.getWorld().playSound(all.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 2.0f, 1.0f);
								all.setWalkSpeed(0.4f);
							} else {
								all.setWalkSpeed(0.3f);
							}
						}
					}
					
					if(timer == limit) {
						// 생존자 승리
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendMessage(ChatColor.BOLD + "생존자 목록");
							for(Player all2 : playerList) {
								if(all2 != new Joker().getJoker()) {
									all.sendMessage(all2.getDisplayName());
								}
							}
						}
						
						ary.clear();
						banList.clear();
						playerList.clear();
						start = false;
						timer = 0;
						new Joker().setJoker(null);
						new Bar().bar1.setVisible(false);
						
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.teleport(startLoc);
							all.getInventory().clear();
							for(PotionEffect effect : all.getActivePotionEffects ()){
						        all.removePotionEffect(effect.getType());
						    }
						}
						
						firework(startLoc);
					}
					
					timer++;
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
		
		if(banList.contains(player.getDisplayName())) {
			event.setJoinMessage(null);
			player.kickPlayer(ChatColor.RED + "이번 게임에는 다시 참여할 수 없습니다.");
		} else {
			new Bar().bar1.addPlayer(player);
			new Bar().bar1.setVisible(false);
		}
		
		player.getInventory().clear();
		
		for(PotionEffect effect : player.getActivePotionEffects ()){
			player.removePotionEffect(effect.getType());
	    }
		
//		ItemStack hel = new ItemStack(Material.LEATHER_HELMET);
//		hel.addEnchantment(Enchantment.BINDING_CURSE, 1);
//		player.getInventory().setHelmet(hel);
//		
//		ItemStack che = new ItemStack(Material.LEATHER_CHESTPLATE);
//		che.addEnchantment(Enchantment.BINDING_CURSE, 1);
//		player.getInventory().setChestplate(che);
//		
//		ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
//		leg.addEnchantment(Enchantment.BINDING_CURSE, 1);
//		player.getInventory().setLeggings(leg);
//		
//		ItemStack bo = new ItemStack(Material.LEATHER_BOOTS);
//		bo.addEnchantment(Enchantment.BINDING_CURSE, 1);
//		player.getInventory().setBoots(bo);
//		
//		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false));
		
	}
	
	@EventHandler
	public void dropEvent(PlayerDropItemEvent event) {
		try {
			if(event.getItemDrop().getItemStack().getType() == Material.IRON_SWORD) {
				event.setCancelled(true);
			}
		} catch(Exception e) {
			
		}
	}
	
	@EventHandler 
	public void respawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		
		player.getInventory().clear();
		
		for(PotionEffect effect : player.getActivePotionEffects ()){
			player.removePotionEffect(effect.getType());
	    }
		
//		ItemStack hel = new ItemStack(Material.LEATHER_HELMET);
//		hel.addEnchantment(Enchantment.BINDING_CURSE, 1);
//		player.getInventory().setHelmet(hel);
//		
//		ItemStack che = new ItemStack(Material.LEATHER_CHESTPLATE);
//		che.addEnchantment(Enchantment.BINDING_CURSE, 1);
//		player.getInventory().setChestplate(che);
//		
//		ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
//		leg.addEnchantment(Enchantment.BINDING_CURSE, 1);
//		player.getInventory().setLeggings(leg);
//		
//		ItemStack bo = new ItemStack(Material.LEATHER_BOOTS);
//		bo.addEnchantment(Enchantment.BINDING_CURSE, 1);
//		player.getInventory().setBoots(bo);
//		
//		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false));
	}
	
	@EventHandler
	public void die(PlayerDeathEvent event) {
		event.setDeathMessage(ChatColor.RED + "" + event.getEntity().getDisplayName() + "님이 살해당했습니다!");
		
		Location loc = event.getEntity().getLocation();
		try {
			Player player = (Player) event.getEntity();
			player.kickPlayer(ChatColor.RED + "You Died");
			
			banList.add(player.getDisplayName());
		} catch(Exception e) {
			
		}
	}
	
	@EventHandler
	public void damageEvent(EntityDamageByEntityEvent event) {
		try {
			if(event.getDamager() instanceof Player) {
				Player damager = (Player) event.getDamager();
				if(damager.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RED + "조커의 검")) {
					event.setDamage(2000);
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
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if(event.getClickedBlock().getType() == Material.STONE_BUTTON || event.getClickedBlock().getType() == Material.WOOD_BUTTON) {
						
						if(event.getClickedBlock().getType() == Material.STONE_BUTTON) {
							limit = 12000.0;
						} else {
							limit = 6000.0;
						}
						
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
							
							for(int i = -300 ; i <= 300 ; i++) {
								for(int j = 40 ; j <= 80 ; j++) {
									for(int k = -300 ; k <= 300 ; k++) {
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
								player.sendMessage(ChatColor.RED + "출발 포인트가 없습니다. 출발 포인트는 하얀 콘크리트로 만들어주세요.");
								start = false;
								return;
							}
							
							
							
							int cnt = 0;
							
							try {
								for(Player all : Bukkit.getOnlinePlayers()) {
									if(all.getLocation().add(0,-1,0).getBlock().getType() == Material.CONCRETE) {
										if(all.getLocation().add(0,-1,0).getBlock().getData() == 13) {
											int num = rnd.nextInt(ary.size());
											Location startLoc = ary.get(num).add(0.5,1,0.5);
											all.teleport(startLoc);
											ary.remove(ary.get(num));
											
											playerList.add(all);
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
								timer = 0;
							}
							
						}
					}
				}
			}
		} catch(Exception e) {
			
		}
	}
	
	@EventHandler
	public void chatEvent(AsyncPlayerChatEvent event) {
		if(event.getPlayer() == new Joker().getJoker()) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				player.sendMessage(ChatColor.DARK_RED + "[조커] " + event.getPlayer().getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
			}
		} else if(event.getPlayer().isOp()) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				player.sendMessage(ChatColor.GOLD + "[관리자] " + event.getPlayer().getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
			}
		} else {
			for(Player player : Bukkit.getOnlinePlayers()) {
				player.sendMessage(ChatColor.GRAY + "[일반인] " + event.getPlayer().getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
			}
		}
		event.setCancelled(true);
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