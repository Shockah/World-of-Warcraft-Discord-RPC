package pl.shockah.wowdiscordrpc.comm.activity.raid;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lombok.Getter;
import pl.shockah.wowdiscordrpc.comm.activity.other.Expansion;

import static pl.shockah.wowdiscordrpc.comm.activity.other.Expansion.*;

public class Raid {
	@Nonnull
	private static final Map<String, Raid> messageKeyMap = new HashMap<>();

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

	public Raid(@Nonnull Expansion expansion, @Nonnull String shortMessageKey, @Nonnull String name) {
		this(expansion, shortMessageKey, null, name);
	}

	public Raid(@Nonnull Expansion expansion, @Nonnull String shortMessageKey, @Nullable String rpcImageKey, @Nonnull String name) {
		this.expansion = expansion;
		this.messageKey = String.format("%d-%s", expansion.ordinal() + 1, shortMessageKey);
		this.rpcImageKey = rpcImageKey != null ? rpcImageKey : expansion.rpcImageKey;
		this.name = name;
	}

	@Nonnull
	public static Raid getRaid(@Nonnull String messageKey) {
		return messageKeyMap.get(messageKey);
	}

	static {
		Raid[] dungeons = new Raid[] {
				new Raid(BattleForAzeroth, "ul", "raid-bfa-uldir", "Uldir")
		};

		for (Raid dungeon : dungeons) {
			messageKeyMap.put(dungeon.messageKey, dungeon);
		}
	}
}