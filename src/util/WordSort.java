package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder.In;
/*
 * 词频排序
 */
public class WordSort{
	
	public String[] get_words() {
		return _words;
	}

	public void set_words(String[] _words) {
		this._words = _words;
	}

	public int[] get_wordFreqs() {
		return _wordFreqs;
	}

	public void set_wordFreqs(int[] _wordFreqs) {
		this._wordFreqs = _wordFreqs;
	}

	Map<String, Integer> wordMap = new HashMap<>();
	String[] _words = null;
	int[] _wordFreqs = null;
 	
	public void SortWord(String[] words, int[] wordFreqs){
		_words = words;
		_wordFreqs = wordFreqs;
		for(int i = 0;i < _words.length;i++){
			wordMap.put(_words[i], _wordFreqs[i]);
		}
		
		List<Map.Entry<String, Integer>> _wordMap = new ArrayList<Map.Entry<String, Integer>>(wordMap.entrySet());
		
		//排序
		Collections.sort(_wordMap, new Comparator<Map.Entry<String, Integer>>() {   
		    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {      
		        return (o2.getValue() - o1.getValue()); 
//		        return (o1.getKey()).toString().compareTo(o2.getKey());
		    }
		}); 
			
		//排序后
		for (int i = 0; i < _wordMap.size(); i++) {
		    String id = _wordMap.get(i).toString();
//		    System.out.println(">>>..." + id);
//		    System.out.println("*****  " + _wordMap.get(i).getKey());
//		    System.out.println("&&&&&  " + _wordMap.get(i).getValue());
		    _words[i] = _wordMap.get(i).getKey();
		    _wordFreqs[i] = _wordMap.get(i).getValue();
		}
		System.out.println("词频排序完毕！");
	}
}
