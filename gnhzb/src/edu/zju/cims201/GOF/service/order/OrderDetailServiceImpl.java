package edu.zju.cims201.GOF.service.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.order.OrderDetailDao;
import edu.zju.cims201.GOF.hibernate.pojoA.DemandManage;
import edu.zju.cims201.GOF.hibernate.pojoA.DemandValue;
import edu.zju.cims201.GOF.hibernate.pojoA.OrderDetail;
import edu.zju.cims201.GOF.hibernate.pojoA.OrderManage;
import edu.zju.cims201.GOF.rs.dto.OrderDetailDTO;

@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {
	private OrderDetailDao orderDetailDao;

	public String addOrderDetail(long orderId, long demandId, long demandValueId){
		OrderDetail orderDetail = new OrderDetail();
		OrderManage order = new OrderManage();
		DemandManage demand = new DemandManage();
		DemandValue demandValue = new DemandValue();
		order.setId(orderId);
		demand.setId(demandId);
		demandValue.setId(demandValueId);
		orderDetail.setDemand(demand);
		orderDetail.setDemandValue(demandValue);
		orderDetail.setOrder(order);
		orderDetailDao.save(orderDetail);
		return "增加需求值成功！";
	}
	
	public String updateOrderDetail(long id,long demandValueId){
		OrderDetail od=orderDetailDao.get(id);
		DemandValue dv =new DemandValue();
		dv.setId(demandValueId);
		od.setDemandValue(dv);
		orderDetailDao.save(od);
		return "修改成功！";
		
	}
	
	
	public List<OrderDetailDTO> getOrderDetailByOrderId(long orderId){
		List<OrderDetail> odList = orderDetailDao.find("from OrderDetail od where od.order.id=?", orderId);
		List<OrderDetailDTO> odDTOList = new ArrayList<OrderDetailDTO>();
		for(OrderDetail od:odList){
			OrderDetailDTO odDTO =new OrderDetailDTO();
			OrderManage om = od.getOrder();
			DemandManage dm = od.getDemand();
			DemandValue dv = od.getDemandValue();
			odDTO.setDemandId(dm.getId());
			odDTO.setDemandMemo(dm.getDemandMemo());
			odDTO.setDemandName(dm.getDemandName());
			odDTO.setDemandValueId(dv.getId());
			odDTO.setDemandValueMemo(dv.getDemandValueMemo());
			odDTO.setDemandValueName(dv.getDemandValueName());
			odDTO.setId(od.getId());
			odDTO.setOrderId(om.getId());
			odDTOList.add(odDTO);
		}
		
		return odDTOList;
	}
	
	public String deleteOrderDetail(long id){
		orderDetailDao.delete(id);
		return "删除成功！";
	}
	
	public String deleteAllOrderDetail(long orderId){
		orderDetailDao.batchExecute("delete from OrderDetail od where od.order.id=?", orderId);
		return "全部清空成功！";
	}
	
	
	public OrderDetailDao getOrderDetailDao() {
		return orderDetailDao;
	}
	@Autowired
	public void setOrderDetailDao(OrderDetailDao orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}
	
	
}
