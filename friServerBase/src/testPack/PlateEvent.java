package testPack;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Scoreboard;

public class PlateEvent {
	Scoreboard board;
	
	public boolean plateEvent(Player player) {
		Location loc = player.getLocation();
		if(getTeam(player).equals("red")) {
			if (loc.getX() <= -125 && loc.getY() <= 120 && loc.getZ() <= -10 && 
					loc.getX() >= -140 && loc.getY() >= 110 && loc.getZ() >= -21) {
				return false;
			} else {
				return true;
			}
		} else if(getTeam(player).equals("blue")) {
			if (loc.getX() <= -105 && loc.getY() <= 115 && loc.getZ() <= 101 && 
					loc.getX() >= -120 && loc.getY() >= 110 && loc.getZ() >= 90) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
	
	
	public String getTeam(Player player) {
		board = new BoardManager().getBoard();
		Team red = new BoardManager().getRed();
		Team blue = new BoardManager().getBlue();
		if(red.getPlayers().contains(player)) {
			return "red";
		} else if(blue.getPlayers().contains(player)) {
			return "blue";
		} else {
			return "";
		}
	}
	
}
