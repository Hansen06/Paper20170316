package com.yhs.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import utils.WriteFile;

import com.yhs.keywordmatch.Key_Word_Match;
import com.yhs.main.MainWindow;
import com.yhs.main.MainWindow_;
import com.yhs.statistics.Statistics;
/*
 * 设计按钮动作
 * by yhs
 * date 2017/3/13
 */
public class PP_BtnListener implements ActionListener {

	private JButton btn_pipie_;
	private ArrayList<String> matchedWord = new ArrayList<String>();//保存从文本中匹配的关键词
	private JTextArea textArea_keyword_;//显示匹配的关键词
	
	List<String> wordList_ = null;//需要获取 从CP_BtnListener中获取
	static private String wf_save_fileName_ = null;	//获取保存文本名称    需要获取从SingleBtnListener中获取
	static List<String>  pipeiWord_key = new ArrayList<String>();  //保存关键词及词频统计的词，用于匹配句子，传递给AbstractExtract类    
	static String self_save_filepath_ = null;//用户自定义文件保存路径
	static String _rootpath = null;
	/*
	 *添加构造函数，接收传入的参数，以作出动作 
	 */
	public PP_BtnListener(JButton btn_pipie, JTextArea textArea_keyword) {
		super();
		this.btn_pipie_ = btn_pipie;
		this.textArea_keyword_ = textArea_keyword;
	}

	/*
	 * 实现自己定义的动作
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		wordList_ = CP_BtnListener.getWordList();//获取
		wf_save_fileName_ = SingleBtnListener.getWf_save_fileName();//获取保存路径
		self_save_filepath_ = MainWindow_.getSelf_save_filepath();
		_rootpath = SingleBtnListener.getRoot_path();
		if(self_save_filepath_ == null){
			self_save_filepath_ = "F:/Key_Phrases_Extraction/";
		}
		
		pipeiWord_key.clear();
		textArea_keyword_.setText("");
		matchedWord.clear();
		System.out.println("开始匹配 ActionListener");
		Key_Word_Match kwm = new Key_Word_Match();
		try {
			kwm.Word_Match(wordList_);
			matchedWord = Key_Word_Match.getMatchedWord();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		WriteFile writeFile = new WriteFile();
		File savefile = new File(self_save_filepath_ + "/" + _rootpath + "/" + wf_save_fileName_+ ".txt");
		writeFile.writeFile(savefile , "keyword" + "\r\n");
		
		for(int i = 0;i < matchedWord.size();i++){
			
			pipeiWord_key.add(matchedWord.get(i));//将关键词加入匹配链表
			textArea_keyword_.append(matchedWord.get(i) + "\r\n");
			
			/*
			 * 将匹配的关键词写入文件
			 */
			writeFile.writeFile(savefile , matchedWord.get(i) + "\r\n");
		}
	}

	public static List<String> getPipeiWord_key() {
		return pipeiWord_key;
	}

	public static void setPipeiWord_key(List<String> pipeiWord_key) {
		PP_BtnListener.pipeiWord_key = pipeiWord_key;
	}

}
