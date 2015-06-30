package edu.zju.cims201.GOF.util;

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

import org.apache.commons.lang.StringUtils;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.codec.TIFFDirectory;
import com.sun.media.jai.codecimpl.TIFFImageDecoder;

public class UStifdownload {

	Document document;

	PdfContentByte cb;

	// Inventors:

	public String Srch1(String getString) {
		Pattern p1 = Pattern.compile("Srch1\".+?>");
		Matcher m1 = p1.matcher(getString);
		String Srch1temp = "";
		String Srch1 = null;
		boolean result = m1.find();
		if (result) {
			// name_V.addElement(m1.group(0));
			Srch1temp = m1.group(0);
			String Srch1tmp1[] = Srch1temp.split("VALUE=\"");
			Srch1 = Srch1tmp1[1];
			Srch1tmp1 = Srch1.split("\"");
			Srch1 = Srch1tmp1[0];
//			System.out.println("docid：" + Srch1);
		} else {
			
			Srch1 = "无";
			System.out.println(Srch1);
		}
		return Srch1;
	}

	public String docid(String getString1) {
		Pattern p1 = Pattern.compile("docid[^%]+?\">");
		Matcher m1 = p1.matcher(getString1);
		String docidtmp = "";
		String docid = null;
		boolean result = m1.find();
		if (result) {
			// name_V.addElement(m1.group(0));
			docidtmp = m1.group(0);
			String docidtmp1[] = docidtmp.split("\"");
			docid = docidtmp1[1];
//			System.out.println("docid：" + docid);
		} else {
			// Pub_date_V.addElement("11111111");
			docid = "无";
			System.out.println(docid);
		}
		return docid;
	}

	public String IDkey(String getString1) {
		Pattern p1 = Pattern.compile("IDKey[^%]+?\">");
		Matcher m1 = p1.matcher(getString1);
		String IDkeytmp = "";
		String IDkey = null;
		boolean result = m1.find();
		if (result) {
			// name_V.addElement(m1.group(0));
			IDkeytmp = m1.group(0);
			String IDkeytmp1[] = IDkeytmp.split("\"");
			IDkey = IDkeytmp1[1];
//			System.out.println("IDKey：" + IDkey);
		} else {
			// Pub_date_V.addElement("11111111");
			IDkey = "无";
//			System.out.println(IDkey);
		}
		return IDkey;
	}

	public String imagesurlurl(String getString1) {
		Pattern p1 = Pattern.compile("<embed.+?tif");
		Matcher m1 = p1.matcher(getString1);
		String imagesurltmp = "";
		String imagesurl = null;
		boolean result = m1.find();
		if (result) {
			// name_V.addElement(m1.group(0));
			imagesurltmp = m1.group(0);
			String imagesurltmp1[] = imagesurltmp.split("\"");
			imagesurl = imagesurltmp1[1];
			System.out.println("图片网页：" + imagesurl);
		} else {
			// Pub_date_V.addElement("11111111");
			imagesurl = "无";
			System.out.println(imagesurl);
		}
		return imagesurl;
	}

	public String images(String getString) {
		Pattern p1 = Pattern.compile("NumPages=[^=]+?-");
		Matcher m1 = p1.matcher(getString);
		String imagestmp = "";
		String images = null;
		boolean result = m1.find();
		if (result) {
			// name_V.addElement(m1.group(0));
			imagestmp = m1.group(0);
			// System.out.println("图片网页："+imagestmp);
			String imagestmp1[] = imagestmp.split("=");
			images = imagestmp1[1];
			imagestmp1 = images.split("-");
			images = imagestmp1[0];
			System.out.println("图片网页：" + images);

		} else {
			// Pub_date_V.addElement("11111111");
			images = "无";
			System.out.println(images);
		}
		return images;
	}

	public String imagepageurl(String getString) {
		Pattern p1 = Pattern.compile("Add.to.Shopping.Cart[^\\[]+?\\[");
		Matcher m1 = p1.matcher(getString);
		String imagepageurltmp = "";
		String imagepageurl = null;
		boolean result = m1.find();
		if (result) {
			// name_V.addElement(m1.group(0));
			imagepageurltmp = m1.group(0);
//			System.out.println("待解析内容：" + imagepageurltmp);
			String imagepageurltmp1[] = imagepageurltmp.split("href=");
			imagepageurl = imagepageurltmp1[1];
			imagepageurltmp1 = imagepageurl.split("><img");
			imagepageurl = imagepageurltmp1[0];
			System.out.println("图片网页：" + imagepageurl);

		} else {
			// Pub_date_V.addElement("11111111");
			imagepageurl = "无";
			System.out.println(imagepageurl);
		}
		return imagepageurl;
	}

