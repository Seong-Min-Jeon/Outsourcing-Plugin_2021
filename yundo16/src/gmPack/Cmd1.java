package gmPack;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(player.isOp()) {
				String type = args[0];
				if(type.equals("0")) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.setGameMode(GameMode.SURVIVAL);
					}
					player.sendMessage("전체 서바이벌 모드로 변경됩니다.");
				} else if(type.equals("1")) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.setGameMode(GameMode.CREATIVE);
					}
					player.sendMessage("전체 크리에이티브 모드로 변경됩니다.");
				} else if(type.equals("2")) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.setGameMode(GameMode.ADVENTURE);
					}
					player.sendMessage("전체 모험 모드로 변경됩니다.");
				} else if(type.equals("3")) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.setGameMode(GameMode.SPECTATOR);
					}
					player.sendMessage("전체 관전자 모드로 변경됩니다.");
				}
			} else {
				player.sendMessage("오피 커맨드입니다.");
			}
		}	
		return true;
	}
}
