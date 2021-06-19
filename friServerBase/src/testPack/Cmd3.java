package testPack;

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

public class Cmd3 implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(!player.isOp()) {
				player.sendMessage("오피 명령어 입니다!");
				return false;
			}
			
			try {
				String name = args[0];
				String job = args[1];
				
				Player target = Bukkit.getPlayer(name);
				if(Bukkit.getOnlinePlayers().contains(target)) {
					if(job.equals("1")) {
						giveToken1(target);
					} else if(job.equals("2")) {
						giveToken2(target);
					} else if(job.equals("3")) {
						giveToken3(target);
					} else if(job.equals("4")) {
						giveToken4(target);
					} else if(job.equals("5")) {
						giveToken5(target);
					} else if(job.equals("6")) {
						giveToken6(target);
					} else if(job.equals("7")) {
						giveToken7(target);
					} else if(job.equals("8")) {
						giveToken8(target);
					} else if(job.equals("9")) {
						giveToken9(target);
					} else if(job.equals("10")) {
						giveToken10(target);
					} 
				}
				player.sendMessage("성공적으로 증표를 수여하였습니다.");
			} catch(Exception e) {
				
			}
			
		}	
		return true;
	}

	public void giveToken1(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setDisplayName("마우러스의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(8, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken2(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setDisplayName("나오의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(8, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken3(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setDisplayName("타르라크의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(8, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken4(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setDisplayName("루에리의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(8, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken5(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setDisplayName("루와이의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(8, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken6(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setDisplayName("아쿨의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(8, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken7(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setDisplayName("콜로서스의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(8, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken8(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setDisplayName("스티브의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(8, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken9(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setDisplayName("마리의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(8, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken10(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setDisplayName("와라에의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(8, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}

}
