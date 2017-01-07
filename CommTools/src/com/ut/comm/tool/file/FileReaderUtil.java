package com.ut.comm.tool.file;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.ut.comm.tool.SerializeUtil;

public class FileReaderUtil {
	public static byte[] getByteFromRequest(InputStream iis) throws IOException {
		ByteArrayOutputStream o = new ByteArrayOutputStream(1024);
		try {
			int i = 1024;
			byte[] buf = new byte[i];

			while ((i = iis.read(buf, 0, i)) > 0) {
				o.write(buf, 0, i);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] data = o.toByteArray();
		o.close();
		return data;
	}

	public static void setByteToRequest(OutputStream outstr, byte[] data) throws IOException {
		outstr.write(data, 0, data.length);

		outstr.flush();
	}

	public static void writeFile(String filePath, Object content) throws IOException {
		FileOutputStream fos = null;
		FileChannel fc_out = null;
		try {
			fos = new FileOutputStream(filePath, true);
			fc_out = fos.getChannel();
			byte[] data = SerializeUtil.serialize(content);
			ByteBuffer buf = ByteBuffer.wrap(data);
			buf.put(data);
			buf.flip();
			fc_out.write(buf);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fc_out) {
				fc_out.close();
			}
			if (null != fos) {
				fos.close();
			}
		}
	}
}
