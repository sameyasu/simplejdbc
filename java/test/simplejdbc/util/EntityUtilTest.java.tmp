package simplejdbc.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import simplejdbc.annotation.DateString;
import simplejdbc.exception.SimpleJdbcRuntimeException;

import junit.framework.TestCase;

/**
 * EntityUtilのテストクラス
 * @author yasu
 *
 */
public class EntityUtilTest extends TestCase {

	/**
	 * convGetterMethodNameのテスト
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public void testConvGetterMethodName() throws SecurityException, NoSuchFieldException {
		Class<?> entityClass = new DummyEntity().getClass();
		assertEquals("getStringValue", EntityUtil.convGetterMethodName(entityClass.getDeclaredField("stringValue")));
		assertEquals("getDateValue", EntityUtil.convGetterMethodName(entityClass.getDeclaredField("dateValue")));
		assertEquals("isBooleanValue", EntityUtil.convGetterMethodName(entityClass.getDeclaredField("booleanValue")));
		assertEquals("getIntegerValue", EntityUtil.convGetterMethodName(entityClass.getDeclaredField("integerValue")));
	}

	/**
	 * convSetterMethodNameのテスト
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public void testConvSetterMethodName() throws SecurityException, NoSuchFieldException {
		Class<?> entityClass = new DummyEntity().getClass();
		assertEquals("setStringValue", EntityUtil.convSetterMethodName(entityClass.getDeclaredField("stringValue")));
		assertEquals("setDateValue", EntityUtil.convSetterMethodName(entityClass.getDeclaredField("dateValue")));
		assertEquals("setBooleanValue", EntityUtil.convSetterMethodName(entityClass.getDeclaredField("booleanValue")));
		assertEquals("setIntegerValue", EntityUtil.convSetterMethodName(entityClass.getDeclaredField("integerValue")));
	}

	/**
	 * copyToBeanのテスト
	 */
	public void testCopyToBean() {

		DummyEntity entity = new DummyEntity();
		entity.stringValue = "文字列値";
		entity.dateValue = new Date();
		entity.booleanValue = true;
		entity.intValue = Integer.MAX_VALUE;
		entity.longValue = Long.MAX_VALUE;
		entity.floatValue = Float.MAX_VALUE;
		entity.doubleValue = Double.MAX_VALUE;
		entity.charValue = Character.MAX_VALUE;
		entity.byteValue = Byte.MAX_VALUE;
		entity.byteArrayValue = "バイト変換文字列".getBytes();
		entity.setIntegerValue(Integer.MIN_VALUE);
		entity.bigDecimalValue = new BigDecimal("123456789012345678901234567890.12345678901234567890");	//50桁実数
		entity.bigIntegerValue = new BigInteger("-987654321098765432109876543210");	//30桁整数
		entity.privateField = "private";
		entity.protectedField = "protected";
		entity.defaultField = "default";

		//インスタンス化のみ
		DummyBean bean = new DummyBean();

		EntityUtil.copyToBean(entity, bean);

		assertEquals(entity.stringValue, bean.getStringValue());
		assertEquals(entity.dateValue, bean.getDateValue());
		assertEquals(entity.booleanValue, bean.isBooleanValue());
		assertEquals(entity.intValue, bean.getIntValue());
		assertEquals(entity.longValue, bean.getLongValue());
		assertEquals(entity.floatValue, bean.getFloatValue());
		assertEquals(entity.doubleValue, bean.getDoubleValue());
		assertEquals(entity.charValue, bean.getCharValue());
		assertEquals(entity.byteValue, bean.getByteValue());
		assertEquals(entity.byteArrayValue, bean.getByteArrayValue());
		assertEquals(entity.getIntegerValue(), bean.getIntegerValue());
		assertEquals(entity.bigDecimalValue, bean.getBigDecimalValue());
		assertEquals(entity.bigIntegerValue, bean.getBigIntegerValue());
		assertEquals(null, bean.getPrivateField());	//privateなフィールドは詰め替えできない
		assertEquals(entity.protectedField, bean.getProtectedField());
		assertEquals(entity.defaultField, bean.getDefaultField());


	}

