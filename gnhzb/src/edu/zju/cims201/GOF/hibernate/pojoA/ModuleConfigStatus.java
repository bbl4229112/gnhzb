package edu.zju.cims201.GOF.hibernate.pojoA;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class ModuleConfigStatus {
	private long id;
	private PlatStructTree platStructTree;
	private OrderManage order;
	private String statusName;
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="platStructTree_id")
	public PlatStructTree getPlatStructTree() {
		return platStructTree;
	}
	public void setPlatStructTree(PlatStructTree platStructTree) {
		this.platStructTree = platStructTree;
	}
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="order_id")
	public OrderManage getOrder() {
		return order;
	}
	public void setOrder(OrderManage order) {
		this.order = order;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}
