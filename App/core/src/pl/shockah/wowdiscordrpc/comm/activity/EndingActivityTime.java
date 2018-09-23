package pl.shockah.wowdiscordrpc.comm.activity;

public class EndingActivityTime extends StartedActivityTime {
	public final int endingTime;

	public EndingActivityTime(int currentTime, int startedTime, int endingTime) {
		super(currentTime, startedTime);
		this.endingTime = endingTime;
	}
}