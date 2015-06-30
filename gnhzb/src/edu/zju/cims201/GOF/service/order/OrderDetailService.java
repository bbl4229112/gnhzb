package edu.zju.cims201.GOF.service.order;

import java.util.List;

import edu.zju.cims201.GOF.rs.dto.OrderDetailDTO;

public interface OrderDetailService {

	public String addOrderDetail(long orderId, long demandId, long demandValueId);

	public List<OrderDetailDTO> getOrderDetailByOrderId(long orderId);

	public String updateOrderDetail(long id,long demandValueId);

	public String deleteOrderDetail(long id);

	public String deleteAllOrderDetail(long orderId);

}
