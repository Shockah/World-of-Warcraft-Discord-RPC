package pl.shockah.wowdiscordrpc.comm.activity.other;

import javax.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;
import pl.shockah.wowdiscordrpc.comm.activity.Activity;

@Getter
@Setter
public class WorldActivity extends Activity {
	@Nullable
	public String zoneName;
}