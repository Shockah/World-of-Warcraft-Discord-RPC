package pl.shockah.wowdiscordrpc.bin;

import java.nio.charset.Charset;
import java.util.Arrays;

import javax.annotation.Nonnull;

import lombok.Getter;

public class BitBuffer {
	@Nonnull
	private byte[] buffer;

	@Getter
	private int size;

	@Getter
	private int position;

	public BitBuffer() {
		this(1024);
	}

	public BitBuffer(int initialCapacity) {
		this(new byte[(int)Math.ceil(initialCapacity / 8.0)], initialCapacity);
	}

	public BitBuffer(@Nonnull byte[] buffer, int size) {
		this.buffer = buffer;
		this.size = size;
	}

	private void verifyReadSize(int bits) {
		if (size - position < bits)
			throw new ArrayIndexOutOfBoundsException();
	}

	private void ensureWriteCapacity(int bits) {
		int newSize = buffer.length * 8;
		while (newSize - position < bits) {
			newSize *= 4;
		}
		if (buffer.length * 8 != newSize)
			buffer = Arrays.copyOf(buffer, (int)Math.ceil(newSize / 8.0));
	}

	public void seekTo(int position) {
		if (position > size)
			throw new ArrayIndexOutOfBoundsException();
		this.position = position;
	}

	public void seek(int offset) {
		seekTo(position + offset);
	}

	public int getAvailable() {
		return size - position;
	}

	public void clear() {
		size = 0;
		position = 0;
	}

	private boolean readInternal() {
		int byteIndex = position / 8;
		int bitIndex = position % 8;
		return ((buffer[byteIndex] >> bitIndex) & 1) != 0;
	}

	private void writeInternal(boolean bit) {
		int byteIndex = position / 8;
		int bitIndex = position % 8;
		if (bit)
			buffer[byteIndex] |= 1 << bitIndex;
		else
			buffer[byteIndex] &= ~(1 << bitIndex);
		position++;
	}

	public boolean read() {
		verifyReadSize(1);
		return readInternal();
	}

	public void write(boolean bit) {
		ensureWriteCapacity(1);
		writeInternal(bit);
	}

	public int readUInt(int bits) {
		verifyReadSize(bits);
		int value = 0;
		for (int i = 0; i < bits; i++) {
			if (readInternal())
				value |= 1 << i;
		}
		return value;
	}

	public void writeUInt(int bits, int value) {
		ensureWriteCapacity(bits);
		for (int i = 0; i < bits; i++) {
			writeInternal(((value >> i) & 1) != 0);
		}
	}

	@Nonnull
	public String readString(int lengthBits, @Nonnull Charset charset) {
		byte[] bytes = new byte[readUInt(lengthBits)];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte)readUInt(8);
		}
		return new String(bytes, charset);
	}

	public void writeString(int lengthBits, @Nonnull String value, @Nonnull Charset charset) {
		byte[] bytes = value.getBytes(charset);
		writeUInt(lengthBits, bytes.length);
		for (byte b : bytes) {
			writeUInt(8, b);
		}
	}
}