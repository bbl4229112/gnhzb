package edu.zju.cims201.GOF.springsecurity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.StringUtils;



/**
 * 从客户端传入的URL，在完成授权后，由本类处理URL跳转
 * @author zju
 *
 */

public class ClientTargetUrlRequestHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	
		
		  public ClientTargetUrlRequestHandler() {
			  super();
		    }

	    
		public ClientTargetUrlRequestHandler(String defaultTargetUrl) {
		    super(defaultTargetUrl);
		}
		
		
		protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
			String targetUrl=super.determineTargetUrl(request, response);
			targetUrl=ChineseURLStringConverter.converter(targetUrl);
			return targetUrl;
		}
		
		


	

	
	
	

	        

}
