package edu.zju.cims201.GOF.springsecurity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ChineseURLStringConverter {
	
	
	public static void main(String[] args){
		String result=ChineseURLStringConverter.converter("http://localhost:8080/caltks/ui!clientsearch.action?j_username=daifeng@zju.edu.cn&j_password=123456&spring-security-redirect=/ui!clientsearch.action?keyword=%E7%9F%A5%E8%AF%86");
		System.out.println(result);
		
	}
	
	
	public static String converter(String chineseURL){
		
		StringBuffer sb=new StringBuffer();
		String tempStr;
		for(int i=0;i<chineseURL.length();i++){
			tempStr=String.valueOf(chineseURL.charAt(i));
			if(tempStr.getBytes().length==1){
				sb.append(tempStr);
			}else if(tempStr.getBytes().length==2){
				try {
					tempStr=URLEncoder.encode(tempStr, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				sb.append(tempStr);
			}
		}
		
		
		return sb.toString();
		
	}

}
