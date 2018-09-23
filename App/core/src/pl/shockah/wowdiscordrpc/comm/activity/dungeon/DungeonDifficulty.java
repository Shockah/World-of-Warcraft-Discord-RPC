package pl.shockah.wowdiscordrpc.comm.activity.dungeon;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class DungeonDifficulty {
	@Nonnull
	public static final DungeonDifficulty normal = new DungeonDifficulty("Normal");

	@Nonnull
	public static final DungeonDifficulty heroic = new DungeonDifficulty("Heroic");

	@Nonnull
	public static final DungeonDifficulty mythic = new DungeonDifficulty("Mythic");

	@Nonnull
	private static final Map<Integer, DungeonDifficulty> mythicPlusCache = new HashMap<>();

	@Nonnull
	public final String name;

	@Nonnull
	public static DungeonDifficulty getMythicPlus(int keyLevel) {
		return mythicPlusCache.computeIfAbsent(keyLevel, key -> new DungeonDifficulty(String.format("Mythic +%d", (int)key)));
	}

	private DungeonDifficulty(@Nonnull String name) {
		this.name = name;
	}
}