	public String imagepageurl2(String getString) {
		Pattern p1 = Pattern.compile("Order.Copy[^\\[]+?\\[");
		Matcher m1 = p1.matcher(getString);
		String imagepageurltmp = "";
		String imagepageurl = null;
		boolean result = m1.find();
		if (result) {
			// name_V.addElement(m1.group(0));
			imagepageurltmp = m1.group(0);
	//		System.out.println("待解析内容：" + imagepageurltmp);
			String imagepageurltmp1[] = imagepageurltmp.split("href=");
			imagepageurl = imagepageurltmp1[1];
			imagepageurltmp1 = imagepageurl.split("><img");
			imagepageurl = imagepageurltmp1[0];
		//	System.out.println("图片网页：" + imagepageurl);

		} else {
			// Pub_date_V.addElement("11111111");
			imagepageurl = "无";
			System.out.println(imagepageurl);
		}
		return imagepageurl;
	}

	public String parserPub_date(String getString) {
		Pattern p1 = Pattern.compile("<B>United States Patent[^?]+?</TABLE>");
		Matcher m1 = p1.matcher(getString);
		// System.out.println("源头"+getString);
		String Pub_datetmp = "";
		String Pub_date = null;
		boolean result = m1.find();
		if (result) {
			// name_V.addElement(m1.group(0));
			Pub_datetmp = m1.group(0);

			String Pub_datetmp1[] = Pub_datetmp.split("<B>");
			Pub_date = Pub_datetmp1[(Pub_datetmp1.length - 1)];
			System.out.println("源头" + (Pub_datetmp1.length - 1) + Pub_date);

			Pub_datetmp1 = Pub_date.split("</B>");
			Pub_date = Pub_datetmp1[0];
			Pub_date = Pub_date.trim();
//			System.out.println(Pub_date);

		} else {
			// Pub_date_V.addElement("11111111");
			Pub_date = "无";
			System.out.println(Pub_date);
		}
		
		return Pub_date;
	}

	public String parserAgent(String getString) {
		Pattern p1 = Pattern.compile("Attorney,.Agent.or.Firm:[^:]+?<BR>");
		Matcher m1 = p1.matcher(getString);
		String Agenttmp = "";
		String Agent = null;
		boolean result = m1.find();
		if (result) {
			// name_V.addElement(m1.group(0));
			Agenttmp = m1.group(0);

			String Agenttmp1[] = Agenttmp.split("</I>");
			Agent = Agenttmp1[1];
			Agenttmp1 = Agent.split("<BR>");
			Agent = Agenttmp1[0];
			Agenttmp = Agent.replaceAll("<coma>", "");
			Agent = Agenttmp;
//			System.out.println(Agent);

		} else {
			// Agent_V.addElement("11111111");
			Agent = "无";
			System.out.println(Agent);
		}
		return Agent;
	}

	public String parserIPC(String getString) {
		Pattern p1 = Pattern.compile("Current.International.Class:.+?</TR>");
		Matcher m1 = p1.matcher(getString);
		String IPCtmp = "";
		String IPC = null;
		boolean result = m1.find();
		if (result) {
			// name_V.addElement(m1.group(0));
			IPCtmp = m1.group(0);
			Pattern p2 = Pattern.compile("<TD.+?</TD>");
			Matcher m2 = p2.matcher(IPCtmp);
			boolean result2 = m2.find();
			if (result2) {
				IPC = m2.group(0);
				String IPCtmp1[] = IPC.split(">");
				IPC = IPCtmp1[1];
				IPCtmp1 = IPC.split("<");
				IPC = IPCtmp1[0];
//				System.out.println(IPC);
			} else {
				IPC = "";
				System.out.println(IPC);
			}
		} else {
			// IPC_V.addElement("11111111");
			IPC = "";
			System.out.println(IPC);
		}
		return IPC;
	}

	public String parserIPC2(String getString) {
		Pattern p1 = Pattern.compile("International Class:.+?</TR>");
		Matcher m1 = p1.matcher(getString);
		String IPCtmp = "";
		String IPC = null;
		boolean result = m1.find();
		if (result) {

			IPCtmp = m1.group(0);
//			System.out.println("?????IPC=" + IPCtmp);
			Pattern p2 = Pattern.compile("<TD.+?</TD>");
			Matcher m2 = p2.matcher(IPCtmp);
			boolean result2 = m2.find();
			if (result2) {
				IPC = m2.group(0);
				try {
//					System.out.println("?????222IPC=" + IPC);
					IPC = IPC.substring(IPC.indexOf(">") + 1, IPC
							.lastIndexOf("<"));
				} catch (Exception e) {
					e.printStackTrace();
					IPC = "";
				}
//				System.out.println("?????IPC=" + IPC);
			} else {
				IPC = "";
				System.out.println(IPC);
			}
		} else {
			// IPC_V.addElement("11111111");
			IPC = "";
			System.out.println(IPC);
		}
		return IPC;
	}

