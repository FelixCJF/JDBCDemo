package com.dom4j.exercise;

import java.io.FileOutputStream;
import java.util.List;

import javax.lang.model.util.Elements;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

/**
 * dom4j练习
 * @author aspire
 *
 */
public class Dom4J_Dom {
	
	/**
	 * 得到document
	 */
	public Document getDocument() {
		//得到解析器
		SAXReader reader = new SAXReader();
		//得到document
		Document document;
		try {
			document = reader.read("src/book.xml");
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 1、得到某个具体的节点内容:第2本书的作者
	@Test
	public void test1(){
		Document document = getDocument();
		//得到根元素
		Element root = document.getRootElement();
		// 2.得到根元素书里面所有(书)元素的集合(只得到儿子,不得到孙子),注意不是get方法,是element.
		List<Element> books = root.elements("书");
		//得到第二本书
		Element secondBook = books.get(1);
		//得到书中的作者元素
		Element authour = secondBook.element("作者");
		System.out.println(authour.getTextTrim());
	}
	// 3、修改某个元素节点的主体内容
    // 第二本书售价由2.00元变为1.00元
	@Test
	public void test2() throws Exception{
		Document document = getDocument();
		//得到根元素
		Element root = document.getRootElement();
		List<Element> books = root.elements("书");
		Element secondBook =  books.get(1);
		 // 4.得到书中的(售价)元素,修改
		secondBook.element("售价").setText("1.00元");
		// 5.写入到文件.复制DOM4J文档说明Writing a document to a file
		OutputFormat format = OutputFormat.createPrettyPrint();//好看的,默认的输出格式.
		//OutputFormat format = OutputFormat.createCompactFormat(); // 不好看的,机器看的
		format.setEncoding("UTF-8");
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/book.xml"),format);
		writer.write(document);
		writer.close();
	}
	
	// 4、向指定元素节点中增加子元素节点 :在第一本书增加内部价节点,48.00元
    private void test4() throws Exception  {
    	Document document = getDocument();
        // 1.创建元素DocumentHelper,这个类可以创建好多东西;
       Element e = DocumentHelper. createElement ( "内部价" ).addText( "48.00元" );
        // 2.添加元素到文档中,有父亲才可以添加.
       document.getRootElement().element( "书" ).add(e);
        // 3.写入元素
       XMLWriter writer =  new  XMLWriter( new  FileOutputStream( "src/book.xml" ));
       writer.write(document);
       writer.close();
   }
    // 5、向指定元素节点上增加同级元素节点,在售价上增加批发价58.00元.
    private void test5() throws Exception  {
    	Document document = getDocument();
        // 1.创建元素DocumentHelper,这个类可以创建好多东西;
       Element e = DocumentHelper.createElement("批发价").addText( "58.00元" );
        // 2.添加元素到文档中,得到第一本书的孩子们,他们在一个集合中,操作这个集合即可.
       Element book = document.getRootElement().element( "书" );
       List<Element> list =  book.elements() ;
        // 3.批发价是插入到第三个元素,index为2
       list.add(2, e);
        // 4.写入元素
       XMLWriter writer =  new  XMLWriter( new  FileOutputStream( "src/book.xml" ));
       writer.write(document);
       writer.close();
   }
    // 6、删除指定元素节点:删除第一本书的批发价
    private void test6()  throws  Exception  {
    	Document document = getDocument();
        // 1.删除元素,得到第一本书的孩子们,他们在一个集合中,操作这个集合即可.
       Element book = document.getRootElement().element( "书" );
       List<Element> list =  book.elements() ;
        // 2.批发价是插入到第三个元素,index为2
       list.remove(2);
        // 3.写入元素
       XMLWriter writer =  new  XMLWriter( new  FileOutputStream( "src/book.xml" ));
       writer.write(document);
       writer.close();
   }
    // 7、操作XML文件属性：第二本书添加出版社="传智"
    private  void test7() throws Exception{
    	Document document = getDocument();
        // 1.得到根元素
       Element root = document.getRootElement();
        // 2.找到第二本书,增加属性,因为是第二本,不是第一本,所以要从集合里区.第一本直接取
       Element book2 = (Element)root.elements( "书" ).get(1);
       book2.addAttribute( "出版社" ,  "机械工业" );
        // 3.写入元素
       XMLWriter writer =  new  XMLWriter( new  FileOutputStream( "src/book.xml" ));
       writer.write(document);
       writer.close();
    }
	
}




