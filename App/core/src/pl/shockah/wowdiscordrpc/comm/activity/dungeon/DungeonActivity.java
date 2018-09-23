package pl.shockah.wowdiscordrpc.comm.activity.dungeon;

import javax.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;
import pl.shockah.wowdiscordrpc.comm.activity.Activity;

@Getter
@Setter
public class DungeonActivity extends Activity {
	@Nullable
	public DungeonDifficulty difficulty;

	@Nullable
	public Dungeon dungeon;

	@Nullable
	@Override
	public String getRpcImageKey() {
		if (dungeon != null)
			return dungeon.rpcImageKey;
		else
			return super.getRpcImageKey();
	}
}