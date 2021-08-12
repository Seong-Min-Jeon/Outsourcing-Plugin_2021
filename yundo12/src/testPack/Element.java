package testPack;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Element {

	private static ArrayList<Player> fire = new ArrayList<>();
	private static ArrayList<Player> water = new ArrayList<>();
	private static ArrayList<Player> grass = new ArrayList<>();
	
	public boolean fireContainPlayer(Player player) {
		return fire.contains(player);
	}
	
	public boolean waterContainPlayer(Player player) {
		return water.contains(player);
	}
	
	public boolean grassContainPlayer(Player player) {
		return grass.contains(player);
	}
	
	public void fireAddPlayer(Player player) {
		fire.add(player);
	}
	
	public void waterAddPlayer(Player player) {
		water.add(player);
	}
	
	public void grassAddPlayer(Player player) {
		grass.add(player);
	}
	
	public void fireRemovePlayer(Player player) {
		fire.remove(player);
	}
	
	public void waterRemovePlayer(Player player) {
		water.remove(player);
	}
	
	public void grassRemovePlayer(Player player) {
		grass.remove(player);
	}
	
	public void fireClear() {
		fire.clear();
	}
	
	public void waterClear() {
		water.clear();
	}
	
	public void grassClear() {
		grass.clear();
	}
	
}
