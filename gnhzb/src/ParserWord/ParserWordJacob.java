package ParserWord;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.junit.*;
//import org.apache.poi.hwpf.extractor.WordExtractor;
//import org.apache.poi.hwpf.HWPFDocument;
//import org.apache.poi.hwpf.usermodel.Paragraph;
//import org.apache.poi.hwpf.usermodel.Range;
//import org.apache.poi.hwpf.usermodel.Table;
//import org.apache.poi.hwpf.usermodel.TableCell;
//import org.apache.poi.hwpf.usermodel.TableIterator;
//import org.apache.poi.hwpf.usermodel.TableRow;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class ParserWordJacob {
    //Word路径
	private String m_strWordDir;
	//书签的名字
	private List<String> m_listBookMark;
	
	/**
	 * @构造函数
	 */
	public ParserWordJacob(String wordpath,List<String> listBookMark){
		m_strWordDir = wordpath;
		m_listBookMark =listBookMark;
	}
	
	/**
	 * @函数名：SetWordDir
	 * @功能：设置Word文件路径
	 * @作者：wangliwei
	 * @时间：2011-06-29
	 */
	public void SetList(List<String> listBookMark){
		m_listBookMark = listBookMark;
	}
	/**
	 * @函数名：SetWordDir
	 * @功能：设置Word文件路径
	 * @作者：wangliwei
	 * @时间：2011-06-29
	 */
	public void SetWordDir(String strWordparth){
		m_strWordDir = strWordparth;
	}
	
	/**
	 * @函数名：GetBookMarkValue 
	 * @功能：获取Word书签值
	 * @作者：wangliwei
	 * @时间：2011-06-29
	 */
	public String GetBookMarkValue(){
		
		//***************************************打桩*************************************************
//		String strKnType = "知识类型";
//		String strKnLevel = "密级";
//		String strKnChTitle = "标题";
//		String strKnAuther = "作者";
//		String strKnWork = "作者单位";
//		m_listBookMark.add(strKnType);
//		m_listBookMark.add(strKnLevel);
//		m_listBookMark.add(strKnChTitle);
//		m_listBookMark.add(strKnAuther);
//		m_listBookMark.add(strKnWork);
		//****************************************************************************************
		String strReturnValue = "";
		// 启动word
		ActiveXComponent app = new ActiveXComponent("Word.Application");
		//String docPath = strWrodPath;  // "D:\\2003.doc";
		// 这里的false表示生成过程不显示word的打开过程，而如果是true，则在生成过程中会显示word打开
		app.setProperty("Visible", new Variant(false));
		// 打开word文件
		Dispatch docs = app.getProperty("Documents").toDispatch();
		Dispatch doc = Dispatch.invoke((Dispatch) docs,	"Open",
				Dispatch.Method,new Object[] { m_strWordDir, new Variant(false),
						new Variant(true) }, new int[1]).toDispatch();

		try {
			
			Dispatch activeDocument = app.getProperty("ActiveDocument").toDispatch();
			Dispatch bookMark = app.call(activeDocument, "Bookmarks").toDispatch(); 
            
			for (Iterator iter = m_listBookMark.iterator(); iter.hasNext();)   
			{
				String bookMarkKey = iter.next().toString();
				boolean bookMarkExist = Dispatch.call(bookMark, "Exists",bookMarkKey).toBoolean();
				// 判断书签是否存在
				String bookMarkValue = "";
				String bookmarkNameAndValue  = "";
				if (bookMarkExist == true) {
					Dispatch rangeItem = Dispatch.call(bookMark, "Item",bookMarkKey).toDispatch(); 
					Dispatch range = Dispatch.call(rangeItem, "Range").toDispatch(); 
					// 获取书签处的值
					bookMarkValue = Dispatch.get(range, "Text").toString();
					//书签名和值
					if(null!=bookMarkValue)
					{		bookMarkValue=bookMarkValue.replaceAll("", "");
					bookMarkValue=bookMarkValue.trim();}
					bookmarkNameAndValue = bookMarkKey + "->" + bookMarkValue;
				}
		
				if (bookmarkNameAndValue != "") {

					// 组字符串
					if (strReturnValue == "") {
						strReturnValue = bookmarkNameAndValue;
					} else {
						//pl 下面这个判断，如果书签key是"知识模板"，那么就将知识模板的value加入到最前面，
						//如果不是，就加到后面，这样就确保知识模板放在最开始，返回去就可以拿到第一个设置为ktype
						if(null!=bookMarkKey&&bookMarkKey.equals("知识模板"))
						{
							strReturnValue = bookmarkNameAndValue+ "@#@"+strReturnValue  ;
						}else
						   strReturnValue = strReturnValue + "@#@"
								+ bookmarkNameAndValue;
					}
				}
			}
			//关闭Word
			Variant closeWord = new Variant(false);
			Dispatch.call(doc, "Close", closeWord);
		
			    Dispatch.call(app, "Quit");
			    app = null;
			


		} catch (Exception e) {
			Variant closeWrod = new Variant(false);
			Dispatch.call(doc, "Close", closeWrod);
			e.printStackTrace();
			if (app != null) 
			{
			    Dispatch.call(app, "Quit");
			    app = null;
			}

		}
		return strReturnValue;
	}
	
//	@Test
//	public void test()
//	{
//		String docPath = "D:\\2003.doc";
//		GetBookMarkValue(docPath);
//	}
	
	
	
//	/**
//	 * @param args
//	 */
//	public static  void main(String[] args) {
//	
//		String docPath = "D:\\2003.doc";
//		ParserWordJacob jTemp = new ParserWordJacob();
//		
//		String s = jTemp.GetBookMarkValue(docPath);
//		
//
//			//FileInputStream in = new FileInputStream("files\\2003.doc");// 载入文档
//			//			WordExtractor ex = new WordExtractor(in);
//			//			String text2003 = ex.getText();
//			//			String strPharText[] = ex.getParagraphText();
//			//			for(int i = 0;i<strPharText.length;i++){
//			//				System.out.println(strPharText[i]);
//			//			}
//			//			
//			//			POIFSFileSystem pfs = new POIFSFileSystem(in);
//			//			HWPFDocument hwpf = new HWPFDocument(pfs);
//			//			Range range = hwpf.getRange();// 得到文档的读取范围
//			//			TableIterator it = new TableIterator(range);
//			//			// 迭代文档中的表格
//			//			while (it.hasNext()) {
//			//				Table tb = (Table) it.next();
//			//				// 迭代行，默认从0开始
//			//				for (int i = 0; i < tb.numRows(); i++) {
//			//					TableRow tr = tb.getRow(i);
//			//					// 迭代列，默认从0开始
//			//					for (int j = 0; j < tr.numCells(); j++) {
//			//						TableCell td = tr.getCell(j);
//			//						// System.out.println(td.text());
//			//						// 取得单元格的内容
//			//						for (int k = 0; k < td.numParagraphs(); k++) {
//			//							Paragraph para = td.getParagraph(k);
//			//							String s = para.text();
//			//							System.out.println(s);
//			//							
//			////							System.out.println(s.replaceAll("\r", "")
//			////									.replaceAll(" ", "")
//			////									+ ":"
//			////									+ s2.replaceAll("\r", "").replaceAll(" ",
//			////											""));
//			//
//			//							// String text2003 = ex.getText();
//			//							// System.out.println(text2003);
//			//						}
//			//					}
//			//				}
//			//			}
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//	}
}
