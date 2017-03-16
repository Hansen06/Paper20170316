package com.yhs.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import util.ReadStopWords;
import util.WriteFile;

public class Relative_paths_to_read {

	static ArrayList<String> list = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		demo1();
//		ReadStopWords read = new ReadStopWords();
//		read.readStopWords();
//		list = read.getStopWordList();
//		for(int i = 0;i < list.size();i++){
//			System.out.println(">>>>...." + list.get(i));
//		}
	}

	private static void demo1() throws UnsupportedEncodingException,
			FileNotFoundException, IOException {
		String encoding="utf-8";
        File file=new File("file/filter.txt");
        WriteFile writeFile = new WriteFile();
        if(file.isFile() && file.exists()){ //判断文件是否存在
            InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            String regex = "[\\w]|[-]";
            while((lineTxt = bufferedReader.readLine()) != null){
            	System.out.println(">>>..." + lineTxt);
            	writeFile.writeFile("F:/shareget/a.txt" , lineTxt);
             }
            read.close();
	     }else{
	            System.out.println("找不到指定的文件");
        }
	}

}
