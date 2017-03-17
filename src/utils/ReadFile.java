package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
/*
 * 读取某个文件夹下的所有文件
 * @param filepath:文件夹路径
 */
public class ReadFile {
	
	
	public static ArrayList<String> filepathList = new ArrayList<String>();
	public static ArrayList<String> filenameList = new ArrayList<String>();
     
	
	public static boolean readfile(String filepath) throws FileNotFoundException, IOException {
	
        try {
            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println("文件");
//                System.out.println("path=" + file.getPath());
//                System.out.println("absolutepath=" + file.getAbsolutePath());
//                System.out.println("name=" + file.getName());
                
                filepathList.add(file.getName());
                

            } else if (file.isDirectory()) {
                System.out.println("文件夹");
                String[] filelist = file.list();
                System.out.println("文件个数: "+filelist.length);
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
//                        System.out.println("path=" + readfile.getPath());
//                        System.out.println("absolutepath=" + readfile.getAbsolutePath());
//                        System.out.println("name=" + readfile.getName());
                    	filepathList.add(readfile.getName());

                    } else if (readfile.isDirectory()) {
                        readfile(filepath + "\\" + filelist[i]);
                    }
                }

            }

        } catch (FileNotFoundException e) {
                System.out.println("readfile()   Exception:" + e.getMessage());
        }
              
        return true;
    }

	 //获取层级的方法  
    public static String getLevel(int level)  
    {  
        //A mutable sequence of characters.  
        StringBuilder sb = new StringBuilder();  
        for(int l = 0; l < level; l++)  
        {  
            sb.append("|--");  
        }  
        return sb.toString();  
    }  
    public static boolean getAllFiles(File dir,int level)  
    {  

        level++;  
        File[] files = dir.listFiles();  
        for(int i = 0;i < files.length; i++)  
        {  
            if(files[i].isDirectory())  
            {  
                //这里面用了递归的算法  
                getAllFiles(files[i],level);  
            }  
            else {  
            	GetFileName getFileName = new GetFileName();
//            	  System.out.println("bbb: " + files[i]);
//                System.out.println("aaa: " + i + " " + getFileName.getFileNameNoEx(files[i].getName()));  
                filepathList.add(files[i].toString());//保存文件路径
      
                filenameList.add(getFileName.getFileNameNoEx(files[i].getName()));//保存文件名称
            }     
        } 
        return true;
    }  
	
    public static ArrayList<String> getFilenameList() {
		return filenameList;
	}

	public static void setFilenameList(ArrayList<String> filenameList) {
		ReadFile.filenameList = filenameList;
	}

	public static void print(){
    	 for(int i = 0; i < filepathList.size(); i++){
         	System.out.println("list file name： " + i + " " + filepathList.get(i));
         }
    }

	public static ArrayList<String> getFilepathList() {
		return filepathList;
	}

	public static void setFilepathList(ArrayList<String> filepathList) {
		ReadFile.filepathList = filepathList;
	}

}
