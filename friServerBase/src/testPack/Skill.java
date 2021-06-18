package testPack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Husk;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Mushroom;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class Skill {

	Random rnd = new Random();
	
	public void effect(Player player, String key) {
		if(player.getInventory().getItem(8).getItemMeta().getDisplayName().equals(ChatColor.GOLD + "마우러스의 증표")) {
			skill1(player, key);
		} else if(player.getInventory().getItem(8).getItemMeta().getDisplayName().equals(ChatColor.GOLD + "나오의 증표")) {
			skill2(player, key);
		} else if(player.getInventory().getItem(8).getItemMeta().getDisplayName().equals(ChatColor.GOLD + "타르라크의 증표")) {
			skill3(player, key);
		} else if(player.getInventory().getItem(8).getItemMeta().getDisplayName().equals(ChatColor.GOLD + "루에리의 증표")) {
			skill4(player, key);
		} else if(player.getInventory().getItem(8).getItemMeta().getDisplayName().equals(ChatColor.GOLD + "루와이의 증표")) {
			skill5(player, key);
		} else if(player.getInventory().getItem(8).getItemMeta().getDisplayName().equals(ChatColor.GOLD + "아쿨의 증표")) {
			skill6(player, key);
		} else if(player.getInventory().getItem(8).getItemMeta().getDisplayName().equals(ChatColor.GOLD + "콜로서스의 증표")) {
			skill7(player, key);
		} else if(player.getInventory().getItem(8).getItemMeta().getDisplayName().equals(ChatColor.GOLD + "스티브의 증표")) {
			skill8(player, key);
		} else if(player.getInventory().getItem(8).getItemMeta().getDisplayName().equals(ChatColor.GOLD + "마리의 증표")) {
			skill9(player, key);
		}
	}
	
	// 마우러스
	public void skill1(Player player, String key) {
		if(key.equals("RRR")) {
			int cool = 10;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect1();
				player.sendMessage(ChatColor.GREEN + "[스킬]데스 마리오네트가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RRL")) {
			int cool = 20;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect2();
				player.sendMessage(ChatColor.GREEN + "[스킬]클럭워크가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLL")) {
			int cool = 25;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect3();
				player.sendMessage(ChatColor.GREEN + "[스킬]슈레드치퍼가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLR")) {
			if(player.getLevel() >= 10) {
				player.setLevel(0);
				player.setExp(0);
				new ParticleEffect(player).newEffect4();
				player.sendMessage(ChatColor.GREEN + "[스킬]데스트로이 액세스가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "궁극기는 10레벨에 사용할 수 있습니다.");
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		}
	}
	
	// 나오
	public void skill2(Player player, String key) {
		if(key.equals("RRR")) {
			int cool = 5;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect5();
				player.sendMessage(ChatColor.GREEN + "[스킬]바르카롤이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RRL")) {
			int cool = 20;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect6();
				player.sendMessage(ChatColor.GREEN + "[스킬]에델바이스가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLL")) {
			int cool = 30;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect7();
				player.sendMessage(ChatColor.GREEN + "[스킬]스톤가든이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLR")) {
			if(player.getLevel() >= 10) {
				player.setLevel(0);
				player.setExp(0);
				new ParticleEffect(player).newEffect8();
				player.sendMessage(ChatColor.GREEN + "[스킬]알스트로메리아가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "궁극기는 10레벨에 사용할 수 있습니다.");
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		}
	}
	
	// 타르라크
	public void skill3(Player player, String key) {
		if(key.equals("RRR")) {
			int cool = 20;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect9();
				player.sendMessage(ChatColor.GREEN + "[스킬]파이라가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RRL")) {
			int cool = 20;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect10();
				player.sendMessage(ChatColor.GREEN + "[스킬]블리자드가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLL")) {
			int cool = 35;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect11();
				player.sendMessage(ChatColor.GREEN + "[스킬]시프트 브레이크가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLR")) {
			if(player.getLevel() >= 10) {
				player.setLevel(0);
				player.setExp(0);
				new ParticleEffect(player).newEffect12();
				player.sendMessage(ChatColor.GREEN + "[스킬]썬더가가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "궁극기는 10레벨에 사용할 수 있습니다.");
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		}
	}
	
	// 루에리
	public void skill4(Player player, String key) {
		if(key.equals("RRR")) {
			int cool = 25;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect13();
				player.sendMessage(ChatColor.GREEN + "[스킬]워커메이커가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RRL")) {
			int cool = 20;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect14();
				player.sendMessage(ChatColor.GREEN + "[스킬]인티미데이션가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLL")) {
			int cool = 10;
			if(new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect15();
				player.sendMessage(ChatColor.GREEN + "[스킬]러시다운이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if(key.equals("RLR")) {
			if(player.getLevel() >= 10) {
				player.setLevel(0);
				player.setExp(0);
				new ParticleEffect(player).newEffect16();
				player.sendMessage(ChatColor.GREEN + "[스킬]마타도르가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "궁극기는 10레벨에 사용할 수 있습니다.");
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		}
	}
	
	// 루와이
	public void skill5(Player player, String key) {
		if (key.equals("RRR")) {
			int cool = 30;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect17();
				player.sendMessage(ChatColor.GREEN + "[스킬]'하나된 별의 반짝임이, 새로운 기적을 비춰준다. 소환! 빛으로 태어나라! 아이스 골렘!'이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RRL")) {
			int cool = 30;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect18();
				player.sendMessage(ChatColor.GREEN + "[스킬]'결집한 별이 하나가 될 때, 새로운 유대가 미래를 밝히니, 빛이 비추는 길이 되어라! 소환! 진화의 빛, 울프 워리어!!'가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RLL")) {
			int cool = 30;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect19();
				player.sendMessage(ChatColor.GREEN + "[스킬]'클리어 마인드! 하나된 꿈의 결정이 새로운 진화의 문을 연다. 빛이 비추는 길이 되어라! 소환! 태어나라, 캣시 위저드!'가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RLR")) {
			if (player.getLevel() >= 10) {
				player.setLevel(0);
				player.setExp(0);
				new ParticleEffect(player).newEffect20();
				player.sendMessage(ChatColor.GREEN + "[스킬]'우주에 가득 찬 신비의 힘, 기적의 별에 쏟아져, 무한한 생명을 자아내라! 소환! 나타나라, 팬텀 나이트!'가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "궁극기는 10레벨에 사용할 수 있습니다.");
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		}
	}
	
	// 아쿨
	public void skill6(Player player, String key) {
		if (key.equals("RRR")) {
			int cool = 10;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect21();
				player.sendMessage(ChatColor.GREEN + "[스킬]익스플로더가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RRL")) {
			int cool = 30;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect22();
				player.sendMessage(ChatColor.GREEN + "[스킬]퀵해머러시가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RLL")) {
			int cool = 30;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect23();
				player.sendMessage(ChatColor.GREEN + "[스킬]블러드팬이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RLR")) {
			if (player.getLevel() >= 10) {
				player.setLevel(0);
				player.setExp(0);	
				new ParticleEffect(player).newEffect24();
				player.sendMessage(ChatColor.GREEN + "[스킬]더블콤바인이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "궁극기는 10레벨에 사용할 수 있습니다.");
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		}
	}

	// 콜로서스
	public void skill7(Player player, String key) {
		if (key.equals("RRR")) {
			int cool = 40;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect25();
				player.sendMessage(ChatColor.GREEN + "[스킬]기간틱앵거가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RRL")) {
			int cool = 40;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect26();
				player.sendMessage(ChatColor.GREEN + "[스킬]파워슬램이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RLL")) {
			int cool = 20;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect27();
				player.sendMessage(ChatColor.GREEN + "[스킬]아토믹버스터가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RLR")) {
			if (player.getLevel() >= 10) {
				player.setLevel(0);
				player.setExp(0);	
				new ParticleEffect(player).newEffect28();
				player.sendMessage(ChatColor.GREEN + "[스킬]크레이그혼이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "궁극기는 10레벨에 사용할 수 있습니다.");
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		}
	}
	
	// 스티브
	public void skill8(Player player, String key) {
		if (key.equals("RRR")) {
			int cool = 10;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect29();
				player.sendMessage(ChatColor.GREEN + "[스킬]이글훅이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RRL")) {
			int cool = 20;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect30();
				player.sendMessage(ChatColor.GREEN + "[스킬]플리커가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RLL")) {
			int cool = 20;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect31();
				player.sendMessage(ChatColor.GREEN + "[스킬]소닉팡이 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RLR")) {
			int cool = 60;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect32();
				player.sendMessage(ChatColor.GREEN + "[스킬]파워크래시가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		}
	}
	
	// 마리
	public void skill9(Player player, String key) {
		if (key.equals("RRR")) {
			int cool = 15;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect33();
				player.sendMessage(ChatColor.GREEN + "[스킬]포이즈너가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RRL")) {
			int cool = 20;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect34();
				player.sendMessage(ChatColor.GREEN + "[스킬]에로우 실드가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RLL")) {
			int cool = 30;
			if (new CoolTime().coolCheck(player, cool, key)) {
				new ParticleEffect(player).newEffect35();
				player.sendMessage(ChatColor.GREEN + "[스킬]저지먼트 스크류가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "쿨타임: " + new CoolTime().returnCool(player, cool, key));
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		} else if (key.equals("RLR")) {
			if (player.getLevel() >= 10) {
				player.setLevel(0);
				player.setExp(0);	
				new ParticleEffect(player).newEffect36();
				player.sendMessage(ChatColor.GREEN + "[스킬]밤 에로우가 발동됩니다.");
			} else {
				player.sendMessage(ChatColor.WHITE + "궁극기는 10레벨에 사용할 수 있습니다.");
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 2.0f);
			}
		}
	}
	
}