	/**
	 * copyToBeanのテスト2
	 */
	public void testCopyToBean2() {

		DummyBean bean = new DummyBean();
		bean.setStringValue("文字列値");
		bean.setDateValue(new Date());
		bean.setBooleanValue(true);
		bean.setIntValue(Integer.MAX_VALUE);
		bean.setLongValue(Long.MAX_VALUE);
		bean.setFloatValue(Float.MAX_VALUE);
		bean.setDoubleValue(Double.MAX_VALUE);
		bean.setShortValue(Short.MAX_VALUE);
		bean.setCharValue(Character.MAX_VALUE);
		bean.setByteValue(Byte.MAX_VALUE);
		bean.setByteArrayValue("バイト変換文字列".getBytes());
		bean.integerValue = Integer.MIN_VALUE;
		bean.bigDecimalValue = BigDecimal.ZERO;
		bean.bigIntegerValue = BigInteger.ONE;
		bean.setPrivateField("private");
		bean.setProtectedField("protected");
		bean.setDefaultField("default");

		//インスタンス化のみ
		DummyEntity entity = new DummyEntity();

		//逆にコピー
		EntityUtil.copyToBean(bean, entity);

		assertEquals(bean.getStringValue(), entity.stringValue);
		assertEquals(bean.getDateValue(), entity.dateValue);
		assertEquals(bean.isBooleanValue(), entity.booleanValue);
		assertEquals(bean.getIntValue(), entity.intValue);
		assertEquals(bean.getLongValue(), entity.longValue);
		assertEquals(bean.getFloatValue(), entity.floatValue);
		assertEquals(bean.getDoubleValue(), entity.doubleValue);
		assertEquals(bean.getShortValue(), entity.shortValue);
		assertEquals(bean.getCharValue(), entity.charValue);
		assertEquals(bean.getByteValue(), entity.byteValue);
		assertEquals(bean.getByteArrayValue(), entity.byteArrayValue);
		assertEquals(bean.getIntegerValue(), entity.getIntegerValue());
		assertEquals(bean.getBigDecimalValue(), entity.bigDecimalValue);
		assertEquals(bean.getBigIntegerValue(), entity.bigIntegerValue);
		assertEquals(null, entity.privateField);	//privateなフィールドは詰め替えできない
		assertEquals(bean.getProtectedField(), entity.protectedField);
		assertEquals(bean.getDefaultField(), entity.defaultField);

	}


	/**
	 * copyToNewBeanのテスト
	 */
	public void testCopyToNewBean() {

		DummyEntity entity = new DummyEntity();
		entity.stringValue = "文字列値";
		entity.dateValue = new Date();
		entity.booleanValue = true;
		entity.intValue = Integer.MAX_VALUE;
		entity.longValue = Long.MAX_VALUE;
		entity.floatValue = Float.MAX_VALUE;
		entity.doubleValue = Double.MAX_VALUE;
		entity.shortValue = Short.MIN_VALUE;
		entity.charValue = Character.MAX_VALUE;
		entity.byteValue = Byte.MAX_VALUE;
		entity.byteArrayValue = "バイト変換文字列".getBytes();
		entity.setIntegerValue(Integer.MIN_VALUE);
		entity.bigDecimalValue = BigDecimal.ONE;
		entity.bigIntegerValue = new BigInteger("11111111111111111111111122222222222222222222233333333333333333333");

		//インスタンス化のみ
		DummyBean bean = EntityUtil.copyToNewBean(entity, DummyBean.class);

		assertEquals(entity.stringValue, bean.getStringValue());
		assertEquals(entity.dateValue, bean.getDateValue());
		assertEquals(entity.booleanValue, bean.isBooleanValue());
		assertEquals(entity.intValue, bean.getIntValue());
		assertEquals(entity.longValue, bean.getLongValue());
		assertEquals(entity.floatValue, bean.getFloatValue());
		assertEquals(entity.doubleValue, bean.getDoubleValue());
		assertEquals(entity.shortValue, bean.getShortValue());
		assertEquals(entity.charValue, bean.getCharValue());
		assertEquals(entity.byteValue, bean.getByteValue());
		assertEquals(entity.byteArrayValue, bean.getByteArrayValue());
		assertEquals(entity.getIntegerValue(), bean.getIntegerValue());
		assertEquals(entity.bigDecimalValue, bean.getBigDecimalValue());
		assertEquals(entity.bigIntegerValue, bean.getBigIntegerValue());

	}

