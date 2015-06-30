package edu.zju.cims201.GOF.web.partdraft;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.service.partdraft.PartDraftService;

public class UploadPartImgAction extends ActionSupport implements
		ServletResponseAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = -377604773899335292L;
	private HttpServletResponse response;
	private long partId;
	private File pic;
	private String picFileName;
	private PartDraftService partDraftService;
	private PrintWriter out;

	public void uploadPartImg() throws IOException{
		String msg =partDraftService.uploadPartImg(partId, pic, picFileName);
		out=response.getWriter();
		out.print(msg);
	}
	
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
	}

	public long getPartId() {
		return partId;
	}

	public void setPartId(long partId) {
		this.partId = partId;
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

	public PartDraftService getPartDraftService() {
		return partDraftService;
	}
	@Autowired
	public void setPartDraftService(PartDraftService partDraftService) {
		this.partDraftService = partDraftService;
	}

}
