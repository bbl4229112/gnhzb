package edu.zju.cims201.GOF.web.bom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.emory.mathcs.backport.java.util.Arrays;
import edu.zju.cims201.GOF.hibernate.pojoA.BomTemp;
import edu.zju.cims201.GOF.rs.dto.PartDTO;
import edu.zju.cims201.GOF.service.bom.BomTempService;
import edu.zju.cims201.GOF.util.JSONUtil;

public class BomTempAction extends ActionSupport implements ServletResponseAware{
	
	private HttpServletResponse response;
	private BomTempService bomTempService;
	private long platId;
	private long orderId;
	private long platStructId;
	private String partIds;
	private long partId;
	private int partCount;
	PrintWriter out;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
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
	

	public String getPartIds() {
		return partIds;
	}

	public void setPartIds(String partIds) {
		this.partIds = partIds;
	}

	public long getPartId() {
		return partId;
	}

	public void setPartId(long partId) {
		this.partId = partId;
	}

	public int getPartCount() {
		return partCount;
	}

	public void setPartCount(int partCount) {
		this.partCount = partCount;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response =arg0;
	}

	public BomTempService getBomTempService() {
		return bomTempService;
	}
	@Autowired
	public void setBomTempService(BomTempService bomTempService) {
		this.bomTempService = bomTempService;
	}
	
	public void addAll2BomTemp() throws IOException{
		String msg = bomTempService.addAll2BomTemp(platId,orderId);
		out = response.getWriter();
		out.print(msg);
	}
	
	public void getBomTempByPlatIdAndOrderId() throws IOException{
		String msg = bomTempService.getBomTempByPlatIdAndOrderId(platId,orderId);
		out = response.getWriter();
		out.print(msg);
	}
	
	public void deleteBomTempByPlatIdAndOrderId(){
		bomTempService.deleteBomTempByPlatIdAndOrderId(platId,orderId);
	}

	public void getInstance2ChooseByPlatStructId() throws IOException{
		List<PartDTO> bomTempList =bomTempService.getInstance2ChooseByPlatStructId(platStructId, orderId);
		String bomTempListStr = JSONUtil.write(bomTempList);
		out = response.getWriter();
		out.print(bomTempListStr);
		
	}
	
	public void configOrderByPartIds() throws IOException{

		String partIdsStr = partIds.substring(1,partIds.length()-1);
		long[] partIdsArr;
		if(("").equals(partIdsStr)){
			//表示没有选中任何零件
			partIdsArr = new long[]{-1};
		}else{
			String[] partIdsArrStr = partIdsStr.split(",");
			int length = partIdsArrStr.length;
			partIdsArr = new long[length];
			
			for(int i=0;i<length;i++){
				partIdsArr[i] = Long.parseLong(partIdsArrStr[i]);
			}
			
		}

		String msg =  bomTempService.configOrderByPartIds(platStructId,orderId,partIdsArr);
		out = response.getWriter();
		out.print(msg);
	}
	
	
	public void configPartCount(){
		bomTempService.configPartCount(platId,orderId,partId,partCount);
	}
}