	/**
	 * copyToNewBeanのテスト2
	 */
	public void testCopyToNewBean2() {

		try {
			DummyEntity3 entity = new DummyEntity3();
			entity.stringValue = "文字列値";
			EntityUtil.copyToNewBean(entity, DummyBean3.class);
			fail();
		} catch (SimpleJdbcRuntimeException e) {
			assertEquals("<simplejdbc.util.EntityUtilTest$DummyBean3> JavaBeansインスタンス化失敗", e.getMessage());
		}

		try {
			DummyBean3 entity = new DummyBean3();
			entity.stringValue = "文字列値";
			EntityUtil.copyToNewBean(entity, DummyEntity3.class);
			fail();
		} catch (SimpleJdbcRuntimeException e) {
			assertEquals("<simplejdbc.util.EntityUtilTest$DummyEntity3> JavaBeansインスタンス化失敗", e.getMessage());
		}

	}

	/**
	 * copyToBeanIgnoreStringのテスト
	 */
	public void testCopyToBeanIgnoreString() {

		Date now = new Date();

		DummyEntity2 entity = new DummyEntity2();
		entity.samplingDate1 = now;
		entity.samplingDate2 = new java.sql.Date(now.getTime());
		entity.samplingDate3 = new java.sql.Timestamp(now.getTime());
		entity.stringValue = "文字列値";
		entity.dateValue = new Date();
		entity.booleanValue = true;
		entity.intValue = Integer.MAX_VALUE;
		entity.longValue = Long.MAX_VALUE;
		entity.floatValue = Float.MAX_VALUE;
		entity.doubleValue = Double.MAX_VALUE;
		entity.shortValue = Short.MAX_VALUE;
		entity.charValue = Character.MAX_VALUE;
		entity.byteValue = Byte.MAX_VALUE;
		entity.byteArrayValue = "バイト変換文字列".getBytes();
		entity.booleanObjectValue = false;
		entity.intObjectValue = Integer.MIN_VALUE;
		entity.longObjectValue = Long.MIN_VALUE;
		entity.floatObjectValue = Float.MIN_VALUE;
		entity.doubleObjectValue = Double.MIN_VALUE;
		entity.shortObjectValue = Short.MIN_VALUE;
		entity.charObjectValue = Character.MIN_VALUE;
		entity.byteObjectValue = Byte.MIN_VALUE;
		entity.setIntegerValue(Integer.MIN_VALUE);
		entity.bigDecimalValue = new BigDecimal("123456789012345678901234567890.12345678901234567890");	//50桁実数
		entity.bigIntegerValue = new BigInteger("-987654321098765432109876543210");	//30桁整数

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

		DummyBean2 bean = new DummyBean2();

		EntityUtil.copyToBean(entity, bean);

		assertEquals(dateFormat1.format(now), bean.getSamplingDate1());
		assertEquals(dateFormat2.format(now), bean.getSamplingDate2());
		assertEquals(dateFormat3.format(now), bean.getSamplingDate3());
		assertEquals(entity.stringValue, bean.getStringValue());
		assertEquals(dateFormat1.format(entity.dateValue), bean.getDateValue());
		assertEquals("true", bean.getBooleanValue());
		assertEquals(Integer.toString(entity.intValue), bean.getIntValue());
		assertEquals(Long.toString(entity.longValue), bean.getLongValue());
		assertEquals(Float.toString(entity.floatValue), bean.getFloatValue());
		assertEquals(Double.toString(entity.doubleValue), bean.getDoubleValue());
		assertEquals(Short.toString(entity.shortValue), bean.getShortValue());
		assertEquals(new String(new char[]{entity.charValue}), bean.getCharValue());
		assertEquals(Byte.toString(entity.byteValue), bean.getByteValue());
		assertEquals("false", bean.getBooleanObjectValue());
		assertEquals(Integer.toString(entity.intObjectValue), bean.getIntObjectValue());
		assertEquals(Long.toString(entity.longObjectValue), bean.getLongObjectValue());
		assertEquals(Float.toString(entity.floatObjectValue), bean.getFloatObjectValue());
		assertEquals(Double.toString(entity.doubleObjectValue), bean.getDoubleObjectValue());
		assertEquals(Short.toString(entity.shortObjectValue), bean.getShortObjectValue());
		assertEquals(new String(new char[]{entity.charObjectValue}), bean.getCharObjectValue());
		assertEquals(Byte.toString(entity.byteObjectValue), bean.getByteObjectValue());
		assertEquals(null, bean.getByteArrayValue());	//配列型のためNULLとする
		assertEquals(entity.getIntegerValue().toString(), bean.getIntegerValue());
		assertEquals(entity.bigDecimalValue.toString(), bean.getBigDecimalValue());
		assertEquals(entity.bigIntegerValue.toString(), bean.getBigIntegerValue());

	}

