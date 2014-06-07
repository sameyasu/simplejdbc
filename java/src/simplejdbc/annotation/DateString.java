package simplejdbc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日付文字列を示すアノテーション
 * @author yasu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DateString {

	/**
	 * 日付時刻フォーマット文字列
	 * <pre>
	 * 日付変換を行うフォーマットを指定する属性です。
	 * <code>SimpleDateFormat</code>のフォーマット書式が使えます。
	 * デフォルトは<code>yyyy/MM/dd HH:mm:ss</code>となります。
	 * </pre>
	 * @see java.text.SimpleDateFormat
	 */
	String format() default "yyyy/MM/dd HH:mm:ss";

}
