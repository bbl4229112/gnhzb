package edu.zju.cims201.GOF.util;


import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;


import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.codec.TIFFDirectory;
import com.sun.media.jai.codecimpl.TIFFImageDecoder;

public class Tifdownload2 {

	Document document;

	PdfContentByte cb;

	/*
	 * 实际上是专利公开号，为方便与jsp集成所以没有改
	 */
	
	public void parserAppcode(String getString, Vector app_code_V) {
		Pattern p1 = Pattern.compile(">           (.*?)<");
		Matcher m1 = p1.matcher(getString);
		String app_codetmp = "", bodytmp = "";
		do {
			boolean result = m1.find();
			if (result) {
				app_code_V.addElement(m1.group(1));
				app_codetmp = m1.group(1);
				//				
			} else {
				// app_code_V.addElement("11111111");
				app_codetmp = "1111111111111111111111111111111111111111";
			}

			int index = getString.indexOf(app_codetmp);
			bodytmp = "";
			if ((index > 0)
					&& (index + app_codetmp.length() <= getString.length())) {
				bodytmp = getString.substring(index + app_codetmp.length() + 1);
			}

		} while (bodytmp != "");

	}

	public void parserPatentName(String getString, Vector patent_name_V) {
		Pattern p1 = Pattern.compile(">(.*?)</TD>\n<");
		Matcher m1 = p1.matcher(getString);
		String patent_nametmp = "", bodytmp = "";
		do {
			boolean result = m1.find();
			if (result) {
				patent_name_V.addElement(m1.group(1));
				patent_nametmp = m1.group(1);
			//	System.out.println("patent_nametmp="+patent_nametmp);
				//				
			} else {
				// app_code_V.addElement("11111111");
				patent_nametmp = "1111111111111111111111111111111111111111";
			}

			int index = getString.indexOf(patent_nametmp);
			bodytmp = "";
			if ((index > 0)
					&& (index + patent_nametmp.length() <= getString.length())) {
				bodytmp = getString.substring(index + patent_nametmp.length()
						+ 1);
			}

		} while (bodytmp != "");

	}
	
	
	

