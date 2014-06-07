package simplejdbc.tools;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import simplejdbc.exception.SimpleJdbcRuntimeException;

/**
 * テーブル一覧のフレームクラス
 * @author yasu
 *
 */
public class TableListFrame extends JFrame implements ActionListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5356141560993708970L;

	/**
	 * プロパティ
	 */
	private ToolsProperties properties;

	/**
	 * テーブル情報のリスト
	 */
	private List<TableInfo> tableInfoList;

	/**
	 * コンストラクタ
	 * @param title
	 * @throws HeadlessException
	 */
	protected TableListFrame(String title) throws HeadlessException {
		super(title);
	}

	/**
	 *
	 * @param properties
	 * @param tableInfoList
	 * @throws HeadlessException
	 */
	public TableListFrame(ToolsProperties properties, List<TableInfo> tableInfoList) throws HeadlessException {
		super("テーブル一覧");

		this.properties = properties;
		this.tableInfoList = tableInfoList;

		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setMaximumSize(new Dimension(800, 600));
		this.setLocationRelativeTo(null);

	    Container contentPane = this.getContentPane();

	    GridBagLayout layout = new GridBagLayout();
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.anchor = GridBagConstraints.CENTER;

	    this.setLayout(layout);

	    TableListTableModel tableModel = new TableListTableModel(tableInfoList);

	    JTable table = new JTable(tableModel);
	    table.setOpaque(true);

	    DefaultTableColumnModel columnModel = (DefaultTableColumnModel)table.getColumnModel();
	    TableColumn column = null;
		column = columnModel.getColumn(0);
		column.setPreferredWidth(160);
		column.setHeaderValue("テーブル名");
		column = columnModel.getColumn(1);
		column.setPreferredWidth(160);
		column.setHeaderValue("Entityクラス名");
		column = columnModel.getColumn(2);
		column.setPreferredWidth(160);
		column.setHeaderValue("Daoクラス名");
		column = columnModel.getColumn(3);
		column.setPreferredWidth(90);
		column.setHeaderValue("作成する");

	    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    JScrollPane scrollPane = new JScrollPane(table);
	    scrollPane.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridheight = 4;
	    gbc.weightx = 0.9d;
	    gbc.weighty = 1.0d;
	    gbc.insets = new Insets(10, 20, 10, 10);
	    layout.addLayoutComponent(scrollPane, gbc);

	    JPanel allSelectPanel = new JPanel();
	    allSelectPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	    JButton allSelectButton = new JButton("全て選択");
	    allSelectButton.addActionListener(new AllSelectButtonActionListener(table));
	    allSelectPanel.add(allSelectButton);

	    gbc.gridx = 1;
	    gbc.gridy = 0;
	    gbc.gridheight = 1;
	    gbc.weightx = 0.1d;
	    gbc.weighty = 0.025d;
	    gbc.insets = new Insets(10, 10, 10, 10);
	    layout.addLayoutComponent(allSelectPanel, gbc);

	    JPanel allCancelPanel = new JPanel();
	    allCancelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	    JButton allCancelButton = new JButton("全て解除");
	    allCancelButton.addActionListener(new AllCancelButtonActionListener(table));
	    allCancelPanel.add(allCancelButton);

	    gbc.gridx = 1;
	    gbc.gridy = 1;
	    gbc.gridheight = 1;
	    gbc.weighty = 0.025d;
	    gbc.insets = new Insets(10, 10, 10, 10);
	    layout.addLayoutComponent(allCancelPanel, gbc);

	    JPanel spacerPanel = new JPanel();
	    spacerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	    spacerPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

	    gbc.gridx = 1;
	    gbc.gridy = 2;
	    gbc.gridheight = 1;
	    gbc.weighty = 0.9d;
	    gbc.insets = new Insets(10, 10, 10, 10);
	    layout.addLayoutComponent(spacerPanel, gbc);

	    JPanel execPanel = new JPanel();
	    execPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	    JButton execButton = new JButton("自動生成実行");
	    execButton.addActionListener(this);
	    execPanel.add(execButton);

	    gbc.gridx = 1;
	    gbc.gridy = 3;
	    gbc.gridheight = 1;
	    gbc.weighty = 0.05d;
	    gbc.insets = new Insets(10, 10, 10, 10);
	    layout.addLayoutComponent(execPanel, gbc);

	    contentPane.add(scrollPane);
	    contentPane.add(allSelectPanel);
	    contentPane.add(allCancelPanel);
	    contentPane.add(spacerPanel);
	    contentPane.add(execPanel);
	}

	/**
	 * 自動生成実行ボタンアクション
	 */
	public void actionPerformed(ActionEvent e) {

		JFrame frame = new JFrame("自動生成");
		frame.setSize(350, 150);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		JButton okButton = new JButton("アプリケーションを終了");
		okButton.setAlignmentX(0.5f);
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		okButton.setEnabled(false);	//最初はボタン押下無効

		JPanel messagePanel = new JPanel();
		messagePanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 70));
		messagePanel.setAlignmentX(0.5f);

		JLabel messageLabel = new JLabel("Javaソースファイルを自動生成中です。");

		messagePanel.add(messageLabel);

		JProgressBar progressBar = new JProgressBar();
		//progressBar.setSize(new Dimension(150, 20));
		progressBar.setPreferredSize(new Dimension(250, 20));
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		progressBar.addChangeListener(new ProgressBarChangeListener(messageLabel, okButton));

		messagePanel.add(messageLabel);
		messagePanel.add(progressBar);

		panel.add(messagePanel);
		panel.add(okButton);

		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);

		//実行
		try {
			Thread thread = new Thread(new UserTablesEntityMaker(properties, tableInfoList, progressBar));
			thread.start();
		} catch (SimpleJdbcRuntimeException e1) {
			messageLabel.setText("自動生成に失敗しました。");
			e1.printStackTrace();
		}
	}


	/**
	 * 進捗バー変更リスナー
	 * @author yasu
	 *
	 */
	private static class ProgressBarChangeListener implements ChangeListener {

		/**
		 * ラベル
		 */
		private JLabel messageLabel;

		/**
		 * ボタン
		 */
		private JButton button;

		/**
		 * コンストラクタ
		 * @param frame
		 */
		private ProgressBarChangeListener(JLabel messageLabel, JButton button) {
			super();
			this.messageLabel = messageLabel;
			this.button = button;
		}

		/**
		 * 変更時実行メソッド
		 */
		public void stateChanged(ChangeEvent e) {
			JProgressBar progressBar = (JProgressBar) e.getSource();
			if(progressBar.getValue() == progressBar.getMaximum()) {
				this.messageLabel.setText("自動生成が正常に完了しました。");
				this.button.setEnabled(true);
			}
		}

	}

	/**
	 * 全て選択ボタンリスナー
	 * @author yasu
	 *
	 */
	private static class AllSelectButtonActionListener implements ActionListener {

		private JTable table;

		/**
		 * コンストラクタ
		 * @param table
		 */
		public AllSelectButtonActionListener(JTable table) {
			super();
			this.table = table;
		}

		public void actionPerformed(ActionEvent e) {
			for(int i=0; i<table.getRowCount(); i++) {
				table.setValueAt(Boolean.TRUE, i, 3);
			}
		}
	}

	/**
	 * 全て解除ボタンリスナー
	 * @author yasu
	 *
	 */
	private static class AllCancelButtonActionListener implements ActionListener {

		/**
		 *
		 */
		private JTable table;

		/**
		 * コンストラクタ
		 * @param table
		 */
		public AllCancelButtonActionListener(JTable table) {
			super();
			this.table = table;
		}

		public void actionPerformed(ActionEvent e) {
			for(int i=0; i<table.getRowCount(); i++) {
				table.setValueAt(Boolean.FALSE, i, 3);
			}
		}
	}
}
