package io.github.rypofalem.updraft.draft;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticleTrail {
	Location location;
	Particle particleType;
	double yspeed;
	int pauseTicks=1;
	int index = 0;
	ArrayList<Location> borders;
	double maxHeight;

	public ParticleTrail(int start, ArrayList<Location> borders, Particle particleType, double yspeed, double maxHeight){
		this.location = borders.get(0);
		this.particleType = particleType;
		this.yspeed = yspeed;
		index = start;
		this.borders = borders;
		this.maxHeight = maxHeight;
	}

	void playEffect(){
		if(borders == null || borders.size() <1) return;
		double height = location.getY();
		while(height <= maxHeight){
			if(index >= borders.size()) index = 0;
			location = borders.get(index).clone();
			location.setY(height);
			location.getWorld().spawnParticle(particleType, location, 1, .1d, .25d, .1d, 0d);
			index++;
			height += yspeed;
		}
	}
}
