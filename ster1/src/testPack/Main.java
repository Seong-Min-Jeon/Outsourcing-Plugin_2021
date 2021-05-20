package testPack;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

public class Main extends JavaPlugin implements Listener{
	
	int time = 0;
	boolean lock = false;
	boolean able = false;
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		
		getCommand("지하철").setExecutor(new Cmd1());
		
		new StationManage().setFolder(getDataFolder());
		new Cooltime().start();
		
		new BukkitRunnable() {
			
			int saveTime = 0;
			
			@Override
			public void run() {
				
				if(new Train().isArrive() && !lock) {
					lock = true;
					Station st = new Train().getCurrentStation();
					//영역안에 있는 사람들 메세지 10초 후 도착, 방송
					for(Player all : Bukkit.getOnlinePlayers()) {
						Location loc = all.getLocation();
						if (loc.getX() <= 500 && loc.getY() <= 40 && loc.getZ() <= -240 
								&& loc.getX() >= 440 && loc.getY() >= 30 && loc.getZ() >= -300) {
							all.sendMessage(ChatColor.WHITE + "10초 후 [" + st.name + "]에 도착합니다.");
							all.playSound(all.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1.0f, 1.0f);
						}
					}
					
					saveTime = time;
				}
				
				if(time - saveTime == 200 && lock) {
					//타고 내릴 수 있음
					able = true;
					
					Station st = new Train().getCurrentStation();
					//메세지
					for(Player all : Bukkit.getOnlinePlayers()) {
						Location loc = all.getLocation();
						if (loc.getX() <= 500 && loc.getY() <= 40 && loc.getZ() <= -240 
								&& loc.getX() >= 440 && loc.getY() >= 30 && loc.getZ() >= -300) {
							all.sendMessage(ChatColor.WHITE + "[" + st.name + "]에 도착했습니다.");
						}
					}
				}
				
				if(time - saveTime == 2400 && lock) {
					able = false;
					lock = false;
					
					//메세지
					for(Player all : Bukkit.getOnlinePlayers()) {
						Location loc = all.getLocation();
						if (loc.getX() <= 500 && loc.getY() <= 40 && loc.getZ() <= -240 
								&& loc.getX() >= 440 && loc.getY() >= 30 && loc.getZ() >= -300) {
							all.sendMessage(ChatColor.WHITE + "기차가 출발합니다.");
						}
					}
				}
				
				time++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	@Override
	public void onDisable() {
		getLogger().info("===============");
	}
	
	@EventHandler
	public void mouseEvent(PlayerInteractEvent event) {
		try {
			EquipmentSlot e = event.getHand();
			if (e.equals(EquipmentSlot.HAND)) {
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if(event.getClickedBlock().getType() == Material.STONE_BUTTON) {
						Player player = event.getPlayer();
						Block b = event.getClickedBlock();
						Location loc = b.getLocation();
						
						if (loc.getX() <= 500 && loc.getY() <= 40 && loc.getZ() <= -240 
								&& loc.getX() >= 440 && loc.getY() >= 30 && loc.getZ() >= -300) {
							if(able) {
								Station st = new Train().getCurrentStation();
								Location dest = new StationManage().getNearPlatform(new Location(player.getWorld(), st.x, st.y, st.z));
								player.teleport(dest);
							}
						}
						
					}
				}
			}
		} catch(Exception e) {
			
		}
    }
	
	@EventHandler
	public void moveEvent(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		try {
			if(able) {
				Station st = new Train().getCurrentStation();
				
				int x = st.x;
				int y = st.y;
				int z = st.z;
				Location loc = player.getLocation();
				
				int length = (int)(Math.pow(loc.getX()-x, 2) + Math.pow(loc.getY()-y, 2) + Math.pow(loc.getZ()-z, 2));
				
				if(length < 45) {
					player.teleport(new Location(player.getWorld(), 473.5, 36, -270.5));
				}
			}
		} catch(Exception e) {
			
		}
		
	}
	
}