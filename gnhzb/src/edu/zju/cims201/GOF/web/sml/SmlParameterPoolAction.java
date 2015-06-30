package edu.zju.cims201.GOF.web.sml;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.hibernate.pojoA.SmlParameterPool;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.SmlParameterPoolDTO;
import edu.zju.cims201.GOF.service.sml.SmlParameterPoolService;
import edu.zju.cims201.GOF.util.JSONUtil;

public class SmlParameterPoolAction extends ActionSupport implements
		ServletResponseAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1786212101620271065L;
	HttpServletResponse response;
	PrintWriter out;
	private SmlParameterPoolService  smlParameterPoolService;
	
	private int index;
	private int size;
	private long id;
	private String smlName;
	private String smlCode;
	private long smlCodeId;
	private String dataType;
	private long moduleId;
	private long unitId;
	private double maxValue;
	private double minValue;
	private String defaultValue;
	private String information;
	
	public void addSmlParameter() throws IOException{
		String msg =smlParameterPoolService.addSmlParameter(smlName,smlCode,smlCodeId,dataType,moduleId,unitId,maxValue,minValue,defaultValue,information);
		out =response.getWriter();
		out.print(msg);
	}
	
	public void modifySmlParameter() throws IOException{
		String msg =smlParameterPoolService.modifySmlParameter(id,smlName,smlCode,smlCodeId,dataType,moduleId,unitId,maxValue,minValue,defaultValue,information);
		out =response.getWriter();
		out.print(msg);
	}
	
	public void deleteSmlParameter()throws IOException{
		String msg =smlParameterPoolService.deleteSmlParameter(id);
		out =response.getWriter();
		out.print(msg);
	}
	
	public void getAllSmlParameter() throws IOException{
		PageDTO sppList = smlParameterPoolService.getAllSmlParameter(index,size);
		String sppListStr =JSONUtil.write(sppList);
		out =response.getWriter();
		out.print(sppListStr);
	}
	
	public void getSmlParameterByModId() throws IOException{
		List<SmlParameterPoolDTO> sppList= smlParameterPoolService.getSmlParameterByModId(moduleId);
		String sppListStr =JSONUtil.write(sppList);
		out =response.getWriter();
		System.out.println(sppListStr);
		out.print(sppListStr);
	}
	
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response = arg0;
	}

	public SmlParameterPoolService getSmlParameterPoolService() {
		return smlParameterPoolService;
	}
	@Autowired
	public void setSmlParameterPoolService(SmlParameterPoolService smlParameterPoolService) {
		this.smlParameterPoolService = smlParameterPoolService;
	}

	public int getIndex() {
		return index;
	}

	public int getSize() {
		return size;
	}

	public String getSmlName() {
		return smlName;
	}

	public String getSmlCode() {
		return smlCode;
	}

	public long getSmlCodeId() {
		return smlCodeId;
	}

	public String getDataType() {
		return dataType;
	}

	public long getModuleId() {
		return moduleId;
	}

	public long getUnitId() {
		return unitId;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public double getMinValue() {
		return minValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getInformation() {
		return information;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setSmlName(String smlName) {
		this.smlName = smlName;
	}

	public void setSmlCode(String smlCode) {
		this.smlCode = smlCode;
	}

	public void setSmlCodeId(long smlCodeId) {
		this.smlCodeId = smlCodeId;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}

	public void setUnitId(long unitId) {
		this.unitId = unitId;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
