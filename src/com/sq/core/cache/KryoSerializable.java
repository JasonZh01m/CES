package com.sq.core.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.sq.core.cache.aop.CacheAnnotationScaner;
import com.sq.core.utils.ClassUtil;

public class KryoSerializable {
	private static final Kryo kryo = new Kryo();

	static {
		initRegistration(kryo);
		kryo.setReferences(false);
	}

	public static void registerClass(Class<? extends Object> cls) {
		int clsId = 0;
		if ("com.gdret.core.sso.vo.SSOSession".equals(cls.getName()))
			clsId = 1;
		else if ("com.gdret.rms.model.Permission".equals(cls.getName()))
			clsId = 2;
		else {
			clsId = hash(cls.getName());
		}
		Registration r = kryo.register(cls, clsId);
		System.out.println("Registration cls:" + cls.getName() + " clsId:"
				+ r.getId());
	}

	private static void initRegistration(Kryo kryo) {
		String path = null;
		ClassLoader classLoager = Thread.currentThread()
				.getContextClassLoader();
		if (classLoager == null) {
			classLoager = CacheAnnotationScaner.class.getClassLoader();
		}
		path = classLoager.getResource("").getPath();

		File root = new File(path);
		List list = new ArrayList();
		try {
			list = ClassUtil.loop(list, root, "", ".model.");
			list = ClassUtil.loop(list, root, "", ".vo.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		String jarPath = path.replaceAll("classes", "lib");
		root = new File(jarPath);
		try {
			File[] files = root.listFiles();
			if ((files != null) && (files.length > 0))
				for (int fileIndex = 0; fileIndex < files.length; fileIndex++) {
					File file = files[fileIndex];
					String jarName = file.getName();
					if ((jarName.startsWith("sso"))
							&& (jarName.endsWith("jar"))) {
						JarFile jarFile = new JarFile(file.getPath());
						Enumeration entrys = jarFile.entries();
						while (entrys.hasMoreElements()) {
							JarEntry jarEntry = (JarEntry) entrys.nextElement();
							String entryName = jarEntry.getName();
							if ((entryName.indexOf(".class") > -1)
									&& ((entryName.indexOf("model") > -1) || (entryName
											.indexOf("vo") > -1))) {
								String className = entryName.replaceAll("/",
										".").replace(".class", "");
								Object o = Class.forName(className)
										.newInstance();
								list.add(o);
							}
						}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ((list != null) && (list.size() > 0))
			for (int i = 0; i < list.size(); i++) {
				Class cls = list.get(i).getClass();
				registerClass(cls);
			}
	}

	public static byte[] serializable(Object o) {
		Output output = null;
		byte[] bs = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			output = new Output(baos);
			kryo.writeObject(output, o);
			output.flush();
			bs = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();

			if (output != null) {
				output.close();
			}
			if (baos != null)
				try {
					baos.close();
				} catch (IOException ie) {
					ie.printStackTrace();
				}
		} finally {
			if (output != null) {
				output.close();
			}
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bs;
	}

	public static Object deserialize(byte[] ob, Class cls) {
		if ((ob == null) || (ob.length == 0))
			return null;
		Input ois = null;
		Object o = null;
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(ob);
			ois = new Input(bais);
			o = kryo.readObject(ois, cls);
		} catch (Exception e) {
			e.printStackTrace();

			if (ois != null) {
				ois.close();
			}
			if (bais != null)
				try {
					bais.close();
				} catch (IOException ie) {
					ie.printStackTrace();
				}
		} finally {
			if (ois != null) {
				ois.close();
			}
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return o;
	}

	private static int hash(String data) {
		int p = 16777619;
		int hash = -2128831035;
		for (int i = 0; i < data.length(); i++)
			hash = (hash ^ data.charAt(i)) * 16777619;
		hash += (hash << 13);
		hash ^= hash >> 7;
		hash += (hash << 3);
		hash ^= hash >> 17;
		hash += (hash << 5);

		hash &= 2147483644;
		return hash;
	}
}
