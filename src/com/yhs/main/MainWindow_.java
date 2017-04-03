package com.yhs.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.yhs.abstract_extract.S_AbstractExtract;
import com.yhs.listener.CP_BtnListener;
import com.yhs.listener.M_CP_BtnListener;
import com.yhs.listener.MultipleBtnListener;
import com.yhs.listener.PP_BtnListener;
import com.yhs.listener.SingleBtnListener;
import java.awt.Color;
import java.awt.SystemColor;

/** 
 * @author YHS
 * @version 创建时间：2017年3月26日 下午1:53:17 
 * 类说明 
 */
public class MainWindow_ {

	private JFrame frame;
	private JTextField textField_filename;
	private JTextField m_textField_filename;

//	private String xpath = null;//文件路径
	static private String content = null;//文本内容
//	private String wf_save_fileName = null;	//获取保存文本名称
	
	static String self_save_filepath = null;//用户自定义文件保存路径
	
	static int ranking = 0;//选择的频率
	
	private JScrollPane scrollPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow_ window = new MainWindow_();
					window.frame.setVisible(true);
					window.frame.setResizable(false);//不可改变窗口大小
					window.frame.setLocation(500, 200);//窗口出现的位置
					window.frame.setIconImage(Toolkit.getDefaultToolkit()
							  .createImage("D:/works/muc_workspace/Key Phrases Extraction/src/Picture/flag.jpg"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow_() {
		// 设置对话框的风格  
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {  
		   // TODO Auto-generated catch block  
		   e1.printStackTrace();  
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 868, 816);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		/*
		 * 以下是单个文件处理Tab
		 */
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		tabbedPane.addTab("单个文件处理", null, panel, null);
		panel.setLayout(null);
		
		JLabel label = new JLabel("所选文件");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label.setBounds(10, 10, 78, 22);
		panel.add(label);
		
		textField_filename = new JTextField();
		textField_filename.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_filename.setColumns(10);
		textField_filename.setBounds(10, 42, 711, 33);
		panel.add(textField_filename);
		
		JButton btn_choose = new JButton("选择文件");
		btn_choose.addActionListener(new SingleBtnListener(btn_choose,textField_filename));// 添加监听事件
		
		
		btn_choose.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btn_choose.setBounds(731, 42, 101, 33);
		panel.add(btn_choose);
		
		JLabel label_1 = new JLabel("统计词频");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_1.setBounds(20, 103, 78, 22);
		panel.add(label_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 91, 822, 2);
		panel.add(separator);
		
		JLabel label_2 = new JLabel("匹配关键词");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_2.setBounds(435, 108, 91, 22);
		panel.add(label_2);
		
		JTextArea textArea_wordfrequency = new JTextArea();
		textArea_wordfrequency.setLineWrap(true);
		textArea_wordfrequency.setFont(new Font("Microsoft Himalaya", Font.PLAIN, 26));
		scrollPane = new JScrollPane(textArea_wordfrequency);
		scrollPane.setBounds(20, 135, 393, 263);
		panel.add(scrollPane);
		
		JTextArea textArea_keyword = new JTextArea();
		textArea_keyword.setLineWrap(true);
		textArea_keyword.setFont(new Font("Microsoft Himalaya", Font.PLAIN, 26));
		scrollPane = new JScrollPane(textArea_keyword);
		scrollPane.setBounds(438, 136, 396, 263);
		panel.add(scrollPane);
		
		JButton btn_begain = new JButton("开始统计");
		btn_begain.addActionListener(new CP_BtnListener(btn_begain, textArea_wordfrequency));
		
		
		btn_begain.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btn_begain.setBounds(312, 103, 101, 28);
		panel.add(btn_begain);
		
		JButton btn_pipie = new JButton("开始匹配");
		btn_pipie.addActionListener(new PP_BtnListener(btn_pipie, textArea_keyword));// 添加监听事件
		
		btn_pipie.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btn_pipie.setBounds(731, 103, 101, 28);
		panel.add(btn_pipie);
		
		final JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Microsoft Himalaya", Font.PLAIN, 26));
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(20, 465, 812, 255);
		panel.add(scrollPane);
		
		JButton button = new JButton("生成摘要");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<String> sentenceList = new  ArrayList<String>();//保存提取的句子
				textArea.setText("");
				S_AbstractExtract ae = new S_AbstractExtract();
				String xpath = SingleBtnListener.getXpath();//获取路径
				String wf_save_fileName = SingleBtnListener.getWf_save_fileName();	//获取保存文本名称
				List<String>  pipeiWord_cp = new ArrayList<String>();  //保存关键词及词频统计的词，用于匹配句子，传递给AbstractExtract类
				List<String>  pipeiWord_key = new ArrayList<String>();  //保存关键词及词频统计的词，用于匹配句子，传递给AbstractExtract类
				pipeiWord_cp = CP_BtnListener.getPipeiWord_cp();
				pipeiWord_key = PP_BtnListener.getPipeiWord_key();
				String path = xpath.replace("已", "未");
				String title = SingleBtnListener.getTitle();
				
				System.out.println(path);
				try {
					ae.Abstract(path, pipeiWord_cp,pipeiWord_key,title,wf_save_fileName,
							SingleBtnListener.getDigest_root_path());
					pipeiWord_cp.clear();
					pipeiWord_key.clear();
					sentenceList = ae.getSentenceList();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (int i = 0; i < sentenceList.size(); i++) {
					textArea.append(sentenceList.get(i) +"།"+ "\r\n");
				}
				sentenceList.clear();
			}
		});
		button.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		button.setBounds(731, 424, 101, 28);
		panel.add(button);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 412, 822, 2);
		panel.add(separator_2);
		
