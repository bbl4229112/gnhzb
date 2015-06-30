package edu.zju.cims201.GOF.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class UseHttpPost {
    private URL url;

    private URLConnection conn;
    

    public UseHttpPost() {
    	
    }

    public void setURL(String urlAddr) {

        try {
            url = new URL(urlAddr);
      
            conn = url.openConnection();
            

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendPost(String post) {
        conn.setDoInput(true);
        conn.setDoOutput(true);
        PrintWriter output;
        try {
            output = new PrintWriter(conn.getOutputStream());
            output.print(post);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getContent() throws IOException {
        String line, result = "";

            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
           
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
   
        return result;
    }
    public String getContent2() throws IOException {
        String line, result = "";
       
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            while ((line = in.readLine()) != null) {
                result += line;
            }
            in.close();
   
        return result;
    }
    public String getContent3() throws IOException {
        String line, result = "";

            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            while ((line = in.readLine()) != null) {
            	line=line.trim();
            	result += line;
            }
            in.close();
  
        return result;
    }
	/**
	 * 设置代理服务器
	 * 
	 * @param proxy
	 *            String
	 * @param proxyPort
	 *            String
	 */
	public void setProxyServer() {
		// 设置代理服务器
		ProxyUtil.useHttpProxy();

	}

//	public class MyAuthenticator extends Authenticator {
//		private String username, password;
//
//		public MyAuthenticator(String username, String password) {
//			this.username = username;
//			this.password = password;
//		}
//
//		protected PasswordAuthentication getPasswordAuthentication() {
//			return new PasswordAuthentication(username, password.toCharArray());
//		}
//	}
//
//	/**
//	 * 设置认证用户名与密码
//	 * 
//	 * @param uid
//	 *            String
//	 * @param pwd
//	 *            String
//	 */
//
//	public void setAuthenticator(String uid, String pwd) {
//		Authenticator.setDefault(new MyAuthenticator(uid, pwd));
//	}

/*
    public static void main(String[] args) {
        String urlAddr = "http://search.sipo.gov.cn/sipo/zljs/hyjs-jieguo.jsp";
        String post = "flag3=1&selectbase=0&sign=0&recshu=20&searchword=申请（专利权）人=(武汉) and 专利代理机构=(汇泽)&pg=1";

        UseHttpPost test = new UseHttpPost();

        test.setURL(urlAddr);
        test.sendPost(post);
        System.out.println(test.getContent());
    }
    */

}