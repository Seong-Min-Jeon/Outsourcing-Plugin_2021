package testPack;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Train {
	
	// 긴급탈출 시 플랫폼으로 이동 o
	// 처음역에서 재시작 시 모습이 보여야됨 o
	// 마지막역에서 대기 o
	// 기차 내부에서 메세지, 소리 o
	// 내리고 타는거 o

	static boolean stop = false;
	int time = 0;
	static int st = 0;
	static boolean arrived = false;

	public void start() {
		
		time = 0;
		st = 0;
		arrived = true;
		
		new BukkitRunnable() {

			Station current = null;
			Station next = null;
			
			@Override
			public void run() {
				
				if(st < new StationManage().getList().size()) {
					// 정차
					if(time % 3600 == 0) {
						arrived = true;
						
						// next의 위치에서 current위치부터 도착하는 움직임이 보임
						trainArrive(current, next);
						
						current = new StationManage().getList().get(st);
						if(time == 0) {
							trainArrive(current, next);
						}
						st++;
					}
					
					// 운행중
					if(time % 3600 == 2400) {
						arrived = false;
						
						next = new StationManage().getList().get(st);
						
						// current위치부터 next로 출발하는 움직임이 보임
						trainStart(current, next);
					}
					
				} else {
					this.cancel();
				}
				
				if(stop) {
					this.cancel();
				}
				time++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	public void trainStart(Station current, Station next) {
		if(current != null && next != null) {
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y-1;
			int z = current.z;
			String rot = null;
			String dir = null;
			
			Block flag = world.getBlockAt(new Location(world, x, y, z));
			
			// 가로 세로 체크
			Block search = null;
			if(flag.getType() == Material.ANVIL) {
				search = world.getBlockAt(new Location(world, x+1, y, z));
				if(search.getType() == Material.ANVIL) {
					rot = "h";
				}
				search = world.getBlockAt(new Location(world, x, y, z+1));
				if(search.getType() == Material.ANVIL) {
					rot = "v";
				}
			}
			
			// 방향성 체크
			if(rot.equals("h")) {
				if(current.x > next.x) {
					dir = "c>n";
				} else {
					dir = "c<n";
				}
			} else if(rot.equals("v")) {
				if(current.z > next.z) {
					dir = "c>n";
				} else {
					dir = "c<n";
				}
			}
			
			// 실제 구현
			if(rot.equals("h")) {
				if(dir.equals("c>n")) {
					// x축으로 이동, x가 감소하는 방향
					if(flag.getLocation().add(0,0,3).getBlock().getType() == Material.ANVIL) {
						trainStart1(current);
					} else {
						trainStart1_1(current);
					}
				} else {
					// x축으로 이동, x가 증가하는 방향
					if(flag.getLocation().add(0,0,3).getBlock().getType() == Material.ANVIL) {
						trainStart2(current);
					} else {
						trainStart2_1(current);
					}
				}
			} else if(rot.equals("v")) {
				if(dir.equals("c>n")) {
					// y축으로 이동, y가 감소하는 방향
					if(flag.getLocation().add(3,0,0).getBlock().getType() == Material.ANVIL) {
						trainStart3(current);
					} else {
						trainStart3_1(current);
					}
				} else {
					// y축으로 이동, y가 증가하는 방향
					if(flag.getLocation().add(3,0,0).getBlock().getType() == Material.ANVIL) {
						trainStart4(current);
					} else {
						trainStart4_1(current);
					}
				}
			}
			
		} else if(current != null && next == null) {
			// 처음역
			Station second = new StationManage().getList().get(1);
			
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y-1;
			int z = current.z;
			String rot = null;
			String dir = null;
			
			Block flag = world.getBlockAt(new Location(world, x, y, z));
			
			// 가로 세로 체크
			Block search = null;
			if(flag.getType() == Material.ANVIL) {
				search = world.getBlockAt(new Location(world, x+1, y, z));
				if(search.getType() == Material.ANVIL) {
					rot = "h";
				}
				search = world.getBlockAt(new Location(world, x, y, z+1));
				if(search.getType() == Material.ANVIL) {
					rot = "v";
				}
			}
			
			// 방향성 체크
			if(rot.equals("h")) {
				if(current.x > second.x) {
					dir = "c>n";
				} else {
					dir = "c<n";
				}
			} else if(rot.equals("v")) {
				if(current.z > second.z) {
					dir = "c>n";
				} else {
					dir = "c<n";
				}
			}
			
			// 실제 구현
			if(rot.equals("h")) {
				if(dir.equals("c>n")) {
					// x축으로 이동, x가 감소하는 방향
					if(flag.getLocation().add(0,0,3).getBlock().getType() == Material.ANVIL) {
						trainStartFirst1(current);
					} else {
						trainStartFirst1_1(current);
					}
				} else {
					// x축으로 이동, x가 증가하는 방향
					if(flag.getLocation().add(0,0,3).getBlock().getType() == Material.ANVIL) {
						trainStartFirst2(current);
					} else {
						trainStartFirst2_1(current);
					}
				}
			} else if(rot.equals("v")) {
				if(dir.equals("c>n")) {
					// y축으로 이동, y가 감소하는 방향
					if(flag.getLocation().add(3,0,0).getBlock().getType() == Material.ANVIL) {
						trainStartFirst3(current);
					} else {
						trainStartFirst3_1(current);
					}
				} else {
					// y축으로 이동, y가 증가하는 방향
					if(flag.getLocation().add(3,0,0).getBlock().getType() == Material.ANVIL) {
						trainStartFirst4(current);
					} else {
						trainStartFirst4_1(current);
					}
				}
			}
			
		}
	}
	
	public void trainStart1(Station current) {
		new BukkitRunnable() {
			
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y;
			int z = current.z;
			Location loc = new Location(world, x, y, z);
			
			int timer = 0; 
			
			@Override
			public void run() {
				
				if(timer % 1 == 0) {
					removeBlock(loc);
					// 1층
					setBlock(loc.clone().add(-6, 0, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 0, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-1, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(4, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(-6, 0, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 0, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 0, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-1, 0, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(4, 0, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(-6, 0, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 0, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 2층
					setBlock(loc.clone().add(-6, 1, -1).add(-timer * 0.1, 0, 0), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, 4).add(-timer * 0.1, 0, 0), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					// 3층
					setBlock(loc.clone().add(-6, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(-6, 2, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 2, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 2, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 2, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 2, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 2, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					// 4층
					setBlock(loc.clone().add(-6, 3, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 3, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, -1).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 3, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 3, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 3, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 3, 0).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(5, 3, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 3, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(5, 3, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 3, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(5, 3, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 3, 3).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(5, 3, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 3, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 3, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, 4).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 3, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 3, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 3, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 5층
					setBlock(loc.clone().add(-6, 4, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 4, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, -1).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 4, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 4, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 4, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 4, 0).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(5, 4, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 4, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(5, 4, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 4, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(5, 4, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 4, 3).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(5, 4, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 4, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 4, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, 4).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 4, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 4, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 4, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 6층
					setBlock(loc.clone().add(-5, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(-5, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					// 7층
					setBlock(loc.clone().add(-5, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, 0).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 6, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, 2).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 6, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, 3).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 6, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
				}
				
				if(timer >= 100) {
					removeBlock(loc);
					this.cancel();
				}
				
				timer++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	public void trainStart1_1(Station current) {
		new BukkitRunnable() {
			
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y;
			int z = current.z;
			Location loc = new Location(world, x, y, z);
			
			int timer = 0; 
			
			@Override
			public void run() {
				
				if(timer % 1 == 0) {
					removeBlock(loc);
					// 1층
					setBlock(loc.clone().add(-6, 0, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 0, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-1, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(4, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(-6, 0, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 0, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 0, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-1, 0, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(4, 0, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(-6, 0, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 0, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 2층
					setBlock(loc.clone().add(-6, 1, 1).add(-timer * 0.1, 0, 0), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, -4).add(-timer * 0.1, 0, 0), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					// 3층
					setBlock(loc.clone().add(-6, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(-6, 2, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 2, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 2, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 2, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 2, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 2, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					// 4층
					setBlock(loc.clone().add(-6, 3, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 3, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, 1).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 3, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 3, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 3, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 3, 0).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(5, 3, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 3, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(5, 3, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 3, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(5, 3, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 3, -3).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(5, 3, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 3, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 3, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, -4).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 3, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 3, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 3, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 5층
					setBlock(loc.clone().add(-6, 4, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 4, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, 1).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 4, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 4, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 4, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 4, 0).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(5, 4, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 4, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(5, 4, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 4, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(5, 4, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 4, -3).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(5, 4, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 4, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 4, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, -4).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 4, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 4, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 4, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 6층
					setBlock(loc.clone().add(-5, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(-5, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					// 7층
					setBlock(loc.clone().add(-5, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, 0).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 6, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, -2).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 6, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, -3).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 6, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
				}
				
				if(timer >= 100) {
					removeBlock(loc);
					this.cancel();
				}
				
				timer++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	public void trainStart2(Station current) {
		new BukkitRunnable() {
			
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y;
			int z = current.z;
			Location loc = new Location(world, x, y, z);
			
			int timer = 0; 
			
			@Override
			public void run() {
				
				if(timer % 1 == 0) {
					removeBlock(loc);
					// 1층
					setBlock(loc.clone().add(6, 0, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 0, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(1, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-4, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(6, 0, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 0, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 0, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(1, 0, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-4, 0, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(6, 0, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 0, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 2층
					setBlock(loc.clone().add(6, 1, -1).add(timer * 0.1, 0, 0), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(5, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, 4).add(timer * 0.1, 0, 0), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(5, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					// 3층
					setBlock(loc.clone().add(6, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(6, 2, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 2, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 2, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 2, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 2, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 2, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					// 4층
					setBlock(loc.clone().add(6, 3, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 3, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, -1).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 3, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 3, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 3, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 3, 0).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-5, 3, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 3, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-5, 3, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 3, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-5, 3, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 3, 3).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-5, 3, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 3, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 3, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, 4).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 3, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 3, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 3, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 5층
					setBlock(loc.clone().add(6, 4, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 4, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, -1).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 4, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 4, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 4, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 4, 0).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-5, 4, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 4, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-5, 4, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 4, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-5, 4, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 4, 3).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-5, 4, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 4, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 4, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, 4).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 4, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 4, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 4, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 6층
					setBlock(loc.clone().add(5, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(5, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					// 7층
					setBlock(loc.clone().add(5, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, 0).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, 2).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, 3).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
				}
				
				if(timer >= 100) {
					removeBlock(loc);
					this.cancel();
				}
				
				timer++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	public void trainStart2_1(Station current) {
		new BukkitRunnable() {
			
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y;
			int z = current.z;
			Location loc = new Location(world, x, y, z);
			
			int timer = 0; 
			
			@Override
			public void run() {

				if(timer % 1 == 0) {
					removeBlock(loc);
					// 1층
					setBlock(loc.clone().add(6, 0, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 0, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(1, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-4, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(6, 0, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 0, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 0, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(1, 0, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-4, 0, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(6, 0, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 0, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 2층
					setBlock(loc.clone().add(6, 1, 1).add(timer * 0.1, 0, 0), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(5, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, -4).add(timer * 0.1, 0, 0), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(5, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					// 3층
					setBlock(loc.clone().add(6, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(6, 2, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 2, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 2, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 2, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 2, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 2, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					// 4층
					setBlock(loc.clone().add(6, 3, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 3, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, 1).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 3, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 3, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 3, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 3, 0).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-5, 3, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 3, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-5, 3, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 3, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-5, 3, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 3, -3).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-5, 3, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 3, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 3, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, -4).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 3, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 3, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 3, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 5층
					setBlock(loc.clone().add(6, 4, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 4, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, 1).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 4, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 4, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 4, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 4, 0).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-5, 4, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 4, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-5, 4, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 4, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-5, 4, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 4, -3).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-5, 4, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 4, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 4, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, -4).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 4, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 4, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 4, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 6층
					setBlock(loc.clone().add(5, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(5, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					// 7층
					setBlock(loc.clone().add(5, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, 0).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, -2).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, -3).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
				}
				
				if(timer >= 100) {
					removeBlock(loc);
					this.cancel();
				}
				
				timer++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	public void trainStart3(Station current) {
		new BukkitRunnable() {
			
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y;
			int z = current.z;
			Location loc = new Location(world, x, y, z);
			
			int timer = 0; 
			
			@Override
			public void run() {
				
				if(timer % 1 == 0) {
					removeBlock(loc);
					// 1층
					setBlock(loc.clone().add(-1, 0, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 0, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(0, 0, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 0, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 0, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(3, 0, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 0, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 0, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(4, 0, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 0, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 2층
					setBlock(loc.clone().add(-1, 1, -6).add(0, 0, -timer * 0.1), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(0, 1, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(1, 1, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(2, 1, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(3, 1, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(4, 1, -6).add(0, 0, -timer * 0.1), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					// 3층
					setBlock(loc.clone().add(-1, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(0, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(4, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					
					// 4층
					setBlock(loc.clone().add(-1, 3, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, -4).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-1, 3, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(0, 3, -6).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(0, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 3, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(1, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 3, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(2, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 3, -6).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(4, 3, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, -4).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(4, 3, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 5층
					setBlock(loc.clone().add(-1, 4, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, -4).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-1, 4, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(0, 4, -6).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(0, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 4, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(1, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 4, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(2, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 4, -6).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(4, 4, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, -4).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(4, 4, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 6층
					setBlock(loc.clone().add(-1, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(0, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(4, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					
					// 7층
					setBlock(loc.clone().add(-1, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -4).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -3).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -2).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -1).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 0).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 1).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 2).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 3).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 4).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					
					setBlock(loc.clone().add(0, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(4, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -4).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -3).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -2).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -1).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 0).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 1).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 2).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 3).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 4).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
				}
				
				if(timer >= 100) {
					removeBlock(loc);
					this.cancel();
				}
				
				timer++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	public void trainStart3_1(Station current) {
		new BukkitRunnable() {
			
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y;
			int z = current.z;
			Location loc = new Location(world, x, y, z);
			
			int timer = 0; 
			
			@Override
			public void run() {
				
				if(timer % 1 == 0) {
					removeBlock(loc);
					// 1층
					setBlock(loc.clone().add(1, 0, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 0, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(0, 0, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 0, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 0, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(-3, 0, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 0, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 0, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(-4, 0, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 0, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 2층
					setBlock(loc.clone().add(1, 1, -6).add(0, 0, -timer * 0.1), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(0, 1, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-1, 1, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-2, 1, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-3, 1, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-4, 1, -6).add(0, 0, -timer * 0.1), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					// 3층
					setBlock(loc.clone().add(1, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(0, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-4, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					
					// 4층
					setBlock(loc.clone().add(1, 3, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, -4).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(1, 3, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(0, 3, -6).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(0, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 3, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-1, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 3, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-2, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 3, -6).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-4, 3, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, -4).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-4, 3, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 5층
					setBlock(loc.clone().add(1, 4, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, -4).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(1, 4, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(0, 4, -6).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(0, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 4, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-1, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 4, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-2, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 4, -6).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-4, 4, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, -4).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-4, 4, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 6층
					setBlock(loc.clone().add(1, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(0, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-4, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					
					// 7층
					setBlock(loc.clone().add(1, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -4).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -3).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -2).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -1).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 0).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 1).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 2).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 3).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 4).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					
					setBlock(loc.clone().add(0, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-4, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -4).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -3).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -2).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -1).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 0).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 1).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 2).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 3).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 4).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
				}
				
				if(timer >= 100) {
					removeBlock(loc);
					this.cancel();
				}
				
				timer++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	public void trainStart4(Station current) {
		new BukkitRunnable() {
			
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y;
			int z = current.z;
			Location loc = new Location(world, x, y, z);
			
			int timer = 0; 
			
			@Override
			public void run() {
				
				if(timer % 1 == 0) {
					removeBlock(loc);
					// 1층
					setBlock(loc.clone().add(-1, 0, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 0, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(0, 0, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 0, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 0, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(3, 0, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 0, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 0, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(4, 0, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 0, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 2층
					setBlock(loc.clone().add(-1, 1, 6).add(0, 0, timer * 0.1), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(0, 1, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(1, 1, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(2, 1, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(3, 1, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(4, 1, 6).add(0, 0, timer * 0.1), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					// 3층
					setBlock(loc.clone().add(-1, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(0, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(4, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					
					// 4층
					setBlock(loc.clone().add(-1, 3, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, 4).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-1, 3, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(0, 3, 6).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(0, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 3, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(1, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 3, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(2, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 3, 6).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(4, 3, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, 4).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(4, 3, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 5층
					setBlock(loc.clone().add(-1, 4, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, 4).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-1, 4, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(0, 4, 6).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(0, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 4, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(1, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 4, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(2, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 4, 6).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(4, 4, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, 4).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(4, 4, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 6층
					setBlock(loc.clone().add(-1, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(0, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(4, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					
					// 7층
					setBlock(loc.clone().add(-1, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 4).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 3).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 2).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 1).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 0).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -1).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -2).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -3).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -4).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					
					setBlock(loc.clone().add(0, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(4, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 4).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 3).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 2).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 1).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 0).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -1).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -2).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -3).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -4).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
				}
				
				if(timer >= 100) {
					removeBlock(loc);
					this.cancel();
				}
				
				timer++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	public void trainStart4_1(Station current) {
		new BukkitRunnable() {
			
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y;
			int z = current.z;
			Location loc = new Location(world, x, y, z);
			
			int timer = 0; 
			
			@Override
			public void run() {
				
				if(timer % 1 == 0) {
					removeBlock(loc);
					// 1층
					setBlock(loc.clone().add(1, 0, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 0, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(0, 0, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 0, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 0, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(-3, 0, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 0, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 0, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(-4, 0, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 0, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 2층
					setBlock(loc.clone().add(1, 1, 6).add(0, 0, timer * 0.1), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(0, 1, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-1, 1, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-2, 1, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-3, 1, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-4, 1, 6).add(0, 0, timer * 0.1), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					// 3층
					setBlock(loc.clone().add(1, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(0, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-4, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					
					// 4층
					setBlock(loc.clone().add(1, 3, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, 4).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(1, 3, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(0, 3, 6).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(0, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 3, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-1, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 3, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-2, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 3, 6).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-4, 3, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, 4).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-4, 3, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 5층
					setBlock(loc.clone().add(1, 4, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, 4).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(1, 4, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(0, 4, 6).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(0, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 4, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-1, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 4, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-2, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 4, 6).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-4, 4, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, 4).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-4, 4, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 6층
					setBlock(loc.clone().add(1, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(0, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-4, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					
					// 7층
					setBlock(loc.clone().add(1, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 4).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 3).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 2).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 1).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 0).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -1).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -2).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -3).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -4).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					
					setBlock(loc.clone().add(0, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-4, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 4).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 3).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 2).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 1).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 0).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -1).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -2).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -3).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -4).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
				}
				
				if(timer >= 100) {
					removeBlock(loc);
					this.cancel();
				}
				
				timer++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	public void trainArrive(Station current, Station next) {
		if(current != null && next != null) {
			World world = Bukkit.getWorld("world");
			int x = next.x;
			int y = next.y-1;
			int z = next.z;
			String rot = null;
			String dir = null;
			
			Block flag = world.getBlockAt(new Location(world, x, y, z));
			
			// 가로 세로 체크
			Block search = null;
			if(flag.getType() == Material.ANVIL) {
				search = world.getBlockAt(new Location(world, x+1, y, z));
				if(search.getType() == Material.ANVIL) {
					rot = "h";
				}
				search = world.getBlockAt(new Location(world, x, y, z+1));
				if(search.getType() == Material.ANVIL) {
					rot = "v";
				}
			}
			
			// 방향성 체크
			if(rot.equals("h")) {
				if(current.x > next.x) {
					dir = "c>n";
				} else {
					dir = "c<n";
				}
			} else if(rot.equals("v")) {
				if(current.z > next.z) {
					dir = "c>n";
				} else {
					dir = "c<n";
				}
			}
			
			// 실제 구현
			if(rot.equals("h")) {
				if(dir.equals("c>n")) {
					// x축으로 이동, x가 감소하는 방향
					if(flag.getLocation().add(0,0,3).getBlock().getType() == Material.ANVIL) {
						trainArrive1(new Station("temp", next.x+10, next.y, next.z));
					} else {
						trainArrive1_1(new Station("temp", next.x+10, next.y, next.z));
					}
				} else {
					// x축으로 이동, x가 증가하는 방향
					if(flag.getLocation().add(0,0,3).getBlock().getType() == Material.ANVIL) {
						trainArrive2(new Station("temp", next.x-10, next.y, next.z));
					} else {
						trainArrive2_1(new Station("temp", next.x-10, next.y, next.z));
					}
				}
			} else if(rot.equals("v")) {
				if(dir.equals("c>n")) {
					// y축으로 이동, y가 감소하는 방향
					if(flag.getLocation().add(3,0,0).getBlock().getType() == Material.ANVIL) {
						trainArrive3(new Station("temp", next.x, next.y, next.z+10));
					} else {
						trainArrive3_1(new Station("temp", next.x, next.y, next.z+10));
					}
				} else {
					// y축으로 이동, y가 증가하는 방향
					if(flag.getLocation().add(3,0,0).getBlock().getType() == Material.ANVIL) {
						trainArrive4(new Station("temp", next.x, next.y, next.z-10));
					} else {
						trainArrive4_1(new Station("temp", next.x, next.y, next.z-10));
					}
				}
			}
			
		} else if(current != null && next == null) {
			// 처음역
			Station second = new StationManage().getList().get(1);
			
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y-1;
			int z = current.z;
			String rot = null;
			String dir = null;
			
			Block flag = world.getBlockAt(new Location(world, x, y, z));
			
			// 가로 세로 체크
			Block search = null;
			if(flag.getType() == Material.ANVIL) {
				search = world.getBlockAt(new Location(world, x+1, y, z));
				if(search.getType() == Material.ANVIL) {
					rot = "h";
				}
				search = world.getBlockAt(new Location(world, x, y, z+1));
				if(search.getType() == Material.ANVIL) {
					rot = "v";
				}
			}
			
			// 방향성 체크
			if(rot.equals("h")) {
				if(current.x > second.x) {
					dir = "c>n";
				} else {
					dir = "c<n";
				}
			} else if(rot.equals("v")) {
				if(current.z > second.z) {
					dir = "c>n";
				} else {
					dir = "c<n";
				}
			}

			// 실제 구현
			if(rot.equals("h")) {
				if(dir.equals("c>n")) {
					// x축으로 이동, x가 감소하는 방향
					if(flag.getLocation().add(0,0,3).getBlock().getType() == Material.ANVIL) {
						trainStartFirst1(current);
					} else {
						trainStartFirst1_1(current);
					}
				} else {
					// x축으로 이동, x가 증가하는 방향
					if(flag.getLocation().add(0,0,3).getBlock().getType() == Material.ANVIL) {
						trainStartFirst2(current);
					} else {
						trainStartFirst2_1(current);
					}
				}
			} else if(rot.equals("v")) {
				if(dir.equals("c>n")) {
					// y축으로 이동, y가 감소하는 방향
					if(flag.getLocation().add(3,0,0).getBlock().getType() == Material.ANVIL) {
						trainStartFirst3(current);
					} else {
						trainStartFirst3_1(current);
					}
				} else {
					// y축으로 이동, y가 증가하는 방향
					if(flag.getLocation().add(3,0,0).getBlock().getType() == Material.ANVIL) {
						trainStartFirst4(current);
					} else {
						trainStartFirst4_1(current);
					}
				}
			}
			
		}
	}
	
	public void trainArrive1(Station current) {
		new BukkitRunnable() {
			
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y;
			int z = current.z;
			Location loc = new Location(world, x, y, z);
			
			int timer = 0; 
			
			@Override
			public void run() {
				
				if(timer % 1 == 0) {
					removeBlock(loc);
					// 1층
					setBlock(loc.clone().add(-6, 0, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 0, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-1, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(4, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(-6, 0, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 0, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 0, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-1, 0, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(4, 0, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(-6, 0, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 0, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 2층
					setBlock(loc.clone().add(-6, 1, -1).add(-timer * 0.1, 0, 0), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, 4).add(-timer * 0.1, 0, 0), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					// 3층
					setBlock(loc.clone().add(-6, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(-6, 2, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 2, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 2, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 2, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 2, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 2, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 2, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					// 4층
					setBlock(loc.clone().add(-6, 3, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 3, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, -1).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 3, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 3, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 3, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 3, 0).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(5, 3, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 3, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(5, 3, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 3, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(5, 3, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 3, 3).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(5, 3, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 3, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 3, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, 4).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 3, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 3, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 3, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 5층
					setBlock(loc.clone().add(-6, 4, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 4, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, -1).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 4, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 4, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 4, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 4, 0).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(5, 4, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 4, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(5, 4, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 4, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(5, 4, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 4, 3).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(5, 4, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 4, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 4, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, 4).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 4, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 4, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 4, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 6층
					setBlock(loc.clone().add(-5, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(-5, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 5, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 5, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 5, 4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					// 7층
					setBlock(loc.clone().add(-5, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, 0).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 6, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, 2).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 6, 2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, 3).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 6, 3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
				}
				
				if(timer >= 100) {
					this.cancel();
				}
				
				timer++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	public void trainArrive1_1(Station current) {
		new BukkitRunnable() {
			
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y;
			int z = current.z;
			Location loc = new Location(world, x, y, z);
			
			int timer = 0; 
			
			@Override
			public void run() {
				
				if(timer % 1 == 0) {
					removeBlock(loc);
					// 1층
					setBlock(loc.clone().add(-6, 0, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 0, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-1, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(4, 0, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(-6, 0, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 0, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 0, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-1, 0, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(4, 0, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(-6, 0, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 0, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 2층
					setBlock(loc.clone().add(-6, 1, 1).add(-timer * 0.1, 0, 0), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 1, -4).add(-timer * 0.1, 0, 0), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 1, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					// 3층
					setBlock(loc.clone().add(-6, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 2, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(-6, 2, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 2, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 2, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 2, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 2, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 2, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 2, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 2, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					// 4층
					setBlock(loc.clone().add(-6, 3, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 3, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, 1).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 3, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 3, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 3, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 3, 0).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(5, 3, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 3, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(5, 3, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 3, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(5, 3, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 3, -3).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(5, 3, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 3, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 3, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, -4).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 3, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 3, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 3, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 5층
					setBlock(loc.clone().add(-6, 4, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 4, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, 1).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 4, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 4, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 4, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 4, 0).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(5, 4, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 4, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(5, 4, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 4, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(5, 4, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-6, 4, -3).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(5, 4, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-6, 4, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 4, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, -4).add(-timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 4, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 4, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 4, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 6층
					setBlock(loc.clone().add(-5, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 5, 1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(-5, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 5, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 5, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 5, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 5, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 5, -4).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					// 7층
					setBlock(loc.clone().add(-5, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, 0).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 6, 0).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, -1).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 6, -1).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, -2).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 6, -2).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, -3).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 6, -3).add(-timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-5, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -4).add(-timer * 0.1, 0, 0), Material.STEP, (byte) 0);
				}
				
				if(timer >= 100) {
					this.cancel();
				}
				
				timer++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	public void trainArrive2(Station current) {
		new BukkitRunnable() {
			
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y;
			int z = current.z;
			Location loc = new Location(world, x, y, z);
			
			int timer = 0; 
			
			@Override
			public void run() {
				
				if(timer % 1 == 0) {
					removeBlock(loc);
					// 1층
					setBlock(loc.clone().add(6, 0, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 0, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(1, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-4, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(6, 0, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 0, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 0, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(1, 0, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-4, 0, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(6, 0, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 0, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 2층
					setBlock(loc.clone().add(6, 1, -1).add(timer * 0.1, 0, 0), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(5, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, 4).add(timer * 0.1, 0, 0), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(5, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					// 3층
					setBlock(loc.clone().add(6, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(6, 2, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 2, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 2, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 2, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 2, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 2, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 2, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					// 4층
					setBlock(loc.clone().add(6, 3, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 3, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, -1).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 3, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 3, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 3, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 3, 0).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-5, 3, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 3, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-5, 3, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 3, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-5, 3, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 3, 3).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-5, 3, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 3, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 3, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, 4).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 3, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 3, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 3, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 5층
					setBlock(loc.clone().add(6, 4, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 4, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, -1).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 4, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 4, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 4, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 4, 0).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-5, 4, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 4, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-5, 4, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 4, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-5, 4, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 4, 3).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-5, 4, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 4, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 4, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, 4).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 4, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 4, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 4, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 6층
					setBlock(loc.clone().add(5, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(5, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 5, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 5, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 5, 4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					// 7층
					setBlock(loc.clone().add(5, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, 0).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, 2).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, 3).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
				}
				
				if(timer >= 100) {
					this.cancel();
				}
				
				timer++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	public void trainArrive2_1(Station current) {
		new BukkitRunnable() {
			
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y;
			int z = current.z;
			Location loc = new Location(world, x, y, z);
			
			int timer = 0; 
			
			@Override
			public void run() {

				if(timer % 1 == 0) {
					removeBlock(loc);
					// 1층
					setBlock(loc.clone().add(6, 0, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 0, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(1, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-4, 0, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(6, 0, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 0, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 0, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(1, 0, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-4, 0, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(6, 0, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 0, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 2층
					setBlock(loc.clone().add(6, 1, 1).add(timer * 0.1, 0, 0), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(5, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 1, -4).add(timer * 0.1, 0, 0), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(5, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 1, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					// 3층
					setBlock(loc.clone().add(6, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 2, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(6, 2, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 2, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 2, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 2, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 2, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 2, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 2, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(5, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 2, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					// 4층
					setBlock(loc.clone().add(6, 3, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 3, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, 1).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 3, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 3, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 3, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 3, 0).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-5, 3, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 3, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-5, 3, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 3, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-5, 3, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 3, -3).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-5, 3, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 3, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 3, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, -4).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 3, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 3, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 3, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 5층
					setBlock(loc.clone().add(6, 4, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 4, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, 1).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 4, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 4, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 4, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 4, 0).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-5, 4, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 4, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-5, 4, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 4, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-5, 4, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(6, 4, -3).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-5, 4, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(6, 4, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(5, 4, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, -4).add(timer * 0.1, 0, 0), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 4, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 4, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 4, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					// 6층
					setBlock(loc.clone().add(5, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 5, 1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(5, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 5, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 5, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 5, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-5, 5, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-5, 5, -4).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 4);
					
					// 7층
					setBlock(loc.clone().add(5, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, 0).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 0).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, -1).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -1).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, -2).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -2).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, -3).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -3).add(timer * 0.1, 0, 0), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(5, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -4).add(timer * 0.1, 0, 0), Material.STEP, (byte) 0);
				}
				
				if(timer >= 100) {
					this.cancel();
				}
				
				timer++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	public void trainArrive3(Station current) {
		new BukkitRunnable() {
			
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y;
			int z = current.z;
			Location loc = new Location(world, x, y, z);
			
			int timer = 0; 
			
			@Override
			public void run() {
				
				if(timer % 1 == 0) {
					removeBlock(loc);
					// 1층
					setBlock(loc.clone().add(-1, 0, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 0, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(0, 0, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 0, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 0, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(3, 0, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 0, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 0, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(4, 0, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 0, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 2층
					setBlock(loc.clone().add(-1, 1, -6).add(0, 0, -timer * 0.1), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(0, 1, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(1, 1, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(2, 1, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(3, 1, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(4, 1, -6).add(0, 0, -timer * 0.1), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					// 3층
					setBlock(loc.clone().add(-1, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(0, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(4, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					
					// 4층
					setBlock(loc.clone().add(-1, 3, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, -4).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-1, 3, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(0, 3, -6).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(0, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 3, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(1, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 3, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(2, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 3, -6).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(4, 3, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, -4).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(4, 3, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 5층
					setBlock(loc.clone().add(-1, 4, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, -4).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-1, 4, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(0, 4, -6).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(0, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 4, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(1, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 4, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(2, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 4, -6).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(4, 4, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, -4).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(4, 4, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 6층
					setBlock(loc.clone().add(-1, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(0, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(4, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					
					// 7층
					setBlock(loc.clone().add(-1, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -4).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -3).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -2).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -1).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 0).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 1).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 2).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 3).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 4).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					
					setBlock(loc.clone().add(0, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(4, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -4).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -3).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -2).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -1).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 0).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 1).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 2).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 3).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 4).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
				}
				
				if(timer >= 100) {
					this.cancel();
				}
				
				timer++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	public void trainArrive3_1(Station current) {
		new BukkitRunnable() {
			
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y;
			int z = current.z;
			Location loc = new Location(world, x, y, z);
			
			int timer = 0; 
			
			@Override
			public void run() {
				
				if(timer % 1 == 0) {
					removeBlock(loc);
					// 1층
					setBlock(loc.clone().add(1, 0, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 0, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(0, 0, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 0, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 0, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(-3, 0, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 0, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 0, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(-4, 0, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 0, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 2층
					setBlock(loc.clone().add(1, 1, -6).add(0, 0, -timer * 0.1), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(0, 1, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-1, 1, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-2, 1, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-3, 1, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-4, 1, -6).add(0, 0, -timer * 0.1), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					// 3층
					setBlock(loc.clone().add(1, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(0, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-4, 2, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					
					// 4층
					setBlock(loc.clone().add(1, 3, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, -4).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(1, 3, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(0, 3, -6).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(0, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 3, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-1, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 3, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-2, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 3, -6).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-4, 3, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, -4).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-4, 3, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 5층
					setBlock(loc.clone().add(1, 4, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, -4).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(1, 4, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(0, 4, -6).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(0, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 4, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-1, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 4, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-2, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 4, -6).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-4, 4, -6).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, -4).add(0, 0, -timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-4, 4, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 6층
					setBlock(loc.clone().add(1, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(0, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-4, 5, -5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 5).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 4);
					
					// 7층
					setBlock(loc.clone().add(1, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -4).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -3).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -2).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -1).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 0).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 1).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 2).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 3).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 4).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					
					setBlock(loc.clone().add(0, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 0).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 1).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 2).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 3).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 4).add(0, 0, -timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-4, 6, -5).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -4).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -3).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -2).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -1).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 0).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 1).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 2).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 3).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 4).add(0, 0, -timer * 0.1), Material.STEP, (byte) 0);
				}
				
				if(timer >= 100) {
					this.cancel();
				}
				
				timer++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	public void trainArrive4(Station current) {
		new BukkitRunnable() {
			
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y;
			int z = current.z;
			Location loc = new Location(world, x, y, z);
			
			int timer = 0; 
			
			@Override
			public void run() {
				
				if(timer % 1 == 0) {
					removeBlock(loc);
					// 1층
					setBlock(loc.clone().add(-1, 0, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 0, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(0, 0, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 0, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 0, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(3, 0, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 0, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 0, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(3, 0, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(4, 0, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 0, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 2층
					setBlock(loc.clone().add(-1, 1, 6).add(0, 0, timer * 0.1), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(0, 1, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(1, 1, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(2, 1, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(2, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(3, 1, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(3, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(4, 1, 6).add(0, 0, timer * 0.1), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					// 3층
					setBlock(loc.clone().add(-1, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(0, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(4, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					
					// 4층
					setBlock(loc.clone().add(-1, 3, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, 4).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-1, 3, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(0, 3, 6).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(0, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 3, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(1, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 3, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(2, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 3, 6).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(4, 3, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, 4).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(4, 3, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 5층
					setBlock(loc.clone().add(-1, 4, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, 4).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-1, 4, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(0, 4, 6).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(0, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 4, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(1, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 4, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(2, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 4, 6).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(3, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(4, 4, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, 4).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(4, 4, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(4, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 6층
					setBlock(loc.clone().add(-1, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(0, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(4, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(4, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					
					// 7층
					setBlock(loc.clone().add(-1, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 4).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 3).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 2).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 1).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 0).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -1).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -2).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -3).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -4).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					
					setBlock(loc.clone().add(0, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(1, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 6, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(2, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(2, 6, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(2, 6, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(3, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(3, 6, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(3, 6, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(4, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 4).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 3).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 2).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 1).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, 0).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -1).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -2).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -3).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(4, 6, -4).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
				}
				
				if(timer >= 100) {
					this.cancel();
				}
				
				timer++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	public void trainArrive4_1(Station current) {
		new BukkitRunnable() {
			
			World world = Bukkit.getWorld("world");
			int x = current.x;
			int y = current.y;
			int z = current.z;
			Location loc = new Location(world, x, y, z);
			
			int timer = 0; 
			
			@Override
			public void run() {
				
				if(timer % 1 == 0) {
					removeBlock(loc);
					// 1층
					setBlock(loc.clone().add(1, 0, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 0, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(0, 0, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 0, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 0, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(0, 0, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(-3, 0, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 0, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 0, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-3, 0, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);

					setBlock(loc.clone().add(-4, 0, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 0, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 2층
					setBlock(loc.clone().add(1, 1, 6).add(0, 0, timer * 0.1), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(0, 1, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(0, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-1, 1, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-1, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-2, 1, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-2, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-3, 1, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-3, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-4, 1, 6).add(0, 0, timer * 0.1), Material.GLOWSTONE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 1, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					// 3층
					setBlock(loc.clone().add(1, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(0, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-4, 2, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 2, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					
					// 4층
					setBlock(loc.clone().add(1, 3, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, 4).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(1, 3, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(0, 3, 6).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(0, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 3, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-1, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 3, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-2, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 3, 6).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-4, 3, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, 4).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-4, 3, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 3, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 5층
					setBlock(loc.clone().add(1, 4, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, 4).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(1, 4, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(1, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(0, 4, 6).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(0, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 4, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-1, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 4, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 15);
					setBlock(loc.clone().add(-2, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 4, 6).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-3, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);

					setBlock(loc.clone().add(-4, 4, 6).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, 4).add(0, 0, timer * 0.1), Material.STAINED_GLASS, (byte) 15);
					setBlock(loc.clone().add(-4, 4, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-4, 4, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					// 6층
					setBlock(loc.clone().add(1, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(1, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					
					setBlock(loc.clone().add(0, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-4, 5, 5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					setBlock(loc.clone().add(-4, 5, -5).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 4);
					
					// 7층
					setBlock(loc.clone().add(1, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 4).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 3).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 2).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 1).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, 0).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -1).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -2).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -3).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(1, 6, -4).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					
					setBlock(loc.clone().add(0, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(0, 6, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(0, 6, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-1, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-1, 6, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-2, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-2, 6, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-3, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, 0).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -1).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -2).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -3).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					setBlock(loc.clone().add(-3, 6, -4).add(0, 0, timer * 0.1), Material.CONCRETE, (byte) 0);
					
					setBlock(loc.clone().add(-4, 6, 5).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 4).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 3).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 2).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 1).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, 0).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -1).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -2).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -3).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
					setBlock(loc.clone().add(-4, 6, -4).add(0, 0, timer * 0.1), Material.STEP, (byte) 0);
				}
				
				if(timer >= 100) {
					this.cancel();
				}
				
				timer++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
	}
	
	public void setBlock(Location loc, Material mat, byte b) {
		FallingBlock fb;
		fb = (FallingBlock) loc.getWorld().spawnFallingBlock(loc.clone().add(0.5,0,0.5), mat, b);
		fb.setGravity(false);
		fb.setVelocity(new Vector(0, 0, 0));
		fb.setTicksLived(Integer.MAX_VALUE);
		fb.setDropItem(false);
	}

	public void removeBlock(Location loc) {
		
		ArmorStand ar;
		ar = (ArmorStand) loc.getWorld().spawnEntity(loc.clone().add(0.5,15,0.5), EntityType.ARMOR_STAND);
		ar.setVisible(false);
		for(Entity ent : ar.getNearbyEntities(50, 25, 50)) {
			if(ent instanceof FallingBlock) {
				ent.remove();
			}
		}
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				ar.remove();
				this.cancel();
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
		
	}
	
	public void trainStartFirst1(Station current) {
		World world = Bukkit.getWorld("world");
		int x = current.x;
		int y = current.y;
		int z = current.z;
		Location loc = new Location(world, x, y, z);
		
		// 1층
		setBlock(loc.clone().add(-6, 0, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 0, -1), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-6, 0, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 0, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 0, 0), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-1, 0, 0), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(3, 0, 0), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(4, 0, 0), Material.CONCRETE, (byte) 15);

		setBlock(loc.clone().add(-6, 0, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 0, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 0, 3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-1, 0, 3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(3, 0, 3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(4, 0, 3), Material.CONCRETE, (byte) 15);

		setBlock(loc.clone().add(-6, 0, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 0, 4), Material.CONCRETE, (byte) 0);
		
		// 2층
		setBlock(loc.clone().add(-6, 1, -1), Material.GLOWSTONE, (byte) 0);
		setBlock(loc.clone().add(-5, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 1, -1), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-6, 1, 0), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 1, 0), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-6, 1, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 1, 1), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-6, 1, 2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 1, 2), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-6, 1, 3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 1, 3), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-6, 1, 4), Material.GLOWSTONE, (byte) 0);
		setBlock(loc.clone().add(-5, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 1, 4), Material.CONCRETE, (byte) 0);

		// 3층
		setBlock(loc.clone().add(-6, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-3, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-2, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(2, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(3, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 2, -1), Material.CONCRETE, (byte) 4);
		
		setBlock(loc.clone().add(-6, 2, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 2, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 2, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 2, 1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 2, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 2, 2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 2, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 2, 3), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-6, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-3, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-2, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(2, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(3, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 2, 4), Material.CONCRETE, (byte) 4);
		
		// 4층
		setBlock(loc.clone().add(-6, 3, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 3, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 3, -1), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-3, 3, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 3, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 3, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 3, -1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 3, 0), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(5, 3, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 3, 1), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(5, 3, 1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 3, 2), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(5, 3, 2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 3, 3), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(5, 3, 3), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-6, 3, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 3, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 3, 4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-3, 3, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 3, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 3, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 3, 4), Material.CONCRETE, (byte) 0);
		
		// 5층
		setBlock(loc.clone().add(-6, 4, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 4, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 4, -1), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-3, 4, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 4, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 4, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 4, -1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 4, 0), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(5, 4, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 4, 1), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(5, 4, 1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 4, 2), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(5, 4, 2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 4, 3), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(5, 4, 3), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-6, 4, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 4, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 4, 4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-3, 4, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 4, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 4, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 4, 4), Material.CONCRETE, (byte) 0);
		
		// 6층
		setBlock(loc.clone().add(-5, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-3, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-2, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(0, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(2, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(3, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 5, -1), Material.CONCRETE, (byte) 4);
		
		setBlock(loc.clone().add(-5, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 5, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-5, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 5, 1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-5, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 5, 2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-5, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 5, 3), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-5, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-3, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-2, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(0, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(2, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(3, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 5, 4), Material.CONCRETE, (byte) 4);
		
		// 7층
		setBlock(loc.clone().add(-5, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-3, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-2, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(0, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(2, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(3, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, -1), Material.STEP, (byte) 0);
		
		setBlock(loc.clone().add(-5, 6, 0), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 6, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-5, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 6, 1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-5, 6, 2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 6, 2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-5, 6, 3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 6, 3), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-5, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(0, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(2, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(3, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 4), Material.STEP, (byte) 0);
	}
	
	public void trainStartFirst1_1(Station current) {
		World world = Bukkit.getWorld("world");
		int x = current.x;
		int y = current.y;
		int z = current.z;
		Location loc = new Location(world, x, y, z);
		
		// 1층
		setBlock(loc.clone().add(-6, 0, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 0, 1), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-6, 0, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 0, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 0, 0), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-1, 0, 0), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(3, 0, 0), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(4, 0, 0), Material.CONCRETE, (byte) 15);

		setBlock(loc.clone().add(-6, 0, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 0, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 0, -3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-1, 0, -3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(3, 0, -3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(4, 0, -3), Material.CONCRETE, (byte) 15);

		setBlock(loc.clone().add(-6, 0, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 0, -4), Material.CONCRETE, (byte) 0);
		
		// 2층
		setBlock(loc.clone().add(-6, 1, 1), Material.GLOWSTONE, (byte) 0);
		setBlock(loc.clone().add(-5, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 1, 1), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-6, 1, 0), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 1, 0), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-6, 1, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 1, -1), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-6, 1, -2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 1, -2), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-6, 1, -3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 1, -3), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-6, 1, -4), Material.GLOWSTONE, (byte) 0);
		setBlock(loc.clone().add(-5, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 1, -4), Material.CONCRETE, (byte) 0);

		// 3층
		setBlock(loc.clone().add(-6, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-3, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-2, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(2, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(3, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 2, 1), Material.CONCRETE, (byte) 4);
		
		setBlock(loc.clone().add(-6, 2, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 2, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 2, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 2, -1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 2, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 2, -2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 2, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 2, -3), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-6, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-3, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-2, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(2, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(3, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 2, -4), Material.CONCRETE, (byte) 4);
		
		// 4층
		setBlock(loc.clone().add(-6, 3, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 3, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 3, 1), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-3, 3, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 3, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 3, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 3, 1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 3, 0), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(5, 3, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 3, -1), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(5, 3, -1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 3, -2), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(5, 3, -2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 3, -3), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(5, 3, -3), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-6, 3, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 3, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 3, -4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-3, 3, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 3, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 3, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 3, -4), Material.CONCRETE, (byte) 0);
		
		// 5층
		setBlock(loc.clone().add(-6, 4, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 4, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 4, 1), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-3, 4, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 4, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 4, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 4, 1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 4, 0), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(5, 4, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 4, -1), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(5, 4, -1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 4, -2), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(5, 4, -2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-6, 4, -3), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(5, 4, -3), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-6, 4, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 4, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 4, -4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-3, 4, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 4, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 4, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 4, -4), Material.CONCRETE, (byte) 0);
		
		// 6층
		setBlock(loc.clone().add(-5, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-3, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-2, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(0, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(2, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(3, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 5, 1), Material.CONCRETE, (byte) 4);
		
		setBlock(loc.clone().add(-5, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 5, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-5, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 5, -1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-5, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 5, -2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-5, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 5, -3), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-5, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-3, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-2, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(0, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(2, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(3, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 5, -4), Material.CONCRETE, (byte) 4);
		
		// 7층
		setBlock(loc.clone().add(-5, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(0, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(2, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(3, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 1), Material.STEP, (byte) 0);
		
		setBlock(loc.clone().add(-5, 6, 0), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 6, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-5, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 6, -1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-5, 6, -2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 6, -2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-5, 6, -3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 6, -3), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-5, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-3, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-2, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(0, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(2, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(3, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, -4), Material.STEP, (byte) 0);
	}
	
	public void trainStartFirst2(Station current) {
		World world = Bukkit.getWorld("world");
		int x = current.x;
		int y = current.y;
		int z = current.z;
		Location loc = new Location(world, x, y, z);
		
		// 1층
		setBlock(loc.clone().add(6, 0, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 0, -1), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(6, 0, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 0, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 0, 0), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(1, 0, 0), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-3, 0, 0), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-4, 0, 0), Material.CONCRETE, (byte) 15);

		setBlock(loc.clone().add(6, 0, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 0, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 0, 3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(1, 0, 3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-3, 0, 3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-4, 0, 3), Material.CONCRETE, (byte) 15);

		setBlock(loc.clone().add(6, 0, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 0, 4), Material.CONCRETE, (byte) 0);
		
		// 2층
		setBlock(loc.clone().add(6, 1, -1), Material.GLOWSTONE, (byte) 0);
		setBlock(loc.clone().add(5, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 1, -1), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(6, 1, 0), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 1, 0), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(6, 1, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 1, 1), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(6, 1, 2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 1, 2), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(6, 1, 3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 1, 3), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(6, 1, 4), Material.GLOWSTONE, (byte) 0);
		setBlock(loc.clone().add(5, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 1, 4), Material.CONCRETE, (byte) 0);

		// 3층
		setBlock(loc.clone().add(6, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(3, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(2, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-2, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-3, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 2, -1), Material.CONCRETE, (byte) 4);
		
		setBlock(loc.clone().add(6, 2, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 2, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 2, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 2, 1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 2, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 2, 2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 2, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 2, 3), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(6, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(3, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(2, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-2, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-3, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 2, 4), Material.CONCRETE, (byte) 4);
		
		// 4층
		setBlock(loc.clone().add(6, 3, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 3, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 3, -1), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(3, 3, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 3, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 3, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 3, -1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 3, 0), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-5, 3, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 3, 1), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-5, 3, 1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 3, 2), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-5, 3, 2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 3, 3), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-5, 3, 3), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(6, 3, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 3, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 3, 4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(3, 3, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 3, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 3, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 3, 4), Material.CONCRETE, (byte) 0);
		
		// 5층
		setBlock(loc.clone().add(6, 4, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 4, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 4, -1), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(3, 4, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 4, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 4, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 4, -1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 4, 0), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-5, 4, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 4, 1), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-5, 4, 1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 4, 2), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-5, 4, 2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 4, 3), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-5, 4, 3), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(6, 4, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 4, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 4, 4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(3, 4, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 4, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 4, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 4, 4), Material.CONCRETE, (byte) 0);
		
		// 6층
		setBlock(loc.clone().add(5, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(3, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(2, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(0, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-2, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-3, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 5, -1), Material.CONCRETE, (byte) 4);
		
		setBlock(loc.clone().add(5, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 5, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(5, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 5, 1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(5, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 5, 2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(5, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 5, 3), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(5, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(3, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(2, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(0, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-2, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-3, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 5, 4), Material.CONCRETE, (byte) 4);
		
		// 7층
		setBlock(loc.clone().add(5, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(3, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(2, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(0, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-2, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-3, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, -1), Material.STEP, (byte) 0);
		
		setBlock(loc.clone().add(5, 6, 0), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(5, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(5, 6, 2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(5, 6, 3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 3), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(5, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(3, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(2, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(0, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 4), Material.STEP, (byte) 0);
	}
	
	public void trainStartFirst2_1(Station current) {
		World world = Bukkit.getWorld("world");
		int x = current.x;
		int y = current.y;
		int z = current.z;
		Location loc = new Location(world, x, y, z);
		
		// 1층
		setBlock(loc.clone().add(6, 0, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 0, 1), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(6, 0, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 0, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 0, 0), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(1, 0, 0), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-3, 0, 0), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-4, 0, 0), Material.CONCRETE, (byte) 15);

		setBlock(loc.clone().add(6, 0, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 0, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 0, -3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(1, 0, -3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-3, 0, -3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-4, 0, -3), Material.CONCRETE, (byte) 15);

		setBlock(loc.clone().add(6, 0, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 0, -4), Material.CONCRETE, (byte) 0);
		
		// 2층
		setBlock(loc.clone().add(6, 1, 1), Material.GLOWSTONE, (byte) 0);
		setBlock(loc.clone().add(5, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 1, 1), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(6, 1, 0), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 1, 0), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(6, 1, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 1, -1), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(6, 1, -2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 1, -2), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(6, 1, -3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 1, -3), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(6, 1, -4), Material.GLOWSTONE, (byte) 0);
		setBlock(loc.clone().add(5, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 1, -4), Material.CONCRETE, (byte) 0);

		// 3층
		setBlock(loc.clone().add(6, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(3, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(2, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-2, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-3, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 2, 1), Material.CONCRETE, (byte) 4);
		
		setBlock(loc.clone().add(6, 2, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 2, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 2, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 2, -1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 2, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 2, -2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 2, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 2, -3), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(6, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(5, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(3, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(2, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-2, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-3, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 2, -4), Material.CONCRETE, (byte) 4);
		
		// 4층
		setBlock(loc.clone().add(6, 3, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 3, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 3, 1), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(3, 3, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 3, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 3, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 3, 1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 3, 0), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-5, 3, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 3, -1), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-5, 3, -1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 3, -2), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-5, 3, -2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 3, -3), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-5, 3, -3), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(6, 3, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 3, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 3, -4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(3, 3, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 3, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 3, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 3, -4), Material.CONCRETE, (byte) 0);
		
		// 5층
		setBlock(loc.clone().add(6, 4, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 4, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 4, 1), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(3, 4, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 4, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 4, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 4, 1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 4, 0), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-5, 4, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 4, -1), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-5, 4, -1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 4, -2), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-5, 4, -2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(6, 4, -3), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-5, 4, -3), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(6, 4, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(5, 4, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 4, -4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(3, 4, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 4, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 4, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 4, -4), Material.CONCRETE, (byte) 0);
		
		// 6층
		setBlock(loc.clone().add(5, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(3, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(2, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(0, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-2, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-3, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 5, 1), Material.CONCRETE, (byte) 4);
		
		setBlock(loc.clone().add(5, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 5, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(5, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 5, -1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(5, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 5, -2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(5, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-5, 5, -3), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(5, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(3, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(2, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(0, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-2, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-3, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-5, 5, -4), Material.CONCRETE, (byte) 4);
		
		// 7층
		setBlock(loc.clone().add(5, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(3, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(2, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(0, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 1), Material.STEP, (byte) 0);
		
		setBlock(loc.clone().add(5, 6, 0), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 0), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(5, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 6, -1), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(5, 6, -2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 6, -2), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(5, 6, -3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 6, -3), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(5, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(3, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(2, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(0, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-2, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-3, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, -4), Material.STEP, (byte) 0);
	}
	
	public void trainStartFirst3(Station current) {
		World world = Bukkit.getWorld("world");
		int x = current.x;
		int y = current.y;
		int z = current.z;
		Location loc = new Location(world, x, y, z);
		
		// 1층
		setBlock(loc.clone().add(-1, 0, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 0, -5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(0, 0, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 0, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 0, -2), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(0, 0, -1), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(0, 0, 3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(0, 0, 4), Material.CONCRETE, (byte) 15);

		setBlock(loc.clone().add(3, 0, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 0, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 0, -2), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(3, 0, -1), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(3, 0, 3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(3, 0, 4), Material.CONCRETE, (byte) 15);

		setBlock(loc.clone().add(4, 0, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 0, -5), Material.CONCRETE, (byte) 0);
		
		// 2층
		setBlock(loc.clone().add(-1, 1, -6), Material.GLOWSTONE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(0, 1, -6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(0, 1, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(1, 1, -6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 1, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(2, 1, -6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(2, 1, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(3, 1, -6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(3, 1, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(4, 1, -6), Material.GLOWSTONE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 5), Material.CONCRETE, (byte) 0);

		// 3층
		setBlock(loc.clone().add(-1, 2, -6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, -5), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, -3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, -2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, 2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, 3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, 5), Material.CONCRETE, (byte) 4);
		
		setBlock(loc.clone().add(0, 2, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 2, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(1, 2, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 2, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(2, 2, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 2, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(3, 2, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 2, 5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(4, 2, -6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, -5), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, -3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, -2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, 2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, 3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, 5), Material.CONCRETE, (byte) 4);
		
		// 4층
		setBlock(loc.clone().add(-1, 3, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 3, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 3, -4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-1, 3, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 3, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 3, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 3, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(0, 3, -6), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(0, 3, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(1, 3, -6), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(1, 3, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(2, 3, -6), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(2, 3, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(3, 3, -6), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(3, 3, 5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(4, 3, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 3, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 3, -4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(4, 3, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 3, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 3, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 3, 5), Material.CONCRETE, (byte) 0);
		
		// 5층
		setBlock(loc.clone().add(-1, 4, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 4, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 4, -4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-1, 4, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 4, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 4, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 4, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(0, 4, -6), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(0, 4, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(1, 4, -6), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(1, 4, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(2, 4, -6), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(2, 4, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(3, 4, -6), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(3, 4, 5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(4, 4, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 4, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 4, -4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(4, 4, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 4, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 4, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 4, 5), Material.CONCRETE, (byte) 0);
		
		// 6층
		setBlock(loc.clone().add(-1, 5, -5), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, -3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, -2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, 0), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, 2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, 3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, 5), Material.CONCRETE, (byte) 4);
		
		setBlock(loc.clone().add(0, 5, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(1, 5, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(2, 5, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(3, 5, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(4, 5, -5), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, -3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, -2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, 0), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, 2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, 3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, 5), Material.CONCRETE, (byte) 4);
		
		// 7층
		setBlock(loc.clone().add(-1, 6, -5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 0), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 4), Material.STEP, (byte) 0);
		
		setBlock(loc.clone().add(0, 6, -5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(0, 6, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 4), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(1, 6, -5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 4), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(2, 6, -5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(2, 6, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 4), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(3, 6, -5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(3, 6, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 4), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(4, 6, -5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, -3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, -2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 0), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 4), Material.STEP, (byte) 0);
	}
	
	public void trainStartFirst3_1(Station current) {
		World world = Bukkit.getWorld("world");
		int x = current.x;
		int y = current.y;
		int z = current.z;
		Location loc = new Location(world, x, y, z);
		
		// 1층
		setBlock(loc.clone().add(1, 0, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 0, -5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(0, 0, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 0, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 0, -2), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(0, 0, -1), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(0, 0, 3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(0, 0, 4), Material.CONCRETE, (byte) 15);

		setBlock(loc.clone().add(-3, 0, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 0, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 0, -2), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-3, 0, -1), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-3, 0, 3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-3, 0, 4), Material.CONCRETE, (byte) 15);

		setBlock(loc.clone().add(-4, 0, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 0, -5), Material.CONCRETE, (byte) 0);
		
		// 2층
		setBlock(loc.clone().add(1, 1, -6), Material.GLOWSTONE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(0, 1, -6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(0, 1, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-1, 1, -6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 1, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-2, 1, -6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-2, 1, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-3, 1, -6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-3, 1, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-4, 1, -6), Material.GLOWSTONE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 5), Material.CONCRETE, (byte) 0);

		// 3층
		setBlock(loc.clone().add(1, 2, -6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, -5), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, -3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, -2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, 2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, 3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, 5), Material.CONCRETE, (byte) 4);
		
		setBlock(loc.clone().add(0, 2, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 2, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-1, 2, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 2, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-2, 2, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 2, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-3, 2, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 2, 5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-4, 2, -6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, -5), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, -3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, -2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, 2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, 3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, 5), Material.CONCRETE, (byte) 4);
		
		// 4층
		setBlock(loc.clone().add(1, 3, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 3, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 3, -4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(1, 3, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 3, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 3, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 3, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(0, 3, -6), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(0, 3, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-1, 3, -6), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-1, 3, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-2, 3, -6), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-2, 3, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-3, 3, -6), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-3, 3, 5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-4, 3, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 3, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 3, -4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-4, 3, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 3, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 3, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 3, 5), Material.CONCRETE, (byte) 0);
		
		// 5층
		setBlock(loc.clone().add(1, 4, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 4, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 4, -4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(1, 4, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 4, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 4, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 4, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(0, 4, -6), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(0, 4, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-1, 4, -6), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-1, 4, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-2, 4, -6), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-2, 4, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-3, 4, -6), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-3, 4, 5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-4, 4, -6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 4, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 4, -4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-4, 4, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 4, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 4, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 4, 5), Material.CONCRETE, (byte) 0);
		
		// 6층
		setBlock(loc.clone().add(1, 5, -5), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, -3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, -2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, 0), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, 2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, 3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, 5), Material.CONCRETE, (byte) 4);
		
		setBlock(loc.clone().add(0, 5, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-1, 5, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-2, 5, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-3, 5, -5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-4, 5, -5), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, -3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, -2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, 0), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, 2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, 3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, 5), Material.CONCRETE, (byte) 4);
		
		// 7층
		setBlock(loc.clone().add(1, 6, -5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, -3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, -2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, 0), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, 2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, 3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, 4), Material.STEP, (byte) 0);
		
		setBlock(loc.clone().add(0, 6, -5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(0, 6, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 4), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-1, 6, -5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 4), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-2, 6, -5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-2, 6, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 4), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-3, 6, -5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-3, 6, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 4), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-4, 6, -5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, -4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, -3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, -2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 0), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 4), Material.STEP, (byte) 0);
	}
	
	public void trainStartFirst4(Station current) {
		World world = Bukkit.getWorld("world");
		int x = current.x;
		int y = current.y;
		int z = current.z;
		Location loc = new Location(world, x, y, z);
		
		// 1층
		setBlock(loc.clone().add(-1, 0, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 0, 5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(0, 0, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 0, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 0, 2), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(0, 0, 1), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(0, 0, -3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(0, 0, -4), Material.CONCRETE, (byte) 15);

		setBlock(loc.clone().add(3, 0, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 0, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 0, 2), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(3, 0, 1), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(3, 0, -3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(3, 0, -4), Material.CONCRETE, (byte) 15);

		setBlock(loc.clone().add(4, 0, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 0, 5), Material.CONCRETE, (byte) 0);
		
		// 2층
		setBlock(loc.clone().add(-1, 1, 6), Material.GLOWSTONE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(0, 1, 6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(0, 1, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(1, 1, 6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 1, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(2, 1, 6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(2, 1, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 1, -5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(3, 1, 6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(3, 1, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 1, -5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(4, 1, 6), Material.GLOWSTONE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 1, -5), Material.CONCRETE, (byte) 0);

		// 3층
		setBlock(loc.clone().add(-1, 2, 6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, 5), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, 3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, 2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, -2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, -3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 2, -5), Material.CONCRETE, (byte) 4);
		
		setBlock(loc.clone().add(0, 2, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 2, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(1, 2, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 2, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(2, 2, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 2, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(3, 2, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 2, -5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(4, 2, 6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, 5), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, 3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, 2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, -2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, -3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 2, -5), Material.CONCRETE, (byte) 4);
		
		// 4층
		setBlock(loc.clone().add(-1, 3, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 3, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 3, 4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-1, 3, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 3, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 3, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 3, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(0, 3, 6), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(0, 3, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(1, 3, 6), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(1, 3, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(2, 3, 6), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(2, 3, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(3, 3, 6), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(3, 3, -5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(4, 3, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 3, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 3, 4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(4, 3, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 3, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 3, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 3, -5), Material.CONCRETE, (byte) 0);
		
		// 5층
		setBlock(loc.clone().add(-1, 4, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 4, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 4, 4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-1, 4, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 4, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 4, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 4, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(0, 4, 6), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(0, 4, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(1, 4, 6), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(1, 4, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(2, 4, 6), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(2, 4, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(3, 4, 6), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(3, 4, -5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(4, 4, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 4, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 4, 4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(4, 4, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 4, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 4, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(4, 4, -5), Material.CONCRETE, (byte) 0);
		
		// 6층
		setBlock(loc.clone().add(-1, 5, 5), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, 3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, 2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, 0), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, -2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, -3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 5, -5), Material.CONCRETE, (byte) 4);
		
		setBlock(loc.clone().add(0, 5, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(1, 5, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 5, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(2, 5, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 5, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(3, 5, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 5, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(4, 5, 5), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, 3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, 2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, 0), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, -2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, -3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(4, 5, -5), Material.CONCRETE, (byte) 4);
		
		// 7층
		setBlock(loc.clone().add(-1, 6, 5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 0), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -4), Material.STEP, (byte) 0);
		
		setBlock(loc.clone().add(0, 6, 5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(0, 6, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -4), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(1, 6, 5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 6, -4), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(2, 6, 5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(2, 6, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(2, 6, -4), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(3, 6, 5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(3, 6, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(3, 6, -4), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(4, 6, 5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, 0), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, -2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, -3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(4, 6, -4), Material.STEP, (byte) 0);
	}
	
	public void trainStartFirst4_1(Station current) {
		World world = Bukkit.getWorld("world");
		int x = current.x;
		int y = current.y;
		int z = current.z;
		Location loc = new Location(world, x, y, z);
		
		// 1층
		setBlock(loc.clone().add(1, 0, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 0, 5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(0, 0, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 0, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 0, 2), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(0, 0, 1), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(0, 0, -3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(0, 0, -4), Material.CONCRETE, (byte) 15);

		setBlock(loc.clone().add(-3, 0, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 0, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 0, 2), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-3, 0, 1), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-3, 0, -3), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-3, 0, -4), Material.CONCRETE, (byte) 15);

		setBlock(loc.clone().add(-4, 0, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 0, 5), Material.CONCRETE, (byte) 0);
		
		// 2층
		setBlock(loc.clone().add(1, 1, 6), Material.GLOWSTONE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 1, -5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(0, 1, 6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(0, 1, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 1, -5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-1, 1, 6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-1, 1, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 1, -5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-2, 1, 6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-2, 1, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 1, -5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-3, 1, 6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-3, 1, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 1, -5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-4, 1, 6), Material.GLOWSTONE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 1, -5), Material.CONCRETE, (byte) 0);

		// 3층
		setBlock(loc.clone().add(1, 2, 6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, 5), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, 3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, 2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, -2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, -3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 2, -5), Material.CONCRETE, (byte) 4);
		
		setBlock(loc.clone().add(0, 2, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 2, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-1, 2, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 2, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-2, 2, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 2, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-3, 2, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 2, -5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-4, 2, 6), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, 5), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, 3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, 2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, -2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, -3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 2, -5), Material.CONCRETE, (byte) 4);
		
		// 4층
		setBlock(loc.clone().add(1, 3, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 3, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 3, 4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(1, 3, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 3, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 3, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 3, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(0, 3, 6), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(0, 3, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-1, 3, 6), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-1, 3, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-2, 3, 6), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-2, 3, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-3, 3, 6), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-3, 3, -5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-4, 3, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 3, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 3, 4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-4, 3, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 3, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 3, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 3, -5), Material.CONCRETE, (byte) 0);
		
		// 5층
		setBlock(loc.clone().add(1, 4, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 4, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 4, 4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(1, 4, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 4, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 4, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(1, 4, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(0, 4, 6), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(0, 4, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-1, 4, 6), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-1, 4, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-2, 4, 6), Material.CONCRETE, (byte) 15);
		setBlock(loc.clone().add(-2, 4, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-3, 4, 6), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-3, 4, -5), Material.CONCRETE, (byte) 0);

		setBlock(loc.clone().add(-4, 4, 6), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 4, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 4, 4), Material.STAINED_GLASS, (byte) 15);
		setBlock(loc.clone().add(-4, 4, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 4, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 4, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-4, 4, -5), Material.CONCRETE, (byte) 0);
		
		// 6층
		setBlock(loc.clone().add(1, 5, 5), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, 3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, 2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, 0), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, -2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, -3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(1, 5, -5), Material.CONCRETE, (byte) 4);
		
		setBlock(loc.clone().add(0, 5, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 5, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-1, 5, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 5, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-2, 5, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 5, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-3, 5, 5), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, -4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 5, -5), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-4, 5, 5), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, 4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, 3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, 2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, 1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, 0), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, -1), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, -2), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, -3), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, -4), Material.CONCRETE, (byte) 4);
		setBlock(loc.clone().add(-4, 5, -5), Material.CONCRETE, (byte) 4);
		
		// 7층
		setBlock(loc.clone().add(1, 6, 5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, 3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, 2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, 0), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, -2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, -3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(1, 6, -4), Material.STEP, (byte) 0);
		
		setBlock(loc.clone().add(0, 6, 5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(0, 6, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(0, 6, -4), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-1, 6, 5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-1, 6, -4), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-2, 6, 5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-2, 6, -4), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-3, 6, 5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 4), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, 0), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, -1), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, -2), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, -3), Material.CONCRETE, (byte) 0);
		setBlock(loc.clone().add(-3, 6, -4), Material.CONCRETE, (byte) 0);
		
		setBlock(loc.clone().add(-4, 6, 5), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 4), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, 0), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, -1), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, -2), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, -3), Material.STEP, (byte) 0);
		setBlock(loc.clone().add(-4, 6, -4), Material.STEP, (byte) 0);
	}
	
	public boolean isArrive() {
		return arrived;
	}

	public Station getStation() {
		try {
			return new StationManage().getList().get(st);
		} catch(Exception e) {
			return null;
		}
		
	}
	
	public Station getCurrentStation() {
		try {
			return new StationManage().getList().get(st-1);
		} catch(Exception e) {
			return null;
		}
		
	}
	
}
