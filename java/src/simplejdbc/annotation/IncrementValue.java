package simplejdbc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * インクリメントするカラムのアノテーション
 * <pre>
 * このアノテーションを指定するとINSERT実行時にシーケンス値が自動的に割り振られます。
 * ただし、このアノテーションが指定されていても、
 * INSERT時にエンティティのフィールドに値がセットされている場合には、そのままの値を挿入します。
 * </pre>
 * @author yasu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IncrementValue {

	/**
	 * シーケンス名
	 * <pre>
	 * ※MySQLでは、"AUTO_INCREMENT"と指定すると自動インクリメントが適用され、
	 * それ以外の文字列に設定すると、別のシーケンス値を指定することができます。<code>table</code>属性のjavadocを参照。
	 * </pre>
	 * @return シーケンス名
	 */
	String sequence() ;

	/**
	 * インクリメントSQL構文
	 * <pre>
	 * この属性にはインクリメントする際のSQL構文を指定することができます。
	 * たとえば<p>TO_CHAR(SEQ_GOODS_ID.NEXTVAL, 'FM00000000')</p>といった文字列を指定することで、シーケンス値を0詰め文字列化した値を指定できます。
	 * この属性が指定されている場合、<code>sequence</code>属性は無視されて、SQL構文を優先してインクリメントします。
	 *
	 * ※この属性に?(クエスチョンマーク)のバインドパラメータを含めることはできません。
	 * ※MySQLでは、この属性内でLAST_INSERT_ID()を使うことができます。
	 * </pre>
	 * @return インクリメントするSQL構文
	 */
	String sql() default "";

	/**
	 * インクリメントSQLを実行する際のテーブル指定
	 * <pre>
	 * MySQLのみ指定可能です。
	 * この値を指定するとINSERT時に下記のSQLが実行されます。
	 * <b>1: INSERT INTO <code>table</code> (<code>sequence</code>) VALUES (NULL);<b>
	 * <b>2: SELECT <code>sql</code> AS <code>Column.name</code>;<b>
	 * </pre>
	 * @return シーケンス取得用テーブル名
	 */
	String table() default "";
}
