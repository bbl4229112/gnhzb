package edu.zju.cims201.GOF.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ParsePatent_sipo {
	private String source;

	private String ret_source;

	//private String pub_code = "";

	public ParsePatent_sipo() {

	}

	public ParsePatent_sipo(String source) {
		this.source = source;
	}

	public String getApp_code() {
		String app_code="";
		Pattern p1 = Pattern.compile("<span\\sclass=\"zi_10\">(\\d{1,15})");
		Matcher m1 = p1.matcher(source);
		boolean result = m1.find();
		if (result) {
			app_code = m1.group(1);
			// app_codetmp = m1.group(1);
			//				
		} else {
			// app_code.addElement("11111111");
			app_code = "111111111";
		}

		// String sposition =
		// "申&nbsp;&nbsp;&nbsp;&nbsp;请&nbsp;&nbsp;&nbsp;&nbsp;号：";
		// String eposition = "</span>";
		// int offset=32;
		// String str_offset =
		// "申&nbsp;&nbsp;&nbsp;&nbsp;请&nbsp;&nbsp;&nbsp;&nbsp;号：</td><td
		// width=\"273\" class=\"kuang2\">&nbsp;<span class=\"zi_10\">";
		// int offset = str_offset.length();

		// System.out.println("offset:" + offset);

		// String app_code = "";
		// getsource(offset, sposition, eposition);
		app_code = "CN" + app_code;

		return app_code;

	}

	public void getsource(int offset, String sposition, String eposition) {
		// TODO Auto-generated method stub

		// String source="";
		String source_trim = "";
		String source1 = "";

		// String source_trim=source.trim();

		String temp_source = "";
		int start_point = 0;
		int end_point = 0;
		start_point = this.source.indexOf(sposition);

		if (start_point > 0) {

			temp_source = source.substring(start_point + offset);
			// System.out.println(temp_source);
			end_point = temp_source.indexOf(eposition);
			if (end_point != -1) {
				source1 = temp_source.substring(0, end_point);
			}
		}

		source_trim = source1.trim();
		// app_code = StringUtils.replace(keyword, ",", " ");

		ret_source = source_trim;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cims201.spider.patent.parser.Parser#getApp_date()
	 */
	public String getApp_date() {
		
		String app_date="";
		Pattern p1 = Pattern.compile("申.*请.*日.*\\s*.*</td>\\s*.*<td\\s*class=\"kuang2\">.*([0-9]{4}.*[0-9]{1,2}.*[0-9]{1,2})");
		Matcher m1 = p1.matcher(source);
		boolean result = m1.find();
		if (result) {
			app_date = m1.group(1);
			// app_codetmp = m1.group(1);
			//				
		} else {
			// app_code.addElement("11111111");
			app_date = "0000.00.00";
		}
	
		// TODO Auto-generated method stub
		/*String sposition = "申&nbsp; &nbsp;请&nbsp;&nbsp; 日：";
		String eposition = "</td>";
		// int offset=32;
		String str_offset = "申&nbsp; &nbsp;请&nbsp;&nbsp; 日：</td><td class='kuang2'>&nbsp;";
		int offset = str_offset.length();
		String app_date = "";
		getsource(offset, sposition, eposition);
		app_date = ret_source;

		String app_year = app_date.substring(0, 4);
		String app_month = app_date.substring(5, 7);
		String app_date1 = app_date.substring(8, 10);
		app_date = app_year + "-" + app_month + "-" + app_date1;
            */
		return app_date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cims201.spider.patent.parser.Parser#getName()
	 */
	public String getName() {
		// TODO Auto-generated method stub
		/*
		String sposition = "名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称： ";
		String eposition = "</td>";
		String str_offset = "名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称： </td><td colspan=\"3\" class=\"kuang2\">&nbsp;";
		int offset = str_offset.length();
		String name = "";
		getsource(offset, sposition, eposition);
		name = ret_source;
        */
		String name="";
		Pattern p1 = Pattern.compile("名.*称.*\\s*</td>\\s*.*<td\\s*colspan=\"3\"\\s*class=\"kuang2\">(.*)</td>");
		Matcher m1 = p1.matcher(source);
		boolean result = m1.find();
		if (result) {
			name = m1.group(1);
			// app_codetmp = m1.group(1);
			//				
		} else {
			// app_code.addElement("11111111");
			name = "1111111";
		}
	
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cims201.spider.patent.parser.Parser#getPub_code()
	 */
	public String getPub_code() {
		String pub_code="";
		Pattern p1 = Pattern.compile("公.*告.*号.*\\s*</td>\\s*.*<td\\s*class=\"kuang2\">(.*)</td>");
		Matcher m1 = p1.matcher(source);
		boolean result = m1.find();
		if (result) {
			pub_code = m1.group(1);
			// app_codetmp = m1.group(1);
			//				
		} else {
			// app_code.addElement("11111111");
			pub_code = "1111111";
		}
		return pub_code;
	
		/*
		String sposition = "公&nbsp;开&nbsp;(公告)&nbsp;号：";
		String eposition = "</td>";
		String str_offset = "公&nbsp;开&nbsp;(公告)&nbsp;号：</td><td class='kuang2'>&nbsp;";
		int offset = str_offset.length();
		String pub_code = "";
		getsource(offset, sposition, eposition);
		pub_code = ret_source;
		this.pub_code = pub_code;
		*/

	
		
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cims201.spider.patent.parser.Parser#getPub_date()
	 */
	public String getPub_date() {
		/*

		String eposition = "</td>";

		String str_offset1 = "公&nbsp;开&nbsp;(公告)&nbsp;号：</td><td class='kuang2'>&nbsp;"
				+ pub_code
				+ " </td><td align='center' bgcolor='#F4F3F3' class='kuang2'>";
		int offset1 = str_offset1.length();
		// String eposition="</td>";
		getsource(offset1, "公&nbsp;开&nbsp;(公告)&nbsp;号：", eposition);
		String sposition = this.ret_source;

		String str_offset = sposition + "</td><td class='kuang2'>&nbsp;";
		int offset = str_offset.length();
		String pub_date = "";
		getsource(offset, sposition, eposition);
		pub_date = ret_source;
		String pub_year = pub_date.substring(0, 4);
		String pub_month = pub_date.substring(5, 7);
		String pub_date1 = pub_date.substring(8, 10);
		pub_date = pub_year + "-" + pub_month + "-" + pub_date1;
		*/
		String pub_date="";
		Pattern p1 = Pattern.compile("公.*告.*日.*\\s*.*</td>\\s*.*<td\\s*class=\"kuang2\">.*([0-9]{4}.*[0-9]{1,2}.*[0-9]{1,2})");
		Matcher m1 = p1.matcher(source);
		boolean result = m1.find();
		if (result) {
			pub_date = m1.group(1);
			// app_codetmp = m1.group(1);
			//				
		} else {
			// app_code.addElement("11111111");
			pub_date = "0000.00.00";
		}
		return pub_date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cims201.spider.patent.parser.Parser#getIPC()
	 */
	public String getIPC() {
		String IPC="";
		Pattern p1 = Pattern.compile("主.*分.*类.*号.*</td>\\s*.*<td\\s*class=\"kuang2\">(.*)</td");
		Matcher m1 = p1.matcher(source);
		boolean result = m1.find();
		if (result) {
			IPC = m1.group(1);
			// app_codetmp = m1.group(1);
			//				
		} else {
			// app_code.addElement("11111111");
			IPC = "11111111";
		}
		
		/*
		String sposition = "主 &nbsp;分 &nbsp;类 &nbsp;号： ";
		String eposition = "</td>";
		String str_offset = "主 &nbsp;分 &nbsp;类 &nbsp;号： </td><td class='kuang2'>&nbsp;";
		int offset = str_offset.length();

		// int offset=29;
		String IPC = "";
		getsource(offset, sposition, eposition);
		IPC = ret_source;
         */
		return IPC;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cims201.spider.patent.parser.Parser#getCat_code()
	 */
	public String getCat_code() {
		String cat_code="";
		Pattern p1 = Pattern.compile("分.*类.*号.*</td>\\s*.*<td\\s*colspan=\"3\"\\s*class=\"kuang2\">(.*)</td");
		Matcher m1 = p1.matcher(source);
		boolean result = m1.find();
		if (result) {
			cat_code = m1.group(1);
			// app_codetmp = m1.group(1);
			//				
		} else {
			// app_code.addElement("11111111");
			cat_code = "11111111";
		}
		
		/*
		String sposition = "分&nbsp;&nbsp;&nbsp; 类&nbsp; &nbsp;&nbsp;号： ";
		String eposition = "</td>";
		String str_offset = "分&nbsp;&nbsp;&nbsp; 类&nbsp; &nbsp;&nbsp;号： </td><td colspan='3' class='kuang2'>&nbsp;";
		int offset = str_offset.length();

		// int offset=29;
		String cat_code = "";
		getsource(offset, sposition, eposition);
		cat_code = ret_source;
         */
		return cat_code;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cims201.spider.patent.parser.Parser#getCer_date()
	 */
	public String getCer_date() {
		/*
		String sposition = "颁&nbsp;&nbsp;&nbsp; 证&nbsp; &nbsp;&nbsp;&nbsp;日： ";
		String eposition = "</td>";
		String str_offset = "颁&nbsp;&nbsp;&nbsp; 证&nbsp; &nbsp;&nbsp;&nbsp;日： </td><td class='kuang2'>&nbsp;";
		int offset = str_offset.length();

		String cer_date = "";
		getsource(offset, sposition, eposition);
		cer_date = ret_source;

		String cer_year = cer_date.substring(0, 4);
		String cer_month = cer_date.substring(5, 7);
		String cer_date1 = cer_date.substring(8, 10);
		cer_date = cer_year + "-" + cer_month + "-" + cer_date1;
		/*
		 * 
		 * 
		 */
		String cer_date="";
		Pattern p1 = Pattern.compile("颁.*证.*日.*\\s*.*</td>\\s*.*<td\\s*class=\"kuang2\">(.*[0-9]{0,4}.*[0-9]{0,2}.*[0-9]{0,2})</td>");
		Matcher m1 = p1.matcher(source);
		boolean result = m1.find();
		if (result) {
			cer_date = m1.group(1);
			// app_codetmp = m1.group(1);
			//				
		} else {
			// app_code.addElement("11111111");
			cer_date = "0000.00.00";
		}

		return cer_date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cims201.spider.patent.parser.Parser#getPriory()
	 */
	public String getPriory() {
		String priory="";
		Pattern p1 = Pattern.compile("优.*先.*权.*\\s*</td>\\s*.*<td\\s*class=\"kuang2\">(.*)</td>");
		Matcher m1 = p1.matcher(source);
		boolean result = m1.find();
		if (result) {
			priory = m1.group(1);
			// app_codetmp = m1.group(1);
			//				
		} else {
			// app_code.addElement("11111111");
			priory = " ";
		}
       /*
		String sposition = "优&nbsp;&nbsp;&nbsp;先&nbsp;&nbsp;&nbsp;权：";
		String eposition = "</td>";
		String str_offset = "优&nbsp;&nbsp;&nbsp;先&nbsp;&nbsp;&nbsp;权：</td><td class='kuang2'>&nbsp;";
		int offset = str_offset.length();
		String priory = "";
		getsource(offset, sposition, eposition);
		priory = ret_source;
         */
		return priory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cims201.spider.patent.parser.Parser#getApp_person()
	 */
	public String getApp_person() {
		
		/*
		String sposition = "申请(专利权)人：";
		String eposition = "</td>";
		String str_offset = "申请(专利权)人： </td><td colspan='3' class='kuang2'>&nbsp;";
		int offset = str_offset.length();
		String app_person = "";
		getsource(offset, sposition, eposition);
		app_person = ret_source;

		app_person = new String(app_person.getBytes());
		app_person = app_person.replaceAll("\\?", ".");
         */
		
		String app_person="";
		Pattern p1 = Pattern.compile("申.*请.*人.*\\s*</td>\\s*.*<td\\s*colspan=\"3\"\\s*class=\"kuang2\">(.*)</td>");
		Matcher m1 = p1.matcher(source);
		boolean result = m1.find();
		if (result) {
			app_person = m1.group(1);
			// app_codetmp = m1.group(1);
			//				
		} else {
			// app_code.addElement("11111111");
			app_person = "";
		}
		
		
		return app_person;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cims201.spider.patent.parser.Parser#getApp_address()
	 */
	public String getApp_address() {
		/*
		String sposition = "地 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：";
		String eposition = "</td>";
		String str_offset = "地 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</td><td colspan='3' class='kuang2'>&nbsp;";
		int offset = str_offset.length();
		String app_address = "";
		getsource(offset, sposition, eposition);
		app_address = ret_source;
      */
		String app_address="";
		Pattern p1 = Pattern.compile("地.*址.*\\s*</td>\\s*.*<td\\s*colspan=\"3\"\\s*class=\"kuang2\">(.*)</td>");
		Matcher m1 = p1.matcher(source);
		boolean result = m1.find();
		if (result) {
			app_address = m1.group(1);
			// app_codetmp = m1.group(1);
			//				
		} else {
			// app_code.addElement("11111111");
			app_address = " ";
		}
		
		return app_address;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cims201.spider.patent.parser.Parser#getInv_person()
	 */
	public String getInv_person() {
		/*
		String sposition = "发&nbsp;明&nbsp;(设计)人：";
		String eposition = "</td>";
		String str_offset = "发&nbsp;明&nbsp;(设计)人：</td><td class='kuang2'>&nbsp;";
		int offset = str_offset.length();
		String inv_person = "";
		getsource(offset, sposition, eposition);
		inv_person = ret_source;

		inv_person = new String(inv_person.getBytes());
		inv_person = inv_person.replaceAll("\\?", ".");
          */
		
		String inv_person="";
		Pattern p1 = Pattern.compile("发.*明.*人.*\\s*</td>\\s*.*<td\\s*class=\"kuang2\">(.*)</td>");
		Matcher m1 = p1.matcher(source);
		boolean result = m1.find();
		if (result) {
			inv_person = m1.group(1);
			// app_codetmp = m1.group(1);
			//				
		} else {
			// app_code.addElement("11111111");
			inv_person = " ";
		}
		return inv_person;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cims201.spider.patent.parser.Parser#getInt_app()
	 */
	public String getInt_app() {
		/*
		String sposition = "国 &nbsp;际 申 请：";
		String eposition = "</td>";
		String str_offset = "国 &nbsp;际 申 请：</td><td class='kuang2'>&nbsp;";
		int offset = str_offset.length();
		String inv_app = "";
		getsource(offset, sposition, eposition);
		inv_app = ret_source;
          */
		String inv_app="";
		Pattern p1 = Pattern.compile("国.*际.*申.*请.*\\s*</td>\\s*.*<td\\s*class=\"kuang2\">(.*)</td>");
		Matcher m1 = p1.matcher(source);
		boolean result = m1.find();
		if (result) {
			inv_app = m1.group(1);
			// app_codetmp = m1.group(1);
			//				
		} else {
			// app_code.addElement("11111111");
			inv_app = " ";
		}
		return inv_app;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cims201.spider.patent.parser.Parser#getInt_pub()
	 */
	public String getInt_pub() {
		/*
		String sposition = "国&nbsp;&nbsp;际&nbsp;&nbsp;公&nbsp;&nbsp;布：";
		String eposition = "</td>";
		String str_offset = "国&nbsp;&nbsp;际&nbsp;&nbsp;公&nbsp;&nbsp;布：</td><td class='kuang2'>&nbsp;";
		int offset = str_offset.length();

		String inv_pub = "";
		getsource(offset, sposition, eposition);
		inv_pub = ret_source;
       */
		String inv_pub="";
		Pattern p1 = Pattern.compile("国.*际.*公.*布.*\\s*</td>\\s*.*<td\\s*class=\"kuang2\">(.*)</td>");
		Matcher m1 = p1.matcher(source);
		boolean result = m1.find();
		if (result) {
			inv_pub = m1.group(1);
			// app_codetmp = m1.group(1);
			//				
		} else {
			// app_code.addElement("11111111");
			inv_pub = " ";
		}
		return inv_pub;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cims201.spider.patent.parser.Parser#getEnt_nation_date()
	 */
	public String getEnt_nation_date() {
		/*
		String sposition = "进入国家日期：";
		String eposition = "</td>";

		String str_offset = "进入国家日期：</td><td class='kuang2'>&nbsp;";
		int offset = str_offset.length();

		String ent_nation_date = "";
		getsource(offset, sposition, eposition);
		ent_nation_date = ret_source;
        */
		String ent_nation_date="";
		Pattern p1 = Pattern.compile("进.*入.*国.*家.*\\s*</td>\\s*.*<td\\s*class=\"kuang2\">(.*)</td>");
		Matcher m1 = p1.matcher(source);
		boolean result = m1.find();
		if (result) {
			ent_nation_date = m1.group(1);
			// app_codetmp = m1.group(1);
			//				
		} else {
			// app_code.addElement("11111111");
			ent_nation_date = "0000-00-00";
		}
		return ent_nation_date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cims201.spider.patent.parser.Parser#getSub_agent()
	 */
	public String getSub_agent() {
		/*
		String sposition = "专利&nbsp;代理&nbsp;机构： ";
		String eposition = "</td>";

		String str_offset = "专利&nbsp;代理&nbsp;机构： </td><td class='kuang3'>&nbsp;";
		int offset = str_offset.length();
		String sub_agent = "";
		getsource(offset, sposition, eposition);
		sub_agent = ret_source;
        */
		String sub_agent="";
		Pattern p1 = Pattern.compile("专利.*代理.*机构.*\\s*</td>\\s*.*<td\\s*class=\"kuang3\">(.*)</td>");
		Matcher m1 = p1.matcher(source);
		boolean result = m1.find();
		if (result) {
			sub_agent = m1.group(1);
			// app_codetmp = m1.group(1);
			//				
		} else {
			// app_code.addElement("11111111");
			sub_agent = "";
		}
		return sub_agent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cims201.spider.patent.parser.Parser#getSub_person()
	 */
	public String getSub_person() {
		
		/*
		String sposition = "代&nbsp;&nbsp;&nbsp;理&nbsp;&nbsp;&nbsp;人：";
		String eposition = "</td>";
		String str_offset = "代&nbsp;&nbsp;&nbsp;理&nbsp;&nbsp;&nbsp;人：</td><td class='kuang3'>&nbsp;";
		int offset = str_offset.length();
		String sub_person = "";
		getsource(offset, sposition, eposition);
		sub_person = ret_source;

		sub_person = new String(sub_person.getBytes());
		sub_person = sub_person.replaceAll("\\?", ".");
        */
		String sub_person="";
		Pattern p1 = Pattern.compile("代.*理.*人.*\\s*</td>\\s*.*<td\\s*class=\"kuang3\">(.*)</td>");
		Matcher m1 = p1.matcher(source);
		boolean result = m1.find();
		if (result) {
			sub_person = m1.group(1);
			// app_codetmp = m1.group(1);
			//				
		} else {
			// app_code.addElement("11111111");
			sub_person = "";
		}
		return sub_person;
	}

	/*
	 * 发明类型
	 */
	public String getCn_type() {
		String sposition = "[专利类型]：";
		String eposition = "</td>";
		String str_offset = "[专利类型]：</b>";
		int offset = str_offset.length();
		String cn_type = "";
		getsource(offset, sposition, eposition);
		cn_type = ret_source;

		return cn_type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cims201.spider.patent.parser.Parser#getAbstract()
	 */
	public String getAbstract() {
		/*
		String sposition = " &nbsp;&nbsp;摘要";
		String eposition = "</td>";
		String str_offset = "&nbsp;&nbsp;摘要　 </td></tr></table><table width='772' border='0' align='center' cellpadding='5' cellspacing='0'><tr><td align='left' class='zi_zw'>&nbsp;    ";
		int offset = str_offset.length();
		String _abstract = "";
		getsource(offset, sposition, eposition);
		_abstract = ret_source;
         */
		
		String _abstract="";
		try{_abstract=source.substring(source.indexOf("class=\"zi_zw\">")+14);
		_abstract=_abstract.substring(0,_abstract.indexOf("</td>"));
		_abstract=_abstract.replaceAll("&nbsp;", "");
		_abstract=_abstract.trim();
		
		}
		catch(Exception e){_abstract="";}
//		Pattern p1 = Pattern.compile("<td\\s*align=\"left\"\\s*class=\"zi_zw\">(.*)</td>");
//		Matcher m1 = p1.matcher(source);
//		System.out.println(source);
//		boolean result = m1.find();
//		if (result) {
//			_abstract = m1.group(1);
//			// app_codetmp = m1.group(1);
//			//				
//		} else {
//			// app_code.addElement("11111111");
//			_abstract = "";
//		System.out.println("没有解析出来");
//		
//		}
		
		return _abstract;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cims201.spider.patent.parser.Parser#getMain_power()
	 */
	public String getMain_power() {
		String sposition = "&nbsp;&nbsp;主权项";
		String eposition = "</td>";
		String str_offset = "&nbsp;&nbsp;主权项 　 </td></tr></table><table width='772' border='0' align='center' cellpadding='5' cellspacing='0'><tr><td align='left' class='zi_zw'>&nbsp;";
		int offset = str_offset.length();
		String main_power = "";
		getsource(offset, sposition, eposition);
		main_power = ret_source;

		return main_power;
	}

	public String getFullurl() {
		/*
		int startpage = source.indexOf("javascript:browsetif");

		int oppsitepage = "javascript:browsetif('fm','".length();
		String substringpage = source.substring(startpage + oppsitepage);
		int endpage = substringpage.indexOf("'");

		String pages = substringpage.substring(0, endpage);
		int starturl = substringpage.indexOf("http");
		String substringurl = substringpage.substring(starturl);
		int endurl = substringurl.indexOf("'");
		String starturls = substringurl.substring(0, endurl);
		System.out.println(pages);
		System.out.println(starturls);
		

		// //// get http://211.157.104.92/books/fm/2006/2238/200610009815 URL
		int endurl2 = starturls.lastIndexOf("/");
		String URL = starturls.substring(0, endurl2);

		// ////////////////////////
		// ////get 000001 jishuqi

		int endurl3 = starturls.indexOf(".tif");
		String jishuqi1 = starturls.substring(endurl2, endurl3);
		String jishuqi = jishuqi1.substring(0, jishuqi1.length() - 1);

		// ////
		// insert into database
		// int page=Integer.parseInt(pages);
		 * 
		 */
		String URL="",pages="";
		Pattern p1 = Pattern.compile("\\('.*[fm,xx].*','(\\d{1,3})','(.*)'\\)");
		Matcher m1 = p1.matcher(source);
		boolean result = m1.find();
		if (result) {
			pages=m1.group(1);
			URL = m1.group(2);
			// app_codetmp = m1.group(1);
			//				
		} else {
			// app_code.addElement("11111111");
			pages="0";
			URL ="http://www.npats.com";
		}
		//URL = URL + jishuqi + "1.tif";
		
		return URL + "-" + pages;
	}

	public String getPoweredurl() {
//		books/sd/2009/20090610/200710055529.3/000001.tif
//		books/fm/2008/20080507/200710055529/000001.tif
//		    System.out.println(source);
		    
			String URL="",pages="";
			Pattern p1 = Pattern.compile("\\('.*sd.*','(\\d{1,3})','(.*)'\\)");
//			Pattern p1 = Pattern.compile("\\('.*[sd,xx].*','(\\d{1,3})','(.*)'\\)");
			Matcher m1 = p1.matcher(source);
			boolean result = m1.find();
			if (result) {
				pages=m1.group(1);
				URL = m1.group(2);
				// app_codetmp = m1.group(1);
				//				
			} else {
				// app_code.addElement("11111111");
				pages="0";
				URL ="http://www.npats.com";
			}
			/*
			int oppsitepage = "javascript:browsetif('sd','".length();
			String substringpage = source.substring(startpage + oppsitepage);
			int endpage = substringpage.indexOf("'");

			String pages = substringpage.substring(0, endpage);
			int starturl = substringpage.indexOf("http");
			String substringurl = substringpage.substring(starturl);
			int endurl = substringurl.indexOf("'");
			String starturls = substringurl.substring(0, endurl);
			System.out.println(pages);
			System.out.println(starturls);

			// //// get http://211.157.104.92/books/fm/2006/2238/200610009815
			// URL
			int endurl2 = starturls.lastIndexOf("/");
			String URL = starturls.substring(0, endurl2);

			// ////////////////////////
			// ////get 000001 jishuqi

			int endurl3 = starturls.indexOf(".tif");
			String jishuqi1 = starturls.substring(endurl2, endurl3);
			String jishuqi = jishuqi1.substring(0, jishuqi1.length() - 1);

			// ////
			// insert into database
			// int page=Integer.parseInt(pages);
			URL = URL + jishuqi + "1.tif";
			*/
			System.out.println(URL);
			return URL + "-" + pages;
		
	}
}
