package io.github.rypofalem.updraft.draft;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public abstract class Draft {
	
	double walkingWindSpeed;
	double glidingWindSpeed;
	double walkingThreshold; //when entities velocities are above this threshold, they simply match the windspeed
	double glidingThreshold;
	ArrayList<Location> borders;
	int bordersIndex = 0;
	List<Entity> entities;
	Location center;
	
	Draft(double walkingWindSpeed, double glidingWindSpeed){
		this.walkingWindSpeed = walkingWindSpeed;
		this.glidingWindSpeed = glidingWindSpeed;
		walkingThreshold = .001 * walkingWindSpeed;
		glidingThreshold = .001 * glidingWindSpeed;
		borders = new ArrayList<Location>();
		entities = new ArrayList<Entity>();
	}
	
	abstract boolean isInRegion(Location location);
	
	public void applyToEntity(Entity entity){
		if(entity == null) return;
		if(entity instanceof Player){
			Player player = (Player) entity;
			if(!player.isOnline()) return;
			if(!player.hasPermission("updraft.use")) return;
		}
		if(!isInRegion(entity.getLocation())) return;
		
		entity.setFallDistance(0);
		if(entity instanceof Player && ((Player)entity).isFlying()){
			//creative mode flight, do nothing
		} else if(entity instanceof LivingEntity && ((LivingEntity)entity).isGliding()){
			addVerticalVelocity(entity, glidingWindSpeed, glidingThreshold);
		} else{
			addVerticalVelocity(entity, walkingWindSpeed, walkingThreshold);
		}
	}
	
	void addVerticalVelocity(Entity le, double targetVelocity, double threshold){
		Vector velocity = le.getVelocity();
		double yVel =  (targetVelocity - velocity.getY());
		if(yVel <= threshold){
			velocity.setY(targetVelocity);
		}else{
			yVel = yVel/8;
			velocity.add(new Vector(0, yVel, 0));
		}
		le.setVelocity(velocity);
	}
	
	abstract double getMaxYValue();
	
	public void spawnParticle(){
		if(borders == null || borders.size() < 1) return;
		if(entities.isEmpty()) return;
		while(bordersIndex < 0){
			bordersIndex += borders.size();
		}
		double speed = .4;
		ParticleTrail p = new ParticleTrail(bordersIndex % borders.size(), borders, Particle.CRIT, speed, getMaxYValue());
		ParticleTrail p2 = new ParticleTrail((bordersIndex+ borders.size()/2) % borders.size(), borders, Particle.CRIT, speed, getMaxYValue());
		p.playEffect();
		p2.playEffect();
		bordersIndex-= 3;
	}

	public void updateNearbyEntities() {
		entities = new ArrayList<Entity>();
		for(Player p : center.getWorld().getPlayers()){
			if(Bounds.isInBox(p.getLocation(), center, 128, 256, 128)) entities.add(p);
		}
	}

	public void applyToEntities() {
		for(Entity e : entities){
			applyToEntity(e);
		}
	}
}
