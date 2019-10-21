package com.gestankbratwurst.persistentblockapi.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.google.common.collect.Lists;

/**
 * Wrapper class for byte[] data
 * @author Gestankbratwurst
 *
 */
public class ByteStash {
	
	public static ByteStash ofBoxed(byte[] bytes) {
		ByteStash stash = new ByteStash();
		stash.add(bytes);
		return stash;
	}
	
	public ByteStash() {
		this.internalArray = Lists.newArrayList();
	}

	private final ArrayList<Byte> internalArray;
	
	/**
	 * Gets a new byte[] that is not backed by this class's
	 * internal storage.
	 * @return a new byte[]
	 */
	public byte[] getBytes() {
		int size = internalArray.size();
		byte[] data = new byte[size];
		for(int i = 0; i < size; i++) {
			data[i] = internalArray.get(i);
		}
		return data;
	}
	
	/**
	 * Appends a byte
	 * @param b the byte
	 */
	public void add(byte b) {
		this.internalArray.add(b);
	}
	
	/**
	 * Appends bytes from 0 to byte.length
	 * @param bytes
	 */
	public void add(byte[] bytes) {
		for (byte b : bytes) {
			this.add(b);
		}
	}
	
	/**
	 * Adds 4 bytes that represent i
	 * @param i
	 */
	public void add(int i) {
		byte[] data = new byte[] {(byte) ((i >> 24) & 0xff), (byte) ((i >> 16) & 0xff), (byte) ((i >> 8) & 0xff),(byte) ((i >> 0) & 0xff)};
		this.add(data);
	}

	/**
	 * Adds 8 bytes that represent l
	 * @param l
	 */
	public void add(long l) {
		byte[] data = new byte[] {(byte) ((l >> 56) & 0xff), (byte) ((l >> 48) & 0xff), (byte) ((l >> 40) & 0xff),(byte) ((l >> 32) & 0xff), (byte) ((l >> 24) & 0xff), (byte) ((l >> 16) & 0xff), (byte) ((l >> 8) & 0xff),(byte) ((l >> 0) & 0xff)};
		this.add(data);
	}
	
	/**
	 * Adds 8 bytes that represent d
	 * @param d
	 */
	public void add(double d) {
		ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
		buffer.putDouble(d);
		this.add(buffer.array());
	}

	/**
	 * Adds 4 bytes that represent f
	 * @param f
	 */
	public void add(float f) {
		ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES);
		buffer.putFloat(f);
		this.add(buffer.array());
	}
	
	/**
	 * Adds n bytes that represent this String
	 * with UTF_8 charset.
	 * @param data
	 * @return the number of added bytes.
	 */
	public int addString(String data) {
		byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
		this.add(bytes);
		return bytes.length;
	}
	
	/**
	 * Adds n bytes that represent this String
	 * with any valid charset.
	 * @param data
	 * @param charSet
	 * @return the number of added bytes.
	 */
	public int addString(String data, Charset charSet) {
		byte[] bytes = data.getBytes(charSet);
		this.add(bytes);
		return bytes.length;
	}
	
}
