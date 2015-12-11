package com.elektronetf.ttt;

public final class TourneyData implements Cloneable {
	public static final int MAX_GROUP_COUNT = 16;
	public static final int PLAYERS_PER_GROUP = 4;

	@Override
	public TourneyData clone() {
		 try {
			TourneyData data = (TourneyData) super.clone();
			// TODO Cloning code
			return data;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);	// Shouldn't happen
		}
	}
}
