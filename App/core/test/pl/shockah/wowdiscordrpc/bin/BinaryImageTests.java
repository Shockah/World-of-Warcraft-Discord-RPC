package pl.shockah.wowdiscordrpc.bin;

import org.junit.jupiter.api.Test;

import javafx.scene.image.Image;
import pl.shockah.wowdiscordrpc.image.BinaryImageHandler;
import pl.shockah.wowdiscordrpc.image.DefaultCachingBinaryImageHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinaryImageTests {
	@Test
	void singleBitTest() {
		BinaryImageHandler handler = new DefaultCachingBinaryImageHandler(0, 0, 0, 8, 8, 8);

		BitBuffer buffer = new BitBuffer();
		buffer.write(true);

		buffer.seekTo(0);
		Image image = handler.write(buffer);
		assertEquals(3 * 4, image.getWidth() * image.getHeight());

		buffer = handler.read(image);
		assertEquals(1, buffer.getSize());
		assertTrue(buffer.read());
	}
}