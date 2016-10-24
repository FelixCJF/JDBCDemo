package com.jaxp.dom;

import javax.xml.crypto.dom.DOMStructure;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Jaxp 的dom解析方式
 * @author aspire
 *
 */
public class Jaxp_Dom {
	
	/**
	 * 获得document
	 */
	public Document getDocument(){
		Document document;
		try {
			//首先得到 doucment,先得到工厂,再由工厂得到DocumentBuilder,最后得到Document.
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			document = builder.parse("src/book.xml");
			return document;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//  1、得到某个具体的节点内容:第2本书的主体内容 <书名>葵花宝典</书名>
	@Test
	public void test1() throws Exception{
		Document document = getDocument();
		
		//根据书名获得书签
		NodeList nodeList = document.getElementsByTagName("书名");
		//第二本书，得到第二本书的书名标签
		Node node = nodeList.item(1);
		//获得主体内容
		String content = node.getTextContent();
		System.out.println(content);
	}
	
	//2、遍历所有元素节点:递归。打印遍历到的元素名称,Document继承Node,传递Document进来即可
	public static void main(String[] args)throws Exception {
		//首先得到 doucment,先得到工厂,再由工厂得到DocumentBuilder,最后得到Document.
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document document = builder.parse("src/book.xml");
		test2(document);
	}
	
	public static void test2(Node node){		
		//判断是否是一个节点，是的话打印节点名称
		if (node.getNodeType()== Node.ELEMENT_NODE) {
			System.out.println(node.getNodeName());
		}
		//不是得话，得到下一个节点，递归
		NodeList nodes = node.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			test2(n);
		}
	}
	
	//3、修改某个元素节点的主体内容:第2本书售价改为2元
	@Test
	public void teste3()throws Exception{
		Document document = getDocument();
		
		//根据书名获得标签
		NodeList nodeList = document.getElementsByTagName("售价");
		//获得第二本书的售价
		Node node = nodeList.item(1);
		//修改售价
		node.setTextContent("2.00元");
		//把修改内容写回xml文件中
		 Transformer transformer = TransformerFactory.newInstance().newTransformer();
		
		 transformer.transform(new DOMSource(document), new StreamResult("src/book.xml"));
		
	}
	
	//4、向指定元素节点中增加子元素节点:给第一本书增加一个<内部价>58.00元</内部价>
	@Test
	public void test4() throws Exception{
		Document document = getDocument();
		//创建新元素
		Element element = document.createElement("内部价");
		element.setTextContent("58.00元");
		//得到第一本书
		Node node = document.getElementsByTagName("书").item(0);
		node.appendChild(element);
		//写回
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(new DOMSource(document), new StreamResult("src/book.xml"));
		
	}
	
	//5、向指定元素节点上增加同级元素节点,售价上面增加批发价
	@Test
	public void test5()throws Exception{
		Document document = getDocument();
		//创建新元素
		Element element = document.createElement("批发价");
		element.setTextContent("48.00元");
		//得到插入点的标签(售价),需要父节点才可以操作子节点
		Node node = document.getElementsByTagName("售价").item(0);
		Node pNode = node.getParentNode();
		pNode.insertBefore(element, node);
		//把修改内容写回xml文件中
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(new DOMSource(document), new StreamResult("src/book.xml"));
	}
	
	//6、删除指定元素节点:删除批发价
	@Test
	public void test6()throws Exception{
		Document document = getDocument();
		//得到批发价节点
		Node node = document.getElementsByTagName("批发价").item(0);
		//2.删除节点,必须有批发价的父节点来做
		Node pNode = node.getParentNode();
		pNode.removeChild(node);
		//把修改内容写回xml文件中
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(new DOMSource(document), new StreamResult("src/book.xml"));
	}
	
	//7、操作XML文件属性:给第一本书添加属性  出版社="黑马"
	@Test
	public void test7()throws Exception{
		Document document = getDocument();
		//获得第一本书
		Element element = (Element) document.getElementsByTagName("书").item(0);
		//2.给第一本书增加属性
		element.setAttribute("出版社", "机械工业出版社");
		//把修改内容写回xml文件中
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(new DOMSource(document), new StreamResult("src/book.xml"));
	}


}
