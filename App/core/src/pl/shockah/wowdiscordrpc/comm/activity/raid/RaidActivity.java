package pl.shockah.wowdiscordrpc.comm.activity.raid;

import javax.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;
import pl.shockah.wowdiscordrpc.comm.activity.Activity;

@Getter
@Setter
public class RaidActivity extends Activity {
	@Nullable
	public RaidDifficulty difficulty;

	@Nullable
	public Raid raid;

	@Nullable
	public RaidComposition composition;

	@Nullable
	public RaidEncounter encounter;

	@Nullable
	@Override
	public String getRpcImageKey() {
		if (raid != null)
			return raid.rpcImageKey;
		else
			return super.getRpcImageKey();
	}
}