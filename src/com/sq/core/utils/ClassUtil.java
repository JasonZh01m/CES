package com.sq.core.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class ClassUtil {
	private static String OBJ_STR = "Object";

	public static List<Object> loop(List<Object> list, File folder,
			String packageName, String index) throws Exception {
		File[] files = folder.listFiles();
		if ((files != null) && (files.length > 0)) {
			for (int fileIndex = 0; fileIndex < files.length; fileIndex++) {
				File file = files[fileIndex];
				if (file.isDirectory()) {
					loop(list, file, packageName + file.getName() + ".", index);
				} else {
					Object obj = listMethodNames(file.getName(), packageName,
							index);
					if (obj != null) {
						list.add(obj);
					}
				}
			}
		}
		return list;
	}

	public static Object listMethodNames(String filename, String packageName,
			String index) {
		Object obj = null;
		String name = filename.replace(".class", "").replace(".java", "");

		String className = packageName + name;
		try {
			if (className.indexOf(index) > 0)
				obj = Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static Object getFieldValue(Object obj, String fieldName)
			throws Exception {
		Class cls = obj.getClass();
		Object o = null;
		while (!OBJ_STR.equals(cls.getSimpleName())) {
			Field[] fields = cls.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if (fieldName.equals(field.getName())) {
					field.setAccessible(true);
					o = field.get(obj);
					break;
				}
			}
			cls = cls.getSuperclass();
		}
		return o;
	}

	public static String getObjectFieldsHash(Object obj) throws Exception {
		// Modified By JasonZh 0728
		// String Hash = hash(JSON.toJSONString(obj, new SerializerFeature[] {
		// SerializerFeature.DisableCircularReferenceDetect }));
		Long Hash = hash(JSON
				.toJSONString(
						obj,
						new SerializerFeature[] { SerializerFeature.DisableCircularReferenceDetect }));
		return Hash + "";
	}

	public static Long hash(String key) {
		ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
		int seed = 305441741;
		ByteOrder byteOrder = buf.order();
		buf.order(ByteOrder.LITTLE_ENDIAN);
		long m = -4132994306676758123L;
		int r = 47;
		long h = seed ^ buf.remaining() * m;

		while (buf.remaining() >= 8) {
			long k = buf.getLong();
			k *= m;
			k ^= k >>> r;
			k *= m;
			h ^= k;
			h *= m;
		}
		if (buf.remaining() > 0) {
			ByteBuffer finish = ByteBuffer.allocate(8).order(
					ByteOrder.LITTLE_ENDIAN);

			finish.put(buf).rewind();
			h ^= finish.getLong();
			h *= m;
		}
		h ^= h >>> r;
		h *= m;
		h ^= h >>> r;
		buf.order(byteOrder);
		return Long.valueOf(h);
	}
}
