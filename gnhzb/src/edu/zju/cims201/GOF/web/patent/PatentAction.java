package edu.zju.cims201.GOF.web.patent;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;

import edu.zju.cims201.GOF.hibernate.pojo.Author;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Patent;
import edu.zju.cims201.GOF.rs.dto.ObjectDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.PatentDTO;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.patent.PatentService;
import edu.zju.cims201.GOF.service.systemFile.FileService;
import edu.zju.cims201.GOF.service.webservice.ConvertFlashWebService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.DownZipfile;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.util.ProxyUtil;
import edu.zju.cims201.GOF.util.Tifdownload;
import edu.zju.cims201.GOF.util.UStifdownload;
import edu.zju.cims201.GOF.util.UseHttpPost;
import edu.zju.cims201.GOF.web.CrudActionSupport;

/**
 * 专利管理Action
 * @author jiangdingding
 *
 */
@Namespace("/patent")
@Results({ @Result(name = CrudActionSupport.RELOAD, location = "patent.action", type = "redirect")})
public class PatentAction extends CrudActionSupport<MetaKnowledge> implements ServletResponseAware{

	private static final long serialVersionUID = 1046248125123603836L;
	@Resource(name="patentServiceImpl")
	private PatentService patentService;
	@Resource(name = "fileServiceImpl")
	private FileService fileservcie;
	@Resource(name = "convertFlashWebServiceImpl")
	private ConvertFlashWebService convertFlashWebService;	
	@Resource(name = "knowledgeServiceImpl")
	private KnowledgeService knowledgeService;
	
	//1.中国专利检索
	private String searchPatentForm;//专利检索表单
	private String fromsearch;//从哪个页面检索，首页检索为1，展示页面为2
	private String selectbase;//数据库
	private String pg;//页数
	
	private HttpServletResponse response;
	private HttpServletRequest request;
	
	//页面一些值
	private String sSearchContentTmp;
	private String tempurl;
	private String totalPage;
	private String currentPage;
	private String totalRecord;
	
	//各种类型中国专利数量
	private String fmRecord;//发明专利
	private String xxRecord;//实用新型专利
	private String wgRecord;//外观设计专利
	
	//专利属性
	private String appCode;
	private String patentType;
	private String app_code;
	private String patent_name;
	private String abstractcontent;
	private String app_date = "";
	private String pub_date = "";
	private String pub_code;
	private String cat_code;
	private String IPC;
	private String app_person;
	private String inv_person;
	private String app_address;
	private String int_pub;
	private String cerdate = "";
	private String sub_agent;
	private String sub_person;
	private String priority;
	
	
	//2.美国专利检索
	private String d;
	private String TERM1;
	private String FIELD1;
	private String co1;
	private String TERM2;
	private String FIELD2;
	private String totallist;
	private String keyword;
	private String Srch1;
	private String s1;
	private String s2;
	
	private String fullurl;
	private String redown;
	
