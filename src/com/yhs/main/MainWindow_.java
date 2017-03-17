package com.yhs.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.yhs.abstract_extract.AbstractExtract;
import com.yhs.listener.CP_BtnListener;
import com.yhs.listener.M_CP_BtnListener;
import com.yhs.listener.MultipleBtnListener;
import com.yhs.listener.PP_BtnListener;
import com.yhs.listener.SingleBtnListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

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
	
	static int ranking = 0;
	
	public static int getRanking() {
		return ranking;
	}

	public static void setRanking(int ranking) {
		MainWindow_.ranking = ranking;
	}

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
		frame.setBounds(100, 100, 865, 639);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		/*
		 * 以下是单个文件处理Tab
		 */
		JPanel panel = new JPanel();
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
		
		JLabel label_1 = new JLabel("词频显示");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_1.setBounds(10, 103, 78, 22);
		panel.add(label_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 91, 822, 2);
		panel.add(separator);
		
		JLabel label_2 = new JLabel("关键词显示");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_2.setBounds(435, 108, 91, 22);
		panel.add(label_2);
		
		JTextArea textArea_wordfrequency = new JTextArea();
		textArea_wordfrequency.setLineWrap(true);
		textArea_wordfrequency.setFont(new Font("Microsoft Himalaya", Font.PLAIN, 26));
		textArea_wordfrequency.setBounds(10, 135, 393, 357);
		panel.add(textArea_wordfrequency);
		
		JTextArea textArea_keyword = new JTextArea();
		textArea_keyword.setLineWrap(true);
		textArea_keyword.setFont(new Font("Microsoft Himalaya", Font.PLAIN, 26));
		textArea_keyword.setBounds(438, 136, 393, 357);
		panel.add(textArea_keyword);
		
		JButton btn_begain = new JButton("开始统计");
		btn_begain.addActionListener(new CP_BtnListener(btn_begain, textArea_wordfrequency));// 添加监听事件
		
		
		btn_begain.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btn_begain.setBounds(304, 504, 101, 33);
		panel.add(btn_begain);
		
		JButton btn_pipie = new JButton("开始匹配");
		btn_pipie.addActionListener(new PP_BtnListener(btn_pipie, textArea_keyword));// 添加监听事件
		
		btn_pipie.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btn_pipie.setBounds(731, 504, 101, 33);
		panel.add(btn_pipie);
		
		
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
		label_4.setBounds(10, 103, 78, 22);
		panel_1.add(label_4);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 91, 822, 2);
		panel_1.add(separator_1);
		
		JLabel label_5 = new JLabel("关键词显示");
		label_5.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_5.setBounds(435, 108, 91, 22);
		panel_1.add(label_5);
		
		JTextArea m_textArea_wordfrequency = new JTextArea();
		m_textArea_wordfrequency.setLineWrap(true);
		m_textArea_wordfrequency.setFont(new Font("Microsoft Himalaya", Font.PLAIN, 26));
		m_textArea_wordfrequency.setBounds(32, 135, 393, 357);
		panel_1.add(m_textArea_wordfrequency);
		
		JTextArea m_textArea_keyword = new JTextArea();
		m_textArea_keyword.setLineWrap(true);
		m_textArea_keyword.setFont(new Font("Microsoft Himalaya", Font.PLAIN, 26));
		m_textArea_keyword.setBounds(435, 140, 393, 357);
		panel_1.add(m_textArea_keyword);
		
		JButton m_btn_begain = new JButton("开始统计");
		m_btn_begain.addActionListener(new M_CP_BtnListener(m_btn_begain, m_textArea_wordfrequency,m_textArea_keyword));
		
		m_btn_begain.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		m_btn_begain.setBounds(304, 504, 101, 33);
		panel_1.add(m_btn_begain);
		
		JButton m_btn_pipie = new JButton("开始匹配");
		m_btn_pipie.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		m_btn_pipie.setBounds(731, 504, 101, 33);
		panel_1.add(m_btn_pipie);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("菜单");
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("摘要生成");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AbstractExtract ae = new AbstractExtract();
				String xpath = SingleBtnListener.getXpath();//获取路径
				String wf_save_fileName = SingleBtnListener.getWf_save_fileName();	//获取保存文本名称
				List<String>  pipeiWord = new ArrayList<String>();  //保存关键词及词频统计的词，用于匹配句子，传递给AbstractExtract类
				pipeiWord = PP_BtnListener.getPipeiWord_();
				String path = xpath.replace("已", "未");
				
				System.out.println(path);
				try {
					ae.Abstract(path, pipeiWord,wf_save_fileName);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		menu.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("批量摘要生成");
		menu.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("设置");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object[] opObjects = {"10","9","8","7","6","5","4","3","2","1"};
				String num = (String) JOptionPane.showInputDialog(null,"请选择频率","输入",JOptionPane
						.INFORMATION_MESSAGE,new ImageIcon(),opObjects,opObjects[0]);
				System.out.println("message: " + num);
				ranking = Integer.parseInt(num);
			}
		});
		menu.add(menuItem_2);
		
		JMenuItem menuItem_3 = new JMenuItem("清空"); 
		menu.add(menuItem_3);
		
		JMenuItem menuItem_4 = new JMenuItem("退出");
		menu.add(menuItem_4);
	}
}
