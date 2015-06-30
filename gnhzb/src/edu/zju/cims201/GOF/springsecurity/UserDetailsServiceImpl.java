package edu.zju.cims201.GOF.springsecurity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

import edu.zju.cims201.GOF.hibernate.pojo.RolePrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.RoleTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.UserPrivilegeTriple;
import edu.zju.cims201.GOF.rs.dto.TripleDTO;
import edu.zju.cims201.GOF.service.privilege.PrivilegeService;
import edu.zju.cims201.GOF.service.privilege.SpringSecurityService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.Constants;

/**
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函数.
 * 
 * @author zju
 */
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserService userService;
	
	
	
	private SessionFactory sessionFactory;
	
	private SpringSecurityService springSecurityService;

	/**
	 * 获取用户Details信息的回调函数.
	 */
	

	
	
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, DataAccessException {

		SystemUser user = userService.getUser(email);
		if (user == null) {
			throw new UsernameNotFoundException("用户" + email + " 不存在");
		}

		Set<GrantedAuthority> grantedAuths = obtainGrantedAuthorities(user);
		if(this.userService.isAdmin(user))
			grantedAuths.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
		else
			grantedAuths.add(new GrantedAuthorityImpl("ROLE_USER"));
		
		if(user.getEmail().equals(Constants.SUPERADMIN)){
			grantedAuths.add(new GrantedAuthorityImpl("ROLE_SUPERADMIN"));
		}

		//-- calkts示例中无以下属性, 暂时全部设为true. --//
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		UserDetails userdetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user
				.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);

		return userdetails;
	}

	/**
	 * 获得用户所有角色的权限集合.格式为R_roleID_TreeNodeID_OperationID,U_userId_TreeNodeID_OperationID
	 * 调用springSecurityService
	 */
	private Set<GrantedAuthority> obtainGrantedAuthorities(SystemUser user) {
		
		
		
		Session session=null;
		Connection connection=null;
		
		Set<TripleDTO> rolePrivilegeTriples=null;
		Set<TripleDTO> userPrivilegeTriples=null;
		Set<TripleDTO> adminPrivilegeTriples=null;
		try {
			session=sessionFactory.openSession();
			connection=session.connection();
			Set<Long> directRoles=springSecurityService.listDirectRoleNodes(user.getId(), connection);
			Set<Long> allRoles=springSecurityService.listParentNodes(directRoles, connection);
			userPrivilegeTriples=springSecurityService.getUserTriples(user.getId(), connection);
			rolePrivilegeTriples=springSecurityService.getRoleTriples(null, null, allRoles, connection);
			if(userService.isAdmin(user)){
				Set<Long> directAdminNodes=springSecurityService.listDirectAdminNodes(user.getId(), connection);
				Set<Long> allAdminNodes=springSecurityService.listSubNodes(directAdminNodes, connection);
				adminPrivilegeTriples=springSecurityService.getAdminTriples(null, null, user.getId(),allAdminNodes, connection);
			}
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			e.printStackTrace();
			
		}finally{
			if(session!=null)
				session.close();
			try {
				if(connection!=null)
					connection.close();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		}
		
		
		
		
		
		//格式化
		
		if(adminPrivilegeTriples==null){
			return new AuthorityStringMaker(userPrivilegeTriples,rolePrivilegeTriples).makeAuthority();
		}
		
		return new AuthorityStringMaker(userPrivilegeTriples,rolePrivilegeTriples,adminPrivilegeTriples).makeAuthority();
		
		
	}
	
	
	
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	

	public SpringSecurityService getSpringSecurityService() {
		return springSecurityService;
	}
	@Autowired
	public void setSpringSecurityService(SpringSecurityService springSecurityService) {
		this.springSecurityService = springSecurityService;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	

	
	
	
	
	
}
