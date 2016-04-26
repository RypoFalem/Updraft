package io.github.rypofalem.updraft.draft;

import org.bukkit.Location;

public class Bounds {
	
	//untested, be careful
	public static boolean isInBox(Location test, Location corner1, Location corner2){
		Location center = corner1.clone().subtract(corner2).multiply(.5); // center of box the appropriate size but with a corner at 0,0,0
		double xRadius = center.getX();
		double yRadius = center.getY();
		double zRadius = center.getZ();
		center = center.add(corner2); //now it is center of the box at the right place
		
		return isInBox(test, center, xRadius, yRadius, zRadius);
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
