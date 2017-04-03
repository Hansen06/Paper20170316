package com.yhs.abstract_extract;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import utils.WriteFile;

import com.sun.javafx.scene.traversal.WeightedClosestCorner;
/**
 * 初始化句子权重
 * @author yhs
 * 2017.3.23
 */
public class SentenceWeight_Set {
	
	static String regax = "།";

	static HashMap<Integer, Double> weightMap = new HashMap<Integer, Double>();//句子编号  句子权值
	static List<String> sentList = new  ArrayList<String>();//保存分离的句子

	public static void setWeight(String content,String self_save_filepath_,String digest_path,
			String wf_save_fileName){
		
		sentList.clear();
		weightMap.clear();
		
		String[] _arr = content.split(regax);//切分句子
		
		ArrayList<String> arr_list = Remove_Long_short.remove_Long_short(_arr);//去除过长和过短的句子
		System.out.println("arr size: " + arr_list.size());
		for(int i = 0;i < arr_list.size();i++){
			sentList.add(arr_list.get(i));
//			System.out.println("第" + i + "句: " + arr[i]);
			/*
			 * 首句，尾句权值设为1; 第二句权值0.5; 其余权值0
			 */
			if(i == 0){
				weightMap.put(0, (double) 1);
			}else if(i == 1){
				weightMap.put(1, 0.5);
			}else if(i == arr_list.size()-1){
				weightMap.put(i,(double)0.6);
			}else{
				weightMap.put(i,(double) 0);
			}
		}
		arr_list.clear();
		WriteFile writeFile = new WriteFile();
		File savefile = new File(self_save_filepath_ + "/" + digest_path +"_set"+ "/" + wf_save_fileName +"_set"+ ".txt");
		
		Set<Integer> set = weightMap.keySet();
        for(Integer key : set) {
            Double value = weightMap.get(key);
            System.out.println("SentenceWeight " + key + "--" + value);
            writeFile.writeFile(savefile , key + "--" + value + "\r\n");
        }
	}
	public static void setWeight1(String content){
		
		sentList.clear();
		weightMap.clear();
		
		String[] arr = content.split(regax);//切分句子
		System.out.println("arr size: " + arr.length);
		for(int i = 0;i < arr.length;i++){
			sentList.add(arr[i]);
//			System.out.println("第" + i + "句: " + arr[i]);
			/*
			 * 首句，尾句权值设为1; 第二句权值0.5; 其余权值0
			 */
			if(i == 0){
				weightMap.put(0, (double) 1);
			}else if(i == 1){
				weightMap.put(1, 0.5);
			}else if(i == arr.length-1){
				weightMap.put(i,(double) 1);
			}else{
				weightMap.put(i,(double) 0);
			}
		}
		
		Set<Integer> set = weightMap.keySet();
        for(Integer key : set) {
            Double value = weightMap.get(key);
            System.out.println("SentenceWeight " + key + "--" + value);
//            System.out.println(arr[key]);
        }
	}
	
	public static HashMap<Integer, Double> getWeightMap() {
		return weightMap;
	}

	public static void setWeightMap(HashMap<Integer, Double> weightMap) {
		SentenceWeight_Set.weightMap = weightMap;
	}


	public static List<String> getSentList() {
		return sentList;
	}

	public static void setSentList(List<String> sentList) {
		SentenceWeight_Set.sentList = sentList;
	}
	
}
