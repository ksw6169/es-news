package com.corgi.esnews.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtil {

    public static Map<String, Object> toMap(Object object) throws IllegalAccessException {

        Map<String, Object> map = null;

        if (object != null) {
            map = new HashMap<>();

            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(object));
            }
        }

        return map;
    }
}
