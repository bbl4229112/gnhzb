package edu.zju.cims201.GOF.springsecurity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import edu.zju.cims201.GOF.hibernate.pojo.RolePrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.UserPrivilegeTriple;
import edu.zju.cims201.GOF.rs.dto.TripleDTO;

/**
 * 本类把TripleDTO转为R_roleID_TreeNodeID_OperationID,U_userId_TreeNodeID_OperationID,A_adminId__TreeNodeID_OperationID
 * 等格式存入ThreadLocal
 * 用TripleDTO等初始化
 * @author zju
 *
 */

public class AuthorityStringMaker {
	
	
	private Set<TripleDTO> userPrivilegeTriples;
	private Set<TripleDTO> rolePrivilegeTriples;
	
	private Set<TripleDTO> adminPrivilegeTriples;
	
	public AuthorityStringMaker(Set<TripleDTO> userPrivilegeTriples,Set<TripleDTO> rolePrivilegeTriples){
		this.userPrivilegeTriples=userPrivilegeTriples;
		this.rolePrivilegeTriples=rolePrivilegeTriples;
	}
	
	
	public AuthorityStringMaker(Set<TripleDTO> userPrivilegeTriples,Set<TripleDTO> rolePrivilegeTriples,Set<TripleDTO> adminPrivilegeTriples){
		this(userPrivilegeTriples,rolePrivilegeTriples);
		this.adminPrivilegeTriples=adminPrivilegeTriples;
	}
	
	
	/**
	 * 格式为R_roleID_TreeNodeID_OperationID,U_userId_TreeNodeID_OperationID,A_adminId__TreeNodeID_OperationID
	 */
	
	public Set<String> makeString(){
		Set<String> setString=new HashSet<String>();
		if(userPrivilegeTriples!=null){
			for(TripleDTO triple:userPrivilegeTriples){
				String temp="U_"+triple.getRoleUserID()+"_"+triple.getTreeNodeID()+"_"+triple.getOperationID();
				setString.add(temp);
			}
		}
		
		if(rolePrivilegeTriples!=null){
			for(TripleDTO triple:rolePrivilegeTriples){
				String temp="R_"+triple.getRoleUserID()+"_"+triple.getTreeNodeID()+"_"+triple.getOperationID();
				setString.add(temp);
			}
		}
		
		
		if(adminPrivilegeTriples!=null){
			for(TripleDTO triple:adminPrivilegeTriples){
				String temp="A_"+triple.getRoleUserID()+"_"+triple.getTreeNodeID()+"_"+triple.getOperationID();
				setString.add(temp);
			}
		}
		
		return setString;
	}
	
	
	
	/**
	 * 用户登录时将读出的权限三元组转换成授权信息
	 * @return
	 */
	public Set<GrantedAuthority> makeAuthority(){
		Set<String> setString=this.makeString();
		Set<GrantedAuthority> authorities=new HashSet<GrantedAuthority>();
		for(String s:setString){
			GrantedAuthority authority=new GrantedAuthorityImpl(s);
			authorities.add(authority);
		}
		return authorities;
	}
	
	
	/**
	 * 访问某个资源时，将操作资源所需权限转为特定格式
	 * @return
	 */
	
	public Set<ConfigAttribute> makeConfigAttribute(){
		Set<String> setString=this.makeString();
		Set<ConfigAttribute> configAttributes=new HashSet<ConfigAttribute>();
		for(String s:setString){
			ConfigAttribute ca = new SecurityConfig(s);
			configAttributes.add(ca);
		}
		return configAttributes;
		
	}
	

}