	/**
	 * copyToBeanIgnoreStringのテスト2
	 */
	public void testCopyToBeanIgnoreString2() {

		Date now = new Date();

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

		DummyBean2 bean = new DummyBean2();
		bean.setSamplingDate1(dateFormat1.format(now));
		bean.setSamplingDate2(dateFormat2.format(now));
		bean.setSamplingDate3(dateFormat3.format(now));
		bean.setSamplingDate4("フォーマットできない日付文字列");
		bean.setStringValue("文字列値");
		bean.setDateValue(dateFormat1.format(now));
		bean.setBooleanValue("true");
		bean.setIntValue("12345");
		bean.setLongValue("1111111111111");
		bean.setFloatValue("1234.567");
		bean.setDoubleValue("1223252346.018378342436346");
		bean.setShortValue("12345");
		bean.setCharValue("1");
		bean.setByteValue("9");
		bean.setBooleanObjectValue("true");
		bean.setIntObjectValue("12345");
		bean.setLongObjectValue("1111111111111");
		bean.setFloatObjectValue("1234.567");
		bean.setDoubleObjectValue("1223252346.018378342436346");
		bean.setShortObjectValue("9876");
		bean.setCharObjectValue("1");
		bean.setByteObjectValue("9");
		bean.setByteArrayValue("バイト変換文字列");
		bean.setIntegerValue("8967786");
		bean.setBigDecimalValue("123456789012345678901234567890.12345678901234567890");
		bean.setBigIntegerValue("-987654321098765432109876543210");

		DummyEntity2 entity = new DummyEntity2();

		EntityUtil.copyToBean(bean, entity);

		assertEquals(dateFormat1.format(now), dateFormat1.format(entity.samplingDate1));
		assertEquals(dateFormat2.format(now), dateFormat2.format(entity.samplingDate2));
		assertEquals(dateFormat3.format(now), dateFormat3.format(entity.samplingDate3));
		assertEquals(null, entity.samplingDate4);	//フォーマットできないのでNULL
		assertEquals(bean.getStringValue(), entity.stringValue);
		assertEquals(bean.getDateValue(), dateFormat1.format(entity.dateValue));
		assertEquals(Boolean.parseBoolean("true"), entity.booleanValue);
		assertEquals(Integer.parseInt("12345"), entity.intValue);
		assertEquals(Long.parseLong("1111111111111"), entity.longValue);
		assertEquals(Float.parseFloat("1234.567"), entity.floatValue);
		assertEquals(Double.parseDouble("1223252346.018378342436346"), entity.doubleValue);
		assertEquals(Short.parseShort("12345"), entity.shortValue);
		assertEquals('1', entity.charValue);
		assertEquals(Byte.parseByte("9"), entity.byteValue);
		assertEquals(Boolean.valueOf("true"), entity.booleanObjectValue);
		assertEquals(Integer.valueOf("12345"), entity.intObjectValue);
		assertEquals(Long.valueOf("1111111111111"), entity.longObjectValue);
		assertEquals(Float.valueOf("1234.567"), entity.floatObjectValue);
		assertEquals(Double.valueOf("1223252346.018378342436346"), entity.doubleObjectValue);
		assertEquals(Short.valueOf("9876"), entity.shortObjectValue);
		assertEquals(new Character('1'), entity.charObjectValue);
		assertEquals(Byte.valueOf("9"), entity.byteObjectValue);
		assertEquals(null, entity.byteArrayValue);	//配列型のためNULL
		assertEquals(new Integer("8967786"), entity.getIntegerValue());
		assertEquals(new BigDecimal("123456789012345678901234567890.12345678901234567890"), entity.bigDecimalValue);
		assertEquals(new BigInteger("-987654321098765432109876543210"), entity.bigIntegerValue);

	}


