package edu.zju.cims201.GOF.web.draft;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.service.draft.TreeDraftService;
import edu.zju.cims201.GOF.util.Constants;

@Result(name="download" ,type="stream",
	params = {  
        "contentType", "application/octet-stream",  
        "inputName", "inputStream",  
        "contentDisposition", "attachment;filename=\"${draftName}\""}	
)
public class DownloadSelfAction extends ActionSupport {

	private static final long serialVersionUID = -3995281807452652608L;
	private Long id;
	private String fileName;
	private String  draftName;
	private TreeDraftService  treeDraftService;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TreeDraftService getTreeDraftService() {
		return treeDraftService;
	}

	@Autowired
	public void setTreeDraftService(TreeDraftService treeDraftService) {
		this.treeDraftService = treeDraftService;
	}
	
	
	public String getDraftName() {
		return draftName;
	}
	public void setDraftName(String draftName) {
		this.draftName = draftName;
	}
	public InputStream getInputStream(){
		fileName =this.getChineseFileName();
		String url = Constants.UPLOADSELF_PATH+fileName;
		String suffix =fileName.substring(fileName.lastIndexOf(".")+1);
		draftName =this.getDraftName()+"."+suffix;
		try {
			return new FileInputStream(url);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getChineseFileName(){
		String ChineseFileName =this.getFileName();
		
		try {
			ChineseFileName =new String(ChineseFileName.getBytes(),"ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ChineseFileName;
	}
	
	public String execute(){
		return "download";
	}
	
}
