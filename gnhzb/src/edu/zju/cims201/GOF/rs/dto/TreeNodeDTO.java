package edu.zju.cims201.GOF.rs.dto;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TreeNodeDTO implements Comparable<TreeNodeDTO>{
	
	
	//private int __depth;
	//private Long __id;
	private String index;
	private long id;
	private String name;
	private long orderId;
	private boolean __viewicon;
	//private Long __pid;
	//private int __index;
	//private int __preid;
	private String style;
	private int checked = 0;
	//private boolean __viewicon=true;
	private boolean expanded=true;
	private String icon="e-tree-folder";
	private List children;
	private String nodeDescription;
	private Set<TreeNodeDTO> treenodedtos = new TreeSet<TreeNodeDTO>();
	
	private long parentId;

	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getNodeDescription() {
		return nodeDescription;
	}
	public void setNodeDescription(String nodeDescription) {
		this.nodeDescription = nodeDescription;
	}
	public List getChildren() {
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	
	
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int compareTo(TreeNodeDTO o) {
		if(this.getOrderId()==0||o.getOrderId()==0)
			return 0;
		return this.getOrderId()>o.getOrderId()?1:-1;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public Set<TreeNodeDTO> getTreenodedtos() {
		return treenodedtos;
	}
	public void setTreenodedtos(Set<TreeNodeDTO> treenodedtos) {
		this.treenodedtos = treenodedtos;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public boolean is__viewicon() {
		return __viewicon;
	}
	public void set__viewicon(boolean __viewicon) {
		this.__viewicon = __viewicon;
	}
	public boolean get__viewicon() {
		return this.__viewicon;
	}
	
	
	
	
}
