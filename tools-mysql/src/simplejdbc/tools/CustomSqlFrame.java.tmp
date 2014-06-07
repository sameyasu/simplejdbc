package simplejdbc.tools;

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
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import simplejdbc.exception.SimpleJdbcRuntimeException;
import simplejdbc.tools.windows.ExplorerOpener;

/**
 * カスタマイズSQL文入力フレーム
 * @author yasu
 *
 */
public class CustomSqlFrame extends JFrame {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -9127586680777286272L;

	/**
	 * リソース取得
	 */
	private static final ResourceBundle defaultValueResource = ResourceBundle.getBundle("tools");


	private JTextArea sqlTextArea ;

	private FieldListTableModel tableModel;

	private JButton allSelectButton;

	private JButton allCancelButton;

	private JTextField entityClassText;

	private JTextField daoClassText;

	private JButton resetButton;

	private JButton execButton;

	/**
	 * ツールプロパティ
	 */
	private ToolsProperties properties;

	/**
	 * コンストラクタ
	 * @throws HeadlessException
	 */
	public CustomSqlFrame(ToolsProperties properties) throws HeadlessException {
		super("カスタムSQL文からエンティティ自動生成");

		this.properties = properties;
		this.setSize(900, 720);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();

	    Container contentPane = this.getContentPane();
	    this.setLayout(layout);

	    JPanel sqlPanel = new JPanel();
	    sqlPanel.setLayout(new BoxLayout(sqlPanel, BoxLayout.PAGE_AXIS));

	    JLabel sqlInputLabel = new JLabel("SQL文");
	    sqlInputLabel.setAlignmentX(0.0f);
	    sqlInputLabel.setAlignmentY(0.0f);
	    sqlTextArea = new JTextArea(27, 30);
	    JScrollPane scrollPane = new JScrollPane(sqlTextArea);
	    sqlPanel.add(sqlInputLabel);
	    sqlPanel.add(Box.createRigidArea(new Dimension(5,5)));
	    sqlPanel.add(scrollPane);

	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridheight = 3;
	    gbc.gridwidth = 1;
	    gbc.weightx = 0.5d;
	    gbc.weighty = 0.7d;
	    gbc.insets = new Insets(10, 10, 10, 10);
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.anchor = GridBagConstraints.CENTER;

	    layout.addLayoutComponent(sqlPanel, gbc);

	    JPanel resultPanel = new JPanel();
	    resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.PAGE_AXIS));

	    JLabel resultLabel = new JLabel("SQL結果カラムリスト");
	    resultLabel.setAlignmentX(0.0f);
	    resultLabel.setAlignmentY(0.0f);


	    tableModel = new FieldListTableModel();

	    JTable table = new JTable(tableModel);
	    TableColumnModel columnModel = table.getColumnModel();
	    TableColumn column = columnModel.getColumn(0);
	    column.setHeaderValue("カラム名");
	    column.setPreferredWidth(100);
	    column = columnModel.getColumn(1);
	    column.setHeaderValue("フィールド名");
	    column.setPreferredWidth(100);
	    column = columnModel.getColumn(2);
	    column.setHeaderValue("Javaクラス");
	    column.setPreferredWidth(100);
	    column = columnModel.getColumn(3);
	    column.setHeaderValue("生成する");
	    column.setPreferredWidth(30);

	    JScrollPane tableScroll = new JScrollPane(table);
	    resultPanel.add(resultLabel);
	    resultPanel.add(Box.createRigidArea(new Dimension(5,5)));
	    resultPanel.add(tableScroll);

	    gbc.gridx = 1;
	    gbc.gridy = 0;
	    gbc.gridheight = 1;
	    gbc.gridwidth = 1;
	    gbc.weightx = 0.5d;
	    gbc.weighty = 0.5d;
	    gbc.insets = new Insets(10, 10, 10, 10);
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.anchor = GridBagConstraints.CENTER;

	    layout.addLayoutComponent(resultPanel, gbc);


	    JPanel execSqlPanel = new JPanel();
	    JButton execSqlButton = new JButton("SQL発行してカラム解析");
	    execSqlButton.addActionListener(new ExecSqlButtonActionListener(this));
	    execSqlPanel.add(execSqlButton);

	    gbc.gridx = 0;
	    gbc.gridy = 3;
	    gbc.gridheight = 1;
	    gbc.gridwidth = 1;
	    gbc.weighty = 0.1d;
	    gbc.insets = new Insets(2, 10, 10, 10);
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.anchor = GridBagConstraints.NORTH;

	    layout.addLayoutComponent(execSqlPanel, gbc);

	    JPanel selectButtonPanel = new JPanel();
	    allSelectButton = new JButton("全て選択");
	    allSelectButton.setEnabled(false);
	    allSelectButton.addActionListener(new AllSelectButtonActionListener(table));
	    allCancelButton = new JButton("全て解除");
	    allCancelButton.setEnabled(false);
	    allCancelButton.addActionListener(new AllCancelButtonActionListener(table));

	    selectButtonPanel.add(allSelectButton);
	    selectButtonPanel.add(allCancelButton);

	    gbc.gridx = 1;
	    gbc.gridy = 1;
	    gbc.gridheight = 1;
	    gbc.gridwidth = 1;
	    gbc.weighty = 0.05d;
	    gbc.insets = new Insets(2, 10, 10, 10);
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.anchor = GridBagConstraints.EAST;

	    layout.addLayoutComponent(selectButtonPanel, gbc);

	    JPanel classPanel = new JPanel();
	    classPanel.setLayout(new BoxLayout(classPanel, BoxLayout.PAGE_AXIS));

	    JPanel entityClassPanel = new JPanel();
	    entityClassPanel.setAlignmentX(0.0f);
	    entityClassPanel.setLayout(new FlowLayout());
	    JLabel entityClassLabel = new JLabel("Entityクラス名");
	    entityClassText = new JTextField(defaultValueResource.getString("customsql.entity.class.name"));
	    entityClassText.setColumns(30);
	    entityClassPanel.add(entityClassLabel);
	    entityClassPanel.add(entityClassText);


	    JPanel daoClassPanel = new JPanel();
	    daoClassPanel.setAlignmentX(0.0f);
	    daoClassPanel.setLayout(new FlowLayout());
	    JLabel daoClassLabel = new JLabel("Daoクラス名");
	    daoClassText = new JTextField(defaultValueResource.getString("customsql.dao.class.name"));
	    daoClassText.setColumns(30);

	    daoClassPanel.add(daoClassLabel);
	    daoClassPanel.add(daoClassText);

	    classPanel.add(entityClassPanel);
	    classPanel.add(daoClassPanel);

	    gbc.gridx = 1;
	    gbc.gridy = 2;
	    gbc.gridheight = 1;
	    gbc.gridwidth = 1;
	    gbc.weighty = 0.1d;
	    gbc.insets = new Insets(2, 10, 10, 2);
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.anchor = GridBagConstraints.WEST;

	    layout.addLayoutComponent(classPanel, gbc);

	    JPanel execPanel = new JPanel();
	    execPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    resetButton = new JButton("SQL文クリア");
	    resetButton.addActionListener(new ResetButtonActionListener(this));
	    execPanel.add(resetButton);

	    execButton = new JButton("自動生成実行");
	    execButton.setEnabled(false);
	    execButton.addActionListener(new ExecButtonActionListener(this));
	    execPanel.add(execButton);

	    gbc.gridx = 1;
	    gbc.gridy = 3;
	    gbc.gridheight = 1;
	    gbc.gridwidth = 1;
	    gbc.weighty = 0.1d;
	    gbc.insets = new Insets(2, 10, 10, 10);
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.anchor = GridBagConstraints.EAST;

	    layout.addLayoutComponent(execPanel, gbc);

	    contentPane.add(sqlPanel);
	    contentPane.add(resultPanel);
	    contentPane.add(execSqlPanel);
	    contentPane.add(selectButtonPanel);
	    contentPane.add(classPanel);
	    contentPane.add(execPanel);
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

	/**
	 * SQL発行ボタンリスナー
	 * @author yasu
	 *
	 */
	private static class ExecSqlButtonActionListener implements ActionListener {

		/**
		 * フレーム
		 */
		private CustomSqlFrame frame;

		/**
		 * コンストラクタ
		 * @param table
		 */
		public ExecSqlButtonActionListener(CustomSqlFrame frame) {
			this.frame = frame;
		}

		public void actionPerformed(ActionEvent e) {

			//SELECTから始まっていないSQLは無効
			if(this.frame.sqlTextArea.getText().toUpperCase(Locale.US).startsWith("SELECT") == false &&
					this.frame.sqlTextArea.getText().toUpperCase(Locale.US).startsWith("SHOW") == false &&
						this.frame.sqlTextArea.getText().toUpperCase(Locale.US).startsWith("DESC") == false){
				JDialog dialog = new JDialog(this.frame);
				dialog.setTitle("エラー");
				dialog.setAlwaysOnTop(true);
				dialog.setSize(400, 60);
				dialog.setLocationRelativeTo(null);

				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

				JPanel messagePanel = new JPanel();
				messagePanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));
				messagePanel.setAlignmentX(0.0f);

				JLabel messageLabel = new JLabel("SQL文が無効です。SQLはSELECT文にしか対応していません。");
				messagePanel.add(messageLabel);

				panel.add(messagePanel);

				dialog.getContentPane().add(panel);
				dialog.setVisible(true);
				return;
			}

			List<FieldInfo> fieldList;
			try {
				fieldList = CustomSqlEntityMaker.executeSelect(this.frame.properties, this.frame.sqlTextArea.getText());
			} catch (SimpleJdbcRuntimeException ex) {
				//例外処理
				JDialog dialog = new JDialog(this.frame);
				dialog.setTitle("エラー");
				dialog.setAlwaysOnTop(true);
				dialog.setSize(400, 100);
				dialog.setLocationRelativeTo(null);

				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

				JPanel messagePanel = new JPanel();
				messagePanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 80));
				messagePanel.setAlignmentX(0.0f);

				JLabel messageLabel = new JLabel(ex.getMessage());
				messagePanel.add(messageLabel);

				JLabel messageDetailLabel = new JLabel(ex.getCause().getMessage());
				messagePanel.add(messageDetailLabel);

				panel.add(messagePanel);

				dialog.getContentPane().add(panel);
				dialog.setVisible(true);

				ex.printStackTrace(System.out);
				return;
			}
			this.frame.tableModel.setFieldList(fieldList);
			this.frame.allSelectButton.setEnabled(true);
			this.frame.allCancelButton.setEnabled(true);
			this.frame.execButton.setEnabled(true);
		}
	}

	/**
	 * SQL発行ボタンリスナー
	 * @author yasu
	 *
	 */
	private static class ResetButtonActionListener implements ActionListener {

		/**
		 * フレーム
		 */
		private CustomSqlFrame frame;

		/**
		 * コンストラクタ
		 * @param table
		 */
		public ResetButtonActionListener(CustomSqlFrame frame) {
			this.frame = frame;
		}

		public void actionPerformed(ActionEvent e) {
			this.frame.tableModel.clear();
			this.frame.entityClassText.setText(defaultValueResource.getString("customsql.entity.class.name"));
			this.frame.daoClassText.setText(defaultValueResource.getString("customsql.dao.class.name"));
			this.frame.allSelectButton.setEnabled(false);
			this.frame.allCancelButton.setEnabled(false);
			this.frame.execButton.setEnabled(false);
		}
	}


	/**
	 * 自動生成実行ボタンリスナー
	 * @author yasu
	 *
	 */
	private static class ExecButtonActionListener implements ActionListener {

		/**
		 * フレーム
		 */
		private CustomSqlFrame frame;

		/**
		 * コンストラクタ
		 * @param table
		 */
		public ExecButtonActionListener(CustomSqlFrame frame) {
			this.frame = frame;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				CustomSqlEntityMaker.executeMake(this.frame.properties, this.frame.tableModel.getFieldList(), this.frame.entityClassText.getText(), this.frame.daoClassText.getText());
				ExplorerOpener.open(this.frame.properties.getSourceFilePrefix());
			} catch (SimpleJdbcRuntimeException ex) {
				//例外処理
				JDialog dialog = new JDialog(this.frame);
				dialog.setTitle("エラー");
				dialog.setAlwaysOnTop(true);
				dialog.setSize(400, 100);
				dialog.setLocationRelativeTo(null);

				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

				JPanel messagePanel = new JPanel();
				messagePanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 80));
				messagePanel.setAlignmentX(0.0f);

				JLabel messageLabel = new JLabel(ex.getMessage());
				messagePanel.add(messageLabel);

				JLabel messageDetailLabel = new JLabel(ex.getCause().getMessage());
				messagePanel.add(messageDetailLabel);

				panel.add(messagePanel);

				dialog.getContentPane().add(panel);
				dialog.setVisible(true);
				return;
			}

			//例外処理
			JDialog dialog = new JDialog(this.frame);
			dialog.setTitle("自動生成成功");
			dialog.setAlwaysOnTop(true);
			dialog.setSize(400, 100);
			dialog.setLocationRelativeTo(null);

			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

			JPanel messagePanel = new JPanel();
			messagePanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 80));
			messagePanel.setAlignmentX(0.0f);

			JLabel messageLabel = new JLabel("Javaソースの自動生成に成功しました。");
			messagePanel.add(messageLabel);
			panel.add(messagePanel);

			dialog.getContentPane().add(panel);
			dialog.setVisible(true);
			return;
		}
	}
}
