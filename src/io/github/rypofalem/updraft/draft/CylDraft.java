package io.github.rypofalem.updraft.draft;

import java.util.ArrayList;

import org.bukkit.Location;

public class CylDraft extends Draft {
	double radius;
	double height;
	final double PI = Math.PI;
	ArrayList<Location> base;

	CylDraft(double acceleration, double glidingAcceleration) {
		super(acceleration, glidingAcceleration);
		// TODO Auto-generated constructor stub
	}
	
	public CylDraft(double acceleration, double glidingAcceleration, Location center, double radius, double height){
		super(acceleration, glidingAcceleration);
		setRegion(center, radius, height);
	}
	
	public void setRegion(Location center, double radius, double height){
		this.center = center;
		this.radius = radius;
		this.height = height;
		
		double resolution = .5;
		double arc = resolution/radius; //radians of an arc that is resolution blocks in length along the edge of the cylinder base
		double arcProgress = 0;
		double maxArc = 2*PI; //full circle
		while(arcProgress < maxArc){
			Location border = new Location(center.getWorld(), Math.cos(arcProgress) * radius, 0, Math.sin(arcProgress) * radius).add(center);
			borders.add(border);
			arcProgress += arc;
		}
		
		base = new ArrayList<Location>();
		for(double x = -1 * radius; x<= radius; x++){
			for(double z = -1 * radius; z<= radius; z++){
				Location test = new Location(center.getWorld(), x, 0, z).add(center);
				if(test.distance(center) <= radius) base.add(test);
			}
		}
	}

	@Override
	public boolean isInRegion(Location location) {
//		for(Location baseBlock : base){
//			if(location.getBlockX() == baseBlock.getBlockX() && 
//					location.getBlockZ() == baseBlock.getBlockZ() &&
//					location.getBlockY() >= baseBlock.getBlockY() &&
//					location.getBlockY() <= getMaxYValue()){
//				return true;
//			}
//		}
		
		if( ! Bounds.isInBox(location, center, radius, height, radius)) return false;
		
		if(location.getY() <= height + center.getY() && location.getY() >= center.getY()){
			double horizontalDistance = Math.sqrt(Math.pow(location.getX() - center.getX(), 2) + Math.pow(location.getZ() - center.getZ(), 2));
			if(horizontalDistance <= radius){
				return true;
			}
		}
		return false;
	}

	@Override
	double getMaxYValue() {
		return height + center.getY();
	}

}
