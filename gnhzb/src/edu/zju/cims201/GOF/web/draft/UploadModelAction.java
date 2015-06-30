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
import edu.zju.cims201.GOF.service.draft.DraftTypeService;
import edu.zju.cims201.GOF.service.draft.TreeDraftService;
import edu.zju.cims201.GOF.util.JSONUtil;

public class UploadModelAction extends ActionSupport implements ServletResponseAware{


	private static final long serialVersionUID = -1689091342967910455L;
	private TreeDraftService treeDraftService;
	private HttpServletResponse response;
	PrintWriter out;
	/**
	 * @param file 上传文件
	 */
	private File file;
	/**
	 * @param fileFileName 上传文件名
	 */
	private String fileFileName;
	private String fileContentType;
	
	private Long treeId;
	/**
	 * @param draftName 自定义文件名
	 */
	private String draftName;
	private String description;
	/**
	 * @param id TreeDraft对象的id
	 */
	private Long id;
	
	
	
	
	public String uploadModel() throws Exception{
		String msg =treeDraftService.uploadModel(treeId, draftName, description, file, fileFileName);
		out=response.getWriter();
		out.write(msg);
		return null;
	}
	
	public String getModelByTreeId() throws IOException{
		List<TreeDraft> tds =treeDraftService.getModelByTreeId(treeId);
		String tdsStr =JSONUtil.write(tds);
		
		out=response.getWriter();
		out.print(tdsStr);
		return null;
	};
	
	public TreeDraftService getTreeDraftService(){
		return treeDraftService;
	}
	@Autowired
	public void setTreeDraftService(TreeDraftService treeDraftService) {
		this.treeDraftService = treeDraftService;
	}
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
	}
	public Long getTreeId() {
		return treeId;
	}
	public void setTreeId(Long treeId) {
		this.treeId = treeId;
	}
	public String getDraftName() {
		return draftName;
	}
	public void setDraftName(String draftName) {
		this.draftName = draftName;
	}



	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