	/**
	 * テスト用のダミーエンティティクラス
	 * @author yasu
	 *
	 */
	public static class DummyEntity {

		public String stringValue;
		public Date dateValue;
		public boolean booleanValue;
		public int intValue;
		public long longValue;
		public double doubleValue;
		public float floatValue;
		public short shortValue;
		public byte byteValue;
		public char charValue;
		public byte[] byteArrayValue;
		public BigDecimal bigDecimalValue;
		public BigInteger bigIntegerValue;
		private String privateField;
		protected String protectedField;
		String defaultField;

		/**
		 * プライベートなIntegerフィールド
		 */
		private Integer integerValue;

		/**
		 * エンティティにしか用意していないフィールド
		 */
		public double entityOnlyField;

		public Integer getIntegerValue() {
			return integerValue;
		}

		public void setIntegerValue(Integer integerValue) {
			this.integerValue = integerValue;
		}

		public String getPrivateField() {
			return privateField;
		}

		public void setPrivateField(String privateField) {
			this.privateField = privateField;
		}
	}

	/**
	 * テスト用のダミーJavaBeans
	 * @author yasu
	 *
	 */
	public static class DummyBean {

		private String stringValue;
		private Date dateValue;
		private boolean booleanValue;
		private int intValue;
		private long longValue;
		private double doubleValue;
		private float floatValue;
		private short shortValue;
		private byte byteValue;
		public char charValue;
		private byte[] byteArrayValue;
		private Integer integerValue;
		private BigDecimal bigDecimalValue;
		private BigInteger bigIntegerValue;
		private String privateField;
		protected String protectedField;
		String defaultField;

		/**
		 * JavaBeansにしか用意していないフィールド
		 */
		private int javabeansOnlyField;


		public String getStringValue() {
			return stringValue;
		}

		public void setStringValue(String stringValue) {
			this.stringValue = stringValue;
		}

		public Date getDateValue() {
			return dateValue;
		}

		public void setDateValue(Date dateValue) {
			this.dateValue = dateValue;
		}

		public boolean isBooleanValue() {
			return booleanValue;
		}

		public void setBooleanValue(boolean booleanValue) {
			this.booleanValue = booleanValue;
		}

		public int getJavabeansOnlyField() {
			return javabeansOnlyField;
		}

		public void setJavabeansOnlyField(int javabeansOnlyField) {
			this.javabeansOnlyField = javabeansOnlyField;
		}

		public int getIntValue() {
			return intValue;
		}

		public void setIntValue(int intValue) {
			this.intValue = intValue;
		}

		public long getLongValue() {
			return longValue;
		}

		public void setLongValue(long longValue) {
			this.longValue = longValue;
		}

		public double getDoubleValue() {
			return doubleValue;
		}

		public void setDoubleValue(double doubleValue) {
			this.doubleValue = doubleValue;
		}

		public float getFloatValue() {
			return floatValue;
		}

		public void setFloatValue(float floatValue) {
			this.floatValue = floatValue;
		}

		public short getShortValue() {
			return shortValue;
		}

		public void setShortValue(short shortValue) {
			this.shortValue = shortValue;
		}

		public byte getByteValue() {
			return byteValue;
		}

		public void setByteValue(byte byteValue) {
			this.byteValue = byteValue;
		}

		public char getCharValue() {
			return charValue;
		}

		public void setCharValue(char charValue) {
			this.charValue = charValue;
		}

		public byte[] getByteArrayValue() {
			return byteArrayValue;
		}

		public void setByteArrayValue(byte[] byteArrayValue) {
			this.byteArrayValue = byteArrayValue;
		}

		public Integer getIntegerValue() {
			return integerValue;
		}

		public void setIntegerValue(Integer integerValue) {
			this.integerValue = integerValue;
		}

