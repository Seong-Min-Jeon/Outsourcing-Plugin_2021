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
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
