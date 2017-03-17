package com.yhs.keywordmatch;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import utils.List_To_String;
import utils.ReadKeyWord;
/**
 * 关键词匹配
 * @author yhs
 * date 2017.3.17
 */
public class Key_Word_Match {

	static String[] fileName = {"工业科技_纯藏文.txt","行动·移动·交通运输_纯藏文.txt","教育·艺术·文化·体育_纯藏文.txt",
		"农业_纯藏文.txt","饮食_纯藏文.txt","宇宙·地球_纯藏文.txt"};
	static ArrayList<String> keyWordList = new ArrayList<String>();//保存读取的关键词
	static ArrayList<String> matchedWord = new ArrayList<String>();//保存从文本中匹配的关键词
	
	@Test
	public void Word_Match(List wordList) throws Exception{
//	public void Word_Match(String content) throws Exception{
		
//		ExtractText extract = new ExtractText();
//		extract.Extract("E:MUC/pipei/0_qhzywlgg_0_0_2016-10-20_www.qhtb.cn.news.world.2016.10-20.47041.html.xml");
//		content =  extract.getList().get(1);
//		System.out.println("content:"+content);
		
		List_To_String lts = new List_To_String();
		String content = lts.ListToString(wordList);
		
		/*
		 * 读取关键词
		 */
		for(int j = 0;j < fileName.length;j++){
			keyWordList.clear();
			ReadKeyWord readKeyWord = new ReadKeyWord();
			readKeyWord.readTxtFile("file/"+fileName[j]);
			keyWordList = readKeyWord.getKeyword();
			
			for(int i = 0;i < keyWordList.size();i++){
//				System.out.println(keyWordList.get(i));
				Pattern pattern = Pattern.compile("/" + keyWordList.get(i) + "/");
				Matcher match = pattern.matcher(content);
				while(match.find()){
//					matchedWord.add(j + ":  " + match.group());
					if(!(matchedWord.contains(match.group().replaceAll("/", "")))){ //判断是否有重复的关键词，保证不重复加入
						matchedWord.add(match.group().replaceAll("/", ""));
					}
				}
			}
		}
	}
	
	public static ArrayList<String> getMatchedWord() {
		return matchedWord;
	}

	public static void setMatchedWord(ArrayList<String> matchedWord) {
		Key_Word_Match.matchedWord = matchedWord;
	}

}
