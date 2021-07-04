package testPack;

import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

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
				player.sendMessage("오피만 사용할 수 있는 명령어입니다.");
			}
			
			if(cmd.getName().equalsIgnoreCase("joker")) {
				if(args.length == 0) {
					return true;
				}
				try { 
					String name = args[0];
					Player target = Bukkit.getPlayer(name);
					new Joker().setJoker(target);			
					
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.getInventory().clear();
						
						ItemStack hel = new ItemStack(Material.LEATHER_HELMET);
						hel.addEnchantment(Enchantment.BINDING_CURSE, 1);
						p.getInventory().setHelmet(hel);
						
						ItemStack che = new ItemStack(Material.LEATHER_CHESTPLATE);
						che.addEnchantment(Enchantment.BINDING_CURSE, 1);
						p.getInventory().setChestplate(che);
						
						ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
						leg.addEnchantment(Enchantment.BINDING_CURSE, 1);
						p.getInventory().setLeggings(leg);
						
						ItemStack bo = new ItemStack(Material.LEATHER_BOOTS);
						bo.addEnchantment(Enchantment.BINDING_CURSE, 1);
						p.getInventory().setBoots(bo);
					}
					
					ItemStack hel = new ItemStack(Material.IRON_HELMET);
					hel.addEnchantment(Enchantment.BINDING_CURSE, 1);
					target.getInventory().setHelmet(hel);
					
					ItemStack che = new ItemStack(Material.IRON_CHESTPLATE);
					che.addEnchantment(Enchantment.BINDING_CURSE, 1);
					target.getInventory().setChestplate(che);
					
					ItemStack leg = new ItemStack(Material.IRON_LEGGINGS);
					leg.addEnchantment(Enchantment.BINDING_CURSE, 1);
					target.getInventory().setLeggings(leg);
					
					ItemStack bo = new ItemStack(Material.IRON_BOOTS);
					bo.addEnchantment(Enchantment.BINDING_CURSE, 1);
					target.getInventory().setBoots(bo);
					
					ItemStack we = new ItemStack(Material.IRON_SWORD);
					ItemMeta im = we.getItemMeta();
					im.setDisplayName(ChatColor.RED + "조커의 검");
					we.setItemMeta(im);
					target.getInventory().setItem(0, we);
					
					target.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false));
					target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false));
					
					for(Player p : Bukkit.getOnlinePlayers()) {
						try {
							PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
									ChatSerializer.a("{\"text\":\"§C조커는" + target.getDisplayName() + "입니다!\"}"));
//							PacketPlayOutTitle subtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, 
//									ChatSerializer.a("{\"text\":\"§710초 후에 부활합니다.\"}"));
							Object handle = p.getClass().getMethod("getHandle").invoke(p);
					        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
					        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, title);
//					        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, subtitle);
						} catch(Exception e) {
							
						}
					}
					
					return true;
				} catch(Exception e) {
					return true;
				}
			}
		}	
		return true;
	}

	private Class<?> getNMSClass(String name) {
		try {
	        return Class.forName("net.minecraft.server."
	                + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
}
