import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simplejdbc.tools.CustomSqlEntityMakerFrame;
import simplejdbc.tools.UserTablesEntityMakerFrame;

/**
 * simplejdbc-toolsのメインクラス
 * @author yasu
 *
 */
public class Main {

	/**
	 * メインメソッド
	 * @param args プログラム引数
	 */
	public static void main(String[] args) {

		JFrame frame = new JFrame("simpleJdbc支援 自動生成ツール");

	    frame.setSize(400, 200);
	    frame.setLocationRelativeTo(null);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

	    GridBagLayout layout = new GridBagLayout();
	    GridBagConstraints gbc = new GridBagConstraints();

	    frame.setLayout(layout);

	    JPanel tablesMakerPanel = new JPanel();
	    JButton tablesMakerButton = new JButton("テーブルエンティティ自動生成ツール");
	    tablesMakerButton.addActionListener(new TablesMakerActionListener(frame));
	    tablesMakerPanel.add(tablesMakerButton);

	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridwidth = 1;
	    gbc.gridheight = 1;
	    gbc.insets = new Insets(10, 10, 10, 10);
	    gbc.anchor = GridBagConstraints.CENTER;
	    gbc.fill = GridBagConstraints.BOTH;
	    layout.setConstraints(tablesMakerPanel, gbc);

	    JPanel customEntityMakerPanel = new JPanel();
	    JButton customEntityMakerButton = new JButton("カスタムSQLエンティティ自動生成ツール");
	    customEntityMakerButton.addActionListener(new CustomSqlMakerActionListener(frame));
	    customEntityMakerPanel.add(customEntityMakerButton);

	    gbc.gridy++;
	    layout.setConstraints(customEntityMakerPanel, gbc);

	    Container contentPane = frame.getContentPane();
	    contentPane.add(tablesMakerPanel);
	    contentPane.add(customEntityMakerPanel);

	    frame.setVisible(true);
	}

	/**
	 * リスナー
	 * @author yasu
	 */
	public static class TablesMakerActionListener implements ActionListener {

		/**
		 * 親フレーム
		 */
		private JFrame parentFrame;

		/**
		 * コンストラクタ
		 * @param frame
		 */
		private TablesMakerActionListener(JFrame parentFrame) {
			super();
			this.parentFrame = parentFrame;
		}

		public void actionPerformed(ActionEvent e) {
			UserTablesEntityMakerFrame frame = new UserTablesEntityMakerFrame();
			frame.setVisible(true);
			parentFrame.setVisible(false);
		}
	}

	/**
	 * リスナー
	 * @author yasu
	 */
	public static class CustomSqlMakerActionListener implements ActionListener {

		/**
		 * 親フレーム
		 */
		private JFrame parentFrame;

		/**
		 * コンストラクタ
		 * @param frame
		 */
		private CustomSqlMakerActionListener(JFrame parentFrame) {
			super();
			this.parentFrame = parentFrame;
		}

		/**
		 * ボタン押下後アクション
		 */
		public void actionPerformed(ActionEvent e) {
			CustomSqlEntityMakerFrame frame = new CustomSqlEntityMakerFrame();
			frame.setVisible(true);
			parentFrame.setVisible(false);
		}

	}

}
