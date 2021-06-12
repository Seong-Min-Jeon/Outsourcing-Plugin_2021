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
import org.bukkit.event.inventory.ClickType;
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

public class Main extends JavaPlugin implements Listener{
	
	HashMap<HashMap<Location, Integer>, Location> map = new HashMap<>();
	Random rnd = new Random();
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		
		getCommand("giveToken").setExecutor(new Cmd1());
	}
	
	@Override
	public void onDisable() {
		getLogger().info(" ");
	}
	
	@EventHandler
	public void mouseEvent(PlayerInteractEvent event) {
		try {
			EquipmentSlot e = event.getHand();
			if (e.equals(EquipmentSlot.HAND)) {
				Player player = event.getPlayer();
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
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
						
					}
				}
			}
		} catch(Exception e) {
			
		}
	}
	
	@EventHandler
	public void dropEvent(PlayerDropItemEvent event) {
		if(event.getItemDrop().getItemStack().getType() == Material.NETHER_STAR) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void clickInv(InventoryClickEvent event) {
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