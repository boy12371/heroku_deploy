/**
 * 
 * Copyright (c) 2013-2015 sveil.com All Rights Reserved.
 */
package com.sveil.other.common.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author richard
 */
public class ApplicationContext {

    private static Map<String, Object> applicationMap = new HashMap<String, Object>();
    
    @SuppressWarnings("unchecked")
	public static <T> T get(Class<T> clazz){
        return (T) applicationMap.get(clazz.getName());
    }
    
    public static void set(Object obj){
        applicationMap.put(obj.getClass().getName(), obj);
    }
}