		JLabel lblNewLabel = new JLabel("摘要信息");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel.setBounds(20, 433, 91, 22);
		panel.add(lblNewLabel);
		
		
		/*
		 * 以下是批处理Tab
		 */
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("批处理", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel label_3 = new JLabel("所选文件");
		label_3.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_3.setBounds(10, 10, 78, 22);
		panel_1.add(label_3);
		
		m_textField_filename = new JTextField();
		m_textField_filename.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		m_textField_filename.setColumns(10);
		m_textField_filename.setBounds(10, 42, 688, 33);
		panel_1.add(m_textField_filename);
		
		JButton m_btn_choose = new JButton("选择文件夹");
		m_btn_choose.addActionListener(new MultipleBtnListener(m_btn_choose, m_textField_filename));
		
		m_btn_choose.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		m_btn_choose.setBounds(721, 42, 113, 33);
		panel_1.add(m_btn_choose);
		
		JLabel label_4 = new JLabel("词频显示");
		label_4.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_4.setBounds(20, 108, 78, 22);
		panel_1.add(label_4);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 91, 822, 2);
		panel_1.add(separator_1);
		
		JLabel label_5 = new JLabel("关键词显示");
		label_5.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_5.setBounds(442, 108, 91, 22);
		panel_1.add(label_5);
		
		JTextArea m_textArea_wordfrequency = new JTextArea();
		m_textArea_wordfrequency.setLineWrap(true);
		m_textArea_wordfrequency.setFont(new Font("Microsoft Himalaya", Font.PLAIN, 26));
		m_textArea_wordfrequency.setBounds(20, 140, 393, 429);
		panel_1.add(m_textArea_wordfrequency);
		
		JTextArea m_textArea_keyword = new JTextArea();
		m_textArea_keyword.setLineWrap(true);
		m_textArea_keyword.setFont(new Font("Microsoft Himalaya", Font.PLAIN, 26));
		m_textArea_keyword.setBounds(442, 140, 393, 429);
		panel_1.add(m_textArea_keyword);
		
		JButton m_btn_begain = new JButton("开始统计");
		m_btn_begain.addActionListener(new M_CP_BtnListener(m_btn_begain, m_textArea_wordfrequency,m_textArea_keyword));
		
		m_btn_begain.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		m_btn_begain.setBounds(733, 590, 101, 33);
		panel_1.add(m_btn_begain);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("菜单");
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("摘要生成");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<String> sentenceList = new  ArrayList<String>();//保存提取的句子
				textArea.setText("");
				S_AbstractExtract ae = new S_AbstractExtract();
				String xpath = SingleBtnListener.getXpath();//获取路径
				String wf_save_fileName = SingleBtnListener.getWf_save_fileName();	//获取保存文本名称
				List<String>  pipeiWord_cp = new ArrayList<String>();  //保存关键词及词频统计的词，用于匹配句子，传递给AbstractExtract类
				List<String>  pipeiWord_key = new ArrayList<String>();  //保存关键词及词频统计的词，用于匹配句子，传递给AbstractExtract类
				pipeiWord_cp = CP_BtnListener.getPipeiWord_cp();
				pipeiWord_key = PP_BtnListener.getPipeiWord_key();
				String path = xpath.replace("已", "未");
				String title = SingleBtnListener.getTitle();
				
				System.out.println(path);
				try {
					ae.Abstract(path, pipeiWord_cp,pipeiWord_key,title,
							wf_save_fileName,SingleBtnListener.getDigest_root_path());
					pipeiWord_cp.clear();
					pipeiWord_key.clear();
					sentenceList = ae.getSentenceList();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (int i = 0; i < sentenceList.size(); i++) {
					textArea.append(sentenceList.get(i) +"།"+ "\r\n");
				}
				sentenceList.clear();
			}
		});
		menu.add(menuItem);
		
		JMenuItem menuItem_2 = new JMenuItem("设置频率");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object[] opObjects = {"10","9","8","7","6","5","4","3","2","1"};
				String num = (String) JOptionPane.showInputDialog(null,
						"请选择频率",
						"请选择",
						JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(),
						opObjects,
						opObjects[0]);
				if ((num != null) && (num.length() > 0)) {
					System.out.println("message: " + num);
					ranking = Integer.parseInt(num);
				}else{
					
				}
			}
		});
		menu.add(menuItem_2);
		
		JMenuItem menuItem_5 = new JMenuItem("设置保存路径");
		menuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();//创建文件选择对话框
				
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //选择文件模式  
				int i = fileChooser.showOpenDialog(fileChooser);//显示文件选择对话框
		        File file = fileChooser.getSelectedFile();  
		        
		        self_save_filepath = file.toString();
		        
		        System.out.println("文件路径验证：" + file);
			}
		});
		menu.add(menuItem_5);
		
		JMenuItem menuItem_3 = new JMenuItem("清空"); 
		menu.add(menuItem_3);
		
		JMenuItem menuItem_4 = new JMenuItem("退出");
		menu.add(menuItem_4);
		
		JMenu menu_1 = new JMenu("帮助");
		menuBar.add(menu_1);
		
		JMenuItem menuItem_9 = new JMenuItem("查看帮助");
		menu_1.add(menuItem_9);
	}
	
	public static String getSelf_save_filepath() {
		return self_save_filepath;
	}

	public static void setSelf_save_filepath(String self_save_filepath) {
		MainWindow_.self_save_filepath = self_save_filepath;
	}
	
	
	public static int getRanking() {
		return ranking;
	}

	public static void setRanking(int ranking) {
		MainWindow_.ranking = ranking;
	}
}
