package pl.shockah.wowdiscordrpc.comm.activity.other;

import javax.annotation.Nonnull;

import pl.shockah.wowdiscordrpc.bin.BitBuffer;

public class WorldActivitySerializer {
	@Nonnull
	public WorldActivity deserialize(@Nonnull BitBuffer bits, int readVersion) {
		WorldActivity activity = new WorldActivity();

		if (bits.read())
			activity.zoneName = bits.readString(7);

		return activity;
	}
}