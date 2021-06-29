package testPack;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.ScoreboardManager;

public class MakeScoreboard {
	static ScoreboardManager manager = Bukkit.getScoreboardManager();
	
	public ScoreboardManager getManager() {
		return manager;
	}
}
