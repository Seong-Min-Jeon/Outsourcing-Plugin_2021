package testPack;

import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Cmd1 implements CommandExecutor {
	
	ArrayList<Location> ary = new ArrayList<>();
	Random rnd = new Random();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("부활")) {
				if(args.length == 0) {
					return true;
				}
				String name = args[0];
				Player target = Bukkit.getPlayer(name);
				
				if(Bukkit.getOnlinePlayers().contains(target)) {
					if(player.getInventory().contains(Material.BONE)) {
						World world = Bukkit.getWorld("world");
						if (world.getTime() == 0) {
							if (target.getLocation().add(0, -1, 0).getBlock().getType() == Material.CONCRETE) {
								if (target.getLocation().add(0, -1, 0).getBlock().getData() == 13) {

									Location loc = null;
									for (int i = -300; i <= 300; i++) {
										for (int j = 40; j <= 80; j++) {
											for (int k = -300; k <= 300; k++) {
												loc = new Location(world, i, j, k);
												if (loc.getBlock().getType() == Material.CONCRETE) {
													if (loc.getBlock().getData() == 4) {
														ary.add(loc);
													}
												}
											}
										}
									}

									int num = rnd.nextInt(ary.size());
									Location startLoc = ary.get(num).add(0.5, 1, 0.5);
									target.teleport(startLoc);
									ary.remove(ary.get(num));
									
									new PlayerList().playerList.add(target);

									player.getInventory().getItem(10).setAmount(player.getInventory().getItem(10).getAmount() - 1);

									for (Player all : Bukkit.getOnlinePlayers()) {
										all.sendMessage(target.getDisplayName() + " 돌아와!");
									}
								} else {
									player.sendMessage("대상이 현재 부활 가능한 위치에 존재하지 않습니다.");
								}
							} else {
								player.sendMessage("대상이 현재 부활 가능한 위치에 존재하지 않습니다.");
							}
						} else {
							player.sendMessage("부활시킬 수 있는 시간이 아닙니다.");
						}
					} else {
						player.sendMessage("부활의 증표가 없습니다.");
					}
				} else {
					player.sendMessage("존재하지 않는 플레이어입니다.");
				}

				return true;

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
