package gmPack;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		
		getCommand("gm").setExecutor(new Cmd1());
	}
	
	@Override
	public void onDisable() {
		getLogger().info("===============");
	}
	
}