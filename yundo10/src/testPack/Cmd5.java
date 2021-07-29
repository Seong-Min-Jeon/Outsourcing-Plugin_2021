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

public class Cmd5 implements CommandExecutor {
	
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
	    				while((name = bufReader.readLine()) != null) {
	        				try {
	        					if(name.substring(0, 1).equals("#")) {
	        						continue;
	        					}
	        					
	        					String[] aryLoc = name.split("/");
	        					
	        					if(aryLoc[0].equals(player.getDisplayName())) {
	        						player.sendMessage("당신의 명성수치는 " + ChatColor.BOLD + aryLoc[1] + ChatColor.RESET + " 입니다.");
	        					}
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
		return true;
	}

}
