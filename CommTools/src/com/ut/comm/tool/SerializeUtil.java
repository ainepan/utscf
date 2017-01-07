package com.ut.comm.tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class SerializeUtil {
	/**
	 * 序列化
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String serializeStr(Object object) {
		byte[] bytes = serialize(object);
		return new sun.misc.BASE64Encoder().encodeBuffer(bytes);
	}

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object unSerialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object unSerializeStr(String object) throws IOException {
		byte[] bytes = new sun.misc.BASE64Decoder().decodeBuffer(object);
		return unSerialize(bytes);
	}

	public static String convertByte2String(byte[] object) throws IOException {
		return new sun.misc.BASE64Encoder().encodeBuffer(object);
	}
	
	public static byte[] convertString2Byte(String object) throws IOException {
		return new sun.misc.BASE64Decoder().decodeBuffer(object);
	}
}
