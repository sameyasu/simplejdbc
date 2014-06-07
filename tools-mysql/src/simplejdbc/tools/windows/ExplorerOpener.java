package simplejdbc.tools.windows;

import java.io.IOException;

/**
 * エクスプローラーを開くクラス
 * @author yasu
 *
 */
public class ExplorerOpener {

	/**
	 * エクスプローラ起動時のオプション
	 */
	private static final String EXPLORER_OPTION = "/n,/e,";

	/**
	 * エクスプローラ起動メソッド
	 * @param directoryPath フォルダパス
	 */
	public static final void open(String directoryPath) {

		String osName = System.getProperty("os.name");

		System.out.println("os.name = "+osName);

		if(osName != null && osName.startsWith("Windows")){
			Runtime runtime = Runtime.getRuntime();
			String command = "explorer.exe "+EXPLORER_OPTION+convPath(directoryPath);
			System.out.println(command);
			try {
				runtime.exec(command);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("エクスプローラ起動しない。");
		}

	}

	/**
	 * パス名変更メソッド
	 * @param slashPath
	 * @return
	 */
	private static final String convPath(String slashPath) {
		return "\""+ slashPath.replaceAll("\\/", "\\\\") + "\"";
	}

}
