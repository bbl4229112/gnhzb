package edu.zju.cims201.GOF.springsecurity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

public class CustomClientURLRedirectFilter extends GenericFilterBean {
	
	 private String default_target_parameter = "spring-security-redirect";
	 private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if(requiresRediction(request,response)){
        	System.out.println(request.getRequestURI());
        	String redirectURL=this.determineTargetUrl(request, response);
        	redirectURL=ChineseURLStringConverter.converter(redirectURL);
        	redirectStrategy.sendRedirect(request, response, redirectURL);
        	
        	
        }
        	
		chain.doFilter(request, response);
		
	}
	
	protected boolean requiresRediction(HttpServletRequest request, HttpServletResponse response) {
		String targetURL=request.getParameter(this.getDefault_target_parameter());
		if(targetURL==null)
			return false;
		return true;
	}
	
	/**
     * Builds the target URL according to the logic defined in the main class Javadoc.
     */
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {

        // Check for the parameter and use that if available
        String targetUrl = request.getParameter(default_target_parameter);

        if (StringUtils.hasText(targetUrl)) {
            try {
                targetUrl = URLDecoder.decode(targetUrl, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException("UTF-8 not supported. Shouldn't be possible");
            }

            logger.debug("Found targetUrlParameter in request: " + targetUrl);

            return targetUrl;
        }

        if (!StringUtils.hasText(targetUrl)) {
            targetUrl = default_target_parameter;
            logger.debug("Using default Url: " + targetUrl);
        }

        return targetUrl;
    }

	public String getDefault_target_parameter() {
		return default_target_parameter;
	}

	public void setDefault_target_parameter(String default_target_parameter) {
		this.default_target_parameter = default_target_parameter;
	}

	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}
	

	
	
	

}
