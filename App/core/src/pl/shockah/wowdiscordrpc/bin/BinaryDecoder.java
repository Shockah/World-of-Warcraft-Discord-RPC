package pl.shockah.wowdiscordrpc.bin;

import javax.annotation.Nonnull;

public interface BinaryDecoder<T> {
	@Nonnull
	T decode(@Nonnull BitBuffer buffer);
}