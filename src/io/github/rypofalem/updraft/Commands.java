package io.github.rypofalem.updraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.rypofalem.updraft.draft.CuboidDraft;
import io.github.rypofalem.updraft.draft.CylDraft;
import io.github.rypofalem.updraft.draft.Draft;

public class Commands implements CommandExecutor{

	UpdraftPlugin plugin;
	final String CREATEDESCRIPT = "/draft create <radius> <height> <walkVelocity> <flyVelocity> [\"cuboid\"] - create a new draft";
	final String REMOVEDESCRIPT = "/draft remove - Remove all drafts you are standing in";
	final String RELOADDESCRIPT = "/draft reload - reload the draft config";
	
	Commands(UpdraftPlugin plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if(args == null || args.length < 1) return false;
		switch(args[0]){
		case "?": 
			listOptions(sender);
			break;
		case "create":
			if(sender.hasPermission("draft.create")){
				create(sender, args);
			} else return false;
			break;
		case "remove":
			if(sender.hasPermission("draft.create")){
				remove(sender, args);
			} else return false;
			break;
		case "reload":
			if(sender.isOp()){
				reload(sender, args);
			} else return false;
			break;
		}
		return true;
	}

	private void reload(CommandSender sender, String[] args) {
		plugin.loadConfig();
		
	}

	private void listOptions(CommandSender sender) {
		if(sender instanceof Player && sender.hasPermission("draft.create")){
			sender.sendMessage(CREATEDESCRIPT);
			sender.sendMessage("/draft remove");
		} 
		if(sender.isOp()){
			sender.sendMessage("/draft reload");
		}
	}

	private void create(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("Only players can use this command. Add an entry in UpDraft/config.yml and reload with /updraft reload in order to add drafts via console.");
			return;
		}
		if(args.length <5){
			sender.sendMessage("Not enough arguments.");
			sender.sendMessage(CREATEDESCRIPT);
			return;
		}
		if(args.length >6){
			sender.sendMessage("Too many arguments.");
			sender.sendMessage(CREATEDESCRIPT);
			return;
		}
		
		double radius, height, walkVelocity, flyVelocity;
		boolean cuboid = false;
		
		try{radius = Double.parseDouble(args[1]);} catch(Exception e){
			sender.sendMessage(args[1] + " is not an integer or decimal");
			sender.sendMessage(CREATEDESCRIPT);
			return;
		}
		
		try{height = Double.parseDouble(args[2]);} catch(Exception e){
			sender.sendMessage(args[2] + " is not an integer or decimal");
			sender.sendMessage(CREATEDESCRIPT);
			return;
		}
		
		try{walkVelocity = Double.parseDouble(args[3]);} catch(Exception e){
			sender.sendMessage(args[3] + " is not a integer or decimal");
			sender.sendMessage(CREATEDESCRIPT);
			return;
		}
		
		try{flyVelocity = Double.parseDouble(args[4]);} catch(Exception e){
			sender.sendMessage(args[4] + " is not a integer or decimal");
			sender.sendMessage(CREATEDESCRIPT);
			return;
		}
		
		if(args.length == 6){
			if(args[5].equalsIgnoreCase("cuboid")){
				cuboid = true;
			} else{
				sender.sendMessage(args[5] + " should either be \"cuboid\" for a box-shaped draft or left blank.");
				sender.sendMessage(CREATEDESCRIPT);
				return;
			}
		}
		
		if(radius > 25 || radius < 0){
			sender.sendMessage("Radius must be between 0 and 25. Aborting creation.");
			return;
		}
		
		if(height > 256 || height < 0){
			sender.sendMessage("Height must be between 0 and 256. Aborting creation.");
			return;
		}
		
		if(walkVelocity > 100 || walkVelocity < 0){
			sender.sendMessage("WalkVelocity must be between 0 and 100. Aborting creation.");
			return;
		}
		
		if(flyVelocity > 100 || flyVelocity < 0){
			sender.sendMessage("FlyVelocity must be between 0 and 100. Aborting creation.");
			return;
		}
		
		Draft draft;
		if(cuboid){
			draft = new CuboidDraft(walkVelocity, flyVelocity, ((Player)sender).getLocation(), radius, height);
		} else{
			draft = new CylDraft(walkVelocity, flyVelocity, ((Player)sender).getLocation(), radius, height);
		}
		plugin.addDraft(draft);
	}
	
	private void remove(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("Only players can use this command. Add an entry in UpDraft/config.yml and reload with /updraft reload in order to add drafts via console.");
			return;
		}
		plugin.removeDraft(((Player)sender).getLocation());
	}
}