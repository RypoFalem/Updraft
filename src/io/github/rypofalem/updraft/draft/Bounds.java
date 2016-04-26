package io.github.rypofalem.updraft.draft;

import org.bukkit.Location;

public class Bounds {
	
	//untested, be careful
	public static boolean isInBox(Location test, Location corner1, Location corner2){
		if(test == null || corner1 == null || corner2 == null) return false;
		if(test.getWorld() != corner1.getWorld() || test.getWorld() != corner2.getWorld()) return false;
		double maxX = Math.max(corner1.getX(), corner2.getX()) + 1; 
		double minX = Math.min(corner1.getX(), corner2.getX());
		double maxY = Math.max(corner1.getY(), corner2.getY()) + 1;
		double minY = Math.min(corner1.getY(), corner2.getY());
		double maxZ = Math.max(corner1.getZ(), corner2.getZ()) + 1;
		double minZ = Math.min(corner1.getZ(), corner2.getZ());
		
		if(test.getX() >= minX && test.getX() <= maxX 
				&& test.getY() >= minY && test.getY() <= maxY
				&& test.getZ() >= minZ && test.getZ() <= maxZ)
		{
			return true;
		}
		return false;
	}
	
	public static boolean isInBox(Location test, Location center, double xRadius, double yRadius, double zRadius){
		if(test.getWorld() != center.getWorld()) return false;
		
		double minX = center.getX() - xRadius;
		double maxX = center.getX() + xRadius;
		double minY = center.getY() - yRadius;
		double maxY = center.getY() + yRadius;
		double minZ = center.getZ() - zRadius;
		double maxZ = center.getZ() + zRadius;
		
		if(test.getX() >= minX && test.getX() <= maxX &&
				test.getY() >= minY && test.getY() <= maxY &&
				test.getZ() >= minZ && test.getZ() <= maxZ){
			return true;
		}
		return false;
		
	}
	
}
