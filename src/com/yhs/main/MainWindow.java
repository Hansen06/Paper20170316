package com.yhs.main;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import utils.GetFileName;
import utils.ReadFile;
import utils.ReadStopWords;
import utils.WordSort;
import utils.WriteFile;

import com.yhs.abstract_extract.AbstractExtract;
import com.yhs.extract.ExtractText;
import com.yhs.keywordmatch.Key_Word_Match;
import com.yhs.statistics.Statistics;

public class MainWindow {

	private JFrame frame;
	private JTextField textField_filename;
	private JScrollPane scrollPane;
	private JTextArea textArea_wordfrequency;//显示统计的词频
	private JTextArea textArea_keyword;//显示匹配的关键词
	private JLabel label;
	private JButton btn_begain;
	private JButton btn_pipie;
	private JSeparator separator;//分隔条
	private String xpath;//文件路径
	static private String content;//文本内容
	
	private String wf_save_fileName = null;	//获取保存文本名称
	private String wf_save_path = null;	
	
	ArrayList<String> filepathList = new ArrayList<String>();//保存文件路径
	ArrayList<String> filenameList = new ArrayList<String>();//保存文件名

	ArrayList<String> contentList = new ArrayList<String>();//保存提取的内容
 	
 	private boolean isDirectory = false;
 	private JMenuBar menuBar;
 	private JMenuItem menuItem;
 	
 	static ArrayList<String> matchedWord = new ArrayList<String>();//保存从文本中匹配的关键词
 	List<String> wordList = null;
 	
 	List<String>  pipeiWord = new ArrayList<String>();  //保存关键词及词频统计的词，用于匹配句子，传递给AbstractExtract类
 	private JMenuItem menuItem_3;
  	
 	int ranking = 0;
 	

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

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
		frame.getContentPane().setBackground(SystemColor.menu);
		frame.getContentPane().setLayout(null);
		frame.setTitle("词频统计");
		frame.setBounds(100, 100, 857, 617);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		label = new JLabel("所选文件");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label.setBounds(10, 10, 78, 22);
		frame.getContentPane().add(label);
		
		//显示选择的文件
		textField_filename = new JTextField();
		textField_filename.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_filename.setBounds(10, 42, 711, 33);
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
		scrollPane.setBounds(10, 135, 395, 359);
		frame.getContentPane().add(scrollPane);
		
		//关键词显示区
		textArea_keyword = new JTextArea();
		textArea_keyword.setLineWrap(true);//自动换行
		textArea_keyword.setFont(new Font("Microsoft Himalaya", Font.PLAIN, 26));
		JScrollPane scrollPane_1 = new JScrollPane(textArea_keyword);
		scrollPane_1.setBounds(437, 135, 395, 359);
		frame.getContentPane().add(scrollPane_1);
		
		JButton btn_choose = new JButton("选择文件");
		btn_choose.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btn_choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();//创建文件选择对话框
				
				// 使用父目录
//				fileChooser.changeToParentDirectory();
//				fileChooser.showOpenDialog(null);
				
				//使用指定目录
//				fileChooser.setCurrentDirectory(new File("E:/MUC/研究/藏文统计词频/藏文文件/藏文"));
				fileChooser.setCurrentDirectory(new File("E:/MUC/研究/基于Web的藏文文本主题词提取及摘要生成技术/2016已分词/2016已分词/9/4"));
//				fileChooser.setCurrentDirectory(new File("E:/MUC/研究/基于Web的藏文文本主题词提取及摘要生成技术/2016已分词/2016已分词"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("XML文件", "xml");
				fileChooser.setFileFilter(filter);
				fileChooser.setMultiSelectionEnabled(true);//设置是否可以多选
//				fileChooser.getBackground();

	
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); //选择文件模式  
				int i = fileChooser.showOpenDialog(fileChooser);//显示文件选择对话框
		        File file = fileChooser.getSelectedFile();  
		        
		        GetFileName getName = new GetFileName();
		        wf_save_fileName = getName.getFileNameNoEx(fileChooser.getDescription(file));//获得文件名 用來保存统计的词频
		        
		        System.out.println("仅得到所选择的文件名：" + wf_save_fileName);
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
//					textArea_wordfrequency.setText(selectedFile.getName());

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
		btn_choose.setBounds(731, 42, 101, 33);
		frame.getContentPane().add(btn_choose);
		
		/*
		 * 统计词频事件
		 */
		btn_begain = new JButton("开始统计");
