package pl.shockah.wowdiscordrpc.comm.activity.other;

import javax.annotation.Nonnull;

import pl.shockah.wowdiscordrpc.bin.BitBuffer;

public class IslandExpeditionActivitySerializer {
	@Nonnull
	public IslandExpeditionActivity deserialize(@Nonnull BitBuffer bits, int readVersion) {
		IslandExpeditionActivity activity = new IslandExpeditionActivity();

		if (bits.read())
			activity.difficulty = IslandExpeditionDifficulty.values()[bits.readUInt(2)];
		if (bits.read()) {
			activity.playerProgress = bits.readUInt(15);
			activity.enemyProgress = bits.readUInt(15);
			activity.maxProgress = bits.readUInt(15);
		}

		return activity;
	}
}