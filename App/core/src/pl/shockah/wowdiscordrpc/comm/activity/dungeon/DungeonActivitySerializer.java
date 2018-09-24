package pl.shockah.wowdiscordrpc.comm.activity.dungeon;

import javax.annotation.Nonnull;

import pl.shockah.wowdiscordrpc.bin.BitBuffer;

public class DungeonActivitySerializer {
	@Nonnull
	public DungeonActivity deserialize(@Nonnull BitBuffer bits, int readVersion) {
		DungeonActivity activity = new DungeonActivity();

		if (bits.read())
			activity.difficulty = parseDifficulty(bits.readUInt(6));
		if (bits.read())
			activity.dungeon = Dungeon.getDungeon(bits.readString(4));

		return activity;
	}

	@Nonnull
	private DungeonDifficulty parseDifficulty(int difficultyNumber) {
		switch (difficultyNumber) {
			case 0:
				return DungeonDifficulty.normal;
			case 1:
				return DungeonDifficulty.heroic;
			case 2:
				return DungeonDifficulty.mythic;
			case 3:
				return DungeonDifficulty.timewalking;
			default:
				return DungeonDifficulty.getMythicPlus(difficultyNumber - 2);
		}
	}
}