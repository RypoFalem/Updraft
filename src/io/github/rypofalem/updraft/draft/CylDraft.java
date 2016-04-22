package io.github.rypofalem.updraft.draft;

import org.bukkit.Location;

public class CylDraft extends Draft {

	CylDraft(double acceleration, double glidingAcceleration, double maxYVelocity) {
		super(acceleration, glidingAcceleration, maxYVelocity);
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean isInRegion(Location location) {
		// TODO Auto-generated method stub
		return false;
	}

}
