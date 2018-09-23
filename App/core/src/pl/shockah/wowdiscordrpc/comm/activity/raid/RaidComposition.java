package pl.shockah.wowdiscordrpc.comm.activity.raid;

import java.util.Map;

import javax.annotation.Nonnull;

import pl.shockah.unicorn.collection.Array1D;
import pl.shockah.unicorn.collection.MutableArray1D;
import pl.shockah.wowdiscordrpc.comm.CharacterRole;

public class RaidComposition {
	@Nonnull
	private final Array1D<Integer> counts;

	public RaidComposition(@Nonnull Map<CharacterRole, Integer> counts) {
		MutableArray1D<Integer> arrayCounts = new MutableArray1D<>(Integer.class, CharacterRole.values().length);
		for (Map.Entry<CharacterRole, Integer> entry : counts.entrySet()) {
			arrayCounts.set(entry.getKey().ordinal(), entry.getValue());
		}
		this.counts = arrayCounts;
	}

	public int getCount(@Nonnull CharacterRole role) {
		return counts.get(role.ordinal());
	}
}