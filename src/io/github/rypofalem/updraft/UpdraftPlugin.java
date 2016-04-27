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
	Commands commandex;
	int multiplier = 2;
	
	@Override
	public void onEnable(){
		loadConfig();
		getServer().getPluginManager().registerEvents(this, this);
		commandex = new Commands(this);
		getCommand("draft").setExecutor(commandex);
		dp = new DraftPusher(this);
		Bukkit.getScheduler().runTaskTimer(this, dp, 1,  multiplier);
	}
	
	public void loadConfig(){
		drafts = new ArrayList<Draft>();
		CuboidDraft cd = new CuboidDraft(.3, 3, new Location(Bukkit.getWorld("world"), -41, 77, -32), new Location(Bukkit.getWorld("world"), -51, 100, -44));
		CylDraft cyd = new CylDraft(.3, 3, new Location(Bukkit.getWorld("world"),-46, 77, -6), 5d, 30d);
		addDraft(cd);
		addDraft(cyd);
	}
	
	public void addDraft(Draft draft){
		if(drafts == null) drafts = new ArrayList<Draft>();
		drafts.add(draft);
	}
	
	public void removeDraft(Location location){
		if(drafts == null) return;
		ArrayList<Draft> removal = new ArrayList<Draft>();
		for(Draft draft : drafts){
			if(draft.isInRegion(location)) removal.add(draft);
		}
		drafts.removeAll(removal);
	}
	
	public static UpdraftPlugin getPlugin(){
		return (UpdraftPlugin) Bukkit.getServer().getPluginManager().getPlugin("Updraft");
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
		long offset = tick; //space the drafts that update out to reduce potential spikes
		int incr = (plugin.drafts.size() > 32) ? 1 : 64/plugin.drafts.size();
		for(Draft draft : plugin.drafts){
			if(offset % 64 == 0) draft.updateNearbyEntities();
			if(offset % 2 == 0) draft.spawnParticle();
			draft.applyToEntities();
			offset+= incr;
		}
		tick++;
	}
}
