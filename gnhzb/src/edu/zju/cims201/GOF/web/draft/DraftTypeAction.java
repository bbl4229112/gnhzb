package edu.zju.cims201.GOF.web.draft;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.hibernate.pojoA.DraftType;
import edu.zju.cims201.GOF.service.draft.DraftTypeService;
import edu.zju.cims201.GOF.util.JSONUtil;

public class DraftTypeAction extends ActionSupport implements ServletResponseAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 217257148237863375L;
	private HttpServletResponse response;
	private DraftTypeService draftTypeService;
	
	private long id;
	private String typeName;
	private String typeCode;
	private String typeSuffix;
	private String typeDes;
	
	PrintWriter out;

	
	public String getAllIsnotMaster() throws IOException{
		List<DraftType> dts=draftTypeService.getAllIsnotMaster();
		String dtsStr =JSONUtil.write(dts);
		out =response.getWriter();
		out.print(dtsStr);
		return null;
	}
	
	public void getAll() throws IOException{
		List<DraftType> dts = draftTypeService.getAll();
		String dtsStr =JSONUtil.write(dts);
		out =response.getWriter();
		out.print(dtsStr);
	}
	
	public void editModel() throws IOException{
		String msg=draftTypeService.editModel(id, typeSuffix, typeDes);
		out =response.getWriter();
		out.print(msg);
	}
	
	public void addSelf() throws IOException{
		String msg=draftTypeService.addSelf(typeName, typeCode, typeSuffix, typeDes);
		out =response.getWriter();
		out.print(msg);
	}
	
	public void editSelf() throws IOException{
		String msg=draftTypeService.editSelf(id, typeName, typeCode, typeSuffix, typeDes);
		out =response.getWriter();
		out.print(msg);
	}
	
	public void deleteSelf() throws IOException{
		String msg=draftTypeService.deleteSelf(id);
		out =response.getWriter();
		out.print(msg);
	}
	
	public void setServletResponse(HttpServletResponse arg0) {
		this.response =arg0;
	}
	
	public DraftTypeService getDraftTypeService() {
		return draftTypeService;
	}
	@Autowired
	public void setDraftTypeService(DraftTypeService draftTypeService) {
		this.draftTypeService = draftTypeService;
	}

	public long getId() {
		return id;
	}

	public String getTypeName() {
		return typeName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public String getTypeSuffix() {
		return typeSuffix;
	}

	public String getTypeDes() {
		return typeDes;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public void setTypeSuffix(String typeSuffix) {
		this.typeSuffix = typeSuffix;
	}

	public void setTypeDes(String typeDes) {
		this.typeDes = typeDes;
	}
}
