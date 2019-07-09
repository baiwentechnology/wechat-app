package com.baiwen.common.util;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtil {


    /**
     * 默认json格式化方式
     */
    public static final SerializerFeature[] DEFAULT_FORMAT = {
            //SerializerFeature.DisableCircularReferenceDetect,
            //SerializerFeature.WriteDateUseDateFormat,
            SerializerFeature.WriteEnumUsingToString,
            SerializerFeature.WriteNonStringKeyAsString,
            //SerializerFeature.QuoteFieldNames,
            SerializerFeature.SkipTransientField,
            SerializerFeature.WriteNullListAsEmpty,
            //SerializerFeature.SortField,
            //SerializerFeature.PrettyFormat
    };


    /**
     * 从json获取指定key的字符串
     *
     * @param
     *            json字符串
     * @param key
     *            字符串的key
     * @return 指定key的值
     */
    public static Object getObjectFromJSONObject(final String jsonStr,
                                                 final String key) {
        return JSON.parseObject(jsonStr).getString(key);
    }

    /**
     * 从json获取指定key的字符串
     *
     * @param
     *
     * @param key
     *            字符串的key
     * @return 指定key的值
     */
    public static String getStringFromJSONString(final String jsonStr,
                                                 final String key) {
        return String.valueOf(getObjectFromJSONObject(jsonStr, key));
    }

    /**
     * 将字符串转换成JSON对象
     *
     * @param
     *
     * @return 转换成的json对象
     */
    public static JSONObject getJSONFromString(final String jsonStr) {
        return JSON.parseObject(jsonStr);
    }

    /**
     * 将json字符串，转换成指定java bean
     *
     * @param jsonStr
     *            json串对象
     * @param beanClass
     *            指定的bean
     * @param <T>
     *            任意bean的类型
     * @return 转换后的java bean对象
     */
    public static <T> T toBean(String jsonStr, Class<T> beanClass) {
        return JSON.parseObject(jsonStr, beanClass);
    }

	/*public static <T> T toBean(String jsonStr, Class<T> beanClass) {
		JSONObject jo = JSON.parseObject(jsonStr);
		if(jo==null){
			jo = new JSONObject();
		}
		jo.put(JSON.DEFAULT_TYPE_KEY, beanClass.getName());
		return JSON.parseObject(jo.toJSONString(), beanClass);
	}*/

    /**
     * 根据指定的类型 将json字符串转换成对象
     *
     * @param
     *
     * @param typeOfT
     *            对象类型
     * @return
     *            对象
     */
    public static Object toBean(String jsonStr, Type typeOfT){
        return JSON.parseObject(jsonStr,typeOfT);
    }

    /**
     * 将json字符串 转换成指定的java bean 列表
     * @param jsonStr
     *            json串对象
     * @param beanClass
     *            指定的bean
     * @param <T>
     *            任意bean的类型
     * @return 转换后的 java bean 对象列表
     */
    public static <T> List<T>   toListBean(String jsonStr,Class<T> beanClass){
        List<T> listBean = JSON.parseArray(jsonStr, beanClass);
        return listBean;
    }

    /**
     * 从json获取指定key的字符串，将获得的字符串 转换成指定的java bean 列表
     * @param jsonStr
     *            json串对象
     * @param beanClass
     *            指定的bean
     * @param <T>
     *            任意bean的类型
     * @param key
     *            字符串的key
     * @return 转换后的 java bean 对象列表
     */
    public static <T> List<T> toListBean(String jsonStr,Class<T> beanClass,String key){
        String resultValeJson = getStringFromJSONString(jsonStr,key);
        return toListBean(resultValeJson, beanClass);
    }


    /**
     * 将 对象 转换成json字符串,默认格式
     * @param obj
     *            需要转换的java bean
     * @param <T>
     *            入参对象类型泛型
     * @return 对应的json字符串
     */
    public static <T> String toJson(T obj) {
        if(obj == null){
            return "{}";
        }
        return  StringUtils.replaceJson(JSON.toJSONString(obj, DEFAULT_FORMAT));
    }

    /**
     * 通过Map生成一个json字符串m,默认格式
     *
     * @param map
     *            需要转换的map
     * @return json串
     */
    public static String toJson(Map<String, Object> map) {
        return StringUtils.replaceJson(JSON.toJSONString(map, DEFAULT_FORMAT));
    }

    /**
     * 将List生成一个json字符串,默认格式
     * @param <T>
     *            任意对象
     * @param
     *
     * @return json串
     */
    public static <T> String toJson(List<T> list) {
        return StringUtils.replaceJson(JSON.toJSONString(list, DEFAULT_FORMAT));
    }

    /**
     * 将List生成一个json字符串，数字为null转换成零
     * @param <T>
     *            任意对象
     * @param
     *
     * @return json串
     */
    public static <T> String toJsonByNullNumberAsZero(List<T> list) {
        return StringUtils.replaceJson(JSON.toJSONString(list, SerializerFeature.WriteNullNumberAsZero));
    }

    /**
     * 将List生成一个json字符串，消除循环引用
     * @param <T>
     *            任意对象
     * @param
     *            需要转换的list
     * @return json串
     */
    public static <T> String toJsonByDisCircular(List<T> list){
        return StringUtils.replaceJson(JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect));
    }

    /**
     * 将Object转成字符串，不使用feature
     *
     * @param
     *
     * @return 转后字符串
     */
    public static String toJsonString(Object obj) {
        if(obj == null){
            return "{}";
        }
        return StringUtils.replaceJson(JSON.toJSONString(obj));
    }

    /**
     * 通过Map生成一个json字符串,不使用feature
     *
     * @param map
     *            需要转换的map
     * @return json串
     */
    public static String toJsonString(Map<String, Object> map) {
        return StringUtils.replaceJson(JSON.toJSONString(map));
    }

    /**
     * 将List生成一个json字符串，不适用feature
     * @param <T>
     *            任意对象
     * @param
     *
     * @return json串
     */
    public static <T> String toJsonString(List<T> list) {
        return StringUtils.replaceJson(JSON.toJSONString(list));
    }

    /**
     * 美化传入的json,使得该json字符串容易查看
     *
     * @param
     *
     * @return 美化后的json串
     */
    public static String prettyFormatJson(String jsonStr) {
        return JSON.toJSONString(getJSONFromString(jsonStr), true);
    }

    /**
     * 将传入的json字符串转换成Map
     *
     * @param
     *
     * @return 对应的map
     */
    public static Map<String, Object> toMap(String jsonStr) {
        return getJSONFromString(jsonStr);
    }

    /**
     * 将json字符串，转换成指定java bean
     *
     * @param json
     *            json串对象
     * @param clazz
     *            指定的bean
     * @param <T>
     *            任意bean的类型
     * @return 转换后的java bean对象
     */
	/*public static <T> T parseObject(String json, Class<T> clazz) {
		return JSON.parseObject(json, clazz);
	}*/

    /**
     * 将Object转成字符串，使用默认模式
     *
     * @param object
     *            java对象
     * @return 转后字符串
     */
	/*public static String toJSONString(Object object) {
		return StringUtils.replaceJson(JSON.toJSONString(object));
	}*/

    /**
     * 把字符串List，转换成json数组
     *
     * @param list
     *            字符串List
     * @return
     *            json数组
     */
    public static JSONArray getJsonArray(List<String> list){
        return JSON.parseArray(JSON.toJSONString(list));
    }

    /**
     * 把字符串 转换成json数组
     * @param
     *            字符串
     * @return
     *            json数组
     */
    public static JSONArray getJsonArray(String jsonStr){
        return JSON.parseArray(jsonStr);
    }

    /**
     * 将对象 转换成json对象数组
     * @param list
     *            对象
     * @return
     *            json数组对象
     */
    public static <T> JSONArray getJsonArray(T list){
        return JSON.parseArray(JSON.toJSONString(list));
    }


}
