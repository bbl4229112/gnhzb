package edu.zju.cims201.GOF.hibernate.pojoA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * OrderManage的状态
 * 1：待录入
 * 2：待审核
 * 3：待配置
 * 4：已发放
 * 5：失效
 * 6：审核不通过
 * 7：已配置，BOM审核中
 * @author cqz
 *
 */
@Entity
public class Status {
	private long id;
	private String statusName;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
}