	// public String parserApp_Person(String getString) {
	// String Inventor="无";
	// try{
	// String
	// temp=getString.substring(getString.indexOf("Assignee:"),getString.indexOf("Appl.
	// No."));
	// Inventor=temp;
	// // //name_V.addElement(m1.group(0));
	// // Inventortmp = m1.group(0);
	// // Pattern p2 = Pattern.compile("<B>.+<BR>");
	// // Matcher m2 = p2.matcher(Inventortmp);
	// // boolean result2 = m2.find();
	// // if (result2) {Inventor=m2.group(0);
	// // String Inventortmp1[]=Inventor.split("<BR>");
	// // Inventor=Inventortmp1[0];
	// // System.out.println(Inventor);
	// // } else {
	// // Inventor= "无";System.out.println(Inventor);
	// // }
	// // } else {
	// // //Inventor_V.addElement("11111111");
	// // Inventor= "无";
	// // System.out.println(Inventor);
	// }catch(Exception e){e.printStackTrace();}
	// return Inventor;
	// }
	public String parserApp_Person(String getString) {
		Pattern p1 = Pattern.compile("Assignee.+?</TR>");
		Matcher m1 = p1.matcher(getString);
		String Inventortmp = "";
		String Inventor = null;
		boolean result = m1.find();
		if (result) {
			// name_V.addElement(m1.group(0));
			Inventortmp = m1.group(0);
			Pattern p2 = Pattern.compile("<B>.+<BR>.*</TD>");
			Matcher m2 = p2.matcher(Inventortmp);
			boolean result2 = m2.find();
			if (result2) {
				Inventor = m2.group(0);
				String Inventortmp1[] = Inventor.split("</TD>");
				Inventor = Inventortmp1[0];

				Inventor = Inventor.replaceAll("<BR>", ";");
				Inventor = Inventor.replaceAll(";;", ";");
				Inventor = Inventor.replaceAll("<B>", "");
				Inventor = Inventor.replaceAll("</B>", "");
//				System.out.println(Inventor);
			} else {
				Inventor = "";
				System.out.println(Inventor);
			}
		} else {
			// Inventor_V.addElement("11111111");
			Inventor = "";
			System.out.println(Inventor);
		}
		return Inventor;
	}

	public String parserInventor(String getString) {
		Pattern p1 = Pattern.compile("Inventors:.+?</TR>");
		Matcher m1 = p1.matcher(getString);
		String Inventortmp = "";
		String Inventor = null;

		boolean result = m1.find();
		if (result) {
			// name_V.addElement(m1.group(0));
			Inventortmp = m1.group(0);
			//Inventors:</TH> <TD ALIGN="LEFT" WIDTH="90%"> <B>Wikstrom; Jon P.</B> (Eagan, MN)<B>, Schiller; Dan</B> (Woodbury, MN)<B>, Turner; Jon R.</B> (Edina, MN) </TD> </TR>
			Pattern p2 = Pattern.compile("<B>.+</B>");
			Matcher m2 = p2.matcher(Inventortmp);
			boolean result2 = m2.find();
			if (result2) {
				Inventor = m2.group(0);
				Inventor = Inventor.replaceAll("<B>","");
				Inventor = Inventor.replaceAll("</B>","");
//				System.out.println(Inventor);
			} else {
				Inventor = "无";
				System.out.println(Inventor);
			}

		} else {
			// Inventor_V.addElement("11111111");
			Inventor = "无";
			System.out.println(Inventor);
		}

		return Inventor;
	}

	public String parserAbstract(String getString) {
		Pattern p1 = Pattern.compile("Abstract.+?</P>");
		Matcher m1 = p1.matcher(getString);
		String abstrat1tmp = "";
		String abstrat1 = null;

		boolean result = m1.find();
		if (result) {
			// name_V.addElement(m1.group(0));
			abstrat1tmp = m1.group(0);
			String abstrat1tmp1[] = abstrat1tmp.split("<P>");
			abstrat1tmp = abstrat1tmp1[1];
			abstrat1tmp1 = abstrat1tmp.split("</P>");

			abstrat1 = abstrat1tmp1[0];
			// name_V.add(i, abstrat1tmp);
//			System.out.println(abstrat1);
			//				
		} else {
			// abstrat1_V.addElement("11111111");
			abstrat1 = "无";
			System.out.println(abstrat1);
		}
		return abstrat1;
	}

	public String parserpname(String getString) {
        String pname="";
        	pname=getString.substring(getString.indexOf("<HR>       <font size=\"+1\">"));
//        	System.out.println("pname1 ="+pname);
        	pname=pname.substring(0,pname.indexOf("</font><BR>       <BR><CENTER><B>Abstract</B>"));
//        	System.out.println("pname2 ="+pname);
        	pname=pname.replace("<HR>       <font size=\"+1\">","");
//        	System.out.println("pname3 ="+pname);
        	pname=pname.replaceAll("<B><I>", " ");
        	pname=pname.replaceAll("</I></B>", " ");
        	pname=pname.replaceAll("  ", " ");
        	pname=pname.trim();
		return pname;
	}

	public String parserpid(String getString) {

		String pid = "";
		pid = getString
				.substring(0, getString.indexOf("</TITLE></HEAD><BODY "));
		pid = pid.substring(pid.indexOf("<BASE TARGET=\"_top\"><TITLE>"));
		pid=pid.replace("<BASE TARGET=\"_top\"><TITLE>", "");
		pid=pid.replace("United States Patent:", "");
		pid=pid.trim();
		return pid;
	}

