package util.chlwhdtn;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;

import economy.chlwhdtn.Economy;
import economy.chlwhdtn.MoneyManager;

public class CUtil extends JavaPlugin {
	
	@Override
	public void onEnable() {
		if(Bukkit.getPluginManager().getPlugin("C-Economy") == null) {
			System.out.println("필수 플러그인이 존재하지 않습니다.");
			Bukkit.getPluginManager().disablePlugin(this);
		} else {
			Economy.online(this);
		}
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static void updateScoreboard(Player p) {
		Scoreboard sb = p.getScoreboard();
		Objective obj;
		if((obj = sb.getObjective("stat")) == null) {
			sb = Bukkit.getScoreboardManager().getNewScoreboard();
			obj = sb.registerNewObjective("stat", "dummy", "메리 크리스마스!", RenderType.INTEGER);
		}
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		obj.getScore("돈").setScore((int) MoneyManager.getMoney(p.getName()));
		if(getScore(p, "mining") != 0)
			obj.getScore("채굴").setScore(getScore(p, "mining"));
		if(getScore(p, "pakuru") != 0) 
			obj.getScore("파쿠르").setScore(getScore(p, "pakuru"));
		if(getScore(p, "penalty") != 0) {
			obj.getScore("비매너").setScore(getScore(p, "penalty"));
		}
		
		if((obj = sb.getObjective("pstat")) == null) {
			obj = sb.registerNewObjective("pstat", "dummy", "비매너", RenderType.INTEGER);
		}
		obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
		for(Player onlinep : Bukkit.getOnlinePlayers())
			obj.getScore(onlinep.getName()).setScore(getScore(onlinep, "penalty"));
		
		p.setScoreboard(sb);
	}
	
	public static void addScore(Player p, String scorename, String scoredisplay, int addpoint) {
		Scoreboard sb = p.getScoreboard();
		Objective obj;
		if ((obj = sb.getObjective("stat")) == null) {
			sb = Bukkit.getScoreboardManager().getNewScoreboard();
			obj = sb.registerNewObjective("stat", "dummy", "상태", RenderType.INTEGER);
		}
		if (Bukkit.getScoreboardManager().getMainScoreboard().getObjective(scorename) == null) {
			Bukkit.getScoreboardManager().getMainScoreboard().registerNewObjective(scorename, "dummy", scoredisplay);
		}
		Bukkit.getScoreboardManager().getMainScoreboard().getObjective(scorename)
				.getScore(p.getName()).setScore(Bukkit.getScoreboardManager().getMainScoreboard()
						.getObjective(scorename).getScore(p.getName()).getScore() + addpoint);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);

		obj.getScore(scoredisplay).setScore(Bukkit.getScoreboardManager().getMainScoreboard().getObjective(scorename).getScore(p.getName()).getScore());
		p.setScoreboard(sb);
	}
	
	public static void setScore(Player p, String scorename, String scoredisplay, int point) {
		Scoreboard sb = p.getScoreboard();
		Objective obj;
		if ((obj = sb.getObjective("stat")) == null) {
			sb = Bukkit.getScoreboardManager().getNewScoreboard();
			obj = sb.registerNewObjective("stat", "dummy", "상태", RenderType.INTEGER);
		}
		if (Bukkit.getScoreboardManager().getMainScoreboard().getObjective(scorename) == null) {
			Bukkit.getScoreboardManager().getMainScoreboard().registerNewObjective(scorename, "dummy", scoredisplay);
		}

		Bukkit.getScoreboardManager().getMainScoreboard().getObjective(scorename)
				.getScore(p.getName()).setScore(point);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);

		obj.getScore(scoredisplay).setScore(Bukkit.getScoreboardManager().getMainScoreboard().getObjective(scorename).getScore(p.getName()).getScore());
		p.setScoreboard(sb);
	}
	
	public static void addTempScore(Player p, String scoredisplay, int addpoint) {
		Scoreboard sb = p.getScoreboard();
		Objective obj;
		if ((obj = sb.getObjective("stat")) == null) {
			sb = Bukkit.getScoreboardManager().getNewScoreboard();
			obj = sb.registerNewObjective("stat", "dummy", "상태", RenderType.INTEGER);
		}
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.getScore(scoredisplay).setScore(getScore(p, scoredisplay) + addpoint);
		p.setScoreboard(sb);
	}
	
	public static int getScore(Player p, String scorename) {
		try {
			return Bukkit.getScoreboardManager().getMainScoreboard().getObjective(scorename).getScore(p.getName()).getScore();
		} catch(NullPointerException e) {
			try {
				return p.getScoreboard().getObjective("stat").getScore(scorename).getScore();
			} catch(NullPointerException e2) {
				return 0;
			}
		}
	}
	public static void resetTempScore(Player p, String scorename) {
		Scoreboard sb = p.getScoreboard();
		sb.resetScores(scorename);
		p.setScoreboard(sb);
	}

}
