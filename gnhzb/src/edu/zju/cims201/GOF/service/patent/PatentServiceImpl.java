package edu.zju.cims201.GOF.service.patent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.media.jai.codec.SeekableStream;

import edu.zju.cims201.GOF.dao.patent.PatentDao;
import edu.zju.cims201.GOF.hibernate.pojo.Author;
import edu.zju.cims201.GOF.hibernate.pojo.DomainTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledgetype;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.Patent;
import edu.zju.cims201.GOF.hibernate.pojo.SystemFile;
import edu.zju.cims201.GOF.hibernate.pojo.Version;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.service.ServiceException;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.service.systemFile.FileService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.webservice.ConvertFlashWebService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.util.ParsePatent_sipo;
import edu.zju.cims201.GOF.util.UStifdownload;
import edu.zju.cims201.GOF.util.UseHttpPost;

@Service
@Transactional
public class PatentServiceImpl implements PatentService {

	@Resource(name="patentDao")
	private PatentDao patentDao;
	
	@Resource(name = "userServiceImpl")
	private UserService userservice;
	
	@Resource(name = "ktypeServiceImpl")
	private KtypeService ktypeservice;
	
	@Resource(name = "knowledgeServiceImpl")
	private KnowledgeService kservice;
	
	@Resource(name = "fileServiceImpl")
	private FileService fileservcie;
	@Resource(name = "convertFlashWebServiceImpl")
	private ConvertFlashWebService convertFlashWebService;
	
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
	private String cer_date = "";
	private String sub_agent;
	private String sub_person;
	private String priority;
	
	public PatentDao getPatentDao() {
		return patentDao;
	}

	public void setPatentDao(PatentDao patentDao) {
		this.patentDao = patentDao;
	}


	public UserService getUserservice() {
		return userservice;
	}

	public void setUserservice(UserService userservice) {
		this.userservice = userservice;
	}

	public List findByAppCode(String patentid) {
				
		return patentDao.findBy("appCode", patentid);
	}

	public KtypeService getKtypeservice() {
		return ktypeservice;
	}

	public void setKtypeservice(KtypeService ktypeservice) {
		this.ktypeservice = ktypeservice;
	}

	public KnowledgeService getKservice() {
		return kservice;
	}

