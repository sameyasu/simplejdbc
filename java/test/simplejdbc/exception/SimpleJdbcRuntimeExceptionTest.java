package simplejdbc.exception;

import junit.framework.TestCase;

/**
 * SimpleJdbcRuntimeExceptionのテストクラス
 * @author yasu
 *
 */
public class SimpleJdbcRuntimeExceptionTest extends TestCase {


	/**
	 * protectedコンストラクタのテスト
	 */
	public void testProtectedConstructer() {

		SimpleJdbcRuntimeException exception = new SimpleJdbcRuntimeException();
		assertEquals(null, exception.getMessage());
		assertEquals(null, exception.getCause());

		exception = new SimpleJdbcRuntimeException("test message");
		assertEquals("test message", exception.getMessage());
		assertEquals(null, exception.getCause());

		exception = new SimpleJdbcRuntimeException("test message", new IllegalArgumentException("dummy message"));
		assertEquals("test message", exception.getMessage());
		assertTrue(exception.getCause() instanceof IllegalArgumentException);
		assertEquals("dummy message" ,exception.getCause().getMessage());

		exception = new SimpleJdbcRuntimeException(new IllegalArgumentException("dummy message"));
		assertEquals("java.lang.IllegalArgumentException: dummy message", exception.getMessage());
		assertTrue(exception.getCause() instanceof IllegalArgumentException);
		assertEquals("dummy message" ,exception.getCause().getMessage());
	}

	/**
	 * publicコンストラクタのテスト
	 */
	public void testPublicConstructer() {

		SimpleJdbcRuntimeException exception = new SimpleJdbcRuntimeException(EntityMock.class, "test message");
		assertEquals("<simplejdbc.exception.SimpleJdbcRuntimeExceptionTest$EntityMock> test message", exception.getMessage());
		assertEquals(null, exception.getCause());

		exception = new SimpleJdbcRuntimeException(EntityMock.class, "test message", new IllegalArgumentException("dummy message"));
		assertEquals("<simplejdbc.exception.SimpleJdbcRuntimeExceptionTest$EntityMock> test message", exception.getMessage());
		assertTrue(exception.getCause() instanceof IllegalArgumentException);
		assertEquals("dummy message" ,exception.getCause().getMessage());

		exception = new SimpleJdbcRuntimeException(EntityMock.class, "test message", null);
		assertEquals("<simplejdbc.exception.SimpleJdbcRuntimeExceptionTest$EntityMock> test message", exception.getMessage());
		assertEquals(null ,exception.getCause());

		exception = new SimpleJdbcRuntimeException(EntityMock.class, null);
		assertEquals("<simplejdbc.exception.SimpleJdbcRuntimeExceptionTest$EntityMock> null", exception.getMessage());
		assertEquals(null, exception.getCause());

		exception = new SimpleJdbcRuntimeException(null, "test message");
		assertEquals("test message", exception.getMessage());
		assertEquals(null, exception.getCause());

		exception = new SimpleJdbcRuntimeException(null, "test message", new IllegalArgumentException("dummy message"));
		assertEquals("test message", exception.getMessage());
		assertTrue(exception.getCause() instanceof IllegalArgumentException);
		assertEquals("dummy message" ,exception.getCause().getMessage());

		exception = new SimpleJdbcRuntimeException(EntityMock.class, null, new IllegalArgumentException("dummy message"));
		assertEquals("<simplejdbc.exception.SimpleJdbcRuntimeExceptionTest$EntityMock> null", exception.getMessage());
		assertTrue(exception.getCause() instanceof IllegalArgumentException);
		assertEquals("dummy message" ,exception.getCause().getMessage());

		exception = new SimpleJdbcRuntimeException((Class<?>) null, null);
		assertEquals("null", exception.getMessage());
		assertEquals(null, exception.getCause());

		exception = new SimpleJdbcRuntimeException((Class<?>) null, null, new IllegalArgumentException("dummy message"));
		assertEquals("null", exception.getMessage());
		assertTrue(exception.getCause() instanceof IllegalArgumentException);
		assertEquals("dummy message" ,exception.getCause().getMessage());

		exception = new SimpleJdbcRuntimeException((Class<?>) null, null, null);
		assertEquals("null", exception.getMessage());
		assertEquals(null ,exception.getCause());

	}

	/**
	 * エンティティクラスのモック
	 * @author yasu
	 *
	 */
	public static class EntityMock {

		private String dummyField;

		public String getDummyField() {
			return dummyField;
		}

		public void setDummyField(String dummyField) {
			this.dummyField = dummyField;
		}

	}

}
