package edu.zju.cims201.GOF.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.zju.cims201.GOF.util.ParsePatent_sipo;
import edu.zju.cims201.GOF.util.ProxyUtil;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.codec.TIFFDirectory;
import com.sun.media.jai.codecimpl.TIFFImageDecoder;

public class Tifdownload {

	Document document;

	PdfContentByte cb;

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
	
	public void parserPatentType(String getString, Vector patent_name_V) {
		Pattern p1 = Pattern.compile("leixin=(.*?)&");
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
	public Tifdownload() {

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

	public void downLoadtifByList(
			 String savepath) throws Exception {
		String Url = null;
		document = new Document();
		FileOutputStream fo=	new FileOutputStream(savepath);
		PdfWriter writer = PdfWriter.getInstance(document, fo);
		document.open();
		cb = writer.getDirectContent();
		System.out.println("开始下载专利全文图片资源，可能会花费几秒到几分钟");
		for (int i = 0; i < vDownLoad.size(); i++) {
			
			Url = (String) vDownLoad.get(i);
			// filename = (String) vFileList.get(i);
			//System.out.println("url=" + Url);
			for(int j = 0; j<20; j++){
				if(downLoadSuccessed(Url)){
					//System.out.println("第"+(j+1)+"次"+"下载资源[" + Url + "]下载成功!!!");
					break;
				}else{
					//System.out.println("第"+(j+1)+"次"+"下载资源[" + Url + "]下载失败!!!");					
					if(downLoadSuccessed(Url)){
						//System.out.println("第"+(j+1)+"次"+"下载资源[" + Url + "]下载成功!!!");
						break;	
					}					
				}
				
			}
			

			//			
		}
		document.close();
		fo.close();
	//	rech.close();

		if (DEBUG) {
			

		}
	}

	public boolean downLoadSuccessed(String Url){
		boolean successed = true;
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
			//	System.out.println(percent);
			// System.out.println(pos);
			img.scalePercent(percent);
			img.setAbsolutePosition(0, pos);
			// System.out.println("Image: " + i);
			
			cb.addImage(img);
			document.newPage();
			stream.close();
			// file.delete();
			httpUrl.disconnect();
			

		}catch(Exception err){
			successed = false;
		}
		return successed;
	}
	
	public void downLoadjpgByList(
			 String savepath) throws Exception {
		String Url = null;
		document = new Document();
		FileOutputStream fo=	new FileOutputStream(savepath);
		PdfWriter writer = PdfWriter.getInstance(document, fo);
		document.open();
		cb = writer.getDirectContent();
        System.out.println("开始下载图片");
		for (int i = 0; i < vDownLoad.size(); i++) {
				Url = (String) vDownLoad.get(i);
				// filename = (String) vFileList.get(i);

	            System.out.println("当前url="+Url);

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
		
				Image img = Image.getInstance(url);

				float h = img.height();
				float w = img.width();
				// h= img.getBorderWidthBottom();
				// w= img.getBorderWidthLeft();

				img.setAlignment(Image.ALIGN_CENTER);

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
			
				// pos=10;
				img.setAbsolutePosition(X, Y);
			//	System.out.println("Image: " + i);

				cb.addImage(img);
				document.newPage();
				stream.close();
				// file.delete();
				httpUrl.disconnect();
			//			
		}
		
		document.close();
		fo.close();
	//	rech.close();

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

}
