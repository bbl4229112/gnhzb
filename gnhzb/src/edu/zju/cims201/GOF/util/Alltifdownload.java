package edu.zju.cims201.GOF.util;



import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Vector;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.media.jai.codec.SeekableStream;

import edu.zju.cims201.GOF.dao.patent.PatentDao;
import edu.zju.cims201.GOF.hibernate.pojo.Patent;
import edu.zju.cims201.GOF.service.patent.PatentService;
import edu.zju.cims201.GOF.service.patent.PatentServiceImpl;


public class Alltifdownload {

	Document document;

	PdfContentByte cb;

	
	public void setProxyServer() {
		// 设置代理服务器
	
		ProxyUtil.useHttpProxy();

	}

	public void tifcn(String dir, Patent patent, String savepath) {
		String cn_type = patent.getCnType();
		String fullurl = patent.getFullurltmp();
		//fullurl=fullurl.replace("http://", "");
	
		boolean iswaiguan = false;
		// 判断是否是外观，外观没有公开说明书，只有外观的图象，下载规律不同，因此要区分开来
		String filetype = "";
		if (cn_type.equals("3")) {
			filetype = "jpg";
			iswaiguan = true;
			fullurl=fullurl.replace("http://", "");
			fullurl=Constants.sipotexturl+fullurl;
		} else {
			if(fullurl.indexOf("http://")!=-1)
			{	fullurl=fullurl.replace("http://", "");
			fullurl=fullurl.substring(fullurl.indexOf("/")+1);
			}
			filetype = "tif";
			fullurl=Constants.sipotexturl+fullurl;
		}
		
	
    	System.out.println("fullurl="+fullurl);
		
		
		String pagenumber = patent.getPagenumber();

		Tifdownload tif = new Tifdownload();

		tif.setProxyServer();
		


		String starturl = fullurl.replaceFirst("000001.tif", "");
		System.out.println("starturl=" + starturl);

		String oo = "000";
		int i = Integer.parseInt(pagenumber);
		for (int k = 1; k <= i; k++) {
			if (k < 100) {
				oo = "0000";
				if (k < 10)
					oo = "00000";
			}

			tif.addItem(starturl + oo + k + "." + filetype);
		}

		// 开始下载

		try {
			System.out.println("开始下载到"+savepath);
			if (iswaiguan)
				tif.downLoadjpgByList(savepath);
			else {
				tif.downLoadtifByList(savepath);

			}
		} catch (Exception err) {
			System.out.println(err.getMessage());
		}

	}

