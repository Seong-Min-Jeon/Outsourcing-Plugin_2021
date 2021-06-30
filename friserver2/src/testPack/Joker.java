package testPack;

import org.bukkit.entity.Player;

public class Joker {

	static Player joker = null;
	
	public void setJoker(Player player) {
		joker = player;
	}
	
	public Player getJoker() {
		return joker;
	}
}
