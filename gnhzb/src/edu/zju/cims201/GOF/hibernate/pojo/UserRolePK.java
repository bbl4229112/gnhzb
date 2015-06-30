package edu.zju.cims201.GOF.hibernate.pojo;

public class UserRolePK  implements java.io.Serializable{
	
	private long userId;
	private long roleNodeId;
	
	public UserRolePK(){
	}
	
	public UserRolePK(long userId,long roleNodeId){
		this.roleNodeId=roleNodeId;
		this.userId=userId;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getRoleNodeId() {
		return roleNodeId;
	}
	public void setRoleNodeId(long roleNodeId) {
		this.roleNodeId = roleNodeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (roleNodeId ^ (roleNodeId >>> 32));
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UserRolePK other = (UserRolePK) obj;
		if (roleNodeId != other.roleNodeId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	
	
	
	

}
