package edu.zju.cims201.GOF.hibernate.pojo;


public class Component {
	private int id;
	private String name;
	private String description;	
	private int componentlevel;
	private int parentId;
	private boolean __viewicon;
	
	private String icon = "e-icon-fold1";
	private boolean expanded; 
	
	//CalculateResult,碳足迹计算20130602--杨文君
	//private Set<CalculateResult> calculateResult=new HashSet<CalculateResult>();
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String descrption) {
		this.description = descrption;
	}
	
	public boolean is__viewicon() {
		return __viewicon;
	}
	public void set__viewicon(boolean __viewicon) {
		this.__viewicon = __viewicon;
	}
	public int getComponentlevel() {
		return componentlevel;
	}
	public void setComponentlevel(int componentlevel) {
		this.componentlevel = componentlevel;
	}
	
//	public Set<CalculateResult> getCalculateResult() {
//		return calculateResult;
//	}
//	public void setCalculateResult(Set<CalculateResult> calculateResult) {
//		this.calculateResult = calculateResult;
//	}
	
}
