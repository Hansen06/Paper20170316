package utils;

import java.io.File;
/*
 * 将数据写入文本
 * by yhs 2016-12-20
 */
import java.io.FileOutputStream;
/*
 * 写文件工具类
 * @param path：文件路径  content:写入的内容
 * @return 无
 */
public class WriteFile {
	
	public void writeFile(String path, String content) {  
        String str = "this  is a program"; // 要写入的内容  
        try {  
            FileOutputStream out = new FileOutputStream(path,true); // 输出文件路径  
            out.write(content.getBytes());
            out.flush();
            out.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        System.out.println("写入文件成功！");
    }  
	public void writeFile(File dir, String content) {  
        String str = "this  is a program"; // 要写入的内容  
        
        File fileParent = dir.getParentFile();  
        if(!fileParent.exists()){  //如果文件夹不存在则创建
            fileParent.mkdirs();  
            try {  
                FileOutputStream out = new FileOutputStream(dir,true); // 输出文件路径  
                out.write(content.getBytes());
                out.flush();
                out.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            System.out.println("写入文件成功！");
        }else{
        	try {  
                FileOutputStream out = new FileOutputStream(dir,true); // 输出文件路径  
                out.write(content.getBytes());
                out.flush();
                out.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            System.out.println("写入文件成功！");
        }
    }  
}
