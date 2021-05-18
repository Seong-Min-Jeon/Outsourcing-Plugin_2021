package testPack;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin implements Listener{
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		
		getCommand("지하철").setExecutor(new Cmd1());
		
		new StationManage().setFolder(getDataFolder());
		new Cooltime().start();
	}
	
	@Override
	public void onDisable() {
		getLogger().info("===============");
	}
	
}