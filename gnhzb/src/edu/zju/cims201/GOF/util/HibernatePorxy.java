package edu.zju.cims201.GOF.util;

import org.hibernate.proxy.HibernateProxy;

public class HibernatePorxy<T> {
	
	public T getRealEntity(T proxy){
		Object proxyObj = proxy;
		Object  realEntity=null;
		if (proxyObj instanceof HibernateProxy) {  
		      realEntity= ((HibernateProxy)proxyObj).getHibernateLazyInitializer().getImplementation() ;    
		 } else{
			 realEntity=proxyObj;
		 }
		return  (T)realEntity;
	}

}