		public BigDecimal getBigDecimalValue() {
			return bigDecimalValue;
		}

		public void setBigDecimalValue(BigDecimal bigDecimalValue) {
			this.bigDecimalValue = bigDecimalValue;
		}

		public BigInteger getBigIntegerValue() {
			return bigIntegerValue;
		}

		public void setBigIntegerValue(BigInteger bigIntegerValue) {
			this.bigIntegerValue = bigIntegerValue;
		}

		private String getPrivateField() {
			return privateField;
		}

		private void setPrivateField(String privateField) {
			this.privateField = privateField;
		}

		protected String getProtectedField() {
			return protectedField;
		}

		protected void setProtectedField(String protectedField) {
			this.protectedField = protectedField;
		}

		String getDefaultField() {
			return defaultField;
		}

		void setDefaultField(String defaultField) {
			this.defaultField = defaultField;
		}

	}

	public static class DummyEntity2 {

		public Date samplingDate1;
		public java.sql.Date samplingDate2;
		public Timestamp samplingDate3;
		public Date samplingDate4;

		public String stringValue;
		public Date dateValue;
		public boolean booleanValue;
		public int intValue;
		public long longValue;
		public double doubleValue;
		public float floatValue;
		public short shortValue;
		public byte byteValue;
		public char charValue;
		public Boolean booleanObjectValue;
		public Integer intObjectValue;
		public Long longObjectValue;
		public Double doubleObjectValue;
		public Float floatObjectValue;
		public Short shortObjectValue;
		public Byte byteObjectValue;
		public Character charObjectValue;

		public byte[] byteArrayValue;
		public BigDecimal bigDecimalValue;
		public BigInteger bigIntegerValue;

		/**
		 * プライベートなIntegerフィールド
		 */
		private Integer integerValue;

		/**
		 * エンティティにしか用意していないフィールド
		 */
		public double entityOnlyField;

		public Integer getIntegerValue() {
			return integerValue;
		}

		public void setIntegerValue(Integer integerValue) {
			this.integerValue = integerValue;
		}
	}

	public static class DummyBean2 {

		@DateString
		private String samplingDate1;

		@DateString(format="yyyy/MM/dd")
		private String samplingDate2;

		@DateString(format="yyyy/MM/dd HH:mm:ss.SSS")
		private String samplingDate3;

		@DateString(format="yyyy/MM/dd HH:mm:ss.SSS")
		private String samplingDate4;

		private String stringValue;
		private String dateValue;
		private String booleanValue;
		private String intValue;
		private String longValue;
		private String doubleValue;
		private String floatValue;
		private String shortValue;
		private String byteValue;
		private String charValue;
		private String byteArrayValue;
		private String integerValue;
		private String booleanObjectValue;
		private String intObjectValue;
		private String longObjectValue;
		private String doubleObjectValue;
		private String floatObjectValue;
		private String shortObjectValue;
		private String byteObjectValue;
		private String charObjectValue;
		private String bigDecimalValue;
		private String bigIntegerValue;

		/**
		 * JavaBeansにしか用意していないフィールド
		 */
		private int javabeansOnlyField;

		public String getSamplingDate1() {
			return samplingDate1;
		}

		public String getSamplingDate2() {
			return samplingDate2;
		}

		public void setSamplingDate2(String samplingDate2) {
			this.samplingDate2 = samplingDate2;
		}

		public String getSamplingDate3() {
			return samplingDate3;
		}

		public void setSamplingDate3(String samplingDate3) {
			this.samplingDate3 = samplingDate3;
		}

		public void setSamplingDate1(String samplingDate1) {
			this.samplingDate1 = samplingDate1;
		}

		public String getStringValue() {
			return stringValue;
		}

		public void setStringValue(String stringValue) {
			this.stringValue = stringValue;
		}

		public String getDateValue() {
			return dateValue;
		}

		public void setDateValue(String dateValue) {
			this.dateValue = dateValue;
		}

		public String getBooleanValue() {
			return booleanValue;
		}

		public void setBooleanValue(String booleanValue) {
			this.booleanValue = booleanValue;
		}

		public String getIntValue() {
			return intValue;
		}

		public void setIntValue(String intValue) {
			this.intValue = intValue;
		}

