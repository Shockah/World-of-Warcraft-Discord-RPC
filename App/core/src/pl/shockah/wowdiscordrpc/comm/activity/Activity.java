package pl.shockah.wowdiscordrpc.comm.activity;

import javax.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Activity {
	@Nullable
	public ActivityTime time;

	@Nullable
	public String getRpcImageKey() {
		return null;
	}
}