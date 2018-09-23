package pl.shockah.wowdiscordrpc.comm.activity;

public class StartedActivityTime extends ActivityTime {
	public final int startedTime;

	public StartedActivityTime(int currentTime, int startedTime) {
		super(currentTime);
		this.startedTime = startedTime;
	}
}