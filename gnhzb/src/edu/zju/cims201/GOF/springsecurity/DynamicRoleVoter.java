package edu.zju.cims201.GOF.springsecurity;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import edu.zju.cims201.GOF.util.Constants;

/**
 * 自定义voter，用以处理动态权限
 * @author zju
 *
 */

@Component
public class DynamicRoleVoter implements  AccessDecisionVoter{
	
	private String dynamic="DYNAMIC";

	private DynamicRoleProvider dynamicRoleProvider;

	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		
		//是否为超级管理员
		if(authentication.getName().equals(Constants.SUPERADMIN))
			return ACCESS_GRANTED;
		
		if(attributes==null){
			return ACCESS_ABSTAIN;
			
		}
		for (ConfigAttribute attribute : attributes) {
			if(supports(attribute)){

				//得到object对应的权限
				Collection<ConfigAttribute> dynamicCA=dynamicRoleProvider.getAttributes(object);
				if(dynamicCA==null)
					return ACCESS_ABSTAIN;
				Iterator<ConfigAttribute> iterator=dynamicCA.iterator();
				while(iterator.hasNext()){
					ConfigAttribute configAttribute=iterator.next();
					String neededRole=((SecurityConfig)configAttribute).getAttribute();
					
					//一个个地比对权限字符串
					for(GrantedAuthority ga:authentication.getAuthorities()){
						if(neededRole.equals(ga.getAuthority())){
							return ACCESS_GRANTED;
						}
					}
				}
				return ACCESS_DENIED;	
			}
		}
		
		return ACCESS_ABSTAIN;	
		
	}
	
	/**
	 * 本voter所能处理的url，
	 * 
	 * @param attribute 在配置文件中的配置参数，本类为DYNAMIC
	 */
	public boolean supports(ConfigAttribute attribute) {
		String caString=attribute.getAttribute().trim();
        if ((caString != null) && caString.equals(getDynamic())) {
            return true;
        }
        else {
            return false;
        }
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}


	public DynamicRoleProvider getDynamicRoleProvider() {
		return dynamicRoleProvider;
	}

	@Autowired
	public void setDynamicRoleProvider(DynamicRoleProvider dynamicRoleProvider) {
		this.dynamicRoleProvider = dynamicRoleProvider;
	}


	public String getDynamic() {
		return dynamic;
	}


	public void setDynamic(String dynamic) {
		this.dynamic = dynamic;
	}
	
	
	
	
	
	
	
	
	
	
	

}
