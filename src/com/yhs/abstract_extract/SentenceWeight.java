package com.yhs.abstract_extract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.sun.javafx.scene.traversal.WeightedClosestCorner;
/**
 * 初始化句子权重
 * @author yhs
 * 2017.3.23
 */
public class SentenceWeight {
	
	static String regax = "།";

	static HashMap<Integer, Double> weightMap = new HashMap<Integer, Double>();//句子编号  句子权值
	static List<String> sentList = new  ArrayList<String>();//保存分离的句子

	public static void setWeight(String content){
		
		String[] arr = content.split(regax);//切分句子
		System.out.println("arr size: " + arr.length);
		for(int i = 0;i < arr.length-1;i++){
			sentList.add(arr[i]);
			System.out.println("第" + i + "句: " + arr[i]);
			/*
			 * 首句，尾句权值设为1; 第二句权值0.5; 其余权值0
			 */
			if(i == 0){
				weightMap.put(0, (double) 1);
			}else if(i == 1){
				weightMap.put(1, 0.5);
			}else if(i == arr.length-2){
				weightMap.put(i,(double) 1);
			}else{
				weightMap.put(i,(double) 0);
			}
		}
		
		Set<Integer> set = weightMap.keySet();
        for(Integer key : set) {
            Double value = weightMap.get(key);
            System.out.println(key+"----"+value);
//            System.out.println(arr[key]);
        }
	}
	
	
	public static HashMap<Integer, Double> getWeightMap() {
		return weightMap;
	}

	public static void setWeightMap(HashMap<Integer, Double> weightMap) {
		SentenceWeight.weightMap = weightMap;
	}


	public static List<String> getSentList() {
		return sentList;
	}

	public static void setSentList(List<String> sentList) {
		SentenceWeight.sentList = sentList;
	}
	
}
