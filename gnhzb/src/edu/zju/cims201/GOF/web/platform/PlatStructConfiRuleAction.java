package edu.zju.cims201.GOF.web.platform;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.rs.dto.PartDTO;
import edu.zju.cims201.GOF.rs.dto.PartRuleDTO;
import edu.zju.cims201.GOF.service.platform.PlatStructConfiRuleService;
import edu.zju.cims201.GOF.util.JSONUtil;

public class PlatStructConfiRuleAction extends ActionSupport implements
	ServletResponseAware {
	private HttpServletResponse response;
	
	private PlatStructConfiRuleService platStructConfiRuleService;
	PrintWriter out;
	/**
	 * 平台id
	 */
	private long platId;
	/**
	 * 平台节点id
	 */
	private long platStructId;
	private long moduleClassId;
	/**
	 * 零件id
	 */
	private long partId;
	/**
	 * 规则状态
	 * 1：必选
	 * 2：可选
	 * 3：排斥
	 */
	private int status;
	private String classCode;
	private String classText;
	private long partSelectedId;
	private String info;
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getClassText() {
		return classText;
	}
	public void setClassText(String classText) {
		this.classText = classText;
	}
	public long getPartSelectedId() {
		return partSelectedId;
	}
	public void setPartSelectedId(long partSelectedId) {
		this.partSelectedId = partSelectedId;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getPlatId() {
		return platId;
	}
	public void setPlatId(long platId) {
		this.platId = platId;
	}
	public long getPlatStructId() {
		return platStructId;
	}
	public void setPlatStructId(long platStructId) {
		this.platStructId = platStructId;
	}
	public long getModuleClassId() {
		return moduleClassId;
	}
	public void setModuleClassId(long moduleClassId) {
		this.moduleClassId = moduleClassId;
	}
	public long getPartId() {
		return partId;
	}
	public void setPartId(long partId) {
		this.partId = partId;
	}
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response = arg0;
	}
	
	public PlatStructConfiRuleService getPlatStructConfiRuleService() {
		return platStructConfiRuleService;
	}
	@Autowired
	public void setPlatStructConfiRuleService(
			PlatStructConfiRuleService platStructConfiRuleService) {
		this.platStructConfiRuleService = platStructConfiRuleService;
	}
	/**
	 * 根据平台结构树中的节点id和属于该节点的partId得到
	 * @throws IOException 
	 */
	public  void getAllRuleByClass() throws IOException{
		List<PartRuleDTO> partRuleList = platStructConfiRuleService.getAllRuleByClass(platStructId,moduleClassId);
		String partRuleListStr =JSONUtil.write(partRuleList);
		out=response.getWriter();
		out.print(partRuleListStr);
	}
	/**
	 * 根据平台id获取所有模块所属零部件信息（不包括选中模块）
	 * @throws IOException 
	 */
	public void getAllPartsByPlatId() throws IOException{
		String partsString = platStructConfiRuleService.getAllPartsByPlatId(platId,moduleClassId);
		out = response.getWriter();
		out.print(partsString);
	}
	
	public void getSelectedParts() throws IOException{
		List<PartDTO> partList = platStructConfiRuleService.getSelectedParts(platStructId, moduleClassId,partId,status);
		String partListString = JSONUtil.write(partList);
		out = response.getWriter();
		out.print(partListString);
	}
	public void addPartRule() throws IOException{
		String msg =platStructConfiRuleService.addPartRule(platId,platStructId,moduleClassId,classCode,classText,partId,partSelectedId,info,status);
		out = response.getWriter();
		out.print(msg);
	}
	
	public void deletePartRule() throws IOException{
		String msg = platStructConfiRuleService.deletePartRule(platStructId,partId,partSelectedId,status);
		out = response.getWriter();
		out.print(msg);
	}
	

}