	public void tifissuedcn(String dir, Patent patent, String savepath) {
		String powerFulTmp = patent.getPoweredfullurl();
		powerFulTmp=powerFulTmp.replace("http://", "");		
		powerFulTmp=powerFulTmp.substring(powerFulTmp.indexOf("/"));
		powerFulTmp=Constants.sipotexturl+powerFulTmp;
    	System.out.println("powerFulTmp"+powerFulTmp);
		String poweredpagenumber = patent.getPoweredpagenumber();

		Tifdownload tif = new Tifdownload();
		String filetype = "tif";

		String starturl = powerFulTmp.replaceFirst("000001.tif", "");

		String oo = "000";
		int i = Integer.parseInt(poweredpagenumber);
		for (int k = 1; k <= i; k++) {
			if (k < 100) {
				oo = "0000";
				if (k < 10)
					oo = "00000";
			}

			tif.addItem(starturl + oo + k + "." + filetype);
		}

		try {
			tif.downLoadtifByList(savepath);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

	}

	public void tifus(String dir, Patent patent, String savepath) throws IOException {
		
	String	poweredpagenumber = patent.getPagenumber();
	String	imagepageurl2 = patent.getFullurl();
		UStifdownload tif = new UStifdownload();
		tif.setProxyServer();
		UseHttpPost useHttpPost=new UseHttpPost();
		useHttpPost.setProxyServer();
		useHttpPost.setURL(imagepageurl2);
		String getString1=useHttpPost.getContent2();
	//	System.out.println("getString1="+getString1);
		if(imagepageurl2.indexOf("patft.uspto.gov")!=-1)
			imagepageurl2=tif.imagepageurl(getString1);
		else
			imagepageurl2=tif.imagepageurl2(getString1);
	//	System.out.println("imgesurl2="+imagepageurl2);
		useHttpPost.setURL(imagepageurl2);
		getString1=useHttpPost.getContent2();
		if(null==poweredpagenumber||poweredpagenumber.equals("无")||poweredpagenumber.equals(""))
		{	poweredpagenumber = tif.images(getString1);
        	patent.setPagenumber(poweredpagenumber);
        	//jiangdingding修改 2013-5-18
        	PatentService patentService = new PatentServiceImpl() ;
        	patentService.save(patent);
		}
		String Docid=tif.docid(getString1);
	    String	IDKey=tif.IDkey(getString1);
		String imgesurl1[]=imagepageurl2.split("\\/");
		imagepageurl2=imgesurl1[2];	
		String	Url = "http://" + imagepageurl2 + "/.DImg?" + "Docid="
				+ Docid + "&IDKey="+IDKey+"&ImgFormat=tif" + "&PageNum=";
	try {
		
		PdfContentByte cb;
	

		Document document = new Document();

		PdfWriter writer;
		FileOutputStream fo=	new FileOutputStream(savepath);
		// writer = PdfWriter.getInstance(document, rech);
		writer = PdfWriter.getInstance(document,fo );
		document.open();
		cb = writer.getDirectContent();

		int spagenumbertemp = Integer
				.parseInt(poweredpagenumber.trim());
		int j = spagenumbertemp;

		try {
			for (int i = 1; i <= j; i++) {
				
				String Urltemp = Url + i;
            //     System.out.println("url="+Urltemp);
				System.out.println("imgurl" + i + ":" + Urltemp);
			
				HttpURLConnection httpUrl = null;
				URL url1 = null;
				// byte[] buf = new byte[BUFFER_SIZE];
				// int size = 0;

				// 建立链接
				url1 = new URL(Urltemp);
				httpUrl = (HttpURLConnection) url1.openConnection();
				// 连接指定的资源
				httpUrl.connect();

				SeekableStream stream = null;
				stream = SeekableStream.wrapInputStream(httpUrl
						.getInputStream(), true);
			
				Image img = Image.getInstance(url1);

				float h = img.height();
				float w = img.width();

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
				

				img.scalePercent(percent);

				img.setAbsolutePosition(X, Y);
//				System.out.println("Image: " + i);

				cb.addImage(img);
				document.newPage();
				stream.close();
			
				httpUrl.disconnect();
		

			}document.close();
			fo.close();
		} catch (IOException err) {

			System.out.println("资源[" + Url + "]下载失败!!!");

		}

		
	} catch (Exception e) {
		// TODO 自动生成 catch 块
		e.printStackTrace();
	}
		

	}

	public void tifep(String dir, Patent patent, String savepath) {
		String imagesurl = patent.getFullurltmp();
        if(null!=imagesurl&&(!imagesurl.equals("null"))&&(!imagesurl.equals(""))){
		String[] urls = imagesurl.split("@@@@######@@@@@");

		System.out.println("size=" + urls.length);
		if (!(urls.length < 1)) {
			// String Url = null;

			document = new Document();
			// OutputStream rech = response.getOutputStream();
			// PdfWriter writer = PdfWriter.getInstance(document, rech);
			document.open();
			// cb = writer.getDirectContent();

			URL url = null;
			PdfCopy copy = null;
			try {
				String imageurl = "";

				imageurl = Paserepimageurl(urls[0]);

				url = new URL(imageurl + "&pageNumber=1");

				PdfReader reader = new PdfReader(url);
				int size = reader.getNumberOfPages();
				System.out.println("第1篇文章共有=" + size + "页");
				document = new Document(reader.getPageSize(1));
				copy = new PdfCopy(document, new FileOutputStream(savepath));

				document.open();

				document.newPage();
				PdfImportedPage page = copy.getImportedPage(reader, 1);
				copy.addPage(page);
				// cb.addPSXObject(page);
				System.out.println("第1篇文章第 1 页 success!");
				for (int i = 2; i <= size; i++) {
					url = new URL(imageurl + "&pageNumber=" + i);
					PdfReader reader2 = new PdfReader(url);
					document.newPage();
					PdfImportedPage page2 = copy.getImportedPage(reader2, i);
					copy.addPage(page2);

					System.out.println("第1篇文章第 " + i + "页 success!");
				}
				for (int u = 1; u < urls.length; u++) {

					imageurl = Paserepimageurl(urls[u]);
					url = null;
					url = new URL(imageurl + "&pageNumber=1");
					for (int i = 1; i <= size; i++) {

						url = new URL(imageurl + "&pageNumber=" + i);
						PdfReader reader2 = new PdfReader(url);
						if (i == 1) {
							size = reader2.getNumberOfPages();
							System.out.println("第" + u + "篇文章共有=" + size + "页");
						}
						document.newPage();
						PdfImportedPage page2 = copy
								.getImportedPage(reader2, i);
						copy.addPage(page2);

						System.out
								.println("第" + u + "篇文章第 " + i + "页 success!");
					}

				}
			} catch (IOException err) {

				System.out.println("资源[" + url + "]下载失败!!!");

				//			
			} catch (DocumentException e) {
				// TODO 自动生成 catch 块
				System.out.println("资源[" + url + "]下载失败!!!");
			}
			document.close();
		}}

	}

	public void tifjp(String dir, Patent patent, String savepath) throws HttpException, IOException {
		
		 //http://www4.ipdl.inpit.go.jp/Tokujitu/PAJdetail.ipdl?N0000=60&N0120=01&N2001=2&N3001=H08-258231
	     String url = patent.getFullurltmp(); 
	     if(!url.equals("full text is not available")){
	    	 
		
		        System.out.println("开始下载：\n ---"+url);
	     
		        Tifdownload2 parser = new Tifdownload2();
		        
		    	UseHttpPost useHttpPost =new UseHttpPost();
				useHttpPost.setProxyServer(); 
		        
		        
				HttpClient client = new HttpClient();

				//client.getState().setCookiePolicy(CookiePolicy.COMPATIBILITY);
				//System.setProperty("apache.commons.httpclient.cookiespec","compatibility");
				
				client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
				client.getParams().setParameter("http.protocol.single-cookie-header", true);

				// client.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
				client.getHostConfiguration().setHost("www19.ipdl.inpit.go.jp", 80,
						"http");

				// 初始化
				GetMethod getinit = new GetMethod("/PA1/cgi-bin/PA1INIT");
				client.executeMethod(getinit);

				String getString = getinit.getResponseBodyAsString();
				// System.out.println(getString);
				getinit.releaseConnection();
				
				// 解析第一次get得到的TempName,ResultId
				String tempName1 = parser.parserTempName(getString);
				String resultId1 = parser.parserResultId(getString);
				String reserve21 = parser.parserReserve2(getString);
				
				System.out.println(tempName1+"----"+resultId1+"----"+reserve21);

               //查询（按数字搜索--申请号）
				String maxCount = "1000";
				String pageCount = "50";
				String searchType = "10";
				String tempName = tempName1;
				String maxPage = "0000000000";
				String dispPage = "0000000000";
				String hitCount = "0";
				String cookieId = "2";
				String resultId = resultId1;
				String detailPage = "0000000000";
				String language = "ENG";
				String reserve1 = "";
				String reserve2 = reserve21;
				String reserve3 = "";
				String freeWord1 = "";
				String freeWord2 = "";
				String freeWord3 = "";
				String freeWordType1 = "";
				String freeWordType2 = "";
				String freeWordType3 = "";
				String derive = "";
				String ending = "";
				String startDate = "";
				String endDate = "";
				String iPC = "";
				String numberType = "0";
				String number = patent.getAppCode().substring(2);
               System.out.println("专利号："+number);
				PostMethod post = new PostMethod("/PA1/cgi-bin/PA1NUMBER");
				// 自动重试3次
			    //post以得到结果信息   
		       
		        post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
		                new DefaultHttpMethodRetryHandler());
		        
		        NameValuePair MaxCount = new NameValuePair("MaxCount",maxCount);
		        NameValuePair PageCount = new NameValuePair("PageCount",pageCount);
		        NameValuePair SearchType = new NameValuePair("SearchType",searchType );
		        NameValuePair TempName = new NameValuePair("TempName",tempName);
		        NameValuePair MaxPage = new NameValuePair("MaxPage",maxPage);
		        NameValuePair DispPage = new NameValuePair("DispPage",dispPage);
		        NameValuePair HitCount = new NameValuePair("HitCount",hitCount);
		        NameValuePair DetailPage = new NameValuePair("DetailPage",detailPage);
		        NameValuePair Language = new NameValuePair("Language",language);
		        NameValuePair Reserve1 = new NameValuePair("Reserve1",reserve1);
		        NameValuePair Reserve2 = new NameValuePair("Reserve2",reserve2);
		        NameValuePair Reserve3 = new NameValuePair("Reserve3",reserve3);
		        NameValuePair FreeWord1 = new NameValuePair("FreeWord1",freeWord1);
		        NameValuePair FreeWord2 = new NameValuePair("FreeWord2",freeWord2);
		        NameValuePair FreeWord3 = new NameValuePair("FreeWord3",freeWord3);
		        NameValuePair StartDate = new NameValuePair("StartDate",startDate);
		        NameValuePair EndDate = new NameValuePair("EndDate",endDate);
		        NameValuePair IPC = new NameValuePair("IPC",iPC);
		        NameValuePair NumberType = new NameValuePair("NumberType",numberType);
		        NameValuePair Number = new NameValuePair("Number",number);
		        NameValuePair FreeWordType1 = new NameValuePair("FreeWordType1",freeWordType1);
		        NameValuePair FreeWordType2 = new NameValuePair("FreeWordType2",freeWordType2);
		        NameValuePair FreeWordType3 = new NameValuePair("FreeWordType3",freeWordType3);
		        NameValuePair Derive = new NameValuePair("Derive",derive);
		        NameValuePair Ending = new NameValuePair("Ending",ending);
		        NameValuePair ResultId = new NameValuePair("ResultId",resultId);
		        NameValuePair CookieId = new NameValuePair("CookieId",cookieId);
		        
		        
		        
		      
		        	        
		        post.setRequestBody(new NameValuePair[] {MaxCount,PageCount,SearchType,TempName,MaxPage,DispPage,HitCount,ResultId,CookieId,DetailPage,
		        		Language,Reserve1,Reserve2,Reserve3,FreeWord1, FreeWord2, FreeWord3,	
		        		StartDate ,EndDate,IPC,NumberType,Number,FreeWordType1 ,FreeWordType2,FreeWordType3 ,Derive ,Ending});

			    client.executeMethod(post);
			    
	    
			   //打印服务器返回的状态
			    System.out.println(post.getStatusLine());

			 
			   // String results =post.getResponseBodyAsString();
			    //System.out.println("searchResult:"+results);

			   //从post得到的信息中解析出get的URL
				
		        //String listmain = parser.parserListMain(results);
		        //String listHead = parser.parserListHead(results);
		        
		       // System.out.println("listmain:--"+listmain);
		        
		       // post.releaseConnection();
			  	 
		        //获取结果信息
				//GetMethod getlistmain = new GetMethod(listmain);
		        //client.executeMethod(getlistmain);		        
		        //String getStringmain = getlistmain.getResponseBodyAsString();
				//System.out.println("listmainResult:--"+getStringmain);
									
			   
				client.getHostConfiguration().setHost("www4.ipdl.inpit.go.jp", 80, "http");
				String geturl = url.replaceAll("http://www4.ipdl.inpit.go.jp", "");
				System.out.println(geturl);
				GetMethod get1 = new GetMethod(geturl);
		       	client.executeMethod(get1);

		        String getStringdetail = get1.getResponseBodyAsString();
		        //System.out.println(getStringdetail);
		        get1.releaseConnection();
		        
		        
				String tjswhen = parser.parserTjswhen(getStringdetail);
		      //System.out.println("tjswhen:"+tjswhen);			
				
				
				String tjswhen2=tjswhen.replaceAll("http://www4.ipdl.inpit.go.jp", "");
				System.out.println("tjswhen:"+tjswhen2);	
				GetMethod get2 = new GetMethod(tjswhen2);
				
			    //get2.setRequestHeader(header);
				//get2.setRequestHeader("Cookie","ARPT=IMUNUNSALPCYQW;LADDR=60.12.143.5;ACSTM=20090219224033639459");
		        client.executeMethod(get2);
		        
		        String getString2 = get2.getResponseBodyAsString();
		      //System.out.println(getString2);
		        String tjitemidx= parser.parserTjitemidx(getString2);
		      //System.out.println("tjitemidx:"+tjitemidx);
		        
		      //String tjitemcnt= parser.parserTjitemcnt(getString2);
		     // System.out.println("tjitemcnt:"+tjitemcnt);
		        
		        String H_flg= parser.parserH_flg(getString2);
		        String DL= parser.parserDL(getString2);
		        String EL= parser.parserEL(getString2);
		        String PI= parser.parserPI(getString2);
		        String MID= parser.parserMID(getString2);
		        String K_flg= parser.parserK_flg(getString2);
		        
		        
		      // System.out.println("geturl:"+getUrl);
		        

		        
		        get2.releaseConnection();
		        
		        GetMethod get3 = new GetMethod(tjitemidx);
		        //get3.setRequestHeader("Cookie","ARPT=IMUNUNSALPCYQW;LADDR=60.12.143.5;ACSTM=20090219224033639459");
		        client.executeMethod(get3);
		        String getString3 = get3.getResponseBodyAsString();
		        //System.out.println(getString3);
		        
		        String N0005= parser.parserN0005(getString3);
		        
				Vector showFrame_V = new Vector();
				Vector showFrame_Vtmp = new Vector();
				
		        parser.parserShowFrames(getString3, showFrame_Vtmp);
		        
		        showFrame_V.addElement(H_flg);
		        
	            
		        for(int i=0;i< showFrame_Vtmp.size();i++){
		        	showFrame_V.addElement(showFrame_Vtmp.elementAt(i));
		        	
		        	//System.out.println(i+":-"+showFrame_Vtmp.elementAt(i));
		        	
		        }
		        String getUrl = "/cgi-bin/tran_web_cgi_ejje?atw_u=http://www4.ipdl.inpit.go.jp/Tokujitu/tjitemcntpaj.ipdl"
		            +"&N0000=287"+"&N0001="+MID+"&N0005="+N0005+"&N0500="+URLEncoder.encode(DL,"UTF-8")+"&N0510="+EL+"&N0551="+H_flg+"&N0550="+K_flg+"&N0580=0";

		        
		        System.out.println("--------我是分割线---------");
		        for(int i=0;i< showFrame_V.size();i++){
		        		        	
		        	System.out.println(i+":-"+showFrame_V.elementAt(i));
		        	
		        }
		        
		        get3.releaseConnection();
		        
		        Html2Pdf html2pdf = new Html2Pdf();
		        String files[]= new String[ showFrame_V.size()];
			        
		        for (int i = 0; i < showFrame_V.size();i++ ) {
		        	
		        	String getUrli= getUrl.replaceAll("N0551=(\\d{20})", "N0551="+(String)showFrame_V.elementAt(i));
		        	System.out.println("开始下载--第"+i+"部分\nURL:--"+getUrli);
		   		    String tmpfilename = dir+"\\tmp-"+patent.getPubCode()+i+".pdf";
		   		    System.out.println("临时文件"+tmpfilename);
		   		    files[i]= tmpfilename;
		        	
			        GetMethod get = new GetMethod(getUrli);
			        get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
			                new DefaultHttpMethodRetryHandler());
			        //get.setRequestHeader("Cookie","ARPT=IMUNUNSALPCYQW;LADDR=60.12.143.5;ACSTM=20090219224033639459");
			        client.executeMethod(get);
			        
			        if (i == showFrame_V.size()-1){

			        	
			        	String getstr = get.getResponseBodyAsString();
			        	//System.out.println(getstr);
			        	String title = parser.parserTitle(getstr).trim();
			        	if(title.endsWith("[DRAWINGS]")){
			        		try {
								parser.downloadGif(getstr, tmpfilename, title, client);
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

			        	 
			        	}
			        	
			        	else{
			 		        InputStream inHtml = get.getResponseBodyAsStream();
	   		   	            
				   	        html2pdf.toPdf(inHtml,tmpfilename);
				   	        		        
				            get.releaseConnection();
			        	}
			        	        	
			         	
			        }
			        else{
			        
			        
		 		        InputStream inHtml = get.getResponseBodyAsStream();
		   		    		   	            
			   	        html2pdf.toPdf(inHtml,tmpfilename);
			   	        		        
			            get.releaseConnection();
			        
			        }

		        	
		        	
		        }
		        
		        html2pdf.mergePdfFilesLocal(files,savepath);
	     }
	     else{
	    	 
	    	 System.out.println("没有全文数据");
	     }
		
		
		
		

	}
	
	
	public void tifjpAll(String dir, Patent patent, String savepath,HttpClient client) throws HttpException, IOException {
		
		 //http://www4.ipdl.inpit.go.jp/Tokujitu/PAJdetail.ipdl?N0000=60&N0120=01&N2001=2&N3001=H08-258231
	     String url = patent.getFullurltmp(); 
	     if(!url.equals("full text is not available")){
	    	 
		
		        System.out.println("开始下载：\n ---"+url);
	     
		        
		    	UseHttpPost useHttpPost =new UseHttpPost();
				useHttpPost.setProxyServer(); 
		        
		        
				//HttpClient client = new HttpClient();
				Tifdownload2 parser = new Tifdownload2();
						 
				System.setProperty("apache.commons.httpclient.cookiespec", CookiePolicy.BROWSER_COMPATIBILITY); 
				//client.getState().setCookiePolicy(CookiePolicy.COMPATIBILITY);
				client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
				client.getParams().setParameter("http.protocol.single-cookie-header", true);
				
								
			   
				client.getHostConfiguration().setHost("www4.ipdl.inpit.go.jp", 80, "http");
				String geturl = url.replaceAll("http://www4.ipdl.inpit.go.jp", "");
				System.out.println(geturl);
				GetMethod get1 = new GetMethod(geturl);
		       
				client.executeMethod(get1);

		        
		        String getString = get1.getResponseBodyAsString();
		        //System.out.println(getString);
		        get1.releaseConnection();
		        
		        
				String tjswhen = parser.parserTjswhen(getString);
		      //System.out.println("tjswhen:"+tjswhen);			
				
				
				String tjswhen2=tjswhen.replaceAll("http://www4.ipdl.inpit.go.jp", "");
				System.out.println("tjswhen:"+tjswhen2);	
				
				
				GetMethod get2 = new GetMethod(tjswhen2);
					
				//get2.setRequestHeader("Host", "http://www4.ipdl.inpit.go.jp");
				//get2.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.3pre) Gecko/20100405 Firefox/3.6.3plugin1 (.NET CLR 3.5.30729)");
				//get2.setRequestHeader("Referer",url);
				//get2.setRequestHeader("Cookie",cookie);
			    //get2.setRequestHeader(header);
				//get2.setRequestHeader("Cookie","ARPT=IMUNUNSALPCYQW;LADDR=60.12.143.5;ACSTM=20090219224033639459");
		        client.executeMethod(get2);
		        
		        String getString2 = get2.getResponseBodyAsString();
		      //System.out.println(getString2);
		        String tjitemidx= parser.parserTjitemidx(getString2);
		      System.out.println("tjitemidx:"+tjitemidx);
		        
		        String tjitemcnt= parser.parserTjitemcnt(getString2);
		     // System.out.println("tjitemcnt:"+tjitemcnt);
		        
		        String H_flg= parser.parserH_flg(getString2);
		        String DL= parser.parserDL(getString2);
		        String EL= parser.parserEL(getString2);
		        String PI= parser.parserPI(getString2);
		        String MID= parser.parserMID(getString2);
		        String K_flg= parser.parserK_flg(getString2);
		        
		        
		      // System.out.println("geturl:"+getUrl);
		        

		        
		        get2.releaseConnection();
		        
		        GetMethod get3 = new GetMethod(tjitemidx);
		        //get3.setRequestHeader("Cookie","ARPT=IMUNUNSALPCYQW;LADDR=60.12.143.5;ACSTM=20090219224033639459");
		        client.executeMethod(get3);
		        String getString3 = get3.getResponseBodyAsString();
		        //System.out.println(getString3);
		        
		        String N0005= parser.parserN0005(getString3);
		        
				Vector showFrame_V = new Vector();
				Vector showFrame_Vtmp = new Vector();
				
		        parser.parserShowFrames(getString3, showFrame_Vtmp);
		        
		        showFrame_V.addElement(H_flg);
		        
	            
		        for(int i=0;i< showFrame_Vtmp.size();i++){
		        	showFrame_V.addElement(showFrame_Vtmp.elementAt(i));
		        	
		        	//System.out.println(i+":-"+showFrame_Vtmp.elementAt(i));
		        	
		        }
		        String getUrl = "/cgi-bin/tran_web_cgi_ejje?atw_u=http://www4.ipdl.inpit.go.jp/Tokujitu/tjitemcntpaj.ipdl"
		            +"&N0000=287"+"&N0001="+MID+"&N0005="+N0005+"&N0500="+URLEncoder.encode(DL,"UTF-8")+"&N0510="+EL+"&N0551="+H_flg+"&N0550="+K_flg+"&N0580=0";

		        
		        System.out.println("--------我是分割线---------");
		        for(int i=0;i< showFrame_V.size();i++){
		        		        	
		        	System.out.println(i+":-"+showFrame_V.elementAt(i));
		        	
		        }
		        
		        get3.releaseConnection();
		        
		        Html2Pdf html2pdf = new Html2Pdf();
		        String files[]= new String[ showFrame_V.size()];
			        
		        for (int i = 0; i < showFrame_V.size();i++ ) {
		        	
		        	String getUrli= getUrl.replaceAll("N0551=(\\d{20})", "N0551="+(String)showFrame_V.elementAt(i));
		        	System.out.println("开始下载--第"+i+"部分\nURL:--"+getUrli);
		   		    String tmpfilename = dir+"\\tmp-"+patent.getPubCode()+i+".pdf";
		   		    System.out.println("临时文件"+tmpfilename);
		   		    files[i]= tmpfilename;
		        	
			        GetMethod get = new GetMethod(getUrli);
			        get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
			                new DefaultHttpMethodRetryHandler());
			        //get.setRequestHeader("Cookie","ARPT=IMUNUNSALPCYQW;LADDR=60.12.143.5;ACSTM=20090219224033639459");
			        client.executeMethod(get);
			        
			        if (i == showFrame_V.size()-1){

			        	
			        	String getstr = get.getResponseBodyAsString();
			        	//System.out.println(getstr);
			        	String title = parser.parserTitle(getstr).trim();
			        	if(title.endsWith("[DRAWINGS]")){
			        		try {
								parser.downloadGif(getstr, tmpfilename, title, client);
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

			        	 
			        	}
			        	
			        	else{
			 		        InputStream inHtml = get.getResponseBodyAsStream();
	   		   	            
				   	        html2pdf.toPdf(inHtml,tmpfilename);
				   	        		        
				            get.releaseConnection();
			        	}
			        	        	
			         	
			        }
			        else{
			        
			        
		 		        InputStream inHtml = get.getResponseBodyAsStream();
		   		    		   	            
			   	        html2pdf.toPdf(inHtml,tmpfilename);
			   	        		        
			            get.releaseConnection();
			        
			        }

		        	
		        	
		        }
		        
		        html2pdf.mergePdfFilesLocal(files,savepath);
	     }
	     else{
	    	 
	    	 System.out.println("没有全文数据");
	     }
		
		
		
		

	}


	public String Paserepimageurl(String Url) {
		String url = "";
		try {
			// UseHttpPost useHttpPost=new UseHttpPost();
			// useHttpPost.setURL(Url);
			// String getString=useHttpPost.getContent2();
			url = Url.substring(Url.indexOf("?"));
			url = "http://v3.espacenet.com/espacenetDocument.pdf" + url;

			url = url.replaceAll("&FT=D", "&FT=E");
			url = url.replaceAll("A1&", "&");
			url = url.replaceAll("A2&", "&");
			url = url.replaceAll("A3&", "&");
			if (url.indexOf("&patentid") != -1)
				url = url.substring(0, url.indexOf("&patentid"));
			url = url + "&flavour=phantomFull";
			System.out.println("url" + url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;

	}

}
