package testPack;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CoolTime {

	public static HashMap<HashMap<String, String>, Integer> map = new HashMap<>();
	
	public void countTime() {
		for(HashMap<String, String> tmp : map.keySet()) {
			int cnt = map.get(tmp) + 1;
			map.put(tmp, cnt);
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			
			boolean pass = false;
			
			for(HashMap<String, String> tmp : map.keySet()) {
				for(String id : tmp.keySet()) {
					if(p.getUniqueId().toString().equals(id)) {
						pass = true;
					}
				}
			}
			
			if(pass == false) {
				HashMap<String, String> hash1 = new HashMap<>();
				String id = p.getUniqueId().toString();
				hash1.put(id, "RRR");
				map.put(hash1, 100000);
				HashMap<String, String> hash2 = new HashMap<>();
				hash2.put(id, "RRL");
				map.put(hash2, 100000);
				HashMap<String, String> hash3 = new HashMap<>();
				hash3.put(id, "RLL");
				map.put(hash3, 100000);
				HashMap<String, String> hash4 = new HashMap<>();
				hash4.put(id, "RLR");
				map.put(hash4, 100000);
			}
			
		}
	}
	
	public boolean coolCheck(Player player, int cool, String key) {
		for(HashMap<String, String> tmp : map.keySet()) {
			for(String id : tmp.keySet()) {
				if(player.getUniqueId().toString().equals(id)) {
//					System.out.println(tmp.get(id));
					if(tmp.get(id).equals(key)) {
						int currentCool = map.get(tmp);
						if(currentCool >= cool) {
							map.put(tmp, 0);
							return true;
						}
					} else if(tmp.get(id).equals(key)) {
						int currentCool = map.get(tmp);
						if(currentCool >= cool) {
							map.put(tmp, 0);
							return true;
						}
					} else if(tmp.get(id).equals(key)) {
						int currentCool = map.get(tmp);
						if(currentCool >= cool) {
							map.put(tmp, 0);
							return true;
						}
					} else if(tmp.get(id).equals(key)) {
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
	
	public int returnCool(Player player, int cool, String key) {
		for(HashMap<String, String> tmp : map.keySet()) {
			for(String id : tmp.keySet()) {
				if(player.getUniqueId().toString().equals(id)) {
					if(tmp.get(id).equals(key)) {
						int currentCool = map.get(tmp);
						return cool-currentCool;
					} else if(tmp.get(id).equals(key)) {
						int currentCool = map.get(tmp);
						return cool-currentCool;
					} else if(tmp.get(id).equals(key)) {
						int currentCool = map.get(tmp);
						return cool-currentCool;
					} else if(tmp.get(id).equals(key)) {
						int currentCool = map.get(tmp);
						return cool-currentCool;
					}
				}
			}
		}
		return cool;
	}
	
	
	public void showHash(HashMap<HashMap<String, String>, Integer> hash) {
//		for(HashMap<String, String> tmp : hash.keySet()) {
//			for(String id : tmp.keySet()) {
//				System.out.println(id + " " + tmp.get(id) + " " + map.get(tmp));
//			}
//		}
	}
	
	public void showHash2(HashMap<String, String> hash) {
//		for (String id : hash.keySet()) {
//			System.out.println(id + " " + hash.get(id));
//		}
	}
}
