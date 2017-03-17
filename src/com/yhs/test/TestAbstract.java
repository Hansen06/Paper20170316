package com.yhs.test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yhs.extract.ExtractText;

public class TestAbstract {

	static List<String> sentenceList = new ArrayList<String>();
	static String content = null;
	static String regax = "།";
	
	public static void main(String[] args) throws Exception {

//		Demo1();
		String[] wordKey = {"སློབ་གྲྭར","མཚོ་སྔོན་","མི་རིགས་","ཁོའི","སྤྱི་ལོ","པ་་","རྩོམ་རིག་","འགྲོ་"};
		String path = "E:/MUC/研究/基于Web的藏文文本主题词提取及摘要生成技术/2016未分词/2016未分词/9/1/0_qhzywlgg_0_0_2016-9-1_www.qhtb.cn.zhuanti.rsh.2016.09-01.44656.html.xml";
        ExtractText extract = new ExtractText();
        extract.Extract(path);
        content = extract.getList().get(1);
        System.out.println("content: " + content);
        String[] arr = content.split(regax);
		for(int i = 0;i < arr.length;i++){
			System.out.println("第" + (i+1) + "句: " + arr[i]);
			for(int j = 0;j < wordKey.length;j++){
				if(arr[i].contains(wordKey[j])){
					if(!sentenceList.contains(arr[i])){
						sentenceList.add(arr[i]);
					}
				}
			}
		}
		for(int k = 0;k < sentenceList.size();k++){
			System.out.println("匹配的句子： " + (k+1) + sentenceList.get(k));
		}
		
		
	}

	public List<String> getSentenceList() {
		return sentenceList;
	}

	public void setSentenceList(List<String> sentenceList) {
		this.sentenceList = sentenceList;
	}

	private static void Demo1() {
		String str = "ཉིན་ཤས་བསྟུད་མར་རང་ལྗོངས་ཀྱི་ས་གནས་ཁག་དང་སྡེ་ཚན་ཁག་གིས་ལྗོངས་ཡོངས་ཀྱི་འགོ་ཁྲིད་ལས་བྱེད་པའི་ཚོགས་འདུའི་དགོངས་དོན་སློབ་སྦྱོང་ལག་བསྟར་ནན་ཏན་བྱས་ཤིང་།";
		String regax = "།";
		Pattern patern = Pattern.compile(regax);
		Matcher match = patern.matcher(str);
		while(match.find()){
			System.out.println(match.group());
		}
	}

}
