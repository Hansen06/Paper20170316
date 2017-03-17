package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
 * 读取关键词表，放入list
 * by yhs 
 * date 2017/3/9
 */
public class ReadKeyWord {
	
	public static ArrayList<String> keywordList = new ArrayList<String>();
	
	/*
	 * 获取关键词，并返回
	 */
	 public ArrayList<String> getKeyword() {
		return keywordList;
	}

	public void setKeyword(ArrayList<String> keywordList) {
		ReadKeyWord.keywordList = keywordList;
	}

	
	public void readTxtFile(String filePath){
        try{
	        String encoding="utf-8";
	        File file=new File(filePath);
	        if(file.isFile() && file.exists()){ //判断文件是否存在
	            InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
	            BufferedReader bufferedReader = new BufferedReader(read);
	            String lineTxt = null;
	            String regex = "[\\w]|[-]";
	            while((lineTxt = bufferedReader.readLine()) != null){
	            	/*
	            	 * 如果读到空行就跳过
	            	 */
	            	if(!lineTxt.equals("")){
	            		String str = lineTxt.replaceAll(regex, "");
	            		keywordList.add(str);	                      
	                	}
	 
	             }
	            read.close();
		     }else{
		            System.out.println("找不到指定的文件");
	        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
	}
}
