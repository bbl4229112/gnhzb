package ParserWord;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Test;

import edu.zju.cims201.GOF.util.Constants;

import java.util.ArrayList;
import java.util.List;
public class ParserDandianWord {
	/**
	 * 一个表信息
	 */
	private List<List<String> > listTabInfo;
	/**
	 * 整个文件信息
	 */
	private List<List<List<String> > > listFileInfo;
	private String strHead;
	private String strheadHtml;
	private String m_strFileDir;       //文件路径
	//每个单元格的信息
	class CellInfo{
		public String strValue; //单元格值
		public int  rowSpan;    //占几个行
		public CellInfo(){
			strValue = "";
			rowSpan = 1;
		}
	};
	
	/**
	 * 
	 */
	public List<List<List<String> > > GetFileInfo(String docPath){

		
		
//		 docPath = "D:\\Workspaces\\Test\\files\\附件3.doc";
//	String docPath = "D:\\Workspaces\\Test\\files\\表1 船箭锁紧装置单机（组件）单点故障分析表.doc";
//	String docPath = "D:\\Workspaces\\Test\\files\\20032.doc";
//	String docPath = "D:\\Workspaces\\Test\\files\\表1T1火箭单点故障涉及产品关键（关注）特性控制汇总表.doc";
//ConstructOneLineHead();
		int type=0;
	try {
		 type=getTableType(docPath);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("type==="+type);
	if(type==1)
ConstructOneLineHead();
	if(type==2)
ConstructTwoLineHead();
//	if(type==6)
//ConstructSixLineHead(docPath);
if(type!=0)
ParserWord(docPath,type);
System.out.println("共有表数="+listFileInfo.size());
for (List<List<String> >  rowlist : listFileInfo) {
	System.out.println("共有知识行数"+rowlist.size());
	for (List<String> collist : rowlist) {
		System.out.println("共有属性数"+collist.size());
		for (String string : collist) {
			System.out.println("属性值分别为:"+string);
		}
		
	}
	
}

		
		
		return listFileInfo;
	}
	/**
	 * @Constuct
	 */
	public ParserDandianWord(){
		listTabInfo = new ArrayList<List<String> >();
		listFileInfo = new  ArrayList<List<List<String> > >();
		strHead = "";
		strheadHtml = "";
	}

	
	@Test
	public void test()
	{
		GetFileInfo("");	
	}
	/**
	 * @param args
	 */
	public static  void main(String[] args) {
		ParserDandianWord p=new	ParserDandianWord();
	p.test();
	}
	public int getTableType(String filepath) throws IOException
	{
		
		InputStream in = new FileInputStream(filepath);
		  String header;
		  int type=0;

		  WordExtractor wordExtractor = new WordExtractor(in);
		  header = wordExtractor.getHeaderText();
		//  System.out.println("header=="+header);
		  if(null!=header&&header.trim().equals(Constants.HEADLINE1))
			  type=1;
		  if(null!=header&&header.trim().equals(Constants.HEADLINE2))
			  type=2;
		 if(null!=header&&header.trim().equals(Constants.HEADLINE6))
		      type=6;
		  return type;
		
		
	}
	/**
     * @fun解析头部前四行，形成Html字符串
     * 特点： 有一行
     * Input 文件路径
     * ouput Html字符串
     * time 2011-09-28
     * auther wangliwei
    */
	public  void ConstructOneLineHead(){
		
		/*****************列明**************************************/
		String strOneCol = "单点故障产品名称";
		String strTwoCol = "产品数量";
		String strThrCol = "单点故障模式";
		String strfourCol = "故障原因";
		String strfiveCol = "故障影响";
		String strsixCol = "严酷度类别";
		String strsevenCol = "频率度";
		String streightCol = "设计、生产、实验、测试、操作等方面的措施";
		String strnightCol = "建议改进措施";
		String strtenCol = "所属系统名称";
		
		strHead = "序号";
			
		List<String> listCurrentHeadRow = new ArrayList<String>();
		listCurrentHeadRow.add(strOneCol);
		listCurrentHeadRow.add(strTwoCol);
		listCurrentHeadRow.add(strThrCol);
		listCurrentHeadRow.add(strfourCol);
		listCurrentHeadRow.add(strfiveCol);
		listCurrentHeadRow.add(strsixCol);
		listCurrentHeadRow.add(strsevenCol);
		listCurrentHeadRow.add(streightCol);
		listCurrentHeadRow.add(strnightCol);
		listCurrentHeadRow.add(strtenCol);
	
		/************************************************************/
		
        List<CellInfo> listRowInfo = new ArrayList<CellInfo>();
        List<List<CellInfo> > listHeadInfo = new ArrayList<List<CellInfo> >();
		for(int isize= 0;isize <listCurrentHeadRow.size()-1;isize++){
			CellInfo cellinfo = new CellInfo();
			cellinfo.strValue = listCurrentHeadRow.get(isize);
			cellinfo.rowSpan = 1;
			listRowInfo.add(cellinfo);
		}
		listHeadInfo.add(listRowInfo);
		String strHtml = ConstructHtml(listHeadInfo,true);
		
		listCurrentHeadRow.add(strHtml);
		listTabInfo.add(listCurrentHeadRow);
	    return;
	
	}
	
	/**
     * @fun解析头部前四行，形成Html字符串
     * 特点： 有两行
     * Input 文件路径
     * ouput Html字符串
     * time 2011-09-28
     * auther wangliwei
    */
	public  void ConstructTwoLineHead(){
		
		/*****************列明**************************************/
		String strOneCol = "产品名称";
		String strTwoCol = "单点故障模式内容";
		String strThrCol = "涉及元组件及数量";
		String strfiveCol = "设计";
		String strsixCol = "工艺";
		String strsevenCol = "过程控制";
		String streightCol = "强制检验点设置";
		String strnightCol = "强制检验点内容";
		String strtenCol = "后续实施计划";
		String strtweCol = "产品所属名称";
		
		String strfourCol = "对应三方面关键特性设置及内容";
		
		strHead = "序号";
			
		List<String> listCurrentHeadRow = new ArrayList<String>();
		listCurrentHeadRow.add(strOneCol);
		listCurrentHeadRow.add(strTwoCol);
		listCurrentHeadRow.add(strThrCol);
		listCurrentHeadRow.add(strfiveCol);
		listCurrentHeadRow.add(strsixCol);
		listCurrentHeadRow.add(strsevenCol);
		listCurrentHeadRow.add(streightCol);
		listCurrentHeadRow.add(strnightCol);
		listCurrentHeadRow.add(strtenCol);
		listCurrentHeadRow.add(strtweCol);
	
		//第一行
		String strFirstHtml1 = "<th rowspan = " + "\"" + "2" + "\"" + ">" + strOneCol + "</th>";
		String strFirstHtml2 = "<th rowspan = " + "\"" + "2" + "\"" + ">" + strTwoCol + "</th>";
		String strFirstHtml3 = "<th rowspan = " + "\"" + "2" + "\"" + ">" + strThrCol + "</th>";
		String strFirstHtml4 = "<th colspan = " + "\"" + "3" + "\"" + ">" + strfourCol + "</th>";
		String strFirstHtml5 = "<th rowspan = " + "\"" + "2" + "\"" + ">" + streightCol + "</th>";
		String strFirstHtml6 = "<th rowspan = " + "\"" + "2" + "\"" + ">" + strnightCol + "</th>";
		String strFirstHtml7 = "<th rowspan = " + "\"" + "2" + "\"" + ">" + strtenCol + "</th>";
		//String strFirstHtml8 = "<th rowspan = " + "\"" + "2" + "\"" + ">" + strtweCol + "</th>";

        String strFirstRowHtml = "<tr>" + strFirstHtml1+strFirstHtml2+strFirstHtml3+ strFirstHtml4+
                                 strFirstHtml5+strFirstHtml6+strFirstHtml7+/*strFirstHtml8 +*/"</tr>";
				
        //第二行
        String strSecondHtml1 = "<th rowspan = " + "\"" + "1" + "\"" + ">" + strfiveCol + "</th>";
        String strSecondHtml2 = "<th rowspan = " + "\"" + "1" + "\"" + ">" + strsixCol + "</th>";
        String strSecondHtml3 = "<th rowspan = " + "\"" + "1" + "\"" + ">" + strsevenCol + "</th>";
        
        String strSecondRowHtml = "<tr>" +strSecondHtml1 + strSecondHtml2 + strSecondHtml3+"</tr>";
        
        String strNewHtml = strFirstRowHtml + strSecondRowHtml ;
        listCurrentHeadRow.add(strNewHtml);
        listTabInfo.add(listCurrentHeadRow);
        strheadHtml = strNewHtml;
	    return;
	
	}
	/**
     * @fun解析头部前四行，形成Html字符串
     * 特点： 有六行
     * Input 文件路径
     * ouput Html字符串
     * time 2011-09-28
     * auther wangliwei
    */
	public String GetFourLineInfo(Table tab){
		
		try{
			//行信息
		//	List<String> listRowInfo = new ArrayList<String>();
//			
//			FileInputStream inputWord = new FileInputStream(strFilePath);// 载入文档
//			
//			POIFSFileSystem pfs = new POIFSFileSystem(inputWord);
//			HWPFDocument hwpf = new HWPFDocument(pfs);
//			Range range = hwpf.getRange();
//			TableIterator itor = new TableIterator(range);
		    // 临时变量
			List<String> listTabHeadName = new ArrayList<String>();
			List<String> listTabHeadValue = new ArrayList<String>();
			List<CellInfo> listHeadNameHtml = new ArrayList<CellInfo>();
			List<CellInfo> listHeadValueHtml = new ArrayList<CellInfo>();
		//	List<CellInfo> listRowHtmlInfo = new ArrayList<CellInfo>();
		//	List<List<CellInfo> > listHtmlInfo = new ArrayList<List<CellInfo> >();
			// 迭代文档中的表格
		//	while (itor.hasNext()){
		//		Table tab = (Table) itor.next();
				//迭代行
				for (int iRow = 0; iRow < 4;iRow++){
					TableRow tabRow = tab.getRow(iRow);
					int nColNUM = tabRow.numCells();
					int colNum  = 0;     //前四行的列数
					 //迭代列
					for (int jCol = 0;jCol < nColNUM; jCol++){
						TableCell tabCell = tabRow.getCell(jCol);
						String strCellValue = "";
						for (int k = 0; k < tabCell.numParagraphs(); k++){
							Paragraph parag = tabCell.getParagraph(k);
							String strTempValue = parag.text();
							strTempValue = strTempValue.replaceAll("", "");
							if(0==k){
								strCellValue = strTempValue;
							}
							else{
								strCellValue = strCellValue +  strTempValue;
							}
						}
		
						int iCol = jCol % 2;  //判断是否为奇数列
						CellInfo cellinfo = new CellInfo();
						if(iCol == 0){
							listTabHeadName.add(strCellValue);
							cellinfo.strValue = strCellValue;
							cellinfo.rowSpan = 1;
							listHeadNameHtml.add(cellinfo);
						}
						else{
							listTabHeadValue.add(strCellValue);
							cellinfo.strValue = strCellValue;
							cellinfo.rowSpan = 1;
							listHeadValueHtml.add(cellinfo);
						}
					}
				}
	    		List<List<CellInfo> > listTemp = new ArrayList<List<CellInfo> > ();
		    	listTemp.add(listHeadNameHtml);
		    	String strTempName = ConstructHtml(listTemp,false);
		    	listTabHeadName.add(strTempName);
		    	listTemp.clear();
		    	listTemp.add(listHeadValueHtml);
		    	String strTempValue = ConstructHtml(listTemp,false);
		    	//listTabHeadValue.add(strTempValue);
		    	//listTabInfo.add(listTabHeadName);
		   	 List<List<String> > listSixTabInfo=new ArrayList<List<String> > ();
	    	 listSixTabInfo.add(listTabHeadValue);
	    	listFileInfo.add(listSixTabInfo);   //后加的    //后加的 
		    	String strTemp = strTempName + strTempValue;
		  //  	inputWord.close();
		    	return strTemp;
			//}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
     * @fun解析头部，形成Html字符串
     * 特点： 有六行
     * Input 文件路径
     * time 2011-09-28
     * auther wangliwei
    */
	public void ConstructSixLineHead(Table tab){
		//获取前4列信息
		String strFourLineHtml = GetFourLineInfo(tab);
		//获取5,6列信息
		/*****************列**************************************/
		String str11Col = "元器件/零部件名称或图号";
		String str12Col = "功能与要求";
		String str13Col = "潜在功能故障模式";
		String str14Col = "潜在故障原因";
		String str15Col = "潜在故障影响";
		String str16Col = "关键件/重要件/一般件";
		String str17Col = "当前控制情况";
		String str18Col = "建议改进措施";
		String str19Col = "改进结果";
		String str110Col = "责任人及完成日期";
		
		String str20Col = "设计、实验、测试等方面的措施";
		String str21Col = "频率度";
		String str22Col = "严酷度";
		String str23Col = "检测度";
		String str24Col = "风险指数";
		String str25Col = "采取的改进措施";
		String str26Col = "频率度";
		String str27Col = "严酷度";
		String str28Col = "检测度";
		String str29Col = "风险指数";
		
		/*****************列**************************************/
		strHead = "编号";
			
		List<String> listCurrentHeadRow = new ArrayList<String>();
		listCurrentHeadRow.add(str11Col);
		listCurrentHeadRow.add(str12Col);
		listCurrentHeadRow.add(str13Col);
		listCurrentHeadRow.add(str14Col);
		listCurrentHeadRow.add(str15Col);
		listCurrentHeadRow.add(str16Col);
		listCurrentHeadRow.add(str20Col);
		listCurrentHeadRow.add(str21Col);
		listCurrentHeadRow.add(str22Col);
		listCurrentHeadRow.add(str23Col);
		listCurrentHeadRow.add(str24Col);
		
		listCurrentHeadRow.add(str18Col);
		
		listCurrentHeadRow.add(str25Col);
		listCurrentHeadRow.add(str26Col);
		listCurrentHeadRow.add(str27Col);
		listCurrentHeadRow.add(str28Col);
		listCurrentHeadRow.add(str29Col);
		listCurrentHeadRow.add(str110Col);
		
		//第一行
		String strFirstHtml1 = "<th rowspan = " + "\"" + "2" + "\"" + ">" + str11Col + "</th>";
		String strFirstHtml2 = "<th rowspan = " + "\"" + "2" + "\"" + ">" + str12Col + "</th>";
		String strFirstHtml3 = "<th rowspan = " + "\"" + "2" + "\"" + ">" + str13Col + "</th>";
		String strFirstHtml4 = "<th rowspan = " + "\"" + "2" + "\"" + ">" + str14Col + "</th>";
		String strFirstHtml5 = "<th rowspan = " + "\"" + "2" + "\"" + ">" + str15Col + "</th>";
		String strFirstHtml6 = "<th rowspan = " + "\"" + "2" + "\"" + ">" + str16Col + "</th>";
		String strFirstHtml7 = "<th colspan = " + "\"" + "5" + "\"" + ">" + str17Col + "</th>";
		
		String strFirstHtml8 = "<th rowspan = " + "\"" + "2" + "\"" + ">" + str18Col + "</th>";
		String strFirstHtml9 = "<th colspan = " + "\"" + "5" + "\"" + ">" + str19Col + "</th>";
		String strFirstHtml10 = "<th rowspan = " + "\"" + "2" + "\"" + ">" + str110Col + "</th>";


        String strFirstRowHtml = "<tr>" + strFirstHtml1+strFirstHtml2+strFirstHtml3+ 
               strFirstHtml4+strFirstHtml5+strFirstHtml6+strFirstHtml7+strFirstHtml8 +strFirstHtml9+ strFirstHtml10+"</tr>";
				
        //第二行
        String strSecondHtml1 = "<th rowspan = " + "\"" + "1" + "\"" + ">" + str20Col + "</th>";
        String strSecondHtml2 = "<th rowspan = " + "\"" + "1" + "\"" + ">" + str21Col + "</th>";
        String strSecondHtml3 = "<th rowspan = " + "\"" + "1" + "\"" + ">" + str22Col + "</th>";
        String strSecondHtml4 = "<th rowspan = " + "\"" + "1" + "\"" + ">" + str23Col + "</th>";
        String strSecondHtml5 = "<th rowspan = " + "\"" + "1" + "\"" + ">" + str24Col + "</th>";
        String strSecondHtml6 = "<th rowspan = " + "\"" + "1" + "\"" + ">" + str25Col + "</th>";
        String strSecondHtml7 = "<th rowspan = " + "\"" + "1" + "\"" + ">" + str26Col + "</th>";
        String strSecondHtml8 = "<th rowspan = " + "\"" + "1" + "\"" + ">" + str27Col + "</th>";
        String strSecondHtml9 = "<th rowspan = " + "\"" + "1" + "\"" + ">" + str28Col + "</th>";
        String strSecondHtm20 = "<th rowspan = " + "\"" + "1" + "\"" + ">" + str29Col + "</th>";
        
        String strSecondRowHtml = "<tr>" +strSecondHtml1 + strSecondHtml2 + strSecondHtml3+strSecondHtml4+strSecondHtml5+
               strSecondHtml6+ strSecondHtml7+ strSecondHtml8+ strSecondHtml9+ strSecondHtm20+"</tr>";
        
        String strNewHtml =  strFirstRowHtml + strSecondRowHtml ;
        
        listCurrentHeadRow.add(strNewHtml);
        //listTabInfo.add(listCurrentHeadRow);
        
        strheadHtml = strFourLineHtml + "</table><table border=1 style=\"font-size:13px;\">" + strNewHtml;
	    return;
	}
	
	/**
    * @fun构造HtML语句
    * input 要组合字符串，是否为文件头部
    * output Html字符串
    * time 2011-09-28
    * auther wangliwei 
    */
	public String ConstructHtml( List<List<CellInfo> > listInfo,boolean bHead){
		/*******************处理一个知识点信息*****************************/
		//String  strTableHead = "<table>";
		//String  strTableTail = "</table>";
	    String  strTRhead = "<tr>";
	    String  strTRtail = "</tr>";
		String  strTHheadA = "<th rowspan = ";
		String  strTHheadB = ">";
		String  strTHtail = "</th>"; 
    	String  strHtml = "";
    	String  strNewHtml = "";
		for (List<CellInfo> list : listInfo) {
			String strRowHtml = "";
			String strNewRowHtml = "";
			for(CellInfo cellInfo:list){
				String scellValue = cellInfo.strValue;
				if(!scellValue.equals("")){
					if(!scellValue .equals("合并单元格")){
					
					int rowspan = cellInfo.rowSpan;
					String strTemp = Integer.toString(rowspan);
					strTemp = strTHheadA +"\""+ strTemp+"\""+strTHheadB; 
					strRowHtml =  strTemp + scellValue + strTHtail;
					strNewRowHtml = strNewRowHtml+ strRowHtml;
				}
					}
				else
				{
                   
					int rowspan = cellInfo.rowSpan;
					String strTemp = Integer.toString(rowspan);
					strTemp = strTHheadA +"\""+ strTemp+"\""+strTHheadB; 
					strRowHtml =  strTemp + "&nbsp" + strTHtail;
					strNewRowHtml = strNewRowHtml+ strRowHtml;
	
				}

			}
			strHtml = strTRhead + strNewRowHtml + strTRtail;
			strNewHtml = strNewHtml +strHtml;
		}
		if(true == bHead){     //记录头部html
			strheadHtml = strNewHtml;
		}
		//strHtml = strTableHead + strNewHtml + strTableTail;
		
		return strNewHtml;
	}
	/**
     * @name ParserWord
     * @fun  解析word中表，循环解析，不解析表头。
     * INput 文件路径，开始行
     * time  2011-09-28
     * auther wangliwei
    */
	public String ParserWord(String strWordDir,int startRow){
		
		try {
		
			FileInputStream inputWord = new FileInputStream(strWordDir);// 载入文档
			POIFSFileSystem pfs = new POIFSFileSystem(inputWord);
			HWPFDocument hwpf = new HWPFDocument(pfs);
			Range range = hwpf.getRange();
			TableIterator itor = new TableIterator(range);
			
			//WordExtractor wordExtractor = new WordExtractor(inputWord);
			// String header = wordExtractor.geth
			
			// 迭代文档中的表格
			while (itor.hasNext()){
			    //产品所属类型
				String strSYSname = "";
				//在一个表中循环
				boolean bSameknowledge = false;     //是否为同一条知识.不同行可能为一条知识
				//最大列数
				int maxColNum = 0;
				//记录当前行信息
				List<String> listCurrentRowValue = new ArrayList<String>();
				//记录当前产品信息
				List<List<CellInfo> > listHtmlInfo = new ArrayList<List<CellInfo> >();
				
				//记录当前表信息
				List<List<String> > listCurrentTabInfo = new ArrayList<List<String> >();
				
				Table tab = (Table) itor.next();
				if(startRow==6)
					ConstructSixLineHead(tab);
				int totalRowNum = tab.numRows();
				for (int iRow = startRow; iRow < totalRowNum;iRow++){
					//变量
					boolean isHead = false;      //是否为头部
					boolean isSYSname = false;      //是否为知识所属的系统
					List<CellInfo> listRowHtmlInfo= new ArrayList<CellInfo>();
					
					/***************初始化数组***************************/
					TableRow tabRow = tab.getRow(iRow);
					int nColNum = tabRow.numCells();
					if(startRow ==iRow){
						int nColNum2 = 0;
						if(startRow <totalRowNum -1){
							TableRow tabRow2 = tab.getRow(iRow +1);
							nColNum2  = tabRow2.numCells();
						}
						if(nColNum >nColNum2){
							maxColNum = nColNum;
						}
						else{
							maxColNum = nColNum2;
						}
					    for (int j = 0;j < maxColNum+1; j++)
					    {
					    	listCurrentRowValue.add("");
					    }
					}
					/******************************************************/
					
					 //迭代列
					for (int jCol = 0;jCol < nColNum; jCol++){
						TableCell tabCell = tabRow.getCell(jCol);
						// 取得单元格的内容
						String strCellValue = "";      //单元格内容
						for (int k = 0; k < tabCell.numParagraphs(); k++){
							Paragraph parag = tabCell.getParagraph(k);
							String strTempValue = parag.text();
							strTempValue = strTempValue.replaceAll("", "");
							//System.out.println("strTempValue.length()="+strTempValue.length());
							//System.out.println("strTempValue.lastIndexOf(\\r)="+strTempValue.lastIndexOf("\r"));
							if(strTempValue.lastIndexOf("\r")!=-1&&strTempValue.lastIndexOf("\r")==strTempValue.length()-1)
								strTempValue=strTempValue.substring(0,strTempValue.length()-1);
							//strTempValue = strTempValue.replaceAll("\\r", "");
						
							if(0 == k){
								strCellValue = strTempValue;
							}
							else{
								strCellValue = strCellValue + strTempValue;
							}	
						}
						//过滤头部和产品所属的系统名
						if(true == isHead ||true == isSYSname ){
							continue;
						}
					    if(0 == jCol){
					    	if(strCellValue.trim().equals(strHead)){     //判断是否为头部信息
								isHead = true;
								iRow = iRow +(startRow -1);//跳过所有的头部行？？？？？
								continue;
							}
					    	if(strCellValue == null||strCellValue.trim().equals("")){ 
					    		continue;
					    	}
					    	else
					    	{
								if(!CheckDigit(strCellValue)){
	                        		isSYSname = true;
	                        		strSYSname = strCellValue;
	                        		continue;
	                        	}
					    	}
					    }
						else{     //通过数字判断是否为一条知识，还是知识所属的系统名
							if(1== jCol){    //从第二列开始读取有用信息
								if(strCellValue == null||strCellValue.trim().equals("")){     //判断是否同一条知识
									bSameknowledge = true;
							     }
						    	else{
						    		bSameknowledge = false;
						    		//处理上一条知识
						    		if(!listCurrentRowValue.get(0).trim().equals("")){
						    			listCurrentRowValue.set(nColNum-1,strSYSname);
			                        	String strHtml = "";
			                        	strHtml = ConstructHtml(listHtmlInfo,false);
			                        	String strNewHtml = "<table  border=1 style=\"font-size:13px;\">" + strheadHtml + strHtml + "</table>";
			                        	listCurrentRowValue.set(nColNum,strNewHtml);
			                        	listCurrentTabInfo.add(listCurrentRowValue);
			                        	
			                        	listCurrentRowValue = new ArrayList<String>();
			                        	for (int j = 0;j < maxColNum+1; j++)
			    					    {
			    					    	listCurrentRowValue.add("");
			    					    }
			                        	listHtmlInfo.clear();
						    		}
						    	}
							}
	                        if(bSameknowledge ==false){
	                        		CellInfo tempCelInfo = new CellInfo();
	                        		tempCelInfo.strValue = strCellValue;
						    		tempCelInfo.rowSpan = 1;
						    		listRowHtmlInfo.add(tempCelInfo);     //存储Cell信息
	                        		listCurrentRowValue.set(jCol-1, strCellValue);
					    	}
	                        else{
	                        	CellInfo tempCellInfo = new CellInfo();
	                        	String strTempValue = listCurrentRowValue.get(jCol-1);
	                        	if(strCellValue !=null&& !strCellValue.trim().equals("")){
						    		 /*************html************************/
						    		 tempCellInfo.strValue = strCellValue;
						    		 tempCellInfo.rowSpan = 1;
						    		 listRowHtmlInfo.add(tempCellInfo);     //存储Cell信息
						    		 /**************************************/
						    		 strTempValue = strTempValue + ";" +strCellValue;
						    	 }
						    	 else{
						    		 tempCellInfo.strValue = "合并单元格";
						    		 tempCellInfo.rowSpan = 1;
						    		 int nsize = listHtmlInfo.size();
						    		 for(int size =  listHtmlInfo.size()-1;size>=0;size--){
						    			 List<CellInfo> existRowInfo  =  listHtmlInfo.get(size);
						    			 CellInfo exitCellInfo =  existRowInfo.get(jCol-1);
						    			 if(!exitCellInfo.strValue.equals("合并单元格")){
						    				 exitCellInfo.rowSpan++;
						    				 break;
						    			 }
						    		 }
						    		 listRowHtmlInfo.add(tempCellInfo);       //存储Cell信息
						    	 }
	                        	listCurrentRowValue.set(jCol-1, strTempValue);
	                         }
						}
					}
					/******************html***每行多一个属性******************/
					if(true == isSYSname||true == isHead){
						continue;
					}
					//else{
					//	CellInfo  lastCell = new CellInfo();
        			//    lastCell.rowSpan = 1;
        			//    lastCell.strValue = strSYSname;
        			//    listRowHtmlInfo.add(lastCell);
					//	//保存一行信息
        			    listHtmlInfo.add(listRowHtmlInfo);
					//}
        			/****************************************************/
				}
				//****************保存最后一条记录**************
				listCurrentRowValue.set(maxColNum-1,strSYSname);
            	String strHtml = "";
            	strHtml = ConstructHtml(listHtmlInfo,false);
            	String strNewHtml = "<table  border=1 style=\"font-size:13px;\">" + strheadHtml + strHtml + "</table>";
            	listCurrentRowValue.set(maxColNum,strNewHtml);
            	listCurrentTabInfo.add(listCurrentRowValue);
				//*******************************************
				listFileInfo.add(listCurrentTabInfo);
			}
			inputWord.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
     * @name CheckDigit
     * @fun  判断是否为数字
     * time 2011-09-28
     * auther wangliwei
    */
    public boolean CheckDigit(String strCurrent)
	{
    	for(int i = 0;i< strCurrent.length();i++){
    	    if(!Character.isDigit(strCurrent.charAt(i))){
    	    	return false;
    	    }
    	    else{
    	    	return true;
    	    }
    	}
    	return false;
	}
	
}//end class