	public String parserUsCode(String getString) {
		Pattern p1 = Pattern.compile("Appl..No.:.+?</TR>");
		Matcher m1 = p1.matcher(getString);
		String app_codetmp = "";
		String app_code = null;
		boolean result = m1.find();
		if (result) {
			// name_V.addElement(m1.group(0));
			app_codetmp = m1.group(0);
			String app_codetmp1[] = app_codetmp.split("<B>");
			app_codetmp = app_codetmp1[1];
			app_codetmp1 = app_codetmp.split("</B>");

			app_code = app_codetmp1[0];
			// name_V.add(i, app_codetmp);
//			System.out.println(app_code);
			//				
		} else {
			// app_code_V.addElement("11111111");
			app_code = "无";
			System.out.println(app_code);
		}
		return app_code;
	}

	public String parserUsApp_date(String getString) {
		Pattern p1 = Pattern.compile("Filed:.+?</TR>");
		Matcher m1 = p1.matcher(getString);
		String app_datetmp = "";
		String app_date = null;
		boolean result = m1.find();
		if (result) {
			// name_V.addElement(m1.group(0));
			app_datetmp = m1.group(0);
			String app_datetmp1[] = app_datetmp.split("<B>");
			app_datetmp = app_datetmp1[1];
			app_datetmp1 = app_datetmp.split("</B>");

			app_date = app_datetmp1[0];
			// name_V.add(i, app_datetmp);
			System.out.println(app_date);
			//				
		} else {
			// app_date_V.addElement("11111111");
			app_date = "无";
			System.out.println(app_date);
		}
		return app_date;
	}

	public void parserName(String getString, Vector name_V, Vector href_V) {

		Pattern p1 = Pattern.compile("<TABLE>.+PAT..NO.?.{1,}</A></TD>?");
		Matcher m1 = p1.matcher(getString);
		// System.out.println(getString);
		String name = "";
		String nametemp = "";
		String nametemp1[] = null;
		boolean result = m1.find();
		boolean result1;
		name = m1.group(0);
		// System.out.println(1111);
		// System.out.println(name);
//		System.out.println(1111);
		if (result) {
			// System.out.println(2222222);

			name = m1.group(0);
			// System.out.println("第一次匹配结果："+name);
			Pattern p2 = Pattern.compile("<A.+/A>");
			Matcher m2 = p2.matcher(name);
			result1 = m2.find();
			if (result1) {
				// System.out.println(3333333);
				name = m2.group(0);
				// System.out.println("第二次匹配结果："+name);
				// name =name .replace("S=frig>","");
				// name p=name .replace("</A><","");
				String name1[];
				// System.out.println(spliter);
				// System.out.println(name);
				// name1=name.split(spliter.trim()+">"); </TD><TD
				// valign=baseline><IMG border=0 src="/netaicon/PTO/ftext.gif"
				// alt="Full-Text"></TD><TD valign=top>
				name1 = name.split("</TD><TD valign=top>");
				// System.out.println(spliter);
				// System.out.println("匹配容器长度"+name1.length);
				// System.out.println(name1);
				for (int i = 0; i < name1.length; i += 1) {
					// System.out.println("进入循环"+i);
					nametemp = name1[i];
					// String name2[]=null;
					// nametemp1=nametemp.split("</A>");
					nametemp1 = nametemp.split("</TD><TD valign=baseline>");

					// System.out.println(name1[i]);
					String temp = nametemp1[0];
					nametemp1 = temp.split("</A>");
					// System.out.println("nametemp1[0]"+nametemp1[0]);
					nametemp1 = nametemp1[0].split("\\?");
					// System.out.println("nametemp1[1]"+nametemp1[1]);
					nametemp1 = nametemp1[1].split(">");
					href_V.add(nametemp1[0]);
					// System.out.println("nametemp1[1]"+nametemp1[0]);
					// name2[0]=nametemp1[0];
					// name2[1]=nametemp1[1];
					// name2[1]=nametemp1[1].replaceAll(" ", "");
					// name2[0]=nametemp1[0].replaceAll("&quot;", "\"");
					// System.out.println(nametemp1[0]);
					// System.out.println(nametemp1[1]);
					// href_V.add(i,name2[0]);
					//
					name_V.add(i, nametemp1[1]);

					// System.out.println(name1[i]);
					// System.out.println(patent_name_Vtmp.elementAt(i));
				}

			} else {
				System.out.println("NoNoNo!!!!");
			}
			// name_V1.add(i, app_codetmp);
			// System.out.println(app_codetmp);
			//				
		} else {
			System.out.println("NoNoNo!!!!");
		}

	}