	/**
	 * 从服务器数据库查询专利
	 * jiangdingding添加 2013-5-25
	 */
	public String searchPatentFromDB() throws Exception{
		
		HashMap<String, String> jsonMap = (HashMap<String, String>)JSONUtil.read(searchPatentForm);

		PageDTO resultlist = patentService.listPatentInDB(jsonMap);

		JSONUtil ju  = new JSONUtil();
	
		ju.write(response, resultlist);

		return null;
	}
	
	
	/**
	 * 搜索中国专利 
	 * jiangdingding 添加 2013-5-16
	 */
	public String searchPatentCN () throws Exception{
				

		//从专利列表检索
		if(fromsearch.equals("2")){
			
			if (sSearchContentTmp.trim().length() > 0) {
				sSearchContentTmp = new String(sSearchContentTmp.getBytes("ISO-8859-1"),"UTF-8");
			//System.out.println("3333333333===="+sSearchContentTmp);
			}
			//System.out.println("sSearchContentTmp1="+sSearchContentTmp);
			sSearchContentTmp=sSearchContentTmp.replaceAll("selectbase=(\\d{0,9}[\\,]{0,1}){1,3}", "selectbase="+selectbase);
			sSearchContentTmp=sSearchContentTmp.replaceAll("pg=\\d{0,9}", "pg="+pg);
//			System.out.println("selectbase="+selectbase+",pg="+pg);
//			System.out.println("sSearchContentTmp2="+sSearchContentTmp);
			
		}else if(fromsearch.equals("1")){//首页检索
			
			sSearchContentTmp = "";
			selectbase = "0";
			//乱码解决方案
			//searchPatentForm = new String(searchPatentForm.getBytes("ISO-8859-1"),"UTF-8");  
			HashMap<String, String> jsonMap = (HashMap<String, String>)JSONUtil.read(searchPatentForm);
			if(!jsonMap.get("selectbase").equals("")){				
				selectbase = jsonMap.get("selectbase");
			}
			if(!jsonMap.get("pg").equals("") && null!=jsonMap.get("pg")){
				pg = jsonMap.get("pg");
			}else{
				pg = "1";
			}
			app_code = jsonMap.get("app_code");
			patent_name = jsonMap.get("patent_name");
			abstractcontent = jsonMap.get("abstractcontent");
			if(jsonMap.get("app_date") != null){			
				app_date = jsonMap.get("app_date");
				app_date = (app_date.split("T"))[0];
				app_date = app_date.replaceAll("-", "");
			}
			if(jsonMap.get("pub_date") != null){			
				pub_date = jsonMap.get("pub_date");
				pub_date = (pub_date.split("T"))[0];
				pub_date = pub_date.replaceAll("-", "");
			}
			pub_code = jsonMap.get("pub_code");
			cat_code = jsonMap.get("cat_code");
			IPC = jsonMap.get("IPC");
			app_person = jsonMap.get("app_person");
			inv_person = jsonMap.get("inv_person");
			app_address = jsonMap.get("app_address");
			int_pub = jsonMap.get("int_pub");
			
			//设置关键词 2013-6-2
			if(patent_name!=null && !patent_name.equals("")){
				keyword = patent_name.trim();
			}
			if(jsonMap.get("cerdate") != null){			
				cerdate = jsonMap.get("cerdate");
				cerdate = (cerdate.split("T"))[0];
				cerdate = cerdate.replaceAll("-", "");
			}
			sub_agent = jsonMap.get("sub_agent");
			sub_person = jsonMap.get("sub_person");
			priority = jsonMap.get("priority");
			//System.out.println("+++++patent_name = "+patent_name+"+++++++++++");
			if (app_code.trim().length() > 0) {
				//		app_code = chartools.ISO2UTF8(app_code);
				app_code = app_code.replaceAll("[c,C,N,n,z,Z,l,L]", "");// 去掉zl
				// ZL
				// cn
				// CN
				app_code = app_code.replaceAll(" ", "");// 去掉空格
				app_code=app_code.replace("　","");
				
				sSearchContentTmp = sSearchContentTmp + "申请号=('CN%"
						+ app_code.trim() + "%') ";
				//sSearchContentTmp = sSearchContentTmp.replaceAll(" ", "%20");
			}
			// str=str + "申请号=('CN%" + num + "%" + "') and "
			
			if (patent_name.trim().length() > 0) {
				//	patent_name = chartools.ISO2UTF8(patent_name);
				if (sSearchContentTmp.length() > 0) {
					sSearchContentTmp = sSearchContentTmp + " and ";
				}
				sSearchContentTmp = sSearchContentTmp + "名称=("
						+ patent_name.trim() + ") ";
				//sSearchContentTmp = sSearchContentTmp.replaceAll(" ", "%20");
			}
			if (abstractcontent.trim().length() > 0) {
				//abstractcontent = chartools.ISO2UTF8(abstractcontent);
				
				if (sSearchContentTmp.length() > 0) {
					sSearchContentTmp = sSearchContentTmp + " and ";
				}
				sSearchContentTmp = sSearchContentTmp + "摘要=("
						+ abstractcontent.trim() + ")";
				//sSearchContentTmp = sSearchContentTmp.replaceAll(" ", "%20");
			}
			if (app_date.trim().length() > 0) {
				//app_date = chartools.ISO2UTF8(app_date);
				if (sSearchContentTmp.length() > 0) {
					sSearchContentTmp = sSearchContentTmp + " and ";
				}
				sSearchContentTmp = sSearchContentTmp + "申请日=("
						+ app_date.trim() + ")";
				//sSearchContentTmp = sSearchContentTmp.replaceAll(" ", "%20");
			}
			if (pub_date.trim().length() > 0) {
				//pub_date = chartools.ISO2UTF8(pub_date);
				if (sSearchContentTmp.length() > 0) {
					sSearchContentTmp = sSearchContentTmp + " and ";
				}
				sSearchContentTmp = sSearchContentTmp + "公开（公告）日=("
						+ pub_date.trim() + ")";
				//sSearchContentTmp = sSearchContentTmp.replaceAll(" ", "%20");
			}
			if (pub_code.trim().length() > 0) {
				//pub_code = chartools.ISO2UTF8(pub_code);
				if (sSearchContentTmp.length() > 0) {
					sSearchContentTmp = sSearchContentTmp + " and ";
				}
				if ((pub_code.indexOf("cN") != -1)
						|| (pub_code.indexOf("cn") != -1)
						|| (pub_code.indexOf("CN") != -1)
						|| (pub_code.indexOf("Cc") != -1)) {
					pub_code = pub_code.replaceAll("[c,C,n,N]", "");
					sSearchContentTmp = sSearchContentTmp + "公开（公告）号=('CN"
							+ pub_code.trim() + "%')";
					
				} else {
					pub_code = pub_code.replaceAll("[c,C,n,N,%]", "");
					
					sSearchContentTmp = sSearchContentTmp + "公开（公告）号=('CN%"
							+ pub_code.trim() + "%')";
				}
				
				//sSearchContentTmp = sSearchContentTmp.replaceAll(" ", "%20");
			}
			
			if (cat_code.trim().length() > 0) {
				//	cat_code = chartools.ISO2UTF8(cat_code);
				if (sSearchContentTmp.length() > 0) {
					sSearchContentTmp = sSearchContentTmp + " and ";
				}
				// if (cat_code.startsWith("%"))
				// {
				// sSearchContentTmp = sSearchContentTmp + "分类号=('"
				// + cat_code.trim() + "%')";
				// }
				// else
				// {
				if (cat_code.startsWith("%")) {
					cat_code = cat_code.substring(1);
				}
				if (cat_code.endsWith("%")) {
					
					cat_code = cat_code.substring(0, cat_code.length() - 1);
				}
				// cat_code =cat_code.replaceAll("%", "");
				cat_code = cat_code.replaceAll(" ", "");
				cat_code = cat_code.toUpperCase();
				sSearchContentTmp = sSearchContentTmp + "分类号=('"
						+ cat_code.trim() + "%')";
				// }
				//sSearchContentTmp = sSearchContentTmp.replaceAll(" ", "%20");
			}
			// str=str + "分类号=('%" + cate_num + "%') and ";
			if (IPC.trim().length() > 0) {
				//	IPC = chartools.ISO2UTF8(IPC);
				if (sSearchContentTmp.length() > 0) {
					sSearchContentTmp = sSearchContentTmp + " and ";
				}
				if (IPC.startsWith("%")) {
					IPC = IPC.substring(1);
				}
				if (IPC.endsWith("%")) {
					IPC = IPC.substring(0, IPC.length() - 1);
				}
				IPC = IPC.replaceAll(" ", "");
				IPC = IPC.toUpperCase();
				sSearchContentTmp = sSearchContentTmp + "主分类号=('" + IPC.trim()
						+ "%')";
				//sSearchContentTmp = sSearchContentTmp.replaceAll(" ", "%20");
			}
			
			if (app_person.trim().length() > 0) {
				//	app_person = chartools.ISO2UTF8(app_person);
				if (sSearchContentTmp.length() > 0) {
					sSearchContentTmp = sSearchContentTmp + " and ";
				}
				sSearchContentTmp = sSearchContentTmp + "申请（专利权）人=("
						+ app_person.trim() + ")";
				//sSearchContentTmp = sSearchContentTmp.replaceAll(" ", "%20");
			}
			if (inv_person.trim().length() > 0) {
				//	inv_person = chartools.ISO2UTF8(inv_person);
				if (sSearchContentTmp.length() > 0) {
					sSearchContentTmp = sSearchContentTmp + " and ";
				}
				sSearchContentTmp = sSearchContentTmp + "发明（设计）人=("
						+ inv_person.trim() + ")";
				//sSearchContentTmp = sSearchContentTmp.replaceAll(" ", "%20");
			}
			if (app_address.trim().length() > 0) {
				//	app_address = chartools.ISO2UTF8(app_address);
				if (sSearchContentTmp.length() > 0) {
					sSearchContentTmp = sSearchContentTmp + " and ";
				}
				sSearchContentTmp = sSearchContentTmp + "地址=("
						+ app_address.trim() + ")";
				//sSearchContentTmp = sSearchContentTmp.replaceAll(" ", "%20");
			}
			
			if (int_pub.trim().length() > 0) {
				//	int_pub = chartools.ISO2UTF8(int_pub);
				if (sSearchContentTmp.length() > 0) {
					sSearchContentTmp = sSearchContentTmp + " and  ";
				}
				int_pub = int_pub.replaceAll("%", "");
				sSearchContentTmp = sSearchContentTmp + "国际公布=('%"
						+ int_pub.trim() + "%')";
				//sSearchContentTmp = sSearchContentTmp.replaceAll(" ", "%20");
			}
			
			if (cerdate.trim().length() > 0) {
				//	cerdate = chartools.ISO2UTF8(cerdate);
				if (sSearchContentTmp.length() > 0) {
					sSearchContentTmp = sSearchContentTmp + " and ";
				}
				sSearchContentTmp = sSearchContentTmp + "颁证日=("
						+ cerdate.trim() + ")";
				//sSearchContentTmp = sSearchContentTmp.replaceAll(" ", "%20");
			}
			
			if (sub_agent.trim().length() > 0) {
				//	sub_agent = chartools.ISO2UTF8(sub_agent);
				if (sSearchContentTmp.length() > 0) {
					sSearchContentTmp = sSearchContentTmp + " and ";
				}
				sSearchContentTmp = sSearchContentTmp + "专利代理机构=("
						+ sub_agent.trim() + ")";
				// //sSearchContentTmp = sSearchContentTmp.replaceAll(" ", "%20");
			}
			
			if (sub_person.trim().length() > 0) {
				//	sub_person = chartools.ISO2UTF8(sub_person);
				if (sSearchContentTmp.length() > 0) {
					sSearchContentTmp = sSearchContentTmp + " and ";
				}
				sSearchContentTmp = sSearchContentTmp + "代理人=("
						+ sub_person.trim() + ")";
				//sSearchContentTmp = sSearchContentTmp.replaceAll(" ", "%20");
			}
			
			if (priority.trim().length() > 0) {
				//		priority = chartools.ISO2UTF8(priority);
				if (sSearchContentTmp.length() > 0) {
					sSearchContentTmp = sSearchContentTmp + " and ";
				}
				sSearchContentTmp = sSearchContentTmp + "优先权=("
						+ priority.trim() + ")";
				//sSearchContentTmp = sSearchContentTmp.replaceAll(" ", "%20");
			}
		}
				
			if(!fromsearch.equals("2")){				
				sSearchContentTmp=URLEncoder.encode(sSearchContentTmp);
				sSearchContentTmp ="flag3=1&selectbase="+selectbase+ "&sign=0&recshu=20&pg="+ pg+"&searchword="+ sSearchContentTmp;
			}
			
			UseHttpPost useHttpPost =new UseHttpPost();
			useHttpPost.setProxyServer();
			useHttpPost.setURL(Constants.reQuestUrl);
			
			System.out.println("Constants.reQuestUrl="+Constants.reQuestUrl);
			System.out.println("sSearchContentTmp="+sSearchContentTmp);
			
			//sSearchContentTmp="flag3=1&selectbase=11,22,33&sign=0&recshu=20&searchword=名称=(汽车)&pg=1";
			useHttpPost.sendPost(sSearchContentTmp);
			String getString="";
			try{
				System.out.println("开始抓取中国专利,请耐心等候");
				getString=useHttpPost.getContent();}
			catch(Exception e){e.printStackTrace();
			}
			//System.out.println(getString);
		
			tempurl=sSearchContentTmp.replace("&pg="+pg, "&pg=1");
			//System.out.println("tempurl = "+tempurl);
	        //request.setAttribute("urls", tempurl);
			
			// 下载后解析
			Vector app_code_V = new Vector();
			Vector app_code_Vtmp = new Vector();
			Vector patent_name_V = new Vector();		
			Vector patent_name_Vtmp = new Vector();
			Vector patent_type_tmp = new Vector();
			Vector patent_type = new Vector();
			totalPage = ""; currentPage = "";// 总共多少页,当前页
			totalRecord = ""; fmRecord = ""; xxRecord = ""; wgRecord = "";// 各发明类型的页数
			Tifdownload tifdownLoad = new Tifdownload();
			// 使用代理		
			tifdownLoad.setProxyServer();
			tifdownLoad.parserAppcode(getString, app_code_Vtmp);
			tifdownLoad.parserPatentName(getString, patent_name_Vtmp);
			tifdownLoad.parserPatentType(getString, patent_type_tmp);
						
			//PageDTO pagedto = new PageDTO();			
			HashMap<String, Object> hashmap = new HashMap<String, Object>();
				
			List<PatentDTO> pdtos = new ArrayList<PatentDTO>();
			for (int i = 0; i < app_code_Vtmp.size(); i += 2) {
				PatentDTO pdto = new PatentDTO();
				pdto.setAppCode((app_code_Vtmp.elementAt(i)).toString());
				pdto.setTitleName((patent_name_Vtmp.elementAt(i)).toString());
				pdto.setPatentType((patent_type_tmp.elementAt(i)).toString());
				if(keyword!=null && !keyword.equals("")){
					pdto.setKeyword(keyword);
				}
				pdtos.add(pdto);
//				app_code_V.addElement(app_code_Vtmp.elementAt(i));
//				patent_name_V.addElement(patent_name_Vtmp.elementAt(i));
//				patent_type.addElement(patent_type_tmp.elementAt(i));
			}
			totalRecord = tifdownLoad.parserTotalRecord(getString);
			fmRecord = tifdownLoad.parserfmRecord(getString);
			xxRecord = tifdownLoad.parserxxRecord(getString);
			wgRecord = tifdownLoad.parserwgRecord(getString);
			totalPage = tifdownLoad.parserTotalPage(getString);
			currentPage = tifdownLoad.parserCurrentPage(getString);
			System.out.println("totalpage="+totalPage);
			
			hashmap.put("data", pdtos);
			if(keyword!=null && !keyword.equals("")){
				hashmap.put("keyword",keyword);
			}
			hashmap.put("tempurl",tempurl);
			hashmap.put("sSearchContentTmp", sSearchContentTmp);
			hashmap.put("fmRecord", fmRecord);
			hashmap.put("xxRecord", xxRecord);
			hashmap.put("wgRecord", wgRecord);
			hashmap.put("fmRecord", fmRecord);
			hashmap.put("currentPage", currentPage);
			hashmap.put("totalPage", Long.parseLong(totalPage));
			hashmap.put("total",Long.parseLong(totalRecord));
						
			JSONUtil ju  = new JSONUtil();
			ju.write(response, hashmap);

			return null;
	}

