package simplejdbc.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simplejdbc.annotation.DateString;
import simplejdbc.exception.SimpleJdbcRuntimeException;

/**
 * エンティティ用ユーティリティクラス
 * @author yasu
 */
public class EntityUtil {

	/**
	 * ロガー
	 */
	private static final Log log = LogFactory.getLog(EntityUtil.class);

	/**
	 * エンティティから新しいJavaBeansのインスタンスに詰めなおすメソッド
	 * @param entityInstance エンティティのインスタンス
	 * @return beanClass JavaBeansクラス
	 * @param <B> JavaBeansクラス
	 * @return JavaBeansの新しいインスタンス
	 */
	public static final <B> B copyToNewBean(Object entityInstance, Class<B> beanClass) {

		B beanInstance;

		try {
			//引数なしコンストラクタでインスタンス化
			Constructor<B> constructor = beanClass.getConstructor();
			beanInstance = constructor.newInstance();
		} catch (NoSuchMethodException e) {
			throw new SimpleJdbcRuntimeException(beanClass, "JavaBeansインスタンス化失敗", e);
		} catch (InstantiationException e) {
			throw new SimpleJdbcRuntimeException(beanClass, "JavaBeansインスタンス化失敗", e);
		} catch (IllegalAccessException e) {
			throw new SimpleJdbcRuntimeException(beanClass, "JavaBeansインスタンス化失敗", e);
		} catch (InvocationTargetException e) {
			throw new SimpleJdbcRuntimeException(beanClass, "JavaBeansインスタンス化失敗", e);
		}

		//新しいインスタンスに詰め合わせ
		copyToBean(entityInstance, beanInstance);

		return beanInstance;
	}

	/**
	 * エンティティから任意のJavaBeansに詰めなおすメソッド
	 * @param entityInstance エンティティのインスタンス
	 * @param beanInstance JavaBeansのインスタンス
	 */
	public static final void copyToBean(Object entityInstance, Object beanInstance) {

		Class<?> entityClass = entityInstance.getClass();
		Class<?> beanClass = beanInstance.getClass();

		Field[] fields = entityClass.getDeclaredFields();

		for(Field field : fields) {

			Field beanField;
			try {
				beanField = beanClass.getDeclaredField(field.getName());
			} catch (SecurityException e) {
				log.debug("フィールド取得失敗 Entityフィールド名:"+field.getName());
				//取得できないフィールドはスキップ
				continue;
			} catch (NoSuchFieldException e) {
				log.debug("フィールド取得失敗 Entityフィールド名:"+field.getName());
				//取得できないフィールドはスキップ
				continue;
			}

			copy(field, entityInstance, beanField, beanInstance);
		}

	}

