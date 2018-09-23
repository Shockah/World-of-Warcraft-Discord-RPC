package pl.shockah.wowdiscordrpc.comm.activity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import pl.shockah.wowdiscordrpc.bin.BitBuffer;
import pl.shockah.wowdiscordrpc.comm.activity.dungeon.DungeonActivitySerializer;
import pl.shockah.wowdiscordrpc.comm.activity.other.IslandExpeditionActivitySerializer;
import pl.shockah.wowdiscordrpc.comm.activity.other.WorldActivitySerializer;
import pl.shockah.wowdiscordrpc.comm.activity.raid.RaidActivitySerializer;

public class ActivitySerializer {
	public enum Type {
		World, Dungeon, Raid, IslandExpedition
	}

	@Nonnull
	private final WorldActivitySerializer worldActivitySerializer = new WorldActivitySerializer();

	@Nonnull
	private final DungeonActivitySerializer dungeonActivitySerializer = new DungeonActivitySerializer();

	@Nonnull
	private final RaidActivitySerializer raidActivitySerializer = new RaidActivitySerializer();

	@Nonnull
	private final IslandExpeditionActivitySerializer islandExpeditionActivitySerializer = new IslandExpeditionActivitySerializer();

	@Nonnull
	public Activity deserialize(@Nonnull BitBuffer bits, int readVersion) {
		Activity activity;

		switch (Type.values()[bits.readUInt(5)]) {
			case World:
				activity = worldActivitySerializer.deserialize(bits, readVersion);
				break;
			case Dungeon:
				activity = dungeonActivitySerializer.deserialize(bits, readVersion);
				break;
			case Raid:
				activity = raidActivitySerializer.deserialize(bits, readVersion);
				break;
			case IslandExpedition:
				activity = islandExpeditionActivitySerializer.deserialize(bits, readVersion);
				break;
			default:
				throw new IllegalArgumentException("Unknown activity type.");
		}

		if (bits.read())
			activity.time = readTime(bits);

		return activity;
	}

	@Nullable
	private ActivityTime readTime(@Nonnull BitBuffer bits) {
		int currentTime = bits.readUInt(32);
		if (bits.read()) {
			int startedTime = bits.readUInt(32);
			if (bits.read()) {
				int endingTime = bits.readUInt(32);
				return new EndingActivityTime(currentTime, startedTime, endingTime);
			}
			return new StartedActivityTime(currentTime, startedTime);
		}
		return null;
	}
}