package com.yhs.main;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import util.GetFileName;
import util.ReadFile;
import util.ReadStopWords;
import util.WordSort;
import util.WriteFile;

import com.yhs.extract.ExtractText;
import com.yhs.statistics.Statistics;

public class MainWindow {

	private JFrame frame;
	private JTextField textField_filename;
	private JScrollPane scrollPane;
	private JTextArea textArea_wordfrequency;
	private JLabel label;
	private JButton btn_begain;
	private JButton btn_clear;
	private JSeparator separator;//分隔条
	private String xpath;//文件路径
	static private String content;//文本内容
	
	
	private String wf_save_path = null;	
	
	ArrayList<String> filepathList = new ArrayList<String>();
	ArrayList<String> filenameList = new ArrayList<String>();

	ArrayList<String> contentList = new ArrayList<String>();
 	
 	private boolean isDirectory = false;
 	private JMenuBar menuBar;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
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
	public MainWindow() {
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
		frame.getContentPane().setLayout(null);
		frame.setTitle("词频统计");
		frame.setBounds(100, 100, 801, 559);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*
		 * 创建菜单栏
		 */
//		JRootPane rp = new JRootPane();
//		frame.setContentPane(rp);
//		JMenuBar menubar = new JMenuBar();
//		rp.setJMenuBar(menubar);
//		JMenu menu1 = new JMenu("文件");
//		JMenu menu2 = new JMenu("编辑");
//		JMenu menu3 = new JMenu("工具");
//		JMenu menu4 = new JMenu("帮助");
//		menubar.add(menu1);
//		menubar.add(menu2);
//		menubar.add(menu3);
//		menubar.add(menu4);
	
		label = new JLabel("所选文件");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label.setBounds(10, 10, 78, 22);
		frame.getContentPane().add(label);
		
		//显示选择的文件
		textField_filename = new JTextField();
		textField_filename.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_filename.setBounds(10, 42, 642, 33);
		frame.getContentPane().add(textField_filename);
		textField_filename.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("词频显示");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 103, 78, 22);
		frame.getContentPane().add(lblNewLabel);
		
		//词频显示区
		textArea_wordfrequency = new JTextArea();
		textArea_wordfrequency.setLineWrap(true);//自动换行
		textArea_wordfrequency.setFont(new Font("Microsoft Himalaya", Font.PLAIN, 26));
		scrollPane = new JScrollPane(textArea_wordfrequency);
		scrollPane.setBounds(10, 135, 642, 300);
		frame.getContentPane().add(scrollPane);
		
		JButton btn_choose = new JButton("选择文件");
		btn_choose.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btn_choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();//创建文件选择对话框
				
				// 使用父目录
//				fileChooser.changeToParentDirectory();
//				fileChooser.showOpenDialog(null);
				
				//使用指定目录
				fileChooser.setCurrentDirectory(new File("E:/MUC/研究/藏文统计词频/藏文文件/藏文"));
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("XML文件", "xml");
				fileChooser.setFileFilter(filter);
				fileChooser.setMultiSelectionEnabled(true);//设置是否可以多选
//				fileChooser.getBackground();

	
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); //选择文件模式  
				int i = fileChooser.showOpenDialog(fileChooser);//显示文件选择对话框
		        File file = fileChooser.getSelectedFile();  
		        
		        GetFileName getName = new GetFileName();
		        wf_save_path = getName.getFileNameNoEx(fileChooser.getDescription(file));//获得文件名 用來保存统计的词频
		        
		        System.out.println("仅得到所选择的文件名：" + wf_save_path);
		        System.out.println("验证："+file);
		        
		        /*
		         * 读取全部文件
		        */
		        ReadFile readFile = new ReadFile();
		        readFile.filepathList.clear();
		        readFile.filenameList.clear();