	/**
	 * 下载中国专利全文
	 * jiangdingding 添加 2013-5-18
	 */
	public String downCNPatentText() throws Exception{
		//从前台获得appCode、patentType
		String app_codetmp=appCode;
		String type = patentType;
		String re_download = "0";//re_download是否重新下载 值为“1”时表示重新下载
		if(redown!=null){
			re_download = redown;
		}
//		String dir = Constants.dir;
		String dir = Constants.SOURCEFILE_PATH_TEMP;
		String patentid="";		
		String fullurl="";
		String pagenumber="0";
		String cn_type="";
		String powerFulTmp = "";
		String poweredpagenumber="0";
		
		String targetDirectory = Constants.SOURCEFILE_PATH;
		String uuid = UUID.randomUUID().toString();
		String targetFileName = uuid + ".pdf";
		String savepath = dir + "\\" + targetFileName;//临时保存
		String savepath1 = targetDirectory + "\\" + targetFileName;//专利说明书全文保存路径
		String savepath2 = targetDirectory + "\\" + uuid + "issued.pdf";//专利授权全文保存路径
		String flashfilepath =	uuid + ".swf";
		
		if(app_codetmp.indexOf(".")!=-1){
			patentid=app_codetmp.substring(0,app_codetmp.indexOf("."));
		}else{
			patentid=app_codetmp;
		}

		patentid = patentid.replaceAll(",", "");
		
		List list = patentService.findByAppCode(patentid);
		Patent patent = new Patent();

		if (list.isEmpty()) {		
			System.out.println("专利还不存在数据库中，现在下载解析");
			String urlstr = Constants.sipoUrl + app_codetmp
			 + "&leixin="+type;
			
			ProxyUtil.useHttpProxy();
		
			URL url = new URL(urlstr);
			HttpURLConnection httpConnection = (HttpURLConnection) url
					.openConnection();
		
			// 设置User-Agent
			httpConnection.setRequestProperty("User-Agent", "BorderSpider ("
					+ url + ")");
		
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
		 
		     try {
		    	    //解析中国专利
		    	    String key_word = "";
		    	    if(keyword!=null){
						key_word = new String(keyword.getBytes("iso-8859-1"),"utf-8");
//						key_word = URLDecoder.decode(keyword,"utf-8");
					}
		    	    patent = patentService.parsercn(getString, urlstr , key_word);					
		    	    //保存专利
		    	    patentService.save(patent);
		    		cn_type=patent.getCnType();
		    		fullurl=patent.getFullurltmp();
		    		pagenumber=patent.getPagenumber();
		    		powerFulTmp =patent.getPoweredfullurl();
		    	    poweredpagenumber=patent.getPoweredpagenumber();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		else{
			System.out.println("专利已经存在，直接从数据库中调取数据");
			patent=(Patent)list.get(0);
			cn_type=patent.getCnType();
			fullurl=patent.getFullurltmp();
			fullurl=fullurl.replace("http://", "");
		
//    	    System.out.println("fullurl="+fullurl);
		    pagenumber=patent.getPagenumber();
		    powerFulTmp =patent.getPoweredfullurl();
	        poweredpagenumber=patent.getPoweredpagenumber();	
	        //判断文件是否存在
			if(patent.getKnowledgesourcefilepath()!=null && !patent.getKnowledgesourcefilepath().equals("")){
				savepath1 = targetDirectory + "\\" +patent.getKnowledgesourcefilepath();
				savepath2 = savepath1.substring(0,savepath1.lastIndexOf("."))+"issued.pdf";
			}
		}
		
//		System.out.println("savepath="+savepath1);
		
		File file = new File(savepath1);
		if (!file.isFile()||re_download.equals("1")) {
			if(!file.isFile()){
				System.out.println("专利全文不存在，现在开始下载");
			}
			if(re_download.equals("1")){
				System.out.println("开始重新下载专利全文");
			}
			fullurl=Constants.sipotexturl+fullurl;
			Tifdownload tif = new Tifdownload();
	
			tif.setProxyServer();
			//判断是否是外观，外观没有公开说明书，只有外观的图象，下载规律不同，因此要区分开来
			String filetype="";
			
			boolean iswaiguan=false;
			if(cn_type.equals("3"))
			{
				filetype="jpg";
				iswaiguan=true;
			}else
			{
				filetype="tif";
			}	
			String starturl =fullurl.replaceFirst("000001.tif", "");
//			System.out.println("starturl22="+starturl);
								
			String oo = "000";
			int i = Integer.parseInt(pagenumber);
			for (int k = 1; k <= i; k++) {
				if (k < 100) {
					oo = "0000";
					if (k < 10)
						oo = "00000";
				}
			
				tif.addItem(starturl + oo + k + "."+filetype);
			}

			// 开始下载			
			try {
//				response.reset();
				if(iswaiguan)					
					tif.downLoadjpgByList(savepath);
				else{
					tif.downLoadtifByList(savepath);
				}
				//添加patent的knowledgesourcefilepath、Flashfilepath属性
	    	    patent.setKnowledgesourcefilepath(targetFileName);
	    	    patent.setFlashfilepath(flashfilepath);
	    	    patentService.save(patent);
	    	    
				//另存到system_file表中
				File file2 = new File(savepath);
				if (file2.isFile()) {								
//					File target = new File(dir, targetFileName);
//					FileUtils.copyFile(file2, target);
					fileservcie.save(file2, targetFileName);
				}
	    	    
				//转换flash，并从temp文件夹拷贝到soucefile文件夹
				patentService.flashconvert(targetFileName, response);
				
		} catch (Exception err) {
			System.out.println(err.getMessage());
		}
		}
		else
		{
			System.out.println("专利全文存在");
		}
		//判断授权全文是否存在
		if(poweredpagenumber.equals("0")){
			System.out.println("专利没有授权，没有授权全文");
			FileInputStream fi = null;
			OutputStream out = null;
			try {
					response.reset();
					response.setCharacterEncoding("UTF-8");

					response.setContentType("application/pdf;charset=gb2312");

					String filename = patentid + ".pdf";

					System.out.println("filename=" + filename);
					response.setHeader("Content-Disposition",
							"attachment; filename="
									+ URLEncoder.encode(filename, "UTF-8") + "");
					//response.flushBuffer();
					file = new File(savepath1);
					fi=new FileInputStream(file);
					BufferedInputStream br = new BufferedInputStream(fi);

					byte[] buf = new byte[1024];
					int len = 0;
					out = response.getOutputStream();
					while ((len = br.read(buf)) > 0){
						out.write(buf, 0, len);
					}					
				} catch (Exception e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				} finally{
//					out.flush();
					if(out!=null){
						out.close();
					}
					if(fi!=null){
						fi.close();
					}
						
				}

		}else
				{
					System.out.println("专利有授权，还有授权全文需要下载");
				
					powerFulTmp=Constants.sipotexturl+powerFulTmp;
					
					//判断授权全文是否存在本地
					File file2 = new File(savepath2);
					if (!file2.isFile()||re_download.equals("1")) {
					
						Tifdownload	tif = new Tifdownload();
						String	filetype="tif";
							
						String 	starturl =powerFulTmp.replaceFirst("000001.tif", "");
															
						String	oo = "000";
						int		 i = Integer.parseInt(poweredpagenumber);
						for (int k = 1; k <= i; k++) {
							if (k < 100) {
								oo = "0000";
								if (k < 10)
									oo = "00000";
							}
						
							tif.addItem(starturl + oo + k + "."+filetype);
						}
				
						try {
							tif.downLoadtifByList(savepath2);
						} catch (Exception e) {
							e.printStackTrace();
						}
				    }else{
				    	System.out.println("授权全文存在");
				    }
												
					try {
						String[] savepaths={savepath1,savepath2};
						DownZipfile zip =new DownZipfile();
						zip.downZipfile(savepaths, response,targetDirectory);
					} catch (Exception e) {
						// TODO 自动生成 catch 块
						e.printStackTrace();
					}
				}	
	
//		List list2 = patentService.findByAppCode(patentid);
//		if(list2.size()>0){
//			for(int i=0;i<list2.size();i++){
//				patent=(Patent)list2.get(i);
//				patent.setMainPower("1");
//				patentService.save(patent);
//			}}
		return null;
		
	}

	
	/**
	 * 搜索中国专利详细信息
	 * jiangdingding 添加 2013-5-20
	 */
	public String searchPatentDetailCN() throws Exception{
		
		String app_codetmp = appCode;
		String type = patentType;
		
		String patentid = "0";
		String mispatent = "false";
		String app_person = null;
		
//		String dir = Constants.dir;	
		String dir = Constants.SOURCEFILE_PATH_TEMP;
		String uuid = UUID.randomUUID().toString();
		
		List patentinfolist = new ArrayList();

		int len2 = app_codetmp.indexOf(".");
		if (len2 == -1) {
			len2 = app_codetmp.length();
		}
		app_codetmp = app_codetmp.substring(0, len2);
	
		patentinfolist = patentService.findByAppCode(app_codetmp);
		Patent patentinfo =new Patent();
		
		if (patentinfolist.size() > 0) {
			System.out.println("专利已经存在数据库中");
			patentinfo = (Patent) patentinfolist.get(0);	

		} else {
			System.out.println("数据库中还没有该专利");
			String urlstr = Constants.sipoUrl + app_codetmp
					+ "&leixin="+type;
			
			System.out.println("单个专利的检索地址="+urlstr);
		

			ProxyUtil.useHttpProxy();

			URL url = new URL(urlstr);
			HttpURLConnection httpConnection = (HttpURLConnection) url
					.openConnection();

			// 设置User-Agent
			httpConnection.setRequestProperty("User-Agent", "BorderSpider ("
					+ url + ")");

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
			//解析中国专利
			String key_word = "";
	    	if(keyword!=null){
	    		key_word = keyword;
	    	}
    	    patentinfo = patentService.parsercn(getString, urlstr, key_word);					
    	    
    	    //保存专利
    	    patentService.save(patentinfo);
    	    
		}
		if(patentinfo.getPatentId().equals("us"))
		{	
			System.out.println("database=" + patentinfo.getPatentId());
			//request.setAttribute("IDKey","1");
		}
		PatentDTO pdto = new PatentDTO();
		pdto.setId(patentinfo.getId());
        pdto.setAppCode(patentinfo.getAppCode());
        if(patentinfo.getAppDate()!=null){      	
        	pdto.setAppDate(patentinfo.getAppDate().toString());
        }
		pdto.setTitleName(patentinfo.getTitlename());
		pdto.setPubCode(patentinfo.getPubCode());
		if(patentinfo.getPubDate()!=null){		
			pdto.setPubDate( patentinfo.getPubDate().toString());
		}
		pdto.setIpc(patentinfo.getIpc());
		pdto.setCatCode(patentinfo.getCatCode());
		pdto.setPriory(patentinfo.getPriory());
		List appPersonList = patentinfo.getKauthors();
		Author author = (Author) appPersonList.get(0);		
		pdto.setAppPerson(author.getAuthorName());
		pdto.setAppAddress(patentinfo.getAppAddress());
		pdto.setInvPerson(patentinfo.getInvPerson());
		pdto.setIntApp(patentinfo.getIntApp());
		pdto.setIntPub(patentinfo.getIntPub());
		pdto.setEntNationDate(patentinfo.getEntNationDate());
		pdto.setSubAgent(patentinfo.getSubAgent());
		pdto.setSubPerson(patentinfo.getSubPerson());
		pdto.setAbstract_(patentinfo.getPatAbstract());
		if(patentinfo.getCerDate()!=null){			
			pdto.setCerDate(patentinfo.getCerDate().toString());
		}
		pdto.setCnType(patentinfo.getCnType());
		pdto.setPagenumber(patentinfo.getPagenumber());		
		pdto.setCategory(patentinfo.getCategory());
		pdto.setFullurl(patentinfo.getFullurl());
		pdto.setFullurltmp(patentinfo.getFullurltmp());
		pdto.setPoweredfullurl(patentinfo.getPoweredfullurl());
		pdto.setPoweredpagenumber(patentinfo.getPoweredpagenumber());	
		
		List<ObjectDTO> keywordsdtolist=new ArrayList<ObjectDTO>();
		Set<Keyword> keywordlist = patentinfo.getKeywords();
		for (Keyword keyword : keywordlist) {
			 ObjectDTO keyworddto=new ObjectDTO(keyword.getId(),keyword.getKeywordName());
			 keywordsdtolist.add(keyworddto);
		}
		pdto.setKeywords(keywordsdtolist);
		//设置flash文件路径
//		pdto.setKnowledgeSourceFilePath(patentinfo.getKnowledgesourcefilepath());
//		pdto.setFlashFilePath(patentinfo.getFlashfilepath());
		JSONUtil ju  = new JSONUtil();
		ju.write(response, pdto);
		return null;
	}
	
	
	/**
	 * 搜索美国专利
	 * jiangdingding 添加2013-5-28
	 */
	public String searchPatentUS() throws Exception{
		String currentpage=pg;
		sSearchContentTmp="Sect1=PTO2&Sect2=HITOFF&p=1&u=%2Fnetahtml%2FPTO%2Fsearch-bool.html&r=0&f=S&l=50";
        		
		 if(fromsearch.equals("2")){//换页
			 sSearchContentTmp = "Sect1=PTO2&Sect2=HITOFF&u=%2Fnetahtml%2FPTO%2Fsearch-adv.htm&r=0&f=S&l=50";
			 String keywordtemp=keyword;
	         keyword=keywordtemp.replaceAll(" ", "+");
	         keywordtemp=keyword.replaceAll("\"", "%252522");
	    	 keyword=keywordtemp.replaceAll("\\(", "%28");
	    	 keywordtemp=keyword.replaceAll("\\)", "%29");  
	  		 keyword=keywordtemp.replaceAll("\\:", "%3A");
	  	     keywordtemp=keyword.replaceAll("\\/", "%2F");
	      	 keyword=keywordtemp.replaceAll("\\?", "%3F");
	      	 keywordtemp=keyword.replaceAll("\\=", "%3D");
	    	 keyword=keywordtemp.replaceAll("\\&", "%2526");
	    	 //&d=PTXT&OS=TTL%2Ftransformer&RS=TTL%2Ftransformer&Query=TTL%2Ftransformer&TD=4042&Srch1=transformer.TI.&NextList3=Next+50+Hits         
			 //1.跳到第几页
	    	 //sSearchContentTmp=sSearchContentTmp+"&d="+d+"&RS="+keyword+"&Query="+keyword+"&TD="+totallist+"&Srch1="+s1+"&StartAt=Jump+To&StartAt=Jump+To&StartNum="+currentpage;
			 //2.转到下一页
	    	 sSearchContentTmp=sSearchContentTmp+"&d="+d+"&OS="+keyword+"&RS="+keyword+"&Query="+keyword+"&TD="+totallist+"&Srch1="+s1+"&NextList"+currentpage+"=Next+50+Hits";
	     }else if(fromsearch.equals("1")){//首页检索 	 
	    	 currentpage = "1";
	    	 HashMap<String, String> jsonMap = (HashMap<String, String>)JSONUtil.read(searchPatentForm);
	    	 TERM1=jsonMap.get("TERM1");       
	    	 FIELD1=jsonMap.get("FIELD1");
	    	 co1=jsonMap.get("co1");
	    	 TERM2=jsonMap.get("TERM2");      
	    	 FIELD2=jsonMap.get("FIELD2");
	    	 d=jsonMap.get("d");
//	    	 String Query=jsonMap.get("Query");
//	    	 String TERM=null;
	    	 
	    	 if (TERM1!=null){
	    		 TERM1=TERM1.replaceAll(" ", "+");
	    	 }
	    	 if (TERM2!=null){
	    		 TERM2=TERM2.replaceAll(" ", "+");
	    	 }
	    	 sSearchContentTmp=sSearchContentTmp+"&TERM1="+TERM1+"&FIELD1="+FIELD1+"&co1="+co1+"&TERM2="+TERM2+"&FIELD2="+FIELD2+"&d="+d;
	     }
		 
    	 UseHttpPost useHttpPost=new UseHttpPost();
    	 useHttpPost.setProxyServer();
    	 useHttpPost.setURL(Constants.usreQuestUrl+sSearchContentTmp);
    	 System.out.println("页面链接地址"+Constants.usreQuestUrl+sSearchContentTmp);
    	 
    	 Vector name_V=new Vector();			
    	 Vector href_V=new Vector();
    	 String getString=useHttpPost.getContent2();
    	 UStifdownload tifdownLoad = new UStifdownload();
    	 try{
    		 tifdownLoad.parserName(getString, name_V,href_V);  //得到链接与专利名、号
    	 }
    	 catch(Exception e){
    		 e.printStackTrace();
    		 System.out.println(getString);
    		 if(getString.indexOf("No patents have matched your query")!=-1)
    		 {
    			 System.out.println("No patents have matched your query");
    			 //返回“无数据”
		 		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		 		hashmap.put("data", null);
		 		hashmap.put("total", "0");
		 		hashmap.put("totalPage", "0");
		 		hashmap.put("currentpage", currentpage);
		 		
		 		JSONUtil ju  = new JSONUtil();
		 		ju.write(response, hashmap);
    		 }
    		 
    		 if(getString.indexOf("META HTTP-EQUIV=\"REFRESH\" CONTENT=\"1;URL=")!=-1)
    		 {
    			 String url=getString.replace("<HTML><HEAD><TITLE>Single Document</TITLE><META HTTP-EQUIV=\"REFRESH\" CONTENT=\"1;URL=", "");
    			 url=url.replace("\"></HEAD></HTML>", "");
    			 url=url.replace("/netacgi/nph-Parser?", "");
    			 System.out.println(url);
    			 
    			 useHttpPost=new UseHttpPost();
    			 useHttpPost.setProxyServer();
    			 useHttpPost.setURL(Constants.usreQuestUrl+url);
    			 
    			 String urltemp = Constants.usreQuestUrl+url;
    			 System.out.println(urltemp);
    			 getString=useHttpPost.getContent2();
    			 String patentid=tifdownLoad.parserpid(getString);
    			 patentid="US"+patentid;
    			 String mispatent="false";
    			 
    			 Patent patent=new Patent(); 
				 patent = patentService.parserus(getString, urltemp);
				 
				 List<Author> authorlist = patent.getKauthors();
				 Author author = authorlist.get(0);
				 PatentDTO pdto = new PatentDTO();
				 pdto.setAppPerson(author.getAuthorName());
				 pdto.setPagenumber(patent.getPagenumber());
				 if(patent.getPubDate()!=null){
					 pdto.setPubDate(patent.getPubDate().toString());
				 }
				 pdto.setTitleName(patent.getTitlename());
				 pdto.setAppCode(patent.getAppCode());
				 if(patent.getAppDate()!=null){
					 pdto.setAppDate(patent.getAppDate().toString());
				 }
				 pdto.setAbstract_(patent.getPatAbstract());
				 pdto.setInvPerson(patent.getInvPerson());
				 pdto.setFullurl(patent.getFullurl());
				 pdto.setIpc(patent.getIpc());
				 pdto.setSubAgent(patent.getSubAgent());
				 pdto.setSubPerson(patent.getSubPerson());
				 pdto.setPatentId(patentid);
				 pdto.setCategory(patent.getCategory());
				 
				 JSONUtil ju  = new JSONUtil();
				 ju.write(response, pdto);
    		 }
    	 }
    	 String totallist=tifdownLoad.parserTotalpage(getString);
    	 int totalpage1;
    	 int totallist1=Integer.valueOf(totallist).intValue();
    	 
    	 float y1=(totallist1/50);
    	 System.out.println("总页数"+y1);
    	 totalpage1=(int)Math.ceil(y1);            //页数凑整
    	 String totalpage=String.valueOf(totalpage1); //获得页数
    	 
    	 String keyword=tifdownLoad.parserKeyword(getString);
    	 String s1=tifdownLoad.parserS1(getString);
    	 String s2=tifdownLoad.parserS2(getString);
    	 
    	List<PatentDTO> pdtos = new ArrayList<PatentDTO>();
 		for (int i =0; i < name_V.size(); i +=2) {
 			PatentDTO pdto=new PatentDTO();
 			pdto.setAppCode("US"+((String)name_V.get(i)).replaceAll(",", ""));
 			pdto.setTitleName((String)name_V.get(i+1));
 			String temp=(String)href_V.get(i);
 			String temp1=temp.replaceAll("&quot;", "%22"); //对专利内容链接地址进行规范处理
 			temp=temp1.replaceAll("\\:", "%3A");
         	temp1=temp.replaceAll("\\/", "%2F");
         	temp=temp1.replaceAll("\\?", "%3F");
         	temp1=temp.replaceAll("\\=", "%3D");
     		temp=temp1.replaceAll("\\&", "%2526");
     		temp1=temp.replaceAll(" ", "+");
     		temp=temp1.replaceAll("\"", "%252522");
     		temp1=temp.replaceAll("\\(", "%28");
     		temp=temp1.replaceAll("\\)", "%29");
 			
     		pdto.setFullurl(temp1);				
     		pdtos.add(pdto);
 		}
 		HashMap<String, Object> hashmap = new HashMap<String, Object>();
 		hashmap.put("data", pdtos);
 		hashmap.put("keyword", keyword);
 		hashmap.put("s1", s1);
 		hashmap.put("s2", s2);
 		hashmap.put("d", d);
 		hashmap.put("total", totallist);
 		hashmap.put("totalPage", totalpage);
 		hashmap.put("currentpage", currentpage);
 		hashmap.put("TERM1", TERM1);
 		hashmap.put("TERM2", TERM2);
 		hashmap.put("co1", co1);
 		
 		JSONUtil ju  = new JSONUtil();
 		ju.write(response, hashmap);
		
		return null;
	}

	/**
	 * 下载美国专利说明书
	 * jiangdingding 添加 2013-5-30
	 */
	public String downUSPatentText() throws Exception{
//		String dir = Constants.dir;
		String patentname=patent_name;
        String patentid=appCode;
 		String url=fullurl;
 		String re_download = "0";//re_download是否重新下载 值为“1”时表示重新下载
		if(redown!=null){
			re_download = redown;
		}
 		
		//解析专利的keyword，保存到知识属性中
 		String key_word = "";
    	if(keyword!=null){
    		key_word = new String(keyword.getBytes("iso-8859-1"),"utf-8");
    	}
    	System.out.println("key_word ="+key_word );
    	if(key_word.indexOf("TTL/")!=-1){
    		if(key_word.indexOf("+")!=-1){
    			key_word = key_word.substring(key_word.indexOf("TTL/")+4,key_word.indexOf("+"));
    		}else{
    			key_word = key_word.substring(key_word.indexOf("TTL/")+4);
    		}
    	}else{
    		key_word = "";
    	}
		
		String dir = Constants.SOURCEFILE_PATH_TEMP;
		String targetDirectory = Constants.SOURCEFILE_PATH;;
		String uuid = UUID.randomUUID().toString();
		String targetFileName = uuid + ".pdf";
		String savepath = dir + "\\" + targetFileName;//临时保存
		String savepath1 = targetDirectory + "\\" + targetFileName;//专利说明书全文保存路径
		String flashfilepath =	uuid + ".swf";
		
		String poweredpagenumber = "";
		String imagepageurl2 = "";
		String Url = "";
		
		//解析url
		String urltemp=url.replaceAll("%26", "\\&");
 		url=urltemp.replaceAll(" ", "\\+");
        urltemp=url;
        urltemp=url.replaceAll(Constants.usreQuestUrl, "");
        url=urltemp.replaceAll("/", "%2F");
        url=url.replaceAll("\\?","");
		
		patentid = patentid.replaceAll(",", "");
		List list = patentService.findByAppCode(patentid);
		
		Patent patent = new Patent();
		if (list.isEmpty()) {		
			System.out.println("专利还不存在，现在下载解析");
			UseHttpPost useHttpPost=new UseHttpPost();
			useHttpPost.setProxyServer();
			useHttpPost.setURL(Constants.usreQuestUrl+url);
//	        System.out.println("为获得某具体专利内容的url地址是："+Constants.usreQuestUrl+url);
		    urltemp=Constants.usreQuestUrl+url;
			String getString=useHttpPost.getContent2();			
	        try {
	        	patent = patentService.parserus(getString, patentname, patentid ,urltemp, key_word);
	    	    //保存专利
	    	    patentService.save(patent);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("专利已经存在，直接从数据库中调取数据");
			patent=(Patent)list.get(0);
	        //判断文件是否存在
			if(patent.getKnowledgesourcefilepath()!=null && !patent.getKnowledgesourcefilepath().equals("")){
				savepath1 = targetDirectory + "\\" +patent.getKnowledgesourcefilepath();
			}
		}

		//判断文件是否存在
		File file = new File(savepath1);
		if (!file.isFile()||re_download.equals("1")) {
			if(!file.isFile()){
				System.out.println("专利全文不存在，现在开始下载");
			}
			if(re_download.equals("1")){
				System.out.println("开始重新下载专利全文");
			}
			poweredpagenumber = patent.getPagenumber();
			imagepageurl2 = patent.getFullurl();
			
			UseHttpPost useHttpPost=new UseHttpPost();
			UStifdownload tif = new UStifdownload();
			tif.setProxyServer();
//			UseHttpPost useHttpPost=new UseHttpPost();
			useHttpPost.setProxyServer();
			useHttpPost.setURL(imagepageurl2);
			String getString1=useHttpPost.getContent2();
			if(imagepageurl2.indexOf("patft.uspto.gov")!=-1){
				imagepageurl2=tif.imagepageurl(getString1);
			}
			else{
				imagepageurl2=tif.imagepageurl2(getString1);
			}
			useHttpPost.setURL(imagepageurl2);
			getString1=useHttpPost.getContent2();
			if(null==poweredpagenumber||poweredpagenumber.equals("无")||poweredpagenumber.equals(""))
			{	poweredpagenumber = tif.images(getString1);
//	        	patent.setPagenumber(poweredpagenumber);
//	        	patentService.save(patent);
			}
			String Docid=tif.docid(getString1);
		    String	IDKey=tif.IDkey(getString1);
			String imgesurl1[]=imagepageurl2.split("\\/");
			imagepageurl2=imgesurl1[2];	
			Url = "http://" + imagepageurl2 + "/.DImg?" + "Docid="
					+ Docid + "&IDKey="+IDKey+"&ImgFormat=tif" + "&PageNum=";
			try {
					patentService.downloadUSPatent(Url,savepath,poweredpagenumber);
					
					//添加patent的knowledgesourcefilepath、Flashfilepath属性
		    	    patent.setKnowledgesourcefilepath(targetFileName);
		    	    patent.setFlashfilepath(flashfilepath);
		    	    patentService.save(patent);
		    	    
					//另存到system_file表中
					file = new File(savepath);
					if (file.isFile()) {								
						fileservcie.save(file, targetFileName);
					}
		    	    
					//转换flash，并从temp文件夹拷贝到soucefile文件夹
					patentService.flashconvert(targetFileName, response);
					
					//更改数据库专利是否已经下载的状态，如果已经下载 mainpower==1
//					List list2 = patentService.findByAppCode(patentid);
//					if(list2.size()>0){
//						for(int i=0;i<list2.size();i++){
//							patent=(Patent)list2.get(i);
//							patent.setMainPower("1");
//							patentService.save(patent);
//						}
//					}	
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}

		}else{System.out.println("专利已经存在本地，可以直接下载");}
			try {
				response.reset();
				response.setCharacterEncoding("UTF-8");

				response.setContentType("application/pdf;charset=gb2312");

				String filename = patentid + ".pdf";

				System.out.println("filename=" + filename);
				response.setHeader("Content-Disposition",
						"attachment; filename="
								+ URLEncoder.encode(filename, "UTF-8") + "");
				file = new File(savepath1);
				FileInputStream fi=new FileInputStream(file);
				BufferedInputStream br = new BufferedInputStream(
						fi);

				byte[] buf = new byte[1024];
				int len = 0;
				OutputStream out = response.getOutputStream();
				while ((len = br.read(buf)) > 0)
					out.write(buf, 0, len);
				out.close();
			//	br.close();
				fi.close();
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
		return null;
	}
	
	
	/**
	 * 搜索美国专利检索详细信息
	 * jiangdingding添加 2013-5-30
	 */
	public String searchPatentDetailUS() throws Exception{
		
		String patentname=patent_name;
        String patentid=appCode;
 		String url=fullurl;
 		
 		//解析专利的keyword，保存到知识属性中
 		String key_word = "";
    	if(keyword!=null){
    		key_word = keyword;
    	}
//    	System.out.println("key_word ="+key_word );
    	if(key_word.indexOf("TTL/")!=-1){
    		if(key_word.indexOf("+")!=-1){
    			key_word = key_word.substring(key_word.indexOf("TTL/")+4,key_word.indexOf("+"));
    		}else{
    			key_word = key_word.substring(key_word.indexOf("TTL/")+4);
    		}
    	}else{
    		key_word = "";
    	}
 		
 		String dir = Constants.SOURCEFILE_PATH_TEMP;
		String uuid = UUID.randomUUID().toString();
		String targetFileName = uuid + ".pdf";
		String flashfilepath =	uuid + ".swf";
 		
 		List list=new ArrayList();
 		patentid=patentid.replaceAll(",", "");
 		list=patentService.findByAppCode(patentid);
 		Patent patent =new Patent();
 		if (list.isEmpty())
		{	
	 	    System.out.println("数据库中没有这个专利,需要解析下载");
	 	   //http://211.157.104.87:8080/netacgi/nph-Parser?Sect1=PTO2&Sect2=HITOFF&p=2&u=%2Fnetahtml%2FPTO%2Fsearch-bool.html&r=67&f=G&l=50&co1=AND&d=PTXT&s1=transformer.TI.&OS=TTL/transformer&RS=TTL/transformer
	 	    
	 	   //http://211.157.104.87:8080/sipo/zljs/hyjs-yx-new.jsp?recid=US8362835&leixin=Sect1=PTO2%26Sect2=HITOFF%26u=/netahtml/PTO/search-adv.htm%26r=52%26f=G%26l=50%26d=PTXT%26s1=transformer.TI.%26p=2%26OS=TTL/transformer%26RS=TTL/transformer
	 		String urltemp=url.replaceAll("%26", "\\&");
	 		url=urltemp.replaceAll(" ", "\\+");
	        urltemp=url;
	        urltemp=url.replaceAll(Constants.usreQuestUrl, "");
	        url=urltemp.replaceAll("/", "%2F");
	        url=url.replaceAll("\\?","");
	
			UseHttpPost useHttpPost=new UseHttpPost();
			useHttpPost.setProxyServer();
			useHttpPost.setURL(Constants.usreQuestUrl+url);
	        System.out.println("为获得某具体专利内容的url地址是："+Constants.usreQuestUrl+url);
		    urltemp=Constants.usreQuestUrl+url;
			String getString=useHttpPost.getContent2();			
	        try {
	        	
	        	patent = patentService.parserus(getString, patentname, patentid ,urltemp, key_word);
	        	//添加knowledgesourcefilepath属性
	    	    patent.setKnowledgesourcefilepath(targetFileName);
	    	    patent.setFlashfilepath(flashfilepath);
	    	    //保存专利
	    	    patentService.save(patent);
//				patent= parser.parserus(urltemp, getString, patentname, patentid,"false");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{//数据库中已存在本条数据,查询
			System.out.println("数据库中已存在本条数据,查询");
			patent=(Patent)list.get(0);
		}
 		
		PatentDTO pdto = new PatentDTO();
		pdto.setId(patent.getId());
        pdto.setAppCode(patent.getAppCode());
        if(patent.getAppDate()!=null){      	
        	pdto.setAppDate(patent.getAppDate().toString());
        }
		pdto.setTitleName(patent.getTitlename());
		pdto.setPubCode(patent.getPubCode());
		if(patent.getPubDate()!=null){		
			pdto.setPubDate( patent.getPubDate().toString());
		}
		pdto.setIpc(patent.getIpc());
		pdto.setCatCode(patent.getCatCode());
		pdto.setPriory(patent.getPriory());
		List appPersonList = patent.getKauthors();
		if(appPersonList.size()>0){
			Author author = (Author) appPersonList.get(0);		
			pdto.setAppPerson(author.getAuthorName());
		}
		pdto.setAppAddress(patent.getAppAddress());
		pdto.setInvPerson(patent.getInvPerson());
		pdto.setIntApp(patent.getIntApp());
		pdto.setIntPub(patent.getIntPub());
		pdto.setEntNationDate(patent.getEntNationDate());
		pdto.setSubAgent(patent.getSubAgent());
		pdto.setSubPerson(patent.getSubPerson());
		pdto.setAbstract_(patent.getPatAbstract());
		if(patent.getCerDate()!=null){			
			pdto.setCerDate(patent.getCerDate().toString());
		}
		pdto.setCnType(patent.getCnType());
		pdto.setPagenumber(patent.getPagenumber());		
		pdto.setCategory(patent.getCategory());
		pdto.setFullurl(patent.getFullurl());
		pdto.setFullurltmp(patent.getFullurltmp());
		pdto.setPoweredfullurl(patent.getPoweredfullurl());
		pdto.setPoweredpagenumber(patent.getPoweredpagenumber());		
		//设置flash文件路径
		pdto.setKnowledgeSourceFilePath(patent.getKnowledgesourcefilepath());
		pdto.setFlashFilePath(patent.getFlashfilepath());
		JSONUtil ju  = new JSONUtil();
		ju.write(response, pdto);
		return null;		

	}
	
	public MetaKnowledge getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	public String getSearchPatentForm() {
		return searchPatentForm;
	}

	public void setSearchPatentForm(String searchPatentForm) {
		this.searchPatentForm = searchPatentForm;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
	}
	
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response=response;
	}


	public String getsSearchContentTmp() {
		return sSearchContentTmp;
	}

	public void setsSearchContentTmp(String sSearchContentTmp) {
		this.sSearchContentTmp = sSearchContentTmp;
	}

	public String getTempurl() {
		return tempurl;
	}

	public void setTempurl(String tempurl) {
		this.tempurl = tempurl;
	}

	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(String totalRecord) {
		this.totalRecord = totalRecord;
	}

	public String getFmRecord() {
		return fmRecord;
	}

	public void setFmRecord(String fmRecord) {
		this.fmRecord = fmRecord;
	}

	public String getXxRecord() {
		return xxRecord;
	}

	public void setXxRecord(String xxRecord) {
		this.xxRecord = xxRecord;
	}

	public String getWgRecord() {
		return wgRecord;
	}

	public void setWgRecord(String wgRecord) {
		this.wgRecord = wgRecord;
	}


	public String getFromsearch() {
		return fromsearch;
	}


	public void setFromsearch(String fromsearch) {
		this.fromsearch = fromsearch;
	}


	public String getSelectbase() {
		return selectbase;
	}


	public void setSelectbase(String selectbase) {
		this.selectbase = selectbase;
	}

	public String getPg() {
		return pg;
	}

	public void setPg(String pg) {
		this.pg = pg;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getPatentType() {
		return patentType;
	}

	public void setPatentType(String patentType) {
		this.patentType = patentType;
	}

	public PatentService getPatentService() {
		return patentService;
	}

	public void setPatentService(PatentService patentService) {
		this.patentService = patentService;
	}

	public FileService getFileservcie() {
		return fileservcie;
	}

	public void setFileservcie(FileService fileservcie) {
		this.fileservcie = fileservcie;
	}

	public ConvertFlashWebService getConvertFlashWebService() {
		return convertFlashWebService;
	}

	public void setConvertFlashWebService(
			ConvertFlashWebService convertFlashWebService) {
		this.convertFlashWebService = convertFlashWebService;
	}


	public KnowledgeService getKnowledgeService() {
		return knowledgeService;
	}


	public void setKnowledgeService(KnowledgeService knowledgeService) {
		this.knowledgeService = knowledgeService;
	}


	public String getD() {
		return d;
	}


	public void setD(String d) {
		this.d = d;
	}


	public String getTERM1() {
		return TERM1;
	}


	public void setTERM1(String tERM1) {
		TERM1 = tERM1;
	}


	public String getFIELD1() {
		return FIELD1;
	}


	public void setFIELD1(String fIELD1) {
		FIELD1 = fIELD1;
	}


	public String getCo1() {
		return co1;
	}


	public void setCo1(String co1) {
		this.co1 = co1;
	}


	public String getTERM2() {
		return TERM2;
	}


	public void setTERM2(String tERM2) {
		TERM2 = tERM2;
	}


	public String getFIELD2() {
		return FIELD2;
	}


	public void setFIELD2(String fIELD2) {
		FIELD2 = fIELD2;
	}


	public String getTotallist() {
		return totallist;
	}


	public void setTotallist(String totallist) {
		this.totallist = totallist;
	}


	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public String getSrch1() {
		return Srch1;
	}


	public void setSrch1(String srch1) {
		Srch1 = srch1;
	}


	public String getS1() {
		return s1;
	}


	public void setS1(String s1) {
		this.s1 = s1;
	}


	public String getS2() {
		return s2;
	}


	public void setS2(String s2) {
		this.s2 = s2;
	}


	public String getPatent_name() {
		return patent_name;
	}


	public void setPatent_name(String patent_name) {
		this.patent_name = patent_name;
	}


	public String getFullurl() {
		return fullurl;
	}


	public void setFullurl(String fullurl) {
		this.fullurl = fullurl;
	}


	public String getRedown() {
		return redown;
	}


	public void setRedown(String redown) {
		this.redown = redown;
	}

	
}
