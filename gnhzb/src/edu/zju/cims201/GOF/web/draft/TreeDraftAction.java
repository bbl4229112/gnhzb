package edu.zju.cims201.GOF.web.draft;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.service.draft.TreeDraftService;
import edu.zju.cims201.GOF.util.Constants;

public class TreeDraftAction extends ActionSupport implements
		ServletResponseAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -861517718589504617L;
	private HttpServletResponse response;
	private TreeDraftService treeDraftService;
	private Long id;
	private Long treeId;
	private String fileName;
	
	PrintWriter out;

	public String deleteModel() throws IOException{
		String msg=treeDraftService.deleteModel(id, fileName);
		out=response.getWriter();
		out.print(msg);
		return null;
	}
	public String deleteDeleSelfDefiDoc() throws IOException{
		String msg = treeDraftService.deleteSelfDefiDoc(id, fileName);
		out=response.getWriter();
		out.print(msg);
		return null;
	
	}
	
	public void viewModelImg(){
		
			FileInputStream fis;
			try {
				String url=treeDraftService.getModelImgUrl(treeId);
				if("".equals(url)){
					fis = new FileInputStream(Constants.UPLOADMODELIMG_PATH+"defalut.jpg");
				}else{
					fis= new FileInputStream(Constants.FILEROOT+url);
				}
				int length =fis.available();
				byte[] data =new byte[length];
				fis.read(data);
				fis.close();
				//response.setContentType("image/*");
				OutputStream fos = response.getOutputStream();
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
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public Long getTreeId() {
		return treeId;
	}
	public void setTreeId(Long treeId) {
		this.treeId = treeId;
	}

}
