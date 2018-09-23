package pl.shockah.wowdiscordrpc.comm.activity;

public abstract class ActivityTime {
	public final int currentTime;

	protected ActivityTime(int currentTime) {
		this.currentTime = currentTime;
	}
}