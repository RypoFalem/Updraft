package io.github.rypofalem.updraft.draft;

import org.bukkit.Location;

public class CuboidDraft extends Draft {
	Location corner1;
	Location corner2;

	CuboidDraft(double acceleration, double glidingAcceleration, double mountedAcceleration, double maxYVelocity){
		super(acceleration, glidingAcceleration, maxYVelocity);
	}
	
	public CuboidDraft(double acceleration, double glidingAcceleration, double mountedAcceleration, double maxYVelocity, Location corner1, Location corner2){
		super(acceleration, glidingAcceleration, maxYVelocity);
		setRegion(corner1, corner2);
	}

	void setRegion(Location corner1, Location corner2) {
		this.corner1 = corner1;
		this.corner2 = corner2;
	}

	@Override
	boolean isInRegion(Location location) {
		if(corner1 == null || corner2 == null) return false;
		double maxX = Math.max(corner1.getX(), corner2.getX());
		double minX = Math.min(corner1.getX(), corner2.getX());
		double maxY = Math.max(corner1.getY(), corner2.getY());
		double minY = Math.min(corner1.getY(), corner2.getY());
		double maxZ = Math.max(corner1.getZ(), corner2.getZ());
		double minZ = Math.min(corner1.getZ(), corner2.getZ());
		
		if(location.getX() >= minX && location.getX() <= maxX 
				&& location.getY() >= minY && location.getY() <= maxY
				&& location.getZ() >= minZ && location.getZ() <= maxZ)
		{
			return true;
		}
		return false;
	}

}
