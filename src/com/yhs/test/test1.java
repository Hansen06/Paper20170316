package com.yhs.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class test1 {

	public static void main(String[] args) {
//		demo1();
//		demo2();
//		demo3();
	}

	private static void demo3() {
		ArrayList<HashMap<String, String>> array = new ArrayList<HashMap<String, String>>();
	    HashMap<String, String> hm1 = new HashMap<String, String>();
	    hm1.put("周瑜", "小乔");
	    hm1.put("吕布", "貂蝉");
	    array.add(hm1);
	    HashMap<String, String> hm2 = new HashMap<String, String>();
	    hm2.put("郭靖", "黄蓉");
	    hm2.put("杨过", "小龙女");
	    array.add(hm2);
	    HashMap<String, String> hm3 = new HashMap<String, String>();
	    hm3.put("令狐冲", "任盈盈");
	    hm3.put("林平之", "岳灵珊");
	    array.add(hm3);
	    for(HashMap<String, String> hm :array) {
	        Set<String> set = hm.keySet();
	        for(String key : set) {
	            String value = hm.get(key);
	            System.out.println(key+"----"+value);
	        }
	    }
	}

	private static void demo2() {
		HashMap<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>> ();
	    ArrayList<String> array1 = new ArrayList<String>();
	    array1.add("吕布");
	    array1.add("周瑜");
	    hm.put("三国演义", array1);
	    ArrayList<String> array2 = new ArrayList<String>();
	    array2.add("令狐冲");
	    array2.add("林平之");
	    hm.put("笑傲江湖", array2);
	    ArrayList<String> array3 = new ArrayList<String>();
	    array3.add("郭靖");
	    array3.add("杨过");
	    hm.put("神雕侠侣", array3);
	    Set<String> set = hm.keySet();
	    for (String key :set) {
	        System.out.println(key);
	        ArrayList<String> value = hm.get(key);
	        for (String s : value) {
	            System.out.println("\t"+s);
	            
	        }
	    }
	}

	private static void demo1() {
		int l = 0;
		int a = (l==0)?1:3;
		System.out.println(a);
	}
}
