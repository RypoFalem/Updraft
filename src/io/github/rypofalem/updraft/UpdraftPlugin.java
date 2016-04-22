package io.github.rypofalem.updraft;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.rypofalem.updraft.draft.CuboidDraft;
import io.github.rypofalem.updraft.draft.Draft;

public class UpdraftPlugin extends JavaPlugin implements Listener{
	ArrayList<Draft> drafts;
	DraftPusher dp;
	
	@Override
	public void onEnable(){
		loadConfig();
		getServer().getPluginManager().registerEvents(this, this);
		
	}
	
	public void loadConfig(){
		drafts = new ArrayList<Draft>();
		CuboidDraft cd = new CuboidDraft(.5, 3, 3, 15, new Location(Bukkit.getWorld("world"), -41, 77, -32), new Location(Bukkit.getWorld("world"), -51, 100, -44));
		drafts.add(cd);
		dp = new DraftPusher(drafts);
		Bukkit.getScheduler().runTaskTimer(this, dp, 1, 1);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
//		for(Draft draft : drafts){
//			draft.applyToEntity(event.getPlayer());
//		}
	}
}

class DraftPusher implements Runnable{
	ArrayList<Draft> drafts;
	
	public DraftPusher(ArrayList<Draft> drafts) {
		this.drafts = drafts;
	}

	@Override
	public void run() {
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			for(Draft draft : drafts){
				draft.applyToEntity(p);
			}
		}
	}
	
}
