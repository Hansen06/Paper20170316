package com.yhs.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import utils.ReadFile;

import com.yhs.extract.ExtractText;
/*
 * 设计按钮动作
 * by yhs
 * date 2017/3/13
 */
public class MultipleBtnListener implements ActionListener {

	private JButton m_btn_choose_; 
	private JTextField m_textField_filename_;
	
	private boolean isDirectory_ = false;
	
	
	static ArrayList<String> filepathList_ = new ArrayList<String>();//保存文件路径
	static ArrayList<String> filenameList_ = new ArrayList<String>();//保存文件名

	static ArrayList<String> contentList_ = new ArrayList<String>();//保存提取的content内容
	static ArrayList<String> titleList_ = new ArrayList<String>();//保存提取的title内容
	
	static ArrayList<String> rootpath_ = new ArrayList<String>();//保存根路径
	
	static ArrayList<String> digest_root_path_list_ = new ArrayList<String>();//摘要保存路径
	/*
	 *添加构造函数，接收传入的参数，以作出动作 
	 */
	public MultipleBtnListener(JButton m_btn_choose, JTextField m_textField_filename) {
		super();
		this.m_btn_choose_ = m_btn_choose;
		this.m_textField_filename_ = m_textField_filename;
	}

	/*
	 * 实现自己定义的动作
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		filenameList_.clear();
		filepathList_.clear();
		contentList_.clear();
		titleList_.clear();
		rootpath_.clear();
		digest_root_path_list_.clear();
		
		JFileChooser fileChooser = new JFileChooser();//创建文件选择对话框
		
		//使用指定目录
		fileChooser.setCurrentDirectory(new File("E:/MUC/研究/基于Web的藏文文本主题词提取及摘要生成技术/2016已分词/2016已分词/"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML文件", "xml");
		fileChooser.setFileFilter(filter);
		fileChooser.setMultiSelectionEnabled(true);//设置是否可以多选

		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); //选择文件模式  
		int i = fileChooser.showOpenDialog(fileChooser);//显示文件选择对话框
        File file = fileChooser.getSelectedFile();  
        
//        GetFileName getName = new GetFileName();
//        wf_save_fileName = getName.getFileNameNoEx(fileChooser.getDescription(file));//获得文件名 用來保存统计的词频
//        
//        System.out.println("仅得到所选择的文件名：" + wf_save_fileName);
        System.out.println("验证："+file);
        
        /*
         * 读取全部文件
        */
        ReadFile.filepathList.clear();
        ReadFile.filenameList.clear();
        ReadFile.rootpathList.clear();
        ReadFile.digest_root_path_list.clear();
        

        if(file.isDirectory()){  
        	isDirectory_ = true;
            System.out.println("文件夹:" + file.getAbsolutePath()); 
            
            ReadFile.getAllFiles(new File(file.getAbsolutePath()), 0);//得到全部文件路径
	        ReadFile.print();
	        filepathList_ = ReadFile.getFilepathList();//保存全部文件路径
	        filenameList_ = ReadFile.getFilenameList();//保存全部文件名
	        rootpath_ = ReadFile.getRootpathList();//保存根路径
	        digest_root_path_list_ = ReadFile.getDigest_root_path_list();
        }		
		
		//判断用户单击的是否为“打开”按钮
		if(i == JFileChooser.APPROVE_OPTION){
			File selectedFile = fileChooser.getSelectedFile();//获取选中的文件对象
			m_textField_filename_.setText(selectedFile.getName());//显示选中文件的名称

			ExtractText extract = new ExtractText();
			/*
			 * 提取多个文件
			 */
			if(isDirectory_ == true){
				for(int j = 0; j < filepathList_.size(); j++){
//					System.out.println("filepathList_.get(j)???  " + filepathList_.get(j));
					try {
						extract.Extract(filepathList_.get(j));
//						System.out.println("extract.getList().get(1) " + extract.getList().get(1));
						titleList_.add(extract.getList().get(0));//保存提取的title
						contentList_.add(extract.getList().get(1));//保存提取的content
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		
//		for(int i1 = 0;i1 < filenameList_.size();i1++){
//			System.out.println("filename: " + filenameList_.get(i1));
//			System.out.println("content: " + contentList_.get(i1));
//		}
	}


	public static ArrayList<String> getDigest_root_path_list_() {
		return digest_root_path_list_;
	}

	public static void setDigest_root_path_list_(
			ArrayList<String> digest_root_path_list_) {
		MultipleBtnListener.digest_root_path_list_ = digest_root_path_list_;
	}

	public static ArrayList<String> getRootpath_() {
		return rootpath_;
	}

	public static void setRootpath_(ArrayList<String> rootpath_) {
		MultipleBtnListener.rootpath_ = rootpath_;
	}

	public static ArrayList<String> getFilepathList_() {
		return filepathList_;
	}

	public static void setFilepathList_(ArrayList<String> filepathList_) {
		MultipleBtnListener.filepathList_ = filepathList_;
	}

	public static ArrayList<String> getFilenameList_() {
		return filenameList_;
	}

	public static void setFilenameList_(ArrayList<String> filenameList_) {
		MultipleBtnListener.filenameList_ = filenameList_;
	}

	public static ArrayList<String> getContentList_() {
		return contentList_;
	}

	public static void setContentList_(ArrayList<String> contentList_) {
		MultipleBtnListener.contentList_ = contentList_;
	}
	public static ArrayList<String> getTitleList_() {
		return titleList_;
	}

	public static void setTitleList_(ArrayList<String> titleList_) {
		MultipleBtnListener.titleList_ = titleList_;
	}
}