	public void setKservice(KnowledgeService kservice) {
		this.kservice = kservice;
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

	public void save(Patent instance) {
		// TODO Auto-generated method stub
		patentDao.save(instance);
	}

	public Patent parsercn(String content, String urlstr, String keyword) throws Exception{
		System.out.println("现在开始解析中国专利");
		ParsePatent_sipo parser = new ParsePatent_sipo(
				content);
		// ////////////////////////////////////////////////解析
		String app_code = parser.getApp_code().trim();

		// ParsePatent parser=new ParsePatent(s2);
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		String app_date = parser.getApp_date().trim();
        Date app_dated= format.parse(app_date);   
		String patent_name = parser.getName().trim();

		String pub_code = parser.getPub_code().trim();

		String pub_date = parser.getPub_date();// 5
	    Date pub_dated= format.parse(app_date);   
		String IPC1 = parser.getIPC();// 6
		// IPC1=IPC1.replaceAll("&nbsp","").replaceAll("
		// ","").replaceAll(";","");
		String cat_code = parser.getCat_code();
		// cat_code=cat_code.replaceAll("&nbsp","").replaceAll("
		// ","").replaceAll(";","");

		// String cer_date = parser.getCer_date();// 7
		// String priory = "";

		String priory = parser.getPriory();

		String app_person = parser.getApp_person();// 9

		
		String app_address = parser.getApp_address();// 10

		String inv_person = parser.getInv_person();

		// String int_app = "";
		String int_app = parser.getInt_app();// 12

		// String int_pub = "";
		String int_pub = parser.getInt_pub();// 13

		// String ent_nation_date = "1900-01-01";
		String ent_nation_date = parser
				.getEnt_nation_date();//
		// 14
		// 得到类型

		// String IPC = "";
		// String IPC_part = "", IPC_lclass = "", IPC_sclass
		// = "", outer_lclass = "", outer_sclass = "",
		String cn_type = "";

		int app_index = app_code.indexOf(".");
		int app_code_length = 0;
		// int cn_type_int=0;
		if (app_index != -1) {
			String app_code_temp = app_code.substring(0,
					app_index);
			app_code_length = app_code_temp.length();
			if (app_code_length == 10) {
				cn_type = app_code_temp.substring(4, 5);
			} else {
				cn_type = app_code_temp.substring(6, 7);
			}
		} else {

			if (app_code.length() == 10) {
				cn_type = app_code.substring(4, 5);
			} else {
				cn_type = app_code.substring(6, 7);
			}
		}

		String sub_agent = parser.getSub_agent();
		String sub_person = parser.getSub_person();
		// String cerdate=parser.getCer_date();
		String FullUrlTmp = parser.getFullurl();
		String powerFulTmp = parser.getPoweredurl();
		String fullurl = FullUrlTmp.substring(0, FullUrlTmp
				.indexOf("-"));
		String pagenumber = FullUrlTmp.substring(FullUrlTmp
				.indexOf("-") + 1);
//		System.out.println("fullurl=" + fullurl);

		String poweredpagenumber = "", poweredfullurl = "";
		if (powerFulTmp.trim().length() > 0) {
			poweredfullurl = powerFulTmp.substring(0,
					powerFulTmp.indexOf("-"));
			poweredpagenumber = powerFulTmp
					.substring(powerFulTmp.indexOf("-") + 1);

		} else {
			poweredpagenumber = "0";

		}

		// 下载专利说明书全文代码部分结束
		String _abstract = parser.getAbstract();
		String _mainpower = parser.getMain_power();

		String nation_address[] = new String[] { "日本",
				"德国", "美国", "俄罗斯", "法国", "苏联", "荷兰", "奥地利",
				"澳大利亚", "以色列", "加拿大", "英国", "朝鲜", "韩国",
				"巴西", "白俄罗斯", "阿根廷", "瑞士", "墨西哥", "马来西亚",
				"瑞典", "智利", "喀麦隆", "阿富汗", "哥伦比亚", "哥斯达黎加",
				"古巴", "老挝", "安格拉", "丹麦", "阿尔及利亚", "埃及",
				"西班牙", "芬兰", "阿尔巴尼亚", "格鲁吉亚", "捷克", "希腊",
				"克罗", "匈牙利", "印度尼西亚", "爱尔兰", "菲律宾", "印度",
				"伊拉克", "伊朗", "意大利", "约旦", "葡萄牙", "保加利亚",
				"乌克兰", "泰国", "比利时", "越南", "列支敦士登", "新西兰",
				"摩纳哥", "士耳其", "新加坡", "卢森堡", "英属维尔京群岛",
				"挪威", "巴哈马", "南非", "爱沙尼亚", "人岛(英属)", "波兰",
				"马尔他", "委内瑞拉", "圣马力诺", "大韩民国", "荷属安的列斯库拉索",
				"英属维京群岛托图拉布", "巴哈马", "巴巴多斯百慕大", "斯洛伐克",
				"马耳他", "哈萨克斯坦", "沙特阿拉伯利", "巴巴多斯圣迈克尔",
				"斯洛文尼亚" };

		String area_address[] = new String[] { "黑龙江", "吉林",
				"辽宁", "河南", "河北", "山东", "山西", "陕西", "甘肃",
				"宁夏", "湖南", "湖北", "江西", "安徽", "江苏", "浙江",
				"福建", "台湾", "广东", "广西", "贵州", "云南", "西藏",
				"内蒙古", "新疆", "四川", "北京", "天津", "上海", "海南",
				"重庆", "澳门", "香港", "青海", "中国科学院", "佛山" };
		String app_person_nation = "";
		String app_person_area = "";

		int nation_index = -1;
		// int area_index=0;

		for (int i = 0; i < nation_address.length; i++) {
			if ((app_address.indexOf(nation_address[i])) != -1) {
				app_person_nation = "国外";
				app_person_area = nation_address[i];
				nation_index = i;
				break;

			}

		}

		if (nation_index == -1) {
			app_person_nation = "中国";
			for (int i = 0; i < area_address.length; i++) {
				if ((app_address.indexOf(area_address[i])) != -1) {
					// area_index=i;
					app_person_area = area_address[i];
					break;
				}
				if (i == area_address.length - 1) {
					app_person_area = "中国";
				}
			}

		}
		if (app_person_nation.equals("大韩民国")) {
			app_person_area = "韩国";
		}
		if (app_person_area.equals("中国科学院")) {
			app_person_area = "北京";
		}
		if (app_person_area.equals("深圳")
				|| app_person_area.equals("珠海")
				|| app_person_area.equals("广州")
				|| app_person_area.equals("佛山")) {
			app_person_area = "广东";
		}
		if (app_person_area.equals("青海")) {
			app_person_area = "山东";
		}

		// fso.write(file.getBody().getBytes("iso8859_1"));

		/*
		 * 提取IPC 分类 的部 ，大类 和小类
		 */
        if(app_person_nation.equals("国外"));
        app_person_nation=app_person_area;
   
		String IPC = "";
		String IPC_part = "", IPC_lclass = "", IPC_sclass = "", outer_lclass = "", outer_sclass = ""; // cn_type
																										// =
																										// "";

		IPC1 = IPC1.replaceAll("&nbsp;", "");
		// 修改bug 取出解析中多于的空格&nbsp;
		pub_code = pub_code.replaceAll("&nbsp;", "");
		app_person = app_person.replaceAll("&nbsp;", "");
		app_person = app_person.trim();
		patent_name = patent_name.replaceAll("&nbsp;", "");
		cat_code = cat_code.replaceAll("&nbsp;", "");
		priory = priory.replaceAll("&nbsp;", "");
		app_address = app_address.replaceAll("&nbsp;", "");
		inv_person = inv_person.replaceAll("&nbsp;", "");
		inv_person = inv_person.trim();
		ent_nation_date = ent_nation_date.replaceAll(
				"&nbsp;", "");
		int_pub = int_pub.replaceAll("&nbsp;", "");
		sub_person = sub_person.replaceAll("&nbsp;", "");
		int_app = int_app.replaceAll("&nbsp;", "");
		sub_agent = sub_agent.replaceAll("&nbsp;", "");
		sub_agent = sub_agent.trim();
		_abstract = _abstract.replaceAll("&nbsp;", "");
		_mainpower = _mainpower.replaceAll("&nbsp;", "");

		if (cn_type.equals("3")) {
			try{
			IPC = IPC1;
			outer_lclass = IPC.substring(0, 2);
			outer_sclass = IPC.substring(0, 2)
					+ IPC.substring(3, 5);
			IPC_part = "0";
			IPC_lclass = "0";
			IPC_sclass = "0";
			} 
			catch(Exception e)
			{
				e.printStackTrace();
				outer_lclass = "0";
				outer_sclass = "0";
			}

		} else {
			outer_lclass = "0";
			outer_sclass = "0";
			// IPC1 = IPC1.replaceAll("&nbsp;", "0");
			// System.out.println(IPC1.length());

			IPC = IPC1;
			// System.out.println("+1");
			IPC_part = IPC.substring(0, 1);
//			System.out.println("IPC_part:" + IPC_part);
			IPC_lclass = IPC.substring(0, 3);
//			System.out.println("IPC_lclass:" + IPC_lclass);
			IPC_sclass = IPC.substring(0, 4);
//			System.out.println("IPC_sclass:" + IPC_sclass);

		}
		for (; IPC.indexOf(")") != -1;) {
			IPC = IPC.substring(0, IPC.lastIndexOf("("))
					+ IPC.substring(IPC.lastIndexOf(")") + 1);

		}
			if(IPC.indexOf("I")!=-1)
				IPC=IPC.substring(0, IPC.length()-1);
		Calendar calendar = Calendar.getInstance();
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now =calendar.getTime();
	//	String addtimestr = format.format(calendar.getTime());

		String ispowered = "0";
		String poweredurl = "";
		poweredurl = parser.getPoweredurl();
		if (Integer.valueOf(poweredpagenumber).intValue() > 0) {
			ispowered = "1";
		}
     Patent patent=new Patent();
	
     patent.setPatentId("cn");
     patent.setAppCode(app_code);
     patent.setAppDate(app_dated);
     patent.setTitlename(patent_name);
     patent.setPubCode(pub_code);
     patent.setPubDate(pub_dated);
     patent.setIpc(IPC);
     patent.setCatCode(cat_code);
     patent.setPriory(priory);
     patent.setAppAddress(app_address);
     patent.setInvPerson(inv_person);
     patent.setEntNationDate(ent_nation_date);
     patent.setIntApp(int_app);
     patent.setIntPub(int_pub);
     patent.setSubPerson(sub_person);
     patent.setSubAgent(sub_agent);
     patent.setPatAbstract(_abstract);
     patent.setMainPower(_mainpower);
     patent.setAppPersonNation(app_person_nation);
     patent.setAppPersonArea(app_person_area);
     patent.setCnType(cn_type);
     patent.setIpcPart(IPC_part);
     patent.setIpcLclass(IPC_lclass);
     patent.setIpcSclass(IPC_sclass);
     patent.setOuterOlclass(outer_lclass);
     patent.setOuterOsclass(outer_sclass);
     patent.setUploadtime(now);
     patent.setFullurl(urlstr);
     patent.setPoweredurl(poweredurl);
     patent.setIspowered(ispowered);
     patent.setCategory("cp");
     patent.setFullurltmp(fullurl);
     patent.setPagenumber(pagenumber);
     patent.setPoweredfullurl(poweredfullurl);
     patent.setPoweredpagenumber(poweredpagenumber);
     DomainTreeNode dtn = new DomainTreeNode();
     dtn.setId(3L);
     patent.setDomainnode(dtn);
     
	 //添加kauthors属性
	 List<Author> authorlist = new ArrayList<Author>();					
	 Author authorT = kservice.searchAndSaveAuthor(app_person.trim());
	 authorlist.add(authorT);
	 patent.setKauthors(authorlist);
     // 添加version属性
     Version version = new Version();
	 version.setVersionTime(new Date());
	 version.setVersionNumber("1.0");
     patent.setVersion(version);
     // 添加ktype属性
     Ktype ktype = new Ktype();
     ktype = ktypeservice.getKtypeByKtypeName("专利");
     patent.setKtype(ktype);  
     //添加knowledgetype属性
     Knowledgetype knowledgetype = new Knowledgetype();
     knowledgetype = kservice.SearchAndSaveKnowledgetype("专利");
     patent.setKnowledgetype(knowledgetype);
     // 添加uploader属性
     patent.setUploader(userservice.getUser());
     // 添加uploadtime属性
	 patent.setUploadtime(new Date());
     // 添加keyword属性
	 Set<Keyword> keywordlist = new HashSet<Keyword>();
	 if (null != keyword && !keyword.equals("")) {
			Keyword keywordT = kservice.SearchAndSaveKeyword(keyword.trim());
			keywordlist.add(keywordT);
	 }
     patent.setKeywords(keywordlist);
     //添加securitylevel属性
     patent.setSecuritylevel("非密");
     patent.setStatus("1");
     //添加isvisible属性
     patent.setIsvisible(true);
	 return patent;
	}

	public PageDTO listPatentInDB(HashMap<String,String> propertyValues) throws Exception {
		
		app_code = propertyValues.get("app_code");		
		patent_name = propertyValues.get("patent_name");
		abstractcontent = propertyValues.get("abstractcontent");
		if(propertyValues.get("app_date") != null && !propertyValues.get("app_date").equals("")){			
			app_date = propertyValues.get("app_date");
			app_date = (app_date.split("T"))[0];
			app_date = app_date.replaceAll("-", ".");
		}
		if(propertyValues.get("pub_date") != null && !propertyValues.get("pub_date").equals("")){			
			pub_date = propertyValues.get("pub_date");
			pub_date = (pub_date.split("T"))[0];
			pub_date = pub_date.replaceAll("-", ".");
		}
		pub_code = propertyValues.get("pub_code");
		cat_code = propertyValues.get("cat_code");
		IPC = propertyValues.get("IPC");
		app_person = propertyValues.get("app_person");
		inv_person = propertyValues.get("inv_person");
		app_address = propertyValues.get("app_address");
		int_pub = propertyValues.get("int_pub");
		if(propertyValues.get("cer_date") != null && !propertyValues.get("cer_date").equals("")){			
			cer_date = propertyValues.get("cerdate");
			cer_date = (cer_date.split("T"))[0];
			cer_date = cer_date.replaceAll("-", ".");
		}
		sub_agent = propertyValues.get("sub_agent");
		sub_person = propertyValues.get("sub_person");
		priority = propertyValues.get("priority");
		
		// 根据查询条件构建 hql的内容
		StringBuffer hql = new StringBuffer("");
		hql.append(" o.status !='0' and o.isvisible=true and (");
		// 由于一些级联关系信息，在构建hql时 添加from 中更多的表内容
		StringBuffer hqlfrom = new StringBuffer(" ");
		List<Object> queryParams = new ArrayList<Object>();
		HashMap searchwordtohilght = new HashMap();

		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		if (null!=patent_name) {
			String[] values = patent_name.toString()
					.replaceAll("  ", " ").trim().split(" ");
			for (String string : values) {
				//hql.append("and");
				hql.append("  ( o.titlename like ? ) ");
				queryParams.add("%" + string + "%");
			}
			searchwordtohilght.put("titlename", patent_name);
		}
		
		if (!app_code.equals("")) {
			String[] values = app_code.toString()
					.replaceAll("  ", " ").trim().split(" ");
			for (String string : values) {
				hql.append("and");
				hql.append("  ( o.appCode = ? )");
				queryParams.add(string);
			}
		}

		if (!abstractcontent.equals("")) {
			String[] values = abstractcontent.toString()
					.replaceAll("  ", " ").trim().split(" ");
			for (String string : values) {
				hql.append("and");
				hql.append("  ( o.patAbstract = ? ) ");
				queryParams.add("%" + string + "%");
			}
		}
		if (!app_date.equals("")) {
			Date app_dated= format.parse(app_date);		
			hql.append("and");
			hql.append("  ( o.appDate = ? ) ");
			queryParams.add(app_dated);
			
		}
		if (!pub_date.equals("")) {			
			Date pub_dated= format.parse(pub_date);	
			hql.append("and");
			hql.append("  ( o.pubDate = ? ) ");
			queryParams.add(pub_dated);

		}
		if (!pub_code.equals("")) {
			String[] values = pub_code.toString()
					.replaceAll("  ", " ").trim().split(" ");
			for (String string : values) {
				hql.append("and");
				hql.append("  ( o.pubCode = ? ) ");
				queryParams.add(string);
			}
		}
		if (!cat_code.equals("")) {
			String[] values = cat_code.toString()
					.replaceAll("  ", " ").trim().split(" ");
			for (String string : values) {
				hql.append("and");
				hql.append("  ( o.catCode = ? ) ");
				queryParams.add(string);
			}
		}
		if (!IPC.equals("")) {
			String[] values = IPC.toString()
					.replaceAll("  ", " ").trim().split(" ");
			for (String string : values) {
				hql.append("and");
				hql.append("  ( o.ipc = ? ) ");
				queryParams.add(string);
			}
		}
		if (!app_person.equals("")) {
			String[] values = app_person.toString()
					.replaceAll("  ", " ").trim().split(" ");
			if (hqlfrom.toString().indexOf(",Author author ") == -1)
				hqlfrom.append(" ,Author author ");
			for (String string : values) {
				hql.append("and");
				hql.append(" ( author.id in elements(o.kauthors )   author.authorName like ? ) ");
				queryParams.add("%" + string + "%");

			}
			searchwordtohilght.put("kauthors", app_person);
		}
		if (!inv_person.equals("")) {
			String[] values = inv_person.toString()
					.replaceAll("  ", " ").trim().split(" ");
			for (String string : values) {
				hql.append("and");
				hql.append("  ( o.invPerson = ? ) ");
				queryParams.add("%" + string + "%");
			}
		}
		if (!app_address.equals("")) {
			String[] values = app_address.toString()
					.replaceAll("  ", " ").trim().split(" ");
			for (String string : values) {
				hql.append("and");
				hql.append("  ( o.appAddress = ? ) ");
				queryParams.add("%" + string + "%");
			}
		}
		if (!int_pub.equals("")) {
			String[] values = int_pub.toString()
					.replaceAll("  ", " ").trim().split(" ");
			for (String string : values) {
				hql.append("and");
				hql.append("  ( o.intPub = ? ) ");
				queryParams.add("%" + string + "%");
			}
		}
		if (!cer_date.equals("")) {
			Date cerdated= format.parse(cer_date);	
			hql.append("and");
			hql.append("  ( o.cerDate = ? ) ");
			queryParams.add(cerdated);

		}
		if (!sub_agent.equals("")) {
			String[] values = sub_agent.toString()
					.replaceAll("  ", " ").trim().split(" ");
			for (String string : values) {
				hql.append("and");
				hql.append("  ( o.subAgent = ? ) ");
				queryParams.add("%" + string + "%");
			}
		}
		if (!sub_person.equals("")) {
			String[] values = sub_person.toString()
					.replaceAll("  ", " ").trim().split(" ");
			for (String string : values) {
				hql.append("and");
				hql.append("  ( o.subPerson = ? ) ");
				queryParams.add("%" + string + "%");
			}
		}
		if (!priority.equals("")) {
			String[] values = priority.toString()
					.replaceAll("  ", " ").trim().split(" ");
			for (String string : values) {
				hql.append("and");
				hql.append("  ( o.priory = ? )");
				queryParams.add("%" + string + "%");
			}
		}

		hql.append(")");
		
		PageDTO page = new PageDTO();
		int index = 0;
		int size = Constants.rawPrepage;
		page.setFirstindex(index);
		page.setPagesize(size);
		
		System.out.println("检索hql="+ hql.toString()+"queryParams="+queryParams+"hqlform="+hqlfrom.toString());
		
		Ktype ktype = ktypeservice.getKtypeByKtypeName("专利");
		Knowledge kg = kservice.getExtendKnowledgeByKtype(ktype);
		
		PageDTO pagetable = kservice.searchKnowledge(hqlfrom.toString(), hql.toString(),
				kg.getClass(), page, queryParams.toArray(), searchwordtohilght);

		return pagetable;
	}

	public Patent parserus(String content, String urlstr) throws Exception {
		
		UStifdownload tifdownLoad = new UStifdownload();
		
		String AppCode=tifdownLoad.parserUsCode(content);
		String patentname=tifdownLoad.parserpname(content);		
		String patentid=tifdownLoad.parserpid(content);
		patentid="US"+patentid;
		String App_date=tifdownLoad.parserUsApp_date(content);
		String App_datetemp=App_date.replaceAll("January", "01");
		App_date=App_datetemp.replaceAll("February", "02");
		App_datetemp=App_date.replaceAll("March", "03");
		App_date=App_datetemp.replaceAll("April", "04");
		App_datetemp=App_date.replaceAll("May", "05");
		App_date=App_datetemp.replaceAll("June", "06");
		App_datetemp=App_date.replaceAll("July", "07");
		App_date=App_datetemp.replaceAll("August", "08");
		App_datetemp=App_date.replaceAll("September", "09");
		App_date=App_datetemp.replaceAll("October", "10");
		App_datetemp=App_date.replaceAll("November", "11");
		App_date=App_datetemp.replaceAll("December", "12");
		String App_datetemp1[]=App_date.split(" ");
		App_date=App_datetemp1[2].trim()+"-"+App_datetemp1[0].replace(",", "").trim()+"-"+App_datetemp1[1].trim();		
		Date App_date1 = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
        	App_date1 = format.parse(App_date);
           
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        
		String Abstract1=tifdownLoad.parserAbstract(content);		
		String InvPerson=tifdownLoad.parserInventor(content);		
		String App_Person=tifdownLoad.parserApp_Person(content).trim();												
		String IPC=tifdownLoad.parserIPC(content);	
		IPC=IPC.replaceAll("&nbsp;", " ");
		IPC=IPC.replaceAll("&nbsp", " ");	
		String Agent=tifdownLoad.parserAgent(content);	
		String pub_date=tifdownLoad.parserPub_date(content);

		String pub_datetemp=pub_date.replaceAll("January", "01");
		pub_date=pub_datetemp.replaceAll("February", "02");
		pub_datetemp=pub_date.replaceAll("March", "03");
		pub_date=pub_datetemp.replaceAll("April", "04");
		pub_datetemp=pub_date.replaceAll("May", "05");
		pub_date=pub_datetemp.replaceAll("June", "06");
		pub_datetemp=pub_date.replaceAll("July", "07");
		pub_date=pub_datetemp.replaceAll("August", "08");
		pub_datetemp=pub_date.replaceAll("September", "09");
		pub_date=pub_datetemp.replaceAll("October", "10");
		pub_datetemp=pub_date.replaceAll("November", "11");
		pub_date=pub_datetemp.replaceAll("December", "12");
		String pub_datetemp1[]=pub_date.split(" ");
		pub_date=pub_datetemp1[2].trim()+"-"+pub_datetemp1[0].replace(",", "").trim()+"-"+pub_datetemp1[1].trim();
		
		Date pub_date1 = null;
        try {
        	pub_date1 = format.parse(pub_date);
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        
        String imagepageurl=tifdownLoad.imagepageurl(content);
              
        Patent patent = new Patent();
        
    	patent.setPatentId("us");
        patent.setAppCode(patentid);
		patent.setTitlename(patentname);
		patent.setPubCode(AppCode);		
		patent.setAppDate(App_date1);
		patent.setPatAbstract(Abstract1);	
		patent.setInvPerson(InvPerson);		
		patent.setIpc(IPC);	
		patent.setSubAgent(Agent);
		patent.setPubDate(pub_date1);
        patent.setCategory("fp");
        patent.setFullurl(urlstr);
        
        UseHttpPost useHttpPost=new UseHttpPost();
		useHttpPost.setProxyServer();
		useHttpPost.setURL(urlstr);
		patent.setFullurltmp(imagepageurl);						
		useHttpPost.setURL(imagepageurl);
		String getString1=useHttpPost.getContent2();		
		String poweredpagenumber=tifdownLoad.images(getString1);
		patent.setPagenumber(poweredpagenumber);
		String IDKey=tifdownLoad.IDkey(getString1);
		
		DomainTreeNode dtn = new DomainTreeNode();
	    dtn.setId(3L);
	    patent.setDomainnode(dtn);
	     
	   	//添加kauthors属性
	   	List<Author> authorlist = new ArrayList<Author>();					
	   	Author authorT = kservice.searchAndSaveAuthor(App_Person.trim());
	   	authorlist.add(authorT);
	   	patent.setKauthors(authorlist);
        // 添加version属性
        Version version = new Version();
	   	version.setVersionTime(new Date());
	    version.setVersionNumber("1.0");
        patent.setVersion(version);
        // 添加ktype属性
        Ktype ktype = new Ktype();
        ktype = ktypeservice.getKtypeByKtypeName("专利");
        patent.setKtype(ktype);  
        //添加knowledgetype属性
        Knowledgetype knowledgetype = new Knowledgetype();
        knowledgetype = kservice.SearchAndSaveKnowledgetype("专利");
        patent.setKnowledgetype(knowledgetype);
        // 添加uploader属性
        patent.setUploader(userservice.getUser());
        // 添加uploadtime属性
        patent.setUploadtime(new Date());
        // 添加keyword属性
        String keyword = "un";    
	   	 Set<Keyword> keywordlist = new HashSet<Keyword>();
	   	 if (null != keyword && !keyword.equals("")) {
	   			Keyword keywordT = kservice.SearchAndSaveKeyword(keyword.trim());
	   			keywordlist.add(keywordT);
	   	 }
        patent.setKeywords(keywordlist);
        //添加securitylevel属性
        patent.setSecuritylevel("非密");
        patent.setStatus("1");
        //添加isvisible属性
        patent.setIsvisible(true);        
               
		return patent;
	}

	public Patent parserus(String content, String patentname, String patentid,
			String urlstr, String keyword) throws Exception {
		UStifdownload tifdownLoad = new UStifdownload();
		
		String AppCode=tifdownLoad.parserUsCode(content);
		String App_date=tifdownLoad.parserUsApp_date(content);
		String App_datetemp=App_date.replaceAll("January", "01");
		App_date=App_datetemp.replaceAll("February", "02");
		App_datetemp=App_date.replaceAll("March", "03");
		App_date=App_datetemp.replaceAll("April", "04");
		App_datetemp=App_date.replaceAll("May", "05");
		App_date=App_datetemp.replaceAll("June", "06");
		App_datetemp=App_date.replaceAll("July", "07");
		App_date=App_datetemp.replaceAll("August", "08");
		App_datetemp=App_date.replaceAll("September", "09");
		App_date=App_datetemp.replaceAll("October", "10");
		App_datetemp=App_date.replaceAll("November", "11");
		App_date=App_datetemp.replaceAll("December", "12");
		String App_datetemp1[]=App_date.split(" ");
		App_date=App_datetemp1[2].trim()+"-"+App_datetemp1[0].replace(",", "").trim()+"-"+App_datetemp1[1].trim();		
		Date App_date1 = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
        	App_date1 = format.parse(App_date);
           
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        
		String Abstract1=tifdownLoad.parserAbstract(content);		
		String InvPerson=tifdownLoad.parserInventor(content);		
		String App_Person=tifdownLoad.parserApp_Person(content).trim();												
		String IPC=tifdownLoad.parserIPC(content);	
		IPC=IPC.replaceAll("&nbsp;", " ");
		IPC=IPC.replaceAll("&nbsp", " ");	
		String Agent=tifdownLoad.parserAgent(content);	
		String pub_date=tifdownLoad.parserPub_date(content);

		String pub_datetemp=pub_date.replaceAll("January", "01");
		pub_date=pub_datetemp.replaceAll("February", "02");
		pub_datetemp=pub_date.replaceAll("March", "03");
		pub_date=pub_datetemp.replaceAll("April", "04");
		pub_datetemp=pub_date.replaceAll("May", "05");
		pub_date=pub_datetemp.replaceAll("June", "06");
		pub_datetemp=pub_date.replaceAll("July", "07");
		pub_date=pub_datetemp.replaceAll("August", "08");
		pub_datetemp=pub_date.replaceAll("September", "09");
		pub_date=pub_datetemp.replaceAll("October", "10");
		pub_datetemp=pub_date.replaceAll("November", "11");
		pub_date=pub_datetemp.replaceAll("December", "12");
		String pub_datetemp1[]=pub_date.split(" ");
		pub_date=pub_datetemp1[2].trim()+"-"+pub_datetemp1[0].replace(",", "").trim()+"-"+pub_datetemp1[1].trim();
		
		Date pub_date1 = null;
        try {
        	pub_date1 = format.parse(pub_date);
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        
        String imagepageurl=tifdownLoad.imagepageurl(content);
              
        Patent patent = new Patent();
        
    	patent.setPatentId("us");
        patent.setAppCode(patentid);
		patent.setTitlename(patentname);
		patent.setPubCode(AppCode);		
		patent.setAppDate(App_date1);
		patent.setPatAbstract(Abstract1);	
		patent.setInvPerson(InvPerson);		
		patent.setIpc(IPC);	
		patent.setSubAgent(Agent);
		patent.setPubDate(pub_date1);
        patent.setCategory("fp");
        patent.setFullurl(urlstr);
        
        UseHttpPost useHttpPost=new UseHttpPost();
		useHttpPost.setProxyServer();
		useHttpPost.setURL(urlstr);
		patent.setFullurltmp(imagepageurl);						
		useHttpPost.setURL(imagepageurl);
		String getString1=useHttpPost.getContent2();		
		String poweredpagenumber=tifdownLoad.images(getString1);
		patent.setPagenumber(poweredpagenumber);
		String IDKey=tifdownLoad.IDkey(getString1);
		
		DomainTreeNode dtn = new DomainTreeNode();
	    dtn.setId(3L);
	    patent.setDomainnode(dtn);
	   	//添加kauthors属性
	   	List<Author> authorlist = new ArrayList<Author>();					
	   	Author authorT = kservice.searchAndSaveAuthor(App_Person.trim());
	   	authorlist.add(authorT);
	   	patent.setKauthors(authorlist);
        // 添加version属性
        Version version = new Version();
	   	version.setVersionTime(new Date());
	    version.setVersionNumber("1.0");
        patent.setVersion(version);
        // 添加ktype属性
        Ktype ktype = new Ktype();
        ktype = ktypeservice.getKtypeByKtypeName("专利");
        patent.setKtype(ktype);  
        //添加knowledgetype属性
        Knowledgetype knowledgetype = new Knowledgetype();
        knowledgetype = kservice.SearchAndSaveKnowledgetype("专利");
        patent.setKnowledgetype(knowledgetype);
        // 添加uploader属性
        patent.setUploader(userservice.getUser());
        // 添加uploadtime属性
        patent.setUploadtime(new Date());
        // 添加keyword属性
	   	 Set<Keyword> keywordlist = new HashSet<Keyword>();
	   	 if (null != keyword && !keyword.equals("")) {
	   			Keyword keywordT = kservice.SearchAndSaveKeyword(keyword.trim());
	   			keywordlist.add(keywordT);
	   	 }
        patent.setKeywords(keywordlist);
        //添加securitylevel属性
        patent.setSecuritylevel("非密");
        patent.setStatus("1");
        //添加isvisible属性
        patent.setIsvisible(true);        
               
		return patent;
	}

	public void downloadUSPatent(String url, String savepath, String poweredpagenumber) throws Exception {
		try{
		PdfContentByte cb;
		Document document = new Document();
		PdfWriter writer;
		writer = PdfWriter.getInstance(document, new FileOutputStream(
				savepath));
		document.open();
		cb = writer.getDirectContent();

		int spagenumbertemp = Integer
				.parseInt(poweredpagenumber.trim());
		int j = spagenumbertemp;
		System.out.println("开始下载美国专利图片资源，可能会花费几秒到几分钟");
		for (int i = 1; i <= j; i++) {
			
			String Urltemp = url + i;
//			System.out.println("imgurl" + i + ":" + Urltemp);
			HttpURLConnection httpUrl = null;
			URL url1 = null;
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

			cb.addImage(img);
			document.newPage();
			stream.close();
		
			httpUrl.disconnect();
	
		}document.close();
	}catch(IOException er){
		System.out.println("资源[" + url + "]下载失败!!!");
	}

}

    public void flashconvert(String filename,HttpServletResponse response)
    {
    	String flashname = "";
    	if (null != filename) {
			if(null!=Constants.FLASHCONVERTMETHOD&&Constants.FLASHCONVERTMETHOD.equals("local")){
//*********************************通过本地转换flash****************************************8	
//			 首先判断是否符合抽取的文档 doc
			if (filename.indexOf(".") != -1) {
				
					flashname=filename.substring(0,filename.lastIndexOf("."))+".swf";
					String sourceext=filename.substring(filename.lastIndexOf("."));
					System.out.println("flashname是："+flashname);
					int result = kservice.convertDOC2SWF(filename,flashname);
					if (result==3){
					//将转换好的flash存入到数据库
						
						fileservcie.convertFile( filename, flashname,sourceext);
						
//						JSONUtil.write(response, "转换成功");
						System.out.println( "转换成功");
				
						
						
					}
					else{
//						JSONUtil.write(response, "可能文件格式不支持");
						System.out.println( "可能文件格式不支持");
					}
				
			} else {
//				JSONUtil.write(response, "可能文件格式不支持");
				System.out.println( "可能文件格式不支持");
			}
			}
//*******************************t通过webservice转换flash*****************************************************8		
			else{
			if (filename.indexOf(".") != -1) {
		    String docfilename = filename.substring(0, filename.lastIndexOf("."));
			String filetype = filename.substring(filename.lastIndexOf("."));
			try{	
	     	SystemFile sf=fileservcie.getFile(docfilename, filetype);
	     	if(null!=sf){
			String	flashfilename=convertFlashWebService.ConvertAndSaveFlash(sf.getId());
				
				if(null!=flashfilename&&!flashfilename.equals("fail")){
//					JSONUtil.write(response, "转换成功");
					System.out.println("转换成功");
					}
				else{
//					JSONUtil.write(response, "可能文件格式不支持");
					System.out.println( "可能文件格式不支持");
					}
	     	}
	     	   else{
	     		  throw new ServiceException("系统错误，没有得到文件名");
	     		   
	     	   }
				}catch (Exception e) {
					e.printStackTrace();
				}	
			} else {
//				JSONUtil.write(response, "可能文件格式不支持");
				System.out.println("可能文件格式不支持");
			}
			}	
		   	// 转换成功的话 将flash 、源文件、附件 从temp文件中拷贝到存储文件中
			// 将源文件从临时文件夹中转移到源文件文件夹中
			System.out.println("源文件从临时文件夹拷贝到正式文件夹开始！");
			if (filename != null) {
				kservice.moveTemp2Target(Constants.SOURCEFILE_PATH_TEMP,
						Constants.SWF_PATH, filename);
				System.out.println("源文件从临时文件夹拷贝到正式文件夹成功！");
			} else {
				System.out.println("不存在源文件路径");
			}
			System.out.println("flash文件从临时文件夹拷贝到正式文件夹开始！");
			if (flashname != null) {

				// 判断是否有flash文件 如果没有则不移动将flash名称更改为文件名称
				File tempFilea = new File(Constants.SOURCEFILE_PATH_TEMP + "\\"
						+ flashname);
				if (tempFilea.exists()) {

					kservice.moveTemp2Target(Constants.SOURCEFILE_PATH_TEMP,
							Constants.SOURCEFILE_PATH, flashname);
					System.out.println("flash文件从临时文件夹拷贝到正式文件夹成功！");
				}

				else {
					
					System.out.println("flash文件没有找到，将flash名称更改为文件名称！");
				}

			} else {
				System.out.println("不存在flash文件路径");
			}
		}
    }







}	
