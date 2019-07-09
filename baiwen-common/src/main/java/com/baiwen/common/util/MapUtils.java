package com.baiwen.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
@Slf4j
public class MapUtils {


    /**
     * 把一个对象转换成为一个Map，其中map的key是属性名,value是属性值
     * <p>
     * 例如： 一个bean的属性有 id 和 name两个属性，对应属性值分别是 100 和 zhangsan 最终转换的结果就是
     * id=100,name="zhangsan"的Map
     * </p>
     *
     * @param <Bean>
     * @param bean
     * @return
     */
    public static <Bean> Map<String, Object> toMap(Bean bean) {
        if (bean == null) {
            return null;
        }

        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        PropertyDescriptor[] psArray = propertyUtilsBean.getPropertyDescriptors(bean.getClass());
        Map<String, Object> map = new HashMap<String, Object>();
        for (PropertyDescriptor ps : psArray) {
            String property = ps.getName();
            // 跳过本次循环，不对class属性做map
            if ("class".equals(property)) {
                continue;
            }
            try {
                Object o = propertyUtilsBean.getProperty(bean, property);
                if (o != null)
                    map.put(property, o);
            } catch (Exception e) {
                log.error("toMap==获取数据域的值出错", e);
            }
        }
        return map;
    }

    /**
     * 把一个对象的list 转换成为一个 Map<String,Object>的list。其中map的key是属性名,value是属性值
     * <p>
     * 例如：User对象有两个属性id和name, beanList有10个User对象，那么调用此方法返回的结果为List<Map>，
     * 长度为10,且每个Map对象包含两个key：id,name, value为对应属性的值.
     * </p>
     *
     * @param
     * @param beanList
     *            需要进行转换的对象列表
     * @return 返回List对象
     */
    public static <V> List<Map<String, Object>> toMapList(List<V> beanList) {
        if (beanList == null || beanList.size() < 1) {
            return null;
        }
        // 保存结果
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (V bean : beanList) {
            result.add(toMap(bean));
        }
        return result;

    }



    /**
     * 把一个对象List按某个属性转换成Map, key为属性值，value为对象。
     * <p>
     * 例如： convertToMap(List<User> values, "name")返回由name属性的值为Key,User对象为值的Map
     * </p>
     *
     * @param <K>
     * @param <V>
     * @param values
     *            对象列表
     * @param propertyName
     *            属性
     * @return 返回由propertyName值作为key组成的Map
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> toMap(List<V> values, String propertyName) {
        Map<K, V> result = new HashMap<K, V>();
        if (values == null) {
            return result;
        }
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        for (V v : values) {
            try {

                K k = (K) propertyUtilsBean.getProperty(v, propertyName);
                if (k != null) {
                    result.put(k, v);
                }
            } catch (Exception e) {
                log.error("toMap2===获取数据域的值出错", e);
            }
        }
        return result;
    }

    /**
     * 把一个对象List按某个属性转换成Map, key为属性值，value为由相应属性值对应的对象组成的List。
     * <p>
     * 例如： 调用convertToMapAndList（List<User> value,
     * "name"）返回结果为由User对象的name属性值作为key的Map, 如果name属性 相同则把它们组成List.
     * </p>
     *
     * @param <K>
     * @param <V>
     * @param values
     *            对象列表
     * @param propertyName
     *            属性
     * @return 返回由propertyName值作为key的Map,相同key的对象组成List.
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, List<V>> toMapAndList(List<V> values, String propertyName) {
        Map<K, List<V>> result = new HashMap<K, List<V>>();
        if (values == null) {
            return result;
        }
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        for (V v : values) {
            try {
                K k = (K) propertyUtilsBean.getProperty(v, propertyName);
                if (k != null) {
                    if (!result.containsKey(k)) {
                        result.put(k, new ArrayList<V>());
                    }
                    result.get(k).add(v);
                }
            } catch (Exception e) {
                log.error("toMapAndList===获取数据域的值出错", e);
            }
        }
        return result;
    }

    /**
     * 把一个对象List按某个属性转换成Map, key为属性值，value为由相应属性值对应的对象组成的List,并且可以按key进行排序。
     * <p>
     * 例如： 调用convertToMapAndList（List<User> value,
     * "name"）返回结果为由User对象的name属性值作为key的Map, 如果name属性 相同则把它们组成List.
     * </p>
     *
     * @param <K>
     * @param <V>
     * @param values
     *            对象列表
     * @param propertyName
     *            属性
     * @param sorted
     *            是否排序
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, List<V>> toMapAndList(List<V> values, String propertyName, boolean sorted) {
        Map<K, List<V>> result = sorted ? new TreeMap<K, List<V>>() : new HashMap<K, List<V>>();
        if (values == null) {
            return result;
        }
        for (V v : values) {
            String methodName = getGetMethodName(propertyName);
            try {
                Method method = v.getClass().getMethod(methodName, new Class<?>[] {});
                K k = (K) method.invoke(v, new Object[] {});
                if (k != null) {
                    if (!result.containsKey(k)) {
                        result.put(k, new ArrayList<V>());
                    }
                    result.get(k).add(v);
                }
            } catch (Exception e) {
                log.error("toMapAndList2===获取数据域的值出错", e);
            }
        }
        return result;
    }

    /**
     * 把一个对象List按两个属性转换成Map,key为propertyName1, 值为由propertyName2作为key的Map。
     * <p>
     * 例如： 调用convertToMutilMap（List<User> values, "id", "name"）返回由id作为key的Map,
     * 值为由name作为key的Map.
     * </p>
     *
     * @param <K1>
     * @param <K2>
     * @param <V>
     * @param values
     *            对象列表
     * @param propertyName1
     *            属性１
     * @param propertyName2
     *            属性２
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K1, K2, V> Map<K1, Map<K2, V>> toMutilMap(List<V> values, String propertyName1,
                                                             String propertyName2) {
        Map<K1, Map<K2, V>> result = new HashMap<K1, Map<K2, V>>();
        if (values == null) {
            return result;
        }
        for (V v : values) {
            String methodName1 = getGetMethodName(propertyName1);
            String methodName2 = getGetMethodName(propertyName2);
            try {
                Method method1 = v.getClass().getMethod(methodName1, new Class<?>[] {});
                K1 k1 = (K1) method1.invoke(v, new Object[] {});
                if (k1 != null) {
                    if (!result.containsKey(k1)) {
                        result.put(k1, new HashMap<K2, V>());
                    }
                    Method method2 = v.getClass().getMethod(methodName2, new Class<?>[] {});
                    K2 k2 = (K2) method2.invoke(v, new Object[] {});
                    if (k2 != null) {
                        if (!result.get(k1).containsKey(k2)) {
                            result.get(k1).put(k2, v);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("toMutilMap===获取数据域的值出错", e);
            }
        }
        return result;
    }




    private static String getGetMethodName(String propertyName) {
        return "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }

	/*
	 * private static String getSetMethodName(String propertyName) { return "set" +
	 * propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1); }
	 */


