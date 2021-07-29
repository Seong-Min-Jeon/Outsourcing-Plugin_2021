package testPack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Cmd4 implements CommandExecutor {
	
	static File folder = null;
	static ArrayList<Player> ary = new ArrayList<>();
	HashMap<String, String> map = new HashMap<>();
	
	public void setFolder(File f) {
		folder = f;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			try {
				String pName = args[0];
				
				Player target = Bukkit.getPlayer(pName);
				
				if(ary.contains(player)) {
					player.sendMessage(ChatColor.RED + "추천은 하루에 한번만 가능합니다.");
					return true;
				}
				
				if(Bukkit.getOnlinePlayers().contains(target)) {
					ary.add(player);
					
					File dataFolder = folder;
		            if(!dataFolder.exists()) {
		                dataFolder.mkdir();
		            } else {
		            	File dir = new File(dataFolder + "/save");
		            	if(!dir.exists()) {
		            		try{
		            		    dir.mkdir(); 
		            		} catch(Exception e) {
		            		    e.getStackTrace();
		            		}
		            	}
		            	try {
		            		File file = new File(dir, "fame.txt");
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
		        					if(name.substring(0, 1).equals("#")) {
		        						continue;
		        					}
		        					
		        					String[] aryLoc = name.split("/");
		        					
		        					map.put(aryLoc[0], aryLoc[1]);
		        				} catch(Exception e) {
		        					
		        				}
		    				}
		    				bufReader.close();
		            	} catch(Exception e) {
		            		
		            	}
					}
		            
		            if (!dataFolder.exists()) {
						dataFolder.mkdir();
					} else {
						File dir = new File(dataFolder + "/save");
		            	if(!dir.exists()) {
		            		try{
		            		    dir.mkdir(); 
		            		} catch(Exception e) {
		            		    e.getStackTrace();
		            		}
		            	}

		            	File file = new File(dir, "fame.txt");
						if (!file.exists()) {
							try {
								file.createNewFile();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						FileReader filereader = new FileReader(file);
						BufferedReader bufReader = new BufferedReader(filereader);
						BufferedWriter fw = new BufferedWriter(new FileWriter(file));

						if(map.keySet().contains(pName)) {
							for(String key : map.keySet()) {
								if(key.equals(pName)) {
									fw.write(key + "/" + Integer.toString( Integer.parseInt(map.get(key)) + 1 ));
									fw.write("\n");
								} else {
									fw.write(key + "/" + map.get(key));
									fw.write("\n");
								}
							}
						} else {
							fw.write(pName + "/1");
							fw.write("\n");
						}
						
						fw.close();
						bufReader.close();
					}
					
				} else {
					player.sendMessage(ChatColor.RED + "해당 플레이어는 서버에 없습니다.");
				}
			} catch(Exception e) {
				
			}
			
		}	
		return true;
	}

}
