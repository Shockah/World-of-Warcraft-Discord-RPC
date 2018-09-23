package pl.shockah.wowdiscordrpc.comm.activity.other;

import javax.annotation.Nonnull;

import lombok.Getter;

public enum Expansion {
	Vanilla(""),
	TheBurningCrusade(""),
	WrathOfTheLichKing(""),
	Cataclysm(""),
	MistsOfPandaria(""),
	WarlordsOfDraenor(""),
	Legion(""),
	BattleForAzeroth("");

	@Nonnull
	@Getter
	public final String rpcImageKey;

	Expansion(@Nonnull String rpcImageKey) {
		this.rpcImageKey = rpcImageKey;
	}
}