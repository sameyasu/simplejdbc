package simplejdbc.util;

import java.util.Date;

import junit.framework.TestCase;

/**
 * DumpUtilのテストクラス
 * @author sameyasu
 *
 */
public class DumpUtilTest extends TestCase {

	/**
	 * convertStringのテスト
	 */
	public void testConvertString() {
		assertEquals(
				"field1[string1],field2[string2],field3[string3],fieldInteger[123456789],fieldLong[123456789012345],fieldDouble[123.456],fieldFloat[0.123456],nullString[null],fieldDate[Sat Feb 14 08:31:30 JST 2009]",
				DumpUtil.convertString(new DtoMock1(), true)
		);

		assertEquals(
				"[string1],[string2],[string3],[123456789],[123456789012345],[123.456],[0.123456],[null],[Sat Feb 14 08:31:30 JST 2009]",
				DumpUtil.convertString(new DtoMock1(), false)
		);

		assertEquals(
				"fieldInt[123],fieldLong[123456789012345],fieldDouble[123.456],fieldFloat[0.123456]",
				DumpUtil.convertString(new DtoMock2(), true)
		);

		assertEquals(
				"[123],[123456789012345],[123.456],[0.123456]",
				DumpUtil.convertString(new DtoMock2(), false)
		);

		assertEquals(
				"",
				DumpUtil.convertString(new DtoMock3(), true)
		);

		assertEquals(
				"",
				DumpUtil.convertString(new DtoMock3(), false)
		);

		assertEquals(
				"null",
				DumpUtil.convertString(null, true)
		);

		assertEquals(
				"null",
				DumpUtil.convertString(null, false)
		);
	}

	/**
	 * convertStringのテスト
	 */
	public void testConvertString2() {
		assertEquals(
				"[0]: java.lang.String[あいうえお]\n[1]: java.lang.String[かきくけこ]\n[2]: java.lang.String[さしすせそ]\n[3]: java.lang.String[たちつてと]\n",
				DumpUtil.convertString(new String[]{"あいうえお", "かきくけこ", "さしすせそ", "たちつてと"}, true)
		);
		assertEquals(
				"[0]: [あいうえお]\n[1]: [かきくけこ]\n[2]: [さしすせそ]\n[3]: [たちつてと]\n",
				DumpUtil.convertString(new String[]{"あいうえお", "かきくけこ", "さしすせそ", "たちつてと"}, false)
		);

		DtoMock1[] mockArray1 = new DtoMock1[] {
				new DtoMock1(),
				new DtoMock1(),
				new DtoMock1()
		};

		assertEquals(
				"[0]: field1[string1],field2[string2],field3[string3],fieldInteger[123456789],fieldLong[123456789012345],fieldDouble[123.456],fieldFloat[0.123456],nullString[null],fieldDate[Sat Feb 14 08:31:30 JST 2009]\n" +
				"[1]: field1[string1],field2[string2],field3[string3],fieldInteger[123456789],fieldLong[123456789012345],fieldDouble[123.456],fieldFloat[0.123456],nullString[null],fieldDate[Sat Feb 14 08:31:30 JST 2009]\n" +
				"[2]: field1[string1],field2[string2],field3[string3],fieldInteger[123456789],fieldLong[123456789012345],fieldDouble[123.456],fieldFloat[0.123456],nullString[null],fieldDate[Sat Feb 14 08:31:30 JST 2009]\n",
				DumpUtil.convertString(mockArray1, true)
		);

		assertEquals(
				"[0]: [string1],[string2],[string3],[123456789],[123456789012345],[123.456],[0.123456],[null],[Sat Feb 14 08:31:30 JST 2009]\n" +
				"[1]: [string1],[string2],[string3],[123456789],[123456789012345],[123.456],[0.123456],[null],[Sat Feb 14 08:31:30 JST 2009]\n" +
				"[2]: [string1],[string2],[string3],[123456789],[123456789012345],[123.456],[0.123456],[null],[Sat Feb 14 08:31:30 JST 2009]\n",
				DumpUtil.convertString(mockArray1, false)
		);

		assertEquals(
				"[0]: null\n" +
				"[1]: null\n" +
				"[2]: null\n",
				DumpUtil.convertString(new Object[]{null, null, null}, false)
		);

		assertEquals(
				"[EMPTY ARRAY]",
				DumpUtil.convertString(new Object[]{}, false)
		);
	}

	/**
	 * exportのテスト
	 */
	public void testExport1() {
		DumpUtil.export(new DtoMock1(), true);
		DumpUtil.export(new DtoMock1(), false);
		DumpUtil.export(new DtoMock2(), true);
		DumpUtil.export(new DtoMock2(), false);
		DumpUtil.export(new DtoMock3(), true);
		DumpUtil.export(new DtoMock3(), false);
		DumpUtil.export(null, true);
		DumpUtil.export(null, false);
	}

	/**
	 * exportのテスト
	 */
	public void testExport2() {
		DumpUtil.export(new String[]{"あいうえお", "かきくけこ", "さしすせそ", "たちつてと"}, true);
		DumpUtil.export(new String[]{"あいうえお", "かきくけこ", "さしすせそ", "たちつてと"}, false);

		DtoMock1[] mockArray1 = new DtoMock1[] {
				new DtoMock1(),
				new DtoMock1(),
				new DtoMock1()
		};

		DumpUtil.export(mockArray1, true);
		DumpUtil.export(mockArray1, false);

		DumpUtil.export(new String[]{null, null}, true);
		DumpUtil.export(new String[]{null, null}, true);
	}

	/**
	 * テスト用モッククラス1
	 * @author sameyasu
	 *
	 */
	protected class DtoMock1 {

		public String field1 = "string1";
		public String field2 = "string2";
		public String field3 = "string3";
		public Integer fieldInteger = 123456789;
		public Long fieldLong = 123456789012345L;
		public Double fieldDouble = 123.456;
		public Float fieldFloat = 0.123456f;
		public String nullString = null;

		public Date fieldDate = new Date(1234567890120L);

	}

	/**
	 * テスト用モッククラス1
	 * @author sameyasu
	 *
	 */
	protected class DtoMock2 {
		public int fieldInt = 123;
		public long fieldLong = 123456789012345L;
		public double fieldDouble = 123.456;
		public float fieldFloat = 0.123456f;
	}

	/**
	 * テスト用モッククラス2
	 * @author sameyasu
	 *
	 */
	protected class DtoMock3 {
		//no fields
	}
}
