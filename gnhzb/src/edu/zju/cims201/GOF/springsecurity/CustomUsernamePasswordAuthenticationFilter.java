package edu.zju.cims201.GOF.springsecurity;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.TextEscapeUtils;
import org.springframework.util.Assert;

import edu.zju.cims201.GOF.service.logging.SysBehaviorLogService;


/**
 * 定制spring security 自身的UsernamePasswordAuthenticationFilter，增加filterProcessesUrlFromClient用以处理
 * 从客户端传来的url
 * @author zju
 *
 */

public class CustomUsernamePasswordAuthenticationFilter extends
	UsernamePasswordAuthenticationFilter {
	
	
	private SysBehaviorLogService sysBehaviorLogService;
	
	private String filterProcessesUrlFromClient;
	
	
	/**
	 * 允许通过get方式登录
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		
		
		super.setPostOnly(false);
		return super.attemptAuthentication(request, response);
		
    }
	
	/**
	 * 决定执行AuthenticationFilter的url
	 */
	
	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String uri=request.getRequestURI();
		if(uri.contains(super.getFilterProcessesUrl())){//处理默认login。action
			super.setAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler());
			return super.requiresAuthentication(request, response);
		}else if(uri.startsWith(request.getContextPath()+this.getFilterProcessesUrlFromClient())&&super.obtainUsername(request)!=null
						&&super.obtainPassword(request)!=null){//处理默认客户端过来的知识搜索url，如果用户未登录则登录
			
			
			String username=null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(authentication==null){//决定用哪个SuccessHandler
				super.setAuthenticationSuccessHandler(new ClientTargetUrlRequestHandler(uri));
				return true;
			}
			
			Object principal = authentication.getPrincipal();
			if(principal==null){//决定用哪个SuccessHandler
				super.setAuthenticationSuccessHandler(new ClientTargetUrlRequestHandler(uri));
				return true;
			}
			if (principal instanceof UserDetails) {
			  username = ((UserDetails)principal).getUsername();
			} else {
			  username = principal.toString();
			}
			String passedUsername=super.obtainUsername(request);
			if(!username.endsWith(passedUsername)){//决定用哪个SuccessHandler
				super.setAuthenticationSuccessHandler(new ClientTargetUrlRequestHandler(uri));
				return true;
			}	
			else
				return false;

		}else {
			return false;
		}
	}

    
    /**
     * 登录成功后的操作，完成用户登录次数的统计
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            Authentication authResult) throws IOException, ServletException {
    	super.successfulAuthentication(request, response, authResult);
    	String email=authResult.getName();
    	this.sysBehaviorLogService.logLogin(email);
    	//System.out.println("ssss"+email);
    	
    	
    }



	public SysBehaviorLogService getSysBehaviorLogService() {
		return sysBehaviorLogService;
	}


	@Autowired
	public void setSysBehaviorLogService(SysBehaviorLogService sysBehaviorLogService) {
		this.sysBehaviorLogService = sysBehaviorLogService;
	}



	public void setFilterProcessesUrlFromClient(String filterProcessesUrlFromClient) {
		this.filterProcessesUrlFromClient = filterProcessesUrlFromClient;
	}

	public String getFilterProcessesUrlFromClient() {
		return filterProcessesUrlFromClient;
	}
	
	
	
	
	
    
    

    
}
