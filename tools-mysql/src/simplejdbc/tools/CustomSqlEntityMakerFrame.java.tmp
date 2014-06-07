package simplejdbc.tools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeListener;

import simplejdbc.exception.SimpleJdbcRuntimeException;

/**
 * ORACELのUSER_TABLESビューからエンティティ自動生成を行うツールのフレーム
 * @author yasu
 *
 */
public class CustomSqlEntityMakerFrame extends UserTablesEntityMakerFrame implements ActionListener, ChangeListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4155263858435112887L;

	/**
	 * コンストラクタ
	 * @throws HeadlessException
	 */
	public CustomSqlEntityMakerFrame() throws HeadlessException {
		super("カスタマイズSQL文エンティティ自動生成ツール");
		this.execButton.setText("SQL入力へ");
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
			try {
				CustomSqlEntityMaker.checkConnect(properties);
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
			CustomSqlFrame frame = new CustomSqlFrame(properties);
			frame.setVisible(true);
		}
	}

}
