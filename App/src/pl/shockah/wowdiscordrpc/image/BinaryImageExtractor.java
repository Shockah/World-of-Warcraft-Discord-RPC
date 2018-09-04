package pl.shockah.wowdiscordrpc.image;

import javax.annotation.Nonnull;

import javafx.scene.image.Image;

public interface BinaryImageExtractor {
	@Nonnull
	Image extract(@Nonnull Image fullImage);
}