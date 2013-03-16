package com.towne.framework.core.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class ClassUtils {
	private static final Log log = LogFactory.getLog(ClassUtils.class);
	@SuppressWarnings("rawtypes")
	private static final Class[] NO_CLASSES = new Class[0];
	@SuppressWarnings("rawtypes")
	private static final Class[] OBJECT = { Object.class };
	private static final Method OBJECT_EQUALS;
	@SuppressWarnings("rawtypes")
	private static final Class[] NO_PARAM = new Class[0];
	private static final Method OBJECT_HASHCODE;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean overridesEquals(Class clazz) {
		Method equals;
		try {
			equals = clazz.getMethod("equals", OBJECT);
		} catch (NoSuchMethodException nsme) {
			return false;
		}

		return (!(OBJECT_EQUALS.equals(equals)));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean overridesHashCode(Class clazz) {
		Method hashCode;
		try {
			hashCode = clazz.getMethod("hashCode", NO_PARAM);
		} catch (NoSuchMethodException nsme) {
			return false;
		}

		return (!(OBJECT_HASHCODE.equals(hashCode)));
	}

	@SuppressWarnings("rawtypes")
	public static Class forName(String name) throws ClassNotFoundException {
		return forName(name, getDefaultClassLoader());
	}

	@SuppressWarnings("rawtypes")
	public static Class forName(String name, ClassLoader contextClassLoader)
			throws ClassNotFoundException {
		if (contextClassLoader != null) {
			return contextClassLoader.loadClass(name);
		}
		return Class.forName(name);
	}

	@SuppressWarnings("rawtypes")
	public static boolean isPublic(Class clazz, Member member) {
		return ((Modifier.isPublic(member.getModifiers())) && (Modifier
				.isPublic(clazz.getModifiers())));
	}

	@SuppressWarnings("rawtypes")
	public static Object getConstantValue(String name) {
		Class clazz;
		try {
			clazz = forName(StringUtils.qualifier(name));
		} catch (ClassNotFoundException cnfe) {
			return null;
		}
		try {
			return clazz.getField(StringUtils.unqualify(name)).get(null);
		} catch (Exception e) {
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Constructor getDefaultConstructor(Class clazz)
			throws SecurityException, NoSuchMethodException {
		if (isAbstractClass(clazz))
			return null;
		Constructor constructor = clazz.getDeclaredConstructor(NO_CLASSES);
		if (!(isPublic(clazz, constructor))) {
			constructor.setAccessible(true);
		}
		return constructor;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isAbstractClass(Class clazz) {
		int modifier = clazz.getModifiers();
		return ((Modifier.isAbstract(modifier)) || (Modifier
				.isInterface(modifier)));
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Method getMethod(Class clazz, Method method) {
		try {
			return clazz
					.getMethod(method.getName(), method.getParameterTypes());
		} catch (Exception e) {
		}
		return null;
	}

	public static Object newInstance(String classname) {
		try {
			return forName(classname).newInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static Object newInstance(Class type) {
		try {
			return type.newInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static void populate(Object obj, Map map) {
		Set set = map.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			String name = (String) e.getKey();
			setProperty(obj, name, e.getValue());
		}
	}

	public static Object getProperty(Object entity, String name) {
		try {
			return PropertyUtils.getProperty(entity, name);
		} catch (Exception ex) {
			log.error(ex);
		}
		return null;
	}

	public static Object[] getProperties(Object entity, String[] names) {
		Object[] a = new Object[names.length];
		for (int i = 0; i < a.length; ++i) {
			a[i] = getProperty(entity, names[i]);
		}
		return a;
	}

	@SuppressWarnings("rawtypes")
	public static Object getProperties(Object entity, String[] names, Class type) {
		Object array = Array.newInstance(type, names.length);
		for (int i = 0; i < names.length; ++i) {
			Object value = getProperty(entity, names[i]);
			Array.set(array, i, value);
		}
		return array;
	}

	public static void setProperty(Object entity, String name, Object value) {
		try {
			PropertyUtils.setProperty(entity, name, value);
		} catch (Exception ex) {
			log.error(ex);
		}
	}

	@SuppressWarnings("rawtypes")
	public static String getPureName(Class clazz) {
		String name = clazz.getName();
		int i = name.lastIndexOf(".");
		if (i > 0) {
			return name.substring(i + 1);
		}
		return name;
	}

	@SuppressWarnings("rawtypes")
	public static String getSimpleName(Class clazz) {
		try {
			Method m = Class.class.getMethod("getSimpleName", new Class[0]);
			return ((String) m.invoke(clazz, (Object[]) null));
		} catch (Exception ex) {
		}
		return getPureName(clazz);
	}

	@SuppressWarnings("rawtypes")
	public static Class getPropertyType(Object bean, String name) {
		try {
			return PropertyUtils.getPropertyType(bean, name);
		} catch (NoSuchMethodException ex) {
			return null;
		} catch (InvocationTargetException ex) {
			return null;
		} catch (IllegalAccessException ex) {
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static void setProperty(Object target, PropertyDescriptor prop,
			Object value) throws Exception {
		Method setter = prop.getWriteMethod();
		if (setter == null) {
			return;
		}

		Class[] params = setter.getParameterTypes();

		if ((value != null) && (value instanceof java.util.Date)) {
			if (params[0].getName().equals("java.sql.Date")) {
				value = new java.sql.Date(((java.util.Date) value).getTime());
			} else if (params[0].getName().equals("java.sql.Time")) {
				value = new Time(((java.util.Date) value).getTime());
			} else if (params[0].getName().equals("java.sql.Timestamp")) {
				value = new Timestamp(((java.util.Date) value).getTime());
			}

		}

		if (isCompatibleType(value, params[0]))
			setter.invoke(target, new Object[] { value });
		else
			throw new Exception("Cannot set " + prop.getName()
					+ ": incompatible types.");
	}

	@SuppressWarnings("rawtypes")
	public static boolean isCompatibleType(Object value, Class type) {
		if ((value == null) || (type.isInstance(value)))
			return true;
		if ((type.equals(Integer.TYPE)) && (Integer.class.isInstance(value))) {
			return true;
		}
		if ((type.equals(Long.TYPE)) && (Long.class.isInstance(value)))
			return true;
		if ((type.equals(Double.TYPE)) && (Double.class.isInstance(value))) {
			return true;
		}
		if ((type.equals(Float.TYPE)) && (Float.class.isInstance(value)))
			return true;
		if ((type.equals(Short.TYPE)) && (Short.class.isInstance(value)))
			return true;
		if ((type.equals(Byte.TYPE)) && (Byte.class.isInstance(value)))
			return true;
		if ((type.equals(Character.TYPE))
				&& (Character.class.isInstance(value))) {
			return true;
		}

		return ((type.equals(Boolean.TYPE)) && (Boolean.class.isInstance(value)));
	}

	@SuppressWarnings("rawtypes")
	public static Object createObject(Map properties, Class[] interfaceClasses) {
		return Proxy.newProxyInstance(interfaceClasses[0].getClassLoader(),
				interfaceClasses, new BeanInvocationHandler(properties));
	}

	@SuppressWarnings("rawtypes")
	public static Object createObject(Map properties, Class interfaceClass) {
		return createObject(properties, new Class[] { interfaceClass });
	}

	public static boolean isPresent(String className) {
		return isPresent(className, getDefaultClassLoader());
	}

	public static boolean isPresent(String className, ClassLoader classLoader) {
		try {
			forName(className, classLoader);
			return true;
		} catch (Throwable ex) {
			if (log.isDebugEnabled()) {
				log.debug("Class [" + className
						+ "] or one of its dependencies is not present: " + ex);
			}
		}
		return false;
	}

	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
			log
					.debug(
							"Cannot access thread context ClassLoader - falling back to system class loader",
							ex);
		}

		if (cl == null) {
			cl = ClassUtils.class.getClassLoader();
		}
		return cl;
	}

	public static final ClassLoader createPackageClassLoader(String[] packages) {
		return new PackageClassLoader(packages);
	}

	static {
		Method eq;
		Method hash;
		try {
			eq = Object.class.getMethod("equals", OBJECT);
			hash = Object.class.getMethod("hashCode", NO_PARAM);
		} catch (Exception e) {
			throw new RuntimeException(
					"Could not find Object.equals() or Object.hashCode()", e);
		}

		OBJECT_EQUALS = eq;
		OBJECT_HASHCODE = hash;
	}

	public static class PackageClassLoader extends ClassLoader {
		private String[] imports;

		public PackageClassLoader() {
		}

		public PackageClassLoader(String imports) {
			setImports(new String[] { imports });
		}

		public PackageClassLoader(String[] imports) {
			setImports(imports);
		}

		public void setImports(String[] imports) {
			this.imports = imports;
			for (int i = 0; i < imports.length; ++i)
				if (imports[i].endsWith(".*"))
					this.imports[i] = imports[i].substring(0, imports[i]
							.length() - 1);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		protected Class findClass(String name) throws ClassNotFoundException {
			if (name.indexOf(".") != -1) {
				throw new ClassNotFoundException(name);
			}
			for (int i = 0; i < this.imports.length; ++i) {
				Class c;
				if (this.imports[i].endsWith("." + name))
					c = forName(this.imports[i]);
				else {
					c = forName(this.imports[i] + name);
				}
				if (c != null) {
					return c;
				}
			}
			throw new ClassNotFoundException(name);
		}

		@SuppressWarnings("rawtypes")
		private Class forName(String name) {
			try {
				return ClassUtils.forName(name);
			} catch (ClassNotFoundException ex) {
			}
			return null;
		}
	}

	private static class BeanInvocationHandler implements InvocationHandler {
		@SuppressWarnings("rawtypes")
		private final Map map;

		@SuppressWarnings("rawtypes")
		public BeanInvocationHandler(Map map) {
			this.map = map;
		}

		@SuppressWarnings("unchecked")
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			String methodName = method.getName();
			if (methodName.startsWith("get")) {
				return this.map.get(getPropertyName(methodName));
			}
			if (methodName.startsWith("is")) {
				Object value = this.map.get(getPropertyName(methodName));
				if ((value != null) && (Boolean.class.isInstance(value)))
					return value;
			} else {
				if (methodName.startsWith("set")) {
					if (args.length == 1) {
						this.map.put(getPropertyName(methodName), args[0]);
					}
					return null;
				}
				if (methodName.equals("equals"))
					return ((proxy == args[0]) ? Boolean.TRUE : Boolean.FALSE);
				if (methodName.equals("hashCode"))
					return new Integer(this.map.hashCode());
				if (methodName.equals("toString")) {
					return this.map.toString();
				}

			}

			throw new UnsupportedOperationException(methodName);
		}

		protected static String getPropertyName(String methodName) {
			String name = methodName.substring(3);
			if ((name == null) || (name.length() == 0)) {
				return name;
			}
			return name.substring(0, 1).toLowerCase() + name.substring(1);
		}
	}
}