	public String parserTempName(String getString) {
		Pattern p1 = Pattern.compile("(/IPDL/PA1/exe/result/.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String TempName = "";
		boolean result = m1.find();
		if (result) {
			TempName = m1.group(1);
		} else {
			TempName = "";
		}
		return TempName;

	}
	

	public String parserResultId(String getString) {
		Pattern p1 = Pattern.compile("RESULT_ID    = \"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String ResultId = "";
		boolean result = m1.find();
		if (result) {
			ResultId = m1.group(1);
		} else {
			ResultId = "";
		}
		return ResultId;

	}
	
	public String parserReserve2(String getString) {
		Pattern p1 = Pattern.compile("RESERVE2     = \"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String ResultId = "";
		boolean result = m1.find();
		if (result) {
			ResultId = m1.group(1);
		} else {
			ResultId = "";
		}
		return ResultId;

	}
	
	
	public String parserHitCount(String getString) {
		Pattern p1 = Pattern.compile("Search Results : (.*?)<");
		Matcher m1 = p1.matcher(getString);
		String HitCount = "";
		boolean result = m1.find();
		if (result) {
			HitCount = m1.group(1);
		} else {
			HitCount = "";
		}
		return HitCount;

	}
	
	public String parserListMain(String getString) {
		Pattern p1 = Pattern.compile("(/PA1/result/list/main/.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String ListMain = "";
		boolean result = m1.find();
		if (result) {
			ListMain = m1.group(1);
		} else {
			ListMain = "";
		}
		return ListMain;

	}
	
	
	
	public String parserListHead(String getString) {
		Pattern p1 = Pattern.compile("(/PA1/result/list/head/.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String ListHead = "";
		boolean result = m1.find();
		if (result) {
			ListHead = m1.group(1);
		} else {
			ListHead = "";
		}
		return ListHead;

	}
	

	
	
	public void parserListDetail(String getString, Vector listDetailtmp_V) {
		Pattern p1 = Pattern.compile("(JavaScript:LinkToDetail(.*?))\"");
		Matcher m1 = p1.matcher(getString);
		String listDetailtmp = "", bodytmp = "";
		do {
			boolean result = m1.find();
			if (result) {
				listDetailtmp_V.addElement(m1.group(1));
				listDetailtmp = m1.group(1);
			//	System.out.println("patent_nametmp="+patent_nametmp);
				//				
			} else {
				// app_code_V.addElement("11111111");
				listDetailtmp = "1111111111111111111111111111111111111111";
			}

			int index = getString.indexOf(listDetailtmp);
			bodytmp = "";
			if ((index > 0)
					&& (index + listDetailtmp.length() <= getString.length())) {
				bodytmp = getString.substring(index + listDetailtmp.length()
						+ 1);
			}

		} while (bodytmp != "");

	}
	

	
	public String parserDetailMain(String getString) {
		Pattern p1 = Pattern.compile("(/PA1/result/detail/main/.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String DetailMain = "";
		boolean result = m1.find();
		if (result) {
			DetailMain = m1.group(1);
		} else {
			DetailMain = "";
		}
		return DetailMain;

	}
	
	
	
	public String parserDetailHead(String getString) {
		Pattern p1 = Pattern.compile("(/PA1/result/detail/head/.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String DetailHead = "";
		boolean result = m1.find();
		if (result) {
			DetailHead = m1.group(1);
		} else {
			DetailHead = "";
		}
		return DetailHead;

	}
	
	
	
	
	
	
	
	public String parserListMaxCount(String getString) {
		Pattern p1 = Pattern.compile("MaxCount\"   VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String MaxCount = "";
		boolean result = m1.find();
		if (result) {
			MaxCount = m1.group(1);
		} else {
			MaxCount = "";
		}
		return MaxCount;

	}
	
	
	public String parserListPageCount(String getString) {
		Pattern p1 = Pattern.compile("PageCount\"  VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String PageCount = "";
		boolean result = m1.find();
		if (result) {
			PageCount = m1.group(1);
		} else {
			PageCount = "";
		}
		return PageCount;

	}
	
	public String parserListSearchType(String getString) {
		Pattern p1 = Pattern.compile("SearchType\" VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String SearchType = "";
		boolean result = m1.find();
		if (result) {
			SearchType = m1.group(1);
		} else {
			SearchType = "";
		}
		return SearchType;

	}
	
	
	public String parserListTempName(String getString) {
		Pattern p1 = Pattern.compile("TempName\"   VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String TempName = "";
		boolean result = m1.find();
		if (result) {
			TempName = m1.group(1);
		} else {
			TempName = "";
		}
		return TempName;

	}
	
	
	
	public String parserListMaxPage(String getString) {
		Pattern p1 = Pattern.compile("MaxPage\"    VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String MaxPage = "";
		boolean result = m1.find();
		if (result) {
			MaxPage = m1.group(1);
		} else {
			MaxPage = "";
		}
		return MaxPage;

	}
	
	
	public String parserListDispPage(String getString) {
		Pattern p1 = Pattern.compile("DispPage\"   VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String DispPage = "";
		boolean result = m1.find();
		if (result) {
			DispPage = m1.group(1);
		} else {
			DispPage = "";
		}
		return DispPage;

	}
	
	
	public String parserListHitCount(String getString) {
		Pattern p1 = Pattern.compile("HitCount\"   VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String HitCount = "";
		boolean result = m1.find();
		if (result) {
			HitCount = m1.group(1);
		} else {
			HitCount = "";
		}
		return HitCount;

	}
	
	
	public String parserListResultId(String getString) {
		Pattern p1 = Pattern.compile("ResultId\"   VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String ResultId = "";
		boolean result = m1.find();
		if (result) {
			ResultId = m1.group(1);
		} else {
			ResultId = "";
		}
		return ResultId;

	}
	
	
	public String parserListCookieId(String getString) {
		Pattern p1 = Pattern.compile("CookieId\"   VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String CookieId = "";
		boolean result = m1.find();
		if (result) {
			CookieId = m1.group(1);
		} else {
			CookieId = "";
		}
		return CookieId;

	}
	
	
	public String parserListDetailPage(String getString) {
		Pattern p1 = Pattern.compile("DetailPage\" VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String DetailPage = "";
		boolean result = m1.find();
		if (result) {
			DetailPage = m1.group(1);
		} else {
			DetailPage = "";
		}
		return DetailPage;

	}
	
	
	public String parserListLanguage(String getString) {
		Pattern p1 = Pattern.compile("Language\"   VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String Language = "";
		boolean result = m1.find();
		if (result) {
			Language = m1.group(1);
		} else {
			Language = "";
		}
		return Language;

	}
	
	
	public String parserListReserve1(String getString) {
		Pattern p1 = Pattern.compile("Reserve1\"   VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String Reserve1 = "";
		boolean result = m1.find();
		if (result) {
			Reserve1 = m1.group(1);
		} else {
			Reserve1 = "";
		}
		return Reserve1;

	}
	
	
	public String parserListReserve2(String getString) {
		Pattern p1 = Pattern.compile("Reserve2\"   VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String Reserve2 = "";
		boolean result = m1.find();
		if (result) {
			Reserve2 = m1.group(1);
		} else {
			Reserve2 = "";
		}
		return Reserve2;

	}
	
	
	public String parserListReserve3(String getString) {
		Pattern p1 = Pattern.compile("Reserve3\"   VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String Reserve3 = "";
		boolean result = m1.find();
		if (result) {
			Reserve3 = m1.group(1);
		} else {
			Reserve3 = "";
		}
		return Reserve3;

	}
	
	public String parserListFreeWord1(String getString) {
		Pattern p1 = Pattern.compile("FreeWord1\"  VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String FreeWord1 = "";
		boolean result = m1.find();
		if (result) {
			FreeWord1 = m1.group(1);
		} else {
			FreeWord1 = "";
		}
		return FreeWord1;

	}
	
	public String parserListFreeWord2(String getString) {
		Pattern p1 = Pattern.compile("FreeWord2\"  VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String FreeWord2 = "";
		boolean result = m1.find();
		if (result) {
			FreeWord2 = m1.group(1);
		} else {
			FreeWord2 = "";
		}
		return FreeWord2;

	}
	
	public String parserListFreeWord3(String getString) {
		Pattern p1 = Pattern.compile("FreeWord3\"  VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String FreeWord3 = "";
		boolean result = m1.find();
		if (result) {
			FreeWord3 = m1.group(1);
		} else {
			FreeWord3 = "";
		}
		return FreeWord3;

	}
	
	public String parserListStartDate(String getString) {
		Pattern p1 = Pattern.compile("StartDate\"  VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String StartDate = "";
		boolean result = m1.find();
		if (result) {
			StartDate = m1.group(1);
		} else {
			StartDate = "";
		}
		return StartDate;

	}
	
	public String parserListEndDate(String getString) {
		Pattern p1 = Pattern.compile("EndDate\"    VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String EndDate = "";
		boolean result = m1.find();
		if (result) {
			EndDate = m1.group(1);
		} else {
			EndDate = "";
		}
		return EndDate;

	}
	
	public String parserListIPC(String getString) {
		Pattern p1 = Pattern.compile("IPC\"        VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String IPC = "";
		boolean result = m1.find();
		if (result) {
			IPC = m1.group(1);
		} else {
			IPC = "";
		}
		return IPC;

	}
	
	public String parserListNumberType(String getString) {
		Pattern p1 = Pattern.compile("NumberType\" VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String NumberType = "";
		boolean result = m1.find();
		if (result) {
			NumberType = m1.group(1);
		} else {
			NumberType = "";
		}
		return NumberType;

	}
	
	public String parserListNumber(String getString) {
		Pattern p1 = Pattern.compile("Number\"     VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String Number = "";
		boolean result = m1.find();
		if (result) {
			Number = m1.group(1);
		} else {
			Number = "";
		}
		return Number;

	}
	
	public String parserListFreeWordType1(String getString) {
		Pattern p1 = Pattern.compile("FreeWordType1\" VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String FreeWordType1 = "";
		boolean result = m1.find();
		if (result) {
			FreeWordType1 = m1.group(1);
		} else {
			FreeWordType1 = "";
		}
		return FreeWordType1;

	}
	
	public String parserListFreeWordType2(String getString) {
		Pattern p1 = Pattern.compile("FreeWordType2\" VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String FreeWordType2 = "";
		boolean result = m1.find();
		if (result) {
			FreeWordType2 = m1.group(1);
		} else {
			FreeWordType2 = "";
		}
		return FreeWordType2;

	}
	
	public String parserListFreeWordType3(String getString) {
		Pattern p1 = Pattern.compile("FreeWordType3\" VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String FreeWordType3 = "";
		boolean result = m1.find();
		if (result) {
			FreeWordType3 = m1.group(1);
		} else {
			FreeWordType3 = "";
		}
		return FreeWordType3;

	}
	
	public String parserListDerive(String getString) {
		Pattern p1 = Pattern.compile("Derive\" VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String Derive = "";
		boolean result = m1.find();
		if (result) {
			Derive = m1.group(1);
		} else {
			Derive = "";
		}
		return Derive;

	}
	
	public String parserListEnding(String getString) {
		Pattern p1 = Pattern.compile("Ending\" VALUE=\"(.*?)\"");
		Matcher m1 = p1.matcher(getString);
		String Ending = "";
		boolean result = m1.find();
		if (result) {
			Ending = m1.group(1);
		} else {
			Ending = "";
		}
		return Ending;

	}
	
	
	public String parserPub_date(String getString) {
		Pattern p1 = Pattern.compile("application : </TD><TD VALIGN=\"top\" WIDTH=\"45%\"><B>(.*?)</B");
		Matcher m1 = p1.matcher(getString);
		String Pub_date = "";
		boolean result = m1.find();
		if (result) {
			Pub_date = m1.group(1);
		} else {
			Pub_date = "";
		}
		return Pub_date.replaceAll(" ", "");

	}

	public String parserApp_code(String getString) {
		Pattern p1 = Pattern.compile("Application number : </TD><TD WIDTH=\"25%\" VALIGN=\"top\"><B>(.*?)</B");
		Matcher m1 = p1.matcher(getString);
		String App_code = "";
		boolean result = m1.find();
		if (result) {
			App_code = m1.group(1);
		} else {
			App_code = "";
		}
		return App_code.replaceAll(" ", "");

	}
	
	public String parserPub_code(String getString) {
		Pattern p1 = Pattern.compile("Publication number : </TD><TD VALIGN=\"top\" WIDTH=\"45%\"><B>(.*?)</B>");
		Matcher m1 = p1.matcher(getString);
		String Pub_code = "";
		boolean result = m1.find();
		if (result) {
			Pub_code = m1.group(1);
		} else {
			Pub_code = "";
		}
		return Pub_code.replaceAll(" ", "");

	}
	
	public String parserPatent_name(String getString) {
		Pattern p1 = Pattern.compile("\\(54\\)<B>(.*?)<B");
		Matcher m1 = p1.matcher(getString);
		String Patent_name = "";
		boolean result = m1.find();
		if (result) {
			Patent_name = m1.group(1);
		} else {
			Patent_name = "";
		}
		return Patent_name.trim();

	}

	
	public String parserApplicant(String getString) {
		Pattern p1 = Pattern.compile("Applicant : </TD><TD WIDTH=\"45%\" VALIGN=\"top\"><B>(.*?)</B");
		Matcher m1 = p1.matcher(getString);
		String Applicant = "";
		boolean result = m1.find();
		if (result) {
			Applicant = m1.group(1);
		} else {
			Applicant = "";
		}
		return Applicant.trim();

	}

	
	public String parserInventor(String getString) {
		Pattern p1 = Pattern.compile("Inventor : </TD><TD WIDTH=\"45%\" VALIGN=\"top\"><B>(.*?)</B");
		Matcher m1 = p1.matcher(getString);
		String Inventor = "";
		boolean result = m1.find();
		if (result) {
			Inventor = m1.group(1);
		} else {
			Inventor = "";
		}
		return Inventor.trim();

	}

	
	public String parserApp_date(String getString) {
		Pattern p1 = Pattern.compile("Date of filing : </TD><TD WIDTH=\"25%\" VALIGN=\"top\"><B>(.*?)</B");
		Matcher m1 = p1.matcher(getString);
		String App_date = "";
		boolean result = m1.find();
		if (result) {
			App_date = m1.group(1);
		} else {
			App_date = "";
		}
		return App_date.replaceAll(" ", "");

	}

	
	public String parserAbstract(String getString) {
		Pattern p1 = Pattern.compile("Abstract:<BR>\n(.*)<B");
		Matcher m1 = p1.matcher(getString);
		String Abstract = "";
		boolean result = m1.find();
		if (result) {
			Abstract = m1.group(1);
		} else {
			Abstract = "";
		}
		return Abstract.trim();

	}
	
	public String parserImgUrl(String getString) {
		Pattern p1 = Pattern.compile("SRC=\"(/PA1/result/detail.*?)\" ");
		Matcher m1 = p1.matcher(getString);
		String ImgUrl = "";
		boolean result = m1.find();
		if (result) {
			ImgUrl = m1.group(1);
		} else {
			ImgUrl = "";
		}
		return ImgUrl;

	}
	
	//<B>   .*
	
	public String parserCat_code(String getString) {
		Pattern p1 = Pattern.compile("<B>   (.*)");
		Matcher m1 = p1.matcher(getString);
		
		String Cat_code="";
		if (m1.find()){ 
			Cat_code = m1.group(1);
					
		}
		
		
		String Cat_codetmp = "", bodytmp = "";
		do {
			boolean result = m1.find();
			if (result) {
				Cat_code=Cat_code+";"+m1.group(1);
				Cat_codetmp = m1.group(1);
			//	System.out.println("patent_nametmp="+patent_nametmp);
				//				
			} else {
				// app_code_V.addElement("11111111");
				Cat_codetmp = "1111111111111111111111111111111111111111";
			}

			int index = getString.indexOf(Cat_codetmp);
			bodytmp = "";
			if ((index > 0)
					&& (index + Cat_codetmp.length() <= getString.length())) {
				bodytmp = getString.substring(index + Cat_codetmp.length()
						+ 1);
			}

		} while (bodytmp != "");
		
		return Cat_code.trim();
		

	}
	
	
	public String parserIPC1(String getString) {
		Pattern p1 = Pattern.compile("<B>   (.*)");
		Matcher m1 = p1.matcher(getString);
		
		String IPC1 = "";
		boolean result = m1.find();
		if (result) {
			IPC1 = m1.group(1);
		} else {
			IPC1 = "";
		}
		return IPC1.trim();
		

	}
	
	
	
		public String parserCat_code2(String getString) {
		Pattern p1 = Pattern.compile("<B>(.*?\\))");
		Matcher m1 = p1.matcher(getString);
		
		String Cat_code="";
		if (m1.find()){ 
			Cat_code = m1.group(1);
					
		}
		
		
		String Cat_codetmp = "", bodytmp = "";
		do {
			boolean result = m1.find();
			if (result) {
				Cat_code=Cat_code+";"+m1.group(1);
				Cat_codetmp = m1.group(1);
			//	System.out.println("patent_nametmp="+patent_nametmp);
				//				
			} else {
				// app_code_V.addElement("11111111");
				Cat_codetmp = "1111111111111111111111111111111111111111";
			}

			int index = getString.indexOf(Cat_codetmp);
			bodytmp = "";
			if ((index > 0)
					&& (index + Cat_codetmp.length() <= getString.length())) {
				bodytmp = getString.substring(index + Cat_codetmp.length()
						+ 1);
			}

		} while (bodytmp != "");
		
		return Cat_code.trim();
		

	}
	
	
	public String parserIPC12(String getString) {
		Pattern p1 = Pattern.compile("<B>(.*?\\))");
		Matcher m1 = p1.matcher(getString);
		
		String IPC1 = "";
		boolean result = m1.find();
		if (result) {
			IPC1 = m1.group(1);
		} else {
			IPC1 = "";
		}
		return IPC1.trim();
		

	}
	
	
	
	

	
	

	public String parserTotalRecord(String getString) {
		Pattern p1 = Pattern.compile("共有(\\d{1,9})条记录");
		Matcher m1 = p1.matcher(getString);
		String totalRecord = "";
		boolean result = m1.find();
		if (result) {
			totalRecord = m1.group(1);
		} else {
			totalRecord = "0";
		}
		return totalRecord;

	}

	public String parserCurrentPage(String getString) {
		Pattern p1 = Pattern.compile("页次.*(\\d{1,9})/(\\d{1,9})");
		Matcher m1 = p1.matcher(getString);
		boolean result = m1.find();
		String currentPage = "";
		if (result) {
			currentPage = m1.group(0);
			Pattern p2 = Pattern.compile("(\\d{1,9})/");
			Matcher m2 = p2.matcher(currentPage);
			boolean result2 = m2.find();
			if (result2) {
				currentPage = m2.group(1);
			} else {
				currentPage = "0";
			}
			return currentPage;

		} else {
			return "0";
		}
	}

	public String parserTotalPage(String getString) {
		Pattern p1 = Pattern.compile("页次.*(\\d{1,9})/(\\d{1,9})");
		Matcher m1 = p1.matcher(getString);
		boolean result = m1.find();
		if (result) {
			return m1.group(2);

		} else {
			return "0";
		}
	}
	
	
	
	
	
	/*
	 * 具体下载部分
	 */
	
	
	public String parserTjswhen(String getString) {
		Pattern p1 = Pattern.compile("tjswhen\" SRC=\"(.*)\">");
		Matcher m1 = p1.matcher(getString);
		boolean result = m1.find();
		if (result) {
			return m1.group(1);

		} else {
			return "0";
		}
	}
	
	
	public String parserTjfix(String getString) {
		Pattern p1 = Pattern.compile("tjfix\" SRC=\"(.*)\">");
		Matcher m1 = p1.matcher(getString);
		boolean result = m1.find();
		if (result) {
			return m1.group(1);

		} else {
			return "0";
		}
	}
	
		

	public String parserTjitemidx(String getString) {
		Pattern p1 = Pattern.compile("tjitemidxpaj\" SRC=\"(.*)\">");
		Matcher m1 = p1.matcher(getString);
		boolean result = m1.find();
		if (result) {
			return m1.group(1);

		} else {
			return "0";
		}
	}
	
	
	public String parserTjitemcnt(String getString) {
		Pattern p1 = Pattern.compile("tjitemcntpaj\" SRC=\"(.*)\">");
		Matcher m1 = p1.matcher(getString);
		boolean result = m1.find();
		if (result) {
			return m1.group(1);

		} else {
			return "0";
		}
	}
	
	public String parserH_flg(String getString) {
		Pattern p1 = Pattern.compile("H_flg = \"(.*)\"");
		Matcher m1 = p1.matcher(getString);
		boolean result = m1.find();
		if (result) {
			return m1.group(1);

		} else {
			return "0";
		}
	}
	
	public String parserDL(String getString) {
		Pattern p1 = Pattern.compile("DL = \"(.*)\"");
		Matcher m1 = p1.matcher(getString);
		boolean result = m1.find();
		if (result) {
			return m1.group(1);

		} else {
			return "0";
		}
	}
	
	public String parserEL(String getString) {
		Pattern p1 = Pattern.compile("EL = \"(.*)\"");
		Matcher m1 = p1.matcher(getString);
		boolean result = m1.find();
		if (result) {
			return m1.group(1);

		} else {
			return "0";
		}
	}
	
	public String parserN0005(String getString) {
		Pattern p1 = Pattern.compile("document.form3.N0005.value = \"(.*)\"");
		Matcher m1 = p1.matcher(getString);
		boolean result = m1.find();
		if (result) {
			return m1.group(1);

		} else {
			return "0";
		}
	}
	
	public String parserPI(String getString) {
		Pattern p1 = Pattern.compile("PI = \"(.*)\"");
		Matcher m1 = p1.matcher(getString);
		boolean result = m1.find();
		if (result) {
			return m1.group(1);

		} else {
			return "0";
		}
	}
	
	public String parserMID(String getString) {
		Pattern p1 = Pattern.compile("MID = (.*);");
		Matcher m1 = p1.matcher(getString);
		boolean result = m1.find();
		if (result) {
			return m1.group(1);

		} else {
			return "0";
		}
	}
	
	
		public String parserK_flg(String getString) {
		Pattern p1 = Pattern.compile("K_flg = \"(.*)\"");
		Matcher m1 = p1.matcher(getString);
		boolean result = m1.find();
		if (result) {
			return m1.group(1);

		} else {
			return "0";
		}
	}	
		
		
	
		public void parserShowFrames(String getString, Vector showFrame_V) {
			Pattern p1 = Pattern.compile("ShowFrames\\('(.*)'\\)");
			Matcher m1 = p1.matcher(getString);
			String string_tmp = "", bodytmp = "";
			do {
				boolean result = m1.find();
				if (result) {
					showFrame_V.addElement(m1.group(1));
					string_tmp = m1.group(1);
					//				
				} else {
					
					string_tmp = "1111111111111111111111111111111111111111";
				}

				int index = getString.indexOf(string_tmp);
				bodytmp = "";
				if ((index > 0)
						&& (index + string_tmp.length() <= getString.length())) {
					bodytmp = getString.substring(index + string_tmp.length() + 1);
				}

			} while (bodytmp != "");

		}	
		
		
		public String parserTitle(String getString) {
			Pattern p1 = Pattern.compile("<TITLE>\n(.*)");
			Matcher m1 = p1.matcher(getString);
			boolean result = m1.find();
			if (result) {
				return m1.group(1);

			} else {
				return "0";
			}
		}	
		
		
		public void parserGifUrl(String getString, Vector GifUrl_V) {
			Pattern p1 = Pattern.compile("SRC=\"http://www4.ipdl.inpit.go.jp(.*)\" WIDTH");
			Matcher m1 = p1.matcher(getString);
			String string_tmp = "", bodytmp = "";
			do {
				boolean result = m1.find();
				if (result) {
					GifUrl_V.addElement(m1.group(1));
					string_tmp = m1.group(1);
					//				
				} else {
					
					string_tmp = "1111111111111111111111111111111111111111";
				}

				int index = getString.indexOf(string_tmp);
				bodytmp = "";
				if ((index > 0)
						&& (index + string_tmp.length() <= getString.length())) {
					bodytmp = getString.substring(index + string_tmp.length() + 1);
				}

			} while (bodytmp != "");

		}	
		
		public void parserGifWidth(String getString, Vector GifWidth_V) {
			Pattern p1 = Pattern.compile("WIDTH=\"(\\d+)\"");
			Matcher m1 = p1.matcher(getString);
			String string_tmp = "", bodytmp = "";
			do {
				boolean result = m1.find();
				if (result) {
					GifWidth_V.addElement(m1.group(1));
					string_tmp = m1.group(1);
					//				
				} else {
					
					string_tmp = "1111111111111111111111111111111111111111";
				}

				int index = getString.indexOf(string_tmp);
				bodytmp = "";
				if ((index > 0)
						&& (index + string_tmp.length() <= getString.length())) {
					bodytmp = getString.substring(index + string_tmp.length() + 1);
				}

			} while (bodytmp != "");

		}
		
		public void parserGifHeight(String getString, Vector GifHeight_V) {
			Pattern p1 = Pattern.compile("HEIGHT=\"(\\d+)\"");
			Matcher m1 = p1.matcher(getString);
			String string_tmp = "", bodytmp = "";
			do {
				boolean result = m1.find();
				if (result) {
					GifHeight_V.addElement(m1.group(1));
					string_tmp = m1.group(1);
					//				
				} else {
					
					string_tmp = "1111111111111111111111111111111111111111";
				}

				int index = getString.indexOf(string_tmp);
				bodytmp = "";
				if ((index > 0)
						&& (index + string_tmp.length() <= getString.length())) {
					bodytmp = getString.substring(index + string_tmp.length() + 1);
				}

			} while (bodytmp != "");

		}
		
		

		
   public void downloadGif(String getstr, String tmpfilename, String title, HttpClient client) throws Exception{
	   
	    Tifdownload2 parser = new Tifdownload2();
		Vector GifUrl_V =new Vector();
		Vector GifWidth_V =new Vector();
		Vector GifHeight_V =new Vector();
		parser.parserGifUrl(getstr, GifUrl_V);
		parser.parserGifWidth(getstr,GifWidth_V);
		parser.parserGifHeight(getstr,GifHeight_V);
		
	try{	
		
		 Document document = new Document();
	     PdfWriter.getInstance(document, new FileOutputStream(tmpfilename));
         document.open();
         Font font = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.BLUE); 
         Paragraph par0 = new Paragraph(title); 
         par0.setAlignment(Element.ALIGN_MIDDLE);
         document.add(par0);
         
		
		for(int j=0;j<GifUrl_V.size();j++){
			
			System.out.println(GifUrl_V.elementAt(j));
		
			GetMethod getGif = new GetMethod(GifUrl_V.elementAt(j).toString());
			//getGif.setRequestHeader("Cookie","ARPT=IMUNUNSALPCYQW;LADDR=60.12.143.5;ACSTM=20090219224033639459");
			client.executeMethod(getGif);
			//System.out.println(getGif.getResponseBodyAsString());
		    Paragraph par = new Paragraph("DRAWING "+(j+1),font);
            document.add(par);
            
		    Image img = Image.getInstance(getGif.getResponseBody());
		    img.scaleAbsolute(Float.parseFloat(GifWidth_V.elementAt(j).toString()), Float.parseFloat(GifHeight_V.elementAt(j).toString()));
		    document.add(img);
            
			getGif.releaseConnection();
		    }
		
		   document.close();
		
	   }catch(DocumentException e){
		 
		  }
	   
	   
   }
		
	public void TiffToPDF(String filename, String[] strImages) throws Exception {
		document = new Document();
		FileOutputStream rech = new FileOutputStream(filename);
		PdfWriter writer = PdfWriter.getInstance(document, rech);
		document.open();
		cb = writer.getDirectContent();
		for (int i = 0; i < strImages.length; ++i) {
			// addImage(strImages[i]);
		}

		document.close();
	}

	// public void addImage(SeekableStream stream) throws Exception {
	//	
	// TIFFDirectory dir = new TIFFDirectory(stream, 0);
	//
	// String[] names = ImageCodec.getDecoderNames(stream);
	// ImageDecoder dec = ImageCodec
	// .createImageDecoder(names[0], stream, null);
	//
	// int total = dec.getNumPages();
	//
	// for (int k = 0; k < total; ++k) {
	// RenderedImage ri = dec.decodeAsRenderedImage(k);
	// Raster ra = ri.getData();
	// BufferedImage bi = new BufferedImage(ri.getColorModel(), Raster
	// .createWritableRaster(ri.getSampleModel(), ra
	// .getDataBuffer(), null), false, new Hashtable());
	// Image img = Image.getInstance(bi, null, true);
	// long h = 0;
	// long w = 0;
	// long IFDOffset = dir.getIFDOffset();
	// while (IFDOffset != 0L) {
	// dir = new TIFFDirectory(stream, IFDOffset, 0);
	// IFDOffset = dir.getNextIFDOffset();
	// h = dir.getFieldAsLong(TIFFImageDecoder.TIFF_IMAGE_LENGTH);
	// w = dir.getFieldAsLong(TIFFImageDecoder.TIFF_IMAGE_WIDTH);
	// }
	// float percent = 100;
	// int pos = 0;
	// if (w > 895)
	// percent = ((595 + 18) * 100 / w);
	// if (h > 842)
	// pos = (int) (842 - h * percent / 100);
	// else
	// pos = (int) (842 - h);
	// System.out.println(percent);
	// System.out.println(pos);
	// img.scalePercent(percent);
	// img.setAbsolutePosition(0, pos);
	// System.out.println("Image: " + k);
	//
	// cb.addImage(img);
	// document.newPage();
	// }
	// stream.close();
	//	
	// System.out.println("@@@@@@@@@@@@@@@@");
	// }

	public final static boolean DEBUG = true;

	private static int BUFFER_SIZE = 8096;

	private Vector vDownLoad = new Vector();

	/**
	 * 构造方法
	 */
	public Tifdownload2() {

	}

	/**
	 * 清除下载列表
	 */
	public void resetList() {
		vDownLoad.clear();

	}

	/**
	 * 增加下载列表项
	 * 
	 * @param url
	 *            String
	 * @param filename
	 *            String
	 */
	public void addItem(String url) {
		vDownLoad.add(url);

	}

	/**
	 * 根据列表下载资源
	 * 
	 * @throws Exception
	 * 
	 * @throws Exception
	 */

	public void requestJsp(String u) {
		URL url;
		InputStream is;
		InputStreamReader isr;
		BufferedReader r;
		String str;
		try {
			System.out.println("URL" + u);
			url = new URL(u);
			is = url.openStream();
			isr = new InputStreamReader(is);
			r = new BufferedReader(isr);
			do {
				str = r.readLine();
				if (str != null) {
					System.out.println(str);
				}

			} while (str != null);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println("URL NOT INVALID");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("CAN'T CONNECT");
		}

	}
	
	
	
	
	public void downLoadjpdfByList( String filename)
	
	throws Exception {
String Url = null;

System.out.println("filename=" + filename);
document = new Document();
FileOutputStream rech = null;
rech = new FileOutputStream(filename);
PdfWriter writer = PdfWriter.getInstance(document, rech);
document.open();
cb = writer.getDirectContent();
for (int i = 0; i < vDownLoad.size(); i++) {
	Url = (String) vDownLoad.get(i);


	try {

		// BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		// byte[] buf = new byte[BUFFER_SIZE];
		// int size = 0;

		// 建立链接
		url = new URL(Url);
		httpUrl = (HttpURLConnection) url.openConnection();
		// 连接指定的资源
		httpUrl.connect();

		SeekableStream stream = null;
		stream = SeekableStream.wrapInputStream(httpUrl
				.getInputStream(), true);
	//	JPEGImageDecoder   decoder   =   JPEGCodec.createJPEGDecoder(fileIn);   	
		//JPGDirectory dir = new TIFFDirectory(stream, 0);
		Image img = Image.getInstance(url);
		
		float h = img.height();
		float w = img.width();
//	h=	img.getBorderWidthBottom();
//	w=	img.getBorderWidthLeft();

		 img.setAlignment(Image.ALIGN_CENTER);

		 
			 System.out.println("h="+h);
			 System.out.println("w="+w);
//			  }catch(IOException   e)
//			  {
//				
//			  }
	
	//	long IFDOffset = dir.getIFDOffset();
	
		float percent = 100;
		int X = 0;
		int Y = 0;
		if (w >595&&h<=842)
			percent = ((595 - 20) * 100 / w);
	
		if (h >842&&w<=595)
		percent = ((842 - 20) * 100 / h);
		
		if (h >842&&w>595)
		{float	percent1 = ((842 - 20) * 100 / h);
		float	percent2 =((595 - 20) * 100 / w);
		percent=percent1<percent2?percent1:percent2;
		
		}
		
		    X= (int) (595  - w* percent/ 100)/2;
			Y = (int) (842 - h* percent/ 100)/2;
		//System.out.println(percent);
		//System.out.println(Y);
	//	Y = (int) (842 - h)/2;
	
		img.scalePercent(percent);
		  System.out.println("percent="+percent);  
		  System.out.println("X="+X); 
		  System.out.println("Y="+Y); 
		//  pos=10;
		img.setAbsolutePosition(X, Y);
		System.out.println("Image: " + i);

		cb.addImage(img);
		document.newPage();
		stream.close();
		// file.delete();
		httpUrl.disconnect();

		httpUrl.disconnect();
	} catch (IOException err) {
		if (DEBUG) {
			System.out.println("资源[" + Url + "]下载失败!!!");
		}
	}

	//			
}
document.close();
rech.close();

if (DEBUG) {
	//System.out.println("下载完成!!!");

}
}	
    


	
	
	
	
	
	
	
public void downLoadtpdfByList(String filename)
	throws Exception {
String Url = null;

System.out.println("filename=" + filename);

document = new Document();

FileOutputStream rech = null;
rech = new FileOutputStream(filename);

PdfWriter writer = PdfWriter.getInstance(document, rech);
document.open();
cb = writer.getDirectContent();

for (int i = 0; i < vDownLoad.size(); i++) {
	Url = (String) vDownLoad.get(i);


	try {

		// BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		// byte[] buf = new byte[BUFFER_SIZE];
		// int size = 0;

		// 建立链接
		url = new URL(Url);
		httpUrl = (HttpURLConnection) url.openConnection();
		// 连接指定的资源
		httpUrl.connect();

		SeekableStream stream = null;
		stream = SeekableStream.wrapInputStream(httpUrl
				.getInputStream(), true);
		TIFFDirectory dir = new TIFFDirectory(stream, 0);
		Image img = Image.getInstance(url);

		long h = 0;
		long w = 0;
		long IFDOffset = dir.getIFDOffset();
		while (IFDOffset != 0L) {
			dir = new TIFFDirectory(stream, IFDOffset, 0);
			IFDOffset = dir.getNextIFDOffset();
			h = dir.getFieldAsLong(TIFFImageDecoder.TIFF_IMAGE_LENGTH);
			w = dir.getFieldAsLong(TIFFImageDecoder.TIFF_IMAGE_WIDTH);
		}
		float percent = 100;
		int pos = 0;
		if (w >595)
			percent = ((595 + 18) * 100 / w);
		if (h > 842)
			pos = (int) (842 - h * percent / 100);
		else
			pos = (int) (842 - h);
		System.out.println(percent);
		//System.out.println(pos);
		img.scalePercent(percent);
		img.setAbsolutePosition(0, pos);
		//System.out.println("Image: " + i);

		cb.addImage(img);
		document.newPage();
		stream.close();
		// file.delete();
		httpUrl.disconnect();

		httpUrl.disconnect();
	} catch (IOException err) {
		if (DEBUG) {
			System.out.println("资源[" + Url + "]下载失败!!!");
		}
	}

	//			
}
document.close();
rech.close();

if (DEBUG) {
	//System.out.println("下载完成!!!");

}
}


	public void downLoadtifByList(HttpServletResponse response, String filenames,String sPatentName)
			throws Exception {
		String Url = null;
		String filename="";
		// String filename = null;
		// OutputStream fos = response.getOutputStream();
		// 按列表顺序保存资源
		sPatentName =sPatentName.replaceAll("&nbsp;", "");
		/*
		if (sPatentName.startsWith(""))
		{
			try {
				sPatentName=sPatentName.substring(1);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				///e.printStackTrace()
				return ;
			}
		}
		*/

		filenames = StringUtils.replace(filenames, ".", "_");
        filenames =filenames.trim();
		filename = filenames + ".pdf";
		response.reset();
		response.setCharacterEncoding("UTF-8");

		response.setContentType("application/pdf;charset=gb2312");

		filename =StringUtils.replace(filename, "?", "");

		System.out.println("filename=" + filename);
		response.setHeader("Content-Disposition", "attachment; filename="
				+ URLEncoder.encode(filename, "UTF-8") + "");
		document = new Document();
		OutputStream rech = response.getOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, rech);
		document.open();
		cb = writer.getDirectContent();

		for (int i = 0; i < vDownLoad.size(); i++) {
			Url = (String) vDownLoad.get(i);
			// filename = (String) vFileList.get(i);

			try {

				// BufferedInputStream bis = null;
				HttpURLConnection httpUrl = null;
				URL url = null;
				// byte[] buf = new byte[BUFFER_SIZE];
				// int size = 0;

				// 建立链接
				url = new URL(Url);
				httpUrl = (HttpURLConnection) url.openConnection();
				// 连接指定的资源
				httpUrl.connect();

				SeekableStream stream = null;
				stream = SeekableStream.wrapInputStream(httpUrl
						.getInputStream(), true);
				TIFFDirectory dir = new TIFFDirectory(stream, 0);
				Image img = Image.getInstance(url);

				long h = 0;
				long w = 0;
				long IFDOffset = dir.getIFDOffset();
				while (IFDOffset != 0L) {
					dir = new TIFFDirectory(stream, IFDOffset, 0);
					IFDOffset = dir.getNextIFDOffset();
					h = dir.getFieldAsLong(TIFFImageDecoder.TIFF_IMAGE_LENGTH);
					w = dir.getFieldAsLong(TIFFImageDecoder.TIFF_IMAGE_WIDTH);
				}
				float percent = 100;
				int pos = 0;
				if (w >595)
					percent = ((595 + 18) * 100 / w);
				if (h > 842)
					pos = (int) (842 - h * percent / 100);
				else
					pos = (int) (842 - h);
				System.out.println(percent);
				//System.out.println(pos);
				img.scalePercent(percent);
				img.setAbsolutePosition(0, pos);
				//System.out.println("Image: " + i);

				cb.addImage(img);
				document.newPage();
				stream.close();
				// file.delete();
				httpUrl.disconnect();

				httpUrl.disconnect();
			} catch (IOException err) {
				if (DEBUG) {
					System.out.println("资源[" + Url + "]下载失败!!!");
				}
			}

			//			
		}
		document.close();
		rech.close();

		if (DEBUG) {
			//System.out.println("下载完成!!!");

		}
	}

	
	public void downLoadjpgByList(HttpServletResponse response, String filenames,String sPatentName)
	throws Exception {
String Url = null;
String filename="";
// String filename = null;
// OutputStream fos = response.getOutputStream();
// 按列表顺序保存资源
sPatentName =sPatentName.replaceAll("&nbsp;", "");


filenames = StringUtils.replace(filenames, ".", "_");
filenames =filenames.trim();
filename = filenames + ".pdf";
response.reset();
//System.out.println(response.getContentType());
response.setCharacterEncoding("UTF-8");

//System.out.println("@@@@@@@@@filename＝" + filename);
response.setContentType("application/pdf;charset=gb2312");

//filename = StringUtils.replace(filename, "+", "%20");
filename =StringUtils.replace(filename, "?", "");

System.out.println("filename=" + filename);
response.setHeader("Content-Disposition", "attachment; filename="
		+ URLEncoder.encode(filename, "UTF-8") + "");
document = new Document();
OutputStream rech = response.getOutputStream();
PdfWriter writer = PdfWriter.getInstance(document, rech);
document.open();
cb = writer.getDirectContent();

for (int i = 0; i < vDownLoad.size(); i++) {
	Url = (String) vDownLoad.get(i);
	// filename = (String) vFileList.get(i);

	try {

		// BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		// byte[] buf = new byte[BUFFER_SIZE];
		// int size = 0;

		// 建立链接
		url = new URL(Url);
		httpUrl = (HttpURLConnection) url.openConnection();
		// 连接指定的资源
		httpUrl.connect();

		SeekableStream stream = null;
		stream = SeekableStream.wrapInputStream(httpUrl
				.getInputStream(), true);
	//	JPEGImageDecoder   decoder   =   JPEGCodec.createJPEGDecoder(fileIn);   	
		//JPGDirectory dir = new TIFFDirectory(stream, 0);
		Image img = Image.getInstance(url);
		
		float h = img.height();
		float w = img.width();
//	h=	img.getBorderWidthBottom();
//	w=	img.getBorderWidthLeft();

		 img.setAlignment(Image.ALIGN_CENTER);

		 
			 System.out.println("h="+h);
			 System.out.println("w="+w);
//			  }catch(IOException   e)
//			  {
//				
//			  }
	
	//	long IFDOffset = dir.getIFDOffset();
	
		float percent = 100;
		int X = 0;
		int Y = 0;
		if (w >595&&h<=842)
			percent = ((595 - 20) * 100 / w);
	
		if (h >842&&w<=595)
		percent = ((842 - 20) * 100 / h);
		
		if (h >842&&w>595)
		{float	percent1 = ((842 - 20) * 100 / h);
		float	percent2 =((595 - 20) * 100 / w);
		percent=percent1<percent2?percent1:percent2;
		
		}
		
		    X= (int) (595  - w* percent/ 100)/2;
			Y = (int) (842 - h* percent/ 100)/2;
		//System.out.println(percent);
		//System.out.println(Y);
	//	Y = (int) (842 - h)/2;
	
		img.scalePercent(percent);
		  System.out.println("percent="+percent);  
		  System.out.println("X="+X); 
		  System.out.println("Y="+Y); 
		//  pos=10;
		img.setAbsolutePosition(X, Y);
		System.out.println("Image: " + i);

		cb.addImage(img);
		document.newPage();
		stream.close();
		// file.delete();
		httpUrl.disconnect();

		httpUrl.disconnect();
	} catch (IOException err) {
		if (DEBUG) {
			System.out.println("资源[" + Url + "]下载失败!!!");
		}
	}

	//			
}
document.close();
rech.close();

if (DEBUG) {
	//System.out.println("下载完成!!!");

}
}
	
	/**
	 * 将HTTP资源另存为文件
	 * 
	 * @param destUrl
	 *            String
	 * @param fileName
	 *            String
	 * @throws Exception
	 */
	public String[] getTifinfor(String patenturl) throws Exception {

		URL url = new URL(patenturl);
		HttpURLConnection httpConnection = (HttpURLConnection) url
				.openConnection();

		// 设置User-Agent
		httpConnection.setRequestProperty("User-Agent", "BorderSpider ("
				+ patenturl + ")");

		// 获得输入流
		InputStream input = httpConnection.getInputStream();
		InputStreamReader inReader = new InputStreamReader(input, "gb2312");// 获得链接该类的流
		BufferedReader reader = new BufferedReader(inReader);

		int retVal = 0;
		char[] cString = new char[1000];
		int len = 1000;
		String getString = "";

		while ((retVal = reader.read(cString, 0, len)) != -1) {
			getString += String.valueOf(cString, 0, retVal);
		}
		//System.out.println(getString);

		// Create a pattern to match cat
		Pattern p1 = Pattern
				.compile("<input type=\"hidden\" name=\"tifpath\" value=\"([^\"]+)\"");
		// Pattern p2 = Pattern.compile("\\((\\d+)页\\)</a>");//20080504 edsion
		// xiugai
		Pattern p2 = Pattern
				.compile("<input.*?type=\"hidden\".*?name=\"totalpage\".*?value=\"\\d{1,9}\">");// 20080504
		// edsion
		// xiugai
		Pattern p3 = Pattern
				.compile("<input type=\"hidden\" name=\"recid\" value=\"([^\"]+)\"");

		Matcher m1 = p1.matcher(getString);
		Matcher m2 = p2.matcher(getString);
		Matcher m3 = p3.matcher(getString);

		boolean result = m1.find();
		boolean result2 = m2.find();
		boolean result3 = m3.find();
		String newurl = "";
		String spagenumber = "";
		String sPatentName="";
		String patentcode = "";

		// Loop through and create a new String
		// with the replacements
		System.out.println("@@@@result1=" + result);
		System.out.println("@@@@result2=" + result2);
		System.out.println("@@@@result3=" + result3);
		if (result) {
			newurl = m1.group(1);

			System.out.println("@@@@newurl=" + newurl);
		}

		if (result2) {
			if (result2) {

				spagenumber = m2.group();
				// 20080504 begin
				Pattern pnumber = Pattern.compile("\\d{1,9}");
				Matcher mnumber = pnumber.matcher(spagenumber);
				boolean resultnumber = mnumber.find();
				if (resultnumber) {
					spagenumber = mnumber.group();
				}
				// 20080504 end

				System.out.println("@@@@spagenumber=" + spagenumber);
			}

			System.out.println("@@@@spagenumber=" + spagenumber);
		}
		if (result3) {

			patentcode = m3.group(1);
			System.out.println("@@@@专利号=" + patentcode);
		}
		//20080529 陈国海 加专利名字
		ParsePatent_sipo parser= new ParsePatent_sipo(getString);
		sPatentName =parser.getName();
	//	parser.
		
		
		// int pagenumber = Integer.parseInt(spagenumber);
		// Pattern p4 = Pattern.compile("/(d+).tif");
		// Matcher m4 = p4.matcher(newurl);
		reader.close();
		inReader.close();
		input.close();
		// Add the last segment of input to
		// the new String

		String[] flag = { newurl, spagenumber, patentcode,sPatentName};
		return flag;

	}

	// public void saveToFile(String destUrl, String fileName,
	// HttpServletResponse response) throws IOException,
	// InterruptedException {
	//
	// response.setContentType("application/x-msdownload;charset=gb2312");
	// response.setHeader("Content-Disposition", "attachment; filename="
	// + fileName);
	// BufferedInputStream bis = null;
	// HttpURLConnection httpUrl = null;
	// URL url = null;
	// byte[] buf = new byte[BUFFER_SIZE];
	// int size = 0;
	//
	// // 建立链接
	// url = new URL(destUrl);
	// httpUrl = (HttpURLConnection) url.openConnection();
	// // 连接指定的资源
	// httpUrl.connect();
	// // 获取网络输入流
	//
	// // InputStream input2 = httpUrl.getInputStream();
	// // InputStreamReader inReader2 = new InputStreamReader(input2,
	// // "gb2312");//获得链接该类的流
	// // BufferedReader br = new BufferedReader(inReader2);
	//
	// // int len = 0;
	// // OutputStream out = response.getOutputStream();
	// // if (this.DEBUG)
	// // System.out.println("正在获取链接[" + destUrl + "]的内容...\n将其保存为文件[" +
	// // fileName + "]");
	// // while ((len = input2.read(buf)) > 0)
	// // out.write(buf, 0, len);
	// // out.close();
	// // br.close();
	// // inReader2.close();
	//
	// bis = new BufferedInputStream(httpUrl.getInputStream());
	// response.reset();
	// System.out.println(response.getContentType());
	// response.setCharacterEncoding("UTF-8");
	// System.out.println("@@@@@@@@@");
	//
	// // fos = new FileOutputStream(fileName);
	// System.out.println("@@@@@@@@@");
	// // 建立文件
	// if (this.DEBUG)
	// System.out.println("正在获取链接[" + destUrl + "]的内容...\n将其保存为文件["
	// + fileName + "]");
	// OutputStream fos = response.getOutputStream();
	// // 保存文件
	// while ((size = bis.read(buf)) != -1)
	// fos.write(buf, 0, size);
	//
	// fos.close();
	// bis.close();
	//
	// httpUrl.disconnect();
	// }

	/**
	 * 设置代理服务器
	 * 
	 * @param proxy
	 *            String
	 * @param proxyPort
	 *            String
	 */
	public void setProxyServer() {
		// 设置代理服务器
//		System.getProperties().put("proxySet", "true");
//		System.getProperties().put("proxyHost", proxy);
//		System.getProperties().put("proxyPort", proxyPort);
		//ProxyUtil prox=new ProxyUtil();
		ProxyUtil.useHttpProxy();

	}

	public class MyAuthenticator extends Authenticator {
		private String username, password;

		public MyAuthenticator(String username, String password) {
			this.username = username;
			this.password = password;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password.toCharArray());
		}
	}

	/**
	 * 设置认证用户名与密码
	 * 
	 * @param uid
	 *            String
	 * @param pwd
	 *            String
	 */

	public void setAuthenticator(String uid, String pwd) {
		Authenticator.setDefault(new MyAuthenticator(uid, pwd));
	}

	/**
	 * 主方法(用于测试)
	 * 
	 * @param argv
	 *            String[]
	 */
//	public void getJPGDownloadInfor(HttpServletResponse response, String url,
//			String pagenumber, String patentcode,String sPatentName) {
//		//System.out.println("正在获取链接[]的内容...\n将其保存为文件[]");
//		Tifdownload oInstance = new Tifdownload();
//		String starturl = url.replaceFirst("000001.jpg", "");
//		System.out.println("starturl="+starturl);
//		try {
//			// 增加下载列表（此处用户可以写入自己代码来增加下载列表）
//			String oo = "000";
//			int i = Integer.parseInt(pagenumber);
//			for (int k = 1; k <= i; k++) {
//				if (k < 100) {
//					oo = "0000";
//					if (k < 10)
//						oo = "00000";
//				}
//			//	System.out.println("urls="+starturl + oo + k + ".jpg");
//				oInstance.addItem(starturl + oo + k + ".jpg");
//			}
//
//			// 开始下载
//			//System.out.println("正在获取链接[]的内容...\n将其保存为文件[]");
//			oInstance.downLoadjpgByList(response, patentcode,sPatentName);
//
//		} catch (Exception err) {
//			System.out.println(err.getMessage());
//		}
//
//	}
//	public void getTifDownloadInfor(HttpServletResponse response, String url,
//			String pagenumber, String patentcode,String sPatentName) {
//		//System.out.println("正在获取链接[]的内容...\n将其保存为文件[]");
//		Tifdownload oInstance = new Tifdownload();
//		String starturl = url.replaceFirst("000001.tif", "");
//		System.out.println("starturl="+starturl);
//		try {
//			// 增加下载列表（此处用户可以写入自己代码来增加下载列表）
//			String oo = "000";
//			int i = Integer.parseInt(pagenumber);
//			for (int k = 1; k <= i; k++) {
//				if (k < 100) {
//					oo = "0000";
//					if (k < 10)
//						oo = "00000";
//				}
//			//	System.out.println("urls="+starturl + oo + k + ".jpg");
//				oInstance.addItem(starturl + oo + k + ".tif");
//			}
//
//			// 开始下载
//			//System.out.println("正在获取链接[]的内容...\n将其保存为文件[]");
//			oInstance.downLoadBytifList( response,patentcode,sPatentName);
//
//		} catch (Exception err) {
//			System.out.println(err.getMessage());
//		}

//	}

}
