package edu.zju.cims201.GOF.service.flex;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;

import flex.messaging.FactoryInstance;
import flex.messaging.FlexContext;
import flex.messaging.FlexFactory;
import flex.messaging.config.ConfigMap;


class SpringFactoryInstance extends FactoryInstance { 
    private Log log =  LogFactory.getLog(getClass()); 

    SpringFactoryInstance(FlexFactory factory, String id, ConfigMap properties) { 
        super(factory, id, properties); 
    } 

    public Object lookup() { 
        ApplicationContext appContext = WebApplicationContextUtils. 
                getRequiredWebApplicationContext( 
                    FlexContext.getServletConfig().getServletContext() 
        ); 
        String beanName = getSource(); 
        try { 
            log.info("Lookup bean from Spring ApplicationContext: " + beanName); 
            return appContext.getBean(beanName); 
        } 
        catch (NoSuchBeanDefinitionException nex) { 
            System.out.println(nex);
        } 
        catch (BeansException bex) { 
        	 System.out.println(bex);
        } 
        catch (Exception ex) { 
        	 System.out.println(ex); 
        } 
        return null;
    } 
} 
 


