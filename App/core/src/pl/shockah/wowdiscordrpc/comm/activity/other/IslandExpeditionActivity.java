package pl.shockah.wowdiscordrpc.comm.activity.other;

import javax.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;
import pl.shockah.wowdiscordrpc.comm.activity.Activity;

@Getter
@Setter
public class IslandExpeditionActivity extends Activity {
	@Nullable
	public IslandExpeditionDifficulty difficulty;

	@Nullable
	public Integer playerProgress;

	@Nullable
	public Integer enemyProgress;

	@Nullable
	public Integer maxProgress;
}