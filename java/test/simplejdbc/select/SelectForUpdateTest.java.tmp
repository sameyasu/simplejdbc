package simplejdbc.select;

import junit.framework.TestCase;

/**
 * SelectForUpdateのテスト
 * @author yasu
 *
 */
public class SelectForUpdateTest extends TestCase {

	/**
	 * setWaitSecondsのテスト
	 */
	public void testSetWaitSeconds() {

		//同じインスタンスで何度もsetterメソッドを呼び出した場合のテスト
		SelectForUpdate selectForUpdate = new SelectForUpdate();

		selectForUpdate.setWaitSeconds(1000);
		assertEquals(1000, selectForUpdate.getWaitSeconds());

		selectForUpdate.setWaitSeconds(-9999);
		assertEquals(-9999, selectForUpdate.getWaitSeconds());

		selectForUpdate.setWaitSeconds(0);
		assertEquals(0, selectForUpdate.getWaitSeconds());

		selectForUpdate.setWaitSeconds(SelectForUpdate.WAITSECONDS_NOWAIT);
		assertEquals(SelectForUpdate.WAITSECONDS_NOWAIT, selectForUpdate.getWaitSeconds());

		selectForUpdate.setWaitSeconds(SelectForUpdate.WAITSECONDS_WAIT_UNLIMITED);
		assertEquals(SelectForUpdate.WAITSECONDS_WAIT_UNLIMITED, selectForUpdate.getWaitSeconds());

		//デフォルト値のテスト
		SelectForUpdate selectForUpdate2 = new SelectForUpdate();
		assertEquals(SelectForUpdate.WAITSECONDS_NOWAIT, selectForUpdate2.getWaitSeconds());
	}

	/**
	 * waitのテスト
	 */
	public void testWait() {
		SelectForUpdate selectForUpdate = SelectForUpdate.wait(123456);
		assertEquals(123456, selectForUpdate.getWaitSeconds());

		selectForUpdate = SelectForUpdate.wait(-9999999);
		assertEquals(-9999999, selectForUpdate.getWaitSeconds());

		selectForUpdate = SelectForUpdate.wait(0);
		assertEquals(0, selectForUpdate.getWaitSeconds());

		selectForUpdate = SelectForUpdate.wait(SelectForUpdate.WAITSECONDS_NOWAIT);
		assertEquals(SelectForUpdate.WAITSECONDS_NOWAIT, selectForUpdate.getWaitSeconds());

		selectForUpdate = SelectForUpdate.wait(SelectForUpdate.WAITSECONDS_WAIT_UNLIMITED);
		assertEquals(SelectForUpdate.WAITSECONDS_WAIT_UNLIMITED, selectForUpdate.getWaitSeconds());
	}

	/**
	 * noWaitのテスト
	 */
	public void testNoWait() {
		SelectForUpdate selectForUpdate = SelectForUpdate.noWait();
		assertEquals(SelectForUpdate.WAITSECONDS_NOWAIT, selectForUpdate.getWaitSeconds());
	}

	/**
	 * waitUnlimitedのテスト
	 */
	public void testWaitUnlimited() {
		SelectForUpdate selectForUpdate = SelectForUpdate.waitUnlimited();
		assertEquals(SelectForUpdate.WAITSECONDS_WAIT_UNLIMITED, selectForUpdate.getWaitSeconds());
	}

}
