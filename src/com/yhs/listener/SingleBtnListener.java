package com.yhs.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import utils.GetFileName;

import com.yhs.extract.ExtractText;
/*
 * 设计按钮动作
 * by yhs
 * date 2017/3/13
 */
public class SingleBtnListener implements ActionListener {

	private JButton btn_choose_; 
	private JTextField textField_filename_;
	
	static private String xpath = null;//文件路径
	static private String content = null;//文本内容
	static private String title = null;//标题内容
	static private String wf_save_fileName = null;	//获取保存文本名称
	static private String root_path = null;
	static private String digest_root_path = null;
	
	/*
	 *添加构造函数，接收传入的参数，以作出动作 
	 */
	public SingleBtnListener(JButton btn_choose, JTextField textField_filename) {
		super();
		this.btn_choose_ = btn_choose;
		this.textField_filename_ = textField_filename;
	}

	/*
	 * 实现自己定义的动作
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		JFileChooser fileChooser = new JFileChooser();//创建文件选择对话框
		
		//使用指定目录
		fileChooser.setCurrentDirectory(new File("E:/MUC/研究/基于Web的藏文文本主题词提取及摘要生成技术/2016已分词/2016已分词/"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML文件", "xml");
		fileChooser.setFileFilter(filter);
		fileChooser.setMultiSelectionEnabled(true);//设置是否可以多选

		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); //选择文件模式  
		int i = fileChooser.showOpenDialog(fileChooser);//显示文件选择对话框
        File file = fileChooser.getSelectedFile();  
        
        GetFileName getName = new GetFileName();
        wf_save_fileName = getName.getFileNameNoEx(fileChooser.getDescription(file));//获得文件名 用來保存统计的词频
        
        System.out.println("仅得到所选择的文件名：" + wf_save_fileName);
        System.out.println("验证：" + file);
        
        /*
         * 读取全部文件
        */
    	xpath = file.getAbsolutePath();
        System.out.println("文件:" + xpath);
        
        String[] str = xpath.split("\\\\");
        root_path = str[str.length-3] + "/" + str[str.length-2];
        digest_root_path = root_path + "_digest";
		System.out.println("root_path " + root_path + " digest " + digest_root_path);
		
		//判断用户单击的是否为“打开”按钮
		if(i == JFileChooser.APPROVE_OPTION){
			File selectedFile = fileChooser.getSelectedFile();//获取选中的文件对象
			textField_filename_.setText(selectedFile.getName());//显示选中文件的名称

			ExtractText extract = new ExtractText();
			/*
			 * 提取单个文件
			 */
		   try {
				extract.Extract(xpath);
				title = extract.getList().get(0);
				content =  extract.getList().get(1);
				System.out.println("content: "+content);
				System.out.println("title: "+title);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	public static String getDigest_root_path() {
		return digest_root_path;
	}

	public static void setDigest_root_path(String digest_root_path) {
		SingleBtnListener.digest_root_path = digest_root_path;
	}

	public static String getXpath() {
		return xpath;
	}

	public static void setXpath(String xpath) {
		SingleBtnListener.xpath = xpath;
	}

	public static String getContent() {
		return content;
	}

	public static void setContent(String content) {
		SingleBtnListener.content = content;
	}

	public static String getWf_save_fileName() {
		return wf_save_fileName;
	}

	public static void setWf_save_fileName(String wf_save_fileName) {
		SingleBtnListener.wf_save_fileName = wf_save_fileName;
	}

	public static String getRoot_path() {
		return root_path;
	}

	public static void setRoot_path(String root_path) {
		SingleBtnListener.root_path = root_path;
	}

	public static String getTitle() {
		return title;
	}

	public static void setTitle(String title) {
		SingleBtnListener.title = title;
	}

}
