package pl.shockah.wowdiscordrpc.bin;

import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BitBufferTests {
	@Test
	void singleBitTest() {
		BitBuffer buffer = new BitBuffer();
		assertEquals(0, buffer.getPosition());
		assertEquals(0, buffer.getSize());
		assertEquals(0, buffer.getAvailable());

		buffer.write(false);
		assertEquals(1, buffer.getPosition());
		assertEquals(1, buffer.getSize());
		assertEquals(0, buffer.getAvailable());

		buffer.seekTo(0);
		assertEquals(0, buffer.getPosition());
		assertEquals(1, buffer.getSize());
		assertEquals(1, buffer.getAvailable());

		assertFalse(buffer.read());
		assertEquals(1, buffer.getPosition());
		assertEquals(1, buffer.getSize());
		assertEquals(0, buffer.getAvailable());

		buffer.clear();
		assertEquals(0, buffer.getPosition());
		assertEquals(0, buffer.getSize());
		assertEquals(0, buffer.getAvailable());

		buffer.write(true);
		assertEquals(1, buffer.getPosition());
		assertEquals(1, buffer.getSize());
		assertEquals(0, buffer.getAvailable());

		buffer.seekTo(0);
		assertEquals(0, buffer.getPosition());
		assertEquals(1, buffer.getSize());
		assertEquals(1, buffer.getAvailable());

		assertTrue(buffer.read());
		assertEquals(1, buffer.getPosition());
		assertEquals(1, buffer.getSize());
		assertEquals(0, buffer.getAvailable());
	}

	@Test
	void uintTest() {
		BitBuffer buffer = new BitBuffer();
		buffer.writeUInt(4, 13);
		assertEquals(4, buffer.getSize());

		buffer.seekTo(0);
		assertEquals(13, buffer.readUInt(4));

		buffer.clear();
		buffer.writeUInt(8, 127);
		buffer.writeUInt(8, 128);
		assertEquals(16, buffer.getSize());

		buffer.seekTo(0);
		assertEquals(127, buffer.readUInt(8));
		assertEquals(128, buffer.readUInt(8));

		buffer.clear();
		buffer.writeUInt(32, Integer.MAX_VALUE);
		assertEquals(32, buffer.getSize());

		buffer.seekTo(0);
		assertEquals(Integer.MAX_VALUE, buffer.readUInt(32));
	}

	@Test
	void stringTest() {
		Charset charset = Charset.defaultCharset();

		BitBuffer buffer = new BitBuffer();
		buffer.writeString(8, "Hello World!", charset);
		assertEquals(8 + 12 * 8, buffer.getSize());

		buffer.seekTo(0);
		assertEquals("Hello World!", buffer.readString(8, charset));
	}
}