    /**
     * 对象转map，属性对象一起转成map
     * @param o
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> T beanToMap(T o) {
        if (o == null) return null;
        if (o instanceof Map) {
            Map tempMap = (Map) o;
            for (Object key : tempMap.keySet()) {
                tempMap.put(key, beanToMap(tempMap.get(key)));
            }
            return (T) tempMap;
        } else if (o instanceof Collection) {
            Collection tempColl = (Collection) o;
            Object[] os = tempColl.toArray();
            tempColl.clear();
            for (int i = 0; i < os.length; i++) {
                tempColl.add(beanToMap(os[i]));
            }
            return (T) tempColl;

        } else if (o instanceof String || o instanceof Byte || o instanceof Short || o instanceof Integer
                || o instanceof Long || o instanceof Float || o instanceof Double || o instanceof Boolean
                || o instanceof Character || o instanceof Date || o instanceof Calendar || o instanceof BigInteger
                || o instanceof BigDecimal) {
            return o;

        } else if (o.getClass().isArray()) {

            Object[] os = (Object[]) o;
            for (int i = 0; i < os.length; i++) {
                os[i] = beanToMap(os[i]);
            }
            return (T) os;
        } else {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] psArray = propertyUtilsBean.getPropertyDescriptors(o.getClass());
            Map<String, Object> map = new HashMap<String, Object>();
            for (PropertyDescriptor ps : psArray) {
                String property = ps.getName();
                // 跳过本次循环，不对class属性做map
                if ("class".equals(property)) {
                    continue;
                }
                try {
                    Object tempo = propertyUtilsBean.getProperty(o, property);

                    if (tempo != null)
                        map.put(property, beanToMap(tempo));
                } catch (Exception e) {
                    log.error("toMap==获取数据域的值出错", e);
                }
            }
            return (T) map;
        }
    }

    /**
     * map 转换成对象,map 为null，返回 null
     * @param <T>
     *
     * @param type
     * @param map
     * @return
     */
    public static <T> Object toObject(Class<T> type, Map<String, Object> map) {

        if(map == null) return null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            Object obj = type.newInstance();

            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();

                if (map.containsKey(propertyName)) {

                    Object value = map.get(propertyName);

                    Object[] args = new Object[1];
                    args[0] = value;

                    descriptor.getWriteMethod().invoke(obj, args);
                }
            }
            return obj;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 获取map中指定key的值，并把该值转换成String,map 为空返回空字符串
     * @param map
     * @param key
     * @return
     */
    public static String getString(Map<String, Object> map, String key) {
        if(map == null) return "";
        Object value = map.get(key);
        if (!StringUtils.isEmpty(value)) {
            return String.valueOf(value);
        }
        return "";
    }

    /**
     * 获取map中指定key的值，并把该值转换成Long,map 为空返回0l
     * @param map
     * @param key
     * @return
     */
    public static Long getLong(Map<String, Object> map, String key) {
        if(map == null) return 0l;
        Object value = map.get(key);
        if (StringUtils.isEmpty(value)) {
            return 0l;
        }

        if (value instanceof Long) {
            Long longValue = (Long) value;
            return longValue;
        }
        if (value instanceof String && ((String) value).indexOf(".") <= -1) {
            return Long.parseLong(String.valueOf(value));
        }
        return 0l;
    }

    /**
     * 获取map中指定key的值，并把该值转换成Integer,map 为空返回0
     * @param map
     * @param key
     * @return
     */
    public static Integer getInteger(Map<String, Object> map, String key) {
        if(map == null) return 0;
        Object value = map.get(key);
        if (value == null) {
            return 0;
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).intValue();
        }
        if (value instanceof Double) {
            Double doubleValue = (Double) value;
            return doubleValue.intValue();
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof Long) {
            Long longValue = (Long) value;
            return longValue.intValue();
        }
        if (value instanceof String && ((String) value).indexOf(".") <= -1) {
            return Integer.parseInt(String.valueOf(value));
        }
        return 0;
    }

    /**
     * 获取map中指定key的值，并把该值转换成Double,map 为空返回0.0
     * @param map
     * @param key
     * @return
     */
    public static Double getDouble(Map<String, Object> map, String key) {
        if(map == null) return 0.0;

        Object value = map.get(key);
        if (value == null) {
            return 0.0;
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).doubleValue();
        }
        if (value instanceof Double) {
            return (Double) value;
        }
        if (value instanceof Integer) {
            return ((Integer) value).doubleValue();
        }
        return 0.0;
    }


}
