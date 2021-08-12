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

import com.nametagedit.plugin.NametagEdit;

public class Cmd1 implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(!player.isOp()) {
				player.sendMessage("오피만 사용할 수 있는 명령어입니다.");
			}
			
			if(cmd.getName().equalsIgnoreCase("속성")) {
				if(args.length == 0) {
					return true;
				}
				try { 
					String name = args[0];
					Player target = Bukkit.getPlayer(name);
					String ele = args[1];
					
					if(ele.equals("불")) {
						giveElement(target, ele);
					} else if(ele.equals("물")) {
						giveElement(target, ele);
					} else if(ele.equals("풀")) {
						giveElement(target, ele);
					}
					return true;
				} catch(Exception e) {
					return true;
				}
			}
		}	
		return true;
	}
	
	public void giveElement(Player player, String ele) {
		Element e = new Element();
		if(e.fireContainPlayer(player)) {
			e.fireRemovePlayer(player);
		}
		if(e.waterContainPlayer(player)) {
			e.waterRemovePlayer(player);
		}
		if(e.grassContainPlayer(player)) {
			e.grassRemovePlayer(player);
		}
		
		NametagEdit.getApi().setPrefix(player, "");
		
		if(ele.equals("불")) {
			NametagEdit.getApi().setPrefix(player, ChatColor.RED + "");
			e.fireAddPlayer(player);
		} else if(ele.equals("물")) {
			NametagEdit.getApi().setPrefix(player, ChatColor.AQUA + "");
			e.waterAddPlayer(player);
		} else if(ele.equals("풀")) {
			NametagEdit.getApi().setPrefix(player, ChatColor.GREEN + "");
			e.grassAddPlayer(player);
		}
		
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
