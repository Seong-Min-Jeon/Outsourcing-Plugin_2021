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
import org.bukkit.material.Button;
import org.bukkit.material.Crops;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Main extends JavaPlugin implements Listener{
	
	HashMap<HashMap<Location, Integer>, Location> map = new HashMap<>();
	Random rnd = new Random();
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		
		getCommand("giveToken").setExecutor(new Cmd1());
		
		new BukkitRunnable() {
			int time = 0;

			@Override
			public void run() {
				
				if(time % 20 == 0) {
					new CoolTime().countTime();
				}
				
				time++;
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	@Override
	public void onDisable() {
		getLogger().info(" ");
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
				} catch(Exception e2) {
					
				}
				
				if (event.getAction() == Action.RIGHT_CLICK_AIR) {

					try {
						if(event.getClickedBlock().getType() == Material.ACACIA_DOOR || event.getClickedBlock().getType() == Material.BIRCH_DOOR ||
								event.getClickedBlock().getType() == Material.DARK_OAK_DOOR || event.getClickedBlock().getType() == Material.IRON_DOOR ||
								event.getClickedBlock().getType() == Material.JUNGLE_DOOR || event.getClickedBlock().getType() == Material.SPRUCE_DOOR ||
								event.getClickedBlock().getType() == Material.WOOD_DOOR || event.getClickedBlock().getType() == Material.WOODEN_DOOR) {
							loadData(player.getWorld());
							Block block = event.getClickedBlock();
							Location loc = block.getLocation();
							HashMap<Location, Integer> tmpMap = new HashMap<>();
							for(Location val : map.values()) {
								if(ud(val.getBlockX()) == loc.getBlockX()) {
									if(ud(val.getBlockZ()) == loc.getBlockZ()) {
										if(loc.getBlockY() - ud(val.getBlockY()) < 3) {
											for(Location key2 : getKey(map, val).keySet()) {
												tmpMap.put(key2, getKey(map, val).get(key2));
											}
										}
									}
								}
							}
							
							for(Location key2 : tmpMap.keySet()) {
								try {
									if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals(Integer.toString(tmpMap.get(key2)))) {
										key2.setPitch(player.getLocation().getPitch());
										key2.setYaw(player.getLocation().getYaw());
										player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 0, true, false));
										player.teleport(key2);
										event.setCancelled(true);
									} else if(!player.isOp()) {
										event.setCancelled(true);
									}
								} catch(Exception e1) {
									
								}
							}
							
						} else if(event.getClickedBlock().getType() == Material.BLACK_SHULKER_BOX || event.getClickedBlock().getType() == Material.BLUE_SHULKER_BOX
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
	}
	
	@EventHandler
	public void clickInv(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
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
		} catch (Exception e7) {
			System.err.println(e7);
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
					block.getWorld().dropItem(block.getLocation(), new ItemStack(block.getType(), 1));
				} else if(player.getInventory().getItem(35).getItemMeta().getLocalizedName().equals("6")) {
					block.getWorld().dropItem(block.getLocation(), new ItemStack(block.getType(), 2));
				}
			} 
		} catch(Exception e) {
			
		}
		
	}
	
	@EventHandler
	public void fishingEvent(PlayerFishEvent event) {
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
	}
	
	public void loadData(World world) {
		map.clear();
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
        					String[] aryLoc = name.split("/");
        					
        					HashMap<Location, Integer> tmpMap = new HashMap<>();
        					tmpMap.put(new Location(world, 
        							Double.parseDouble(aryLoc[1].split(" ")[0]),
        							Double.parseDouble(aryLoc[1].split(" ")[1]),
        							Double.parseDouble(aryLoc[1].split(" ")[2])),
        							
        							Integer.parseInt(aryLoc[2]));
        					
        					map.put(tmpMap, 
        							
        							new Location(world, 
                							Double.parseDouble(aryLoc[0].split(" ")[0]),
                							Double.parseDouble(aryLoc[0].split(" ")[1]),
                							Double.parseDouble(aryLoc[0].split(" ")[2])));
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