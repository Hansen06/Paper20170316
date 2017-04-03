package com.yhs.abstract_extract;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import utils.MathUtils;
import utils.ReadStopWords;
import utils.RemoveStopWord;
import utils.WriteFile;

import com.yhs.extract.ExtractText;
import com.yhs.main.MainWindow_;
import com.yhs.test.Dou;
/**
 * 匹配句子，生成摘要
 * @author yhs
 * date 2017.3.21
 */
public class S_AbstractExtract {

	static List<String> sentenceList = new  ArrayList<String>();//保存提取的句子
	static List<String> sentList = new  ArrayList<String>();//获取切分的句子
	static ArrayList<Double> _sentence_length_list = new ArrayList<Double>();//获取句子长度
	
	
	static String content = null;
	static String regax = "།";
	static HashMap<Integer, Double> weightMap = new HashMap<Integer, Double>();//句子编号  句子权值
	
	static List<Map.Entry<Integer, Double>> weightMap_Value_return = new 
			ArrayList<Map.Entry<Integer, Double>>();
	
	static List<Map.Entry<Integer, Double>> weightMap_Key_return = new 
			ArrayList<Map.Entry<Integer, Double>>();
	
	static HashMap<Integer, Double> weightMap_value_sort = new HashMap<Integer, Double>();//句子编号  句子权值
	
	static String self_save_filepath_ = null;//用户自定义文件保存路径
	
