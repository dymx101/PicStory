package com.towne.framework.core.utils;

import java.awt.Color;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BeanUtils {
	private static final Log log = LogFactory.getLog(BeanUtils.class);
	public static final String DATE_FORMAT = "MM-dd-yyyy";
	private static DateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");

	@SuppressWarnings("rawtypes")
	public static void setProperties(Object bean, Map properties) {
		Iterator iter;
		try {
			for (iter = properties.keySet().iterator(); iter.hasNext();) {
				String propName = (String) iter.next();
				try {
					PropertyDescriptor descriptor = new PropertyDescriptor(
							propName, bean.getClass());

					Class propertyType = descriptor.getPropertyType();
					Object value = decode(propertyType,
							(String) properties.get(propName));

					descriptor.getWriteMethod().invoke(bean,
							new Object[] { value });
				} catch (IntrospectionException ie) {
				} catch (InvocationTargetException ite) {
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public static Map getProperties(Object bean) {
		Map properties = new HashMap();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] descriptors = beanInfo
					.getPropertyDescriptors();
			String[] names = new String[descriptors.length];
			for (int i = 0; i < names.length; ++i) {
				String name = descriptors[i].getName();
				Class type = descriptors[i].getPropertyType();
				Object value = descriptors[i].getReadMethod().invoke(bean,
						(Object[]) null);

				properties.put(name, encode(value));
			}
		} catch (Exception e) {
			log.error(e);
		}
		return properties;
	}

	@SuppressWarnings("rawtypes")
	public static PropertyDescriptor[] getPropertyDescriptors(Class beanClass)
			throws IntrospectionException {
		try {
			BeanInfo beanInfo = (BeanInfo) ClassUtils.forName(
					beanClass.getName() + "BeanInfo").newInstance();

			return beanInfo.getPropertyDescriptors();
		} catch (Exception e) {
		}
		return Introspector.getBeanInfo(beanClass).getPropertyDescriptors();
	}

	@SuppressWarnings("rawtypes")
	public static String encode(Object value) {
		if (value instanceof String)
			return ((String) value);
		if ((value instanceof Boolean) || (value instanceof Integer)
				|| (value instanceof Long) || (value instanceof Float)
				|| (value instanceof Double)) {
			return value.toString();
		}
		if (value instanceof Date)
			;
		try {
			return dateFormatter.format((Date) value);
		} catch (Exception ex) {
			if (value instanceof Color) {
				Color color = (Color) value;
				return color.getRed() + "," + color.getGreen() + ","
						+ color.getBlue();
			}

			if (value instanceof Class)
				return ((Class) value).getName();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static Object decode(Class type, String value) throws Exception {

		if (StringUtils.isNotBlank(value)) {
			if (type.getName().equals("java.lang.String"))
				return value;
			if (type.getName().equals("java.lang.Boolean"))
				return Boolean.valueOf(value);
			if (type.getName().equals("java.lang.Integer"))
				return Integer.valueOf(value);
			if (type.getName().equals("java.lang.Long"))
				return Long.valueOf(value);
			if (type.getName().equals("java.lang.Float"))
				return Float.valueOf(value);
			if (type.getName().equals("java.lang.Double"))
				return Double.valueOf(value);
			if (type.getName().equals("java.util.Date"))
				return dateFormatter.parse(value);
			if (type.getName().equals("java.awt.Color")) {
				StringTokenizer tokens = new StringTokenizer(value, ",");
				int red = Integer.parseInt(tokens.nextToken());
				int green = Integer.parseInt(tokens.nextToken());
				int blue = Integer.parseInt(tokens.nextToken());
				return new Color(red, green, blue);
			}
			if (type.getName().equals("java.lang.Class"))
				return ClassUtils.forName(value);
			else
				return null;
		} else {
			return null;
		}
	}
}
