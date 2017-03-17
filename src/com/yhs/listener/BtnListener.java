package com.yhs.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import utils.WriteFile;

import com.yhs.main.MainWindow;
import com.yhs.statistics.Statistics;
/*
 * 设计按钮动作
 * by yhs
 * date 2017/3/13
 */
public class BtnListener implements ActionListener {

	JButton jBtn = new JButton();
	private JTextArea textArea_wordfrequency = new JTextArea();
 	private boolean isDirectory = false;
 	ArrayList<String> contentList = new ArrayList<String>();
 	ArrayList<String> filenameList = new ArrayList<String>();
	private String content = null;//文本内容
	private String wf_save_path = null;	

	/*
	 *添加构造函数，接收传入的参数，以作出动作 
	 */
	public BtnListener(JTextArea textArea_wordfrequency, boolean isDirectory,
			ArrayList<String> contentList, ArrayList<String> filenameList,
			String content, String wf_save_path) {
		super();
		this.textArea_wordfrequency = textArea_wordfrequency;
		this.isDirectory = isDirectory;
		this.contentList = contentList;
		this.filenameList = filenameList;
		this.content = content;
		this.wf_save_path = wf_save_path;
	}


	/*
	 * 实现自己定义的动作
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		/*
		 * 统计多个文件
		 */
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
//			String regex5 = "[འི་  ར་ ལ་  ཀྱི་ པ་ ནས་ གྱི་ གི་ དུ ས ནི་  ཞིག་ དེ་  བ སུ་ ཀྱིས་  ཡིན་   གིས་   ན་   ལས་  བཅའ་སྡོད་]";
			System.out.println(">>>>>>>..." + content);
			System.out.println(">>>>>>>..." + wf_save_path);
			String[] wordsNew = content
					.replaceAll(regex1, "").replaceAll(regex2, "").replaceAll(regex3, "")
					.replaceAll(regex4, "")
//					.replaceAll(regex5, "")
					.replace("'", "").replace("\"", "")
					.replace(",", "").replace("《", "").replace("》", "").replace("‘", "")
					.replace("’", "").replace("(", "").replace(")", "")						
					.replace("”", "").replace("“", "").replaceAll(";", "").split("/");
		
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
				File savefile = new File("F:/shareget/" + wf_save_path+ ".txt");
				writeFile.writeFile(savefile , words[i]+ ":  " + wordFreqs[i] + "\r\n");
				System.out.println(words[i] + "  " + wordFreqs[i]);
//				textArea_wordfrequency.append(new String (words[i].getBytes("ISO-8859-1"),"utf-8") + ":  " + wordFreqs[i] + "\r\n");
				textArea_wordfrequency.append(words[i]+ ":  " + wordFreqs[i] + "\r\n");
			 }
		}
		
	}

}
