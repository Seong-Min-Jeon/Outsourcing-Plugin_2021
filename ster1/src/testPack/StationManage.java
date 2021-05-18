package testPack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class StationManage {

	private static ArrayList<Station> ary = new ArrayList<>();
	static File folder;
 	
	public ArrayList<Station> getList() {
		loadData();
		return ary;
	}
	
	public void addStation(String name, Location loc) {
		try {
			File dataFolder = folder;
            if(!dataFolder.exists()) {
                dataFolder.mkdir();
            } else {
            	File dir = new File(folder + "/station");
            	if(!dir.exists()) {
            		try{
            		    dir.mkdir(); 
            		} catch(Exception e) {
            		    e.getStackTrace();
            		}
            	}
            	try {
            		File file = new File(dir, "data.dat");
    				if (!file.exists()) {
    					try {
    						file.createNewFile();
    					} catch (IOException e) {
    						e.printStackTrace();
    					}
    				}
    				FileReader filereader = new FileReader(file);
                    BufferedReader bufReader = new BufferedReader(filereader);
                    BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true), "UTF-8"));
                    fw.write(name);
                    fw.write("\n");
                    fw.write(loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ());
                    fw.write("\n");
                    fw.close();
                    bufReader.close();
            	} catch(Exception e) {
            		
            	}
			}
		} catch(Exception e) {
			
		}
		loadData();
	}
	
	public boolean removeStation(Player player, String name) {
		loadData();
		boolean contain = false;
		for(int i = 0 ; i < ary.size() ; i++) {
			if(ary.get(i).name.equals(name)) {
				contain = true;
			}
		}
		if(contain == false) {
			player.sendMessage(ChatColor.RED + "해당 역을 발견하지 못했습니다.");
			return false;
		}
		try {
			File dataFolder = folder;
            if(!dataFolder.exists()) {
                dataFolder.mkdir();
            } else {
            	File dir = new File(folder + "/station");
            	if(!dir.exists()) {
            		try{
            		    dir.mkdir(); 
            		} catch(Exception e) {
            		    e.getStackTrace();
            		}
            	}
            	try {
            		File file = new File(dir, "data.dat");
            		File tempFile = new File(dir, "temp.dat");
    				if (!file.exists()) {
    					try {
    						file.createNewFile();
    					} catch (IOException e) {
    						e.printStackTrace();
    					}
    				}
    				FileReader filereader = new FileReader(file);
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
                    BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8"));

                    String lineToRemove = name;
                    String currentLine;
                    boolean find = false;

                    while((currentLine = bufReader.readLine()) != null) {
                        String trimmedLine = currentLine.trim();
                        if(trimmedLine.equals(lineToRemove)) {
                        	find = true;
                        	continue;
                        }
                        if(find) {
                        	find = false;
                        	continue;
                        }
                        bufWriter.write(currentLine + System.getProperty("line.separator"));
                    }
                    bufWriter.close();
                    bufReader.close();
                    if(!tempFile.renameTo(file)){
                    	byte[] buf = new byte[1024];
                        FileInputStream fin = new FileInputStream(tempFile);
                        FileOutputStream fout = new FileOutputStream(file);
                     
                        int read = 0;
                        while((read=fin.read(buf,0,buf.length))!=-1){
                            fout.write(buf, 0, read);
                        }
                         
                        fin.close();
                        fout.close();
                        tempFile.delete();
                    }

                    player.sendMessage(ChatColor.GREEN + "성공적으로 삭제되었습니다.");
                    return true;
            	} catch(Exception e) {
            		
            	}
			}
		} catch(Exception e) {
			
		}
		loadData();
		player.sendMessage(ChatColor.RED + "작업 중 오류가 발생했습니다.");
		return true;
	}
	
	public Location getNearStation(Player player) {
		loadData();
		HashMap<Integer, Location> map = new HashMap<>();
		Location loc = player.getLocation();
		for(int i = 0 ; i < ary.size() ; i++) {
			Station st = ary.get(i);
			int length = (int)(Math.pow(loc.getX()-st.x, 2) + 
					Math.pow(loc.getY()-st.y, 2) + Math.pow(loc.getZ()-st.z, 2));
			map.put(length, new Location(player.getWorld(),st.x,st.y,st.z));
		}
		
		int min = Integer.MAX_VALUE;
		for(int val : map.keySet()) {
			if(min > val) {
				min = val;
			}
		}
		
		Location near = map.get(min);
		
		return near.add(0,1,0);
	}
	
	public void loadData() {
		ary.clear();
		try {
			File dataFolder = folder;
            if(!dataFolder.exists()) {
                dataFolder.mkdir();
            } else {
            	File dir = new File(folder + "/station");
            	if(!dir.exists()) {
            		try{
            		    dir.mkdir(); 
            		} catch(Exception e) {
            		    e.getStackTrace();
            		}
            	}
            	try {
            		File file = new File(dir, "data.dat");
    				if (!file.exists()) {
    					try {
    						file.createNewFile();
    					} catch (IOException e) {
    						e.printStackTrace();
    					}
    				}
    				FileReader filereader = new FileReader(file);
    				BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
    				String name;
    				String loc;
    				while((name = bufReader.readLine()) != null) {
        				loc = bufReader.readLine();
        				String[] aryLoc = loc.split("/");
        				ary.add(new Station(name, Integer.parseInt(aryLoc[0]), Integer.parseInt(aryLoc[1]), Integer.parseInt(aryLoc[2])));
    				}
    				bufReader.close();
            	} catch(Exception e) {
            		
            	}
			}
		} catch(Exception e) {
			
		}
	}
	
	public void setFolder(File direc) {
		folder = direc;
	}
	
}
