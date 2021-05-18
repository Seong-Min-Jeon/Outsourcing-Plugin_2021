package testPack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Cmd1 implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("지하철")) {
				if(args.length == 0) {
					return true;
				}
				try { 
					String option = args[0];
					String value = null;
					try {
						value = args[1];
					} catch(Exception e) {
						
					}
					// 역 이름 띄어쓰기X
					// 되도록 지하철 운행을 안할 때 추가/삭제
					// 모루 위에서 찍었다는 것을 가정하고 만들었음. 플러그인 파일 들어가서 임의로 수정 ㅇㅋ, 위치 잘못찍으면 에러날거임
					// 마찬가지로 모루 위가 역으로 설정되어있기 때문에, 역을 추가한 후에 건축적으로 역이 사라지면 오류발생 -> 역을 삭제하고 레일을 부수거나, 레일을 부수고 '/지하철 재시작' 전에 역을 삭제해주시길
					
					if(option.equals("역")) {
						ArrayList<Station> ary = new StationManage().getList();
						int size = ary.size();
						if((size / 10) + 1 < Integer.parseInt(value)) {
							player.sendMessage("최대 페이지 수는 " + ((size / 10) + 1) + "페이지 입니다.");
						} else {
							player.sendMessage(ChatColor.DARK_AQUA + "======" + value + "페이지======");
							for(int i = Integer.parseInt(value)*10 - 9 ; i < Integer.parseInt(value)*10 + 1; i++) {
								if(i-1 < size) {
									player.sendMessage(i + ". [" + ary.get(i-1).name + "]   (" + ary.get(i-1).x + ", " + ary.get(i-1).y + ", " + ary.get(i-1).z + ")");
								}
							}
							player.sendMessage(ChatColor.DARK_AQUA + "=================");
						}
						return true;
					} else if(option.equals("추가")) {
						if(!player.isOp()) {
							player.sendMessage("오피만 사용할 수 있는 명령어입니다.");
							return true;
						}
						if(player.getLocation().add(0,-1,0).getBlock().getType() == Material.ANVIL) {
							new StationManage().addStation(value, player.getLocation());
						} else {
							player.sendMessage("올바른 위치가 아닙니다.");
							return true;
						}
						return true;
					} else if(option.equals("삭제")) {
						if(!player.isOp()) {
							player.sendMessage("오피만 사용할 수 있는 명령어입니다.");
							return true;
						}
						new StationManage().removeStation(player, value);
						return true;
					} else if(option.equals("재시작")) {
						if(!player.isOp()) {
							player.sendMessage("오피만 사용할 수 있는 명령어입니다.");
							return true;
						}
						
						ArrayList<Station> ary = new StationManage().getList();
						if(ary.size() == 0) {
							return true;
						}
						Station st = ary.get(ary.size()-1);
						new Train().removeBlock(new Location(player.getWorld(), st.x, st.y, st.z));
						
						new Train().start();
						return true;
					} else if(option.equals("긴급탈출")) {
						if(new Cooltime().effect(player)) {
							player.teleport(new StationManage().getNearStation(player));
							// 위치 수정
						}
						return true;
					} else if(option.equals("테스트")) {
						new Train().trainStart1(new Station("das", 406, 83, 336));
					}
					
				} catch(Exception e) {
					return true;
				}
			}
		}	
		return true;
	}

}
