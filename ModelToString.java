package com.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 作为基类使用： 用来打印类信息(打印类名称，以及打印有get或is前缀声明的方法的字段值)
 * @author blackman8
 *
 */
public abstract class ModleToString {

	private static final String SYMBOL_SUFFIX = "\r\n";
	private static final String SYMBOL_COLON = " : ";
	
	public ModleToString() {
	}

	/**
	 * 在子类中的toString中调用此方法
	 * @return
	 */
	@Override
	public String toString() {
		try {
			return getFieldValue(this.getClass());
		} catch (Exception e) {
			return "parse error!";
		}
	}
	
	private String getFieldValue(Class<?> cls) throws Exception{
		StringBuilder builder = new StringBuilder(cls.getName() + SYMBOL_COLON).append(SYMBOL_SUFFIX);
		
		Object baseModel = cls.newInstance();
		Field[] fields = cls.getDeclaredFields();
		Method[] methods = cls.getDeclaredMethods();
		if(null == fields || fields.length == 0){
			return "no decalre field!";
		}
		
		if(null == methods || methods.length == 0){
			return "no get prefix method!";
		}
			
		for (Field mField : fields) {
			if (mField.getType().isInterface()){
				Object model = mField.getClass().newInstance();
				if(model instanceof ModleToString){
					builder.append(((ModleToString)model).toString()).append(SYMBOL_SUFFIX);
					continue;
				}
			}else{
				for (Method m : methods) {
					String methodname = m.getName();
					if(null == methodname || methodname.length() < 3){
						continue;
					}
					if (m.getName().startsWith("get") && methodname.substring(3).equalsIgnoreCase(mField.getName())) {
						builder.append(mField.getName()).append(SYMBOL_COLON).append(m.invoke(baseModel, new Object[]{})).append(SYMBOL_SUFFIX);
						break;
					}
					
					if (m.getName().startsWith("is") && methodname.substring(2).equalsIgnoreCase(mField.getName())) {
						builder.append(mField.getName()).append(SYMBOL_COLON).append(m.invoke(baseModel, new Object[]{})).append(SYMBOL_SUFFIX);
						break;
					}
				}
			}
		}
		
		
		return builder.substring(0, builder.length() - 1);
	}
		
}
