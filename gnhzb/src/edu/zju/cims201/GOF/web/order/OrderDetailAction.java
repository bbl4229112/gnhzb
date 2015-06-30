package edu.zju.cims201.GOF.web.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.hibernate.pojoA.OrderDetail;
import edu.zju.cims201.GOF.rs.dto.OrderDetailDTO;
import edu.zju.cims201.GOF.service.order.OrderDetailService;
import edu.zju.cims201.GOF.util.JSONUtil;

public class OrderDetailAction extends ActionSupport implements
		ServletResponseAware {
	private HttpServletResponse response;
	PrintWriter out;
	private OrderDetailService orderDetailService;
	private long id;
	private long orderId;
	private long demandId;
	private long demandValueId;
	
	public void addOrderDetail() throws IOException{
		String msg =orderDetailService.addOrderDetail(orderId,demandId,demandValueId);
		out = response.getWriter();
		out.print(msg);
	}
	
	public void updateOrderDetail() throws IOException{
		String msg =orderDetailService.updateOrderDetail(id,demandValueId);
		out = response.getWriter();
		out.print(msg);
	}
	
	public void getOrderDetailByOrderId() throws IOException{
		List<OrderDetailDTO> odList=orderDetailService.getOrderDetailByOrderId(orderId);
		String listStr = JSONUtil.write(odList);
		System.out.println(listStr);
		out =response.getWriter();
		out.print(listStr);
	}
	
	public void deleteOrderDetail() throws IOException{
		String msg =orderDetailService.deleteOrderDetail(id);
		out = response.getWriter();
		out.print(msg);
	}
	public void deleteAllOrderDetail() throws IOException{
		String msg =orderDetailService.deleteAllOrderDetail(orderId);
		out = response.getWriter();
		out.print(msg);
	}
	
	
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
	}

	public OrderDetailService getOrderDetailService() {
		return orderDetailService;
	}
	@Autowired
	public void setOrderDetailService(OrderDetailService orderDetailService) {
		this.orderDetailService = orderDetailService;
	}

	public long getOrderId() {
		return orderId;
	}

	public long getDemandId() {
		return demandId;
	}

	public long getDemandValueId() {
		return demandValueId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public void setDemandId(long demandId) {
		this.demandId = demandId;
	}

	public void setDemandValueId(long demandValueId) {
		this.demandValueId = demandValueId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	
}