		public String getLongValue() {
			return longValue;
		}

		public void setLongValue(String longValue) {
			this.longValue = longValue;
		}

		public String getDoubleValue() {
			return doubleValue;
		}

		public void setDoubleValue(String doubleValue) {
			this.doubleValue = doubleValue;
		}

		public String getFloatValue() {
			return floatValue;
		}

		public void setFloatValue(String floatValue) {
			this.floatValue = floatValue;
		}

		public String getShortValue() {
			return shortValue;
		}

		public void setShortValue(String shortValue) {
			this.shortValue = shortValue;
		}

		public String getByteValue() {
			return byteValue;
		}

		public void setByteValue(String byteValue) {
			this.byteValue = byteValue;
		}

		public String getCharValue() {
			return charValue;
		}

		public void setCharValue(String charValue) {
			this.charValue = charValue;
		}

		public String getByteArrayValue() {
			return byteArrayValue;
		}

		public void setByteArrayValue(String byteArrayValue) {
			this.byteArrayValue = byteArrayValue;
		}

		public String getIntegerValue() {
			return integerValue;
		}

		public void setIntegerValue(String integerValue) {
			this.integerValue = integerValue;
		}

		public String getBigDecimalValue() {
			return bigDecimalValue;
		}

		public void setBigDecimalValue(String bigDecimalValue) {
			this.bigDecimalValue = bigDecimalValue;
		}

		public int getJavabeansOnlyField() {
			return javabeansOnlyField;
		}

		public void setJavabeansOnlyField(int javabeansOnlyField) {
			this.javabeansOnlyField = javabeansOnlyField;
		}

		public String getBooleanObjectValue() {
			return booleanObjectValue;
		}

		public void setBooleanObjectValue(String booleanObjectValue) {
			this.booleanObjectValue = booleanObjectValue;
		}

		public String getIntObjectValue() {
			return intObjectValue;
		}

		public void setIntObjectValue(String intObjectValue) {
			this.intObjectValue = intObjectValue;
		}

		public String getLongObjectValue() {
			return longObjectValue;
		}

		public void setLongObjectValue(String longObjectValue) {
			this.longObjectValue = longObjectValue;
		}

		public String getDoubleObjectValue() {
			return doubleObjectValue;
		}

		public void setDoubleObjectValue(String doubleObjectValue) {
			this.doubleObjectValue = doubleObjectValue;
		}

		public String getFloatObjectValue() {
			return floatObjectValue;
		}

		public void setFloatObjectValue(String floatObjectValue) {
			this.floatObjectValue = floatObjectValue;
		}

		public String getShortObjectValue() {
			return shortObjectValue;
		}

		public void setShortObjectValue(String shortObjectValue) {
			this.shortObjectValue = shortObjectValue;
		}

		public String getByteObjectValue() {
			return byteObjectValue;
		}

		public void setByteObjectValue(String byteObjectValue) {
			this.byteObjectValue = byteObjectValue;
		}

		public String getCharObjectValue() {
			return charObjectValue;
		}

		public void setCharObjectValue(String charObjectValue) {
			this.charObjectValue = charObjectValue;
		}

		public String getBigIntegerValue() {
			return bigIntegerValue;
		}

		public void setBigIntegerValue(String bigIntegerValue) {
			this.bigIntegerValue = bigIntegerValue;
		}

		public String getSamplingDate4() {
			return samplingDate4;
		}

		public void setSamplingDate4(String samplingDate4) {
			this.samplingDate4 = samplingDate4;
		}
	}

	/**
	 * ダミーのエンティティクラス3
	 * @author yasu
	 *
	 */
	public static class DummyEntity3 {

		/**
		 * privateなコンストラクタ
		 */
		private DummyEntity3() {
			super();
		}

		public String stringValue;

	}

	/**
	 * ダミーのエンティティクラス3
	 * @author yasu
	 *
	 */
	public static class DummyBean3 {

		/**
		 * privateなコンストラクタ
		 */
		private DummyBean3() {
			super();
		}

		private String stringValue;

		public String getStringValue() {
			return stringValue;
		}

		public void setStringValue(String stringValue) {
			this.stringValue = stringValue;
		}

	}

}
