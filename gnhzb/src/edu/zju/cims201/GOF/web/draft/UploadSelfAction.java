package edu.zju.cims201.GOF.web.draft;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.hibernate.pojoA.TreeDraft;
import edu.zju.cims201.GOF.service.draft.TreeDraftService;
import edu.zju.cims201.GOF.util.JSONUtil;

public class UploadSelfAction extends ActionSupport implements ServletResponseAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6627669125698196525L;
	private HttpServletResponse response;
	
	private TreeDraftService treeDraftService;
	
	private File self;
	private String selfFileName;
	private String description;
	private String draftName;
	private String typeName;
	private Long treeId;
	
	PrintWriter out;
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
	}
	public String uploadSelf() throws Exception{
		String msg = treeDraftService.uploadSelf(treeId, draftName, description, self, selfFileName, typeName);  
		out=response.getWriter();
		out.write(msg);
		return null;
	}
	
	public String getSelfByTreeId() throws IOException{
		List<TreeDraft> tds =treeDraftService.getSelfByTreeId(treeId); 
		String tdsStr =JSONUtil.write(tds);
		out=response.getWriter();
		out.print(tdsStr);
		return null;
	}
	
	public TreeDraftService getTreeDraftService(){
		return treeDraftService;
	}
	@Autowired
	public void setTreeDraftService(TreeDraftService treeDraftService) {
		this.treeDraftService = treeDraftService;
	}
	public File getSelf() {
		return self;
	}
	public void setSelf(File self) {
		this.self = self;
	}
	public String getSelfFileName() {
		return selfFileName;
	}
	public void setSelfFileName(String selfFileName) {
		this.selfFileName = selfFileName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDraftName() {
		return draftName;
	}
	public void setDraftName(String draftName) {
		this.draftName = draftName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Long getTreeId() {
		return treeId;
	}
	public void setTreeId(Long treeId) {
		this.treeId = treeId;
	}
}
