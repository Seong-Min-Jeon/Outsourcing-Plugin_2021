package testPack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Cmd2 implements CommandExecutor {
	
	static File folder = null;
	
	public void setFolder(File f) {
		folder = f;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(!player.isOp()) {
				player.sendMessage("오피 명령어 입니다!");
				return false;
			}
			
			try {
				String mo = args[0];
				
				int x = player.getLocation().getBlockX();
				int y = player.getLocation().getBlockY();
				int z = player.getLocation().getBlockZ();
				
				File dataFolder = folder;
				
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

	            	File file = new File(dir, "door.txt");
					if (!file.exists()) {
						try {
							file.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					FileReader filereader = new FileReader(file);
					BufferedReader bufReader = new BufferedReader(filereader);
					BufferedWriter fw = new BufferedWriter(new FileWriter(file, true));

					fw.write(x + " " + y + " " + z + "/" + mo);
					fw.write("\n");
					fw.close();
					bufReader.close();
				}
				
				player.sendMessage("설정완료!");
			} catch(Exception e) {
				
			}
			
		}	
		return true;
	}

}
