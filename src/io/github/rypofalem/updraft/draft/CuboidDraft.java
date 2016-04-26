package io.github.rypofalem.updraft.draft;

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

	void setRegion(Location corner1, Location corner2) {
		this.corner1 = corner1;
		this.corner2 = corner2;
		double centerX = (corner1.getX() - corner2.getX())/2d + corner2.getBlockX();
		double centerZ = (corner1.getZ() - corner2.getZ())/2d + corner2.getBlockZ();
		center = new Location(corner1.getWorld(), centerX, corner1.getY(), centerZ);
		
		double x = corner2.getX() + .5;
		int xSign = (corner1.getBlockX()- corner2.getBlockX() >= 0) ? 1 : -1;
		double z = corner2.getZ() + .5;
		int zSign = (corner1.getBlockZ()- corner2.getBlockZ() >= 0) ? 1 : -1;
		double y = Math.min(corner1.getBlockY(), corner2.getBlockY());
		while(x != corner1.getBlockX() + .5){
			borders.add(new Location(corner1.getWorld(), x, y, z));
			x += xSign * .5;
		}
		while(z != corner1.getBlockZ() + .5){
			borders.add(new Location(corner1.getWorld(), x, y, z));
			z += zSign * .5;
		}
		while(x != corner2.getBlockX() + .5){
			borders.add(new Location(corner1.getWorld(), x, y, z));
			x -= xSign * .5;
		}
		while(z != corner2.getBlockZ() + .5){
			borders.add(new Location(corner1.getWorld(), x, y, z));
			z -= zSign * .5;
		}
	}

	@Override
	boolean isInRegion(Location location) {
		if(corner1 == null || corner2 == null) return false;
		double maxX = Math.max(corner1.getX(), corner2.getX()) + 1; 
		double minX = Math.min(corner1.getX(), corner2.getX());
		double maxY = Math.max(corner1.getY(), corner2.getY()) + 1;
		double minY = Math.min(corner1.getY(), corner2.getY());
		double maxZ = Math.max(corner1.getZ(), corner2.getZ()) + 1;
		double minZ = Math.min(corner1.getZ(), corner2.getZ());
		
		if(location.getX() >= minX && location.getX() <= maxX 
				&& location.getY() >= minY && location.getY() <= maxY
				&& location.getZ() >= minZ && location.getZ() <= maxZ)
		{
			return true;
		}
		return false;
	}

	@Override
	double getMaxYValue() {
		return Math.max(corner1.getY(), corner2.getY());
	}
}
