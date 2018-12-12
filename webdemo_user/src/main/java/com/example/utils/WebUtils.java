package com.example.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class WebUtils {

    public static <T> T request2Bean(HttpServletRequest request, Class<T> beanClass) {
        try {
            T bean = beanClass.newInstance();
            BeanUtils.populate(bean, request.getParameterMap());
            return bean;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyBean(Object orig, Object dest) {
        // birthday 需要从 String 转换为 Date
        ConvertUtils.register(new Converter() {
            @Override
            public <T> T convert(Class<T> type, Object value) {
                if (value == null) {
                    return null;
                }

                String str = (String) value;
                str = str.trim();
                if (str.length() == 0) {
                    return null;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return (T) sdf.parse(str);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }, Date.class);
        try {
            BeanUtils.copyProperties(dest, orig);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    // 产生全球唯一 ID
    public static String generateID() {
        return UUID.randomUUID().toString();
    }
}
