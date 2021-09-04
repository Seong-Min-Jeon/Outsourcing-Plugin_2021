package testPack;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Cmd1 implements CommandExecutor {
	
	static HashMap<Player, Integer> jobMap = new HashMap<>();
	
	public void open(Player player) {
		Inventory inv = Bukkit.createInventory(player, 9, "Job Table");
		
		ItemStack var1 = new ItemStack(Material.BARRIER);

		ItemStack var2 = new ItemStack(Material.BARRIER);
		 
		ItemStack var3 = new ItemStack(Material.BARRIER);
		
		ItemStack var4 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var4Im = var4.getItemMeta();
		var4Im.setDisplayName(ChatColor.GOLD + "탱커");
		ArrayList<String> var4Lore = new ArrayList();
		var4Lore.add(ChatColor.GRAY + "체력이 2초간 20 증가 (쿨타임 200초)");
		var4Im.setLore(var4Lore);
		var4Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var4Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var4.setItemMeta(var4Im);
		
		ItemStack var5 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var5Im = var5.getItemMeta();
		var5Im.setDisplayName(ChatColor.GOLD + "딜러");
		ArrayList<String> var5Lore = new ArrayList();
		var5Lore.add(ChatColor.GRAY + "이동속도가 3초간 증가 (쿨타임 100초)");
		var5Im.setLore(var5Lore);
		var5Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var5Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var5.setItemMeta(var5Im);
		
		ItemStack var6 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var6Im = var6.getItemMeta();
		var6Im.setDisplayName(ChatColor.GOLD + "힐러");
		ArrayList<String> var6Lore = new ArrayList();
		var6Lore.add(ChatColor.GRAY + "주위 10블럭 이내 아군 힐링 (쿨타임 100초)");
		var6Im.setLore(var6Lore);
		var6Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var6Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var6.setItemMeta(var6Im);
		
		ItemStack var7 = new ItemStack(Material.BARRIER);
		
		ItemStack var8 = new ItemStack(Material.BARRIER);
		
		ItemStack var9 = new ItemStack(Material.BARRIER);

		inv.setItem(0, var1);
		inv.setItem(1, var2);
		inv.setItem(2, var3);
		inv.setItem(3, var4);
		inv.setItem(4, var5);
		inv.setItem(5, var6);
		inv.setItem(6, var7);
		inv.setItem(7, var8);
		inv.setItem(8, var9);
		player.openInventory(inv);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			try {
				open(player);
			} catch(Exception e) {
				
			}
			
		}	
		return true;
	}

}