	/**
	 * フィールドの値をコピーするメソッド
	 * @param fromField コピー元フィールド
	 * @param fromInstance コピー元オブジェクト
	 * @param toField コピー先フィールド
	 * @param toInstance コピー先オブジェクト
	 */
	protected static final void copy(Field fromField, Object fromInstance, Field toField, Object toInstance) {

		Object fromValue = getFieldValue(fromField, fromInstance);

		// anyClass -> String
		if(fromValue != null && toField.getType().getName().equals("java.lang.String")){
			if(fromValue instanceof String){
				setFieldValue(toField, toInstance, String.class, fromValue);
			}
			else if(fromValue instanceof Date){
				DateString toDateFormat = toField.getAnnotation(DateString.class);
				SimpleDateFormat simpleDateFormat = null;
				if(toDateFormat != null){
					simpleDateFormat = new SimpleDateFormat(toDateFormat.format());
				}
				else {
					simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				}
				String formattedValue = simpleDateFormat.format((Date) fromValue);
				setFieldValue(toField, toInstance, String.class, formattedValue);
			}
			else if(fromField.getType().isArray()){
				log.debug("異なる配列型のためセットしない フィールド名:"+fromField.getName()+" "+fromField.getType().getName()+" <-> "+toField.getType().getName());
			}
			else {
				setFieldValue(toField, toInstance, String.class, fromValue.toString());
			}
		}
		// String -> anyClass
		else if(fromValue != null && fromValue instanceof String){
			Class<?> fieldType = toField.getType();
			String fromString = (String) fromValue;
			if(fieldType.isPrimitive()){
				if(fieldType.getName().equals("int")){
					setFieldValue(toField, toInstance, int.class, Integer.parseInt(fromString));
				}
				else if(fieldType.getName().equals("long")){
					setFieldValue(toField, toInstance, long.class, Long.parseLong(fromString));
				}
				else if(fieldType.getName().equals("double")){
					setFieldValue(toField, toInstance, double.class, Double.parseDouble(fromString));
				}
				else if(fieldType.getName().equals("float")){
					setFieldValue(toField, toInstance, float.class, Float.parseFloat(fromString));
				}
				else if(fieldType.getName().equals("short")){
					setFieldValue(toField, toInstance, short.class, Short.parseShort(fromString));
				}
				else if(fieldType.getName().equals("char")){
					setFieldValue(toField, toInstance, char.class, fromString.toCharArray()[0]);
				}
				else if(fieldType.getName().equals("boolean")){
					setFieldValue(toField, toInstance, boolean.class, Boolean.parseBoolean(fromString));
				}
				else {
					setFieldValue(toField, toInstance, byte.class, Byte.valueOf(fromString));
				}
			}
			else if(fieldType.isArray()){
				log.debug("異なる配列型のためセットしない フィールド名:"+fromField.getName()+" "+fromField.getType().getName()+" <-> "+toField.getType().getName());
			}
			else {
				if(fieldType.getName().equals("java.lang.Integer")){
					setFieldValue(toField, toInstance, Integer.class, Integer.valueOf(fromString));
				}
				else if(fieldType.getName().equals("java.util.Date") || fieldType.getName().equals("java.sql.Date") || fieldType.getName().equals("java.sql.Timestamp")){
					DateString fromDateFormat = fromField.getAnnotation(DateString.class);
					SimpleDateFormat simpleDateFormat = null;
					if(fromDateFormat != null){
						simpleDateFormat = new SimpleDateFormat(fromDateFormat.format());
					}
					else {
						simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					}
					try {
						Date toDate = simpleDateFormat.parse(fromString);
						if(fieldType.getName().equals("java.util.Date")){
							setFieldValue(toField, toInstance, Date.class, toDate);
						}
						else if(fieldType.getName().equals("java.sql.Date")){
							setFieldValue(toField, toInstance, java.sql.Date.class, new java.sql.Date(toDate.getTime()));
						}
						else {
							setFieldValue(toField, toInstance, Timestamp.class, new Timestamp(toDate.getTime()));
						}
					} catch (ParseException e) {
						log.debug("パースエラー フィールド名:"+fromField.getName()+" パターン:"+simpleDateFormat.toPattern());
					}
				}
				else if(fieldType.getName().equals("java.math.BigInteger")){
					setFieldValue(toField, toInstance, BigInteger.class, new BigInteger(fromString));
				}
				else if(fieldType.getName().equals("java.math.BigDecimal")){
					setFieldValue(toField, toInstance, BigDecimal.class, new BigDecimal(fromString));
				}
				else if(fieldType.getName().equals("java.lang.Long")){
					setFieldValue(toField, toInstance, Long.class, Long.valueOf(fromString));
				}
				else if(fieldType.getName().equals("java.lang.Double")){
					setFieldValue(toField, toInstance, Double.class, Double.valueOf(fromString));
				}
				else if(fieldType.getName().equals("java.lang.Float")){
					setFieldValue(toField, toInstance, Float.class, Float.valueOf(fromString));
				}
				else if(fieldType.getName().equals("java.lang.Short")){
					setFieldValue(toField, toInstance, Short.class, Short.valueOf(fromString));
				}
				else if(fieldType.getName().equals("java.lang.Character")){
					setFieldValue(toField, toInstance, Character.class, Character.valueOf(fromString.toCharArray()[0]));
				}
				else if(fieldType.getName().equals("java.lang.Byte")){
					setFieldValue(toField, toInstance, Byte.class, Byte.valueOf(fromString));
				}
				else if(fieldType.getName().equals("java.lang.Boolean")){
					setFieldValue(toField, toInstance, Boolean.class, Boolean.valueOf(fromString));
				}
				else {
					log.debug("型が異なるためセットしない フィールド名:"+fromField.getName()+" "+fromField.getType().getName()+" <-> "+toField.getType().getName());
				}
			}
		}
		// null -> null
		else {
			//双方のフィールドのタイプが同じならそのままセット
			if(fromField.getType().getName().equals(toField.getType().getName())){
				setFieldValue(toField, toInstance, toField.getType(), fromValue);
			}
			else {
				log.debug("型が異なるためセットしない フィールド名:"+fromField.getName()+" "+fromField.getType().getName()+" <-> "+toField.getType().getName());
			}
		}
	}