	public void parserName2(String getString, Vector name_V, Vector href_V) {

		// Pattern p1 = Pattern.compile("<TABLE>.+PAT..NO..?.{1,}</A></TD>?");
		// Matcher m1 = p1.matcher(getString);
		// System.out.println(getString);
		String name = "";
		String nametemp = "";
		String nametemp1[] = null;
		// boolean result = m1.find();
		boolean result1;
		// name = m1.group(0);
		// //System.out.println(1111);
		// //System.out.println(name);
		// System.out.println(1111);
//		System.out.println(getString);
		System.out.println("start="
				+ getString.indexOf("</FORM><TABLE><TR><TD></TD><TD>"));
		System.out.println("end=" + getString.indexOf("</A></TD></TR></TABLE>")
				+ 22);

		name = getString.substring(getString
				.indexOf("</FORM><TABLE><TR><TD></TD><TD>"), getString
				.indexOf("</A></TD></TR></TABLE>") + 22);
		// System.out.println("第一次匹配结果："+name);
		Pattern p2 = Pattern.compile("<A.+/A>");
		Matcher m2 = p2.matcher(name);
		result1 = m2.find();
		if (result1) {
			// System.out.println(3333333);
			name = m2.group(0);
			// System.out.println("第二次匹配结果："+name);
			// name =name .replace("S=frig>","");
			// name p=name .replace("</A><","");
			String name1[];
			// System.out.println(spliter);
			// System.out.println(name);
			// name1=name.split(spliter.trim()+">"); </TD><TD
			// valign=baseline><IMG border=0 src="/netaicon/PTO/ftext.gif"
			// alt="Full-Text"></TD><TD valign=top>
			name1 = name.split("</TD><TD valign=top>");
			// System.out.println(spliter);
			// System.out.println("匹配容器长度"+name1.length);
			// System.out.println(name1);
			for (int i = 0; i < name1.length; i += 1) {
				// System.out.println("进入循环"+i);
				nametemp = name1[i];
				// String name2[]=null;
				// nametemp1=nametemp.split("</A>");
				nametemp1 = nametemp.split("</TD><TD valign=baseline>");

				// System.out.println(name1[i]);
				String temp = nametemp1[0];
				nametemp1 = temp.split("</A>");
				// System.out.println("nametemp1[0]"+nametemp1[0]);
				nametemp1 = nametemp1[0].split("\\?");
				// System.out.println("nametemp1[1]"+nametemp1[1]);
				nametemp1 = nametemp1[1].split(">");
				href_V.add(nametemp1[0]);
				// System.out.println("nametemp1[1]"+nametemp1[0]);
				// name2[0]=nametemp1[0];
				// name2[1]=nametemp1[1];
				// name2[1]=nametemp1[1].replaceAll(" ", "");
				// name2[0]=nametemp1[0].replaceAll("&quot;", "\"");
				// System.out.println(nametemp1[0]);
				// System.out.println(nametemp1[1]);
				// href_V.add(i,name2[0]);
				//
				name_V.add(i, nametemp1[1]);

				// System.out.println(name1[i]);
				// System.out.println(patent_name_Vtmp.elementAt(i));
			}

		} else {
			System.out.println("NoNoNo!!!!");
		}
		// name_V1.add(i, app_codetmp);
		// System.out.println(app_codetmp);
		//				

	}

	public String parserKeyword(String getString) {
		// Query" VALUE="&quot;hot water&quot; AND air">
		Pattern p1 = Pattern.compile("Query\"[^>]+>");
		Matcher m1 = p1.matcher(getString);
		String keyword = "";
		String keywordtemp = "";
		// String keywordtemp1[]=null;
		boolean result = m1.find();
		// boolean result1;
		keyword = m1.group(0);
		// System.out.println(1111);
		// System.out.println(keyword);
		// System.out.println(1111);
		if (result) {
			// System.out.println("keyword有结果");
			// System.out.println(2222222);
			keyword = m1.group(0);
			System.out.println("匹配结果：" + keyword);
		} else {
			System.out.println("NoNoNo!!!!");
		}
		String[] keyword1 = keyword.split("VALUE=\"");
		// System.out.println(keyword);
		keyword = keyword1[1];
		keyword1 = keyword.split("\"");
		keyword = keyword1[0];
		// System.out.println(keyword);
		keywordtemp = keyword.replaceAll(" ", "\\+");
		// System.out.println(keyword);
		keyword = keywordtemp.replaceAll("&quot;", "%22");
//		System.out.println(keyword);
		return keyword;
	}
	public String parserS1(String getString) {
		
		String s1=getString.substring(getString.indexOf("&s1=")+4);
		s1=s1.substring(0,s1.indexOf("&"));
		
		return s1;
	}
	
