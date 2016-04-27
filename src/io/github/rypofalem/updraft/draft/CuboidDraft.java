package io.github.rypofalem.updraft.draft;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class CuboidDraft extends Draft {
	Location corner1;
	Location corner2;

	CuboidDraft(double acceleration, double glidingAcceleration){
		super(acceleration, glidingAcceleration);
	}
	
	public CuboidDraft(double acceleration, double glidingAcceleration, Location corner1, Location corner2){
		super(acceleration, glidingAcceleration);
		setRegion(corner1, corner2);
	}
	
	public CuboidDraft(double acceleration, double glidingAcceleration, Location center, double radius, double height){
		super(acceleration, glidingAcceleration);
		Location corner1 = center.clone().add(-1 * radius, 0, -1 * radius);
		Location corner2 = center.clone().add(radius, height ,radius);
		setRegion(corner1, corner2);
	}

	void setRegion(Location corner1, Location corner2) {
		this.corner1 = corner1;
		this.corner2 = corner2;
		double centerX = (corner1.getX() - corner2.getX())/2d + corner2.getX();
		double centerZ = (corner1.getZ() - corner2.getZ())/2d + corner2.getZ();
		center = new Location(corner1.getWorld(), centerX, corner1.getY(), centerZ);
		
		double x = corner2.getX();
		int xSign = (corner1.getX()- corner2.getX() >= 0) ? 1 : -1;
		double z = corner2.getZ();
		int zSign = (corner1.getZ()- corner2.getZ() >= 0) ? 1 : -1;
		double y = Math.min(corner1.getBlockY(), corner2.getBlockY());
		
		while((xSign < 0 && x > corner1.getX()) 
				|| (xSign > 0 && x < corner1.getX())){
			borders.add(new Location(corner1.getWorld(), x, y, z));
			x += xSign * .5;
		}
		x = corner1.getX();
		
		while((zSign < 0 && z > corner1.getZ()) 
				|| (zSign > 0 && z < corner1.getZ())){
			borders.add(new Location(corner1.getWorld(), x, y, z));
			z += zSign * .5;
		}
		z= corner1.getZ();
		
		while((xSign > 0 && x > corner2.getX())
				|| (xSign < 0 && x < corner2.getX())){
			borders.add(new Location(corner1.getWorld(), x, y, z));
			x -= xSign * .5;
		}
		x = corner2.getX();
		
		while((zSign > 0 && z > corner2.getZ()) 
				|| (zSign < 0 && z < corner2.getZ())){
			borders.add(new Location(corner1.getWorld(), x, y, z));
			z -= zSign * .5;
		}
		
		Bukkit.getServer().broadcastMessage("Corner1: " + corner1.toVector().toString());
		Bukkit.getServer().broadcastMessage("Corner2: " + corner2.toVector().toString());
	}

	@Override
	public boolean isInRegion(Location location) {
		return Bounds.isInBox(location, corner1, corner2);
	}

	@Override
	double getMaxYValue() {
		return Math.max(corner1.getY(), corner2.getY());
	}
}