	/**
	 * フィールドに値を取得するメソッド
	 * @param field フィールド
	 * @param instance エンティティインスタンス
	 * @return フィールドから取得した値
	 */
	protected static final Object getFieldValue(Field field, Object instance) {
		Object value;
		try {
			value = field.get(instance);
		} catch (Exception e) {
			try {
				Method method = instance.getClass().getMethod(convGetterMethodName(field));
				value  = method.invoke(instance);
			} catch (SecurityException e1) {
				log.debug("フィールド値取得失敗 フィールド名:"+field.getName()+" 例外1:"+e.getClass().getName()+" 例外2:"+e1.getClass().getName());
				return null;
			} catch (IllegalArgumentException e1) {
				log.debug("フィールド値取得失敗 フィールド名:"+field.getName()+" 例外1:"+e.getClass().getName()+" 例外2:"+e1.getClass().getName());
				return null;
			} catch (NoSuchMethodException e1) {
				log.debug("フィールド値取得失敗 フィールド名:"+field.getName()+" 例外1:"+e.getClass().getName()+" 例外2:"+e1.getClass().getName());
				return null;
			} catch (IllegalAccessException e1) {
				log.debug("フィールド値取得失敗 フィールド名:"+field.getName()+" 例外1:"+e.getClass().getName()+" 例外2:"+e1.getClass().getName());
				return null;
			} catch (InvocationTargetException e1) {
				log.debug("フィールド値取得失敗 フィールド名:"+field.getName()+" 例外1:"+e.getClass().getName()+" 例外2:"+e1.getClass().getName());
				return null;
			}
		}
		return value;
	}

	/**
	 * フィールドに値をセットするメソッド
	 * @param field フィールド
	 * @param instance エンティティインスタンス
	 * @param fieldClass フィールドにセットするクラス
	 * @param fieldValue フィールドにセットする値
	 */
	protected static final void setFieldValue(Field field, Object instance, Class<?> fieldClass, Object fieldValue) {
		try {
			field.set(instance, fieldValue);
		} catch (Exception e) {
			try {
				Method method = instance.getClass().getMethod(convSetterMethodName(field), fieldClass);
				method.invoke(instance, fieldValue);
			} catch (SecurityException e1) {
				log.debug("フィールドセットメソッド実行失敗 フィールド名:"+field.getName()+" フィールドクラス:"+fieldClass+" 例外1:"+e.getClass().getName()+" 例外2:"+e1.getClass().getName());
			} catch (IllegalArgumentException e1) {
				log.debug("フィールドセットメソッド実行失敗 フィールド名:"+field.getName()+" フィールドクラス:"+fieldClass+" 例外1:"+e.getClass().getName()+" 例外2:"+e1.getClass().getName());
			} catch (NoSuchMethodException e1) {
				log.debug("フィールドセットメソッド実行失敗 フィールド名:"+field.getName()+" フィールドクラス:"+fieldClass+" 例外1:"+e.getClass().getName()+" 例外2:"+e1.getClass().getName());
			} catch (IllegalAccessException e1) {
				log.debug("フィールドセットメソッド実行失敗 フィールド名:"+field.getName()+" フィールドクラス:"+fieldClass+" 例外1:"+e.getClass().getName()+" 例外2:"+e1.getClass().getName());
			} catch (InvocationTargetException e1) {
				log.debug("フィールドセットメソッド実行失敗 フィールド名:"+field.getName()+" フィールドクラス:"+fieldClass+" 例外1:"+e.getClass().getName()+" 例外2:"+e1.getClass().getName());
			}
		}
	}

	/**
	 * フィールド名からGetterメソッド名に変換するメソッド
	 * @param field フィールドオブジェクト
	 * @return Getterメソッド名
	 */
	protected static final String convGetterMethodName(Field field) {
		String fieldName = field.getName();
		StringBuilder builder = new StringBuilder(fieldName.length());
		if(field.getType().isPrimitive() && field.getType().getName().equals("boolean")){
			builder.append("is");
		}
		else {
			builder.append("get");
		}
		builder.append(fieldName.substring(0, 1).toUpperCase(Locale.US));
		builder.append(fieldName.substring(1));
		return builder.toString();
	}

	/**
	 * フィールド名からSetterメソッド名に変換するメソッド
	 * @param field フィールドオブジェクト
	 * @return Setterメソッド名
	 */
	protected static final String convSetterMethodName(Field field) {
		String fieldName = field.getName();
		StringBuilder builder = new StringBuilder(fieldName.length());
		builder.append("set");
		builder.append(fieldName.substring(0, 1).toUpperCase(Locale.US));
		builder.append(fieldName.substring(1));
		return builder.toString();
	}

}

