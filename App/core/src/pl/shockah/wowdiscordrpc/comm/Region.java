package pl.shockah.wowdiscordrpc.comm;

import javax.annotation.Nonnull;

import lombok.Getter;

public enum Region {
	US("US"),
	Korea("KR"),
	Europe("EU"),
	Taiwan("TW"),
	China("CH");

	@Nonnull
	@Getter
	public final String shortCode;

	Region(@Nonnull String shortCode) {
		this.shortCode = shortCode;
	}
}