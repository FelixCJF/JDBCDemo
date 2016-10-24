package com.jaxp.sax;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.jaxp.domain.Book;

/**
 * 根据jaxp包sax方式解析xml
 * @author aspire
 *
 */
public class Jaxp_Sax {
	//把书中的数据封装到JavaBeam中.
	public static void main(String[] args) throws Exception{
		//得到解析器
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		//得到读取器
		XMLReader reader = saxParser.getXMLReader();
		//定义一个集合,存储JavaBeam
		List<Book> books = new ArrayList<Book>();
		//定义一个集合,存储JavaBeam
		reader.setContentHandler(new MyContentHandler2(books));
	}
}
class MyContentHandler2 extends  DefaultHandler{
	//定义一个集合容器,引用主函数中的集合
    private  List<Book>  books ;

	public MyContentHandler2(List<Book> books) {
		this.books = books;
	}
	//定义一个容器类
	private  Book  book ;
	//定义一个容器字符串
    private  String  currentTagName ;
    public   void  startElement(String uri, String localName,
            String qName, Attributes attributes)  throws  SAXException {
         //如果读到的是书，创建book对象
         if ( "书" .equals(qName)){
             book  =  new  Book();
        }
         currentTagName  = qName;
    }
     public   void  characters( char [] ch,  int  start,  int  length)
             throws  SAXException {
         if ( currentTagName .equals( "书名" ))
         book .setName( new  String(ch,start,length));
         if ( currentTagName .equals( "作者" ))
         book .setAuthor( new  String(ch,start,length));
         if ( currentTagName .equals( "售价" ))
         book .setPrice( new  String(ch,start,length));
        }
     public   void  endElement(String uri, String localName, String qName)
             throws  SAXException {
         //如果读到的是书，把book对象加到集合中去
         if ( "书" .equals(qName)){
             books .add( book );
        }
         currentTagName  =  null ;
     }
}






