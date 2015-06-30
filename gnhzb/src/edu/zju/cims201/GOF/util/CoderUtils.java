package edu.zju.cims201.GOF.util;

public class CoderUtils {

	/**
	 * name: 字符型编码自增
	 * @param start 父节点中最新子节点的子节点段的分类码
	 * @return 新增子节点的子节点段的分类码
	 */
	public static String CharCode(String start){
		String thecode = "";
		char[] ca = start.toCharArray();
		if(ca[ca.length-1]=='Z'){
			ca[ca.length-1] = 'A';
			int step = ca.length-2;
			while(step>=0){
				
				if(ca[step] != 'Z'){
					ca[step] += 1;
					break;
				}else{
					ca[step] = 'A';
					step --;
				}
			}
			
		}else{
			ca[ca.length-1] += 1;
		}
		
		thecode = new String(ca);
		
		return thecode;
	}
	
	/**
	 * 字符型默认初始
	 * @param num 字符长度
	 * @return 默认初始化字符型编码
	 */
	public static String CharDefault(int num){
		String defaultchar="";
		char[] dc = new char[num];
		for(int i = 0 ; i < dc.length ; i ++ ) {
			
			dc[i] = 'A';
		}
		defaultchar = new String(dc);
		return defaultchar;
	}
	/**
	 * 数字型编码自增
	 * @param start
	 * @return
	 */
	public static String NumCode(String start){
		String thecode = "";
		
		char[] cr = start.toCharArray();
		
		for(int i = cr.length-1;i>=0;i--){
			if(cr[i] == '9'){
				cr[i] = '0';
						
			}else{
				cr[i] += 1;
				break;
			}
				
		}
		thecode = new String(cr);
		return thecode;
	}
	/**
	 * name:数字型默认初始
	 * @param num
	 * @return
	 */
	public static String NumDefault(int num) {
		String defaultstr = "";
		char[] dc = new char[num];
		
		for(int i = 0 ; i < dc.length ; i ++ ) {
			
			dc[i] = '0';
		}
		dc[num-1] = '1';
		
		defaultstr = new String(dc);
		
		return defaultstr;
	}
	/**
	 * 识别码六位处理
	 */
	public static String Sixer(String idstr){
		
		String si ="";
		char[] six = new char[6];
		
		for(int i= 0;i<6;i++){
			six[i] ='0';
		}
		
		
		
		return si;
	}

	/**
	 * name:混合型编码自增
	 * 0123456789ABCDEFGHI
	 * @param start
	 * @return thecode
	 */
	public static String BleCode(String start){
		String thecode = "";
		char[] ca = start.toCharArray();
		
		if(ca[ca.length-1]=='Z'){
			ca[ca.length-1] = '0';
			int step = ca.length-2;
			while(step>=0){
				
				if(ca[step] != 'Z'){
					if(ca[step] == '9'){
						ca[step] ='A';	
					}else{
					ca[step] += 1;
					}
					break;
				}else{
					ca[step] = '0';
					step --;
				}
			}
			
		}else if(ca[ca.length-1] == '9'){
			ca[ca.length-1] = 'A';
		}else {
			ca[ca.length-1] += 1;
		}
		thecode = new String(ca);
		return thecode;
	}
}
