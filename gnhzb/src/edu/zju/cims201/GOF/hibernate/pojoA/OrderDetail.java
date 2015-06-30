package edu.zju.cims201.GOF.hibernate.pojoA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderDetail {
	
	private long id;
	private OrderManage order;
	private DemandManage demand;
	private DemandValue demandValue;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	
	@ManyToOne
	@JoinColumn(name="order_id")
	public OrderManage getOrder(){
		return order;
	}
	@ManyToOne
	@JoinColumn(name="demand_id")
	public DemandManage getDemand() {
		return demand;
	}
	
	@ManyToOne
	@JoinColumn(name="demand_vaule_id")
	public DemandValue getDemandValue() {
		return demandValue;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setOrder(OrderManage order) {
		this.order = order;
	}
	public void setDemand(DemandManage demand) {
		this.demand = demand;
	}
	public void setDemandValue(DemandValue demandValue) {
		this.demandValue = demandValue;
	}
	
	
}