//		btn_begain.addActionListener(new BtnListener(textArea_wordfrequency,isDirectory,contentList,filenameList,content,wf_save_path));
		
		btn_begain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				pipeiWord.clear();//将匹配List清空
				
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
					String strr = content
							.replaceAll(regex1, "").replaceAll(regex2, "").replaceAll(regex3, "")
							.replaceAll(regex4, "")
							.replaceAll("'", "").replaceAll("\"", "")
							.replaceAll(",", "").replaceAll("《", "").replaceAll("》", "").replaceAll("‘", "")
							.replaceAll("’", "").replace("(", "").replace(")", "")
							.replaceAll("）", "").replaceAll("（", "")	
							.replaceAll("«", "").replaceAll("»", "")
							.replaceAll("”", "").replaceAll("“", "").replaceAll(";", "");
					
					/*
					 * String regax5 = "(.)\\1+";
					   String strra = strr.replaceAll(regax5,"$1");//$1代表第一组中的内容
					 * 去除重叠的/ 防治划分时出现空白内容
					 * 2017.3.20
					 * by yhs
					 */
					String regax5 = "(.)\\1+";
					String strra = strr.replaceAll(regax5,"$1");//$1代表第一组中的内容
					
					String[] wordsNew = strra.split("/");
					
					System.out.println("wordsNew大小：" + wordsNew.length);
					wordList = new ArrayList<String>();
					for(int i = 0;i < wordsNew.length;i++){
						if(!(wordsNew[i] == null)){
							wordList.add(wordsNew[i]);
							System.out.println(wordsNew[i]);
						}
					}
					
					System.out.println("wordsNew大小：" + wordsNew.length);
					List<String> wordList = new ArrayList<String>();
					for(int i = 0;i < wordsNew.length;i++){
						wordList.add(wordsNew[i]);
					}
					System.out.println("wordList大小：" + wordList.size());
					
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
					
					/*
					 * 去除停用词
					 */
					int listSize = wordList.size();
					for(int k = 0;k < listSize - 1;k++){
//						System.out.println("wordList: " + wordList.get(k));
						for(int j = 0;j < stopWordList.size();j++){
//							System.out.println("Stop>>>: " + stopWordList.get(j));
							if(wordList.get(k).equals(stopWordList.get(j))){
								wordList.remove(k);
								listSize = wordList.size();
//								System.out.println("listSize: " + listSize);
//						        System.out.println("k: " + k);
							}
						}
						if(wordList.get(k).equals("།་")){
							wordList.remove(k);
							listSize = wordList.size();
						}
						if(wordList.get(k).equals("ལ་")){
							wordList.remove(k);
							listSize = wordList.size();
						}
						if(wordList.get(k).equals("དུ་")){
							wordList.remove(k);
							listSize = wordList.size();
						}
						
					}
					String[] _wordsNew = new String[wordList.size()];
					
					for(int j = 0;j < wordList.size();j++){
						_wordsNew[j] = wordList.get(j);
					}
					
					System.out.println("wordsNew...... " + wordsNew[1]);
					String[] words = null; // 保存各个词对应
					int[] wordFreqs = null; // 保存各个词对应的词频
					
					/*
					 * 词频统计
					 */
					Statistics statictics = new  Statistics();
					statictics.countWordFreq(_wordsNew);
					words = statictics.getWords();
					wordFreqs = statictics.getWordFreqs();
					
