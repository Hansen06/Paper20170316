package com.yhs.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Statistics {

	private String[] words;
	private int[] wordFreqs;
	
	public void countWordFreq (String[] rawWords) { // 统计单词个数
		
		Set<String> set = new TreeSet<String>(); // 将所有出现的字符串放入唯一的set中
		for (String word : rawWords) {
			set.add(word);
		}
		
		Iterator<String> it = set.iterator();

		List<String> wordsList = new ArrayList<String>(); // 开辟空间函数
		List<Integer> freqList = new ArrayList<Integer>();

		while (it.hasNext()) {
			String word = (String) it.next();

			int count = 0; // 统计相同字符串的个数
			for (String str : rawWords) {
				if (str.equals(word)) {
					count++;
				}
			}

			wordsList.add(word);
			freqList.add(count++);
		}

		System.out.println("freqList size: " + freqList.size());
		System.out.println("wordsList size: " + wordsList.size());
		
		words = wordsList.toArray(new String[0]); // 存入数组当中
		wordFreqs = new int[freqList.size()];
		for (int i = 0; i < freqList.size(); i++) {
			wordFreqs[i] = freqList.get(i);
		}
		
		wordsList.clear();
		freqList.clear();
			
	}

	public String[] getWords() {
		return words;
	}

	public void setWords(String[] words) {
		this.words = words;
	}

	public int[] getWordFreqs() {
		return wordFreqs;
	}

	public void setWordFreqs(int[] wordFreqs) {
		this.wordFreqs = wordFreqs;
	}
}
