package pl.shockah.wowdiscordrpc.comm.activity.dungeon;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lombok.Getter;
import pl.shockah.wowdiscordrpc.comm.activity.other.Expansion;

import static pl.shockah.wowdiscordrpc.comm.activity.other.Expansion.*;

public class Dungeon {
	@Nonnull
	private static final Map<String, Dungeon> messageKeyMap = new HashMap<>();

	@Nonnull
	@Getter
	public final Expansion expansion;

	@Nonnull
	@Getter
	public final String rpcImageKey;

	@Nonnull
	@Getter
	public final String messageKey;

	@Nonnull
	@Getter
	public final String name;

	public Dungeon(@Nonnull Expansion expansion, @Nonnull String shortMessageKey, @Nonnull String name) {
		this(expansion, shortMessageKey, null, name);
	}

	public Dungeon(@Nonnull Expansion expansion, @Nonnull String shortMessageKey, @Nullable String rpcImageKey, @Nonnull String name) {
		this.expansion = expansion;
		this.messageKey = String.format("%d-%s", expansion.ordinal() + 1, shortMessageKey);
		this.rpcImageKey = rpcImageKey != null ? rpcImageKey : expansion.rpcImageKey;
		this.name = name;
	}

	@Nonnull
	public static Dungeon getDungeon(@Nonnull String messageKey) {
		return messageKeyMap.get(messageKey);
	}

	static {
		Dungeon[] dungeons = new Dungeon[] {
				new Dungeon(BattleForAzeroth, "ad", "dungeon-bfa-ataldazar", "Atal'Dazar"),
				new Dungeon(BattleForAzeroth, "fh", "dungeon-bfa-freehold", "Freehold"),
				new Dungeon(BattleForAzeroth, "kr", "dungeon-bfa-kings-rest", "Kings' Rest"),
				new Dungeon(BattleForAzeroth, "ml", "dungeon-bfa-motherlode", "The MOTHERLODE!!"),
				new Dungeon(BattleForAzeroth, "ss", "dungeon-bfa-shrine-of-the-storm", "Shrine of the Storm"),
				new Dungeon(BattleForAzeroth, "sb", "dungeon-bfa-siege-of-boralus", "Siege of Boralus"),
				new Dungeon(BattleForAzeroth, "ts", "dungeon-bfa-temple-of-sethraliss", "Temple of Sethraliss"),
				new Dungeon(BattleForAzeroth, "td", "dungeon-bfa-tol-dagor", "Tol Dagor"),
				new Dungeon(BattleForAzeroth, "ur", "dungeon-bfa-underrot", "Underrot"),
				new Dungeon(BattleForAzeroth, "wm", "dungeon-bfa-waycrest-manor", "Waycrest Manor")
		};

		for (Dungeon dungeon : dungeons) {
			messageKeyMap.put(dungeon.messageKey, dungeon);
		}
	}
}