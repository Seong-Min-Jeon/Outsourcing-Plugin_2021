package testPack;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class PowerUp {

	private static ArrayList<Player> ary = new ArrayList<>();
	
	public void addAry(Player player) {
		if(ary.contains(player)) {
			ary.remove(player);
		}
		ary.add(player);
	}

	public void removeAry(Player player) {
		if(ary.contains(player)) {
			ary.remove(player);
		}
	}
	
	public boolean containPlayer(Player player) {
		if(ary.contains(player)) {
			return true;
		} else {
			return false;
		}
	}
	
}
