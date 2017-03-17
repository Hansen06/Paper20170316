package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.junit.Test;
/*
 * 读取停用词工具类
 */
public class ReadStopWords {

	ArrayList<String> stopWordList = new ArrayList<>();
	
	public ArrayList<String> getStopWordList() {
		return stopWordList;
	}

	public void setStopWordList(ArrayList<String> stopWordList) {
		this.stopWordList = stopWordList;
	}

	/*
	 * 读取停用词表
	 */
	@Test
	public void readStopWords() throws IOException{
		String encoding="utf-8";
        File file=new File("file/filter.txt");
        String regex1 = "\\s";
        WriteFile writeFile = new WriteFile();
        if(file.isFile() && file.exists()){ //判断文件是否存在
            InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while((lineTxt = bufferedReader.readLine()) != null){
//            	stopWordList.add(lineTxt.replace(regex1,""));
            	stopWordList.add(lineTxt);
//            	System.out.println(">>>..." + lineTxt);
//            	writeFile.writeFile("F:/shareget/b.txt" , lineTxt + "\r\n");
             }
            read.close();
            System.out.println("停用词读取完毕！");
	     }else{
	            System.out.println("找不到指定的文件");
        }
	}
}
