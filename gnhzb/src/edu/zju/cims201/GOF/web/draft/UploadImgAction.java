package edu.zju.cims201.GOF.web.draft;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.service.draft.TreeDraftService;

public class UploadImgAction extends ActionSupport implements
		ServletResponseAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6328947191064660768L;
	private HttpServletResponse response;
	private TreeDraftService treeDraftService;
	private Long treeId;
	private File pic;
	private String picFileName;
	
	
	PrintWriter out;
	
	public Long getTreeId() {
		return treeId;
	}
	public void setTreeId(Long treeId) {
		this.treeId = treeId;
	}
	public File getPic() {
		return pic;
	}
	public void setPic(File pic) {
		this.pic = pic;
	}
	public String getPicFileName() {
		return picFileName;
	}
	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
	}

	
	public TreeDraftService getTreeDraftService() {
		return treeDraftService;
	}
	@Autowired
	public void setTreeDraftService(TreeDraftService treeDraftService) {
		this.treeDraftService = treeDraftService;
	}
	
	public void uploadImg() throws IOException{
		String msg =treeDraftService.uploadImg(treeId, pic, picFileName);
		out=response.getWriter();
		out.print(msg);
	}
}
