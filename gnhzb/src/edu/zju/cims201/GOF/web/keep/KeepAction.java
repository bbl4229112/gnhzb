package edu.zju.cims201.GOF.web.keep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.proxy.HibernateProxy;
import edu.zju.cims201.GOF.hibernate.pojo.KeepTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.rs.dto.KeepTreeNodeDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.service.knowledge.keep.KeepService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;

/**
 * 属性管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数. 
 * 
 * @author panlei
 */
// 定义URL映射对应/ktype/property.action
@Namespace("/knowledge/keep")
// 定义名为reload的result重定向到property.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "knowledge.action", type = "redirect"),@Result(name = "questionview", location = "/aaa.jsp")})
public class KeepAction extends CrudActionSupport<KeepTreeNode> implements
		ServletResponseAware {

	// CrudActionSupport里的参数
	private static final long serialVersionUID = 8683878162525847072L;
	@Resource(name = "userServiceImpl")
	private UserService userservice;
	@Resource(name = "keepServiceImpl")
	private KeepService keepService;	
	
//#######################################################################################################
	//pl 做个知识类型字符串
	private String ktypenamestring;
	public String getKtypenamestring() {
		return ktypenamestring;
	}

	public void setKtypenamestring(String ktypenamestring) {
		this.ktypenamestring = ktypenamestring;
	}
	private String keeptitle;
	public String getKeeptitle() {
		return keeptitle;
	}

	public void setKeeptitle(String keeptitle) {
		this.keeptitle = keeptitle;
	}

	public String getBookmarktitle() {
		return bookmarktitle;
	}

	public void setBookmarktitle(String bookmarktitle) {
		this.bookmarktitle = bookmarktitle;
	}
	private String bookmarktitle;
	
	private Long knowledgeId;
	public Long getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}
	private String bookmarkvalue;
	private String fatherbookmarknodevalue;
	
	public String getBookmarkvalue() {
		return bookmarkvalue;
	}

	public void setBookmarkvalue(String bookmarkvalue) {
		this.bookmarkvalue = bookmarkvalue;
	}

	public String getFatherbookmarknodevalue() {
		return fatherbookmarknodevalue;
	}

	public void setFatherbookmarknodevalue(String fatherbookmarknodevalue) {
		this.fatherbookmarknodevalue = fatherbookmarknodevalue;
	}
	private String nodeId;
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	private Long treenodeid;
	public Long getTreenodeid() {
		return treenodeid;
	}

	public void setTreenodeid(Long treenodeid) {
		this.treenodeid = treenodeid;
	}
	private String nodename;
	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	
	private String newName;
	
	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}
	//#######################################################################################################
	// -- 页面属性 --//
	private Long id;
	// 用于知识采集的知识类型属性
	private Long ktypeid;

	// 用于知识采集的属性内容jason格式
	private String formvalue;

	//知识搜索需要的分页信息
	private int size;
	private int index;
	//全文检索需要的信息
	private String key;

	private HttpServletResponse response;

	private String json;
	//pl 申请改域说明文字############################################################################################
	private String applytext;

	public String getApplytext() {
		return applytext;
	}

	public void setApplytext(String applytext) {
		this.applytext = applytext;
	}
	private String approvalresult;
	public String getApprovalresult() {
		return approvalresult;
	}

	public void setApprovalresult(String approvalresult) {
		this.approvalresult = approvalresult;
	}

	//###############################################################################################################
	
	
	/**
	 * @author panlei
	 * 根据id删除某一条收藏
	 * */
	public String deleteKeepById(){
		String result = "删除成功!";
		try {
			keepService.deleteKeep(treenodeid);
		} catch (Exception e) {
			result = "删除失败!";
		}
		JSONUtil.write(response,result);
		return null;
	}
	
	/**
	 * @author panlei
	 * 根据knowledgeId判断该收藏或者收藏夹是否存在
	 * 
	 * */
	public String keepIsExisted()
	{
		String exist = "收藏";
		JSONUtil ju  = new JSONUtil();
		boolean b = keepService.keepExist(knowledgeId);
		if(b){
			exist = "已收藏";
		}
		ju.write(response, exist);
		return null;
	}
	/**
	 * 根据收藏夹id删除该收藏夹及下所有知识
	 * @param nodeId——收藏夹id
	 * @author panlei
	 * 
	 * */
	public String deleteBookMark(){
		String giveback = "";
		Long nodeid = new Long(nodeId);
		KeepTreeNode ktn = this.keepService.getKeepTreeNode(nodeid);
		Set<KeepTreeNode> set = ktn.getSubNodes();
		if(set.size() > 0){
			deletebookmarkkeep(ktn);
		}else{
			this.keepService.deleteKeep(nodeid);
			giveback = "收藏删除成功！";
		}
		JSONUtil ju  = new JSONUtil();
		ju.write(response, giveback);
		
		return null;
	}
	/**
	 * 根据收藏夹id修改收藏夹名
	 * 
	 * */
	public String renameBookMark(){
		String giveback = "";
		Long nodeid = new Long(nodeId);
		KeepTreeNode ktn = this.keepService.getKeepTreeNode(nodeid);
		try {
			ktn.setNodeName(newName);
			this.keepService.save(ktn);
			giveback = "重命名收藏夹成功！";
		} catch (Exception e) {
			e.printStackTrace();
			giveback = "系统错误，重命名失败！";
		}finally{
			JSONUtil ju  = new JSONUtil();
			ju.write(response, giveback);
		}
		return null;
	}
	/**
	 * 递归删除收藏夹下所有收藏,不包括下层
	 * */
	public void deletebookmarkkeep(KeepTreeNode ktn){
		Set<KeepTreeNode> set = ktn.getSubNodes();
		Object[] arr = set.toArray();
		for(int i = 0;i<set.size();i++){
			KeepTreeNode k = (KeepTreeNode)(arr[i]);
			if(k.getKeepTreeNodeType().equals("keep")){
				this.keepService.deleteKeep(k.getId());
			}
		}
	}
	/**
	 * 递归删除收藏夹下所有收藏，包括下下层
	 * */
	public void deletebookmark(KeepTreeNode ktn){
		Set<KeepTreeNode> set = ktn.getSubNodes();
		Object[] arr = set.toArray();
		for(int i = 0;i<set.size();i++){
			KeepTreeNode k = (KeepTreeNode)(arr[i]);
			deletebookmark(k);
		}
		this.keepService.deleteKeep(ktn.getId());
	}
	/**
	 * @author panlei
	 * 根据节点id，列出该id对应的收藏夹下所有的收藏
	 * */
	public String keepSearch(){
		JSONUtil ju  = new JSONUtil();
		
		KeepTreeNode ktn = keepService.getKeepTreeNode(treenodeid);
		if(ktn == null){
			ju.write(response, null);
		}else{
			Long knowledgeid = ktn.getKnowledgeId();
			boolean b = false;
			if(ktn.getKeepTreeNodeType().equals("keep")){
				b = true;
			}
			PageDTO resultlist = keepService.searchKeepKnowledge(treenodeid,b);
			
			ju.write(response, resultlist);
		}
		
		return null;
	}
	
	/**
	 * @author pl
	 * 新建收藏夹
	 * */
	public String createNewBookmark()throws Exception{
		KeepTreeNode ktn = new KeepTreeNode();
		ktn.setKeepTreeNodeType("bookmark");
		ktn.setNodeDescription(bookmarkvalue);
		ktn.setNodeName(bookmarkvalue);
		Long fatherbookmarkid = Long.parseLong(fatherbookmarknodevalue);
		ktn.setParentId(fatherbookmarkid);
		keepService.save(ktn);
		JSONUtil.write(response,"新建收藏夹成功!");
		return null;
	}	
	
	/**
	 * @author pl
	 * 增加收藏
	 * 
	 * */
	public String createNewKeep() throws Exception{
		Long bookmarkid = Long.parseLong(bookmarktitle);
		String result = "添加收藏成功!";
		List<KeepTreeNode> ktnlist = keepService.getKeepTreeNodesByKnowledgeId(knowledgeId);
		boolean b = false;
		int index = 0;
		int length = ktnlist.size();
		if(length != 0){
			for(int o =0;o<length;o++){
//				if(ktnlist.get(o).getParentId().toString().equals(bookmarktitle)){
				if((long)(ktnlist.get(o).getParentId()) == (long)bookmarkid){
					if(!ktnlist.get(o).getNodeName().equals(keeptitle)){
						b = true;
						index = o;
					}else{
						JSONUtil.write(response,"该收藏已存在!");
						return null;
					}
				}
			}
		}
		KeepTreeNode ktn = new KeepTreeNode();
		if(b){
			Long tid = ktnlist.get(index).getId();
			keepService.deleteKeep(tid);
//			ktn.setId(tid);
			result = "编辑成功!";			
		}
		
		ktn.setKeepTreeNodeType("keep");
		ktn.setKnowledgeId(knowledgeId);
		ktn.setNodeDescription(keeptitle);
		ktn.setNodeName(keeptitle);
		ktn.setParentId(bookmarkid);
		keepService.save(ktn);
		JSONUtil.write(response,result);
		return null;
	}
	
	/**
	 * @author pl
	 * 列出所有收藏夹(有子节点的)
	 * */
	public String listBookmarkTreeNodes() throws Exception{
		SystemUser user = userservice.getUser();
		List<KeepTreeNode> bookmarklist = keepService.listBookmark(user);
		
		JSONUtil.write(response,bookmarklist);
		return null;
	}
	
	/**
	 * @author pl
	 * 列出该收藏夹的所有子收藏夹
	 * */
	public String listSubNodes(){
		if(nodeId=="0" || nodeId.equals(""))
			return null;
		
		KeepTreeNode node= keepService.getKeepTreeNode(Long.parseLong(nodeId));
		ArrayList<KeepTreeNode> list=new ArrayList<KeepTreeNode>();
		list.add(node);
		List<KeepTreeNodeDTO> dtos=new ArrayList<KeepTreeNodeDTO>();
		for(KeepTreeNode treeNode:list){
		
			dtos.add(generateDTO(treeNode,false,false));
			
		}
		Collections.sort(dtos);
		
		JSONUtil.write(response, dtos);
		return null;
	}
	
	private KeepTreeNodeDTO generateDTO(KeepTreeNode treeNode,boolean collapse,boolean isnotroot){
		
		KeepTreeNodeDTO treeNodeDTO=new KeepTreeNodeDTO();
		treeNodeDTO.setKeepTreeNodeType(treeNode.getKeepTreeNodeType());
		treeNodeDTO.setId(treeNode.getId());
		treeNodeDTO.setName(treeNode.getNodeName());
		treeNodeDTO.setOrderId(treeNode.getOrderId());
		if(collapse&&isnotroot)
			treeNodeDTO.setExpanded(!collapse);
		Object proxyObj = treeNode;
		Object  realEntity=null;
		if (proxyObj instanceof HibernateProxy) {  
		      realEntity= ((HibernateProxy)proxyObj).getHibernateLazyInitializer().getImplementation() ;    
		 } else{
			 realEntity=proxyObj;
		 }
		if(treeNode.getKeepTreeNodeType().equals("bookmark") || treeNode.getKeepTreeNodeType().equals("rootbookmark")){
			treeNodeDTO.setIcon("e-tree-folder");
		}else
			treeNodeDTO.setIcon(Constants.keepTreeChildIcon);
		
		Set<KeepTreeNode> set=treeNode.getSubNodes();
//		Iterator<KeepTreeNode> it = set.iterator();
		Set<KeepTreeNode> set2 = set;
		SystemUser user = userservice.getUser();
		Long userid = user.getId();
		Object[] arr = set.toArray();
		for(Object ktn:arr){
			KeepTreeNode ktn123 = (KeepTreeNode)ktn;
			if(!ktn123.getOrderId().equals(userid)){
				set2.remove(ktn123);
			}
		}
			set = set2;
		if(set.size()!=0){
			ArrayList<KeepTreeNodeDTO> arrayList=new ArrayList<KeepTreeNodeDTO>();
			for(KeepTreeNode child:set){
				arrayList.add(generateDTO(child,collapse,true));
			}
			
			Collections.sort(arrayList);
			treeNodeDTO.setChildren(arrayList);
		}
		
		
		return treeNodeDTO;
	}
	//判断是否为收藏夹
	public String isBookMark(){
		Long lo = new Long(nodeId);
		KeepTreeNode ktn = keepService.getKeepTreeNode(lo);
		JSONUtil.write(response, ktn.getKeepTreeNodeType());
		return null;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Long getKtypeid() {
		return ktypeid;
	}

	public void setKtypeid(Long ktypeid) {
		this.ktypeid = ktypeid;
	}



	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public HashMap getJSONvalue()
	{
		//JSONUtil jr  = new JSONUtil();
		HashMap hpropertyValues ;
		try{
			 hpropertyValues = (HashMap) JSONUtil.read(formvalue);}
		catch(Exception e ){
			System.out.println("jason 格式错误！");
			e.printStackTrace();
			return null;
		}
		return hpropertyValues;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}


	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	
	@Override
	public String delete() throws Exception {
		return null;
	}

	
	@Override
	public String input() throws Exception {
		return null;
	}

	@Override
	public String list() throws Exception {
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		
	}

	@Override
	public String save() throws Exception {
		return null;
	}

	public KeepTreeNode getModel() {
		return null;
	}

	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	


}
