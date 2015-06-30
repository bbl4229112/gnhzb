package edu.zju.cims201.GOF.rs.dto;
/**
 * 针对规则编辑的part属性
 * @author cqz
 *
 */
public class PartRuleDTO {
	private long id;
	/**
	 * 零部件的编码
	 */
	private String partnumber;
	/**
	 * 零部件的名称
	 */
	private String partname;
	/**
	 * 对应的必须选择的语句
	 */
	private String and;
	/**
	 * 对应的可以选择的语句
	 */
	private String or;
	/**
	 * 对应的不可以选择的语句
	 */
	private String not;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the partnumber
	 */
	public String getPartnumber() {
		return partnumber;
	}
	/**
	 * @param partnumber the partnumber to set
	 */
	public void setPartnumber(String partnumber) {
		this.partnumber = partnumber;
	}
	/**
	 * @return the ppartname
	 */
	public String getPartname() {
		return partname;
	}
	/**
	 * @param ppartname the ppartname to set
	 */
	public void setPartname(String partname) {
		this.partname = partname;
	}
	/**
	 * @return the and
	 */
	public String getAnd() {
		return and;
	}
	/**
	 * @param and the and to set
	 */
	public void setAnd(String and) {
		this.and = and;
	}
	/**
	 * @return the or
	 */
	public String getOr() {
		return or;
	}
	/**
	 * @param or the or to set
	 */
	public void setOr(String or) {
		this.or = or;
	}
	/**
	 * @return the not
	 */
	public String getNot() {
		return not;
	}
	/**
	 * @param not the not to set
	 */
	public void setNot(String not) {
		this.not = not;
	}
}
