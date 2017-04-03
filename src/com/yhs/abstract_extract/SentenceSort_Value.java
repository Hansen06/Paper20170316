package com.yhs.abstract_extract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.criteria.CriteriaBuilder.In;

/** 
 * @author YHS
 * @version 创建时间：2017年3月24日 上午11:19:30 
 * 类说明 进行句子权重排序
 */
public class SentenceSort_Value {

	
	static Map<Integer, Double> weightMap_ = new HashMap<>();
	static HashMap<Integer, Double> weightMap_return = new HashMap<Integer, Double>();//句子编号  句子权值
 	
	public static List<Entry<Integer, Double>> sentenceValueSort(Map<Integer, Double> weightMap){
		
		weightMap_ = weightMap;
		
		List<Map.Entry<Integer, Double>> _weightMap = new 
				ArrayList<Map.Entry<Integer, Double>>(weightMap_.entrySet());
		
		//排序
		
		Comparator<Map.Entry<Integer, Double>> comparator = new Comparator<Map.Entry<Integer,Double>>() {

			@Override
			public int compare(Entry<Integer, Double> a1,	Entry<Integer, Double> a2) {
				 if((a1.getValue() - a2.getValue()) < 0)   
                     return 1;  
                 else if((a1.getValue() - a2.getValue()) > 0)  
                     return -1;  
                 else return 0; 
			}
		};
		
		Collections.sort(_weightMap,comparator);
		
		//排序后
//		for (int i = 0; i < _weightMap.size(); i++) {
//		    System.out.println(_weightMap.get(i).getKey() + "---" + _weightMap.get(i).getValue());
//		    weightMap_return.put(_weightMap.get(i).getKey(), _weightMap.get(i).getValue());
//		}
		System.out.println("Value排序完毕！");
		
		return _weightMap;
	}

}