//					WriteFile writeFile1 = new WriteFile();
//					File savefile1 = new File("F:/shareget/v.txt");
//					for(int i = 0; i < words.length;i++){
//						writeFile1.writeFile(savefile1, words[i] + "\r\n");
//					}
					
					/*
					 * 词频排序
					 */
					WordSort ws = new WordSort();
					ws.SortWord(words, wordFreqs);
					words = ws.get_words();
					wordFreqs = ws.get_wordFreqs();
					textArea_wordfrequency.setText("");
					/*
					 *将统计的词频写入文件 
					 */
					WriteFile writeFile = new WriteFile();
					int len = words.length;
					
					int num = getRanking();
					int rank = (num==0)?10:num;
					System.out.println("rank: " + rank);
					if(len < rank){
						for (int i = 0; i < words.length; i++) {
							
							pipeiWord.add(words[i]);//将统计的词频加入到匹配list
							
							File savefile = new File("F:/shareget/" + wf_save_fileName+ ".txt");
							writeFile.writeFile(savefile , words[i]+ ":  " + wordFreqs[i] + "\r\n");
							System.out.println(words[i] + "  " + wordFreqs[i]);
//							textArea_wordfrequency.append(new String (words[i].getBytes("ISO-8859-1"),"utf-8") + ":  " + wordFreqs[i] + "\r\n");
							textArea_wordfrequency.append(words[i]+ ":  " + wordFreqs[i] + "\r\n");
						}
					}else{
						for (int i = 0; i < rank; i++) {
							
							pipeiWord.add(words[i]);//将统计的词频加入到匹配list
							
							File savefile = new File("F:/shareget/" + wf_save_fileName+ ".txt");
							writeFile.writeFile(savefile , words[i]+ ":  " + wordFreqs[i] + "\r\n");
							System.out.println(words[i] + "  " + wordFreqs[i]);
//							textArea_wordfrequency.append(new String (words[i].getBytes("ISO-8859-1"),"utf-8") + ":  " + wordFreqs[i] + "\r\n");
							textArea_wordfrequency.append(words[i]+ ":  " + wordFreqs[i] + "\r\n");
						}
					}
				}
			}
		});
		btn_begain.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btn_begain.setBounds(304, 504, 101, 33);
		frame.getContentPane().add(btn_begain);
		
		/*
		 * 关键词匹配事件
		 */
		btn_pipie = new JButton("开始匹配");
		btn_pipie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_keyword.setText("");
				matchedWord.clear();
				System.out.println("开始匹配 ActionListener");
				Key_Word_Match kwm = new Key_Word_Match();
				try {
					kwm.Word_Match(wordList);
					matchedWord = Key_Word_Match.getMatchedWord();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				WriteFile writeFile = new WriteFile();
				File savefile = new File("F:/shareget/" + wf_save_fileName+ ".txt");
				writeFile.writeFile(savefile , "keyword" + "\r\n");
				
				for(int i = 0;i < matchedWord.size();i++){
					
					pipeiWord.add(matchedWord.get(i));//将关键词加入匹配链表
					textArea_keyword.append(matchedWord.get(i) + "\r\n");
					
					/*
					 * 将匹配的关键词写入文件
					 */
					writeFile.writeFile(savefile , matchedWord.get(i) + "\r\n");
				}
			}
		});
		btn_pipie.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btn_pipie.setBounds(731, 504, 101, 33);
		frame.getContentPane().add(btn_pipie);
		
		separator = new JSeparator();
		separator.setBounds(10, 91, 822, 2);
		frame.getContentPane().add(separator);
		
		JLabel label_1 = new JLabel("关键词显示");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_1.setBounds(435, 108, 91, 22);
		frame.getContentPane().add(label_1);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("菜单");
		menuBar.add(mnNewMenu);
		
		menuItem = new JMenuItem("退出");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0); 
			}
		});
		
		
		menuItem = new JMenuItem("退出");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0); 
			}
		});
		
		JMenuItem menuItem_1 = new JMenuItem("清空");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textArea_wordfrequency != null){
					textArea_wordfrequency.setText("");
					textArea_keyword.setText("");
				}
			}
		});
		
		JMenuItem menuItem_2 = new JMenuItem("摘要生成");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbstractExtract ae = new AbstractExtract();
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
		mnNewMenu.add(menuItem_2);
		mnNewMenu.add(menuItem_1);
		mnNewMenu.add(menuItem);
		
		
		menuItem_3 = new JMenuItem("批量摘要生成");
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		mnNewMenu.add(menuItem_3);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("设置");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] opObjects = {"10","9","8","7","6","5","4","3","2","1"};
				String num = (String) JOptionPane.showInputDialog(null,"请选择频率","输入",JOptionPane
						.INFORMATION_MESSAGE,new ImageIcon(),opObjects,opObjects[0]);
				System.out.println("message: " + num);
				setRanking(Integer.parseInt(num));
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		mnNewMenu.add(menuItem_1);
		mnNewMenu.add(menuItem);
	}
}
