package io.github.rypofalem.updraft.draft;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public abstract class Draft {
	
	double acceleration;
	double glidingAcceleration;
	
	double maxYVelocity;
	
	Draft(double acceleration, double glidingAcceleration, double maxYVelocity){
		this.acceleration = acceleration;
		this.glidingAcceleration = glidingAcceleration;
		this.maxYVelocity = maxYVelocity;
	}
	
	abstract boolean isInRegion(Location location);
	
	public void applyToEntity(LivingEntity le){
		if(le == null) return;
		if(le instanceof Player){
			Player player = (Player) le;
			if(!player.isOnline()) return;
			if(!player.hasPermission("updraft.use")) return;
		}
		if(!isInRegion(le.getLocation())) return;
		
		if(le instanceof Player && ((Player)le).isFlying()){
			//creative mode flight, do nothing
		} else if(le.isGliding()){
			addVerticalVelocity(le, glidingAcceleration);
			Bukkit.getServer().broadcastMessage("Gliding Y velocity: " + le.getVelocity().getY());
		} else{
			addVerticalVelocity(le, acceleration);
			Bukkit.getServer().broadcastMessage("Walking Y velocity: " + le.getVelocity().getY());
		}
	}
	
	void addVerticalVelocity(Entity le, double targetVelocity){
		Vector velocity = le.getVelocity();
		double yVel =  .1 * (targetVelocity - velocity.getY()); // add % of the difference
		velocity.add(new Vector(0, yVel, 0));
		if(velocity.getY() > maxYVelocity) velocity.setY(maxYVelocity);
		if(velocity.getY() < maxYVelocity*-1) velocity.setY(maxYVelocity*-1);
		le.setVelocity(velocity);
	}
}
