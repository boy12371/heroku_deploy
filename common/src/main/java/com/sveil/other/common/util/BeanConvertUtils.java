package com.sveil.other.common.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author richard
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BeanConvertUtils {

    static {
        //注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
        ConvertUtils.register(new SqlDateConverter(null), java.util.Date.class);
        //注册util.date的转换器，即允许BeanUtils.copyProperties时的源目标的util类型的值允许为空
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
        //注册Bigdecimal转换器，即允许BeanUtils.copyProperties时的源目标的BigDecimal类型的值允许为空
        ConvertUtils.register(new BigDecimalConverter(null), java.math.BigDecimal.class);
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new Converter() {
			@Override
			public Object convert(Class class1, Object obj) {
				if(StringUtils.isNotBlank((String) obj)){
					return obj;
				}
				return null;
			}
		}, String.class);
   }
    
    public static void registerCustomerConverter(final Class<?> targetClz){
    	ConvertUtils.register(new Converter() {
			
			@Override
			public Object convert(Class targetClz, Object source) {
				if(source ==null || targetClz== null || source.getClass().getClassLoader() == null || targetClz.getClassLoader() == null){
					//非用户自定义类转换
					return source;
				}
				if(targetClz.getClass().getName().equals(source.getClass().getName())){
					// 相同类型的自定义类，不需要转换
					return source;
				}
				try {
					Object target = targetClz.newInstance();
		            BeanUtils.copyProperties(target, source);
		            return target;
		        } catch (Exception e) {
		            throw new RuntimeException(e);
		        }
			}
		}, targetClz);
    }
    
    public static void copyProperties(Object target, Object source){
        if(source == null) {
            target = null;
            return;
        }
        try {
            BeanUtils.copyProperties(target, source);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
