package testPack;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class BoardManager {
 
	public static Scoreboard board;
	public static Team red;
	public static Team blue;
	
	public void startBoard() {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		
		red = board.registerNewTeam("red");
		blue = board.registerNewTeam("blue");
	}
	
	public Scoreboard getBoard() {
		return board;
	}
	
	public Team getRed() {
		return red;
	}
	
	public Team getBlue() {
		return blue;
	}
}
