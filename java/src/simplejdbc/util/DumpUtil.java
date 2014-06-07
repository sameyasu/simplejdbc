package simplejdbc.util;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 任意オブジェクトのダンプを行うユーティリティクラス
 * @author yasu
 */
public final class DumpUtil {

	/**
	 * ロガー
	 */
	private static final Log log = LogFactory.getLog(DumpUtil.class);

	/**
	 * オブジェクトの各フィールドダンプメソッド
	 * @param object オブジェクト
	 * @param includesFieldName true:フィールド名を出力する false:フィールド名を出力しない
	 */
	public static final void export(Object object, boolean includesFieldName) {
		log.info(convertString(object, includesFieldName));
	}

	/**
	 * オブジェクトの各フィールドダンプメソッド
	 * @param objects オブジェクトの配列
	 * @param includesFieldName true:フィールド名を出力する false:フィールド名を出力しない
	 */
	public static final void export(Object[] objects, boolean includesFieldName) {
		log.info(convertString(objects, includesFieldName));
	}
	
	/**
	 * オブジェクトの各フィールドダンプメソッド
	 * @param object オブジェクト
	 * @param includesFieldName true:フィールド名を出力する false:フィールド名を出力しない
	 * @return オブジェクトの各フィールド値を変換した文字列
	 */
	public static final String convertString(Object object, boolean includesFieldName) {

		if(object == null) {
			return "null";
		}

		Class<?> clazz = object.getClass();

		StringBuilder buff = new StringBuilder(100);

		try {
			boolean isFirst = true;
			for(Field field : clazz.getFields()) {
				if(isFirst == false) {
					buff.append(",");
				}
				else {
					isFirst = false;
				}
				String fieldName = field.getName();
				//toStringメソッドの値を連結
				if(includesFieldName) {
					buff.append(fieldName).append("[").append(field.get(object)).append("]");
				}
				else{
					buff.append("[").append(field.get(object)).append("]");
				}
			}
		} catch (SecurityException e) {
			log.error("セキュリティ例外", e);
			return null;
		} catch (IllegalArgumentException e) {
			log.error("パラメータ無効例外", e);
			return null;
		} catch (IllegalAccessException e) {
			log.error("アクセス無効例外", e);
			return null;
		}

		return buff.toString();
	}

	/**
	 * オブジェクトの各フィールドダンプメソッド
	 * @param objects オブジェクト配列
	 * @param includesFieldName true:フィールド名を出力する false:フィールド名を出力しない
	 * @return オブジェクトの各フィールド値を変換した文字列
	 */
	public static final String convertString(Object[] objects, boolean includesFieldName) {

		if(objects == null) {
			return "null";
		}

		StringBuilder buff = new StringBuilder(100);

		try {
			int i = 0;
			for(Object object: objects) {
				buff.append("[").append(i++).append("]: ");
				if(object == null) {
					buff.append("null");
				}
				else {
					Class<?> clazz = object.getClass();
					if(clazz.isPrimitive() || clazz.getName().equals("java.lang.String")) {
						if(includesFieldName) {
							buff.append(clazz.getName());
						}
						buff.append("[").append(object).append("]");
					}
					else {
						boolean isFirst = true;
						for(Field field : clazz.getFields()) {
							if(isFirst == false) {
								buff.append(",");
							}
							else {
								isFirst = false;
							}
							String fieldName = field.getName();
							//toStringメソッドの値を連結
							if(includesFieldName) {
								buff.append(fieldName);
							}
							buff.append("[").append(field.get(object)).append("]");
						}
					}
				}
				buff.append("\n");
			}

			if(i == 0){
				buff.append("[EMPTY ARRAY]");
			}
		} catch (SecurityException e) {
			log.error("セキュリティ例外", e);
			return null;
		} catch (IllegalArgumentException e) {
			log.error("パラメータ無効例外", e);
			return null;
		} catch (IllegalAccessException e) {
			log.error("アクセス無効例外", e);
			return null;
		}

		return buff.toString();
	}
}
