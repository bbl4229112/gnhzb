package edu.zju.cims201.GOF.web.partdraft;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.service.partdraft.PartDraftService;
import edu.zju.cims201.GOF.util.Constants;

public class PartDraftAction extends ActionSupport implements
ServletResponseAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5102529666598629476L;

	private HttpServletResponse response;
	private PartDraftService partDraftService;
	/**
	 * @param partId 零部件id
	 */
	private long partId;
	/**
	 * @param id 零部件图文档id
	 */
	private long id;
	private String fileName;
	
	PrintWriter out;
	public void viewPartImg(){
		FileInputStream fis;
		try {
			String url=partDraftService.getPartImgUrl(partId);
			if("".equals(url)){
				fis = new FileInputStream(Constants.UPLOADPARTIMG_PATH+"defalut.jpg");
			}else{
				fis= new FileInputStream(Constants.FILEROOT+url);
			}
			int length =fis.available();
			byte[] data =new byte[length];
			fis.read(data);
			fis.close();
			//response.setContentType("image/*");
			OutputStream fos =response.getOutputStream();
			fos.write(data);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void deleteDraft()throws IOException{
		String msg=partDraftService.deleteDraft(id, fileName);
		out=response.getWriter();
		out.print(msg);
	}

	public void setServletResponse(HttpServletResponse arg0) {
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

	public void setPartId(long partId) {
		this.partId = partId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