//		        try {
//					readFile.readfile(file.getAbsolutePath());
//				} catch (IOException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
		        
  
		        if(file.isDirectory()){  
		        	isDirectory = true;
		            System.out.println("文件夹:" + file.getAbsolutePath()); 
		            
		            readFile.getAllFiles(new File(file.getAbsolutePath()), 0);//得到全部文件路径
			        readFile.print();
			        filepathList = readFile.getFilepathList();//保存全部文件路径
			        filenameList = readFile.getFilenameList();//保存全部文件名
		        }else if(!file.isDirectory()){ 
		        	xpath = file.getAbsolutePath();
		            System.out.println("文件:" + file.getAbsolutePath());  
		        }  
				
				
				//判断用户单击的是否为“打开”按钮
				if(i == JFileChooser.APPROVE_OPTION){
					File selectedFile = fileChooser.getSelectedFile();//获取选中的文件对象
					textField_filename.setText(selectedFile.getName());//显示选中文件的名称
					textArea_wordfrequency.setText(selectedFile.getName());

					ExtractText extract = new ExtractText();
					/*
					 * 提取多个文件
					 */
					if(isDirectory == true){
						for(int j = 0; j < filepathList.size(); j++){
							try {
								extract.Extract(filepathList.get(j));
								contentList.add(extract.getList().get(1));
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					/*
					 * 提取单个文件
					 */
					else{
					  try {
						extract.Extract(xpath);
						content =  extract.getList().get(1);
						System.out.println("content:"+content);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					}
				}
				
			}
		});
		btn_choose.setBounds(674, 42, 101, 33);
		frame.getContentPane().add(btn_choose);
		//开始统计按钮监听事件
		btn_begain = new JButton("开始统计");
//		btn_begain.addActionListener(new BtnListener(textArea_wordfrequency,isDirectory,contentList,filenameList,content,wf_save_path));
		
		btn_begain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * 统计多个文件
				 */
				System.out.println("///////..." + content);
				if(isDirectory == true){
					for(int k = 0; k < contentList.size(); k++){
						/*
						 * 使用正则表达式将文本中的空格去掉
						 */
						String regex1 = "\\s";  //空白字符：[ \t\n\x0B\f\r] 空格  tab键  换行  制表符  翻页  回车
						String regex2 = "\\d+";  //数字：[0-9]
						String regex3 = " +"; //一个或多个空格
						String regex4 = "\\w*";//去除字母和数字
						String regex5 = "[།་]";//去除停用词
						String[] wordsNew = contentList.get(k)
								.replaceAll(regex1, "").replaceAll(regex2, "").replaceAll(regex3, "")
								.replaceAll(regex4, "")
								.replaceAll(regex5, "")
								.replace("'", "").replace("\"", "")
								.replace(",", "").replace("《", "").replace("》", "").replace("‘", "")
								.replace("’", "").replace("(", "").replace(")", "")
								.replace("”","").replace("“","").replaceAll(";","").split("/");
												
						String[] words = null; // 保存各个词对应
						int[] wordFreqs = null; // 保存各个词对应的词频
						
						Statistics statictics = new  Statistics();
						statictics.countWordFreq(wordsNew);
						words = statictics.getWords();
						wordFreqs = statictics.getWordFreqs();
						
						/*
						 *将统计的词频写入文件 
						 */
						WriteFile writeFile = new WriteFile();
						for (int i = 0; i < words.length; i++) {
							File savefile = new File("F:/shareget/" + filenameList.get(k)+ ".txt");
	//					    if(savefile.exists()){  
	//					    	savefile.renameTo(new File("F:/shareget/" + filenameList.get(k) + "1" + ".txt"));
	//					    	writeFile.writeFile(savefile , words[i]+ ":  " + wordFreqs[i] + "\r\n");
	//					    }else{
	//					    	writeFile.writeFile(savefile , words[i]+ ":  " + wordFreqs[i] + "\r\n");
	//					    }
	//						writeFile.writeFile("F:/shareget/" + filenameList.get(k)+ ".txt", words[i]+ ":  " + wordFreqs[i] + "\r\n");
							writeFile.writeFile(savefile , words[i]+ ":  " + wordFreqs[i] + "\r\n");
							System.out.println(words[i] + "  " + wordFreqs[i]);
	//						textArea_wordfrequency.append(new String (words[i].getBytes("ISO-8859-1"),"utf-8") + ":  " + wordFreqs[i] + "\r\n");
							textArea_wordfrequency.append(words[i]+ ":  " + wordFreqs[i] + "\r\n");
						 }
					}
				}
				/*
				 * 统计单个文件时
				 */
				else{
					String regex1 = "\\s";  //空白字符：[ \t\n\x0B\f\r] 空格  tab键  换行  制表符  翻页  回车
					String regex2 = "\\d+";  //数字：[0-9]
					String regex3 = " +"; //一个或多个空格
					String regex4 = "\\w*";//去除字母和数字
					String[] wordsNew = content
							.replaceAll(regex1, "").replaceAll(regex2, "").replaceAll(regex3, "")
							.replaceAll(regex4, "")
//							.replaceAll("་", "")
							.replace("'", "").replace("\"", "")
							.replace(",", "").replace("《", "").replace("》", "").replace("‘", "")
							.replace("’", "").replace("(", "").replace(")", "")						
							.replace("”", "").replace("“", "").replaceAll(";", "").split("/");
					
					System.out.println("wordsNew大小：" + wordsNew.length);
					List<String> wordList = new ArrayList<String>();

					/*
					 * 读取停用词
					 */
					ReadStopWords rsw = new ReadStopWords();
					try {
						rsw.readStopWords();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ArrayList<String> stopWordList = new ArrayList<>();
					stopWordList = rsw.getStopWordList();
//					for(int i = 0;i < stopWordList.size();i++){
////						System.out.println("停用词： " + stopWordList.get(i));
//						content = content.replaceAll(stopWordList.get(i), "");
//					}
					
					/*
					 * 去除停用词
					 */
					for(int k = 0;k < wordsNew.length;k++){
						for(int j = 0;j < stopWordList.size();j++){
							if(wordsNew[k].equals(stopWordList.get(j))){
								System.out.println("重复词语： " + wordsNew[k]);
								break;
							}else{
								wordList.add(wordsNew[k]);
								break;
							}
						}
					}
					
					System.out.println("wordList大小：" + wordList.size());
					
//					for(int i = 0;i < wordsNew.length;i++){
//						if(!(wordsNew[i].equals(""))){
//							if(!(wordsNew[i].equals("་"))){
//								wordList.add(wordsNew[i]);
//								System.out.println("_word: " + wordsNew[i]);
//							}
//						}
//					}
					String[] _wordsNew = new String[wordList.size()];
					for(int j = 0;j < wordList.size();j++){
						_wordsNew[j] = wordList.get(j);
					}
					
					System.out.println("wordsNew...... " + wordsNew[1]);
					String[] words = null; // 保存各个词对应
					int[] wordFreqs = null; // 保存各个词对应的词频
					
					Statistics statictics = new  Statistics();
					statictics.countWordFreq(_wordsNew);
					words = statictics.getWords();
					wordFreqs = statictics.getWordFreqs();
					
					/*
					 * 词频排序
					 */
					WordSort ws = new WordSort();
					ws.SortWord(words, wordFreqs);
					words = ws.get_words();
					wordFreqs = ws.get_wordFreqs();
					/*
					 *将统计的词频写入文件 
					 */
					WriteFile writeFile = new WriteFile();
					for (int i = 0; i < words.length; i++) {
						File savefile = new File("F:/shareget/" + wf_save_path+ ".txt");
						writeFile.writeFile(savefile , words[i]+ ":  " + wordFreqs[i] + "\r\n");
						System.out.println(words[i] + "  " + wordFreqs[i]);
//						textArea_wordfrequency.append(new String (words[i].getBytes("ISO-8859-1"),"utf-8") + ":  " + wordFreqs[i] + "\r\n");
						textArea_wordfrequency.append(words[i]+ ":  " + wordFreqs[i] + "\r\n");
					 }
				}
				
				
				
			}
		});
		btn_begain.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btn_begain.setBounds(674, 343, 101, 33);
		frame.getContentPane().add(btn_begain);
		
		btn_clear = new JButton("清空");
		btn_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textArea_wordfrequency != null){
					textArea_wordfrequency.setText("");
				}
			}
		});
		btn_clear.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btn_clear.setBounds(674, 397, 101, 33);
		frame.getContentPane().add(btn_clear);
		
		separator = new JSeparator();
		separator.setBounds(10, 91, 765, 2);
		frame.getContentPane().add(separator);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("菜单");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("退出");
		mnNewMenu.add(mntmNewMenuItem);
		
		
		
	}
}
