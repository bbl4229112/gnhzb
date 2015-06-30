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
import edu.zju.cims201.GOF.service.partdraft.PartDraftService;
import edu.zju.cims201.GOF.util.JSONUtil;

public class UploadPartModelAction extends ActionSupport implements
		ServletResponseAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7372268722074592293L;
	private HttpServletResponse response;
	private PartDraftService partDraftService;
	PrintWriter out;
	private long partId;
	/**
	 * @param draftName 自定义文件名
	 */
	private String draftName;
	private String description;
	/**
	 * @param file 上传文件
	 */
	private File file;
	/**
	 * @param fileFileName 上传文件名
	 */
	private String fileFileName;
	


	public void uploadPartModel() throws Exception{
		String msg =partDraftService.uploadPartModel(partId, draftName, description, file, fileFileName);
		out=response.getWriter();
		out.print(msg);
	}
	
	public void getDraftByPartId() throws IOException{
		List<PartDraft> pds =partDraftService.getDraftByPartId(partId);
		String pdsStr =JSONUtil.write(pds);
		out = response.getWriter();
		out.print(pdsStr);
	}

	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response = arg0;
	}

	public PartDraftService getPartDraftService() {
		return partDraftService;
	}
	@Autowired
	public void setPartDraftService(PartDraftService partDraftService) {
		this.partDraftService = partDraftService;
	}

	public long getPartId() {
		return partId;
	}

	public String getDraftName() {
		return draftName;
	}

	public String getDescription() {
		return description;
	}

	public File getFile() {
		return file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setPartId(long partId) {
		this.partId = partId;
	}

	public void setDraftName(String draftName) {
		this.draftName = draftName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
}
