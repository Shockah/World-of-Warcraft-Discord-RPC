package pl.shockah.wowdiscordrpc.comm;

import javax.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;
import pl.shockah.wowdiscordrpc.comm.activity.Activity;

@Getter
@Setter
public class Message {
	@Nullable
	public Character character;

	@Nullable
	public Activity activity;
}