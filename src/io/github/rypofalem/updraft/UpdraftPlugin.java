package io.github.rypofalem.updraft;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.rypofalem.updraft.draft.CuboidDraft;
import io.github.rypofalem.updraft.draft.CylDraft;
import io.github.rypofalem.updraft.draft.Draft;

public class UpdraftPlugin extends JavaPlugin implements Listener{
	ArrayList<Draft> drafts;
	DraftPusher dp;
	int multiplier = 1;
	
	@Override
	public void onEnable(){
		loadConfig();
		getServer().getPluginManager().registerEvents(this, this);
		
	}
	
	public void loadConfig(){
		drafts = new ArrayList<Draft>();
		CuboidDraft cd = new CuboidDraft(.5*multiplier, 3*multiplier, 3*multiplier, new Location(Bukkit.getWorld("world"), -41, 77, -32), new Location(Bukkit.getWorld("world"), -51, 100, -44));
		drafts.add(cd);
		CylDraft cyd = new CylDraft(.5*multiplier, 3*multiplier, 3*multiplier, new Location(Bukkit.getWorld("world"),-46, 77, -6), 5d, 30d);
		drafts.add(cyd);
		dp = new DraftPusher(this);
		Bukkit.getScheduler().runTaskTimer(this, dp, 1, multiplier);
	}
	
	public int getMultiplier(){
		return multiplier;
	}
}

class DraftPusher implements Runnable{
	UpdraftPlugin plugin;
	long tick = 0; // Danger: Overflow in 14.62 billion years. Please do not run this plugin for longer than 14.62 billion years.
	
	
	public DraftPusher(UpdraftPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		tick++;
		for(Draft draft : plugin.drafts){
			if(tick%64 == 0){
				draft.updateNearbyEntities();
			}
			if(tick % plugin.multiplier == 0){
				draft.applyToEntities();
			}
			if(tick %2 == 0) draft.spawnParticle();
		}
	}
}