	public String parserS2(String getString) {
		String s2="";
		try{
			if(getString.indexOf("&s2=")!=-1){
			s2=getString.substring(getString.indexOf("&s2=")+4);
			s2=s2.substring(0,s2.indexOf("&"));
		
			}
			
		}catch(Exception e){}
		return s2;
	}
	public String parserKeyword2(String getString) {
		// Query" VALUE="&quot;hot water&quot; AND air">
		Pattern p1 = Pattern.compile("Query\"[^>]+>");
		Matcher m1 = p1.matcher(getString);
		String keyword = "";
		String keywordtemp = "";
		// String keywordtemp1[]=null;
		boolean result = m1.find();
		// boolean result1;
		keyword = m1.group(0);
		// System.out.println(1111);
		// System.out.println(keyword);
		// System.out.println(1111);
		if (result) {
			// System.out.println("keyword有结果");
			// System.out.println(2222222);
			keyword = m1.group(0);
			System.out.println("匹配结果：" + keyword);
		} else {
			System.out.println("NoNoNo!!!!");
		}
		String[] keyword1 = keyword.split("value=\"");
		// System.out.println(keyword);
		keyword = keyword1[1];
		keyword1 = keyword.split("\"");
		keyword = keyword1[0];
		// System.out.println(keyword);
		keywordtemp = keyword.replaceAll(" ", "\\+");
		// System.out.println(keyword);
		keyword = keywordtemp.replaceAll("&quot;", "%22");
		System.out.println(keyword);
		return keyword;
	}

