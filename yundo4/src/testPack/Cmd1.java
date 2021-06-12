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

public class Cmd1 implements CommandExecutor {
	
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
					} else if(job.equals("11")) {
						giveToken11(target);
					} else if(job.equals("12")) {
						giveToken12(target);
					} else if(job.equals("13")) {
						giveToken13(target);
					} else if(job.equals("14")) {
						giveToken14(target);
					} else if(job.equals("15")) {
						giveToken15(target);
					} else if(job.equals("16")) {
						giveToken16(target);
					} else if(job.equals("17")) {
						giveToken17(target);
					} else if(job.equals("18")) {
						giveToken18(target);
					} else if(job.equals("19")) {
						giveToken19(target);
					} else if(job.equals("20")) {
						giveToken20(target);
					} else if(job.equals("21")) {
						giveToken21(target);
					} else if(job.equals("22")) {
						giveToken22(target);
					} else if(job.equals("23")) {
						giveToken23(target);
					} else if(job.equals("100")) {
						giveToken100(target);
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
		var2Im.setLocalizedName("1");
		var2Im.setDisplayName(ChatColor.GOLD + "농부I의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken2(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("2");
		var2Im.setDisplayName(ChatColor.GOLD + "농부II의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken3(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("3");
		var2Im.setDisplayName(ChatColor.GOLD + "대농의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken4(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("4");
		var2Im.setDisplayName(ChatColor.GOLD + "광부I의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken5(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("5");
		var2Im.setDisplayName(ChatColor.GOLD + "광부II의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken6(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("6");
		var2Im.setDisplayName(ChatColor.GOLD + "연금술사의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken7(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("7");
		var2Im.setDisplayName(ChatColor.GOLD + "어부I의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken8(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("8");
		var2Im.setDisplayName(ChatColor.GOLD + "어부II의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken9(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("9");
		var2Im.setDisplayName(ChatColor.GOLD + "태공의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken10(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("10");
		var2Im.setDisplayName(ChatColor.GOLD + "도적의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken11(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("11");
		var2Im.setDisplayName(ChatColor.GOLD + "대도적의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken12(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("12");
		var2Im.setDisplayName(ChatColor.GOLD + "도제의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken13(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("13");
		var2Im.setDisplayName(ChatColor.GOLD + "모험가의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken14(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("14");
		var2Im.setDisplayName(ChatColor.GOLD + "C헌터의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken15(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("15");
		var2Im.setDisplayName(ChatColor.GOLD + "B헌터의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken16(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("16");
		var2Im.setDisplayName(ChatColor.GOLD + "A헌터의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken17(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("17");
		var2Im.setDisplayName(ChatColor.GOLD + "S헌터의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken18(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("18");
		var2Im.setDisplayName(ChatColor.GOLD + "상인I의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken19(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("19");
		var2Im.setDisplayName(ChatColor.GOLD + "상인II의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken20(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("20");
		var2Im.setDisplayName(ChatColor.GOLD + "거상의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken21(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("21");
		var2Im.setDisplayName(ChatColor.GOLD + "기사의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken22(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("22");
		var2Im.setDisplayName(ChatColor.GOLD + "기사장의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken23(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("23");
		var2Im.setDisplayName(ChatColor.GOLD + "기사단장의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
	
	public void giveToken100(Player player) {
		ItemStack var2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta var2Im = var2.getItemMeta();
		var2Im.setLocalizedName("100");
		var2Im.setDisplayName(ChatColor.DARK_RED + "제왕의 증표");
		var2Im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		var2Im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		var2Im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		var2Im.setUnbreakable(true);
		var2.setItemMeta(var2Im);
		
		player.getInventory().setItem(35, var2);
		
		player.sendMessage("새로운 증표를 얻게되었습니다.");
	}
}
