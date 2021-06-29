package testPack;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class CoolTime {

	public static HashMap<HashMap<String, String>, Integer> map = new HashMap<>();
	
	public void board() {
		try {
			for(Player p : Bukkit.getOnlinePlayers()) {
				Scoreboard board = new MakeScoreboard().getManager().getNewScoreboard();
				if(p.getInventory().getItem(8).getItemMeta().getDisplayName().equals("마우러스의 증표")) {
					Objective obj = board.registerNewObjective(ChatColor.GOLD + "쿨타임 정보", "dummy");
					obj.setDisplaySlot(DisplaySlot.SIDEBAR);
					Score score = obj.getScore(ChatColor.BOLD + "RRR  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 10, "RRR")) + "  ");
					score.setScore(2);
					Score score2 = obj.getScore(ChatColor.BOLD + "RRL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 20, "RRL")) + "  ");
					score2.setScore(1);
					Score score3 = obj.getScore(ChatColor.BOLD + "RLL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 25, "RLL")) + "  ");
					score3.setScore(0);
					p.setScoreboard(board);
				} else if(p.getInventory().getItem(8).getItemMeta().getDisplayName().equals("나오의 증표")) {
					Objective obj = board.registerNewObjective(ChatColor.GOLD + "쿨타임 정보", "dummy");
					obj.setDisplaySlot(DisplaySlot.SIDEBAR);
					Score score = obj.getScore(ChatColor.BOLD + "RRR  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 5, "RRR")) + "  ");
					score.setScore(2);
					Score score2 = obj.getScore(ChatColor.BOLD + "RRL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 20, "RRL")) + "  ");
					score2.setScore(1);
					Score score3 = obj.getScore(ChatColor.BOLD + "RLL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 30, "RLL")) + "  ");
					score3.setScore(0);
					p.setScoreboard(board);
				} else if(p.getInventory().getItem(8).getItemMeta().getDisplayName().equals("타르라크의 증표")) {
					Objective obj = board.registerNewObjective(ChatColor.GOLD + "쿨타임 정보", "dummy");
					obj.setDisplaySlot(DisplaySlot.SIDEBAR);
					Score score = obj.getScore(ChatColor.BOLD + "RRR  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 20, "RRR")) + "  ");
					score.setScore(2);
					Score score2 = obj.getScore(ChatColor.BOLD + "RRL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 20, "RRL")) + "  ");
					score2.setScore(1);
					Score score3 = obj.getScore(ChatColor.BOLD + "RLL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 35, "RLL")) + "  ");
					score3.setScore(0);
					p.setScoreboard(board);
				} else if(p.getInventory().getItem(8).getItemMeta().getDisplayName().equals("루에리의 증표")) {
					Objective obj = board.registerNewObjective(ChatColor.GOLD + "쿨타임 정보", "dummy");
					obj.setDisplaySlot(DisplaySlot.SIDEBAR);
					Score score = obj.getScore(ChatColor.BOLD + "RRR  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 25, "RRR")) + "  ");
					score.setScore(2);
					Score score2 = obj.getScore(ChatColor.BOLD + "RRL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 20, "RRL")) + "  ");
					score2.setScore(1);
					Score score3 = obj.getScore(ChatColor.BOLD + "RLL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 10, "RLL")) + "  ");
					score3.setScore(0);
					p.setScoreboard(board);
				} else if(p.getInventory().getItem(8).getItemMeta().getDisplayName().equals("루와이의 증표")) {
					Objective obj = board.registerNewObjective(ChatColor.GOLD + "쿨타임 정보", "dummy");
					obj.setDisplaySlot(DisplaySlot.SIDEBAR);
					Score score = obj.getScore(ChatColor.BOLD + "RRR  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 30, "RRR")) + "  ");
					score.setScore(2);
					Score score2 = obj.getScore(ChatColor.BOLD + "RRL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 30, "RRL")) + "  ");
					score2.setScore(1);
					Score score3 = obj.getScore(ChatColor.BOLD + "RLL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 30, "RLL")) + "  ");
					score3.setScore(0);
					p.setScoreboard(board);
				} else if(p.getInventory().getItem(8).getItemMeta().getDisplayName().equals("아쿨의 증표")) {
					Objective obj = board.registerNewObjective(ChatColor.GOLD + "쿨타임 정보", "dummy");
					obj.setDisplaySlot(DisplaySlot.SIDEBAR);
					Score score = obj.getScore(ChatColor.BOLD + "RRR  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 10, "RRR")) + "  ");
					score.setScore(2);
					Score score2 = obj.getScore(ChatColor.BOLD + "RRL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 30, "RRL")) + "  ");
					score2.setScore(1);
					Score score3 = obj.getScore(ChatColor.BOLD + "RLL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 30, "RLL")) + "  ");
					score3.setScore(0);
					p.setScoreboard(board);
				} else if(p.getInventory().getItem(8).getItemMeta().getDisplayName().equals("콜로서스의 증표")) {
					Objective obj = board.registerNewObjective(ChatColor.GOLD + "쿨타임 정보", "dummy");
					obj.setDisplaySlot(DisplaySlot.SIDEBAR);
					Score score = obj.getScore(ChatColor.BOLD + "RRR  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 40, "RRR")) + "  ");
					score.setScore(2);
					Score score2 = obj.getScore(ChatColor.BOLD + "RRL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 40, "RRL")) + "  ");
					score2.setScore(1);
					Score score3 = obj.getScore(ChatColor.BOLD + "RLL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 20, "RLL")) + "  ");
					score3.setScore(0);
					p.setScoreboard(board);
				} else if(p.getInventory().getItem(8).getItemMeta().getDisplayName().equals("스티브의 증표")) {
					Objective obj = board.registerNewObjective(ChatColor.GOLD + "쿨타임 정보", "dummy");
					obj.setDisplaySlot(DisplaySlot.SIDEBAR);
					Score score = obj.getScore(ChatColor.BOLD + "RRR  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 10, "RRR")) + "  ");
					score.setScore(3);
					Score score2 = obj.getScore(ChatColor.BOLD + "RRL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 20, "RRL")) + "  ");
					score2.setScore(2);
					Score score3 = obj.getScore(ChatColor.BOLD + "RLL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 20, "RLL")) + "  ");
					score3.setScore(1);
					Score score4 = obj.getScore(ChatColor.BOLD + "RLR  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 60, "RLR")) + "  ");
					score4.setScore(0);
					p.setScoreboard(board);
				} else if(p.getInventory().getItem(8).getItemMeta().getDisplayName().equals("마리의 증표")) {
					Objective obj = board.registerNewObjective(ChatColor.GOLD + "쿨타임 정보", "dummy");
					obj.setDisplaySlot(DisplaySlot.SIDEBAR);
					Score score = obj.getScore(ChatColor.BOLD + "RRR  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 15, "RRR")) + "  ");
					score.setScore(2);
					Score score2 = obj.getScore(ChatColor.BOLD + "RRL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 20, "RRL")) + "  ");
					score2.setScore(1);
					Score score3 = obj.getScore(ChatColor.BOLD + "RLL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 30, "RLL")) + "  ");
					score3.setScore(0);
					p.setScoreboard(board);
				} else if(p.getInventory().getItem(8).getItemMeta().getDisplayName().equals("와라에의 증표")) {
					Objective obj = board.registerNewObjective(ChatColor.GOLD + "쿨타임 정보", "dummy");
					obj.setDisplaySlot(DisplaySlot.SIDEBAR);
					Score score = obj.getScore(ChatColor.BOLD + "RRR  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 20, "RRR")) + "  ");
					score.setScore(2);
					Score score2 = obj.getScore(ChatColor.BOLD + "RRL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 20, "RRL")) + "  ");
					score2.setScore(1);
					Score score3 = obj.getScore(ChatColor.BOLD + "RLL  " + ChatColor.GREEN + "" + Integer.toString(timeCheck(p, 10, "RLL")) + "  ");
					score3.setScore(0);
					p.setScoreboard(board);
				} else {
					p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
				}
				
//				if(p.getHealth() > 19) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.AQUA + "|||||20|||||");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 18) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.AQUA + "|||||19|||||");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 17) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.AQUA + "|||||18||||");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 16) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.AQUA + "|||||17||||");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 15) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.AQUA + "|||||16|||");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 14) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.GREEN + "|||||15|||");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 13) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.GREEN + "|||||14||");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 12) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.GREEN + "|||||13||");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 11) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.GREEN + "|||||12|");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 10) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.GREEN + "|||||11|");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 9) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.YELLOW + "|||||10");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 8) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.YELLOW + "|||||9");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 7) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.YELLOW + "||||8");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 6) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.YELLOW + "||||7");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 5) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.YELLOW + "|||6");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 4) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.RED + "|||5");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 3) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.RED + "||4");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 2) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.RED + "||3");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 1) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.RED + "|2");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else if(p.getHealth() > 0) {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.RED + "|1");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				} else {
//					Objective obj = board.registerNewObjective("health", "dummy");
//					obj.setDisplayName(ChatColor.RED + "0");
//					obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//					obj.getScore(p.getDisplayName()).setScore(p.getLevel());
//				}
//				
//				p.setScoreboard(board);
			}
		} catch(Exception e) {
			
		}
	}
	
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
	
	public int timeCheck(Player player, int cool, String key) {
		for(HashMap<String, String> tmp : map.keySet()) {
			for(String id : tmp.keySet()) {
				if(player.getUniqueId().toString().equals(id)) {
//					System.out.println(tmp.get(id));
					if(tmp.get(id).equals(key)) {
						int currentCool = map.get(tmp);
						int ret = cool - currentCool;
						if(ret <= 0) {
							ret = 0;
						}
						return ret;
					} else if(tmp.get(id).equals(key)) {
						int currentCool = map.get(tmp);
						int ret = cool - currentCool;
						if(ret <= 0) {
							ret = 0;
						}
						return ret;
					} else if(tmp.get(id).equals(key)) {
						int currentCool = map.get(tmp);
						int ret = cool - currentCool;
						if(ret <= 0) {
							ret = 0;
						}
						return ret;
					} else if(tmp.get(id).equals(key)) {
						int currentCool = map.get(tmp);
						int ret = cool - currentCool;
						if(ret <= 0) {
							ret = 0;
						}
						return ret;
					}
				}
			}
		}
		return 0;
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
	
	public void resetTime(Player player) {
		
		HashMap<String, String> hash1 = new HashMap<>();
		String id = player.getUniqueId().toString();
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
