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

public class Cmd6 implements CommandExecutor {
	
	static File folder = null;
	
	
	public void setFolder(File f) {
		folder = f;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			try {
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
	    				
	    				String f1 = null;
	    				int f2 = 0;
	    				String s1 = null;
	    				int s2 = 0;
	    				String t1 = null;
	    				int t2 = 0;
	    				
	    				while((name = bufReader.readLine()) != null) {
	        				try {
	        					if(name.substring(0, 1).equals("#")) {
	        						continue;
	        					}
	        					
	        					String[] aryLoc = name.split("/");
	        					
	        					if(Integer.parseInt(aryLoc[1]) > t2) {
	        						if(Integer.parseInt(aryLoc[1]) > s2) {
	        							if(Integer.parseInt(aryLoc[1]) > f2) {
	        								s1 = f1;
	        								s2 = f2;
	    	        						f1 = aryLoc[0];
	    	        						f2 = Integer.parseInt(aryLoc[1]);
	    	        					} else {
	    	        						t1 = s1;
	    	        						t2 = s2;
	    	        						s1 = aryLoc[0];
	    	        						s2 = Integer.parseInt(aryLoc[1]);
	    	        					}
		        					} else {
		        						t1 = aryLoc[0];
		        						t2 = Integer.parseInt(aryLoc[1]);
		        					}
	        					}
	        				} catch(Exception e) {
	        					
	        				}
	    				}
	    				
	    				player.sendMessage(ChatColor.DARK_RED + "[1위] " + ChatColor.WHITE + f1 + "  " + ChatColor.BOLD + f2);
	    				player.sendMessage(ChatColor.AQUA + "[2위] " + ChatColor.WHITE + s1 + "  " + ChatColor.BOLD + s2);
	    				player.sendMessage(ChatColor.GREEN + "[3위] " + ChatColor.WHITE + t1 + "  " + ChatColor.BOLD + t2);
	    				
	    				bufReader.close();
	            	} catch(Exception e) {
	            		
	            	}
				}
			} catch(Exception e) {
				
			}
			
		}	
		return true;
	}

}
