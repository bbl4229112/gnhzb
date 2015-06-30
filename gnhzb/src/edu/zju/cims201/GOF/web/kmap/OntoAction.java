package edu.zju.cims201.GOF.web.kmap;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springside.modules.orm.Page;
import org.stringtree.json.JSONWriter;
import prefuse.data.Graph;
import prefuse.owl2prefuse.graph.OWLGraphConverter;
import prefuse.owl2prefuse.graph.OWLGtreeBackwardConverter;
import prefuse.owl2prefuse.graph.OWLGtreeConverterNew;
import prefuse.owl2prefuse.graph.OWLGtreeForwardConverter;
import prefuse.owl2prefuse.graph.OWLReGtreeConverter;
import prefuse.owl2prefuse.graph.OWLrelateGraphConverter;




import edu.zju.cims201.GOF.hibernate.pojo.Comment;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Tag;
import edu.zju.cims201.GOF.hibernate.pojo.UserKnowledgeTag;
import edu.zju.cims201.GOF.rs.dto.TagDTO;

import edu.zju.cims201.GOF.service.kmap.KmapService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.comment.CommentService;
import edu.zju.cims201.GOF.service.knowledge.tag.TagService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;

/**
 * 评论管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数. 演示带分页的管理界面.
 * 
 * @author cwd
 */
// 定义URL映射对应/comment/comment.action
@Namespace("/kmap")
// 定义名为reload的result重定向到user.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "tag.action", type = "redirect") }
          

)
public class OntoAction extends CrudActionSupport<Tag> implements ServletResponseAware{

	// CrudActionSupport里的参数
	private static final long serialVersionUID = 8683878162525847072L;
	@Resource(name = "tagServiceImpl")
	private TagService tservice;
	@Resource(name="kmapServiceImpl")
    private KmapService kmapservice;
	@Resource(name = "userServiceImpl")
	private UserService uservice;
	@Resource(name = "knowledgeServiceImpl")
	private KnowledgeService kservice;
    private HttpServletResponse response;
    private Tag entity;
	private Long id;
	

	private String tagname;
	private String owlFileName;
	private String key;
    private String viewtype;
    private String direction;
    private Graph graph;
	@Override
	public String delete() throws Exception {

	

		return RELOAD;
	}

	@Override
	public String input() throws Exception {

		return INPUT;
	}

	// 返回个人知识标签
	@Override
	public String list() throws Exception {
 
		
		
		return null;

	}

	// 返回大众标签
	public String listALL() throws Exception { 
		
		return null;

	}
	
	public String recommentTag() throws Exception {
		
	   List<TagDTO>	tags=new ArrayList<TagDTO>();
		tags = kmapservice.getTagsuggest(key,owlFileName);
        JSONUtil.write(response, tags);
		return null;
	}
	
	public void loadGraph() throws IOException
	{   
		String OWL_FILE = Constants.LOCAL_ONTO_FILE_PATH;
		System.out.println("tagname==="+tagname);
		if (direction.equals("forward")) {
//			System.out.println("正向展示");
		//	OWLReGtreeConverter graphConverter = new OWLReGtreeConverter(OWL_FILE, true, tagname, viewtype);
			
			 long startTime = System.currentTimeMillis();//获取当前时间
			 OWLGtreeForwardConverter graphConverter=new OWLGtreeForwardConverter(OWL_FILE, tagname, viewtype);
				graph = graphConverter.getGraph();
			 long endTime = System.currentTimeMillis();
			 System.out.println("程序运行时间："+(endTime-startTime)+"ms");
			
             System.out.println(graph.getNodeTable());
		}
		if (direction.equals("backward")) {
//			System.out.println("反向展示");
			OWLGtreeBackwardConverter graphConverter = new OWLGtreeBackwardConverter(OWL_FILE,tagname, viewtype);

			graph = graphConverter.getGraph();
		}
		OutputStream out;
		ObjectOutputStream objStream;
		out = response.getOutputStream();
		objStream = new ObjectOutputStream(out);
		objStream.writeObject(graph);	
		
	}
	public void loadRidaGraph() throws IOException
	{	
	
			String OWL_FILE = Constants.LOCAL_ONTO_FILE_PATH;

		 //  tagname="火箭发动机";
			//System.out.println("tagname=="+tagname);
			kmapservice.setOrderrelation(tagname);

			Hashtable <String, String> relatelist= new Hashtable<String, String>();
			relatelist=	kmapservice.getOrderrelation();
			List relatelists=new ArrayList();
			Map<String,String> m_edges=kmapservice.getM_edges();
			
			for (int ii = 1; ii <= relatelist.size()&&ii <= 25; ii++) {

				relatelists.add(relatelist.get(ii + ""));
			}
	
			 OWLrelateGraphConverter graphConverter = new OWLrelateGraphConverter(tagname ,relatelists,m_edges);
		     graph = graphConverter.getGraph();
	
	    	 OutputStream   out;   
	         ObjectOutputStream   objStream;   
	         out   =   response.getOutputStream();   
	         objStream   =   new   ObjectOutputStream(out);   
	         objStream.writeObject(graph);      		
		
	}
	@Override
	protected void prepareModel() throws Exception { 
		

	}

	// 添加个人知识标签
	@Override
	public String save() throws Exception {


		return null;
	}

	public Tag getModel() {
		// TODO Auto-generated method stub
		return entity;
	}
	
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
		
	}

	public TagService getTservice() {
		return tservice;
	}

	public void setTservice(TagService tservice) {
		this.tservice = tservice;
	}

	public UserService getUservice() {
		return uservice;
	}

	public void setUservice(UserService uservice) {
		this.uservice = uservice;
	}

	public KnowledgeService getKservice() {
		return kservice;
	}

	public void setKservice(KnowledgeService kservice) {
		this.kservice = kservice;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getViewtype() {
		return viewtype;
	}

	public void setViewtype(String viewtype) {
		this.viewtype = viewtype;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	public String getOwlFileName() {
		return owlFileName;
	}

	public void setOwlFileName(String owlFileName) {
		this.owlFileName = owlFileName;
	}

}
