package pl.shockah.wowdiscordrpc.bin;

import javax.annotation.Nonnull;

public interface BinaryEncoder<T> {
	@Nonnull
	BitBuffer encode(@Nonnull T data);
}