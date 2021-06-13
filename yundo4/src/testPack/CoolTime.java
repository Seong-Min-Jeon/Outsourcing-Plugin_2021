package testPack;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CoolTime {

	public static HashMap<HashMap<Player, String>, Integer> map = new HashMap<>();
	
	public void countTime() {
		for(HashMap<Player, String> tmp : map.keySet()) {
			int cnt = map.get(tmp) + 1;
			map.put(tmp, cnt);
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			HashMap<Player, String> hash = new HashMap<>();
			hash.put(p, "RRR");
			map.put(hash, 100000);
			hash.put(p, "RRL");
			map.put(hash, 100000);
			hash.put(p, "RLL");
			map.put(hash, 100000);
			hash.put(p, "RLR");
			map.put(hash, 100000);
		}
	}
	
	public boolean coolCheck(Player player, int cool, String key) {
		for(HashMap<Player, String> tmp : map.keySet()) {
			for(Player p : tmp.keySet()) {
				if(player == p) {
					if(tmp.get(p).equals("RRR")) {
						int currentCool = map.get(tmp);
						if(currentCool >= cool) {
							map.put(tmp, 0);
							return true;
						}
					} else if(tmp.get(p).equals("RRL")) {
						int currentCool = map.get(tmp);
						if(currentCool >= cool) {
							map.put(tmp, 0);
							return true;
						}
					} else if(tmp.get(p).equals("RLL")) {
						int currentCool = map.get(tmp);
						if(currentCool >= cool) {
							map.put(tmp, 0);
							return true;
						}
					} else if(tmp.get(p).equals("RLL")) {
						int currentCool = map.get(tmp);
						if(currentCool >= cool) {
							map.put(tmp, 0);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
}
