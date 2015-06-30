package edu.zju.cims201.spider.commontool;

import java.net.Authenticator;

import edu.zju.cims201.GOF.util.Constants;



public class ProxyUtil {
    public static void useHttpProxy(){
    	if(Boolean.valueOf(Constants.HTTPPROXYSET).booleanValue()){
    		System.setProperty("http.proxySet", Constants.HTTPPROXYSET);
    		System.setProperty("http.proxyHost", Constants.HTTPPROXYHOST);
    		System.setProperty("http.proxyPort", Constants.HTTPPROXYPORT);
    		System.getProperties().setProperty("socksProxyHost", Constants.SOCKSPROXYHOST);
    		System.getProperties().setProperty("socksProxyPort", Constants.SOCKSPROXYPORT);
    		
    		if(null!=Constants.PROXYUSERNAME&&!Constants.PROXYUSERNAME.equalsIgnoreCase("")){
    			confirmUser();
            }
    	}		
    }
    
    public static void removeHttpProxy(){
    	System.getProperties().remove("http.proxySet");
    	System.getProperties().remove("http.proxyHost");
    	System.getProperties().remove("http.proxyPort");
    }
    public static void confirmUser() {
		Authenticator.setDefault(new MyAuthenticator(Constants.PROXYUSERNAME,Constants.PROXYPASSWORD));
	}
}
