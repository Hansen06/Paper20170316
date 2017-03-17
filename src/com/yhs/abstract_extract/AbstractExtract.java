package com.yhs.abstract_extract;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import utils.WriteFile;

import com.yhs.extract.ExtractText;
/**
 * 匹配句子，生成摘要
 * @author yhs
 * date 2017.3.21
 */
public class AbstractExtract {

	static List<String> sentenceList = new  ArrayList<String>();//保存提取的句子
	static List<String> sentList = new  ArrayList<String>();//获取切分的句子
	static String content = null;
	static String regax = "།";
	static HashMap<Integer, Double> weightMap = new HashMap<Integer, Double>();//句子编号  句子权值
	
	public static void Abstract(String path, List<String> wordKey, String wf_save_fileName) throws Exception{
		System.out.println("this abstractExtract" + "关键词个数：" + wordKey.size());
		
		/*
		 * 提取content内容
		 */
		ExtractText extract = new ExtractText();
        extract.Extract(path);
        content = extract.getList().get(1);
        
        /*
         * 初始化句子权重
         */
        SentenceWeight.setWeight(content);
        weightMap = SentenceWeight.getWeightMap();
        sentList = SentenceWeight.getSentList();
        /*
         * 匹配句子是否含有关键词，有则增加权重，0.5
         */
        Set<Integer> set = weightMap.keySet();
        for(Integer key : set) {
        	for(int j = 0;j < wordKey.size();j++){
        		if(sentList.get(key).contains(wordKey.get(j))){
        			Double value = weightMap.get(key);
        			value = value + 0.5;
        			weightMap.put(key, value);
        			
					if(!sentenceList.contains(sentList.get(key))){
						sentenceList.add(sentList.get(key));
					}
				}
        	}
        }
        
//        Set<Integer> set1 = weightMap.keySet();
//        for(Integer key : set1) {
//            Double value = weightMap.get(key);
//            System.out.println(key+"----"+value);
//        }
        
        /*
         * 将匹配的句子进行保存
         */
        WriteFile writeFile = new WriteFile();
        File savefile = new File("F:/shareget/zhaiyao/" + wf_save_fileName + ".txt");
		
		for(int k = 0;k < sentenceList.size();k++){
			System.out.println("匹配的句子： " + (k+1) + sentenceList.get(k));
			writeFile.writeFile(savefile , sentenceList.get(k) + "\r\n");
		}
	}

	private static void demo1(List<String> wordKey) {
		System.out.println("content: " + content);
        String[] arr = content.split(regax);//切分句子
        
        /*
         * 匹配句子是否含有关键词，有则提取，保存至句子list，并判断句子的重复性，不重复加入
         */
		for(int i = 0;i < arr.length;i++){
			System.out.println("第" + (i+1) + "句: " + arr[i]);
			for(int j = 0;j < wordKey.size();j++){
				if(arr[i].contains(wordKey.get(j))){
					if(!sentenceList.contains(arr[i])){
						sentenceList.add(arr[i]);
					}
				}
			}
		}
	}
	
	public static List<String> getSentenceList() {
		return sentenceList;
	}

	public static void setSentenceList(List<String> sentenceList) {
		AbstractExtract.sentenceList = sentenceList;
	}
}
