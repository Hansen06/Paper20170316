package com.yhs.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextArea;

import utils.ReadStopWords;
import utils.RemoveStopWord;
import utils.WordSort;
import utils.WriteFile;

import com.yhs.main.MainWindow_;
import com.yhs.statistics.Statistics;

/*
 * 设计按钮动作
 * by yhs
 * date 2017/3/13
 */
public class CP_BtnListener implements ActionListener {

	private JButton btn_begain_; 
	private JTextArea textArea_wordfrequency_;
	
	private String content_ = null;//文本内容  需要传递
	private String wf_save_fileName_ = null;	//获取保存文本名称  需要传递
	
//	static int ranking = 0;
	static List<String>  pipeiWord_cp = new ArrayList<String>();  //保存关键词及词频统计的词，用于匹配句子，传递给AbstractExtract类    需要返回
	static List<String> wordList = null; //需要返回
	static String self_save_filepath_ = null;//用户自定义文件保存路径
	
	static String _rootpath = null;
	
	/*
	 *添加构造函数，接收传入的参数，以作出动作 
	 */
	public CP_BtnListener(JButton btn_begain,
			JTextArea textArea_wordfrequency) {
		super();
		this.btn_begain_ = btn_begain;
		this.textArea_wordfrequency_ = textArea_wordfrequency;
	}
	/*
	 * 实现自己定义的动作
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		content_ = SingleBtnListener.getContent();//获取读取的文本内容
//		System.out.println("CP_BtnListener: " + content_);
		wf_save_fileName_ = SingleBtnListener.getWf_save_fileName();//获取文件名，用于保存
		self_save_filepath_ = MainWindow_.getSelf_save_filepath();

		_rootpath = SingleBtnListener.getRoot_path();
		
		if(self_save_filepath_ == null){
			self_save_filepath_ = "F:/Key_Phrases_Extraction/";
		}
		
		pipeiWord_cp.clear();//将匹配List清空
		
//		System.out.println("CP_BtnListener: " + content_);
		
		String[] wordsNew = Split_RemoveStop_Word();
		
		String[] _wordsNew = new String[wordList.size()];
		
		for(int j = 0;j < wordList.size();j++){
			_wordsNew[j] = wordList.get(j);
		}
		
		System.out.println("wordsNew...... " + wordsNew[1]);
		String[] words = null; // 保存各个词对应
		int[] wordFreqs = null; // 保存各个词对应的词频
		
		System.out.println("_wordsNew size is : " + _wordsNew.length);
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
		textArea_wordfrequency_.setText("");
		/*
		 *将统计的词频写入文件 
		 */
		WriteFile writeFile = new WriteFile();
		int len = words.length;
		int num = MainWindow_.getRanking();
		int rank = (num==0)?10:num;
		System.out.println("rank: " + rank);
		
		if(len < 30){
			for (int i = 0; i < words.length; i++) {
				
				File savefile = new File(self_save_filepath_ + "/" + _rootpath + "/cipin/" + wf_save_fileName_+ ".txt");
				writeFile.writeFile(savefile , words[i]+ ":  " + wordFreqs[i] + "\r\n");
			}
		}else{
			for (int i = 0; i < 30; i++) {
				File savefile = new File(self_save_filepath_ + "/" + _rootpath + "/cipin/" + wf_save_fileName_+ ".txt");
				writeFile.writeFile(savefile , words[i]+ ":  " + wordFreqs[i] + "\r\n");
			}
		}
		
		
		if(len < rank){
			for (int i = 0; i < words.length; i++) {
				
				pipeiWord_cp.add(words[i]);//将统计的词频加入到匹配list
				
				File savefile = new File(self_save_filepath_ + "/" + _rootpath + "/" + wf_save_fileName_+ ".txt");
				writeFile.writeFile(savefile , words[i]+ ":  " + wordFreqs[i] + "\r\n");
				System.out.println(words[i] + "  " + wordFreqs[i]);
				textArea_wordfrequency_.append(words[i]+ ":  " + wordFreqs[i] + "\r\n");
			}
		}else{
			for (int i = 0; i < rank; i++) {
				
				pipeiWord_cp.add(words[i]);//将统计的词频加入到匹配list
				
				File savefile = new File(self_save_filepath_ + "/" + _rootpath + "/" + wf_save_fileName_+ ".txt");
				writeFile.writeFile(savefile , words[i]+ ":  " + wordFreqs[i] + "\r\n");
				System.out.println(words[i] + "  " + wordFreqs[i]);
				textArea_wordfrequency_.append(words[i]+ ":  " + wordFreqs[i] + "\r\n");
			}
		}
	}
	
	private String[] Split_RemoveStop_Word() {
		String regex1 = "\\s";  //空白字符：[ \t\n\x0B\f\r] 空格  tab键  换行  制表符  翻页  回车
		String regex2 = "\\d+";  //数字：[0-9]
		String regex3 = " +"; //一个或多个空格
		String regex4 = "\\w*";//去除字母和数字
		String strr = content_
				.replaceAll(regex1, "").replaceAll(regex2, "").replaceAll(regex3, "")
				.replaceAll(regex4, "")
				.replaceAll("'", "").replaceAll("\"", "")
				.replaceAll(",", "").replaceAll("《", "").replaceAll("》", "").replaceAll("‘", "")
				.replaceAll("’", "").replace("(", "").replace(")", "")
				.replaceAll("）", "").replaceAll("（", "")
				.replaceAll("%", "")
//				.replaceAll(".", "")
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
//				System.out.println(wordsNew[i]);
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
		RemoveStopWord rs = new RemoveStopWord();
		rs.removeStopWord(wordList,stopWordList);
		return wordsNew;
	}
	public static List<String> getPipeiWord_cp() {
		return pipeiWord_cp;
	}
	public static void setPipeiWord_cp(List<String> pipeiWord_cp) {
		CP_BtnListener.pipeiWord_cp = pipeiWord_cp;
	}
	public static List<String> getWordList() {
		return wordList;
	}
	public static void setWordList(List<String> wordList) {
		CP_BtnListener.wordList = wordList;
	}
}