	/*
	 * public void parserName1(String getString, Vector name_V,String spliter) {
	 * 
	 * Pattern p1 = Pattern.compile("<TABLE>.+PAT..NO.?.{1,}</A></TD>?");
	 * Matcher m1 = p1.matcher(getString); String name = ""; String nametemp="";
	 * String nametemp1[]=null; boolean result = m1.find(); boolean result1;
	 * name = m1.group(0); //System.out.println(1111);
	 * //System.out.println(name); System.out.println("patentname解析开始"); String
	 * nameS[]=name.split("</A"); System.out.println("一次分割"+nameS.length);
	 * Pattern p2 = Pattern.compile(">[^<>]{1,}</"); //name = m1.group(1); for
	 * (int i =0; i <nameS.length; i += 1) { System.out.println(nameS[i]);
	 * String[] name1 = m2.group(i).split(">"); //String name2[]=null;
	 * name=(name1[0].split(">"))[0]; System.out.println("这是匹配结果"+i+name);
	 * name=name.replaceAll(" ", ""); name_V.add(i,name); }
	 * 
	 * 
	 * System.out.println("第一次匹配结果："+name); Pattern p2 = Pattern.compile(">[^<>]{1,}</");
	 * Matcher m2 = p2.matcher(name); result1=m2.find();
	 * 
	 * if (result1) { System.out.println(3333333); name = m2.group(0);
	 * 
	 * System.out.println("二次匹配结束"+name+m2.end()); for (int i =0; i <m2.end(); i +=
	 * 1) {
	 * 
	 * String[] name1 = m2.group(i).split(">"); //String name2[]=null;
	 * name=(name1[0].split(">"))[0]; System.out.println("这是匹配结果"+i+name);
	 * name=name.replaceAll(" ", ""); name_V.add(i,name); } } else {
	 * System.out.println("NoNoNo!!!!"); } //name_V1.add(i, app_codetmp);
	 * //System.out.println(app_codetmp);
	 * 
	 *  }
	 */
	public void parserAppcode(String getString, Vector app_code_V) {
		Pattern p1 = Pattern.compile("(CN.*?)&");
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

	public String parserTotalpage(String getString) {
		String totalpages = null;
		// System.out.println(getString);
		Pattern p1 = Pattern.compile("of..strong>.+[0-9]<");
		Matcher m1 = p1.matcher(getString);
		boolean result = m1.find();
		if (result) {
			// System.out.println("matcher"+m1);
			totalpages = m1.group(0);
			String totalpages1[] = null;
			totalpages1 = totalpages.split("<strong>");
			totalpages = totalpages1[1];
			totalpages1 = totalpages.split("<");
			totalpages = totalpages1[0];
			System.out.println("总页数" + totalpages);
		} else {
			System.out.println("总页数总页数总页数总页数总页数总页数总页数总页数总页数");
		}

		return totalpages;
	}

	public void parserPatentName(String getString, Vector patent_name_V) {
		Pattern p1 = Pattern.compile("title=(.*?)&");
		Matcher m1 = p1.matcher(getString);
		String patent_nametmp = "", bodytmp = "";
		do {
			boolean result = m1.find();
			if (result) {
				patent_name_V.addElement(m1.group(1));
				patent_nametmp = m1.group(1);
				// System.out.println("patent_nametmp="+patent_nametmp);
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

	public String parserfmRecord(String getString) {
		Pattern p1 = Pattern.compile("发明专利.*\\s*.*(\\d{1,9})\\s*</b>");
		Matcher m1 = p1.matcher(getString);
		String fmRecord = "";
		boolean result = m1.find();
		if (result) {
			fmRecord = m1.group(0);
			Pattern p2 = Pattern.compile("\\d{1,9}");
			Matcher m2 = p2.matcher(fmRecord);
			boolean result2 = m2.find();
			if (result2) {
				fmRecord = m2.group();
			} else {
				fmRecord = "0";
			}
		} else {
			fmRecord = "0";
		}
		return fmRecord;

	}

	public String parserxxRecord(String getString) {
		Pattern p1 = Pattern.compile("实用新型.*\\s*.*(\\d{1,9})\\s*</b>");
		Matcher m1 = p1.matcher(getString);
		String xxRecord = "";
		boolean result = m1.find();
		if (result) {
			xxRecord = m1.group(0);
			Pattern p2 = Pattern.compile("\\d{1,9}");
			Matcher m2 = p2.matcher(xxRecord);
			boolean result2 = m2.find();
			if (result2) {
				xxRecord = m2.group();
			} else {
				xxRecord = "0";
			}

		} else {
			xxRecord = "0";
		}
		return xxRecord;

	}

	public String parserwgRecord(String getString) {
		Pattern p1 = Pattern.compile("外观.*\\s*.*(\\d{1,9})\\s*</b>");
		Matcher m1 = p1.matcher(getString);
		String wgRecord = "";
		boolean result = m1.find();
		if (result) {
			wgRecord = m1.group(0);
			Pattern p2 = Pattern.compile("\\d{1,9}");
			Matcher m2 = p2.matcher(wgRecord);
			boolean result2 = m2.find();
			if (result2) {
				wgRecord = m2.group();
			} else {
				wgRecord = "0";
			}
		} else {
			wgRecord = "0";
		}
		return wgRecord;

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

	private Vector vDownLoad = new Vector();

	/**
	 * 构造方法
	 */
	public UStifdownload() {

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

	public void downLoadjpdfByList(String filename) throws Exception {
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
				// JPEGImageDecoder decoder =
				// JPEGCodec.createJPEGDecoder(fileIn);
				// JPGDirectory dir = new TIFFDirectory(stream, 0);
				Image img = Image.getInstance(url);

				float h = img.height();
				float w = img.width();
				// h= img.getBorderWidthBottom();
				// w= img.getBorderWidthLeft();

				img.setAlignment(Image.ALIGN_CENTER);

				System.out.println("h=" + h);
				System.out.println("w=" + w);
				// }catch(IOException e)
				// {
				//				
				// }

				// long IFDOffset = dir.getIFDOffset();

				float percent = 100;
				int X = 0;
				int Y = 0;
				if (w > 595 && h <= 842)
					percent = ((595 - 20) * 100 / w);

				if (h > 842 && w <= 595)
					percent = ((842 - 20) * 100 / h);

				if (h > 842 && w > 595) {
					float percent1 = ((842 - 20) * 100 / h);
					float percent2 = ((595 - 20) * 100 / w);
					percent = percent1 < percent2 ? percent1 : percent2;

				}

				X = (int) (595 - w * percent / 100) / 2;
				Y = (int) (842 - h * percent / 100) / 2;
				// System.out.println(percent);
				// System.out.println(Y);
				// Y = (int) (842 - h)/2;

				img.scalePercent(percent);
				System.out.println("percent=" + percent);
				System.out.println("X=" + X);
				System.out.println("Y=" + Y);
				// pos=10;
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
			// System.out.println("下载完成!!!");

		}
	}

	public void downLoadtpdfByList(String filename) throws Exception {
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
				if (w > 595)
					percent = ((595 + 18) * 100 / w);
				if (h > 842)
					pos = (int) (842 - h * percent / 100);
				else
					pos = (int) (842 - h);
				System.out.println(percent);
				// System.out.println(pos);
				img.scalePercent(percent);
				img.setAbsolutePosition(0, pos);
				// System.out.println("Image: " + i);

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
			// System.out.println("下载完成!!!");

		}
	}

	public void downLoadtifByList(HttpServletResponse response,
			String filenames, String sPatentName) throws Exception {
		String Url = null;
		String filename = "";
		// String filename = null;
		// OutputStream fos = response.getOutputStream();
		// 按列表顺序保存资源
		sPatentName = sPatentName.replaceAll("&nbsp;", "");
		/*
		 * if (sPatentName.startsWith("")) { try {
		 * sPatentName=sPatentName.substring(1); } catch (RuntimeException e) { //
		 * TODO Auto-generated catch block ///e.printStackTrace() return ; } }
		 */

		filenames = StringUtils.replace(filenames, ".", "_");
		filenames = filenames.trim();
		filename = filenames + ".pdf";
		response.reset();
		response.setCharacterEncoding("UTF-8");

		response.setContentType("application/pdf;charset=gb2312");

		filename = StringUtils.replace(filename, "?", "");

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
				if (w > 595)
					percent = ((595 + 18) * 100 / w);
				if (h > 842)
					pos = (int) (842 - h * percent / 100);
				else
					pos = (int) (842 - h);
				System.out.println(percent);
				// System.out.println(pos);
				img.scalePercent(percent);
				img.setAbsolutePosition(0, pos);
				// System.out.println("Image: " + i);

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
			// System.out.println("下载完成!!!");

		}
	}

	public void downLoadjpgByList(HttpServletResponse response,
			String filenames, String sPatentName) throws Exception {
		String Url = null;
		String filename = "";
		// String filename = null;
		// OutputStream fos = response.getOutputStream();
		// 按列表顺序保存资源
		sPatentName = sPatentName.replaceAll("&nbsp;", "");

		filenames = StringUtils.replace(filenames, ".", "_");
		filenames = filenames.trim();
		filename = filenames + ".pdf";
		response.reset();
		// System.out.println(response.getContentType());
		response.setCharacterEncoding("UTF-8");

		// System.out.println("@@@@@@@@@filename＝" + filename);
		response.setContentType("application/pdf;charset=gb2312");

		// filename = StringUtils.replace(filename, "+", "%20");
		filename = StringUtils.replace(filename, "?", "");

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
				// JPEGImageDecoder decoder =
				// JPEGCodec.createJPEGDecoder(fileIn);
				// JPGDirectory dir = new TIFFDirectory(stream, 0);
				Image img = Image.getInstance(url);

				float h = img.height();
				float w = img.width();
				// h= img.getBorderWidthBottom();
				// w= img.getBorderWidthLeft();

				img.setAlignment(Image.ALIGN_CENTER);

				System.out.println("h=" + h);
				System.out.println("w=" + w);
				// }catch(IOException e)
				// {
				//				
				// }

				// long IFDOffset = dir.getIFDOffset();

				float percent = 100;
				int X = 0;
				int Y = 0;
				if (w > 595 && h <= 842)
					percent = ((595 - 20) * 100 / w);

				if (h > 842 && w <= 595)
					percent = ((842 - 20) * 100 / h);

				if (h > 842 && w > 595) {
					float percent1 = ((842 - 20) * 100 / h);
					float percent2 = ((595 - 20) * 100 / w);
					percent = percent1 < percent2 ? percent1 : percent2;

				}

				X = (int) (595 - w * percent / 100) / 2;
				Y = (int) (842 - h * percent / 100) / 2;
				// System.out.println(percent);
				// System.out.println(Y);
				// Y = (int) (842 - h)/2;

				img.scalePercent(percent);
				System.out.println("percent=" + percent);
				System.out.println("X=" + X);
				System.out.println("Y=" + Y);
				// pos=10;
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
			// System.out.println("下载完成!!!");

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
		// System.out.println(getString);

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
		String sPatentName = "";
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
		// 20080529 陈国海 加专利名字
		ParsePatent_sipo parser = new ParsePatent_sipo(getString);
		sPatentName = parser.getName();
		// parser.

		// int pagenumber = Integer.parseInt(spagenumber);
		// Pattern p4 = Pattern.compile("/(d+).tif");
		// Matcher m4 = p4.matcher(newurl);
		reader.close();
		inReader.close();
		input.close();
		// Add the last segment of input to
		// the new String

		String[] flag = { newurl, spagenumber, patentcode, sPatentName };
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
		// System.getProperties().put("proxySet", "true");
		// System.getProperties().put("proxyHost", proxy);
		// System.getProperties().put("proxyPort", proxyPort);
		// ProxyUtil prox=new ProxyUtil();
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
	// public void getJPGDownloadInfor(HttpServletResponse response, String url,
	// String pagenumber, String patentcode,String sPatentName) {
	// //System.out.println("正在获取链接[]的内容...\n将其保存为文件[]");
	// Tifdownload oInstance = new Tifdownload();
	// String starturl = url.replaceFirst("000001.jpg", "");
	// System.out.println("starturl="+starturl);
	// try {
	// // 增加下载列表（此处用户可以写入自己代码来增加下载列表）
	// String oo = "000";
	// int i = Integer.parseInt(pagenumber);
	// for (int k = 1; k <= i; k++) {
	// if (k < 100) {
	// oo = "0000";
	// if (k < 10)
	// oo = "00000";
	// }
	// // System.out.println("urls="+starturl + oo + k + ".jpg");
	// oInstance.addItem(starturl + oo + k + ".jpg");
	// }
	//
	// // 开始下载
	// //System.out.println("正在获取链接[]的内容...\n将其保存为文件[]");
	// oInstance.downLoadjpgByList(response, patentcode,sPatentName);
	//
	// } catch (Exception err) {
	// System.out.println(err.getMessage());
	// }
	//
	// }
	// public void getTifDownloadInfor(HttpServletResponse response, String url,
	// String pagenumber, String patentcode,String sPatentName) {
	// //System.out.println("正在获取链接[]的内容...\n将其保存为文件[]");
	// Tifdownload oInstance = new Tifdownload();
	// String starturl = url.replaceFirst("000001.tif", "");
	// System.out.println("starturl="+starturl);
	// try {
	// // 增加下载列表（此处用户可以写入自己代码来增加下载列表）
	// String oo = "000";
	// int i = Integer.parseInt(pagenumber);
	// for (int k = 1; k <= i; k++) {
	// if (k < 100) {
	// oo = "0000";
	// if (k < 10)
	// oo = "00000";
	// }
	// // System.out.println("urls="+starturl + oo + k + ".jpg");
	// oInstance.addItem(starturl + oo + k + ".tif");
	// }
	//
	// // 开始下载
	// //System.out.println("正在获取链接[]的内容...\n将其保存为文件[]");
	// oInstance.downLoadBytifList( response,patentcode,sPatentName);
	//
	// } catch (Exception err) {
	// System.out.println(err.getMessage());
	// }
	// }
}
