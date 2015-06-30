package edu.zju.cims201.GOF.web.partdraft;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.hibernate.pojoA.PartDraft;
import edu.zju.cims201.GOF.hibernate.pojoA.TreeDraft;
import edu.zju.cims201.GOF.service.partdraft.PartDraftService;
import edu.zju.cims201.GOF.util.JSONUtil;

public class UploadPartSelfAction extends ActionSupport implements
		ServletResponseAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8199251663846820856L;
	private HttpServletResponse response;
	private PartDraftService partDraftServic;
	private File self;
	private String selfFileName;
	private String description;
	private String draftName;
	private String typeName;
	private long partId;
	PrintWriter out;
	
	public void uploadPartSelf() throws Exception{
		String msg=partDraftServic.uploadPartSelf(partId, draftName, description, self, selfFileName, typeName);
		out = response.getWriter();
		out.print(msg);
	}
	
	public void getDraftByPartId() throws IOException{
		List<PartDraft> tds =partDraftServic.getDraftByPartId(partId); 
		String tdsStr =JSONUtil.write(tds);
		out=response.getWriter();
		out.print(tdsStr);
	}
	
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
	}

	public PartDraftService getPartDraftServic() {
		return partDraftServic;
	}
	
	@Autowired
	public void setPartDraftServic(PartDraftService partDraftServic) {
		this.partDraftServic = partDraftServic;
	}


	public File getSelf() {
		return self;
	}


	public String getSelfFileName() {
		return selfFileName;
	}


	public String getDescription() {
		return description;
	}


	public String getDraftName() {
		return draftName;
	}


	public String getTypeName() {
		return typeName;
	}


	public long getPartId() {
		return partId;
	}




	public void setSelf(File self) {
		this.self = self;
	}


	public void setSelfFileName(String selfFileName) {
		this.selfFileName = selfFileName;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public void setDraftName(String draftName) {
		this.draftName = draftName;
	}


	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public void setPartId(long partId) {
		this.partId = partId;
	}

	
}
