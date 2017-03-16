package com.yhs.extract;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ExtractText {

	public List<String> list = new ArrayList<String>();

	public void Extract(String xPath) throws Exception {

		
		SAXReader reader = new SAXReader();// 创建saxReader对象
//		Document document = reader.read(new File("resource/sida.xml"));//通过read方法读取一个文件 转换成Document对象  
//		Document document = reader.read(new File("E:/MUC/藏文文件/藏文/0_rmwzyb_0_0_2015-12-4-_tibet.people.com.cn.15553495.html_seg.xml"));
		Document document = reader.read(new File(xPath));
		
		Element node = document.getRootElement();//获取根节点元素对象
		
		Element element_title = node.element("title");//获取子节点
	    String title = element_title.getText();
	    list.add(title);
		
		Element element_content = node.element("content");//获取子节点
	    String content = element_content.getText(); 
	    list.add(content);
	    
	    System.out.println("extract.java");
	    
	  //listNodes(node);//遍历所有的元素节点  
	}
	
	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
	
	/**
	 *  遍历当前节点元素下面的所有(元素的)子节点 
	 * @param node
	 */
	public void listNodes(Element node) {
		System.out.println("当前节点的名称：" + node.getName());

		List<Attribute> list = node.attributes();//获取当前节点的所有属性节点
		//遍历属性节点
		for (Attribute attr : list) {
			System.out.println(attr.getText() + "-----" + attr.getName()
					+ "---" + attr.getValue());
		}

		if (!(node.getTextTrim().equals(""))) {
			System.out.println("文本内容: " + node.getText());
		}

		// 当前节点下面子节点迭代器
		Iterator<Element> it = node.elementIterator();
		while (it.hasNext()) {
			// 获取某个子节点对象
			Element e = it.next();
			// 对子节点进行遍历
			listNodes(e);
		}
	}
}
