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
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Cmd8 implements CommandExecutor {
	
	public void open(Player player) {
		Inventory inv = Bukkit.createInventory(player, 9, "Reinforcement Table");
		
		ItemStack var8 = new ItemStack(Material.BARRIER);
		ItemMeta var8Im = var8.getItemMeta();
		var8Im.setDisplayName(ChatColor.RED + " ");
		var8Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var8Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var8.setItemMeta(var8Im);
		
		ItemStack var9 = new ItemStack(Material.SLIME_BALL);
		ItemMeta var9Im = var9.getItemMeta();
		var9Im.setDisplayName(ChatColor.GREEN + "강화하기");
		ArrayList<String> var9Lore = new ArrayList();
		var9Lore.add(ChatColor.GRAY + "강화 크리스탈과 돈을 이용해 무엇이든 강화할 수 있다.");
		var9Lore.add(ChatColor.GRAY + "");
		var9Lore.add(ChatColor.GRAY + "강화 크리스탈 | 돈");
		var9Lore.add(ChatColor.GRAY + "    1개     10골드   (+1)");
		var9Lore.add(ChatColor.GRAY + "    1개     10골드   (+2)");
		var9Lore.add(ChatColor.GRAY + "    1개     10골드   (+3)");
		var9Lore.add(ChatColor.GRAY + "    1개     10골드   (+4)");
		var9Lore.add(ChatColor.GRAY + "    1개     10골드   (+5)");
		var9Lore.add(ChatColor.GRAY + "    2개     20골드   (+6)");
		var9Lore.add(ChatColor.GRAY + "    2개     20골드   (+7)");
		var9Lore.add(ChatColor.GRAY + "    2개     20골드   (+8)");
		var9Lore.add(ChatColor.GRAY + "    2개     20골드   (+9)");
		var9Lore.add(ChatColor.GRAY + "    2개     20골드   (+10)");
		var9Lore.add(ChatColor.GRAY + "    4개     40골드   (+11)");
		var9Lore.add(ChatColor.GRAY + "    5개     60골드   (+12)");
		var9Im.setLore(var9Lore);
		var9Im.addEnchant(Enchantment.DURABILITY, 0, true);
		var9Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var9Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var9.setItemMeta(var9Im);

		inv.setItem(3, var8);
		inv.setItem(4, var8);
		inv.setItem(5, var8);
		inv.setItem(6, var8);
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
