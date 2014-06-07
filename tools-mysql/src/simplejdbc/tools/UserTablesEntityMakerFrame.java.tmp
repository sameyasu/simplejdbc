package simplejdbc.tools;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import simplejdbc.exception.SimpleJdbcRuntimeException;

/**
 * ORACELのUSER_TABLESビューからエンティティ自動生成を行うツールのフレーム
 * @author yasu
 *
 */
public class UserTablesEntityMakerFrame extends JFrame implements ActionListener, ChangeListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -967131457263408484L;

	/**
	 * リソース取得
	 */
	protected static final ResourceBundle defaultValueResource = ResourceBundle.getBundle("tools");

	/**
	 * DB接続ホスト
	 */
	protected JTextField dbHostText;

	/**
	 * DB接続ポート番号
	 */
	protected JTextField dbPortText;

	/**
	 * ORACLE_SID
	 */
	protected JTextField dbNameText;

	/**
	 * DB接続ユーザ
	 */
	protected JTextField dbUserText;

	/**
	 * DB接続ユーザ
	 */
	protected JTextField dbPasswordText;

	/**
	 * エンティティ生成チェックボックス
	 */
	protected JCheckBox notGenEntityCheckBox;

	/**
	 * エンティティ生成チェックボックス
	 */
	protected JCheckBox notGenDaoCheckBox;

	/**
	 * エンティティパッケージ
	 */
	protected JTextField entityPackageText;

	/**
	 * Daoパッケージ
	 */
	protected JTextField daoPackageText;

	/**
	 * ソース生成ディレクトリ
	 */
	protected JTextField directoryText;

	/**
	 * ソースファイル文字コード
	 */
	protected JTextField encodingText;

	/**
	 * 実行ボタン
	 */
	protected JButton execButton;

	/**
	 * コンストラクタ
	 * @throws HeadlessException
	 */
	public UserTablesEntityMakerFrame() throws HeadlessException {
		this("テーブルエンティティ自動生成ツール");
	}


	/**
	 * コンストラクタ
	 * @param gc
	 */
	protected UserTablesEntityMakerFrame(GraphicsConfiguration gc) {
		super(gc);
	}

	/**
	 * コンストラクタ
	 * @param title
	 * @throws HeadlessException
	 */
	protected UserTablesEntityMakerFrame(String title) throws HeadlessException {
		super(title);

		this.setSize(700, 350);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		short baseRowHeight = 40;

	    Container contentPane = this.getContentPane();
	    this.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		headerPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 5));

		JPanel dbInfoPanel = new JPanel();
		dbInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		dbInfoPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, baseRowHeight));

		JLabel dbHostLabel = new JLabel("DBホスト(IPアドレス)");
		dbHostText = new JTextField(defaultValueResource.getString("db.host"));
		dbHostText.setColumns(15);

		JLabel dbPortLabel = new JLabel("接続ポート");
		dbPortText = new JTextField(defaultValueResource.getString("db.port"));
		dbPortText.setColumns(5);

		JLabel sidLabel = new JLabel("データベース");
		dbNameText = new JTextField(defaultValueResource.getString("db.name"));
		dbNameText.setColumns(8);

		JLabel dbUserLabel = new JLabel("DB接続ユーザ");
		dbUserText = new JTextField(defaultValueResource.getString("db.user"));
		dbUserText.setColumns(10);

		JLabel dbPasswordLabel = new JLabel("DB接続パスワード");
		dbPasswordText = new JTextField(defaultValueResource.getString("db.password"));
		dbPasswordText.setColumns(10);

		dbInfoPanel.add(dbHostLabel);
		dbInfoPanel.add(dbHostText);
		dbInfoPanel.add(dbPortLabel);
		dbInfoPanel.add(dbPortText);
		dbInfoPanel.add(sidLabel);
		dbInfoPanel.add(dbNameText);

		JPanel dbInfo2Panel = new JPanel();
		dbInfo2Panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		dbInfo2Panel.setMaximumSize(new Dimension(Short.MAX_VALUE, baseRowHeight));

		dbInfo2Panel.add(dbUserLabel);
		dbInfo2Panel.add(dbUserText);
		dbInfo2Panel.add(dbPasswordLabel);
		dbInfo2Panel.add(dbPasswordText);

		JPanel spacerPanel = new JPanel();
		spacerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		spacerPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 10));

		JPanel entityPackagePanel = new JPanel();
		entityPackagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		entityPackagePanel.setMaximumSize(new Dimension(Short.MAX_VALUE, baseRowHeight));

		JLabel entityPackageLabel = new JLabel("Entityクラス生成パッケージ");
		entityPackageText = new JTextField(defaultValueResource.getString("package.entity"));
		entityPackageText.setColumns(15);
		notGenEntityCheckBox = new JCheckBox("Entityクラスを自動生成しない");
		notGenEntityCheckBox.addChangeListener(this);

		entityPackagePanel.add(entityPackageLabel);
		entityPackagePanel.add(entityPackageText);
		entityPackagePanel.add(notGenEntityCheckBox);


		JPanel daoPackagePanel = new JPanel();
		daoPackagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		daoPackagePanel.setMaximumSize(new Dimension(Short.MAX_VALUE, baseRowHeight));

		JLabel daoPackageLabel = new JLabel("Daoクラス生成パッケージ");
		daoPackageText = new JTextField(defaultValueResource.getString("package.dao"));
		daoPackageText.setColumns(15);
		notGenDaoCheckBox = new JCheckBox("Daoクラスを自動生成しない");
		notGenDaoCheckBox.addChangeListener(this);

		daoPackagePanel.add(daoPackageLabel);
		daoPackagePanel.add(daoPackageText);
		daoPackagePanel.add(notGenDaoCheckBox);

		JPanel directoryPanel = new JPanel();
		directoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		directoryPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, baseRowHeight));

		JLabel directoryLabel = new JLabel("ソース生成フォルダ");
		directoryText = new JTextField(defaultValueResource.getString("source.file.prefix"));
		directoryText.setColumns(40);

		directoryPanel.add(directoryLabel);
		directoryPanel.add(directoryText);

		JPanel encodingPanel = new JPanel();
		encodingPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		encodingPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, baseRowHeight));

		JLabel encodingLabel = new JLabel("ソースファイル文字コード");
		encodingText = new JTextField(defaultValueResource.getString("source.file.encoding"));
		encodingText.setColumns(10);

		encodingPanel.add(encodingLabel);
		encodingPanel.add(encodingText);

		JPanel execButtonPanel = new JPanel();
		execButtonPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));

		execButton = new JButton("テーブル一覧を取得");
		execButton.addActionListener(this);
		execButtonPanel.add(execButton);

		//メインのパネルにセット
	    contentPane.add(headerPanel);
	    contentPane.add(dbInfoPanel);
	    contentPane.add(dbInfo2Panel);
	    contentPane.add(spacerPanel);
	    contentPane.add(entityPackagePanel);
	    contentPane.add(daoPackagePanel);
	    contentPane.add(directoryPanel);
	    contentPane.add(encodingPanel);
	    contentPane.add(execButtonPanel);
	}

	/**
	 * コンストラクタ
	 * @param title
	 * @param gc
	 */
	protected UserTablesEntityMakerFrame(String title, GraphicsConfiguration gc) {
		super(title, gc);
	}

	/**
	 * 自動生成ボタンを押下した時の処理メソッド
	 */
	public void actionPerformed(ActionEvent e) {

		ToolsProperties properties = new ToolsProperties();
		properties.setDbHost(this.dbHostText.getText());
		properties.setDbPort(this.dbPortText.getText());
		properties.setDbName(this.dbNameText.getText());
		properties.setDbUser(this.dbUserText.getText());
		properties.setDbPassword(this.dbPasswordText.getText());
		properties.setEntityPackage(this.entityPackageText.getText());
		properties.setDaoPackage(this.daoPackageText.getText());
		properties.setSourceFilePrefix(this.directoryText.getText());
		properties.setSourceEncoding(this.encodingText.getText());
		properties.setGenEntity(!this.notGenEntityCheckBox.isSelected());
		properties.setGenDao(!this.notGenDaoCheckBox.isSelected());
		properties.setDaoSuffix(defaultValueResource.getString("dao.suffix"));

		if(this.notGenDaoCheckBox.isSelected() && this.notGenEntityCheckBox.isSelected()){
			JFrame frame = new JFrame("エラー");
			frame.setSize(250, 60);
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			frame.setAlwaysOnTop(true);
			frame.setLocationRelativeTo(null);

			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

			JPanel messagePanel = new JPanel();
			messagePanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));
			messagePanel.setAlignmentX(0.5f);

			JLabel messageLabel = new JLabel("生成するクラスがありません。");

			messagePanel.add(messageLabel);

			panel.add(messagePanel);

			frame.getContentPane().add(panel, BorderLayout.CENTER);
			frame.setVisible(true);
		}
		else {
			List<TableInfo> tableInfoList;
			try {
				tableInfoList = UserTablesEntityMaker.getTableList(properties);
			} catch (SimpleJdbcRuntimeException ex) {
				JDialog dialog = new JDialog(this);
				dialog.setTitle("エラー");
				dialog.setAlwaysOnTop(true);
				dialog.setSize(400, 100);
				dialog.setLocationRelativeTo(null);

				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

				JPanel messagePanel = new JPanel();
				messagePanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 80));
				messagePanel.setAlignmentX(0.5f);

				JLabel messageLabel = new JLabel(ex.getMessage());
				messagePanel.add(messageLabel);

				panel.add(messagePanel);

				dialog.getContentPane().add(panel);
				dialog.setVisible(true);
				ex.printStackTrace(System.out);
				return;
			}
			TableListFrame frame = new TableListFrame(properties, tableInfoList);
			frame.setVisible(true);
		}
	}

	/**
	 * 自動生成しないのチェックボックスを選択したときの処理メソッド
	 * @param e イベント
	 */
	public void stateChanged(ChangeEvent e) {

		if(this.notGenEntityCheckBox.isSelected()) {
			this.entityPackageText.setEnabled(false);
		}
		else {
			if(this.entityPackageText.isEnabled() == false){
				this.entityPackageText.setEnabled(true);
			}
		}

		if(this.notGenDaoCheckBox.isSelected()) {
			this.daoPackageText.setEnabled(false);
		}
		else {
			if(this.daoPackageText.isEnabled() == false){
				this.daoPackageText.setEnabled(true);
			}
		}

	}


}
