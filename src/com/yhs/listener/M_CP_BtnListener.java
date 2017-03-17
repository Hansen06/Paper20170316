package com.yhs.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import utils.ReadStopWords;
import utils.WordSort;
import utils.WriteFile;

import com.yhs.abstract_extract.AbstractExtract;
import com.yhs.keywordmatch.Key_Word_Match;
import com.yhs.main.MainWindow;
import com.yhs.main.MainWindow_;
import com.yhs.statistics.Statistics;
/*
 * 设计按钮动作
 * by yhs
 * date 2017/3/13
 */
public class M_CP_BtnListener implements ActionListener {

	private JButton m_btn_begain_; 
	private JTextArea m_textArea_wordfrequency_;
	private JTextArea m_textArea_keyword_;
	
	
//	static List<String>  pipeiWord = new ArrayList<String>();  //保存关键词及词频统计的词，用于匹配句子，传递给AbstractExtract类    需要返回
	
	ArrayList<String> filepathList_ = new ArrayList<String>();//保存文件路径  需要获取
	ArrayList<String> filenameList_ = new ArrayList<String>();//保存文件名 需要获取

	ArrayList<String> contentList_ = new ArrayList<String>();//保存提取的内容  需要返回
	
	static List<String> wordList = null; //需要返回
	private String wf_save_fileName_ = null;	//获取保存文本名称
	
	private ArrayList<String> matchedWord = new ArrayList<String>();//保存从文本中匹配的关键词
	static List<String>  pipeiWord_ = new ArrayList<String>();  //保存关键词及词频统计的词，用于匹配句子，传递给AbstractExtract类 
	
	/*
	 *添加构造函数，接收传入的参数，以作出动作 
	 */
	public M_CP_BtnListener(JButton m_btn_begain,
			JTextArea m_textArea_wordfrequency,JTextArea m_textArea_keyword) {
		super();
		this.m_btn_begain_ = m_btn_begain;
		this.m_textArea_wordfrequency_ = m_textArea_wordfrequency;
		this.m_textArea_keyword_ = m_textArea_keyword;
	}
	/*
	 * 实现自己定义的动作
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		filenameList_ = MultipleBtnListener.getFilenameList_();
		contentList_ = MultipleBtnListener.getContentList_();
		filepathList_= MultipleBtnListener.getFilepathList_();
		
		for(int i = 0;i < filenameList_.size();i++){
			wf_save_fileName_ = filenameList_.get(i);
			
			pipeiWord_.clear();//将匹配List清空
			
			String[] wordsNew = Split_RemoveStopWord(i);
			
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
			
			/*
			 * 词频排序
			 */
			WordSort ws = new WordSort();
			ws.SortWord(words, wordFreqs);
			words = ws.get_words();
			wordFreqs = ws.get_wordFreqs();
			m_textArea_wordfrequency_.setText("");
			/*
			 *将统计的词频写入文件 
			 */
			WriteFile writeFile = new WriteFile();
			int len = words.length;
			int num = MainWindow_.getRanking();
			int rank = (num==0)?10:num;
			System.out.println("rank: " + rank);
			if(len < rank){
				for (int j = 0; j < words.length; j++) {
					
					pipeiWord_.add(words[j]);//将统计的词频加入到匹配list
					
					File savefile = new File("F:/shareget/duoge/" + wf_save_fileName_ + ".txt");
					writeFile.writeFile(savefile , words[j]+ ":  " + wordFreqs[j] + "\r\n");
					System.out.println(words[j] + "  " + wordFreqs[j]);
					m_textArea_wordfrequency_.append(words[j]+ ":  " + wordFreqs[j] + "\r\n");
				}
			}else{
				for (int j = 0; j < rank; j++) {
					
					pipeiWord_.add(words[j]);//将统计的词频加入到匹配list
					
					File savefile = new File("F:/shareget/duoge/" + wf_save_fileName_ + ".txt");
					writeFile.writeFile(savefile , words[j]+ ":  " + wordFreqs[j] + "\r\n");
					System.out.println(words[j] + "  " + wordFreqs[j]);
					m_textArea_wordfrequency_.append(words[j]+ ":  " + wordFreqs[j] + "\r\n");
				}
			}
			
			
			
			/*
			 * 匹配关键词
			 */
			m_textArea_keyword_.setText("");
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
//			WriteFile writeFile = new WriteFile();
			File savefile = new File("F:/shareget/duoge/" + wf_save_fileName_+ ".txt");
			writeFile.writeFile(savefile , "keyword" + "\r\n");
			
			for(int l = 0;l < matchedWord.size();l++){
				
				pipeiWord_.add(matchedWord.get(l));//将关键词加入匹配链表
				m_textArea_keyword_.append(matchedWord.get(l) + "\r\n");
				
				/*
				 * 将匹配的关键词写入文件
				 */
				writeFile.writeFile(savefile , matchedWord.get(l) + "\r\n");
			}
			
			/*
			 * 提取摘要
			 */
			AbstractExtract ae = new AbstractExtract();
			String path = filepathList_.get(i).replace("已", "未");
			System.out.println(path);
			try {
				ae.Abstract(path, pipeiWord_,wf_save_fileName_);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	private String[] Split_RemoveStopWord(int i) {
		String regex1 = "\\s";  //空白字符：[ \t\n\x0B\f\r] 空格  tab键  换行  制表符  翻页  回车
		String regex2 = "\\d+";  //数字：[0-9]
		String regex3 = " +"; //一个或多个空格
		String regex4 = "\\w*";//去除字母和数字
		String strr = contentList_.get(i)
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
		for(int k = 0;k < wordsNew.length;k++){
			if(!(wordsNew[k] == null)){
				wordList.add(wordsNew[k]);
//				System.out.println(wordsNew[k]);
			}
		}
		System.out.println("wordList大小：" + wordList.size());
		
		/*
		 * 读取停用词
		 */
		ReadStopWords rsw = new ReadStopWords();
		try {
			rsw.readStopWords();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<String> stopWordList = new ArrayList<>();
		stopWordList = rsw.getStopWordList();
		
		/*
		 * 去除停用词
		 */
		int listSize = wordList.size();
		for(int k = 0;k < listSize - 1;k++){
//			System.out.println("wordList: " + wordList.get(k));
			for(int j = 0;j < stopWordList.size();j++){
//						System.out.println("Stop>>>: " + stopWordList.get(j));
				wordList.get(k);
				stopWordList.get(j);
				if(wordList.get(k).equals(stopWordList.get(j))){
					wordList.remove(k);
					listSize = wordList.size();
//							System.out.println("listSize: " + listSize);
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
		return wordsNew;
	}
}
