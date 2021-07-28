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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Cmd3 implements CommandExecutor {
	
	static File folder = null;
	
	public void open(Player player) {
		Inventory inv = Bukkit.createInventory(player, 9, "Job Table");
		
		ItemStack var1 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var1Im = var1.getItemMeta();
		var1Im.setDisplayName(ChatColor.GOLD + "농부의 별");
		ArrayList<String> var1Lore = new ArrayList();
		var1Lore.add(ChatColor.GRAY + "10골드를 지불하는 것으로 농부I 직업을 선택할 수 있다.");
		var1Lore.add(ChatColor.GRAY + "(직업이 없는 경우에는 골드 지불이 필요없다.)");
		var1Im.setLore(var1Lore);
		var1Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var1Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var1.setItemMeta(var1Im);

		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setDisplayName(ChatColor.GOLD + "광부의 별");
		ArrayList<String> var2Lore = new ArrayList();
		var2Lore.add(ChatColor.GRAY + "10골드를 지불하는 것으로 광부I 직업을 선택할 수 있다.");
		var2Lore.add(ChatColor.GRAY + "(직업이 없는 경우에는 골드 지불이 필요없다.)");
		var2Im.setLore(var2Lore);
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2.setItemMeta(var2Im);
		 
		ItemStack var3 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var3Im = var3.getItemMeta();
		var3Im.setDisplayName(ChatColor.GOLD + "어부의 별");
		ArrayList<String> var3Lore = new ArrayList();
		var3Lore.add(ChatColor.GRAY + "10골드를 지불하는 것으로 어부I 직업을 선택할 수 있다.");
		var3Lore.add(ChatColor.GRAY + "(직업이 없는 경우에는 골드 지불이 필요없다.)");
		var3Im.setLore(var3Lore);
		var3Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var3Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var3.setItemMeta(var3Im);
		
		ItemStack var4 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var4Im = var4.getItemMeta();
		var4Im.setDisplayName(ChatColor.GOLD + "도적의 별");
		ArrayList<String> var4Lore = new ArrayList();
		var4Lore.add(ChatColor.GRAY + "10골드를 지불하는 것으로 도적 직업을 선택할 수 있다.");
		var4Lore.add(ChatColor.GRAY + "(직업이 없는 경우에는 골드 지불이 필요없다.)");
		var4Im.setLore(var4Lore);
		var4Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var4Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var4.setItemMeta(var4Im);
		
		ItemStack var5 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var5Im = var5.getItemMeta();
		var5Im.setDisplayName(ChatColor.GOLD + "모험가의 별");
		ArrayList<String> var5Lore = new ArrayList();
		var5Lore.add(ChatColor.GRAY + "10골드를 지불하는 것으로 모험가 직업을 선택할 수 있다.");
		var5Lore.add(ChatColor.GRAY + "(직업이 없는 경우에는 골드 지불이 필요없다.)");
		var5Im.setLore(var5Lore);
		var5Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var5Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var5.setItemMeta(var5Im);
		
		ItemStack var6 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var6Im = var6.getItemMeta();
		var6Im.setDisplayName(ChatColor.GOLD + "상인의 별");
		ArrayList<String> var6Lore = new ArrayList();
		var6Lore.add(ChatColor.GRAY + "10골드를 지불하는 것으로 상인I 직업을 선택할 수 있다.");
		var6Lore.add(ChatColor.GRAY + "(직업이 없는 경우에는 골드 지불이 필요없다.)");
		var6Im.setLore(var6Lore);
		var6Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var6Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var6.setItemMeta(var6Im);
		
		ItemStack var7 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var7Im = var7.getItemMeta();
		var7Im.setDisplayName(ChatColor.GOLD + "기사의 별");
		ArrayList<String> var7Lore = new ArrayList();
		var7Lore.add(ChatColor.GRAY + "10골드를 지불하는 것으로 기사 직업을 선택할 수 있다.");
		var7Lore.add(ChatColor.GRAY + "(직업이 없는 경우에는 골드 지불이 필요없다.)");
		var7Im.setLore(var7Lore);
		var7Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var7Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var7.setItemMeta(var7Im);
		
		ItemStack var8 = new ItemStack(Material.BARRIER);
		ItemMeta var8Im = var8.getItemMeta();
		var8Im.setDisplayName(ChatColor.RED + " ");
		var8Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var8Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var8.setItemMeta(var8Im);
		
		ItemStack var9 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var9Im = var9.getItemMeta();
		var9Im.setDisplayName(ChatColor.GOLD + "직업 업그레이드");
		ArrayList<String> var9Lore = new ArrayList();
		var9Lore.add(ChatColor.GRAY + "50골드를 지불하는 것으로 현재 직업을 2단계로 업그레이드 할 수 있다.");
		var9Lore.add(ChatColor.GRAY + "(직업이 없는 경우에는 사용할 수 없다.)");
		var9Im.setLore(var9Lore);
		var9Im.addEnchant(Enchantment.DURABILITY, 0, true);
		var9Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var9Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var9.setItemMeta(var9Im);

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
