package com.baiwen.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@Slf4j
public class BeanUtils {

    /**
     * 复制对象
     *
     * @param object
     * @return T
     *
     */
    @SuppressWarnings("unchecked")
    public static <T> T copy(T object) {
        return (T) copy(object, object.getClass());
    }

    @SuppressWarnings("unchecked")
    public static <T> T copy(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        try {
            Object obj = clazz.newInstance();
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            propertyUtilsBean.copyProperties(obj, source);
            return (T) obj;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> String toSerialString(T object) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream out = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            out = new ObjectOutputStream(byteArrayOutputStream);
            out.writeObject(object);
            return byteArrayOutputStream.toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }

            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T toSerialObject(String objStr , Class<T> clazz) {
        byte[] bytes = objStr.getBytes();
        ObjectInputStream ois = null;
        try
        {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return (T) ois.readObject();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }finally
        {

            if (ois != null) {
                try {
                    ois.close();
                } catch (Exception e) {
                }
            }
        }

        return null;
    }

}