	public void Abstract(String path, List<String> pipeiWord_cp, List<String> pipeiWord_key,String title, 
			String wf_save_fileName,String digest_path) throws Exception{
		System.out.println("this abstractExtract" + "词频个数：" + pipeiWord_cp.size());
		System.out.println("this abstractExtract" + "关键词个数：" + pipeiWord_key.size());
		
		WriteFile writeFile = new WriteFile();
		
		self_save_filepath_ = MainWindow_.getSelf_save_filepath();

		if(self_save_filepath_ == null){
			self_save_filepath_ = "F:/Key_Phrases_Extraction/";
		}
		
		sentenceList.clear();
		weightMap_value_sort.clear();
		weightMap_Key_return.clear();
		weightMap_Value_return.clear();
		
		/*
		 * 提取content内容
		 */
		ExtractText extract = new ExtractText();
        extract.Extract(path);
        content = extract.getList().get(1);
        
        /*
         * 初始化句子权重
         */
        SentenceWeight_Set.setWeight(content,self_save_filepath_,digest_path,wf_save_fileName);
        weightMap = SentenceWeight_Set.getWeightMap();
        sentList = SentenceWeight_Set.getSentList();
        _sentence_length_list = Remove_Long_short.getSentence_length_list();//获取句子长度
        
        /*
         * 匹配句子是否含有关键词，
         * 词频  +0.5
         */
        Set<Integer> set = weightMap.keySet();
        for(Integer key : set) {
        	for(int j = 0;j < pipeiWord_cp.size();j++){
        		if(sentList.get(key).contains(pipeiWord_cp.get(j))){
        			Double value = weightMap.get(key);
        			value = value + 0.5;
        			weightMap.put(key, value);
				}
        	}
        }
       
        /*
         * 权值除以句子长度
         */
        Set<Integer> set3 = weightMap.keySet();
        for(Integer key : set3) {
        	Double value = weightMap.get(key); 
        	value = MathUtils.div(value, _sentence_length_list.get(key));
//			value = value/_sentence_length_list.get(key);
			weightMap.put(key, value);
        }
        /*
         * 关键词 +1.0
         */
        Set<Integer> set2 = weightMap.keySet();
        Double key_weight = 1.0;
        for(Integer key : set2) {
        	for(int j = 0;j < pipeiWord_key.size();j++){
        		if(sentList.get(key).contains(pipeiWord_key.get(j))){
        			Double value = weightMap.get(key);
        			value = value + key_weight;
//        			value = MathUtils.mul(value, key_weight);
        			weightMap.put(key, value);
				}
        	}
        }
        
        /*
         * 将设置的权重进行保存
         */
        File savefile_value = new File(self_save_filepath_ + "/" + digest_path +"_value"+ "/" + wf_save_fileName +"_value"+ ".txt");
        Set<Integer> set1 = weightMap.keySet();
        for(Integer key : set1) {
        	Double value = weightMap.get(key);
        	System.out.println("AbstractExtract_set " + key + "--" + value);
        	writeFile.writeFile(savefile_value , key + "----" + value + "\r\n");
        }
        
        /*
         * 引入title进行权值计算 
         * +1.5
         */
        String[] arr_title = title.split("/");
		ReadStopWords rsw = new ReadStopWords();//读取停用词
		try {
			rsw.readStopWords();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<String> stopWordList = new ArrayList<>();
		stopWordList = rsw.getStopWordList();
		
		RemoveStopWord rs = new RemoveStopWord();//去除停用词
		ArrayList<String> titleList = rs.removeStopWord_tilte(arr_title,stopWordList);
		for(int i = 0; i < titleList.size();i++){
			System.out.println("arr_title >>>>>>:" + titleList.get(i));
		}
		
		Set<Integer> set4 = weightMap.keySet();
		Double weight = 1.5;
		System.out.println(weight);
        for(Integer key : set4) {
        	for(int j = 0;j < titleList.size();j++){
        		if(sentList.get(key).contains(titleList.get(j))){
        			Double value = weightMap.get(key);
//        			Double result = MathUtils.mul(value, weight);
        			value = value + weight;
        			weightMap.put(key, MathUtils.round(value, 5));
				}
        	}
        }
        
        /*
         * 将设置的权重进行保存
         */
        File savefile_value1 = new File(self_save_filepath_ + "/" + digest_path +"_value1"+ "/" + wf_save_fileName +"_value1"+ ".txt");
        Set<Integer> set5 = weightMap.keySet();
        for(Integer key : set5) {
        	Double value = weightMap.get(key);
//        	System.out.println("AbstractExtract_set1 " + key + "--" + value);
        	writeFile.writeFile(savefile_value1 , key + "----" + value + "\r\n");
        }
        
        
        
        weightMap_Value_return = SentenceSort_Value.sentenceValueSort(weightMap);//句子权重排序
        
        File savefile_sort = new File(self_save_filepath_ + "/" + digest_path +"_sort"+ "/" + wf_save_fileName +"_sort"+ ".txt");
        for (int i = 0; i < weightMap_Value_return.size(); i++) {
		    System.out.println("AbstractExtract_sort " + i + " "+ weightMap_Value_return.get(i).getKey() 
		    		+ "---" + weightMap_Value_return.get(i).getValue());
        	writeFile.writeFile(savefile_sort, weightMap_Value_return.get(i).getKey() 
		    		+ "---" + weightMap_Value_return.get(i).getValue() + "\r\n");
		}
        
        /*
         * 提取文章的30%作为摘要
         */
        File savefile_value30 = new File(self_save_filepath_ + "/" + digest_path +"_value30"+ "/" + wf_save_fileName +"_value30"+ ".txt");
        for (int i = 0; i < weightMap_Value_return.size()*0.3; i++) {
//		    System.out.println("AbstractExtract " + i + " "+ weightMap_Value_return.get(i).getKey() 
//		    		+ "---" + weightMap_Value_return.get(i).getValue());
        	if(weightMap_Value_return.get(i).getValue()!=0){
        		weightMap_value_sort.put(weightMap_Value_return.get(i).getKey(), weightMap_Value_return.get(i).getValue());
    		    writeFile.writeFile(savefile_value30, weightMap_Value_return.get(i).getKey() 
    		    		+ "---" + weightMap_Value_return.get(i).getValue() + "\r\n");
        	}
		}
        
        weightMap_Key_return = SentenceSort_Key.sentenceKeySort(weightMap_value_sort);//对句子Key排序
        for (int i = 0; i < weightMap_Key_return.size(); i++) {
//		    System.out.println("AbstractExtract1 " + i + " "+ weightMap_Key_return.get(i).getKey() 
//		    		+ "---" + weightMap_Key_return.get(i).getValue());
		    if(weightMap_Key_return.get(i).getValue() != 0){//前30%如果包含权值为0的句子，则去掉
				sentenceList.add(sentList.get(weightMap_Key_return.get(i).getKey()));
		    }
		}
        
        /*
         * 将匹配的句子进行保存
         */
        File savefile = new File(self_save_filepath_ + "/" + digest_path + "/" + wf_save_fileName +"_digest"+ ".txt");
		for(int k = 0;k < sentenceList.size();k++){
//			System.out.println("匹配的句子： " + k + sentenceList.get(k));
			writeFile.writeFile(savefile , sentenceList.get(k) + "།" + "\r\n");
		}
	}

	public static List<String> getSentenceList() {
		return sentenceList;
	}

	public static void setSentenceList(List<String> sentenceList) {
		S_AbstractExtract.sentenceList = sentenceList;
	}
}
